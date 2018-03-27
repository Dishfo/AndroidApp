package com.example.dishfo.androidapp.mvp.Discuss;

import android.util.Log;

import com.example.dishfo.androidapp.DataAcess.FileAcess;
import com.example.dishfo.androidapp.DataAcess.NetMethod;
import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.data.repository.DiscussRepository;
import com.example.dishfo.androidapp.mvp.NewNote.NewNoteTaskContract;
import com.example.dishfo.androidapp.sqlBean.Discuss;
import com.example.dishfo.androidapp.viewBean.ViewDiscuss;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Created by dishfo on 18-3-1.
 */

public class DiscussModelImpl implements DiscussTaskContract.DiscusssModel{

    private DiscussTaskContract.DiscussPresenter presenter=null;
    @Inject
    DiscussRepository repository;

    public DiscussModelImpl( ) {
        MyApplication.getRepositoryComponent().inject(this);
    }

    @Override
    public void setPresent(DiscussTaskContract.DiscussPresenter present) {
        this.presenter=present;
    }

    @Override
    public void setArgs(Object... args) {}

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
    public void DisussNote(ViewDiscuss info, String[] files) {
        uploadFile(info,files);
    }

    private void uploadFile(ViewDiscuss info, String[] files){
        List<String> fileUrls=new ArrayList<>();
        Observable<JsonObject> observable=FileAcess.INSTANCE.uploadFile(files);
        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(jsonObject -> {
                            Log.d("upload",jsonObject.toString());
            if(jsonObject.get("code").getAsInt()==1){

                fileUrls.add(NetMethod.INSTANCE.getUrl(jsonObject));
                info.getDiscuss().setImages(fileUrls);dicussNote(info);
            }else {
                observable.unsubscribeOn(Schedulers.io());
                error(NewNoteTaskContract.UPFILE);
            }
        }
        ,throwable -> {
                            try {
                                throw throwable;
                            } catch (Throwable throwable1) {
                                throwable1.printStackTrace();
                            }
                        });
    }

    private void dicussNote(ViewDiscuss viewDiscuss){
        Observable.create(emitter -> {
            Discuss res=repository.insertDiscuss(viewDiscuss.getDiscuss(),viewDiscuss.getArea());
            emitter.onNext(res);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                   if(o==null){
                       error(NewNoteTaskContract.NOTE);
                   }else {
                       compete(NewNoteTaskContract.NOTE);
                   }
                }/*,throwable -> {
                    error(NewNoteTaskContract.NOTE);
                },() -> {

                }*/);
    }
}
