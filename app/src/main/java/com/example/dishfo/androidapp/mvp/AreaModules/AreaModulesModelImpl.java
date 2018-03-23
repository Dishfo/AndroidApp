package com.example.dishfo.androidapp.mvp.AreaModules;

import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.data.repository.AreaRepository;
import com.example.dishfo.androidapp.data.repository.FollowAreaRepository;
import com.example.dishfo.androidapp.data.repository.LikeRepository;
import com.example.dishfo.androidapp.data.repository.NoteRepository;
import com.example.dishfo.androidapp.data.repository.UserRepository;
import com.example.dishfo.androidapp.mvp.login.LoginModelImpl;
import com.example.dishfo.androidapp.sqlBean.Area;
import com.example.dishfo.androidapp.sqlBean.FollowArea;
import com.example.dishfo.androidapp.sqlBean.Like;
import com.example.dishfo.androidapp.sqlBean.Note;
import com.example.dishfo.androidapp.sqlBean.User;
import com.example.dishfo.androidapp.viewBean.ViewArea;
import com.example.dishfo.androidapp.viewBean.ViewNote;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * area板块使用的model
 * Created by dishfo on 18-3-6.
 */

public class AreaModulesModelImpl implements AreaModulesContract.AreaModulesModel{

    private AreaModulesContract.AreaModulesPresent present;

    @Inject
    LikeRepository likeRepository;
    @Inject
    UserRepository userRepository;
    @Inject
    AreaRepository areaRepository;
    @Inject
    NoteRepository noteRepository;
    @Inject
    FollowAreaRepository followAreaRepository;

    public AreaModulesModelImpl(){
        MyApplication.getRepositoryComponent().inject(this);
    }

    @Override
    public void setPresent(AreaModulesContract.AreaModulesPresent present) {
        this.present=present;
    }

    @Override
    public void setArgs(Object... args) {}

    @Override
    public void stop() {}

    @Override
    public void compete(Object... args) {
        int code= (int) args[0];
        present.onCompete(args[0],args[1]);
    }

    @Override
    public void error(Object... args) {
        present.onError(args[0]);
    }

    @Override
    public void getAreaWithNotes(String name) {
        Observable<ViewArea> observable=Observable.create(emitter -> {
            Area area=areaRepository.getAreaByName(name);
            FollowArea followArea=followAreaRepository.getFollow(new LoginModelImpl().getCurrentUser(),
                    area);
            ViewArea viewArea=new ViewArea();
            viewArea.setArea(area);
            viewArea.setFollowArea(followArea);
            compete(AreaModulesContract.AREA,viewArea);
            emitter.onNext(viewArea);
        });

        Observable<List<ViewNote>> listObservable=observable.flatMap(viewArea -> {
            return  Observable.create(emitter -> {
                List<ViewNote> viewNote=new ArrayList<>();
                List<Note> notes=noteRepository.getNoteByArea(viewArea.getArea().getId());
                for(Note note:notes){
                    ViewNote viewNote1=new ViewNote();
                    User user=userRepository.getUserByEmail(note.getEmail());
                    Like like=likeRepository.getLike(user,note);
                    viewNote1.setLike(like);
                    viewNote1.setArea(viewArea.getArea());
                    viewNote1.setUser(user);
                    viewNote1.setNote(note);
                    viewNote.add(viewNote1);
                }
                emitter.onNext(viewNote);
            });
        });

        listObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(viewNotes -> compete(AreaModulesContract.NOTE,viewNotes)
                ,throwable -> {},() -> {});
//        AreaInfo tmpAreaInfo=new AreaInfo();
//        Observable<JsonObject> observable=null;//AreaAcess.INSTANCE.getAreaByName(name);
//        Observable<JsonObject> areaObservable=observable
//                .flatMap(jsonObject -> {
//                    double code=jsonObject.get("code").getAsDouble();
//                    if(code==1.0){
//                        String json=jsonObject.get("result").getAsString();
//                        NetMethod.INSTANCE.praseArea(json,tmpAreaInfo);
//                        return FollowAreaAcess.INSTANCE.
//                                getFollowAreaByFollow(NetMethod.INSTANCE.getUser(),tmpAreaInfo.id);
//                    }else {
//                        observable.unsubscribeOn(Schedulers.io());
//                        error(AreaModulesContract.AREA);
//                        return null;
//                    }
//                });
//
//        Observable<JsonObject> areaObservable1=areaObservable.flatMap(jsonObject -> {
//            double code=jsonObject.get("code").getAsDouble();
//            Log.d("area",jsonObject.toString());
//            if(code==1.0){
//                String id=NetMethod.INSTANCE.getId(jsonObject.get("result").getAsString());
//                tmpAreaInfo.isFollow=true;
//                tmpAreaInfo.followId=id;
//            }else if(code==37.0) {
//                tmpAreaInfo.isFollow=false;
//            }else {
//                    areaObservable.unsubscribeOn(Schedulers.io());
//                    error(AreaModulesContract.AREA);
//                    return null;
//                }
//            compete(AreaModulesContract.AREA,tmpAreaInfo);
//            return null;//NoteAcess.INSTANCE.getNotesByArea(tmpAreaInfo.name);
//        });
//        areaObservable1.
//                observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(jsonObject -> {
//                    double code=jsonObject.get("code").getAsDouble();
//                    if(code==1.0){
//                        String json=jsonObject.get("result").getAsString();
//                        List<NoteInfo> infos=NetMethod.INSTANCE.praseNoteList(json);
//                        compete(AreaModulesContract.NOTE,infos);
//                    }else if(code==37.0){
//                        List<NoteInfo> infos=new ArrayList<>();
//                        compete(AreaModulesContract.NOTE,infos);
//                    }else {
//                        observable.unsubscribeOn(Schedulers.io());
//                        Log.d("area",jsonObject.toString());
//                        error(AreaModulesContract.NOTE);
//                    }
//                },
//                throwable -> {
//                    observable.unsubscribeOn(Schedulers.io());
//                    Log.d("area",throwable.toString());
//                    error(AreaModulesContract.NOTE);
//                });
    }

