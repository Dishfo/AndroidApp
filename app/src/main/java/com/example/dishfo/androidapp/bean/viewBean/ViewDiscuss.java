package com.example.dishfo.androidapp.bean.viewBean;

import com.example.dishfo.androidapp.bean.sqlBean.Area;
import com.example.dishfo.androidapp.bean.sqlBean.Discuss;
import com.example.dishfo.androidapp.bean.sqlBean.Note;
import com.example.dishfo.androidapp.bean.sqlBean.User;

import java.io.Serializable;

/**
 * Created by dishfo on 18-3-24.
 */

public class ViewDiscuss implements Serializable{
    private Discuss discuss;
    private User user;
    private Area area;
    private Note note;
    public ViewDiscuss(){

    }

    public ViewDiscuss(Discuss discuss, User user) {
        this.discuss = discuss;
        this.user = user;
    }

    public Discuss getDiscuss() {
        return discuss;
    }

    public void setDiscuss(Discuss discuss) {
        this.discuss = discuss;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

}
