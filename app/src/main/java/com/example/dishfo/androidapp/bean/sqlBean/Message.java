package com.example.dishfo.androidapp.bean.sqlBean;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dishfo.androidapp.data.Converts;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * Created by dishfo on 18-3-19.
 */
@Entity(tableName = "message",
primaryKeys = {"sendemail","acceptemail"})
@TypeConverters({Converts.class})
public class Message implements Serializable,Cloneable{

    @Embedded(prefix = "send")
    @NonNull
    private User sendUser;

    @Embedded(prefix = "accept")
    @NonNull
    private User acceptUser;

    private String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
