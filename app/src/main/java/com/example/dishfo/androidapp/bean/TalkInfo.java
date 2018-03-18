package com.example.dishfo.androidapp.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dishfo on 18-3-15.
 */

public class TalkInfo implements Serializable,MultiItemEntity{

    public static final int LEFT=1;
    public static final int RIGHT=0;

   // public int type;
    public String messageContent;
    public boolean other;

    @Override
    public int getItemType() {
        if (other){
            return LEFT;
        }else {
            return RIGHT;
        }
    }
}
