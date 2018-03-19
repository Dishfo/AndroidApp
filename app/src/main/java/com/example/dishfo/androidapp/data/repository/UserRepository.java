package com.example.dishfo.androidapp.data.repository;

import com.example.dishfo.androidapp.sqlBean.User;

import java.util.List;

import javax.inject.Singleton;

/**
 * Created by dishfo on 18-3-19.
 */
@Singleton
public class UserRepository {

    private User currentUser;


    public User getCurrentUser(){
        return currentUser;
    }

    public void setCurrentUser(User user){
        if(user.getEmail()!=null&&user.getName()!=null){
            this.currentUser=user;
        }else {
            this.currentUser=null;
        }
    }

    public User getUserByName(String name){
        return null;
    }

    public User getUserByEmail(String email){
        return null;
    }

}
