package com.example.dishfo.androidapp.sqlBean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

import retrofit2.http.Part;

/**
 * Created by dishfo on 18-3-20.
 */
@Entity
public class FollowArea implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    private String email;
    @ColumnInfo(name = "follow_id")
    private String followAreaId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFollowAreaId() {
        return followAreaId;
    }

    public void setFollowAreaId(String followAreaId) {
        this.followAreaId = followAreaId;
    }
}
