package com.example.dishfo.androidapp.sqlBean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by dishfo on 18-3-20.
 */
@Entity
public class Note {

    @PrimaryKey
    @NonNull
    private String id;
    private String email;
    private String content = null;
    private List<String> images = null;
    @ColumnInfo(name = "appreciate_number")
    private String appreciateNumber = null;
    @ColumnInfo(name = "read_number")
    private String readNumber = null;
    @ColumnInfo(name = "discuss_number")
    private String discussNumber = null;
    private String time;
    @ColumnInfo(name = "area_id")
    private String areaId;

    public Note() {

    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getAppreciateNumber() {
        return appreciateNumber;
    }

    public void setAppreciateNumber(String appreciateNumber) {
        this.appreciateNumber = appreciateNumber;
    }

    public String getReadNumber() {
        return readNumber;
    }

    public void setReadNumber(String readNumber) {
        this.readNumber = readNumber;
    }

    public String getDiscussNumber() {
        return discussNumber;
    }

    public void setDiscussNumber(String discussNumber) {
        this.discussNumber = discussNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
}
