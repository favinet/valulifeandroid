package org.tensorflow.demo.datainterface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import org.tensorflow.demo.DetectorActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetryableCallback<T> implements Callback<T> {

    private int totalRetries = 3;
    private static final String TAG = RetryableCallback.class.getSimpleName();
    private final Call<T> call;
    private int retryCount = 0;
    private Context context;

    public RetryableCallback(Call<T> call, Context context) {
        this.call = call;
        this.context = context;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (!DataInterface.isCallSuccess(response) && (context instanceof Activity))
        {
            onFinalResponse(call,response);
            retryPopup();
        }
//            if (retryCount++ < totalRetries) {
//                Log.v(TAG, "Retrying API Call -  (" + retryCount + " / " + totalRetries + ")");
//                retry();
//            } else
//                onFinalResponse(call, response);
        else
            onFinalResponse(call,response);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
//        Log.e(TAG, t.getMessage());
        if (context instanceof Activity) retryPopup();
//        else onFinalFailure(call, t);
        onFinalFailure(call, t);

//        if (retryCount++ < totalRetries) {
//            Log.v(TAG, "Retrying API Call -  (" + retryCount + " / " + totalRetries + ")");
//            retry();
//        } else
//            onFinalFailure(call, t);
    }

    public void onFinalResponse(Call<T> call, Response<T> response) {

    }

    public void onFinalFailure(Call<T> call, Throwable t) {
    }

    private void retry() {
        call.clone().enqueue(this);
    }

    private void retryPopup()
    {
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("터치다운").setMessage("데이터 요청에 실패하였습니다.\n사용중인 네트워크 상태를 확인해 주세요.\n다시 요청하시겠습니까?").setCancelable(false).setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    retry();
                }
            }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(context instanceof DetectorActivity)
                    {
                        //((DetectorActivity)context).stopIndicator();
                    }
//                ((Activity)context).moveTaskToBack(true);
//                ((Activity)context).finish();
//                android.os.Process.killProcess(android.os.Process.myPid());
                }
            }).create().setCanceledOnTouchOutside(false);

            if(context != null && !((Activity)context).isFinishing()) dialog.show();
        }
        catch (Exception ex)
        {

        }
    }
}
