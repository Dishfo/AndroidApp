package com.example.dishfo.androidapp.bean;

/**
 * Created by apple on 2017/12/8.
 */

public class NoteInfo {
    private String mHeadUrl = null;
    private String mNickName = null;
    private String mTime = null;
    private String mContent = null;
    private String mImageUrl = null;
    private String mAppreciateNumber = null;
    private String mReadNumber = null;
    private String mDiscussNumber = null;
    private String mAreaName = null;
    private boolean isAppreciate = false;

    public boolean isAppreciate() {
        return isAppreciate;
    }

    public void setAppreciate(boolean appreciate) {
        isAppreciate = appreciate;
    }

    public String getmAreaName() {
        return mAreaName;
    }

    public void setmAreaName(String mAreaName) {
        this.mAreaName = mAreaName;
    }

    public String getmHeadUrl() {
        return mHeadUrl;
    }

    public void setmHeadUrl(String mHeadUrl) {
        this.mHeadUrl = mHeadUrl;
    }

    public String getmNickName() {
        return mNickName;
    }

    public void setmNickName(String mNickName) {
        this.mNickName = mNickName;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmAppreciateNumber() {
        return mAppreciateNumber;
    }

    public void setmAppreciateNumber(String mAppreciateNumber) {
        this.mAppreciateNumber = mAppreciateNumber;
    }

    public String getmReadNumber() {
        return mReadNumber;
    }

    public void setmReadNumber(String mReadNumber) {
        this.mReadNumber = mReadNumber;
    }

    public String getmDiscussNumber() {
        return mDiscussNumber;
    }

    public void setmDiscussNumber(String mDiscussNumber) {
        this.mDiscussNumber = mDiscussNumber;
    }
}
