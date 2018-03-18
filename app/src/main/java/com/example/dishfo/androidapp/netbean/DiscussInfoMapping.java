package com.example.dishfo.androidapp.netbean;

import android.util.ArrayMap;

import com.example.dishfo.androidapp.activity.TestActivity;
import com.example.dishfo.androidapp.mvp.FieldConstant;
import com.example.dishfo.androidapp.mvp.TableConstant;

/**
 * Created by dishfo on 18-3-4.
 */

public class DiscussInfoMapping extends Mapping<String>{

    public static DiscussInfoMapping INSTANCE=new DiscussInfoMapping();
    private ArrayMap<String,String> maps;

    private DiscussInfoMapping(){
        this.maps=new ArrayMap<>();
        maps.put(TableConstant.user+"."+ FieldConstant.email,"email");
        maps.put(TableConstant.user+"."+ FieldConstant.head,"mHeadUrl");
        maps.put(TableConstant.Area+"."+FieldConstant.areaName,"discussArea");
        maps.put(TableConstant.user+"."+FieldConstant.name,"mNickName");

        maps.put(TableConstant.discuss+"."+FieldConstant.oldContent,"mReplayedContent");
        maps.put(TableConstant.discuss+"."+FieldConstant.content,"mReplayContent");
        maps.put(TableConstant.discuss+"."+FieldConstant.images,"mImageUrls");
        maps.put(TableConstant.discuss+"."+FieldConstant.time,"mTime");
        competeDiscuss();
    }

    private void competeDiscuss(){
        String[] names=new String[]{TableConstant.discuss,TableConstant.discuessread,
        TableConstant.discusscate,TableConstant.discusscomic
        ,TableConstant.discussentertainment,TableConstant.discussgame
        ,TableConstant.discusssport, TableConstant.discussstar
        ,TableConstant.discussstudy,TableConstant.discusstour};

        for (String name:names){
            maps.put(name+"."+FieldConstant.oldContent,"mReplayedContent");
            maps.put(name+"."+FieldConstant.content,"mReplayContent");
            maps.put(name+"."+FieldConstant.images,"mImageUrls");
            maps.put(name+"."+FieldConstant.time,"mTime");
        }

    }

    public String  get(String key){
        return maps.get(key);
    }
}
