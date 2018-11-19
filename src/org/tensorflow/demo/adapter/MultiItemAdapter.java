package org.tensorflow.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiItemAdapter  extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Row<?>> mRows = new ArrayList<>();
    private RequestManager glide;
    protected Context context;

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Log.e("list position = ", "==>" + position);
        holder.onBindView(getItem(position));
    }

    @SuppressWarnings("unchecked")
    public <ITEM> ITEM getItem(int position) {
        return (ITEM) mRows.get(position).getItem();
    }

    public void addRow(Row<?> row) {
        this.mRows.add(row);
    }

    public void addRows(List<Row<?>> rows) {
        this.mRows.addAll(rows);
    }

    public void removeFromIndex(int position)
    {
        List<Row<?>> target = mRows.subList(position, mRows.size());
        target.clear();
    }

    public void setRow(int position, Row<?> row) {
        this.mRows.set(position, row);
    }

    public void setRows(List<Row<?>> mRows) {
        clear();
        this.mRows.addAll(mRows);
    }

    public void remove(int position) {
        if (getItemCount() - 1 < position) {
            return;
        }

        this.mRows.remove(position);
        notifyItemRemoved(position);
    }

    public void remove(Row<?> row) {
        this.mRows.remove(row);
    }

    public void clear() {
        this.mRows.clear();
    }

    @Override
    public int getItemCount() {
        return mRows.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mRows.get(position).getItemViewType();
    }

    public static class Row<ITEM> {
        private ITEM item;
        private int itemViewType;

        private Row(ITEM item, int itemViewType) {
            this.item = item;
            this.itemViewType = itemViewType;
        }

        public static <T> Row<T> create(T item, int itemViewType) {
            return new Row<>(item, itemViewType);
        }

        public ITEM getItem() {
            return item;
        }

        public int getItemViewType() {
            return itemViewType;
        }
    }

    public void setGlide(Context context, RequestManager glide) {
        this.context = context.getApplicationContext();
        this.glide = glide;
    }

    public RequestManager getGlide() {
        if(glide == null) glide = Glide.with(context);
        return glide;
    }
}