package com.example.dishfo.androidapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dishfo.androidapp.GlideApp;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.adapter.NoteAdapter;
import com.example.dishfo.androidapp.bean.AreaInfo;
import com.example.dishfo.androidapp.bean.NoteInfo;
import com.example.dishfo.androidapp.customview.CustomHorizontalProgressBar;
import com.example.dishfo.androidapp.customview.LoadMoreFooterView;
import com.example.dishfo.androidapp.customview.RefreshHeaderView;
import com.example.dishfo.androidapp.decoration.LinearRecyclerViewDecoration;
import com.example.dishfo.androidapp.mvp.AreaModules.AreaModulesContract;
import com.example.dishfo.androidapp.mvp.AreaModules.AreaModulesModelImpl;
import com.example.dishfo.androidapp.mvp.AreaModules.AreaModulesPresentImpl;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by apple on 2017/12/9.
 */

public class AreaActivity extends BaseActivity implements View.OnClickListener
        ,AreaModulesContract.AreaModulesView,BaseQuickAdapter.OnItemClickListener{

    private ImageView mImageViewAreaHead = null;
    private TextView mTextViewAreaName = null;

    private ImageView mImageViewBack = null;
    private ImageView mImageViewSearch = null;
    private EasyRefreshLayout mEasyRefreshLayout = null;
    private RecyclerView mRecyclerViewNote = null;
    private NoteAdapter mAdapter = null;
    private List<NoteInfo> mDatas = null;
    private FloatingActionButton mFloatingActionButton;
    private TextView mTextViewFollow=null;

    private Animation mRotateInfinite = null;

    private AreaModulesContract.AreaModulesPresent present;
    private AreaInfo info;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        info=new AreaInfo();
        info.name=intent.getStringExtra(AREANAME);
        setContentView(R.layout.activity_area);
    }

    @Override
    public void initView() {

        mImageViewBack = findView(R.id.activity_area_imageView_back);
        mImageViewSearch = findView(R.id.activity_area_imageView_search);
        mRecyclerViewNote = findView(R.id.activity_area_recyclerView_area);
        mEasyRefreshLayout = findView(R.id.activity_area_easyRefreshLayout);
        mFloatingActionButton=findViewById(R.id.activity_area_floatbutton);
        mFloatingActionButton.setOnClickListener(this);

        mEasyRefreshLayout.setRefreshHeadView(new RefreshHeaderView(this));
        mEasyRefreshLayout.setLoadMoreView(new LoadMoreFooterView(this));
        mDatas = new ArrayList<>();
        mAdapter = new NoteAdapter(R.layout.recyclerview_item_note, mDatas);
        mAdapter.setOnItemClickListener(this);
        mAdapter.addHeaderView(getHeaderView());
        mRecyclerViewNote.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewNote.setAdapter(mAdapter);
        mRecyclerViewNote.addItemDecoration(new LinearRecyclerViewDecoration(this, R.drawable.recyclerview_divider_dark1, LinearRecyclerViewDecoration.VERTIACL));
        mRotateInfinite = AnimationUtils.loadAnimation(this, R.anim.rotate_infinite);

        new AreaModulesPresentImpl(new AreaModulesModelImpl(),this);
        present.start(info.name);
        Handler handler=new Handler(Looper.getMainLooper());
    }

    @Override
    public void initData() {
        mEasyRefreshLayout.setLoadMoreModel(null);
        mEasyRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onRefreshing() {
                present.start(info.name);
            }
        });

    }

    private View getHeaderView() {
        View headView = getLayoutInflater().inflate(R.layout.recyclerview_head_area, null);
        mTextViewFollow=headView.findViewById(R.id.recyclerView_head_area_textView_follow);
        mImageViewAreaHead = headView.findViewById(R.id.recyclerView_head_area_imageView_areaHead);
        mTextViewAreaName = headView.findViewById(R.id.recyclerView_head_area_textView_areaName);

        mTextViewFollow.setOnClickListener(this);
       // AndroidSchedulers.mainThread()
        return headView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_area_floatbutton: {
                Intent intent=new Intent(this,NewNoteActivity.class);
                intent.putExtra(AREANAME,info);
                startActivity(intent);
                break;
            }
            case R.id.activity_area_imageView_back:
                onBackPressed();
                break;
            case R.id.recyclerView_head_area_textView_follow:
                onFollowArea(this.info);
                break;
        }
    }

    public void loadArea(AreaInfo areaInfo){
        GlideApp.with(this).load(areaInfo.head).placeholder(R.mipmap.placeholder)
                .error(R.mipmap.placeholder)
                .into(mImageViewAreaHead);
        mTextViewAreaName.setText(areaInfo.name);
        if(areaInfo.isFollow){
            mTextViewFollow.setBackground(getResources().getDrawable(R.drawable.button_background_round_normal_org));
        }else {
            mTextViewFollow.setBackground(getResources().getDrawable(R.drawable.button_background_round_normal));
        }
    }

    @Override
    public void setPresent(AreaModulesContract.AreaModulesPresent present) {
        this.present=present;
    }

    @Override
    public void waitToCompete() {

    }

    @Override
    public void compete(Object... args) {
        int code= (int) args[0];
        sendMessage(code,AreaModulesContract.SUCCEED,args[1]);
    }

    @Override
    public void error(Object... args) {
        int code= (int) args[0];
        sendMessage(code,AreaModulesContract.FAILED,null);
    }

    @Override
    public void showNotes(List<NoteInfo> infos) {
        mDatas.clear();
        mAdapter.addData(infos);
    }

    @Override
    public void showArea(AreaInfo areaInfo) {
        this.info=areaInfo;
        loadArea(areaInfo);
    }

    @Override
    public void onFollowArea(AreaInfo areaInfo) {
        present.onFollowArea(areaInfo);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Toast.makeText(this," recommend 点击事件",Toast.LENGTH_SHORT).show();
        NoteInfo info=mDatas.get(position);
        Intent intent=new Intent(this,NoteActivity.class);
        intent.putExtra(NOTEID,info);
        startActivity(intent);
    }

    private void sendMessage(int code,int arg1,Object object){
        Message message=myHandler.obtainMessage();
        message.what=code;
        message.obj=object;
        message.arg1=arg1;
        myHandler.sendMessage(message);
    }

    private void stopWait(){
        if(mEasyRefreshLayout.isRefreshing())
        {
            mEasyRefreshLayout.refreshComplete();
        }
    }

    private Handler myHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            AreaActivity.this.stopWait();
            switch (msg.what){
                case AreaModulesContract.AREA:
                    if(msg.arg1==AreaModulesContract.SUCCEED){
                        showArea((AreaInfo) msg.obj);
                    }else {
                        Toast.makeText(AreaActivity.this,"加载专区失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case AreaModulesContract.NOTE:
                    if(msg.arg1==AreaModulesContract.SUCCEED){
                        showNotes((List<NoteInfo>) msg.obj);
                    }else {
                        Toast.makeText(AreaActivity.this,"加载帖子失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case AreaModulesContract.FOLLOW:
                    if(msg.arg1==AreaModulesContract.SUCCEED){
                        AreaActivity.this.showArea((AreaInfo) msg.obj);
                    }else {
                        Toast.makeText(AreaActivity.this,"提交关注失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
}
