package com.example.dishfo.androidapp.data.user;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.dishfo.androidapp.sqlBean.User;

/**
 * Created by dishfo on 18-3-19.
 */
@Database(entities = {User.class},version = 1)
public abstract class UserDataBase extends RoomDatabase{
    public abstract UserDao userDao();
}
