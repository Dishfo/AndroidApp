package com.example.dishfo.androidapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * Created by apple on 2017/12/8.
 */

public class FollowPersonActivity extends BaseActivity {
    private ImageView mImageViewBack = null;
    private XRecyclerView mXRecyclerViewNote = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        mImageViewBack = findView(R.id.toolBar_back_imageView_back);
        mXRecyclerViewNote = findView(R.id.activity_follow_person_recyclerView_note);
    }

    @Override
    public void initData() {

    }
}
