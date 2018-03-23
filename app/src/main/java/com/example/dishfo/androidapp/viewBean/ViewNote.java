package com.example.dishfo.androidapp.viewBean;

import android.arch.persistence.room.ColumnInfo;

import com.example.dishfo.androidapp.sqlBean.Area;
import com.example.dishfo.androidapp.sqlBean.Like;
import com.example.dishfo.androidapp.sqlBean.Note;
import com.example.dishfo.androidapp.sqlBean.User;

/**
 * Created by dishfo on 18-3-20.
 */

public class ViewNote {
    private Note note;
    private Area area;
    private Like like;
    private User user;

    public ViewNote(){

    }

    public ViewNote(Note note, Area area, Like like, User user) {
        this.note = note;
        this.area = area;
        this.like = like;
        this.user = user;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Like getLike() {
        return like;
    }

    public void setLike(Like like) {
        this.like = like;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
