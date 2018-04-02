package com.example.dishfo.androidapp.mvp.AreaModules;

import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.data.repository.AreaRepository;
import com.example.dishfo.androidapp.data.repository.FollowAreaRepository;
import com.example.dishfo.androidapp.data.repository.LikeRepository;
import com.example.dishfo.androidapp.data.repository.NoteRepository;
import com.example.dishfo.androidapp.data.repository.UserRepository;
import com.example.dishfo.androidapp.mvp.ModelManager;
import com.example.dishfo.androidapp.bean.sqlBean.Area;
import com.example.dishfo.androidapp.bean.sqlBean.FollowArea;
import com.example.dishfo.androidapp.bean.sqlBean.Like;
import com.example.dishfo.androidapp.bean.sqlBean.Note;
import com.example.dishfo.androidapp.bean.sqlBean.User;
import com.example.dishfo.androidapp.bean.viewBean.ViewArea;
import com.example.dishfo.androidapp.bean.viewBean.ViewNote;

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
            FollowArea followArea=followAreaRepository.getFollow(ModelManager.INSTANCE.getLoginModel().getCurrentUser(),
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
                List<Note> notes=noteRepository.getNoteByArea(viewArea.getArea().getName());
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
    }

    @Override
    public void FollowArea(ViewArea viewArea) {

        if(viewArea.getFollowArea()==null){
            insertFollowArea(viewArea.getArea(),
                    ModelManager.INSTANCE.getLoginModel().getCurrentUser());
        }else {
            deleteFollowArea(viewArea.getFollowArea());
        }
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
                    compete(AreaModulesContract.FOLLOW,null);
                });
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

    }
}
