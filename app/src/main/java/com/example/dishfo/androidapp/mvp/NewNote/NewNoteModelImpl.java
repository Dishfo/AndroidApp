package com.example.dishfo.androidapp.mvp.NewNote;

import com.example.dishfo.androidapp.DataAcess.FileAcess;
import com.example.dishfo.androidapp.DataAcess.NetMethod;
import com.example.dishfo.androidapp.DataAcess.NoteAcess;
import com.example.dishfo.androidapp.bean.AreaInfo;
import com.example.dishfo.androidapp.bean.NoteInfo;
import com.example.dishfo.androidapp.mvp.Discuss.DiscussTaskContract;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dishfo on 18-3-5.
 */

public class NewNoteModelImpl implements NewNoteTaskContract.NewNoteModel{

    private NewNoteTaskContract.NewNotePresenter presenter;
    private List<String> fielUrls;

    public NewNoteModelImpl(){
        fielUrls=new ArrayList<>();
    }

    @Override
    public void setPresent(NewNoteTaskContract.NewNotePresenter present) {
        this.presenter=present;
    }

    @Override
    public void setArgs(Object... args) {
    }

    @Override
    public void stop() {

    }

    @Override
    public void compete(Object... args) {
        presenter.onCompete(args[0]);
    }

    @Override
    public void error(Object... args) {
        presenter.onError(args[0]);
    }

    @Override
    public void PushNote(NoteInfo info, AreaInfo areaInfo, String[] files) {
        Observable<JsonObject> observable= FileAcess.INSTANCE.uploadFile(files);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(jsonObject -> {
                            if(jsonObject.get("code").getAsInt()==1){
                                fielUrls.add(NetMethod.INSTANCE.getUrl(jsonObject));
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
                            info.mImageUrl=fielUrls;
                            addNewNote(areaInfo,info);
                        });
    }

    private void addNewNote(AreaInfo areaInfo,NoteInfo noteInfo){
        Observable<JsonObject> observable=NoteAcess.INSTANCE.addNewNote(noteInfo,areaInfo);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(jsonObject -> {
                            if(jsonObject.get("code").getAsInt()==1){
                                compete(DiscussTaskContract.DISCUSS);
                            }else {
                                error(DiscussTaskContract.DISCUSS);
                            }
                        },
                        throwable -> {
                            observable.unsubscribeOn(Schedulers.io());
                            error(DiscussTaskContract.DISCUSS);
                        });
    }



}
