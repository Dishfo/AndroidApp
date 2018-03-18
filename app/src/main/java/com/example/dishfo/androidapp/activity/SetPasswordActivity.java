package com.example.dishfo.androidapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.mvp.register.RegisterPresentImpl;
import com.example.dishfo.androidapp.mvp.register.RegisterTaskContract;

/**
 * Created by apple on 2017/12/8.
 */

public class SetPasswordActivity extends BaseActivity
        implements View.OnClickListener ,RegisterTaskContract.RegisterView{

    private ImageView mImageViewBack = null;
    private EditText mEditTextVerificationCode = null;
    private EditText mEditTextPassword = null;
    private Button mButtonOk = null;
    private TextView mTextViewTip = null;
    private static final String KEY_EMAIL = "email";
    private RegisterTaskContract.RegisterPresenter mPresenter;

    private String email="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
    }

    @Override
    public void initView() {
        mImageViewBack = findView(R.id.activity_set_password_imageView_back);
        mEditTextVerificationCode = findView(R.id.activity_set_password_editText_verification_code);
        mEditTextPassword = findView(R.id.activity_set_password_editText_password);
        mButtonOk = findView(R.id.activity_set_password_button_ok);
        mTextViewTip = findView(R.id.activity_set_password_textView_tip);

        mImageViewBack.setOnClickListener(this);
        mButtonOk.setOnClickListener(this);

        setPresent(new RegisterPresentImpl(this));
    }

    @Override
    public void initData() {
        setEmail(getIntent().getStringExtra(KEY_EMAIL));
        Glide.with(this).asBitmap().load(R.mipmap.imageview_back).into(mImageViewBack);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_set_password_imageView_back: {
                onBackPressed();
                break;
            }
            case R.id.activity_set_password_button_ok: {
                mPresenter.register(email,mEditTextPassword.getText().toString(),
                        mEditTextVerificationCode.getText().toString());
                break;
            }
        }
    }

    private void setEmail(String email) {
        this.email=email;
        if (email != null && !email.equals("")) {
            String textViewTipString = mTextViewTip.getText().toString();
            String substring = mTextViewTip.getText().toString().substring(textViewTipString.indexOf("到") + 1, textViewTipString.indexOf("，"));
            textViewTipString = textViewTipString.replace(substring, email);
            mTextViewTip.setText(textViewTipString);
        } else {
            String string = "未知错误!";
            mTextViewTip.setText(string);
        }
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
        Toast.makeText(this,args[0],Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRegisterSucceed() {
        onBackPressed();
    }

    @Override
    public void showSendEmailSucceed() {

    }

    @Override
    public void showSendEmailError(String... args) {

    }

    @Override
    public void waitUpdate() {

    }

    @Override
    public void stopWait() {

    }
}
