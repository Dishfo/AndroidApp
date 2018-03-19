package com.example.dishfo.androidapp.data.user;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.dishfo.androidapp.sqlBean.User;

/**
 * Created by dishfo on 18-3-19.
 */
@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void saveUser(User user);

    @Query("select * from User where email = :email limit 1")
    public User getUser(String email);

}
