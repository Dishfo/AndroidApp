package com.example.dishfo.androidapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dishfo.androidapp.data.DataAcess.NetMethod;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.adapter.NoteAdapter;
import com.example.dishfo.androidapp.customview.LoadMoreFooterView;
import com.example.dishfo.androidapp.customview.RefreshHeaderView;
import com.example.dishfo.androidapp.decoration.LinearRecyclerViewDecoration;
import com.example.dishfo.androidapp.mvp.AreaModules.AreaModulesContract;
import com.example.dishfo.androidapp.mvp.AreaModules.AreaModulesPresentImpl;
import com.example.dishfo.androidapp.mvp.ModelManager;
import com.example.dishfo.androidapp.bean.sqlBean.FollowArea;
import com.example.dishfo.androidapp.bean.viewBean.ViewArea;
import com.example.dishfo.androidapp.bean.viewBean.ViewNote;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 *
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
    private List<ViewNote> mDatas = null;
    private FloatingActionButton mFloatingActionButton;
    private TextView mTextViewFollow=null;

    private AreaModulesContract.AreaModulesPresent present;
    private String areaName;
    private ViewArea viewArea;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        areaName=intent.getStringExtra(AREANAME);
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
        mAdapter=new NoteAdapter(R.layout.recyclerview_item_note,mDatas);
        mAdapter.setOnItemClickListener(this);
        mAdapter.addHeaderView(getHeaderView());
        mRecyclerViewNote.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        mRecyclerViewNote.setAdapter(mAdapter);
        mRecyclerViewNote.addItemDecoration(new LinearRecyclerViewDecoration(this, R.drawable.recyclerview_divider_dark1,
                LinearRecyclerViewDecoration.VERTIACL));

        new AreaModulesPresentImpl(ModelManager.INSTANCE.getAreaModulesModel(),this);
        present.start(areaName);
    }

    @Override
    public void initData() {
        mEasyRefreshLayout.setLoadMoreModel(null);
        mEasyRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {}

            @Override
            public void onRefreshing() {
                present.start(areaName);
            }
        });

    }

    private View getHeaderView() {
        View headView = getLayoutInflater().inflate(R.layout.recyclerview_head_area, null);
        mTextViewFollow=headView.findViewById(R.id.recyclerView_head_area_textView_follow);
        mImageViewAreaHead = headView.findViewById(R.id.recyclerView_head_area_imageView_areaHead);
        mTextViewAreaName = headView.findViewById(R.id.recyclerView_head_area_textView_areaName);

        mTextViewFollow.setOnClickListener(this);
        return headView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_area_floatbutton: {
                Intent intent=new Intent(this,NewNoteActivity.class);
                intent.putExtra(AREANAME,viewArea.getArea());
                startActivity(intent);
                break;
            }
            case R.id.activity_area_imageView_back:
                onBackPressed();
                break;
            case R.id.recyclerView_head_area_textView_follow:
                onFollowArea(viewArea);
                break;
        }
    }

    public void loadArea(ViewArea viewArea){
        NetMethod netMethod=new NetMethod();
        netMethod.useGlideWithoutCircle(this,viewArea.getArea().getHead(),mImageViewAreaHead);

        mTextViewAreaName.setText(viewArea.getArea().getName());
        if(viewArea.getFollowArea()!=null){
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


    public void showNotes(List<ViewNote> viewNotes) {
        mDatas.clear();
        mDatas.addAll(viewNotes);
        mAdapter.notifyDataSetChanged();
    }

    public void showArea(ViewArea viewArea) {
        this.viewArea=viewArea;
        loadArea(viewArea);
    }

    @Override
    public void onFollowArea(ViewArea viewArea) {
        present.onFollowArea(viewArea);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Toast.makeText(this," recommend 点击事件",Toast.LENGTH_SHORT).show();
        ViewNote info=mDatas.get(position);
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

    @SuppressLint("HandlerLeak")
    private Handler myHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            AreaActivity.this.stopWait();
            switch (msg.what){
                case AreaModulesContract.AREA:
                    if(msg.arg1==AreaModulesContract.SUCCEED){
                        showArea((ViewArea) msg.obj);
                    }else {
                        Toast.makeText(AreaActivity.this,"加载专区失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case AreaModulesContract.NOTE:
                    if(msg.arg1==AreaModulesContract.SUCCEED){
                        showNotes((List<ViewNote>) msg.obj);
                    }else {
                        Toast.makeText(AreaActivity.this,"加载帖子失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case AreaModulesContract.FOLLOW:
                    if(msg.arg1==AreaModulesContract.SUCCEED){
                        AreaActivity.this.viewArea.setFollowArea((FollowArea) msg.obj);
                        showArea(AreaActivity.this.viewArea);
                    }else {
                        Toast.makeText(AreaActivity.this,"提交关注失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
}
