package com.example.dishfo.androidapp.mvp.Note;

import android.content.Intent;
import android.util.Log;

import com.example.dishfo.androidapp.DataAcess.DiscussAcess;
import com.example.dishfo.androidapp.DataAcess.NoteAcess;
import com.example.dishfo.androidapp.DataAcess.UserAcess;
import com.example.dishfo.androidapp.DataAcess.followUserAcess;
import com.example.dishfo.androidapp.bean.DiscussInfo;
import com.example.dishfo.androidapp.bean.NoteInfo;
import com.example.dishfo.androidapp.bean.UserInfo;
import com.example.dishfo.androidapp.mvp.FieldConstant;
import com.example.dishfo.androidapp.mvp.TableConstant;
import com.example.dishfo.androidapp.mvp.TypeConstant;
import com.example.dishfo.androidapp.netInterface.JsonGenerator;
import com.example.dishfo.androidapp.DataAcess.NetMethod;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectConditionAction;
import com.example.dishfo.androidapp.netbean.AreaWithNDMapping;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dishfo on 18-2-28.
 */

public class NoteModelImpl implements NoteTaskContract.NoteModel{

    private NoteTaskContract.NotePresenter presenter;
    private NoteInfo info;
    private List<DiscussInfo> tmpdiscusses;
    private NoteInfo tmpNoteInfo;
    private UserInfo tmpuserInfo=new UserInfo();

    public NoteModelImpl() {
    }

    @Override
    public void setPresent(NoteTaskContract.NotePresenter present) {
        this.presenter=present;
    }

    @Override
    public void setArgs(Object... args) {
        info= (NoteInfo) args[0];
    }

    @Override
    public void stop() {

    }

    @Override
    public void compete(Object... args) {
        presenter.onCompete(args[0],args[1]);
    }

    @Override
    public void error(Object... args) {
        presenter.onError(args[0]);
    }


    @Override
    public void getDiscuss() {
        String discussName= AreaWithNDMapping.INSTANCE.getDiscuss(info.mAreaName);
        String notename= AreaWithNDMapping.INSTANCE.getNote(info.mAreaName);
        String email=NetMethod.INSTANCE.getUser();

        Observable<JsonObject> observable=DiscussAcess.INSTANCE.getDiscussByNote(info,discussName);
        Observable<JsonObject> headObservable1=NoteAcess.INSTANCE.getNotesById(notename,info.id);

        Observable<Integer> resObservable1=Observable.zip(observable,headObservable1,(jsonObject, jsonObject2) -> {
            int code1=jsonObject.get("code").getAsInt();
            int code2=jsonObject2.get("code").getAsInt();
            if(code1!=1&&code1!=37){
                return NoteTaskContract.DISCUSS;
            }
            if(code2!=1){
                return NoteTaskContract.NOTE;
            }
            if(code1==1){
                this.tmpdiscusses=parseDicusses(jsonObject);
            }else {
                this.tmpdiscusses=new ArrayList<>();
            }

            this.tmpNoteInfo=parseHead(jsonObject2);
            return NoteTaskContract.COMPETE;
        });

        Observable<JsonObject> followObservable1=resObservable1.flatMap(integer -> {
            if(integer==NoteTaskContract.COMPETE){
                tmpuserInfo.name=tmpNoteInfo.mNickName;
                tmpuserInfo.email=tmpNoteInfo.email;
                tmpuserInfo.head=tmpNoteInfo.mHeadUrl;
                if(email.equals(tmpNoteInfo.email)){
                    tmpuserInfo.isOne=true;
                }
                return followUserAcess.INSTANCE.getFollowUserByFollow(email,tmpNoteInfo.email);
            }else {
                noteError(resObservable1,NoteTaskContract.NOTE);
            }
            return null;
        });

        followObservable1.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object -> {
                    int code=object.get("code").getAsInt();
                    if(code==1){
                        String result=object.get("result").getAsString();
                        tmpuserInfo.followId=NetMethod.INSTANCE.getId(result);
                        tmpuserInfo.isFollow=true;
                    }else if(code==37){
                        tmpuserInfo.isFollow=false;
                    }else {
                        noteError(followObservable1,NoteTaskContract.NOTE);
                    }
                },throwable -> {
                    noteError(followObservable1,NoteTaskContract.NOTE);
                },() -> {
                    presenter.onshowHead(tmpNoteInfo);
                    presenter.loadDicuss(tmpdiscusses);
                    compete(NoteTaskContract.FOLLOW,tmpuserInfo);
                });
    }

    @Override
    public void followUser(UserInfo userInfo) {
        if(userInfo.isFollow){
            deleteFollowUser(userInfo);
        }else {
            insertFollowUser(userInfo);
        }
    }

    private void insertFollowUser(UserInfo userInfo){
        Observable<JsonObject> observable=followUserAcess.INSTANCE.
                insertFollow(NetMethod.INSTANCE.getUser(),userInfo.email);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object -> {
                         //   Log.d("test",object.get("result").getAsString());
                            int code=object.get("code").getAsInt();
                            if(code==1){
                                userInfo.isFollow=true;
                                userInfo.followId=NetMethod.INSTANCE.getIdFromInsert(object.get("result").getAsString());
                                compete(NoteTaskContract.FOLLOWUSER,userInfo);
                            }else {
                                noteError(observable,NoteTaskContract.FOLLOWUSER);
                            }
                        },
                        throwable -> {
                            noteError(observable,NoteTaskContract.FOLLOWUSER);
                        });
    }

    private void deleteFollowUser(UserInfo userInfo){
        Observable<JsonObject> observable=followUserAcess.INSTANCE.deleteFollow(userInfo.followId);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object -> {
                            int code=object.get("code").getAsInt();
                            if(code==1){
                                userInfo.isFollow=false;
                                compete(NoteTaskContract.FOLLOWUSER,userInfo);
                            }else {
                                noteError(observable,NoteTaskContract.FOLLOWUSER);
                            }
                        },
                        throwable -> {
                            noteError(observable,NoteTaskContract.FOLLOWUSER);
                        });
    }

    private List<DiscussInfo> parseDicusses(JsonObject object){
        List<DiscussInfo> list=NetMethod.INSTANCE.praseDiscussList(object.get("result").getAsString());
        return list;
    }

    private NoteInfo parseHead(JsonObject object){
        NoteInfo info=NetMethod.INSTANCE
                .praseNoteList(object.get("result").getAsString())
                .get(0);
        return info;
    }

    private void noteError(Observable observable,int code){
        observable.unsubscribeOn(Schedulers.io());
        error(code);
    }
}
