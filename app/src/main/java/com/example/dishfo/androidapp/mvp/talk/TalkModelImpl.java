package com.example.dishfo.androidapp.mvp.talk;

import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.bean.sqlBean.Message;
import com.example.dishfo.androidapp.bean.sqlBean.Talk;
import com.example.dishfo.androidapp.bean.sqlBean.User;
import com.example.dishfo.androidapp.bean.viewBean.ViewTalk;
import com.example.dishfo.androidapp.data.repository.TalkRepository;
import com.example.dishfo.androidapp.data.repository.UserRepository;
import com.example.dishfo.androidapp.longconnect.LongConService;
import com.example.dishfo.androidapp.longconnect.bean.InstanceMessage;
import com.example.dishfo.androidapp.mvp.ModelManager;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * Created by dishfo on 18-3-18.
 */

public class TalkModelImpl implements TalkContract.TalkModel{

    private TalkContract.TalkPresenter presenter;

    @Inject
    TalkRepository repository;
    @Inject
    UserRepository userRepository;


    public TalkModelImpl(){
        MyApplication.getRepositoryComponent().inject(this);
    }

    @Override
    public void setPresent(TalkContract.TalkPresenter present) {
        this.presenter=present;
    }

    @Override
    public void setArgs(Object... args) {}

    @Override
    public void stop() {}

    @Override
    public void compete(Object... args) {
        if(presenter!=null)
            presenter.onCompete(args[0],args[1]);
    }

    @Override
    public void error(Object... args) {
        error(args[0]);
    }


    @Override
    public void saveMessage(Message msg) {
        Observable.create(emitter -> {
            Talk talk=new Talk();
            talk.setOwner(msg.getAcceptUser().getEmail());
            talk.setSend(msg.getSendUser());
            talk.setMessage(msg.getMessage());
            talk.setOther(msg.getSendUser());
            talk.setTime(null);
            repository.saveTalk(talk);
            emitter.onNext(talk);
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(o -> {

                });
    }

    @Override
    public void loadMessage(String email, String ouser) {
        Observable.create(emitter -> {
           List<Talk> talks=repository.getTalks(email,ouser);
           emitter.onNext(talks);
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(o -> {
                    compete(TalkContract.LOAD,o);
                });
    }

    @Override
    public void sendMessage(ViewTalk viewTalk) {
        Observable<Talk> observable= Observable.create(emitter -> {
            User me= ModelManager.INSTANCE.getLoginModel().getCurrentUser();
            User other=userRepository.getUserByEmail(viewTalk.getOtherUser());

            /**
             * 用于调试
             */

            if(other==null){
                other=new User();
                other.setName("server");
                other.setEmail("server");
                other.setHeadUrl("");
            }

            Talk talk=new Talk();
            talk.setMessage(viewTalk.getMessageContent());
            talk.setOwner(me.getEmail());
            talk.setSend(me);
            talk.setOther(other);

            repository.saveTalk(talk);
            emitter.onNext(talk);
        });

        observable.observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(viewTalk1 -> {
                    InstanceMessage message=new InstanceMessage();
                    message.setMessage(viewTalk.getMessageContent());
                    message.setFrom(ModelManager.INSTANCE.getLoginModel().getCurrentUser().getEmail());
                    message.setTo(viewTalk.getOtherUser());
                    LongConService.send(new Gson().toJson(message));
                    compete(TalkContract.SEND,viewTalk1);
                });

    }


}
