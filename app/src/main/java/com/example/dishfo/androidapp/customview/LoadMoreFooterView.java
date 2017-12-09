package com.example.dishfo.androidapp.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ajguan.library.ILoadMoreView;
import com.example.dishfo.androidapp.R;
import com.github.ybq.android.spinkit.SpinKitView;

/**
 * Created by apple on 2017/12/9.
 */

public class LoadMoreFooterView extends FrameLayout implements ILoadMoreView {

    private TextView mTextViewText = null;
    private SpinKitView mSpinKitViewLoad = null;
    private Animation mRotateInfinite = null;

    public LoadMoreFooterView(@NonNull Context context) {
        super(context);
        mRotateInfinite = AnimationUtils.loadAnimation(context, R.anim.rotate_infinite);
        inflate(context, R.layout.recyclerview_loadmore_footer, this);
        mTextViewText = findViewById(R.id.recyclerView_loadMore_footer_textView_text);
        mSpinKitViewLoad = findViewById(R.id.recyclerView_loadMore_header_spinKitView_load);
    }

    @Override
    public void reset() {
        mTextViewText.setText("加载更多...");
        mSpinKitViewLoad.clearAnimation();
    }

    @Override
    public void loading() {
        mTextViewText.setText("正在加载...");
        mSpinKitViewLoad.startAnimation(mRotateInfinite);
    }

    @Override
    public void loadComplete() {
        mTextViewText.setText("加载完成");
        mSpinKitViewLoad.clearAnimation();
    }

    @Override
    public void loadFail() {
        mTextViewText.setText("加载失败");
        mSpinKitViewLoad.clearAnimation();
    }

    @Override
    public void loadNothing() {
        mTextViewText.setText("没有数据加载");
        mSpinKitViewLoad.clearAnimation();
    }

    @Override
    public View getCanClickFailView() {
        return mTextViewText;
    }
}
