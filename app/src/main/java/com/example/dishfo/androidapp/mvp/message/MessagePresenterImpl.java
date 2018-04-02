package com.example.dishfo.androidapp.mvp.message;

import com.example.dishfo.androidapp.bean.sqlBean.Message;
import com.example.dishfo.androidapp.longconnect.bean.InstanceMessage;

/**
 * Created by dishfo on 18-3-17.
 */

public class MessagePresenterImpl implements MessageContract.MessagePresenter{

    private MessageContract.MessageModel model;
    private MessageContract.MessageView view;

    public MessagePresenterImpl(MessageContract.MessageModel model, MessageContract.MessageView view) {
        this.model = model;
        this.view = view;

        model.setPresent(this);
        view.setPresent(this);
    }

    @Override
    public void start(Object... args) {
        model.loadMessage((String) args[0]);
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
    public void onRecevier(InstanceMessage message) {
        //view.compete(args[0],args[1]);
    }
}
