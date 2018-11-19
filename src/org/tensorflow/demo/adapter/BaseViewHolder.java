package org.tensorflow.demo.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.RequestManager;

import butterknife.ButterKnife;

public abstract class BaseViewHolder<ITEM> extends RecyclerView.ViewHolder {

    protected RequestManager glide;
    public Typeface typeface;
    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public abstract void onBindView(ITEM item);

    public void setCustomFont(Context ctx, String asset) {
        Typeface tf = null;
        try {
            tf = Typeface.createFromAsset(ctx.getAssets(), asset);

            //  Log.e(TAG, "tf typeface: "+ tf.isBold());
        } catch (Exception e) {

            e.printStackTrace();
        }

        typeface = tf;
    }

    public void refresh()
    {

    }

    public void setGlide(RequestManager glide)
    {
        this.glide = glide;
    }
}
