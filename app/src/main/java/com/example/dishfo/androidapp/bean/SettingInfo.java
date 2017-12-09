package com.example.dishfo.androidapp.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by dishfo on 17-12-9.
 */

public class SettingInfo implements MultiItemEntity {

    public static final int FIRST_TYPE=1;
    public static final int SECOND_TYPE=1;
    public static final int THRID_TYPE=1;

    public String label;
    public String essue;
    public String imageurl;
    private int itemtype;


    public SettingInfo(String label, int itemtype) {
        this.label = label;
        this.itemtype = itemtype;
    }


    public SettingInfo(String label, String essue, int itemtype) {
        this.label = label;
        this.essue = essue;
        this.itemtype = itemtype;
    }

    public SettingInfo(String label,int itemtype,String imageurl) {
        this.label = label;

        this.imageurl = imageurl;
        this.itemtype = itemtype;
    }

    @Override
    public int getItemType() {
        return itemtype;
    }
}
