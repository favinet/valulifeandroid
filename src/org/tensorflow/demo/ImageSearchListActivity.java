package org.tensorflow.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.tensorflow.demo.adapter.ImageSearchData;
import org.tensorflow.demo.adapter.MultiItemAdapter;
import org.tensorflow.demo.adapter.SimpleItemAdapter;
import org.tensorflow.demo.datainterface.DataInterface;
import org.tensorflow.demo.datainterface.DataManager;
import org.tensorflow.demo.models.ImageSearchListVO;
import org.tensorflow.demo.models.ItemVO;
import org.tensorflow.demo.ui.EndlessRecyclerViewScrollListener;
import org.tensorflow.demo.ui.fragment.view.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

public class ImageSearchListActivity extends Activity {

    private RecyclerView recyclerView;
    private ProgressWheel progressWheel;
    private SimpleItemAdapter adapter;
    private boolean isLoadMore;
    private int currentPage = 1;
    private String mResult = "";
    private ImageSearchData mImageSearchData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search_list);

        Intent intent = getIntent();
        String q = intent.getStringExtra("q");
        Log.e("qqqq==>", "qq=>" + q);
        mResult = q;
        initScreen();
    }

    public void initScreen() {
        mImageSearchData = ImageSearchData.getInstance().getCurrentImageSearchVO(this);
        recyclerView = (RecyclerView) findViewById(R.id.use_recycler);
        progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);

        adapter = new SimpleItemAdapter();
        adapter.setGlide(getApplicationContext(), Glide.with(this));
        adapter.setClickCallback(new SimpleItemAdapter.onClickCallback() {
            @Override
            public void onClick(View view) {
                int position = recyclerView.getChildLayoutPosition(view);

                if(position >= adapter.getItemCount()) return;

                ItemVO itemVO = adapter.getItem(position);

                String contextLink = itemVO.getImageVo().getContextLink();


                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contextLink));
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        EndlessRecyclerViewScrollListener mEndlessScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page) {
                Log.e("onMoreData page==>", "page : " + page);
                currentPage = ((page-1) * 10)+1;
                if(currentPage > 99)
                {
                  //  isLoadMore = false;
                    Toast.makeText(getApplicationContext(), "검색 결과의 끝입니다!!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    isLoadMore = true;
                    Log.e("currentPage==>", "currentPage : " + currentPage);
                    // add loading footer
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.addRow(MultiItemAdapter.Row.create(null, SimpleItemAdapter.VIEW_TYPE_IMAGE_LIST));
                            adapter.notifyItemInserted(adapter.getItemCount() - 0);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getImageSearchList(mResult, String.valueOf(currentPage));
                                }
                            }, 500);
                        }
                    });
                }

            }
        };

        recyclerView.addOnScrollListener(mEndlessScrollListener);

        if(currentPage < 100)
            getImageSearchList(mResult, String.valueOf(currentPage));
    }

    @SuppressLint("LongLogTag")
    private void getImageSearchList(String q, String start)
    {
        Log.e("getImageSearchList q : ", "===>" + q);
        Log.e("getImageSearchList start : ", "===>" + start);
        if(Integer.valueOf(start) > 99)
        {
            Toast.makeText(getApplicationContext(), "검색 결과의 끝입니다!!", Toast.LENGTH_LONG).show();
            return;
        }

        if(!isLoadMore) startIndicator(progressWheel);

        DataManager.getInstance(this).api.getImageSearchlList(this, q, start, new DataInterface.ResponseCallback<ImageSearchListVO>() {
            @Override
            public void onSuccess(ImageSearchListVO response) {

                stopIndicator(progressWheel);

                if(isLoadMore)
                {
                    adapter.remove(adapter.getItemCount()-0);
                    isLoadMore = false;
                }

                int curSize = adapter.getItemCount();
               // Log.e("curSize : ", "===>" + curSize);
                List<ItemVO> items = response.getItemsVo();

                //add item
                List<MultiItemAdapter.Row<?>> b_rows = new ArrayList<>();

                for(ItemVO itemVO : items)
                {
                    Log.e("itemVO : ", "===>" + itemVO.getLink());
                    if(b_rows.size() == 10)
                        break;
                    else
                        b_rows.add(MultiItemAdapter.Row.create(itemVO, SimpleItemAdapter.VIEW_TYPE_IMAGE_LIST));
                }

                Log.e("getImageSearchList currentPage : ", "===>" + currentPage);

                if(currentPage == 1)
                {
                    adapter.setRows(b_rows);
                    adapter.notifyDataSetChanged();
                    return;
                }


                Log.e("curSize : ", "===>" + curSize);
                Log.e("b_rows.size() : ", "===>" + b_rows.size());

                adapter.addRows(b_rows);
                adapter.notifyItemRangeInserted(curSize, b_rows.size());

                Log.e("log list success : ", "===>");


            }

            @Override
            public void onError(String err) {
                Toast.makeText(ImageSearchListActivity.this, err, Toast.LENGTH_SHORT).show();
                stopIndicator(progressWheel);
            }
        });
    }

    private void startIndicator(final ProgressWheel wheel) {
        if(wheel != null && wheel.getVisibility() != View.VISIBLE)
        {
            wheel.setVisibility(View.VISIBLE);
            wheel.spin();
        }
    }

    private void stopIndicator(ProgressWheel wheel) {
        if(wheel != null && wheel.getVisibility() == View.VISIBLE)
        {
            wheel.setVisibility(View.INVISIBLE);
            wheel.stopSpinning();
        }
    }
}
