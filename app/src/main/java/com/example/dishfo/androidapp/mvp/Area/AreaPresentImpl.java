package com.example.dishfo.androidapp.mvp.Area;

import android.content.Context;
import android.widget.Toast;

import com.example.dishfo.androidapp.bean.NoteInfo;

/**
 * Created by dishfo on 18-2-14.
 */

public class AreaPresentImpl implements AreaContract.AreaPresent{

    private Context context;
    private AreaContract.AreaModel mAreaModel;
    private AreaContract.AreaView mAreaView;

    public AreaPresentImpl(AreaContract.AreaModel mAreaModel,
                           AreaContract.AreaView mAreaView,Context context) {
        this.mAreaModel = mAreaModel;
        this.mAreaView = mAreaView;

        mAreaModel.setPresent(this);
        mAreaView.setPresent(this);
        this.context=context;
    }

    @Override
    public void start(Object... args) {
        mAreaModel.setArgs(args[0]);
        mAreaModel.loadNote();
    }

    @Override
    public void stop() {

    }

    @Override
    public void onCompete(Object... args) {
        int code= (int) args[0];
        mAreaView.compete(args[0],args[1]);
    }

    @Override
    public void onError(Object... args) {
        mAreaView.error(args[0]);
    }

    @Override
    public void onAppreciateNote(NoteInfo noteInfo) {
        mAreaModel.onAppreciateNote(noteInfo);
    }

}
