package com.example.dishfo.androidapp.mvp.UserInfo;

import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.data.repository.CollectionRepository;
import com.example.dishfo.androidapp.data.repository.DiscussRepository;
import com.example.dishfo.androidapp.data.repository.FansRepository;
import com.example.dishfo.androidapp.data.repository.FollowAreaRepository;
import com.example.dishfo.androidapp.data.repository.FollowUserRepository;
import com.example.dishfo.androidapp.data.repository.LikeRepository;
import com.example.dishfo.androidapp.data.repository.NoteRepository;
import com.example.dishfo.androidapp.data.repository.UserRepository;
import com.example.dishfo.androidapp.netMapBean.AreaWithNDMapping;
import com.example.dishfo.androidapp.sqlBean.Collection;
import com.example.dishfo.androidapp.sqlBean.Discuss;
import com.example.dishfo.androidapp.sqlBean.Fans;
import com.example.dishfo.androidapp.sqlBean.FollowArea;
import com.example.dishfo.androidapp.sqlBean.FollowUser;
import com.example.dishfo.androidapp.sqlBean.Like;
import com.example.dishfo.androidapp.sqlBean.Note;
import com.example.dishfo.androidapp.sqlBean.User;
import com.example.dishfo.androidapp.viewBean.ViewMine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dishfo on 18-3-7.
 */

public class UserInfoModelImpl implements UserInfoTaskContract.UserInfoModel{

    private UserInfoTaskContract.UserInfoPresent present;

    @Inject
    CollectionRepository collectionRepository;

    @Inject
    NoteRepository noteRepository;

    @Inject
    LikeRepository likeRepository;

    @Inject
    DiscussRepository discussRepository;

    @Inject
    FollowUserRepository followUserRepository;

    @Inject
    FansRepository fansRepository;

    @Inject
    FollowAreaRepository followAreaRepository;

    @Inject
    public UserRepository userRepository;

    public UserInfoModelImpl(){
        MyApplication.getRepositoryComponent().inject(this);
    }

    @Override
    public void setPresent(UserInfoTaskContract.UserInfoPresent present) {
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
    public void loadUserInfo(String email) {
        Observable<List<Like>> likeObservable=getLikes(email);
        Observable<List<Collection>> collectionObservable=getCollections(email);
        Observable<List<Fans>> fansObservable=getFans(email);
        Observable<List<FollowUser>> followUserObservable=getFollowUsers(email);
        Observable<List<Note>> noteObservable=getNotesList(email);
        Observable<List<Discuss>> discussObservable=getDiscussList(email);
        Observable<List<FollowArea>> followAreaObservable=getFollowAreas(email);
        Observable<User> userObservable=getUser(email);

        Observable<ViewMine> viewMineObservable=Observable.zip(likeObservable,collectionObservable,fansObservable,
                followUserObservable,noteObservable,discussObservable,followAreaObservable,userObservable,
                (likes, collections, fans, followUsers, notes, discusses, followAreas, user) -> {
                    ViewMine viewMine=new ViewMine();
                    viewMine.setLikes(likes);
                    viewMine.setCollections(collections);
                    viewMine.setFans(fans);
                    viewMine.setFollowUsers(followUsers);
                    viewMine.setNotes(notes);
                    viewMine.setDiscusses(discusses);
                    viewMine.setFollowAreas(followAreas);
                    viewMine.setUser(user);
                    return viewMine;
                });


        viewMineObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(viewMine -> {
                    compete(UserInfoTaskContract.USER,viewMine);
                });
    }

    private Observable<User> getUser(String email){
        Observable<User> observable=Observable.create(emitter -> {
            User user=userRepository.getUserByEmail(email);
            emitter.onNext(user);
        });

        return observable;
    }

    private Observable<List<Like>> getLikes(String email){
        Observable<List<Like>> listObservable=Observable.create(emitter -> {
            List<Like> list=likeRepository.getLikeByUser(email);
            if(list!=null){
                emitter.onNext(list);
            }else {
                emitter.onNext(new ArrayList<>());
            }

        });
        return listObservable;
    }

    private Observable<List<Collection>> getCollections(String email){
        Observable<List<Collection>> listObservable=Observable.create(emitter -> {
            List<Collection> list=collectionRepository.getCollectionsByUser(email);
            if(list!=null){
                emitter.onNext(list);
            }else {
                emitter.onNext(new ArrayList<>());
            }

        });

        return listObservable;
    }

    private Observable<List<Fans>> getFans(String email){
        Observable<List<Fans>> listObservable=Observable.create(emitter -> {
            List<Fans> list=new ArrayList<>();
            if(list!=null){
                emitter.onNext(list);
            }else {
                emitter.onNext(new ArrayList<>());
            }

        });

        return listObservable;
    }

    private Observable<List<FollowArea>> getFollowAreas(String email){
        Observable<List<FollowArea>> listObservable=Observable.create(emitter -> {
            List<FollowArea> list=followAreaRepository.getFollowAreasByUser(email);
            if(list!=null){
                emitter.onNext(list);
            }else {
                emitter.onNext(new ArrayList<>());
            }
        });

        return listObservable;
    }

    private Observable<List<FollowUser>> getFollowUsers(String email){
        Observable<List<FollowUser>> listObservable=Observable.create(emitter -> {
            List<FollowUser> list=followUserRepository.getFollowUsersByUser(email);
            if(list!=null){
                emitter.onNext(list);
            }else {
                emitter.onNext(new ArrayList<>());
            }
        });

        return listObservable;
    }

    private Observable<List<Note>> getNotesList(String email){

        Set<String> areas=AreaWithNDMapping.INSTANCE.getAreas();
        ArrayList<String> list=new ArrayList<>(areas);
        Observable<List<Note>> listObservable=Observable.just(new ArrayList<>());
        for(String s:list){
            String note=AreaWithNDMapping.INSTANCE.getNote(s);
            Observable<List<Note>> observable=Observable.create(emitter -> {
                List res=noteRepository.getNotesByUser(email,note);
                if(res!=null){
                    emitter.onNext(res);
                }else {
                    emitter.onNext(new ArrayList<>());
                }

            });

            listObservable=listObservable.zipWith(observable,(notes, notes2) -> {
                notes.addAll(notes2);
                return notes;
            });
        }
        return listObservable;
    }


    private Observable<List<Discuss>> getDiscussList(String email){
        Set<String> areas=AreaWithNDMapping.INSTANCE.getAreas();
        ArrayList<String> list=new ArrayList<>(areas);
        Observable<List<Discuss>> listObservable=Observable.just(new ArrayList<>());
        for(String s:list){
            String discuss=AreaWithNDMapping.INSTANCE.getDiscuss(s);
            Observable<List<Discuss>> observable=Observable.create(emitter -> {
                List res=discussRepository.getDiscussByUser(email,discuss);
                if(res!=null){
                    emitter.onNext(res);
                }else {
                    emitter.onNext(new ArrayList<>());
                }
            });
            listObservable=listObservable.zipWith(observable,(discusses, discusses2) -> {
                discusses.addAll(discusses2);
                return discusses;
            });
        }

        return listObservable;
    }

}
