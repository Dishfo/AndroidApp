package com.example.dishfo.androidapp.mvp.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.dishfo.androidapp.mvp.FieldConstant;
import com.example.dishfo.androidapp.mvp.TableConstant;
import com.example.dishfo.androidapp.mvp.TypeConstant;
import com.example.dishfo.androidapp.netInterface.JsonGenerator;
import com.example.dishfo.androidapp.DataAcess.NetMethod;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectClassNameAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectConditionAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectFieldsAction;
import com.google.gson.JsonObject;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dishfo on 18-1-26.
 */

public class LoginModelImpl implements LoginTaskContract.LoginModel{

    public final static String USERS="users";
    public final static String NAME="name";
    public final static String PWD="password";

    private Observable<JsonObject> observable;
    private Context context;
    private LoginTaskContract.LoginPresent mLoginPresent;

    public LoginModelImpl(Context context) {
        this.context = context;
    }

    @Override
    public void loginNow(String name,String pwd) {
        Retrofit retrofit=NetMethod.INSTANCE.getRetrofitWithRx();

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

        observable=service.login("login",generator.toJson());
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(jsonobject -> {
                    double code= jsonobject.get("code").getAsDouble();
                    if(code==1){
                        Log.d("login",jsonobject.toString());
                        NetMethod.INSTANCE.setUser(name);
                        compete(LoginTaskContract.LOGIN,name,pwd);
                    }else{
                        error(LoginTaskContract.LOGIN);
                    }
                },throwable -> {
                    Log.d("login",throwable.toString());
                    error(LoginTaskContract.LOGIN);
                });
    }


    private void saveUser(String name, String pwd) {
        SharedPreferences preferences=context.getSharedPreferences(USERS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(NAME,name);
        editor.putString(PWD,pwd);
        editor.commit();
    }

    @Override
    public void loadUser() {
        SharedPreferences preferences=context.getSharedPreferences(USERS,Context.MODE_PRIVATE);
        String name=preferences.getString(NAME,"");
        String pwd=preferences.getString(PWD,"");
        if(!name.equals("")&&!pwd.equals("")){
            mLoginPresent.init(name,pwd);
        }
    }

    @Override
    public void setPresent(LoginTaskContract.LoginPresent present) {
        mLoginPresent=present;
    }

    @Override
    public void setArgs(Object... args) {

    }

    @Override
    public void stop() {
        observable.unsubscribeOn(Schedulers.io());
    }

    @Override
    public void compete(Object... args) {
        saveUser((String) args[1],(String) args[2]);
        mLoginPresent.onCompete(args[0]);
    }

    @Override
    public void error(Object... args) {
        mLoginPresent.onError(args[0]);
    }
}
