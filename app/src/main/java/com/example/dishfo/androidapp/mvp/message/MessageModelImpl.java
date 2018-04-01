package com.example.dishfo.androidapp.mvp.message;

import com.example.dishfo.androidapp.data.repository.MessageRepository;
import com.example.dishfo.androidapp.sqlBean.Message;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Created by dishfo on 18-3-17.
 */

public class MessageModelImpl implements MessageContract.MessageModel {

    private MessageContract.MessagePresenter presenter;
    final MessageRepository repository=new MessageRepository();
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
        Observable<List<Message>> observable=Observable.create(emitter -> {
            List<Message> messages=repository.getMessagesByEmail(email);
            emitter.onNext(messages);
        });
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(messages -> {
            compete(MessageContract.MESSAGEGET,messages);
        },throwable -> {
           error(MessageContract.MESSAGEGET);
        });
    }

    @Override
    public void saveMessage(Message message) {
        Observable.create(emitter -> {
            repository.saveMessage(message);
            emitter.onComplete();
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(o -> {},throwable -> {
                    error(MessageContract.SAVE);
                },() -> {
                    compete(MessageContract.SAVE,message);
                });

    }
}
