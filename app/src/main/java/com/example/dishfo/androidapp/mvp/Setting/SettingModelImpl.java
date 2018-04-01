package com.example.dishfo.androidapp.mvp.Setting;

import com.example.dishfo.androidapp.data.DataAcess.FileAcess;
import com.example.dishfo.androidapp.data.DataAcess.NetMethod;
import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.data.repository.UserRepository;
import com.example.dishfo.androidapp.sqlBean.User;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dishfo on 18-3-8.
 */

public class SettingModelImpl implements SettingContract.SettingModel{

    private  SettingContract.SettingPresent present;

    @Inject
    UserRepository userRepository;

    public SettingModelImpl(){MyApplication.getRepositoryComponent().inject(this);}


    @Override
    public void setPresent(SettingContract.SettingPresent present) {
        this.present=present;
    }

    @Override
    public void setArgs(Object... args) {

    }

    @Override
    public void stop() {

    }

    @Override
    public void compete(Object... args) {
        present.onCompete(args[0],args[1]);
    }

    @Override
    public void error(Object... args) {
        present.onError(args[0]);
    }

    @Override
    public void toChangeHead(User user, String file) {

        final List<String> fileUrls=new ArrayList<>();
        Observable<JsonObject> observable= FileAcess.INSTANCE.uploadFile(new String[]{file});
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(jsonObject -> {
                            if(jsonObject.get("code").getAsInt()==1){
                                fileUrls.add(NetMethod.INSTANCE.getUrl(jsonObject));
                            }else {
                                observable.unsubscribeOn(Schedulers.io());
                                error(SettingContract.HEAD);
                            }
                        },
                        throwable -> {
                            observable.unsubscribeOn(Schedulers.io());
                            error(SettingContract.HEAD);
                        },
                        () -> {
                            if(fileUrls.size()>0){
                                user.setHeadUrl(fileUrls.get(0));
                                updateUser(user,SettingContract.HEAD);
                            }
                        });
    }


    @Override
    public void toChangeName(User user, String name) {
        user.setName(name);
        updateUser(user,SettingContract.NAME);
    }

    private void updateUser(User user,int code){
        Observable.create(emitter -> {
            boolean result=userRepository.updateUser(user);
            emitter.onNext(result);
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(o -> {
                   Boolean res= (Boolean) o;
                   if(res){
                       compete(code,user);
                   }else {

                   }
                });
    }
}
