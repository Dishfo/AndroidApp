package com.example.dishfo.androidapp.data.DataAcess;

import com.example.dishfo.androidapp.mvp.ModelManager;
import com.google.gson.JsonObject;

import java.io.File;

import io.reactivex.Observable;
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
            String user= ModelManager.INSTANCE.getLoginModel().getCurrentUser().getEmail();
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body=MultipartBody.Part.createFormData("file",file.getName(),requestFile);
            return NetMethod.INSTANCE.getRetrofitWithRx().create(DataAccessService.class)
                    .uploadFilerx(user,body);
        });

    }

}
