package com.example.dishfo.androidapp.data.message;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.dishfo.androidapp.data.Converts;
import com.example.dishfo.androidapp.sqlBean.Area;
import com.example.dishfo.androidapp.sqlBean.Collection;
import com.example.dishfo.androidapp.sqlBean.Discuss;
import com.example.dishfo.androidapp.sqlBean.FollowArea;
import com.example.dishfo.androidapp.sqlBean.FollowUser;
import com.example.dishfo.androidapp.sqlBean.Like;
import com.example.dishfo.androidapp.sqlBean.Message;
import com.example.dishfo.androidapp.sqlBean.Note;
import com.example.dishfo.androidapp.sqlBean.User;

/**
 * Created by dishfo on 18-3-19.
 */
@Database(entities = {Message.class, User.class, Area.class, Like.class, Note.class, FollowArea.class,
        FollowUser.class, Discuss.class, Collection.class},version = 1)
@TypeConverters({Converts.class})
public abstract class MessageDatebase extends RoomDatabase {
    public abstract MessageDao MessageDao();
    public abstract UserDao userDao();
    public abstract DataBaseDao dataBaseDao();
}
