package org.tensorflow.demo.adapter;

import android.view.View;
import android.view.ViewGroup;

public class SimpleItemAdapter extends MultiItemAdapter {

    public static final int VIEW_TYPE_IMAGE_LIST = 0;

    private onClickCallback clickCallback;
    private onCardCallback cardCallback;

    public interface onClickCallback{
        void onClick(View view);
    }

    public interface onCardCallback{
        void onCardRegister();
        void onDeleted(int position);
        void onLongClick();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        BaseViewHolder holder;

        switch (viewType) {

            case VIEW_TYPE_IMAGE_LIST:
                holder = ImageListViewHolder.newInstance(parent, clickCallback);
                holder.setGlide(getGlide());
                break;

            default:
                holder = null;
        }

        return holder;
    }

    public void setClickCallback(onClickCallback callback)
    {
        this.clickCallback = callback;
    }

    public void setCardCallback(onCardCallback callback)
    {
        this.cardCallback = callback;
    }
}
