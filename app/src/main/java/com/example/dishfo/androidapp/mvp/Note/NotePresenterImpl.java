package com.example.dishfo.androidapp.mvp.Note;

import com.example.dishfo.androidapp.mvp.ModelManager;
import com.example.dishfo.androidapp.bean.sqlBean.User;
import com.example.dishfo.androidapp.bean.viewBean.ViewNote;
import com.example.dishfo.androidapp.bean.viewBean.ViewNoteHead;

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
        model.getDiscuss((ViewNote) args[0]);
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
    public void onFollowUser(ViewNoteHead userInfo) {
        if(!isOne(userInfo.getUser())){
            model.followUser(userInfo);
        }else {
            onError(NoteTaskContract.FOLLOW);
        }
    }

    private boolean isOne(User user){
        if(user.getEmail().equals(ModelManager.INSTANCE.getLoginModel().getCurrentUser().getEmail())){
            return true;
        }
        return false;
    }
}
