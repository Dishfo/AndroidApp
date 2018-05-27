package com.example.dishfo.androidapp.mvp.login;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import javax.inject.Inject;

/**
 * Created by dishfo on 18-1-26.
 */

public class LoginPresentImpl implements LoginTaskContract.LoginPresent{

    private LoginTaskContract.LoginModel mLoginModel;
    private LoginTaskContract.LoginView mLoginView;

    private boolean viewAlive;

    private String currentName;
    private String currentPwd;

    @Inject
    public LoginPresentImpl
            (@NonNull  LoginTaskContract.LoginModel loginModel,@NonNull LoginTaskContract.LoginView loginView) {
        this.mLoginModel = loginModel;
        this.mLoginView = loginView;
        viewAlive=true;
        mLoginModel.setPresent(this);
        mLoginView.setPresent(this);
    }

    @Override
    public void start(Object... args) {
        viewAlive=true;
    }

    @Override
    public void stop() {
        mLoginModel.stop();
        viewAlive=false;
    }

    @Override
    public void onCompete(Object... args) {
        if(viewAlive)
            mLoginView.compete(args[0]);
    }

    @Override
    public void onError(Object... args) {
        if(viewAlive)
            mLoginView.error(args[0]);
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
}
