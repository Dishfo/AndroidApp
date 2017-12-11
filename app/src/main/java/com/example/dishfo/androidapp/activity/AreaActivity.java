package com.example.dishfo.androidapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.bumptech.glide.Glide;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.adapter.NoteAdapter;
import com.example.dishfo.androidapp.bean.NoteInfo;
import com.example.dishfo.androidapp.customview.CustomHorizontalProgressBar;
import com.example.dishfo.androidapp.customview.LoadMoreFooterView;
import com.example.dishfo.androidapp.customview.RefreshHeaderView;
import com.example.dishfo.androidapp.decoration.LinearRecyclerViewDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/12/9.
 */

public class AreaActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout mLinearLayoutSign = null;
    private ImageView mImageViewAreaHead = null;
    private ImageView mImageViewLevel = null;
    private TextView mTextViewSign = null;
    private TextView mTextViewAreaName = null;
    private CustomHorizontalProgressBar mCustomHorizontalProgressBarExperience = null;


    private ImageView mImageViewBack = null;
    private ImageView mImageViewSearch = null;
    private EasyRefreshLayout mEasyRefreshLayout = null;
    private RecyclerView mRecyclerViewNote = null;
    private NoteAdapter mAdapter = null;
    private List<NoteInfo> mDatas = null;


    private LinearLayout mLinearLayoutBottom = null;
    private ImageView mImageViewToTop = null;
    private ImageView mImageViewNewNote = null;
    private ImageView mImageViewUpdate = null;

    private Animation mRotateInfinite = null;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
    }

    @Override
    public void initView() {
        mImageViewBack = findView(R.id.activity_area_imageView_back);
        mImageViewSearch = findView(R.id.activity_area_imageView_search);
        mRecyclerViewNote = findView(R.id.activity_area_recyclerView_area);
        mEasyRefreshLayout = findView(R.id.activity_area_easyRefreshLayout);
        mLinearLayoutBottom = findView(R.id.activity_area_linearLayout_bottom);
        mImageViewToTop = findView(R.id.activity_area_imageView_toTop);
        mImageViewNewNote = findView(R.id.activity_area_imageView_newNote);
        mImageViewUpdate = findView(R.id.activity_area_imageView_update);

        mEasyRefreshLayout.setRefreshHeadView(new RefreshHeaderView(this));
        mEasyRefreshLayout.setLoadMoreView(new LoadMoreFooterView(this));
        mDatas = new ArrayList<>();
        mAdapter = new NoteAdapter(R.layout.recyclerview_item_note, mDatas);
        mAdapter.addHeaderView(getHeaderView());
        mRecyclerViewNote.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewNote.setAdapter(mAdapter);
        mRecyclerViewNote.addItemDecoration(new LinearRecyclerViewDecoration(this, R.drawable.recyclerview_divider_dark1, LinearRecyclerViewDecoration.VERTIACL));

        mRotateInfinite = AnimationUtils.loadAnimation(this, R.anim.rotate_infinite);

        mImageViewToTop.setOnClickListener(this);
        mImageViewNewNote.setOnClickListener(this);
        mImageViewUpdate.setOnClickListener(this);
    }

    @Override
    public void initData() {
        for (int i = 0; i < 10; i++) {
            NoteInfo noteInfo = new NoteInfo();
            noteInfo.setmHeadUrl("http://img3.imgtn.bdimg.com/it/u=4217792878,3100855251&fm=11&gp=0.jpg");
            noteInfo.setmNickName("pby");
            noteInfo.setmTime("2017-12-20");
            noteInfo.setmContent("这是我的第一张帖子");
            noteInfo.setmImageUrl("http://img1.imgtn.bdimg.com/it/u=1794894692,1423685501&fm=27&gp=0.jpg");
            noteInfo.setmAppreciateNumber("120");
            noteInfo.setmReadNumber("100");
            noteInfo.setmDiscussNumber("56");
            noteInfo.setmAreaName("动漫");
            mDatas.add(noteInfo);
            mAdapter.notifyItemInserted(mDatas.size() - 1);
        }
        Glide.with(this).load("http://img4.imgtn.bdimg.com/it/u=2303225764,948824682&fm=200&gp=0.jpg").into(mImageViewAreaHead);

        mEasyRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mEasyRefreshLayout.loadMoreComplete();
                    }
                }, 200);
            }

            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mEasyRefreshLayout.refreshComplete();
                        mImageViewUpdate.clearAnimation();
                    }
                }, 2000);
            }
        });

        mRecyclerViewNote.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //向上
                if (dy < 0) {
                    mLinearLayoutBottom.setVisibility(View.VISIBLE);
                } else {
                    mLinearLayoutBottom.setVisibility(View.GONE);
                }
            }
        });
    }

    private View getHeaderView() {
        View headView = getLayoutInflater().inflate(R.layout.recyclerview_head_area, null);

        mImageViewAreaHead = headView.findViewById(R.id.recyclerView_head_area_imageView_areaHead);
        mTextViewAreaName = headView.findViewById(R.id.recyclerView_head_area_textView_areaName);
        mImageViewLevel = headView.findViewById(R.id.recyclerView_head_area_imageView_level);
        mCustomHorizontalProgressBarExperience = headView.findViewById(R.id.recyclerView_head_area_customHorizontalProgressBar_experience);
        mLinearLayoutSign = headView.findViewById(R.id.recyclerView_head_area_linearLayout_sign);
        mTextViewSign = headView.findViewById(R.id.recyclerView_head_area_textView_sign);
        return headView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_area_imageView_toTop: {
                mRecyclerViewNote.smoothScrollToPosition(0);
                break;
            }
            case R.id.activity_area_imageView_newNote: {
                break;
            }
            case R.id.activity_area_imageView_update: {
                mRecyclerViewNote.smoothScrollToPosition(0);
                mEasyRefreshLayout.autoRefresh();
                mImageViewUpdate.startAnimation(mRotateInfinite);
                break;
            }
        }
    }

}
