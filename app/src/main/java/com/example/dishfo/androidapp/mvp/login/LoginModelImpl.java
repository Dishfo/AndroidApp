package com.example.dishfo.androidapp.mvp.login;

import android.util.Log;

import com.example.dishfo.androidapp.DataAcess.NetMethod;
import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.data.repository.UserRepository;
import com.example.dishfo.androidapp.mvp.FieldConstant;
import com.example.dishfo.androidapp.mvp.TableConstant;
import com.example.dishfo.androidapp.mvp.TypeConstant;
import com.example.dishfo.androidapp.netInterface.JsonGenerator;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectClassNameAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectConditionAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectFieldsAction;
import com.example.dishfo.androidapp.sqlBean.User;
import com.google.gson.JsonObject;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * 登录相关的model
 * 可用于存储当前用户的单例类
 * Created by dishfo on 18-1-26.
 */
@Singleton
public class LoginModelImpl implements LoginTaskContract.LoginModel{

    private LoginTaskContract.LoginPresent mLoginPresent;

    @Inject
    UserRepository repository;

    private User currentUser;

    @Inject
    public LoginModelImpl() {
        MyApplication.getRepositoryComponent().inject(this);
    }

    @Override
    public void loginNow(String name,String pwd) {
        Retrofit retrofit=new NetMethod().getRetrofitWithRx();
        LoginService service=retrofit.create(LoginService.class);

        JsonGenerator generator=new JsonGenerator();
        generator.openNode()
                .openArray()
                .compete(new SelectClassNameAction(), TableConstant.user)
                .closeNode("className");

        generator.openArray()
                .compete(new SelectFieldsAction(), FieldConstant.email,TableConstant.user)
                .closeNode("field");

        generator.openArray()
                .compete(new SelectConditionAction(),FieldConstant.email,name, TypeConstant.varchar,TableConstant.user,"0")
                .compete(new SelectConditionAction(),FieldConstant.password,pwd, TypeConstant.varchar,TableConstant.user,"0")
                .closeNode("condition")
                .closeNode("");

        Observable<JsonObject> observable=service.login("login",generator.toJson());
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(jsonobject -> {
                    double code= jsonobject.get("code").getAsDouble();
                    if(code==1){
                        Log.d("login",jsonobject.toString());
                        compete(LoginTaskContract.LOGIN,name,pwd);
                    }else{
                        error(LoginTaskContract.LOGIN);
                    }
                },throwable -> {
                    Log.d("login",throwable.toString());
                    error(LoginTaskContract.LOGIN);
                });
    }

    @Override
    public void setPresent(LoginTaskContract.LoginPresent present) {
        mLoginPresent=present;
    }

    @Override
    public void setArgs(Object... args) {}

    @Override
    public void stop() {}

    @Override
    public void compete(Object... args) {
        Observable.create(emitter -> {
            currentUser= repository.getUserByEmail((String) args[1]);
            emitter.onComplete();
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {},
                        throwable -> {
                            Log.d("login",throwable.toString());
                            error(LoginTaskContract.LOGIN);
                        },
                        () -> mLoginPresent.onCompete(args[0]));

    }

    @Override
    public void error(Object... args) {
        mLoginPresent.onError(args[0]);
    }

    public User getCurrentUser(){
        return currentUser;
    }
}
