package com.example.dishfo.androidapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.mvp.register.RegisterPresentImpl;
import com.example.dishfo.androidapp.mvp.register.RegisterTaskContract;

import java.util.Random;

/**
 * Created by apple on 2017/12/8.
 */

public class RegisterActivity extends
        BaseActivity implements View.OnClickListener, RegisterTaskContract.RegisterView{

    private ImageView mImageViewBack = null;
    private EditText mEditTextEmail = null;
    private EditText mEditTextVerificationCode = null;
    private View mViewVerification = null;
    private Button mButtonNext = null;
    private ImageView mImageViewRandom;
    private RegisterTaskContract.RegisterPresenter mPresenter;

    private static final String KEY_EMAIL = "email";
    private String mRandomNum="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initView() {
        mImageViewBack = findView(R.id.activity_register_imageView_back);
        mEditTextEmail = findView(R.id.activity_register_editText_email);
        mEditTextVerificationCode = findView(R.id.activity_register_editText_verification_code);
        mViewVerification = findView(R.id.activity_register_view_verification_code);
        mButtonNext = findView(R.id.activity_register_button_next);
        mImageViewRandom=findView(R.id.activity_register_view_verification_code);

        mImageViewBack.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
        setPresent(new RegisterPresentImpl(this));
        setRandomNum();
    }

    @Override
    public void initData() {
        Glide.with(this).asBitmap().load(R.mipmap.imageview_back).into(mImageViewBack);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_register_imageView_back: {
                onBackPressed();
                break;
            }
            case R.id.activity_register_button_next: {
                if(!mEditTextVerificationCode.getText().toString().equals(mRandomNum)){
                    Toast.makeText(this,"验证码错误",Toast.LENGTH_SHORT).show();
                    return;
                }
                mPresenter.sendEmail(mEditTextEmail.getText().toString());
                break;
            }
        }
    }

    private void setRandomNum(){
        mImageViewRandom.setImageBitmap(randomBitmap());
    }

    private Bitmap randomBitmap(){

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width= (int) (100*metrics.density);
        int height= (int) (40*metrics.density);
        Log.d("test",width+"---"+height);

        Random random=new Random();
        int num=random.nextInt(2000)%(2000-1000+1)+1000;
        mRandomNum=num+"";
        Bitmap bitmap=Bitmap.createBitmap(metrics,width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        Paint paint=new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawRect(0,0,width,height,paint);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        paint.setTextSize(30);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        Rect bound=new Rect();

        paint.getTextBounds(mRandomNum,0,mRandomNum.length(),bound);

        int x= (int) ((bound.left+20)*metrics.density);
        int y= (int) ((height/2)*metrics.density/2);
        canvas.drawText(mRandomNum,x,y,paint);
        return bitmap;
    }

    @Override
    public void setPresent(RegisterTaskContract.RegisterPresenter present) {
        this.mPresenter=present;
    }

    @Override
    public void waitToCompete() {

    }

    @Override
    public void compete(Object... args) {

    }

    @Override
    public void error(Object... args) {

    }

    @Override
    public void showRegisterError(String... args) {

    }

    @Override
    public void showRegisterSucceed() {

    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.stop();
    }

    @Override
    public void showSendEmailSucceed() {
        String email = mEditTextEmail.getText().toString();
        Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SetPasswordActivity.class);
        intent.putExtra(KEY_EMAIL, email);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void showSendEmailError(String... args) {
        Toast.makeText(this,args[0],Toast.LENGTH_SHORT).show();
    }

    @Override
    public void waitUpdate() {

    }

    @Override
    public void stopWait() {

    }
}
