package com.example.dishfo.androidapp.bean.sqlBean;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 *
 *
 * Created by dishfo on 18-4-2.
 */
@Entity
public class Talk {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String owner;
    @Embedded(prefix = "send")
    @NonNull
    private User send;
    @Embedded(prefix = "other")
    @NonNull
    private User other;
    private Date time;
    private String message;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public User getSend() {
        return send;
    }

    public void setSend(User send) {
        this.send = send;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @NonNull
    public User getOther() {
        return other;
    }

    public void setOther(@NonNull User other) {
        this.other = other;
    }
}
