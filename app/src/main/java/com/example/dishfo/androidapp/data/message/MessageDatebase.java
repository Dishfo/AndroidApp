package com.example.dishfo.androidapp.data.message;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.dishfo.androidapp.data.Converts;
import com.example.dishfo.androidapp.sqlBean.Message;
import com.example.dishfo.androidapp.sqlBean.User;

/**
 * Created by dishfo on 18-3-19.
 */
@Database(entities = {Message.class, User.class},version = 1)
@TypeConverters({Converts.class})
public abstract class MessageDatebase extends RoomDatabase {
    public abstract MessageDao MessageDao();
}
