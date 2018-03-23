package com.example.dishfo.androidapp.netbean;

import android.util.ArrayMap;

import com.example.dishfo.androidapp.bean.NoteInfo;
import com.example.dishfo.androidapp.mvp.FieldConstant;
import com.example.dishfo.androidapp.mvp.TableConstant;
import com.example.dishfo.androidapp.util.PropertiesReader;

import java.util.HashMap;

/**
 * Created by dishfo on 18-2-22.
 */

public class NoteInfoMapping extends Mapping<String>{

    public static NoteInfoMapping INSTANCE=new NoteInfoMapping();
    private ArrayMap<String,String> maps=null;

    private NoteInfoMapping() {
        this.maps = new ArrayMap<>();

        competeMaps();
    }

    private void competeMaps(){
        String[] names={TableConstant.Note,TableConstant.noteate,
                TableConstant.notecomic,TableConstant.noteentertainment,
                TableConstant.notegame,TableConstant.noteread,
                TableConstant.notesport,TableConstant.notestar,
                TableConstant.notetour,TableConstant.notestudy};

        for(String name:names){
            maps.put(name+"."+FieldConstant.email,"email");
            maps.put(name+"."+FieldConstant.areaId,"areaId");
            maps.put(name+"."+FieldConstant.id,"id");
            maps.put(name+"."+FieldConstant.time,"time");
            maps.put(name+"."+FieldConstant.content,"content");
            maps.put(name+"."+FieldConstant.images,"images");
            maps.put(name+"."+FieldConstant.appreciateNumber,"appreciateNumber");
            maps.put(name+"."+FieldConstant.readNumber,"readNumber");
            maps.put(name+"."+FieldConstant.discussNumber,"discussNumber");
        }
    }

    public String get(String key){
        return maps.get(key);
    }

}
