package com.example.dishfo.androidapp.mvp.Area;

import android.util.Log;

import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.data.repository.AreaRepository;
import com.example.dishfo.androidapp.data.repository.LikeRepository;
import com.example.dishfo.androidapp.data.repository.NoteRepository;
import com.example.dishfo.androidapp.data.repository.UserRepository;
import com.example.dishfo.androidapp.sqlBean.Area;
import com.example.dishfo.androidapp.sqlBean.Like;
import com.example.dishfo.androidapp.sqlBean.Note;
import com.example.dishfo.androidapp.sqlBean.User;
import com.example.dishfo.androidapp.viewBean.ViewNote;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dishfo on 18-2-14.
 */

public class AreaModelImpl implements AreaContract.AreaModel{

    private AreaContract.AreaPresent mPresent;
    private  final static String NOTE_ID="1";
    private final static String RECOMM_AREA="开发团队";
    private ViewNote viewNote=null;

    @Inject
    UserRepository userRepository;
    @Inject
    AreaRepository areaRepository;
    @Inject
    LikeRepository likeRepository;
    @Inject
    NoteRepository noteRepository;

    public AreaModelImpl(){
        MyApplication.getRepositoryComponent().inject(this);
    }

    @Override
    public void setPresent(AreaContract.AreaPresent present) {
        this.mPresent=present;
    }

    @Override
    public void setArgs(Object... args) {}

    @Override
    public void stop() {}

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
        Observable.create(emitter -> {
            Area area=areaRepository.getAreaByName(RECOMM_AREA);
            Note note=noteRepository.getNoteById(NOTE_ID, area.getName());
            User user=userRepository.getUserByEmail(note.getEmail());
            Like like=likeRepository.getLike(user,note);

            ViewNote viewNote=new ViewNote(note,area,like,user);
            emitter.onNext(viewNote);
            emitter.onComplete();
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(o -> this.viewNote= (ViewNote) o,
                        throwable -> {
                            Log.d("test",throwable.toString());
                            error(AreaContract.RECOMMEND);
                        },
                        () -> compete(AreaContract.RECOMMEND,viewNote));
    }


    @Override
    public void onAppreciateNote(ViewNote viewNote) {
        if(viewNote.getLike()!=null){
            deleteLike(viewNote);
        }else {
            insertLike(viewNote);
        }
    }

    private void insertLike(ViewNote viewNote){
        final Like like=new Like();
        like.setAreaId(viewNote.getArea().getId());
        like.setEmail(viewNote.getNote().getEmail());
        like.setNoteId(viewNote.getNote().getId());
        Observable.create(emitter -> {
            viewNote.setLike(likeRepository.saveLike(like));
            emitter.onComplete();
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(o -> {},
                        throwable -> {
                            Log.d("test",throwable.toString());
                            error(AreaContract.APPRECIATE);
                        },
                        () -> compete(AreaContract.APPRECIATE,viewNote));
    }


    private void deleteLike(ViewNote viewNote){
        Observable.create(emitter -> {
            likeRepository.deleteLike(viewNote.getLike());
            viewNote.setLike(null);
            emitter.onComplete();
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(o -> {},
                        throwable -> error(AreaContract.NAPPRECIATE),
                        () -> compete(AreaContract.NAPPRECIATE,viewNote));
    }
}
