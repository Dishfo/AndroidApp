package com.example.dishfo.androidapp.data.repository;

import com.example.dishfo.androidapp.data.DataAcess.DiscussAcess;
import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.data.message.DataBaseDao;
import com.example.dishfo.androidapp.bean.sqlBean.Area;
import com.example.dishfo.androidapp.bean.sqlBean.Discuss;
import com.example.dishfo.androidapp.bean.sqlBean.Note;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 *
 * Created by dishfo on 18-3-24.
 */

public class DiscussRepository {

    @Inject
    DataBaseDao dataBaseDao;

    @Inject
    DiscussAcess discussAcess;

    public DiscussRepository(){
        MyApplication.getComponent().inject(this);
    }

    public List<Discuss> getDiscussByNote(Note note, Area area) throws IOException {
        List<Discuss> discusses=dataBaseDao.getDiscuss(note.getId(),area.getId());
        if(discusses==null||discusses.size()==0){
            discusses = discussAcess.getDiscussByNote(note,area);
            if(discusses==null){
                return null;
            }
            dataBaseDao.insertDiscusses(discusses);
        }
        return discusses;
    }


    public List<Discuss> getDiscussByUser(String email,String areaName) throws IOException {
        List<Discuss> list=dataBaseDao.getDiscussByUser(email,areaName);
        if(list==null){
            list=discussAcess.getDissDiscussesByUser(email,areaName);
        }
        return list;
    }

    public Discuss insertDiscuss(Discuss discuss,Area area) throws IOException {
        Discuss res=discussAcess.insertDiscuss(discuss,area.getName());
        dataBaseDao.insertDiscuss(res);
        return res;
    }

    public void  refreshData(){

    }
}
