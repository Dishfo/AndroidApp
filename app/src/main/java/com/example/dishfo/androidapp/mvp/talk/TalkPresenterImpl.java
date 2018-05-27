package com.example.dishfo.androidapp.mvp.talk;

import android.support.annotation.NonNull;

import com.example.dishfo.androidapp.bean.sqlBean.Message;
import com.example.dishfo.androidapp.bean.viewBean.ViewTalk;

/**
 *
 * Created by dishfo on 18-3-18.
 */

public class TalkPresenterImpl implements TalkContract.TalkPresenter {

    private TalkContract.TalkModel model;
    private TalkContract.TalkView view;
    private boolean viewAlive;

    public TalkPresenterImpl(@NonNull  TalkContract.TalkModel model,@NonNull TalkContract.TalkView view) {
        this.model = model;
        this.view = view;

        viewAlive=true;
        model.setPresent(this);
        view.setPresent(this);
    }

    @Override
    public void start(Object... args) {
        model.loadMessage((String) args[0],(String) args[1]);
    }

    @Override
    public void stop() {
        viewAlive=false;
    }

    @Override
    public void onCompete(Object... args) {
        if (viewAlive)
            view.compete(args[0],args[1]);
    }

    @Override
    public void onError(Object... args) {
        if (viewAlive)
            view.error(args[0]);
    }


    @Override
    public void onSend(ViewTalk message) {
        model.sendMessage(message);
    }

    @Override
    public void onRecevier(Message message) {

    }
}
