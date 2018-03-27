package com.example.dishfo.androidapp.sqlBean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

public class Collection implements Serializable{
    @PrimaryKey
    private String id;
    private String email;
    @ColumnInfo(name = "note_id")
    private String noteId;
    @ColumnInfo(name = "area_id")
    private String areaId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
