package com.example.dishfo.androidapp.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ajguan.library.IRefreshHeader;
import com.ajguan.library.State;
import com.example.dishfo.androidapp.R;


public class RefreshHeaderView extends FrameLayout implements IRefreshHeader {


    private Animation mRotateUp;
    private Animation mRotateDown;
    private Animation mRotateInfinite;
    private TextView mTextView;

    public RefreshHeaderView(Context context) {
        this(context, null);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 初始化动画
        mRotateUp = AnimationUtils.loadAnimation(context, R.anim.rotate_up);
        mRotateDown = AnimationUtils.loadAnimation(context, R.anim.rotate_down);
        mRotateInfinite = AnimationUtils.loadAnimation(context, R.anim.rotate_infinite);

        inflate(context, R.layout.recyclerview_refresh_header, this);


    }

    @Override
    public void reset() {

    }

    @Override
    public void pull() {

    }

    @Override
    public void refreshing() {

    }

    @Override
    public void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, State state) {

    }

    @Override
    public void complete() {

    }
}
