package com.example.dishfo.androidapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.longconnect.LongConService;
import com.example.dishfo.androidapp.mvp.ModelManager;
import com.example.dishfo.androidapp.mvp.login.LoginPresentImpl;
import com.example.dishfo.androidapp.mvp.login.LoginService;
import com.example.dishfo.androidapp.mvp.login.LoginTaskContract;

import javax.inject.Inject;

/**
 *
 * 用于登录的界面
 * p通过model 发起特定的登录操作
 * 非普通的数据库相关操作
 * Created by apple on 2017/12/7.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener,
        LoginTaskContract.LoginView {

    public final static String USERS="users";
    public final static String NAME="name";
    public final static String PWD="password";

    private EditText mEditTextUserName = null;
    private EditText mEditTextPassword = null;
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
        Button mButtonLogin = findView(R.id.activity_login_button_login);
        TextView mTextViewForgetPassword = findView(R.id.activity_login_textview_forget_password);
        TextView mTextViewRegister = findView(R.id.activity_login_textview_register);
        mButtonLogin.setOnClickListener(this);
        mTextViewForgetPassword.setOnClickListener(this);
        mTextViewRegister.setOnClickListener(this);

    }

    @Inject
    @Override
    public void initData() {
        new LoginPresentImpl(ModelManager.INSTANCE.getLoginModel(),this);
        mLoginPresent.start();
        loadUser();
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
                    saveUser(mEditTextUserName.getText().toString()
                            ,mEditTextPassword.getText().toString());
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
    protected void onDestroy() {
        super.onDestroy();
        if(LongConService.isConnection()){
            LongConService.close();
        }
    }

    private void loadUser(){
        SharedPreferences preferences = this.getSharedPreferences(USERS, Context.MODE_PRIVATE);
        String name = preferences.getString(NAME, "");
        String pwd = preferences.getString(PWD, "");
        init(name,pwd);
    }

    private void saveUser(String name,String pwd){
        SharedPreferences preferences = this.getSharedPreferences(USERS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(NAME,name);
        editor.putString(PWD,pwd);
        editor.apply();
    }

    private void init(String name, String pwd) {
        mEditTextPassword.setText(pwd);
        mEditTextUserName.setText(name);
    }
}
