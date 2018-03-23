package com.example.dishfo.androidapp.data.repository;

import com.example.dishfo.androidapp.DataAcess.NoteAcess;
import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.data.message.DataBaseDao;
import com.example.dishfo.androidapp.sqlBean.Note;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dishfo on 18-3-20.
 */
public class NoteRepository {
    @Inject
    DataBaseDao dataBaseDao;

    @Inject
    NoteAcess noteAcess;

    public NoteRepository(){
        MyApplication.getComponent().inject(this);
    }

    public Note getNoteById(String id,String areaName){
        Note note=dataBaseDao.getNoteById(id,areaName);
        if(note==null){
            try {
                note=noteAcess.getNoteById(id,areaName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return note;
    }

    public void saveNote(Note note){
        return;
    }

    public List<Note> getNoteByArea(String areaId){
        return null;
    }
}
