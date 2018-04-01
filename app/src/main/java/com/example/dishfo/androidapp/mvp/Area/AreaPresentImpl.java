package com.example.dishfo.androidapp.mvp.Area;

import com.example.dishfo.androidapp.viewBean.ViewNote;

/**
 * Created by dishfo on 18-2-14.
 */

public class AreaPresentImpl implements AreaContract.AreaPresent{


    private AreaContract.AreaModel mAreaModel;
    private AreaContract.AreaView mAreaView;

    public AreaPresentImpl(AreaContract.AreaModel mAreaModel,
                           AreaContract.AreaView mAreaView) {
        this.mAreaModel = mAreaModel;
        this.mAreaView = mAreaView;

        mAreaModel.setPresent(this);
        mAreaView.setPresent(this);
    }

    @Override
    public void start(Object... args) {mAreaModel.loadNote();}

    @Override
    public void stop() {}

    @Override
    public void onCompete(Object... args) {mAreaView.compete(args[0],args[1]);}

    @Override
    public void onError(Object... args) {
        mAreaView.error(args[0]);
    }

    @Override
    public void onAppreciateNote(ViewNote viewNote) {
        mAreaModel.onAppreciateNote(viewNote);
    }

}
