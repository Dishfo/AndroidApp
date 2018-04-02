package com.example.dishfo.androidapp.data.message;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.dishfo.androidapp.bean.sqlBean.User;

import java.util.List;

/**
 * Created by dishfo on 18-3-19.
 */
@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void saveUser(User user);

    @Query("select * from User where email = :email limit 1")
    public User getUser(String email);

    @Query("select * from User")
    public List<User> getAllUser();

    @Delete
    public void deleteUser(User user);

    @Delete
    public void deleteUser(List<User> users);

}
