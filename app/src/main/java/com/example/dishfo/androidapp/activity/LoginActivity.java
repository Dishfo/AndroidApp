package com.example.dishfo.androidapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;

/**
 * Created by apple on 2017/12/7.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEditTextUserName = null;
    private EditText mEditTextPassword = null;
    private Button mButtonLogin = null;
    private TextView mTextViewForgetPassword = null;
    private TextView mTextViewRegister = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setIsFullScreen(true);
    }

    @Override
    public void initView() {
        mEditTextUserName = findView(R.id.activity_login_edittext_username);
        mEditTextPassword = findView(R.id.activity_login_edittext_password);
        mButtonLogin = findView(R.id.activity_login_button_login);
        mTextViewForgetPassword = findView(R.id.activity_login_textview_forget_password);
        mTextViewRegister = findView(R.id.activity_login_textview_register);

        mButtonLogin.setOnClickListener(this);
        mTextViewForgetPassword.setOnClickListener(this);
        mTextViewRegister.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            //登录
            case R.id.activity_login_button_login: {
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            }
            //忘记密码
            case R.id.activity_login_textview_forget_password: {
                Intent intent = new Intent(this, FindPasswordActivity.class);
                startActivity(intent);
                break;
            }
            //注册
            case R.id.activity_login_textview_register: {
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
