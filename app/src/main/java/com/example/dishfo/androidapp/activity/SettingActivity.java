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

import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.decoration.LinearRecyclerViewDecoration;

import java.util.List;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private ImageView mImageViewBack = null;
    private Button mButtonExit=null;
    private RecyclerView mRecyclerView=null;
    private ScrollView container=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void initView() {
        mImageViewBack = findView(R.id.toolBar_back_imageView_back);
        mImageViewBack.setImageResource(R.mipmap.imageview_back);
        mButtonExit=findViewById(R.id.activity_setting_button_exit);
        mRecyclerView=findViewById(R.id.activity_setting_recyclertview_setting);
        container=findView(R.id.container);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        mRecyclerView.addItemDecoration(new LinearRecyclerViewDecoration(this,
                R.drawable.recyclerview_divider_dark3,LinearLayoutManager.VERTICAL));

        mImageViewBack.setOnClickListener(this);
        mButtonExit.setOnClickListener(this);


        OverScrollDecoratorHelper.setUpOverScroll(container);

    }

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {

    }
}
