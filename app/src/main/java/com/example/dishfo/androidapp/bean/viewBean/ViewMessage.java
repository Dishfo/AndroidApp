package com.example.dishfo.androidapp.bean.viewBean;

import com.example.dishfo.androidapp.bean.sqlBean.User;

import java.io.Serializable;

/**
 * Created by dishfo on 17-12-9.
 */

public class ViewMessage implements Serializable {
    private String email;
    private String content;
    private String time;
    private User send;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSend() {
        return send;
    }

    public void setSend(User send) {
        this.send = send;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}