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
    private View mArrowIcon;
    private View mSuccessIcon;
    private View mLoadingIcon;

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

        mTextView = findViewById(R.id.recyclerView_refresh_header_textView_text);
        mArrowIcon = findViewById(R.id.recyclerView_refresh_header_imageView_arrow);
        mSuccessIcon = findViewById(R.id.recyclerView_refresh_header_view_success);
        mLoadingIcon = findViewById(R.id.recyclerView_refresh_header_spinKitView_load);
    }

    @Override
    public void reset() {
        mTextView.setText(getResources().getText(R.string.header_reset));
        mSuccessIcon.setVisibility(INVISIBLE);
        mArrowIcon.setVisibility(VISIBLE);
        mArrowIcon.clearAnimation();
        mLoadingIcon.setVisibility(INVISIBLE);
        mLoadingIcon.clearAnimation();
    }

    @Override
    public void pull() {

    }

    @Override
    public void refreshing() {
        mArrowIcon.setVisibility(INVISIBLE);
        mLoadingIcon.setVisibility(VISIBLE);
        mTextView.setText(getResources().getText(R.string.header_refreshing));
        mArrowIcon.clearAnimation();
        mLoadingIcon.startAnimation(mRotateInfinite);
    }

    @Override
    public void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, State state) {
        // 往上拉
        if (currentPos < refreshPos && lastPos >= refreshPos) {
            Log.i("", ">>>>up");
            if (isTouch && state == State.PULL) {
                mTextView.setText(getResources().getText(R.string.header_pull));
                mArrowIcon.clearAnimation();
                mArrowIcon.startAnimation(mRotateDown);
            }
            // 往下拉
        } else if (currentPos > refreshPos && lastPos <= refreshPos) {
            Log.i("", ">>>>down");
            if (isTouch && state == State.PULL) {
                mTextView.setText(getResources().getText(R.string.header_pull_over));
                mArrowIcon.clearAnimation();
                mArrowIcon.startAnimation(mRotateUp);
            }
        }
    }

    @Override
    public void complete() {
        mLoadingIcon.setVisibility(INVISIBLE);
        mLoadingIcon.clearAnimation();
        mArrowIcon.clearAnimation();
        mSuccessIcon.setVisibility(VISIBLE);
        mTextView.setText(getResources().getText(R.string.header_completed));
    }
}
