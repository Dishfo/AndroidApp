package com.example.dishfo.androidapp.data.message;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.dishfo.androidapp.bean.sqlBean.Talk;
import com.example.dishfo.androidapp.data.Converts;
import com.example.dishfo.androidapp.bean.sqlBean.Area;
import com.example.dishfo.androidapp.bean.sqlBean.Collection;
import com.example.dishfo.androidapp.bean.sqlBean.Discuss;
import com.example.dishfo.androidapp.bean.sqlBean.FollowArea;
import com.example.dishfo.androidapp.bean.sqlBean.FollowUser;
import com.example.dishfo.androidapp.bean.sqlBean.Like;
import com.example.dishfo.androidapp.bean.sqlBean.Message;
import com.example.dishfo.androidapp.bean.sqlBean.Note;
import com.example.dishfo.androidapp.bean.sqlBean.User;

/**
 * Created by dishfo on 18-3-19.
 */
@Database(entities = {Message.class, User.class, Area.class, Like.class, Note.class, FollowArea.class,
        FollowUser.class, Discuss.class, Collection.class, Talk.class},version = 1)
@TypeConverters({Converts.class})
public abstract class MessageDatebase extends RoomDatabase {
    public abstract MessageDao MessageDao();
    public abstract UserDao userDao();
    public abstract DataBaseDao dataBaseDao();
}
