package com.example.dishfo.androidapp.mvp.talk;

import com.example.dishfo.androidapp.bean.TalkInfo;

/**
 * Created by dishfo on 18-3-18.
 */

public class TalkPresenterImpl implements TalkContract.TalkPresenter {

    private TalkContract.TalkModel model;
    private TalkContract.TalkView view;

    public TalkPresenterImpl(TalkContract.TalkModel model, TalkContract.TalkView view) {
        this.model = model;
        this.view = view;

        model.setPresent(this);
        view.setPresent(this);
    }

    @Override
    public void start(Object... args) {
        model.loadMessage((String) args[0],(String) args[1]);
    }

    @Override
    public void stop() {

    }

    @Override
    public void onCompete(Object... args) {
        view.compete(args[0],args[1]);
    }

    @Override
    public void onError(Object... args) {
        view.error(args[0]);
    }

    @Override
    public void onSend(TalkInfo message) {
        model.saveMessage(message);
    }

    @Override
    public void onRecevier(TalkInfo message) {
        model.saveMessage(message);
    }
}
