package com.example.dishfo.androidapp.bean;

import java.util.List;

/**
 * Created by apple on 2017/12/10.
 */

public class DiscussInfo {
    private String mHeaUrl = null;
    private String mNickName = null;
    private String mLevel = null;
    private String mReplayedContent = null;
    private String mReplayContent = null;
    private List<String> mImageUrls = null;
    private String mTime = null;

    public String getmHeaUrl() {
        return mHeaUrl;
    }

    public void setmHeaUrl(String mHeaUrl) {
        this.mHeaUrl = mHeaUrl;
    }

    public String getmNickName() {
        return mNickName;
    }

    public void setmNickName(String mNickName) {
        this.mNickName = mNickName;
    }

    public String getmLevel() {
        return mLevel;
    }

    public void setmLevel(String mLevel) {
        this.mLevel = mLevel;
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
