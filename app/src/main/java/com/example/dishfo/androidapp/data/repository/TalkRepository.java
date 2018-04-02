package com.example.dishfo.androidapp.data.repository;

import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.bean.sqlBean.Talk;
import com.example.dishfo.androidapp.data.message.DataBaseDao;

import java.util.List;

import javax.inject.Inject;

/**
 *
 * Created by dishfo on 18-4-2.
 */

public class TalkRepository{
    @Inject
    DataBaseDao dataBaseDao;

    public TalkRepository(){
        MyApplication.getComponent().inject(this);
    }

    public List<Talk> getTalks(String email,String other){
        return dataBaseDao.getTalk(email,other);
    }

    public void saveTalk(Talk talk){
        dataBaseDao.insertTalk(talk);
    }

}
