package com.example.dishfo.androidapp.data.repository;

import com.example.dishfo.androidapp.data.DataAcess.CollectionAcess;
import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.data.message.DataBaseDao;
import com.example.dishfo.androidapp.sqlBean.Collection;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by dishfo on 18-3-27.
 */

public class CollectionRepository {

    @Inject
    DataBaseDao dataBaseDao;

    @Inject
    CollectionAcess collectionAcess;

    public CollectionRepository(){
        MyApplication.getComponent().inject(this);
    }


    public List<Collection> getCollectionsByUser(String email)
    {
        return null;
    }
}
