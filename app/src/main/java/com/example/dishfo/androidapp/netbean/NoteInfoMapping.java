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
        maps.put(TableConstant.likeNote+"."+FieldConstant.id,"likeId");
        maps.put(TableConstant.user+"."+FieldConstant.head,"mHeadUrl");
        maps.put(TableConstant.user+"."+FieldConstant.name,"mNickName");
        maps.put(TableConstant.Area+"."+FieldConstant.name,"mAreaName");
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
            maps.put(name+"."+FieldConstant.id,"id");
            maps.put(name+"."+FieldConstant.time,"mTime");
            maps.put(name+"."+FieldConstant.content,"mContent");
            maps.put(name+"."+FieldConstant.images,"mImageUrl");
            maps.put(name+"."+FieldConstant.appreciateNumber,"mAppreciateNumber");
            maps.put(name+"."+FieldConstant.readNumber,"mReadNumber");
            maps.put(name+"."+FieldConstant.discussNumber,"mDiscussNumber");
        }



    }

    public String get(String key){
        return maps.get(key);
    }

}
