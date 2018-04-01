package com.example.dishfo.androidapp.data.repository;

import com.example.dishfo.androidapp.data.DataAcess.NoteAcess;
import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.data.message.DataBaseDao;
import com.example.dishfo.androidapp.sqlBean.Area;
import com.example.dishfo.androidapp.sqlBean.Note;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 *
 * Created by dishfo on 18-3-20.
 */
public class NoteRepository {
    @Inject
    DataBaseDao dataBaseDao;

    @Inject
    NoteAcess noteAcess;

    public NoteRepository() {
        MyApplication.getComponent().inject(this);
    }

    public Note getNoteById(String id, String areaName) throws IOException {
        Note note = dataBaseDao.getNoteById(id, areaName);
        if (note == null) {
            note = noteAcess.getNoteById(id, areaName);
        }
        return note;
    }

    public Note saveNote(Note note, Area area) throws IOException {
        Note res=noteAcess.insertNote(note,area);
        dataBaseDao.insertNote(res);
        return res;
    }

    public List<Note> getNoteByArea(String areaName) throws IOException {
        List<Note> notes = dataBaseDao.getNoteByAreaName(areaName);
        if (notes == null||notes.size()==0) {
            notes = noteAcess.getNoteByArea(areaName);
        }
        return notes;
    }

    public List<Note> getNotesByUser(String email,String areaName) throws IOException {
        return noteAcess.getNoteByUser(email,areaName);
    }
}
