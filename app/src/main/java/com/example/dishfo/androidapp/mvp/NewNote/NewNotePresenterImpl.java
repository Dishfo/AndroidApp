package com.example.dishfo.androidapp.mvp.NewNote;

import android.hardware.Camera;

import com.example.dishfo.androidapp.bean.AreaInfo;
import com.example.dishfo.androidapp.bean.NoteInfo;

/**
 * Created by dishfo on 18-3-5.
 */

public class NewNotePresenterImpl implements NewNoteTaskContract.NewNotePresenter {

    private NewNoteTaskContract.NewNoteView view;
    private NewNoteTaskContract.NewNoteModel model;

    public NewNotePresenterImpl(NewNoteTaskContract.NewNoteView view, NewNoteTaskContract.NewNoteModel model) {
        this.view = view;
        this.model = model;

        view.setPresent(this);
        model.setPresent(this);
    }

    @Override
    public void start(Object... args) {

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
        view.compete(args[0]);
    }

    @Override
    public void onPushNote(NoteInfo info, AreaInfo areaInfo, String[] files) {
        model.PushNote(info, areaInfo, files);
    }

}
