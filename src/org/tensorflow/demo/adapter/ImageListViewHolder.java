package org.tensorflow.demo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import org.tensorflow.demo.R;
import org.tensorflow.demo.models.ItemVO;

import butterknife.BindView;

public class ImageListViewHolder extends BaseViewHolder<ItemVO> implements View.OnClickListener{

    private static TextView mTxtTitle;
    private static TextView mTxtSubTitle;
    private static ImageView mImgThumnail;
    private Context mContext;
    private SimpleItemAdapter.onClickCallback callback;

    public static ImageListViewHolder newInstance(ViewGroup parent, SimpleItemAdapter.onClickCallback callback) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_image_list_row, parent, false);

        mTxtTitle = v.findViewById(R.id.txt_title);
        mTxtSubTitle = v.findViewById(R.id.txt_sub_title);
        mImgThumnail = v.findViewById(R.id.img_thumnail);

        return new ImageListViewHolder(v, parent.getContext(), callback);
    }

    public ImageListViewHolder(View itemView, Context context, SimpleItemAdapter.onClickCallback callback) {
        super(itemView);
        mContext = context;
        this.callback = callback;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onBindView(ItemVO itemVO) {
        Log.e("itemVO = ", "==>" + itemVO);


        if(itemVO == null)
        {
           // Toast.makeText(mContext, "검색 결과의 끝입니다.!!", Toast.LENGTH_LONG).show();
        }
        else
        {
            String imgUrl = itemVO.getLink();
            Log.e("imgUrl = ", "==>" + imgUrl);
            Log.e("glide = ", "==>" + glide);
            if(glide != null)
            {
                glide.load(imgUrl)
                        .apply(new RequestOptions().format(DecodeFormat.PREFER_RGB_565).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).centerInside())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(mImgThumnail);
            }
            String title = itemVO.getTitle();
            String subTitle = itemVO.getSnippet();
            mTxtTitle.setText(title);
            mTxtSubTitle.setText(subTitle);
        }


    }

    @Override
    public void onClick(View view) {
        callback.onClick(view);
    }
}