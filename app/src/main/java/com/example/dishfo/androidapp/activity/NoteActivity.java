package com.example.dishfo.androidapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dishfo.androidapp.DataAcess.NetMethod;
import com.example.dishfo.androidapp.GlideApp;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.adapter.DiscussAdapter;
import com.example.dishfo.androidapp.bean.DiscussInfo;
import com.example.dishfo.androidapp.bean.NoteInfo;
import com.example.dishfo.androidapp.bean.UserInfo;
import com.example.dishfo.androidapp.customview.LoadMoreFooterView;
import com.example.dishfo.androidapp.customview.RefreshHeaderView;
import com.example.dishfo.androidapp.decoration.LinearRecyclerViewDecoration;
import com.example.dishfo.androidapp.mvp.Note.NoteModelImpl;
import com.example.dishfo.androidapp.mvp.Note.NotePresenterImpl;
import com.example.dishfo.androidapp.mvp.Note.NoteTaskContract;
import com.example.dishfo.androidapp.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/12/9.
 */

public class NoteActivity extends BaseActivity implements View.OnClickListener,NoteTaskContract.NoteView{

    private ImageView mImageViewBack = null;
    private ImageView mImageViewMore = null;
    private EasyRefreshLayout mEasyRefreshLayout = null;
    private RecyclerView mRecyclerView = null;
    private Button mButtonDiscuss = null;
    private ViewGroup mViewGroupImages;

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

    private NoteTaskContract.NotePresenter mPresenter=null;

