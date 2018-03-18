package com.example.dishfo.androidapp.mvp.Note;

import com.example.dishfo.androidapp.bean.DiscussInfo;
import com.example.dishfo.androidapp.bean.NoteInfo;
import com.example.dishfo.androidapp.bean.UserInfo;

import java.util.List;

/**
 * Created by dishfo on 18-2-28.
 */

public class NotePresenterImpl implements NoteTaskContract.NotePresenter{

    private NoteTaskContract.NoteModel model;
    private NoteTaskContract.NoteView view;

    public NotePresenterImpl(NoteTaskContract.NoteModel model, NoteTaskContract.NoteView view) {
        this.model = model;
        this.view = view;

        model.setPresent(this);
        view.setPresent(this);
    }

    @Override
    public void start(Object... args) {
        model.setArgs(args[0]);
        view.waitToCompete();
        model.getDiscuss();
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
    public void loadDicuss(List<DiscussInfo> discussInfos) {
        view.showDiscuss(discussInfos);
    }



    @Override
    public void onshowHead(NoteInfo noteInfo) {
        view.showHead(noteInfo);
    }

    @Override
    public void onFollowUser(UserInfo userInfo) {
        if(!userInfo.isOne){
            model.followUser(userInfo);
        }else {
            onError(NoteTaskContract.FOLLOW);
        }
    }
}
