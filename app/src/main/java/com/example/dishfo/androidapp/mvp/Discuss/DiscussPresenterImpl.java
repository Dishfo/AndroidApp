package com.example.dishfo.androidapp.mvp.Discuss;

import android.support.annotation.NonNull;

import com.example.dishfo.androidapp.bean.viewBean.ViewDiscuss;

/**
 * Created by dishfo on 18-3-3.
 */

public class DiscussPresenterImpl implements DiscussTaskContract.DiscussPresenter{

    private DiscussTaskContract.DiscusssModel model;
    private DiscussTaskContract.DiscussView view;
    private boolean viewAlive;

    public DiscussPresenterImpl(@NonNull DiscussTaskContract.DiscusssModel model,
                                @NonNull DiscussTaskContract.DiscussView view) {
        this.model = model;
        this.view = view;
        viewAlive=true;
        model.setPresent(this);
        view.setPresent(this);
    }

    @Override
    public void start(Object... args) {}

    @Override
    public void stop() {
        viewAlive=false;
    }

    @Override
    public void onCompete(Object... args) {
        if(viewAlive)
            view.compete(args[0]);
    }

    @Override
    public void onError(Object... args) {
        if(viewAlive)
            view.error(args[0]);
    }

    @Override
    public void onDiscussNote(ViewDiscuss discuss, String[] files) {
        model.DisussNote(discuss,files);
    }


}
