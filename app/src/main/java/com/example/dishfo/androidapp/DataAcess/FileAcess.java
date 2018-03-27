package com.example.dishfo.androidapp.DataAcess;

import android.util.Log;

import com.example.dishfo.androidapp.application.TMPclass;
import com.example.dishfo.androidapp.util.Action;
import com.example.dishfo.androidapp.util.CompeteAction;
import com.example.dishfo.androidapp.util.ErrorAction;
import com.google.gson.JsonObject;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by dishfo on 18-3-6.
 */

public class FileAcess {
    public static FileAcess INSTANCE=new FileAcess();

    private FileAcess(){

    }

    public Observable<JsonObject> uploadFile(String[] files){
        Observable<String> observable=Observable.fromArray(files);
        return  observable.flatMap(s -> {
            File file=new File(s);
            String user= TMPclass.tmp.getCurrentUser().getEmail();
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body=MultipartBody.Part.createFormData("file",file.getName(),requestFile);
            return NetMethod.INSTANCE.getRetrofitWithRx().create(DataAccessService.class)
                    .uploadFilerx(user,body);
        });

    }

}
