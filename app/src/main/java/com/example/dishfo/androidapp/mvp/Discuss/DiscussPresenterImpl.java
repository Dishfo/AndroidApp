package com.example.dishfo.androidapp.mvp.Discuss;

import com.example.dishfo.androidapp.viewBean.ViewDiscuss;

/**
 * Created by dishfo on 18-3-3.
 */

public class DiscussPresenterImpl implements DiscussTaskContract.DiscussPresenter{

    private DiscussTaskContract.DiscusssModel model;
    private DiscussTaskContract.DiscussView view;

    public DiscussPresenterImpl(DiscussTaskContract.DiscusssModel model,
                                DiscussTaskContract.DiscussView view) {
        this.model = model;
        this.view = view;

        model.setPresent(this);
        view.setPresent(this);
    }

    @Override
    public void start(Object... args) {}

    @Override
    public void stop() {

    }

    @Override
    public void onCompete(Object... args) {
        view.compete(args[0]);
    }

    @Override
    public void onError(Object... args) {
        view.error(args[0]);
    }

    @Override
    public void onDiscussNote(ViewDiscuss discuss, String[] files) {
        model.DisussNote(discuss,files);
    }


}
