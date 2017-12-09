package com.example.dishfo.androidapp.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by dishfo on 17-12-9.
 */

public class MineInfo implements MultiItemEntity {

    public static final int FIRST_TYPE=1;
    public static final int SECOND_TYPE=2;
    public static final int THIRD_TYPE=3;
    public static final int FOURTH_TYPE=4;

    //first
    public String headimageUrl="";
    public String name="";
    public String autograph="";

    //secnod
    public int notes=0;
    public int follow=0;
    public int fans=0;

    //third

    //fourth
    public int imageresid;
    public String label="";
    public int number=0;

    private int itemtype;

    public MineInfo(int itemtype){
        this.itemtype=itemtype;
    }

    @Override
    public int getItemType() {
        return itemtype;
    }
}
