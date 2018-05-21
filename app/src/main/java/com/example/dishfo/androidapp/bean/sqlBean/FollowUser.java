package com.example.dishfo.androidapp.bean.sqlBean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by dishfo on 18-3-20.
 */
@Entity
public class FollowUser implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    private String email;
    @ColumnInfo(name = "follow_user")
    private String followUser;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFollowUser() {
        return followUser;
    }

    public void setFollowUser(String followUser) {
        this.followUser = followUser;
    }
}
