package com.example.dishfo.androidapp.data.message;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.dishfo.androidapp.sqlBean.Message;

import java.util.List;

/**
 * Created by dishfo on 18-3-19.
 */
@Dao
public interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void saveMessage(Message message);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void saveMessage(List<Message> messages);

    @Query("select * from message where acceptemail = :email order by time")
    public List<Message> getMessagesByEmail(String email);
}
