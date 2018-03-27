package com.example.dishfo.androidapp.sqlBean;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.dishfo.androidapp.data.Converts;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dishfo on 18-3-19.
 */
@Entity(tableName = "message",
primaryKeys = {"sendemail","acceptemail"})
@TypeConverters({Converts.class})
public class Message implements Serializable {

    @Embedded(prefix = "send")
    @NonNull
    private User sendUser;

    @Embedded(prefix = "accept")
    @NonNull
    private User acceptUser;

    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public User getSendUser() {
        return sendUser;
    }

    public void setSendUser(User sendUser) {
        this.sendUser = sendUser;
    }

    public User getAcceptUser() {
        return acceptUser;
    }

    public void setAcceptUser(User acceptUser) {
        this.acceptUser = acceptUser;
    }
}
