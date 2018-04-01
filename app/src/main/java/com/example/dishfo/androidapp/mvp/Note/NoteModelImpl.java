package com.example.dishfo.androidapp.mvp.Note;

import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.data.repository.DiscussRepository;
import com.example.dishfo.androidapp.data.repository.FollowUserRepository;
import com.example.dishfo.androidapp.data.repository.UserRepository;
import com.example.dishfo.androidapp.mvp.ModelManager;
import com.example.dishfo.androidapp.sqlBean.Discuss;
import com.example.dishfo.androidapp.sqlBean.FollowUser;
import com.example.dishfo.androidapp.sqlBean.User;
import com.example.dishfo.androidapp.viewBean.ViewDiscuss;
import com.example.dishfo.androidapp.viewBean.ViewNote;
import com.example.dishfo.androidapp.viewBean.ViewNoteHead;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dishfo on 18-2-28.
 */

public class NoteModelImpl implements NoteTaskContract.NoteModel {

    private NoteTaskContract.NotePresenter presenter;

    @Inject
    DiscussRepository discussRepository;
    @Inject
    FollowUserRepository followUserRepository;
    @Inject
    UserRepository userRepository;

    public NoteModelImpl() {
        MyApplication.getRepositoryComponent().inject(this);
    }

    @Override
    public void setPresent(NoteTaskContract.NotePresenter present) {
        this.presenter = present;
    }

    @Override
    public void setArgs(Object... args) {
    }

    @Override
    public void stop() {
    }

    @Override
    public void compete(Object... args) {
        presenter.onCompete(args[0], args[1]);
    }

    @Override
    public void error(Object... args) {
        presenter.onError(args[0]);
    }

    @Override
    public void getDiscuss(ViewNote viewNote) {
        Observable<List<ViewDiscuss>> observable=Observable.create(emitter -> {
            FollowUser followUser=followUserRepository.getFollowUserBy(ModelManager.INSTANCE.getLoginModel().getCurrentUser()
                    ,viewNote.getUser());
            ViewNoteHead viewNoteHead=new ViewNoteHead(viewNote.getNote(),viewNote.getUser(),followUser);
            emitter.onNext(viewNoteHead);
        }).flatMap(o -> {
           ViewNoteHead viewNoteHead= (ViewNoteHead) o;
           compete(NoteTaskContract.NOTE,viewNoteHead);
            return Observable.create(emitter -> {
                List<Discuss> discusses = discussRepository.
                        getDiscussByNote(viewNote.getNote(), viewNote.getArea());
                List<ViewDiscuss> viewDiscusses = new ArrayList<>();

                if(discusses==null){
                    return;
                }
                for (Discuss discuss : discusses) {
                    User user = userRepository.getUserByEmail(discuss.getEmail());
                    viewDiscusses.add(new ViewDiscuss(discuss, user));
                }
                emitter.onNext(viewDiscusses);
            });
        });

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(viewDiscusses -> {
                    compete(NoteTaskContract.DISCUSS,viewDiscusses);
                }/*,throwable -> {
                    error(NoteTaskContract.DISCUSS);
                }*/);
    }

    @Override
    public void followUser(ViewNoteHead viewNoteHead) {
        if(viewNoteHead.getFollowUser()==null){
            insertFollowUser(ModelManager.INSTANCE.getLoginModel().getCurrentUser(),viewNoteHead.getUser());

        }else {
           deleteFollowUser(viewNoteHead.getFollowUser());
        }

    }

    private void insertFollowUser(User user,User followed) {
        Observable.create(emitter -> {
            FollowUser followUser=new FollowUser();
            followUser.setEmail(user.getEmail());
            followUser.setFollowUser(followed.getEmail());
            FollowUser res=followUserRepository.saveFollowUser(followUser);
            emitter.onNext(res);
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(o -> {
                    compete(NoteTaskContract.FOLLOW,o);
                });

    }

    private void deleteFollowUser(FollowUser followUser) {
        Observable.create(emitter -> {
            boolean res=followUserRepository.deleteFollowUser(followUser);
            emitter.onNext(res);
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(o -> {
                    compete(NoteTaskContract.FOLLOW,null);
                });

    }
}
