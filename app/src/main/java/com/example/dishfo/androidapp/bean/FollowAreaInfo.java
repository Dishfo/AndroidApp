package com.example.dishfo.androidapp.bean;

/**
 * Created by apple on 2017/12/9.
 */

public class FollowAreaInfo {
    private String mAreaName = null;
    private String mLevel = "0";
    private boolean mIsEdit = false;

    public boolean ismIsEdit() {
        return mIsEdit;
    }

    public void setmIsEdit(boolean mIsEdit) {
        this.mIsEdit = mIsEdit;
    }

    public FollowAreaInfo(String mAreaName, String mLevel) {
        this.mAreaName = mAreaName;
        this.mLevel = mLevel;
    }

    public String getmLevel() {
        return mLevel;
    }

    public void setmLevel(String mLevel) {
        this.mLevel = mLevel;
    }

    public String getmAreaName() {

        return mAreaName;
    }

    public void setmAreaName(String mAreaName) {
        this.mAreaName = mAreaName;
    }
}
