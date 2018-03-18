package com.example.dishfo.androidapp.mvp.UserInfo;

import android.util.Log;

import com.example.dishfo.androidapp.DataAcess.CollectionAcess;
import com.example.dishfo.androidapp.DataAcess.DiscussAcess;
import com.example.dishfo.androidapp.DataAcess.FansAcess;
import com.example.dishfo.androidapp.DataAcess.LikeAcess;
import com.example.dishfo.androidapp.DataAcess.NetMethod;
import com.example.dishfo.androidapp.DataAcess.NoteAcess;
import com.example.dishfo.androidapp.DataAcess.UserAcess;
import com.example.dishfo.androidapp.DataAcess.followAreaAcess;
import com.example.dishfo.androidapp.DataAcess.followUserAcess;
import com.example.dishfo.androidapp.bean.MineMessage;
import com.example.dishfo.androidapp.bean.UserInfo;
import com.example.dishfo.androidapp.netbean.AreaWithNDMapping;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dishfo on 18-3-7.
 */

public class UserInfoModelImpl implements UserInfoTaskContract.UserInfoModel{

    private UserInfoTaskContract.UserInfoPresent present;
    private Observable observable=null;
    @Override
    public void setPresent(UserInfoTaskContract.UserInfoPresent present) {
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
    public void loadUserInfo(String email) {

        Observable<UserInfo> userInfoObservable=getUserInfo(email);
        Observable<Integer> discussObservable=getDiscussNumber(email);

        Observable<Integer> noteObservable=getNoteNumber(email);
        Observable<Integer> follwUserObservable=getFollowUserNumber(email);
        Observable<Integer> followFansObservable=getFansNumber(email);
        Observable<Integer> collectionObservable=getCollectionNumber(email);
        Observable<Integer> likeObservable=getLikeNumber(email);
        Observable<Integer> followAreaObservable=getFollowAreaNumber(email);

        Observable<MineMessage> resObservable=Observable.zip(userInfoObservable,
                noteObservable,follwUserObservable,
                followFansObservable
                ,collectionObservable,likeObservable,followAreaObservable,discussObservable,
                (userinfo,integer, integer2, integer3, integer4, integer5, integer6, integer7) -> {
                    MineMessage mineMessage=new MineMessage();
                    mineMessage.name=userinfo.name;
                    mineMessage.head=userinfo.head;
                    mineMessage.noteNumber=integer+"";
                    mineMessage.followNumber=integer2+"";
                    mineMessage.fansNumber=integer3+"";
                    mineMessage.collectonNoteNumber=integer4+"";
                    mineMessage.likeNoteNumber=integer5+"";
                    mineMessage.followAreaNumber=integer6+"";
                    mineMessage.discussNumber=integer7+"";
                    mineMessage.readNumber="";
                    return mineMessage;
                });
        observable=resObservable;
        resObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(mineMessage ->{
                    compete(UserInfoTaskContract.USER,mineMessage);
                },throwable -> {
                    error(UserInfoTaskContract.USER);
                });

    }

    private Observable<UserInfo> getUserInfo(String email) {
        return UserAcess.INSTANCE.getUserById(email);
    }


    private Observable<Integer> getFollowAreaNumber(String email) {
        Observable<Integer> observable= followAreaAcess.INSTANCE.getFollowAreaByUser(email).map(new MapFunction());
        return observable;
    }

    private Observable<Integer> getLikeNumber(String email) {
        Observable<Integer> observable= LikeAcess.INSTANCE.getLikeByUser(email).map(new MapFunction());
        return observable;
    }

    private Observable<Integer> getCollectionNumber(String email) {
        Observable<Integer> observable= CollectionAcess.INSTANCE.getCollectionByUser(email).map(new MapFunction());
        return observable;
    }

    private Observable<Integer> getFansNumber(String email) {
        Observable<Integer> observable= FansAcess.INSTANCE.getFansByUser(email).map(new MapFunction());
        return observable;
    }

    private Observable<Integer> getFollowUserNumber(String email) {
        Observable<Integer> observable=followUserAcess.INSTANCE.
                getFollowUserByUser(email).map(new MapFunction());

        return observable;
    }

    private Observable<Integer> getNoteNumber(String email) {
        Observable<Integer> noteObservable=Observable.zip(getNoteList(email), new Function<Object[], Integer>() {
            @Override
            public Integer apply(Object[] objects) throws Exception {
                int sum=0;

                for(Object object:objects){
                    JsonObject jsonObject= (JsonObject) object;
                    Double code=jsonObject.get("code").getAsDouble();
                    String result=jsonObject.get("result").getAsString();
                    Log.d("userinfo",jsonObject.toString());
                    if(code==1.0){
                        int size=NetMethod.INSTANCE.getResultSize(result);
                        sum+=size;
                    }else if(code==37.0){

                    }else {
                        error(UserInfoTaskContract.USER);
                        return 0;
                    }
                }
                return sum;
            }
        });
        return noteObservable;
    }

    private List<Observable<JsonObject>> getNoteList(String email) {
        Set<String> areas=AreaWithNDMapping.INSTANCE.getAreas();
        ArrayList<String> list=new ArrayList<>(areas);
        ArrayList<Observable<JsonObject>> observables=new ArrayList<>();
        for(String s:list){
            String note=AreaWithNDMapping.INSTANCE.getNote(s);
            Observable<JsonObject> observable= NoteAcess.INSTANCE.getNoteByUser(email,note);
            observables.add(observable);
        }
        return observables;
    }

    private Observable<Integer> getDiscussNumber(String email){
        Observable<Integer> discssObservable=Observable.zip(getDiscussList(email), new Function<Object[], Integer>() {
            @Override
            public Integer apply(Object[] objects) throws Exception {
                int sum=0;
                for(Object object:objects){
                    JsonObject jsonObject= (JsonObject) object;
                    Double code=jsonObject.get("code").getAsDouble();
                    String result=jsonObject.get("result").getAsString();
                    if(code==1.0){
                        int size=NetMethod.INSTANCE.getResultSize(result);
                        sum+=size;
                    }else if(code==37.0){

                    }else {
                        error(UserInfoTaskContract.USER);
                        return 0;
                    }
                }
                return sum;
            }
        });
        return  discssObservable;
    }

    private List<Observable<JsonObject>> getDiscussList(String email){
        Set<String> areas=AreaWithNDMapping.INSTANCE.getAreas();
        ArrayList<String> list=new ArrayList<>(areas);
        ArrayList<Observable<JsonObject>> observables=new ArrayList<>();
        for(String s:list){
            String discuss=AreaWithNDMapping.INSTANCE.getDiscuss(s);
            Observable<JsonObject> observable=DiscussAcess.INSTANCE.getDiscussByUser(email,discuss);
            observables.add(observable);
        }
        return observables;
    }

    public class MapFunction implements  Function<JsonObject,Integer>{

        @Override
        public Integer apply(JsonObject object) throws Exception {
            int code=object.get("code").getAsInt();
            Log.d("userinfo",object.toString());
            if(code==1){
                return NetMethod.INSTANCE.getResultSize(object.get("result").getAsString());
            }else if(code==37){
                return 0;
            }else {
                error(UserInfoTaskContract.USER);
                return -1;
            }
        }
    }
}
