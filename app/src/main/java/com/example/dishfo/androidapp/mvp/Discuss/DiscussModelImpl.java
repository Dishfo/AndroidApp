package com.example.dishfo.androidapp.mvp.Discuss;

import android.util.Log;

import com.example.dishfo.androidapp.DataAcess.DataAccessService;
import com.example.dishfo.androidapp.DataAcess.DiscussAcess;
import com.example.dishfo.androidapp.DataAcess.FileAcess;
import com.example.dishfo.androidapp.DataAcess.NetMethod;
import com.example.dishfo.androidapp.bean.AreaInfo;
import com.example.dishfo.androidapp.bean.DiscussInfo;
import com.example.dishfo.androidapp.bean.NoteInfo;
import com.example.dishfo.androidapp.mvp.BaseModel;
import com.example.dishfo.androidapp.mvp.NewNote.NewNoteTaskContract;
import com.example.dishfo.androidapp.netInterface.JsonGenerator;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by dishfo on 18-3-1.
 */

public class DiscussModelImpl implements DiscussTaskContract.DiscusssModel{


    private NoteInfo info;
    private DiscussInfo discussInfo;
    private AreaInfo areaInfo;
    private List<String> fileUrl;
    private DiscussTaskContract.DiscussPresenter presenter=null;

    public DiscussModelImpl( ) {
       fileUrl=new ArrayList<>();
    }

    @Override
    public void setPresent(DiscussTaskContract.DiscussPresenter present) {
        this.presenter=present;
    }

    @Override
    public void setArgs(Object... args) {
        info= (NoteInfo) args[0];
        areaInfo= (AreaInfo) args[1];
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
    public void DisussNote(DiscussInfo info, String[] files) {
        this.discussInfo=info;
        uploadFile(files);
    }



    private void uploadFile(String[] files){
        fileUrl.clear();
        Observable<JsonObject> observable= FileAcess.INSTANCE.uploadFile(files);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(jsonObject -> {
                            if(jsonObject.get("code").getAsInt()==1){
                                fileUrl.add(NetMethod.INSTANCE.getUrl(jsonObject));
                            }else {
                                observable.unsubscribeOn(Schedulers.io());
                                error(NewNoteTaskContract.UPFILE);
                            }
                        },
                        throwable -> {
                            error(NewNoteTaskContract.UPFILE);
                        },
                        () -> {
                            discussInfo.mImageUrls=fileUrl;
                            DicussNote();
                        });
    }

    private void DicussNote(){
        Observable<JsonObject> observable= DiscussAcess.INSTANCE.insertDiscuss(NetMethod.INSTANCE.getUser(),
                info,discussInfo,areaInfo);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(jsonObject -> {
                    if(jsonObject.get("code").getAsInt()==1){
                        compete(NewNoteTaskContract.NOTE);
                    }else {
                        error(NewNoteTaskContract.NOTE);
                    }
                },throwable -> {
                    Log.d("discuss",throwable.toString());
                    observable.unsubscribeOn(Schedulers.io());
                    error(NewNoteTaskContract.NOTE);
                });
    }
}