    @Override
    public void FollowArea(ViewArea viewArea) {
        LoginModelImpl loginModel=new LoginModelImpl();
        if(viewArea.getFollowArea()==null){
            insertFollowArea(viewArea.getArea(),loginModel.getCurrentUser());
        }else {
            deleteFollowArea(viewArea.getFollowArea());
        }
//        if(areaInfo.isFollow
//            deleteFollowArea(areaInfo);
//        }else {
//            insertFollowArea(areaInfo);
//        }
    }


    private void deleteFollowArea(FollowArea followArea){
        Observable.create(emitter -> {
           followAreaRepository.deleteFollowArea(followArea);
           emitter.onComplete();
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(o -> {},throwable -> {
                    error(AreaModulesContract.FOLLOW);
                }, () -> {
                    compete(AreaModulesContract.FOLLOW);
                });
//        Observable<JsonObject> observable=FollowAreaAcess.INSTANCE.deleteFollowArea(areaInfo.followId);
//        observable.observeOn(AndroidSchedlets.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(object -> {
//                    Log.d("area",object.toString());
//                    int code=object.get("code").getAsInt();
//                    if(code==1){
//                        compete(AreaModulesContract.FOLLOW,areaInfo);
//                    }else {
//                        areaError(observable,AreaModulesContract.FOLLOW);
//                    }
//                },throwable -> {
//                    areaError(observable,AreaModulesContract.FOLLOW);
//                });
    }

    private void insertFollowArea(Area area,User user){
        Observable.create(emitter -> {
           FollowArea followArea=new FollowArea();
           followArea.setEmail(user.getEmail());
           followArea.setFollowAreaId(area.getId());
           followArea=followAreaRepository.saveFollowArea(followArea);
           emitter.onNext(followArea);
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(o -> {
                            if(o!=null){
                                compete(AreaModulesContract.FOLLOW,o);
                            }else {
                                error(AreaModulesContract.FOLLOW);
                            }
                        },
                        throwable -> error(AreaModulesContract.FOLLOW),
                        () -> {});

//        Observable<JsonObject> observable=FollowAreaAcess.INSTANCE.insertFolloArea(NetMethod.INSTANCE.getUser(),areaInfo.id);
//        observable.observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(object -> {
//                            int code=object.get("code").getAsInt();
//                            if(code==1){
//                                compete(AreaModulesContract.FOLLOW,areaInfo);
//                            }else {
//                                areaError(observable,AreaModulesContract.FOLLOW);
//                            }
//                        },
//                        throwable -> {
//                            areaError(observable,AreaModulesContract.FOLLOW);
//                        });
    }


    private void areaError(Observable observable,int code){
        error(code);
        observable.unsubscribeOn(Schedulers.io());
    }
}
