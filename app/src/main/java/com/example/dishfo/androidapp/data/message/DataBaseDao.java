package com.example.dishfo.androidapp.data.message;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.dishfo.androidapp.sqlBean.Area;
import com.example.dishfo.androidapp.sqlBean.Discuss;
import com.example.dishfo.androidapp.sqlBean.FollowArea;
import com.example.dishfo.androidapp.sqlBean.FollowUser;
import com.example.dishfo.androidapp.sqlBean.Like;
import com.example.dishfo.androidapp.sqlBean.Note;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by dishfo on 18-3-20.
 */
@Dao
public interface DataBaseDao {

    @Query("select * from Note where id=:id and area_id=:areaId")
    public Note getNoteById(String id,String areaId);

    @Query("select * from Note inner join Area on Area.id = Note.area_id" +
            " where Area.id = :areaId")
    public List<Note> getNoteByAreAId(String areaId);

    @Query("select * from Note inner join Area on Area.id = Note.area_id" +
            " where Area.name = :areaName")
    public List<Note> getNoteByAreaName(String areaName);

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

    @Query("select * from FollowArea where follow_id=:areaId and email=:email")
    public FollowArea getFollowArea(String areaId,String email);

    @Query("select * from FollowUser where email=:email and follow_user=:followed")
    public FollowUser getFollowUser(String email,String followed);

    @Query("select * from Discuss where note_id=:noteId and area_id=:areaId")
    public List<Discuss> getDiscuss(String noteId,String areaId);

    @Insert
    public void insertArea(Area area);

    @Insert
    public void insertFollowArea(FollowArea followArea);

    @Insert
    public void insertNote(Note note);

    @Insert
    public void insertLike(Like like);

    @Insert
    public void insertDiscuss(Discuss discuss);

    @Insert
    public void insertFollowUser(FollowUser followUser);

    @Delete
    public void deleteLike(Like like);

    @Delete
    public void deleteFollowArea(FollowArea followArea);

    @Delete
    public void deleteFollowUser(FollowUser followUser);


}
