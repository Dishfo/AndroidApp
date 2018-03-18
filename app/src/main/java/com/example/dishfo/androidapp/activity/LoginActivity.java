package com.example.dishfo.androidapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.longconnect.LongConService;
import com.example.dishfo.androidapp.mvp.login.LoginModelImpl;
import com.example.dishfo.androidapp.mvp.login.LoginPresentImpl;
import com.example.dishfo.androidapp.mvp.login.LoginTaskContract;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by apple on 2017/12/7.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener,
        LoginTaskContract.LoginView {

    private EditText mEditTextUserName = null;
    private EditText mEditTextPassword = null;
    private Button mButtonLogin = null;
    private TextView mTextViewForgetPassword = null;
    private TextView mTextViewRegister = null;

    private LoginTaskContract.LoginPresent mLoginPresent;
    private PopupWindow mPopupWindow=null;


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
        new LoginPresentImpl(new LoginModelImpl(this),this);
        mLoginPresent.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //登录
            case R.id.activity_login_button_login: {

                mLoginPresent.login(mEditTextUserName.getText().toString(),
                        mEditTextPassword.getText().toString());
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

    @Override
    public void setPresent(LoginTaskContract.LoginPresent present) {
        mLoginPresent=present;
    }

    @Override
    public  void waitToCompete() {
        startWait();

    }

    private void startWait(){
        mPopupWindow=getWaitingWindow();
        mPopupWindow.showAsDropDown(getWindow().getDecorView(),0,0);
    }

    private void stopWait(){
        mPopupWindow.dismiss();
        lightBackground();
    }


    @Override
    public void compete(Object... args) {
        int code= (int) args[0];
        new Handler().postDelayed(() -> {
            stopWait();
            switch (code){
                case 1:
                    LongConService.connect();
                    Intent intent=new Intent(this,MainActivity.class);
                    startActivity(intent);
                    break;
            }
        },1000);

    }

    @Override
    public void error(Object... args) {
        int code= (int) args[0];

        new Handler().postDelayed(() -> {
            stopWait();
            switch (code){
                case 1:
                    Toast.makeText(this,"登录失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        },1000);

    }


    @Override
    public void init(String name, String pwd) {
        mEditTextPassword.setText(pwd);
        mEditTextUserName.setText(name);
    }
}
