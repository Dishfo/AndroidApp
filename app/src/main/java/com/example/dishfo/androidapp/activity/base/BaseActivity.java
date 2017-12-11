package com.example.dishfo.androidapp.activity.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;


/**
 * Created by apple on 2017/12/7.
 */

public abstract class BaseActivity extends AppCompatActivity{

    private boolean mIsFullScreen = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setImmerseMode(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        fullScreen();
        initView();
        initData();


    }

    public abstract void initView();

    public abstract void initData();

    public void setImmerseMode(Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void fullScreen() {
        if (mIsFullScreen) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.get(this).clearMemory();
    }

    public void setIsFullScreen(boolean isFullScreen) {
        this.mIsFullScreen = isFullScreen;
    }

    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }
}
