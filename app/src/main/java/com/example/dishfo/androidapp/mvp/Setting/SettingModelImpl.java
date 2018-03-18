package com.example.dishfo.androidapp.mvp.Setting;

import android.util.Log;

import com.example.dishfo.androidapp.DataAcess.FileAcess;
import com.example.dishfo.androidapp.DataAcess.NetMethod;
import com.example.dishfo.androidapp.DataAcess.UserAcess;
import com.example.dishfo.androidapp.activity.LoginActivity;
import com.example.dishfo.androidapp.bean.UserInfo;
import com.example.dishfo.androidapp.mvp.Note.NoteTaskContract;
import com.google.gson.JsonObject;


import io.reactivex.Observable;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by dishfo on 18-3-8.
 */

public class SettingModelImpl implements SettingContract.SettingModel{

    private  SettingContract.SettingPresent present;


    @Override
    public void setPresent(SettingContract.SettingPresent present) {
        this.present=present;
    }

    @Override
    public void setArgs(Object... args) {

    }

    @Override
    public void stop() {

    }

    @Override
    public void compete(Object... args) {
        present.onCompete(args[0],args[1]);
    }

    @Override
    public void error(Object... args) {
        present.onError(args[0]);
    }

    @Override
    public void toChangeHead(UserInfo info, String file) {
        Observable<JsonObject> observable=FileAcess.INSTANCE.uploadFile(new String[]{file});
        Observable<JsonObject> userObservable=observable.flatMap(jsonObject -> {
           if(jsonObject.get("code").getAsInt()==1){
                info.head=NetMethod.INSTANCE.getUrl(jsonObject);
                return UserAcess.INSTANCE.updateUserInfoByUser(NetMethod.INSTANCE.getUser(),info);
           }else {
               observable.unsubscribeOn(Schedulers.io());
           }
           return null;
        });
        userObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(jsonObject -> {
                            double code=jsonObject.get("code").getAsDouble();
                            if(code==1){
                                compete(SettingContract.HEAD,info);
                            }else {
                                settingError(userObservable,SettingContract.HEAD);
                            }
                        },
                        throwable -> {
                            settingError(userObservable,SettingContract.HEAD);
                        });
    }

    private void settingError(Observable observable,int code){
        observable.unsubscribeOn(Schedulers.io());
        error(NoteTaskContract.NOTE);
    }

    @Override
    public void toChangeName(UserInfo info,String name) {
        info.name=name;
        Observable<JsonObject> observable=
                UserAcess.INSTANCE.updateUserInfoByUser(NetMethod.INSTANCE.getUser(),info);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(jsonObject -> {
                            double code=jsonObject.get("code").getAsDouble();
                            if(code==1){
                                info.name=name;
                                compete(SettingContract.NAME,info);
                            }else {
                                settingError(observable,SettingContract.NAME);
                            }
                        }
                        );

    }
}
