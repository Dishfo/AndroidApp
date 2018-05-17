package com.example.dishfo.androidapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.adapter.FollowAreaAdapter;
import com.example.dishfo.androidapp.bean.FollowAreaInfo;
import com.example.dishfo.androidapp.decoration.GridRecyclerViewDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/12/9.
 */

public class FollowAreaActivity extends BaseActivity implements View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener {

    private ImageView mImageViewBack = null;
    private LinearLayout mLinearLayoutSearch = null;
    private RecyclerView mRecyclerViewFollowArea = null;
    private TextView mTextViewEdit = null;
    private List<FollowAreaInfo> mDatas = null;
    private FollowAreaAdapter mAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_area);
    }

    @Override
    public void initView() {
        mImageViewBack = findView(R.id.toolBar_back_imageView_back);
        mTextViewEdit = findView(R.id.activity_follow_area_textView_edit);
        mLinearLayoutSearch = findView(R.id.activity_follow_area_linearLayout_search);
        mRecyclerViewFollowArea = findView(R.id.activity_follow_area_recyclerView_follow_area);

        mDatas = new ArrayList<>();
        mAdapter = new FollowAreaAdapter(R.layout.recyclerview_item_follow_area, mDatas);
        mRecyclerViewFollowArea.setAdapter(mAdapter);
        mRecyclerViewFollowArea.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerViewFollowArea.addItemDecoration(new GridRecyclerViewDecoration(R.drawable.recyclerview_divider_dark3, 2, this));

        mTextViewEdit.setOnClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void initData() {
        Glide.with(this).load(R.mipmap.imageview_back).into(mImageViewBack);

        mDatas.add(new FollowAreaInfo("动漫", "2"));
        mDatas.add(new FollowAreaInfo("明星", "17"));
        mDatas.add(new FollowAreaInfo("学习天堂", "10"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_follow_area_textView_edit: {
                if (mTextViewEdit.getText().toString().equals("编辑")) {
                    mTextViewEdit.setText("完成");
                } else {
                    mTextViewEdit.setText("编辑");
                }
                for (FollowAreaInfo info : mDatas) {
                    info.setmIsEdit(!info.ismIsEdit());
                }
                mAdapter.notifyItemRangeChanged(0, mDatas.size());
                break;
            }
        }

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.recyclerView_item_follow_area_imageView_delete: {
                mAdapter.remove(position);
                break;
            }
        }
    }
}
