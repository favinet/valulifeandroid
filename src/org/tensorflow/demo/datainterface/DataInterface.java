package org.tensorflow.demo.datainterface;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import org.tensorflow.demo.models.ImageSearchListVO;

import retrofit2.Call;
import retrofit2.Response;

public class DataInterface extends BasicDataInterface{

    private static DataInterface instance;

    public interface ResponseCallback<T> {
        void onSuccess(T response);
        void onError(String msg);
    }

    public static DataInterface getInstance() {
        if (instance == null) {
            synchronized (DataInterface.class) {
                if (instance == null) {
                    instance = new DataInterface();
                }
            }
        }

        return instance;
    }

    public DataInterface() {
        super();
    }

    public static boolean isCallSuccess(Response response) {
        return response.isSuccessful();
    }

    public void getImageSearchlList(Context context, String q, String start, final ResponseCallback callback)
    {
        Log.e("callback==>", "===>" + callback);
        Log.e("q==>", "===>" + q);
        Log.e("start==>", "===>" + start);

        try {

            String url = "";
            Call<ImageSearchListVO> call = service.getImageSearchlList(q, start);

            call.enqueue(new RetryableCallback<ImageSearchListVO>(call, context) {
                @SuppressLint("LongLogTag")
                @Override
                public void onFinalResponse(Call<ImageSearchListVO> call, retrofit2.Response<ImageSearchListVO> response) {
                    if (callback == null) return;
                    Log.e("response==>", "===>" + response);
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                    } else {
                        Log.e("error getRealtimeArrivalList = ", "==>" + response.errorBody());
                        callback.onError(response.message());
                    }
                }

                @Override
                public void onFinalFailure(Call<ImageSearchListVO> call, Throwable t) {
                    if (callback == null) return;

                    t.printStackTrace();
                    callback.onError(t.getClass().getName());
                }
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