    private NoteInfo info;
    private UserInfo userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        info= (NoteInfo) intent.getSerializableExtra(NOTEID);
        setContentView(R.layout.activity_note);
    }

    @Override
    public void initView() {
        mImageViewBack = findView(R.id.activity_note_imageView_back);
        mImageViewMore = findView(R.id.activity_note_imageView_more);
        mEasyRefreshLayout = findView(R.id.activity_note_easyRefreshLayout);
        mRecyclerView = findView(R.id.activity_note_recyclerView_note);
        mButtonDiscuss = findView(R.id.activity_note_button_discuss);
        mButtonDiscuss.setOnClickListener(this);
        mDatas = new ArrayList<>();
        mAdapter = new DiscussAdapter(R.layout.recyclerview_item_discuss, mDatas);
        mAdapter.addHeaderView(getHeadView());
        mAdapter.setOnItemChildClickListener(this.childClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new LinearRecyclerViewDecoration(this, R.drawable.recyclerview_divider_dark1, LinearRecyclerViewDecoration.VERTIACL));
        mEasyRefreshLayout.setRefreshHeadView(new RefreshHeaderView(this));
        mEasyRefreshLayout.setLoadMoreView(new LoadMoreFooterView(this));
    }

    @Override
    public void initData() {
        Glide.with(this).load(R.mipmap.imageview_back).into(mImageViewBack);
        Glide.with(this).load(R.mipmap.imageview_more).into(mImageViewMore);
        Glide.with(this).load(R.mipmap.imageview_head).apply(RequestOptions.circleCropTransform()).into(mImageViewHead);

        this.mPresenter=new NotePresenterImpl(new NoteModelImpl(),this);
        mEasyRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                mEasyRefreshLayout.loadMoreComplete();
            }

            @Override
            public void onRefreshing() {
                mPresenter.start(info);

            }
        });
        mEasyRefreshLayout.setLoadMoreModel(LoadModel.NONE);
        mPresenter.start(info);

    }

    public View getHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.recyclerview_head_note, null);
        mViewGroupImages=headView.findViewById(R.id.activity_note_head_images);
        mImageViewHead = headView.findViewById(R.id.recyclerView_head_note_imageView_head);
        mTextViewNickName = headView.findViewById(R.id.recyclerView_head_note_textView_nickName);

        mTextViewFollow = headView.findViewById(R.id.recyclerView_head_note_textView_follow);
        mTextViewFollow.setOnClickListener(this);
        mTextViewContent = headView.findViewById(R.id.recyclerView_head_note_textView_content);
        mImageViewAppreciate = headView.findViewById(R.id.recyclerView_head_note_imageView_appreciate);
        mTextViewAppreciate = headView.findViewById(R.id.recyclerView_head_note_textView_appreciate);
        mTextViewRead = headView.findViewById(R.id.recyclerView_head_note_textView_read);
        mTextViewDiscuss = headView.findViewById(R.id.recyclerView_head_note_textView_discuss);
        mTextViewTime = headView.findViewById(R.id.recyclerView_head_note_textView_time);
        return headView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_note_button_discuss:
                Intent intent=new Intent(this,DiscussActivity.class);
                intent.putExtra(NOTEID,info);
                startActivity(intent);
                break;
            case R.id.recyclerView_head_note_textView_follow:
                mPresenter.onFollowUser(userInfo);
        }

    }

    @Override
    public void setPresent(NoteTaskContract.NotePresenter present) {
        this.mPresenter=present;
    }

    @Override
    public void waitToCompete() {
        startWait();
    }

    private void startWait(){

    }

    private void stopWait(){
        if(mEasyRefreshLayout.isRefreshing()){
            mEasyRefreshLayout.refreshComplete();
        }
    }

    @Override
    public void compete(Object... args) {
        sendMessage((int)args[0],NoteTaskContract.SUCCEED,args[1]);
    }

    @Override
    public void error(Object... args) {
        sendMessage((int)args[0],NoteTaskContract.FAILED,null);
    }

    @Override
    public void showDiscuss(List<DiscussInfo> discussInfos) {
        sendMessage(NoteTaskContract.DISCUSS,NoteTaskContract.SUCCEED,discussInfos);
    }

    @Override
    public void showHead(NoteInfo info) {
        sendMessage(NoteTaskContract.NOTE,NoteTaskContract.SUCCEED,info);
    }

    private void showDiscusses(List<DiscussInfo> discussInfos){
        mDatas.clear();
        mAdapter.addData(discussInfos);
        if(mEasyRefreshLayout.isRefreshing()){
            mEasyRefreshLayout.refreshComplete();
        }
    }

    private void showNoteHead(NoteInfo noteInfo) {
        GlideApp.with(this).load(noteInfo.mHeadUrl).placeholder(R.mipmap.placeholder)
                .error(R.mipmap.placeholder).apply(RequestOptions.circleCropTransform()).into(mImageViewHead);

        mTextViewNickName.setText(noteInfo.mNickName);
        mTextViewContent.setText(noteInfo.mContent);
        mTextViewDiscuss.setText(noteInfo.mReadNumber);
        mTextViewAppreciate.setText(noteInfo.mAppreciateNumber);
        mTextViewTime.setText(noteInfo.mTime);
        if(info.mImageUrl!=null&&info.mImageUrl.size()>0){
            ViewGroup group=mViewGroupImages;
            group.removeAllViews();
            for (String url : info.mImageUrl) {
                ImageView imageView = new ImageView(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.topMargin = (int) ScreenUtils.dpToPx(this, 10);
                lp.leftMargin = (int) ScreenUtils.dpToPx(this, 20);
                lp.rightMargin = (int) ScreenUtils.dpToPx(this, 20);
                imageView.setLayoutParams(lp);
                group.addView(imageView);
                Log.d("test","urlis"+url);
                GlideApp.
                        with(this).
                        load(url).placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder).into(imageView);
            }
        }
    }

    private void showHead(UserInfo userInfo){
        this.userInfo=userInfo;
        if(userInfo.isOne){
            mTextViewFollow.setVisibility(View.GONE);
        }else {
            mTextViewFollow.setVisibility(View.VISIBLE);
            if(userInfo.isFollow){
                mTextViewFollow.setBackground(getResources().getDrawable(R.drawable.button_background_round_normal_org));
            }else {
                mTextViewFollow.setBackground(getResources().getDrawable(R.drawable.button_background_round_normal));
            }
        }
    }

    private void sendMessage(int code,int arg1,Object object){
        Message message=mHandler.obtainMessage();
        message.what=code;
        message.arg1=arg1;
        message.obj=object;
        mHandler.sendMessage(message);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            NoteActivity.this.stopWait();
            switch (msg.what){
                case NoteTaskContract.NOTE:
                    if(msg.arg1==NoteTaskContract.SUCCEED){
                        showNoteHead((NoteInfo) msg.obj);
                    }else {
                        Toast.makeText(NoteActivity.this,"加载顶层失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case NoteTaskContract.DISCUSS:
                    if(msg.arg1==NoteTaskContract.SUCCEED){
                        showDiscusses((List<DiscussInfo>) msg.obj);
                    }else {
                        Toast.makeText(NoteActivity.this,"加载列表失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case NoteTaskContract.FOLLOW:
                    if(msg.arg1==NoteTaskContract.SUCCEED){
                        showHead((UserInfo) msg.obj);
                    }else {
                        Toast.makeText(NoteActivity.this,"获取关注信息失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case NoteTaskContract.FOLLOWUSER:
                    if(msg.arg1==NoteTaskContract.SUCCEED){
                        showHead((UserInfo)msg.obj);
                    }else {
                        Toast.makeText(NoteActivity.this,"获取关注信息失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    private BaseQuickAdapter.OnItemChildClickListener childClickListener=
            new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    DiscussInfo info=mDatas.get(position);
                    if(NetMethod.INSTANCE.getUser().equals(info.email)){
                        return;
                    }
                    UserInfo userInfo=new UserInfo();
                    userInfo.name=info.mNickName;
                    userInfo.email=info.email;
                    userInfo.head=info.mHeadUrl;
                    Intent intent=new Intent(NoteActivity.this,TalkActivity.class);
                    intent.putExtra(USERINFO,userInfo);
                    startActivity(intent);
                }
            };

}

