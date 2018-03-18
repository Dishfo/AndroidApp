package com.example.dishfo.androidapp.mvp.message;

import com.example.dishfo.androidapp.DataAcess.NetMethod;
import com.example.dishfo.androidapp.DataAcess.UserAcess;
import com.example.dishfo.androidapp.bean.MessageBean;
import com.example.dishfo.androidapp.bean.MessageInfo;
import com.example.dishfo.androidapp.bean.UserInfo;
import com.example.dishfo.androidapp.data.message.MessageDataBase;
import com.example.dishfo.androidapp.data.message.MessageEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dishfo on 18-3-17.
 */

public class MessageModelImpl implements MessageContract.MessageModel {

    private MessageContract.MessagePresenter presenter;
    @Override
    public void setPresent(MessageContract.MessagePresenter present) {
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
        presenter.onCompete(args[0],args[1]);
    }

    @Override
    public void error(Object... args) {
        presenter.onError(args[0]);
    }

    @Override
    public void loadMessage(String email) {
        final ArrayList<MessageInfo> lists=new ArrayList<>();
        Observable.create(emitter -> {
            MessageDataBase dataBase=MessageDataBase.getInstance();
            List<MessageEntity> entities=dataBase.getData(email);
            for(MessageEntity entity:entities){
                MessageInfo info=new MessageInfo();
                info.time=entity.getTime();
                info.content=entity.getMessage();
                info.email=entity.getEmail();
                info.userName=entity.getName();

                Observable<UserInfo> infos=UserAcess.INSTANCE.getUserById(info.email);
                infos.subscribe(userInfo -> {
                    info.headUrl=userInfo.head;
                });

                emitter.onNext(info);
            }
        }).observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    lists.add((MessageInfo) o);
                },throwable -> {
                    error(MessageContract.MESSAGELOAD);
                },() -> {
                    compete(MessageContract.MESSAGELOAD,lists);
                });
    }

    @Override
    public void saveMessage(MessageBean messageBean) {
        Observable<MessageEntity> observable=Observable.create(emitter -> {
            MessageEntity entity=new MessageEntity();
            entity.setEmail(NetMethod.INSTANCE.getUser());
            entity.setMessage(messageBean.getText());
            entity.setTime(messageBean.getTime());
            entity.setSendUser(messageBean.getFrom());
            Observable<UserInfo> infos=UserAcess.INSTANCE.getUserById(entity.getSendUser());
            infos.subscribe(userInfo -> {
                entity.setName(userInfo.name);
            });
        });

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(messageEntity -> {
                            compete(MessageContract.MESSAGEGET,messageEntity);
                        },
                        throwable -> {
                            error(MessageContract.MESSAGEGET);
                        });
    }
}
