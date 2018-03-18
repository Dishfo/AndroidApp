package com.example.dishfo.androidapp.mvp.AreaModules;

import android.util.Log;

import com.example.dishfo.androidapp.DataAcess.AreaAcess;
import com.example.dishfo.androidapp.DataAcess.NetMethod;
import com.example.dishfo.androidapp.DataAcess.NoteAcess;
import com.example.dishfo.androidapp.DataAcess.followAreaAcess;
import com.example.dishfo.androidapp.bean.AreaInfo;
import com.example.dishfo.androidapp.bean.NoteInfo;
import com.example.dishfo.androidapp.mvp.login.LoginTaskContract;
import com.example.dishfo.androidapp.util.CompeteAction;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by dishfo on 18-3-6.
 */

public class AreaModulesModelImpl implements AreaModulesContract.AreaModulesModel{

    private AreaModulesContract.AreaModulesPresent present;

    @Override
    public void setPresent(AreaModulesContract.AreaModulesPresent present) {
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
        int code= (int) args[0];
        if(code==AreaModulesContract.FOLLOW){
            AreaInfo info= (AreaInfo) args[1];
            info.isFollow=!info.isFollow;
        }
        present.onCompete(args[0],args[1]);
    }

    @Override
    public void error(Object... args) {
        present.onError(args[0]);
    }

    @Override
    public void getAreaWithNotes(String name) {
        AreaInfo tmpAreaInfo=new AreaInfo();
        Observable<JsonObject> observable=AreaAcess.INSTANCE.getAreaByName(name);
        Observable<JsonObject> areaObservable=observable
                .flatMap(jsonObject -> {
                    double code=jsonObject.get("code").getAsDouble();
                    if(code==1.0){
                        String json=jsonObject.get("result").getAsString();
                        NetMethod.INSTANCE.praseArea(json,tmpAreaInfo);
                        return followAreaAcess.INSTANCE.
                                getFollowAreaByFollow(NetMethod.INSTANCE.getUser(),tmpAreaInfo.id);
                    }else {
                        observable.unsubscribeOn(Schedulers.io());
                        error(AreaModulesContract.AREA);
                        return null;
                    }
                });

        Observable<JsonObject> areaObservable1=areaObservable.flatMap(jsonObject -> {
            double code=jsonObject.get("code").getAsDouble();
            Log.d("area",jsonObject.toString());
            if(code==1.0){
                String id=NetMethod.INSTANCE.getId(jsonObject.get("result").getAsString());
                tmpAreaInfo.isFollow=true;
                tmpAreaInfo.followId=id;
            }else if(code==37.0) {
                tmpAreaInfo.isFollow=false;
            }else {
                    areaObservable.unsubscribeOn(Schedulers.io());
                    error(AreaModulesContract.AREA);
                    return null;
                }
            compete(AreaModulesContract.AREA,tmpAreaInfo);
            return NoteAcess.INSTANCE.getNotesByArea(tmpAreaInfo.name);
        });
        areaObservable1.
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(jsonObject -> {
                    double code=jsonObject.get("code").getAsDouble();
                    if(code==1.0){
                        String json=jsonObject.get("result").getAsString();
                        List<NoteInfo> infos=NetMethod.INSTANCE.praseNoteList(json);
                        compete(AreaModulesContract.NOTE,infos);
                    }else if(code==37.0){
                        List<NoteInfo> infos=new ArrayList<>();
                        compete(AreaModulesContract.NOTE,infos);
                    }else {
                        observable.unsubscribeOn(Schedulers.io());
                        Log.d("area",jsonObject.toString());
                        error(AreaModulesContract.NOTE);
                    }
                },
                throwable -> {
                    observable.unsubscribeOn(Schedulers.io());
                    Log.d("area",throwable.toString());
                    error(AreaModulesContract.NOTE);
                });
    }

    @Override
    public void FollowArea(AreaInfo areaInfo) {
        if(areaInfo.isFollow){
            deleteFollowArea(areaInfo);
        }else {
            insertFollowArea(areaInfo);
        }
    }

    private void deleteFollowArea(AreaInfo areaInfo){
        Observable<JsonObject> observable=followAreaAcess.INSTANCE.deleteFollowArea(areaInfo.followId);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object -> {
                    Log.d("area",object.toString());
                    int code=object.get("code").getAsInt();
                    if(code==1){
                        compete(AreaModulesContract.FOLLOW,areaInfo);
                    }else {
                        areaError(observable,AreaModulesContract.FOLLOW);
                    }
                },throwable -> {
                    areaError(observable,AreaModulesContract.FOLLOW);
                });
    }

    private void insertFollowArea(AreaInfo areaInfo){
        Observable<JsonObject> observable=followAreaAcess.INSTANCE.insertFolloArea(NetMethod.INSTANCE.getUser(),areaInfo.id);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object -> {
                            int code=object.get("code").getAsInt();
                            if(code==1){
                                compete(AreaModulesContract.FOLLOW,areaInfo);
                            }else {
                                areaError(observable,AreaModulesContract.FOLLOW);
                            }
                        },
                        throwable -> {
                            areaError(observable,AreaModulesContract.FOLLOW);
                        });
    }


    private void areaError(Observable observable,int code){
        error(code);
        observable.unsubscribeOn(Schedulers.io());
    }
}
