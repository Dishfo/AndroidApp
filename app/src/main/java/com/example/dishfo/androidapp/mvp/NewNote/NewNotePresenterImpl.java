package com.example.dishfo.androidapp.mvp.NewNote;

import com.example.dishfo.androidapp.bean.viewBean.ViewNote;

/**
 *
 * Created by dishfo on 18-3-5.
 */

public class NewNotePresenterImpl implements NewNoteTaskContract.NewNotePresenter {

    private NewNoteTaskContract.NewNoteView view;
    private NewNoteTaskContract.NewNoteModel model;
    private boolean viewAlive;

    public NewNotePresenterImpl(NewNoteTaskContract.NewNoteView view, NewNoteTaskContract.NewNoteModel model) {
        this.view = view;
        this.model = model;
        viewAlive=true;
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
    public void onPushNote(ViewNote viewNote, String[] files) {
        model.PushNote(viewNote, files);
    }

}
