package com.example.dishfo.androidapp.mvp.message;

import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.bean.sqlBean.Message;
import com.example.dishfo.androidapp.bean.sqlBean.User;
import com.example.dishfo.androidapp.data.repository.MessageRepository;
import com.example.dishfo.androidapp.data.repository.UserRepository;
import com.example.dishfo.androidapp.longconnect.bean.InstanceMessage;
import com.example.dishfo.androidapp.mvp.ModelManager;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dishfo on 18-3-17.
 */

public class MessageModelImpl implements MessageContract.MessageModel {

    private MessageContract.MessagePresenter presenter;
    @Inject
    MessageRepository repository;

    @Inject
    UserRepository userRepository;

    public MessageModelImpl() {
        MyApplication.getRepositoryComponent().inject(this);
    }

    @Override
    public void setPresent(MessageContract.MessagePresenter present) {
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
    public void loadMessage(String email) {
        Observable<List<Message>> observable = Observable.create(emitter -> {
            List<Message> messages = repository.getMessagesByEmail(email);
            emitter.onNext(messages);
        });

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(messages -> {
                    compete(MessageContract.MESSAGEGET, messages);
                });
    }

    public Message saveInstanceMessage(InstanceMessage message) throws IOException {

        Message entity = new Message();
        User send = userRepository.getUserByEmail(message.getFrom());
        User accept = ModelManager.INSTANCE.getLoginModel().getCurrentUser();
        entity.setTime(null);
        if (send == null) {
            send = new User();
            send.setEmail(message.getFrom());
            send.setName(message.getFrom());
        }
        entity.setMessage(message.getMessage());
        entity.setAcceptUser(accept);
        entity.setSendUser(send);
        repository.saveMessage(entity);

        return entity;
    }

    @Override
    public void saveMessage(InstanceMessage message) {
        Observable.create(emitter -> {
            //repository.saveMessage(message);
            emitter.onComplete();
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(o -> {
                }, throwable -> {
                    error(MessageContract.SAVE);
                }, () -> {
                    compete(MessageContract.SAVE, message);
                });

    }
}
