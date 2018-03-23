package com.example.dishfo.androidapp.dagger.module;

import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.data.message.DataBaseDao;
import com.example.dishfo.androidapp.data.message.MessageDao;
import com.example.dishfo.androidapp.data.message.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dishfo on 18-3-19.
 */
@Module
@Singleton
public class MessageDataBaseModule {

    @Provides
    public MessageDao getMessageDao(){
        return MyApplication.getMessageDataBase().MessageDao();
    }

    @Provides
    public DataBaseDao getDataBaseDao(){
        return MyApplication.getMessageDataBase().dataBaseDao();
    }

    @Provides
    public UserDao getUserDao(){
        return MyApplication.getMessageDataBase().userDao();
    }

}
