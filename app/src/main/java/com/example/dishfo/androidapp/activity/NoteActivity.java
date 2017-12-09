package com.example.dishfo.androidapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;

/**
 * Created by apple on 2017/12/9.
 */

public class NoteActivity extends BaseActivity {
    private ImageView mImageViewBack = null;
    private ImageView mImageViewMore = null;
    private EasyRefreshLayout mEasyRefreshLayout = null;
    private RecyclerView mRecyclerView = null;


    private ImageView mImageViewHead = null;
    private TextView mTextViewNickName = null;
    private ImageView mImageViewLevel = null;
    private TextView mTextViewFollow = null;
    private TextView mTextViewContent = null;
    private ImageView mImageViewAppreciate = null;
    private TextView mTextViewAppreciate = null;
    private TextView mTextViewRead = null;
    private TextView mTextViewDiscuss = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
    }

    @Override
    public void initView() {
        mImageViewBack = findView(R.id.activity_note_imageView_back);
        mImageViewMore = findView(R.id.activity_note_imageView_more);
        mEasyRefreshLayout = findView(R.id.activity_note_easyRefreshLayout);
        mRecyclerView = findView(R.id.activity_note_recyclerView_note);

    }

    @Override
    public void initData() {

    }

    public View getHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.recyclerview_head_note, null);
        mImageViewHead = headView.findViewById(R.id.recyclerView_head_note_imageView_head);
        mTextViewNickName = headView.findViewById(R.id.recyclerView_head_note_textView_nickName);
        mImageViewLevel = headView.findViewById(R.id.recyclerView_head_note_imageView_level);
        mTextViewFollow = headView.findViewById(R.id.recyclerView_head_note_textView_follow);
        mTextViewContent = headView.findViewById(R.id.recyclerView_head_note_textView_content);
        mImageViewAppreciate = headView.findViewById(R.id.recyclerView_head_note_imageView_appreciate);
        mTextViewAppreciate = headView.findViewById(R.id.recyclerView_head_note_textView_appreciate);
        mTextViewRead = headView.findViewById(R.id.recyclerView_head_note_textView_read);
        mTextViewDiscuss = headView.findViewById(R.id.recyclerView_head_note_textView_discuss);

        return headView;
    }
}
