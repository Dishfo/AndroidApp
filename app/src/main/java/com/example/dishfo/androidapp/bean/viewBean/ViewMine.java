package com.example.dishfo.androidapp.bean.viewBean;

import com.example.dishfo.androidapp.bean.sqlBean.Collection;
import com.example.dishfo.androidapp.bean.sqlBean.Discuss;
import com.example.dishfo.androidapp.bean.sqlBean.Fans;
import com.example.dishfo.androidapp.bean.sqlBean.FollowArea;
import com.example.dishfo.androidapp.bean.sqlBean.FollowUser;
import com.example.dishfo.androidapp.bean.sqlBean.Like;
import com.example.dishfo.androidapp.bean.sqlBean.Note;
import com.example.dishfo.androidapp.bean.sqlBean.User;

import java.util.List;

/**
 * Created by dishfo on 18-3-27.
 */

public class ViewMine {
    private User user;
    private List<Note> notes;
    private List<FollowUser> followUsers;
    private List<Fans> fans;
    private List<Collection> collections;
    private List<Like> likes;
    private List<FollowArea> followAreas;
    private List<Discuss> discusses;

    public List<Note> getNotes() {
        return notes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<FollowUser> getFollowUsers() {
        return followUsers;
    }

    public void setFollowUsers(List<FollowUser> followUsers) {
        this.followUsers = followUsers;
    }

    public List<Fans> getFans() {
        return fans;
    }

    public void setFans(List<Fans> fans) {
        this.fans = fans;
    }

    public List<Collection> getCollections() {
        return collections;
    }

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Discuss> getDiscusses() {
        return discusses;
    }

    public void setDiscusses(List<Discuss> discusses) {
        this.discusses = discusses;
    }

    public List<FollowArea> getFollowAreas() {
        return followAreas;
    }

    public void setFollowAreas(List<FollowArea> followAreas) {
        this.followAreas = followAreas;
    }
}
