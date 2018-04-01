package com.example.dishfo.androidapp.data.repository;

import com.example.dishfo.androidapp.data.DataAcess.FollowAreaAcess;
import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.data.message.DataBaseDao;
import com.example.dishfo.androidapp.sqlBean.Area;
import com.example.dishfo.androidapp.sqlBean.FollowArea;
import com.example.dishfo.androidapp.sqlBean.User;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 *提供访问followArea 的数据接口
 * Created by dishfo on 18-3-23.
 */

public class FollowAreaRepository {

    @Inject
    DataBaseDao dataBaseDao;

    @Inject
    FollowAreaAcess followAreaAcess;

    public FollowAreaRepository(){
        MyApplication.getComponent().inject(this);
    }

    public FollowArea saveFollowArea(FollowArea area) throws IOException {
        FollowArea followArea=followAreaAcess.inertFollowArea(area);
        dataBaseDao.insertFollowArea(followArea);
        return followArea;
    }

    public void deleteFollowArea(FollowArea area) throws IOException {
        dataBaseDao.deleteFollowArea(area);
        followAreaAcess.deleteFollowaArea(area);
    }

    public FollowArea getFollow(User user,Area area) throws IOException {
        FollowArea followArea=dataBaseDao.getFollowArea(area.getId(),user.getEmail());
        if(followArea==null){
            followArea=followAreaAcess.getFollowArea(area,user);
        }
        return followArea;
    }

    public List<FollowArea> getFollowAreasByUser(String email) throws IOException {
        List<FollowArea> list=dataBaseDao.getFollowAreasByUser(email);
        if(list==null||list.size()==0){
            list=followAreaAcess.getFollowAreasByUser(email);
        }
        return list;
    }

}
