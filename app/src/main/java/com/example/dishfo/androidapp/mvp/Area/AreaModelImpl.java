package com.example.dishfo.androidapp.mvp.Area;

import android.content.ContentUris;
import android.hardware.Camera;
import android.util.Log;
import android.widget.PopupWindow;

import com.example.dishfo.androidapp.DataAcess.LikeAcess;
import com.example.dishfo.androidapp.DataAcess.NoteAcess;
import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.bean.NoteInfo;
import com.example.dishfo.androidapp.mvp.FieldConstant;
import com.example.dishfo.androidapp.mvp.TableConstant;
import com.example.dishfo.androidapp.mvp.TypeConstant;
import com.example.dishfo.androidapp.netInterface.AddAction2;
import com.example.dishfo.androidapp.netInterface.AddAction3;
import com.example.dishfo.androidapp.netInterface.InsertValuesAction;
import com.example.dishfo.androidapp.netInterface.JsonGenerator;
import com.example.dishfo.androidapp.DataAcess.NetMethod;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectClassNameAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectConditionAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectFieldsAction;
import com.example.dishfo.androidapp.netbean.AreaWithNDMapping;
import com.example.dishfo.androidapp.util.PropertiesReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dishfo on 18-2-14.
 */

public class AreaModelImpl implements AreaContract.AreaModel{

    private AreaContract.AreaPresent mPresent;
    private NoteInfo currentNoteInfo;
    private Observable<JsonObject> observable;
    private Observable<JsonObject> insertObservable;
    private Observable<JsonObject> deleteObservable;
    @Override
    public void setPresent(AreaContract.AreaPresent present) {
        this.mPresent=present;
    }

    @Override
    public void setArgs(Object... args) {
        currentNoteInfo= (NoteInfo) args[0];
    }

    @Override
    public void stop() {
        if(observable!=null){
            observable.unsubscribeOn(Schedulers.io());
        }
        if(insertObservable!=null){
            insertObservable.unsubscribeOn(Schedulers.io());
        }
        if(deleteObservable!=null){
            deleteObservable.unsubscribeOn(Schedulers.io());
        }
    }

    @Override
    public void compete(Object... args) {
        mPresent.onCompete(args[0],args[1]);
    }

    @Override
    public void error(Object... args) {
        mPresent.onError(args[0]);
    }

    @Override
    public void loadNote() {
        observable=NoteAcess.INSTANCE.getNotesById(TableConstant.Note,"1");
        observable.flatMap(jsonObject -> {
            double code=jsonObject.get("code").getAsDouble();
            if(code==1.0){
                String result=jsonObject.get("result").getAsString();
                List<NoteInfo> infos=NetMethod.INSTANCE.praseNoteList(result);
                currentNoteInfo=infos.get(0);

                return LikeAcess.INSTANCE.getLikeByNote(infos.get(0));
            }else {
                error(AreaContract.RECOMMEND);
                observable.unsubscribeOn(Schedulers.io());
                return null;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(jsonObject -> {
                    double code=jsonObject.get("code").getAsDouble();
                    String result=jsonObject.get("result").getAsString();
                    if(code==1.0){
                        currentNoteInfo.likeId=getIdFromLike(result);
                        currentNoteInfo.isAppreciate=true;
                    }else if(code==37.0){
                        currentNoteInfo.isAppreciate=false;
                    }else {
                        error(AreaContract.RECOMMEND);
                        observable.unsubscribeOn(Schedulers.io());
                    }
                        },
                        throwable -> {
                            observable.unsubscribeOn(Schedulers.io());
                            error(AreaContract.RECOMMEND);
                        },
                        () -> {
                            compete(AreaContract.RECOMMEND,currentNoteInfo);
                        });

    }


    @Override
    public void onAppreciateNote(NoteInfo noteInfo) {
        if(noteInfo.isAppreciate()){
            deleteLike(noteInfo);
        }else {
            insertLike(noteInfo);
        }
    }

    private void insertLike(NoteInfo info){
        insertObservable=LikeAcess.INSTANCE.insertLike(info);
        insertObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(jsonObject -> {
                            double code=jsonObject.get("code").getAsDouble();
                            if(code==1.0){
                                info.isAppreciate=true;
                            }else {
                                error(AreaContract.APPRECIATE);
                            }
                        },
                        throwable -> {
                            insertObservable.unsubscribeOn(Schedulers.io());
                            error(AreaContract.APPRECIATE);
                        },
                        () -> {
                            compete(AreaContract.APPRECIATE,info);
                        });
    }


    private void deleteLike(NoteInfo info){
        deleteObservable=LikeAcess.INSTANCE.deleteLike(info.likeId);
        deleteObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(jsonObject -> {
                            double code=jsonObject.get("code").getAsDouble();
                            if(code==1.0){
                                info.isAppreciate=false;
                            }else {
                                error(AreaContract.NAPPRECIATE);
                            }
                        },
                        throwable -> {
                            deleteObservable.unsubscribeOn(Schedulers.io());
                            error(AreaContract.NAPPRECIATE);
                        },
                        () -> {
                            compete(AreaContract.NAPPRECIATE,info);
                        });
    }

    private String getIdFromLike(String json){
        JsonParser parser=new JsonParser();
        JsonObject jsonObject=parser.parse(json).getAsJsonObject();
        JsonArray array=jsonObject
                .get("result")
                .getAsJsonArray()
                .get(0)
                .getAsJsonObject()
                .get("result")
                .getAsJsonArray();

        Iterator<JsonElement> elements=array.iterator();
        while (elements.hasNext()){
            JsonObject object=elements.next().getAsJsonObject();
            String name=object.get("name").getAsString();
            String keys=PropertiesReader.strTurn("likeNote.id",MyApplication.REVERSE_MAP);
            if(name.equals(keys)){
                return object.get("value").getAsString();
            }
        }
        return "";
    }


}
