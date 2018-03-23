package com.example.dishfo.androidapp.data.message;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.dishfo.androidapp.sqlBean.Area;
import com.example.dishfo.androidapp.sqlBean.Like;
import com.example.dishfo.androidapp.sqlBean.Note;

import java.util.List;

/**
 * Created by dishfo on 18-3-20.
 */
@Dao
public interface DataBaseDao {

    @Query("select * from Note where id=:id and area_id=:areaId")
    public Note getNoteById(String id,String areaId);

    @Query("select * from Note inner join Area on Area.id = Note.area_id" +
            " where Area.name = :areaName")
    public Note getNoteByArea(String areaName);

    @Query("select * from Note inner join Area on Area.id = Note.area_id" +
            " where Area.name = :areaName and Note.id = :id")
    public Note getNoteByIda(String id,String areaName);

    @Query("select * from Note where email=:email and area_id=:areaId")
    public List<Note> getNoteByEmail(String email,String areaId);

    @Query("select * from Area where name=:name")
    public List<Area> getAreaByName(String name);

    @Query("select * from Area where id=:id limit 1;")
    public Area getAreaById(String id);

    @Query("select * from `Like` where email=:email and note_id=:noteId and area_id=:areaId limit 1")
    public Like getLike(String email,String noteId,String areaId);

    @Query("select * from `Like` where email=:email")
    public List<Like> getLikeByUser(String email);

    @Insert
    public void insertArea(Area area);

    @Insert
    public void insertNote(Note note);

    @Insert
    public void insertLike(Like like);

    @Delete
    public void deleteLike(Like like);


}
