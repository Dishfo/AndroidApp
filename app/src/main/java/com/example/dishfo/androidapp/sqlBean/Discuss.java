package com.example.dishfo.androidapp.sqlBean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.Nullable;

import com.example.dishfo.androidapp.data.Converts;

import java.util.List;

/**
 * Created by dishfo on 18-3-20.
 */
@Entity
@TypeConverters({Converts.class})
public class Discuss {
    @PrimaryKey
    @Nullable
    private String id;
    private String email;
    private String content;
    private List<String> images;

    @ColumnInfo(name = "old_user")
    private String oldUser;
    @ColumnInfo(name = "old_content")
    private String oldContent;
    private String time;
    @ColumnInfo(name = "note_id")
    private String noteId;
    @ColumnInfo(name = "area_id")
    private String areaId;

    @Nullable
    public String getId() {
        return id;
    }

    public void setId(@Nullable String id) {
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

    public String getOldUser() {
        return oldUser;
    }

    public void setOldUser(String oldUser) {
        this.oldUser = oldUser;
    }

    public String getOldContent() {
        return oldContent;
    }

    public void setOldContent(String oldContent) {
        this.oldContent = oldContent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
}
