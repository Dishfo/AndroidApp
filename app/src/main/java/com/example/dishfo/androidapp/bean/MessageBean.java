package com.example.dishfo.androidapp.bean;

import java.io.Serializable;

/**
 * Created by dishfo on 18-3-16.
 */

public class MessageBean implements Serializable{

    public static final  int GETUSERS=0x2;
    public static final int USERSINFO=0x1;
    public static final int USERMESSAGE=0x3;

    private String from;
    private String to;
    private String text;
    private int type;
    private String time;



    public MessageBean(String from, String to, String text, int type) {
        this.from = from;
        this.to = to;
        this.text = text;
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getText() {
        return text;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
