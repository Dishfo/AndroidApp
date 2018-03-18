package com.example.dishfo.androidapp.mvp.Discuss;

import com.example.dishfo.androidapp.bean.DiscussInfo;

/**
 * Created by dishfo on 18-3-3.
 */

public class DiscussPresenterImpl implements DiscussTaskContract.DiscussPresenter{

    private DiscussTaskContract.DiscusssModel model;
    private DiscussTaskContract.DiscussView view;

    public DiscussPresenterImpl(DiscussTaskContract.DiscusssModel model, DiscussTaskContract.DiscussView view) {
        this.model = model;
        this.view = view;

        model.setPresent(this);
        view.setPresent(this);
    }

    @Override
    public void start(Object... args) {
        model.setArgs(args[0],args[1]);
    }

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
    public void onDiscussNote(DiscussInfo info, String[] files) {
        model.DisussNote(info,files);
    }


}
