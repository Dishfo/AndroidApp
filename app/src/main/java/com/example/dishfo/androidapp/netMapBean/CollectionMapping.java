package com.example.dishfo.androidapp.netMapBean;

import android.util.ArrayMap;

import com.example.dishfo.androidapp.constant.FieldConstant;
import com.example.dishfo.androidapp.constant.TableConstant;

/**
 * Created by dishfo on 18-3-27.
 */

public class CollectionMapping extends Mapping<String> {
    public static CollectionMapping INSTANCE=new CollectionMapping();

    private ArrayMap<String,String> maps;

    private CollectionMapping() {
        competeCollection();
    }

    private void competeCollection() {
        maps=new ArrayMap<>();
        maps.put(TableConstant.collection+"."+ FieldConstant.id,"id");
        maps.put(TableConstant.collection+"."+ FieldConstant.email,"email");
        maps.put(TableConstant.collection+"."+ FieldConstant.noteId,"noteId");
        maps.put(TableConstant.collection+"."+ FieldConstant.areaId,"areaId");
    }

    @Override
    public String get(String key) {
        return maps.get(key);
    }
}
