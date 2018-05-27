package com.example.dishfo.androidapp.activity.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.example.dishfo.androidapp.R;


/**
 * Created by apple on 2017/12/7.
 */

public abstract class BaseActivity extends AppCompatActivity{

    public static final int DARKWINDOW=88;
    public static final int LIGHTWINDOW=154;
    public static String NOTEID="noteid";
    public static String AREANAME="areaname";
    public static String USERINFO="userinfo";

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

    public PopupWindow getWaitingWindow(){
        View contentView= LayoutInflater.from(this).inflate(R.layout.window_wait,null);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
        float densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
        float xdpi = dm.xdpi;
        float ydpi = dm.ydpi;
        int screenWidthDip = dm.widthPixels; // 屏幕宽（dip，如：320dip）
        int screenHeightDip = dm.heightPixels;
        PopupWindow popupWindow=new PopupWindow(contentView,screenWidthDip,screenHeightDip);
        darkBackground();
        return popupWindow;
    }

    public void darkBackground(){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    public void lightBackground(){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f; //0.0-1.0
        getWindow().setAttributes(lp);
    }

}
