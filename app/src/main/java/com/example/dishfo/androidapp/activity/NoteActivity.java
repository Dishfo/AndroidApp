package com.example.dishfo.androidapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.adapter.DiscussAdapter;
import com.example.dishfo.androidapp.bean.DiscussInfo;
import com.example.dishfo.androidapp.customview.LoadMoreFooterView;
import com.example.dishfo.androidapp.customview.RefreshHeaderView;
import com.example.dishfo.androidapp.decoration.LinearRecyclerViewDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/12/9.
 */

public class NoteActivity extends BaseActivity {
    private ImageView mImageViewBack = null;
    private ImageView mImageViewMore = null;
    private EasyRefreshLayout mEasyRefreshLayout = null;
    private RecyclerView mRecyclerView = null;
    private Button mButtonDiscuss = null;

    private ImageView mImageViewHead = null;
    private TextView mTextViewNickName = null;
    private ImageView mImageViewLevel = null;
    private TextView mTextViewFollow = null;
    private TextView mTextViewContent = null;
    private ImageView mImageViewAppreciate = null;
    private TextView mTextViewAppreciate = null;
    private TextView mTextViewRead = null;
    private TextView mTextViewDiscuss = null;
    private TextView mTextViewTime = null;

    private DiscussAdapter mAdapter = null;
    private List<DiscussInfo> mDatas = null;

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
        mButtonDiscuss = findView(R.id.activity_note_button_discuss);

        mDatas = new ArrayList<>();
        mAdapter = new DiscussAdapter(R.layout.recyclerview_item_discuss, mDatas);
        mAdapter.addHeaderView(getHeadView());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new LinearRecyclerViewDecoration(this, R.drawable.recyclerview_divider_dark1, LinearRecyclerViewDecoration.VERTIACL));

        mEasyRefreshLayout.setRefreshHeadView(new RefreshHeaderView(this));
        mEasyRefreshLayout.setLoadMoreView(new LoadMoreFooterView(this));
    }

    @Override
    public void initData() {
        //加载toolBar
        Glide.with(this).load(R.mipmap.imageview_back).into(mImageViewBack);
        Glide.with(this).load(R.mipmap.imageview_more).into(mImageViewMore);
        //加载header部分数据
        Glide.with(this).load(R.mipmap.imageview_head).apply(RequestOptions.circleCropTransform()).into(mImageViewHead);
        Glide.with(this).load(R.mipmap.icon_level_10).into(mImageViewLevel);

        for (int i = 0; i < 10; i++) {

            DiscussInfo info = new DiscussInfo();
            info.setmHeaUrl("http://img3.imgtn.bdimg.com/it/u=3848981340,893455915&fm=200&gp=0.jpg");
            info.setmNickName("pby");
            info.setmLevel("" + i);
            if (i % 2 == 0) {
                info.setmReplayedContent("回复 pby1:我觉得楼主的帖子很好");
                info.setmReplayContent("我也觉得不错");
            }
            List<String> imageUrls = new ArrayList<>();
            imageUrls.add("http://img1.imgtn.bdimg.com/it/u=4081034854,2183687467&fm=200&gp=0.jpg");
            imageUrls.add("http://img0.imgtn.bdimg.com/it/u=662783457,1490364573&fm=11&gp=0.jpg");
            info.setmImageUrls(imageUrls);

            info.setmTime("2015-12-10");
            mAdapter.addData(info);
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
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //向上
                if (dy <= 0) {
                    mButtonDiscuss.setVisibility(View.VISIBLE);
                } else {
                    mButtonDiscuss.setVisibility(View.GONE);
                }
            }
        });
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
        mTextViewTime = headView.findViewById(R.id.recyclerView_head_note_textView_time);
        return headView;
    }
}
