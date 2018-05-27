package com.example.dishfo.androidapp.mvp.Area;

import android.support.annotation.NonNull;

import com.example.dishfo.androidapp.bean.viewBean.ViewNote;

/**
 *
 * Created by dishfo on 18-2-14.
 */

public class AreaPresentImpl implements AreaContract.AreaPresent{


    private AreaContract.AreaModel mAreaModel;
    private AreaContract.AreaView mAreaView;
    private boolean viewAlive;

    public AreaPresentImpl(@NonNull AreaContract.AreaModel mAreaModel,
                           @NonNull  AreaContract.AreaView mAreaView) {
        this.mAreaModel = mAreaModel;
        this.mAreaView = mAreaView;

        viewAlive=true;
        mAreaModel.setPresent(this);
        mAreaView.setPresent(this);
    }

    @Override
    public void start(Object... args) {mAreaModel.loadNote();}

    @Override
    public void stop() {viewAlive=false;}

    @Override
    public void onCompete(Object... args) {
        if(viewAlive)
            mAreaView.compete(args[0],args[1]);
    }

    @Override
    public void onError(Object... args) {
        if(viewAlive)
            mAreaView.error(args[0]);
    }

    @Override
    public void onAppreciateNote(ViewNote viewNote) {
        mAreaModel.onAppreciateNote(viewNote);
    }

}
