package com.example.dishfo.androidapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.ajguan.library.EasyRefreshLayout;
import com.bumptech.glide.Glide;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.adapter.NoteAdapter;
import com.example.dishfo.androidapp.bean.NoteInfo;
import com.example.dishfo.androidapp.customview.LoadMoreFooterView;
import com.example.dishfo.androidapp.customview.RefreshHeaderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/12/8.
 */

public class FollowPersonActivity extends BaseActivity {
    private ImageView mImageViewBack = null;
    private List<NoteInfo> mDatas = null;
    private NoteAdapter mAdapter = null;
    private RecyclerView mRecyclerView = null;
    private EasyRefreshLayout mEasyRefreshLayout = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_person);
    }

    @Override
    public void initView() {
        mImageViewBack = findView(R.id.toolBar_back_imageView_back);
        mRecyclerView = findView(R.id.activity_follow_person_recyclerView);
        mEasyRefreshLayout = findView(R.id.activity_follow_person_easyRefreshLayout);

        mEasyRefreshLayout.setRefreshHeadView(new RefreshHeaderView(this));
        mEasyRefreshLayout.setLoadMoreView(new LoadMoreFooterView(this));
        mDatas = new ArrayList<>();
        mAdapter = new NoteAdapter(R.layout.recyclerview_item_note, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void initData() {
        Glide.with(this).load(R.mipmap.imageview_back).into(mImageViewBack);
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
        mEasyRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mEasyRefreshLayout.loadMoreComplete();
                    }
                }, 2000);
            }

            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mEasyRefreshLayout.refreshComplete();
                    }
                }, 2000);
            }
        });
    }
}
