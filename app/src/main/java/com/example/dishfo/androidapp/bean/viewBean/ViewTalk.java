package com.example.dishfo.androidapp.bean.viewBean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 *
 * Created by dishfo on 18-3-15.
 */

public class ViewTalk implements Serializable,MultiItemEntity{

    public static final int LEFT=1;
    public static final int RIGHT=0;

   // public int type;
    private String messageContent;
    private boolean other;
    private String otherUser;
    private String time;

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public void setOther(boolean other) {
        this.other = other;
    }

    public String getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(String otherUser) {
        this.otherUser = otherUser;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int getItemType() {
        if (other){
            return LEFT;
        }else {
            return RIGHT;
        }
    }
}
