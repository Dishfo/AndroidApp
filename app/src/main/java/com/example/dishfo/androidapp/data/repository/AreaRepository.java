package com.example.dishfo.androidapp.data.repository;

import com.example.dishfo.androidapp.data.DataAcess.AreaAcess;
import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.data.message.DataBaseDao;
import com.example.dishfo.androidapp.sqlBean.Area;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 *
 * Created by dishfo on 18-3-20.
 */
public class AreaRepository {

    @Inject
    DataBaseDao dataBaseDao;

    @Inject
    AreaAcess areaAcess;

    public AreaRepository(){
        MyApplication.getComponent().inject(this);
    }

    public Area getAreaById(String id) throws IOException {
        Area area=dataBaseDao.getAreaById(id);
        if(area==null){
            List<Area> areas=areaAcess.getAreaById(id);
            if(areas!=null&&areas.size()>0){
                return areas.get(0);
            }else {
                return null;
            }
        }
        return area;
    }

    public Area getAreaByName(String name) throws IOException {
        List<Area> areas=dataBaseDao.getAreaByName(name);
        if(areas==null||areas.size()==0){
            areas=areaAcess.getAreaByName(name);
            if(areas!=null&&areas.size()>0){
                saveArea(areas.get(0));
                return areas.get(0);
            }else {
                return null;
            }
        }
        return areas.get(0);
    }

    public void saveArea(Area area){

    }

    public void refreshData(){

    }

}
