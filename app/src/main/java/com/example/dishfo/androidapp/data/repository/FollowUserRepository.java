package com.example.dishfo.androidapp.data.repository;

import com.example.dishfo.androidapp.data.DataAcess.FollowUserAcess;
import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.data.message.DataBaseDao;
import com.example.dishfo.androidapp.sqlBean.FollowUser;
import com.example.dishfo.androidapp.sqlBean.User;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 *
 * Created by dishfo on 18-3-24.
 */

public class FollowUserRepository {

    @Inject
    DataBaseDao dataBaseDao;

    @Inject
    FollowUserAcess followUserAcess;

    public FollowUserRepository(){
        MyApplication.getComponent().inject(this);
    }

    public FollowUser getFollowUserBy(User user,User followed) throws IOException {
        FollowUser followUser=dataBaseDao.getFollowUser(user.getEmail(),followed.getEmail());
        if(followed==null){
           followUser=followUserAcess.getFollowUser(user.getEmail(),followed.getEmail());
        }
        return followUser;
    }

    public FollowUser saveFollowUser(FollowUser followUser) throws IOException {
        FollowUser followUser1=followUserAcess.insertFollowUser(followUser);
        dataBaseDao.insertFollowUser(followUser1);
        return followUser1;
    }

    public boolean deleteFollowUser(FollowUser followUser) throws IOException {
        dataBaseDao.deleteFollowUser(followUser);
        return followUserAcess.deleteFollowUser(followUser);
    }


    public List<FollowUser> getFollowUsersByUser(String email) throws IOException {
        List<FollowUser> list=dataBaseDao.getFollowUserByUser(email);
        if(list==null){
            list=followUserAcess.getFollowUsersByUser(email);
        }

        return list;
    }


}
