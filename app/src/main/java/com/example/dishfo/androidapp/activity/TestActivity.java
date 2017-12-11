package com.example.dishfo.androidapp.activity;

import android.graphics.Color;
import android.hardware.Camera;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.adapter.MessageAdapter;
import com.example.dishfo.androidapp.fragment.AreaFragment;
import com.example.dishfo.androidapp.fragment.FoundFragment;
import com.example.dishfo.androidapp.fragment.MessageFragment;
import com.example.dishfo.androidapp.fragment.MineFragment;

import java.util.LinkedHashMap;
import java.util.Map;


public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);


        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, MineFragment.newInstance("",""));
        transaction.commit();

        setContentView(R.layout.activity_test);
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }



    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }
}
