package com.example.dishfo.androidapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/12/10.
 */

public class DiscussInfo implements Serializable{
    public String email=null;
    public String mHeadUrl = null;
    public String mNickName = null;
    public String mReplayedContent = null;
    public String mReplayContent = null;
    public String discussArea=null;
    public List<String> mImageUrls = null;
    public String mTime = null;

    public String getmHeaUrl() {
        return mHeadUrl;
    }

    public void setmHeaUrl(String mHeaUrl) {
        this.mHeadUrl = mHeaUrl;
    }

    public String getmNickName() {
        return mNickName;
    }

    public void setmNickName(String mNickName) {
        this.mNickName = mNickName;
    }

    public String getmReplayedContent() {
        return mReplayedContent;
    }

    public void setmReplayedContent(String mReplayedContent) {
        this.mReplayedContent = mReplayedContent;
    }

    public String getmReplayContent() {
        return mReplayContent;
    }

    public void setmReplayContent(String mReplayContent) {
        this.mReplayContent = mReplayContent;
    }

    public List<String> getmImageUrls() {
        return mImageUrls;
    }

    public void setmImageUrls(List<String> mImageUrls) {
        this.mImageUrls = mImageUrls;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
}
