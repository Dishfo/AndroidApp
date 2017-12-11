package com.example.dishfo.androidapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.adapter.SettingAdapter;
import com.example.dishfo.androidapp.bean.SettingInfo;
import com.example.dishfo.androidapp.decoration.LinearRecyclerViewDecoration;

import java.util.ArrayList;
import java.util.List;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import  static com.example.dishfo.androidapp.bean.SettingInfo.*;
public class SettingActivity extends BaseActivity implements View.OnClickListener,
        BaseQuickAdapter.OnItemClickListener{


    private List<SettingInfo> mDatas=null;
    private ImageView mImageViewBack = null;
    private Button mButtonExit=null;
    private RecyclerView mRecyclerView=null;
   // private ScrollView container=null;
    private SettingAdapter mSettingAdapter=null;

    private SettingInfo[] settingInfos=new SettingInfo[]{
      new SettingInfo("通用设置",FIRST_TYPE),
            new SettingInfo(" 个人头像",SECOND_TYPE,""),
            new SettingInfo(" 个人昵称","昵称",THRID_TYPE),
            new SettingInfo(" 个人简介",FIRST_TYPE),
            new SettingInfo(" 密码管理",FIRST_TYPE),
            new SettingInfo(" 应用设置",FIRST_TYPE),
            new SettingInfo(" 清除缓存","1.0MB",THRID_TYPE),
            new SettingInfo(" 版本更新","1.0",THRID_TYPE),
            new SettingInfo(" 退出帐号",FOURTH_TYPE)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

    }

    @Override
    public void initView() {
        mImageViewBack = findView(R.id.toolBar_back_imageView_back);
        mImageViewBack.setImageResource(R.mipmap.imageview_back);
     //   mButtonExit=findViewById(R.id.activity_setting_button_exit);
        mRecyclerView=findViewById(R.id.activity_setting_recyclertview_setting);
     //   container=findView(R.id.container);
        mSettingAdapter=new SettingAdapter(mDatas,this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

//        container.requestDisallowInterceptTouchEvent(false);
        mRecyclerView.addItemDecoration(new LinearRecyclerViewDecoration(this,
                R.drawable.recyclerview_divider_dark3,LinearLayoutManager.VERTICAL));

        mRecyclerView.setAdapter(mSettingAdapter);
        mImageViewBack.setOnClickListener(this);
    //    mButtonExit.setOnClickListener(this);

    }

    @Override
    public void initData() {
        mDatas=new ArrayList<>();
        int len=settingInfos.length;
        for(int i=0;i<len;i++){
            mSettingAdapter.addData(settingInfos[i]);
        }
        OverScrollDecoratorHelper.setUpOverScroll(mRecyclerView,OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(this,"item click",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Toast.makeText(this,"item click",Toast.LENGTH_SHORT).show();
    }
}
