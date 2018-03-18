package com.example.dishfo.androidapp.mvp.login;

import android.text.TextUtils;

/**
 * Created by dishfo on 18-1-26.
 */

public class LoginPresentImpl implements LoginTaskContract.LoginPresent{

    private LoginTaskContract.LoginModel mLoginModel;
    private LoginTaskContract.LoginView mLoginView;

    private String currentName;
    private String currentPwd;

    public LoginPresentImpl
            (LoginTaskContract.LoginModel loginModel, LoginTaskContract.LoginView loginView) {
        this.mLoginModel = loginModel;
        this.mLoginView = loginView;
        mLoginModel.setPresent(this);
        mLoginView.setPresent(this);
    }

    @Override
    public void start(Object... args) {
        mLoginModel.loadUser();
//        mLoginModel.loginNow((String) args[0],(String)args[1]);
    }

    @Override
    public void stop() {
        mLoginModel.stop();
    }

    @Override
    public void onCompete(Object... args) {
        int code= (int) args[0];
        mLoginView.compete(code);
    }

    @Override
    public void onError(Object... args) {
        int code= (int) args[0];
        mLoginView.error(code);
    }

    @Override
    public void login(String name,String pwd) {
        currentName=name;
        currentPwd=pwd;
        if(!config()){
            mLoginView.error(LoginTaskContract.LOGIN);
            return;
        }else {
            mLoginView.waitToCompete();
            mLoginModel.loginNow(currentName,currentPwd);
        }
    }


    private boolean config(){
        if(currentName==null||currentPwd==null||
                TextUtils.isEmpty(currentName)||TextUtils.isEmpty(currentPwd)){
            return false;
        }
        return true;
    }


    @Override
    public void init(String name, String pwd) {
        mLoginView.init(name,pwd);
    }
}
