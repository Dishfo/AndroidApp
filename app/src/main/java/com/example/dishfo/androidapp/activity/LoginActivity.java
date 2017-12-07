package com.example.dishfo.androidapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;

/**
 * Created by apple on 2017/12/7.
 */

public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setIsFullScreen(true);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
