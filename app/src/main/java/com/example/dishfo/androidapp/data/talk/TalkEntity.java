package com.example.dishfo.androidapp.data.talk;

/**
 * Created by dishfo on 18-3-17.
 */

public class TalkEntity {
    private String id;
    private String email;
    private String message;
    private boolean other;
    private String time;
    private String otherUser;


    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    public boolean isOther() {
        return other;
    }

    public String getTime() {
        return time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setOther(boolean other) {
        this.other = other;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(String otherUser) {
        this.otherUser = otherUser;
    }
}
