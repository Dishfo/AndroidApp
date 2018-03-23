package com.example.dishfo.androidapp.data.repository;

import com.example.dishfo.androidapp.DataAcess.UserAcess;
import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.data.message.UserDao;
import com.example.dishfo.androidapp.sqlBean.User;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dishfo on 18-3-19.
 */

/**
 *
 * 提供同步的数据获取方式
 */
public class UserRepository {

    @Inject
    UserDao dao;
    @Inject
    UserAcess acess;


    public UserRepository(){
        MyApplication.getComponent().inject(this);
    }

    public void saveUser(User user){
        dao.saveUser(user);
    }

    public User getUserByName(String name){
        return null;
    }

    public User getUserByEmail(String email) throws IOException {
        User user=dao.getUser(email);
        if(user==null){
            List<User> users=acess.getUser(email);
            if(users!=null&&users.size()>0){
                user=users.get(0);
                saveUser(user);
            }
        }
        return user;
    }

    public boolean updateUser(User user)throws IOException{
        boolean result=acess.updateUser(user);
        if(result){
            dao.saveUser(user);
            return true;
        }else {
            return false;
        }
    }


    public void refreshUsers() throws IOException {
        List<User> users=dao.getAllUser();
        dao.deleteUser(users);
    }

}
