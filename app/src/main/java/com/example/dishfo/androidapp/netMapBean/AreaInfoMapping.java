package com.example.dishfo.androidapp.netMapBean;

import android.util.ArrayMap;

import com.example.dishfo.androidapp.constant.FieldConstant;
import com.example.dishfo.androidapp.constant.TableConstant;

/**
 * Created by dishfo on 18-3-5.
 */

public class AreaInfoMapping extends Mapping<String>{
    public static AreaInfoMapping INSTANCE=new AreaInfoMapping();
    private ArrayMap<String,String> maps;

    private AreaInfoMapping(){
        maps=new ArrayMap<>();
        maps.put(TableConstant.Area+"."+ FieldConstant.id,"id");
        maps.put(TableConstant.Area+"."+ FieldConstant.head,"head");
        maps.put(TableConstant.Area+"."+ FieldConstant.name,"name");
    }

    @Override
    public String get(String key) {
        return maps.get(key);
    }



}
