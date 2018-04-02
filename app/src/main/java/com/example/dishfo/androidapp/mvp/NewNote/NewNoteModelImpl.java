package com.example.dishfo.androidapp.mvp.NewNote;

import com.example.dishfo.androidapp.data.DataAcess.FileAcess;
import com.example.dishfo.androidapp.data.DataAcess.NetMethod;
import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.data.repository.NoteRepository;
import com.example.dishfo.androidapp.mvp.Discuss.DiscussTaskContract;
import com.example.dishfo.androidapp.bean.sqlBean.Area;
import com.example.dishfo.androidapp.bean.sqlBean.Note;
import com.example.dishfo.androidapp.bean.viewBean.ViewNote;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Created by dishfo on 18-3-5.
 */

public class NewNoteModelImpl implements NewNoteTaskContract.NewNoteModel{

    private NewNoteTaskContract.NewNotePresenter presenter;
    @Inject
    NoteRepository repository;

    public NewNoteModelImpl(){
        MyApplication.getRepositoryComponent().inject(this);
    }

    @Override
    public void setPresent(NewNoteTaskContract.NewNotePresenter present) {
        this.presenter=present;
    }

    @Override
    public void setArgs(Object... args) {}

    @Override
    public void stop() {}

    @Override
    public void compete(Object... args) {
        presenter.onCompete(args[0]);
    }

    @Override
    public void error(Object... args) {
        presenter.onError(args[0]);
    }

    @Override
    public void PushNote(ViewNote viewNote, String[] files) {
        final List<String> fileUrls=new ArrayList<>();
        Observable<JsonObject> observable= FileAcess.INSTANCE.uploadFile(files);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(jsonObject -> {
                            if(jsonObject.get("code").getAsInt()==1){
                                fileUrls.add(NetMethod.INSTANCE.getUrl(jsonObject));
                            }else {
                                observable.unsubscribeOn(Schedulers.io());
                                error(DiscussTaskContract.UPFILE);
                            }
                        },
                        throwable -> {
                            observable.unsubscribeOn(Schedulers.io());
                            error(DiscussTaskContract.UPFILE);
                        },
                        () -> {
                            Note note=viewNote.getNote();
                            note.setImages(fileUrls);
                            addNewNote(viewNote.getArea(),note);
                        });
    }

    private void addNewNote(Area area, Note note){
        Observable.create(emitter -> {
            Note res=repository.saveNote(note,area);
            emitter.onNext(res);
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(o -> {
                            if(o==null){
                                error(DiscussTaskContract.DISCUSS);
                            }else {
                                compete(DiscussTaskContract.DISCUSS);
                            }
                        },
                        throwable -> {
                            error(DiscussTaskContract.DISCUSS);
                        });
    }
}
