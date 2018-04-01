package com.example.dishfo.androidapp.data.message;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.dishfo.androidapp.sqlBean.Area;
import com.example.dishfo.androidapp.sqlBean.Collection;
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
public   interface DataBaseDao {

    @Query("select * from Note where id=:id and area_id=:areaId")
      Note getNoteById(String id,String areaId);

    @Query("select * from Note inner join Area on Area.id = Note.area_id" +
            " where Area.id = :areaId")
      List<Note> getNoteByAreAId(String areaId);

    @Query("select * from Note inner join Area on Area.id = Note.area_id" +
            " where Area.name = :areaName")
      List<Note> getNoteByAreaName(String areaName);

    @Query("select * from Note inner join Area on Area.id = Note.area_id" +
            " where Area.name = :areaName and Note.id = :id")
      Note getNoteByIda(String id,String areaName);

    @Query("select * from Note where email=:email and area_id=:areaId")
      List<Note> getNoteByEmail(String email,String areaId);

    @Query("select * from Area where name=:name")
      List<Area> getAreaByName(String name);

    @Query("select * from Area where id=:id limit 1;")
      Area getAreaById(String id);

    @Query("select * from `Like` where email=:email and note_id=:noteId and area_id=:areaId limit 1")
      Like getLike(String email,String noteId,String areaId);

    @Query("select * from `Like` where email=:email")
      List<Like> getLikeByUser(String email);

    @Query("select * from FollowArea where follow_id=:areaId and email=:email")
      FollowArea getFollowArea(String areaId,String email);

    @Query("select * from FollowArea where email=:email")
      List<FollowArea> getFollowAreasByUser(String email);

    @Query("select * from FollowUser where email=:email and follow_user=:followed")
      FollowUser getFollowUser(String email,String followed);

    @Query("select * from FollowUser where email=:email")
      List<FollowUser> getFollowUserByUser(String email);

    @Query("select * from Discuss where note_id=:noteId and area_id=:areaId")
      List<Discuss> getDiscuss(String noteId,String areaId);

    @Query("select * from Discuss inner join Area on Area.id=Discuss.area_id" +
            " where email=:email and Area.name=:areaName")
      List<Discuss> getDiscussByUser(String email,String areaName);

    @Query("select * from Collection where email=:email")
      List<Collection> getCollectionsByUser(String email);

    @Insert
      void insertArea(Area area);

    @Insert
      void insertFollowArea(FollowArea followArea);

    @Insert
      void insertNote(Note note);

    @Insert
      void insertLike(Like like);

    @Insert
      void insertDiscuss(Discuss discuss);

    @Insert
    void insertDiscusses(List<Discuss> discusses);

    @Insert
      void insertFollowUser(FollowUser followUser);

    @Delete
      void deleteLike(Like like);

    @Delete
      void deleteFollowArea(FollowArea followArea);

    @Delete
      void deleteFollowUser(FollowUser followUser);


}
