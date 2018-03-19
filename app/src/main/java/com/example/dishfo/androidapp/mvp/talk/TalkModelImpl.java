package com.example.dishfo.androidapp.mvp.talk;

import com.example.dishfo.androidapp.bean.TalkInfo;

/**
 * Created by dishfo on 18-3-18.
 */

public class TalkModelImpl implements TalkContract.TalkModel{

    private TalkContract.TalkPresenter presenter;
    @Override
    public void setPresent(TalkContract.TalkPresenter present) {
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
        error(args[0]);
    }

    @Override
    public void saveMessage(TalkInfo msg) {

    }

    @Override
    public void loadMessage(String email, String ouser) {

    }
}
