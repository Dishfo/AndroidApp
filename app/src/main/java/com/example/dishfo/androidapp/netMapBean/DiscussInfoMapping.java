package com.example.dishfo.androidapp.netMapBean;

import android.util.ArrayMap;

import com.example.dishfo.androidapp.constant.FieldConstant;
import com.example.dishfo.androidapp.constant.TableConstant;

/**
 * Created by dishfo on 18-3-4.
 */

public class DiscussInfoMapping extends Mapping<String>{

    public static DiscussInfoMapping INSTANCE=new DiscussInfoMapping();
    private ArrayMap<String,String> maps;

    private DiscussInfoMapping(){
        this.maps=new ArrayMap<>();
        competeDiscuss();
    }

    private void competeDiscuss(){
        String[] names=new String[]{TableConstant.discuss,TableConstant.discuessread,
        TableConstant.discusscate,TableConstant.discusscomic
        ,TableConstant.discussentertainment,TableConstant.discussgame
        ,TableConstant.discusssport, TableConstant.discussstar
        ,TableConstant.discussstudy,TableConstant.discusstour};

        for (String name:names){
            maps.put(name+"."+FieldConstant.id,"id");
            maps.put(name+"."+FieldConstant.email,"email");
            maps.put(name+"."+FieldConstant.content,"content");
            maps.put(name+"."+FieldConstant.images,"images");
            maps.put(name+"."+FieldConstant.oldUserName,"oldUser");
            maps.put(name+"."+FieldConstant.oldContent,"oldContent");
            maps.put(name+"."+FieldConstant.time,"time");
            maps.put(name+"."+FieldConstant.noteId,"noteId");
            maps.put(name+"."+FieldConstant.areaId,"areaId");
        }

    }

    public String  get(String key){
        return maps.get(key);
    }
}
