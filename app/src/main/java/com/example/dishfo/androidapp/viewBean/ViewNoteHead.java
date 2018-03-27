package com.example.dishfo.androidapp.viewBean;

import com.example.dishfo.androidapp.sqlBean.FollowUser;
import com.example.dishfo.androidapp.sqlBean.Note;
import com.example.dishfo.androidapp.sqlBean.User;

import java.io.Serializable;

/**
 * Created by dishfo on 18-3-24.
 */

public class ViewNoteHead implements Serializable{
    private Note note;
    private User user;
    private FollowUser followUser;

    public ViewNoteHead(){}

    public ViewNoteHead(Note note, User user, FollowUser followUser) {
        this.note = note;
        this.user = user;
        this.followUser = followUser;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FollowUser getFollowUser() {
        return followUser;
    }

    public void setFollowUser(FollowUser followUser) {
        this.followUser = followUser;
    }
}
