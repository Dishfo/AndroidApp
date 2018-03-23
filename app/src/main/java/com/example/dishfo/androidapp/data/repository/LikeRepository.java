package com.example.dishfo.androidapp.data.repository;

import com.example.dishfo.androidapp.DataAcess.LikeAcess;
import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.data.message.DataBaseDao;
import com.example.dishfo.androidapp.sqlBean.Like;
import com.example.dishfo.androidapp.sqlBean.Note;
import com.example.dishfo.androidapp.sqlBean.User;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dishfo on 18-3-20.
 */
public class LikeRepository {
    @Inject
    DataBaseDao dataBaseDao;
    @Inject
    LikeAcess likeAcess;

    public LikeRepository(){
        MyApplication.getComponent().inject(this);
    }

    public Like getLike(User user, Note note) throws IOException {
        Like like=dataBaseDao.getLike(user.getEmail(),note.getId(),note.getAreaId());
        if(like==null){
            like=likeAcess.getLike(user.getEmail(),note.getId(),note.getAreaId());
            if(like!=null){

                return like;
            }
        }
        return like;
    }

    public Like getLikeByUser(String email){
        return null;
    }

    public Like saveLike(Like like) throws IOException {
        Like res=likeAcess.saveLike(like);
        dataBaseDao.insertLike(res);
        return res;
    }

    public void deleteLike(Like like) throws IOException {
        dataBaseDao.deleteLike(like);
        likeAcess.deleteLike(like);
    }

    public void refreshData(){

    }
}
