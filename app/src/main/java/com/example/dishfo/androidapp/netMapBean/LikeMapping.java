package com.example.dishfo.androidapp.netMapBean;

import android.util.ArrayMap;

import com.example.dishfo.androidapp.constant.FieldConstant;
import com.example.dishfo.androidapp.constant.TableConstant;

/**
 * Created by dishfo on 18-3-21.
 */

public class LikeMapping extends Mapping<String> {
    public static LikeMapping INSTANCE=new LikeMapping();
    private ArrayMap<String,String> maps=null;

    private LikeMapping() {
        this.maps = new ArrayMap<>();

        competeMaps();
    }

    private void competeMaps(){
        maps=new ArrayMap<>();
        maps.put(TableConstant.likeNote+"."+ FieldConstant.id,"id");
        maps.put(TableConstant.likeNote+"."+ FieldConstant.email,"email");
        maps.put(TableConstant.likeNote+"."+ FieldConstant.noteId,"noteId");
        maps.put(TableConstant.likeNote+"."+ FieldConstant.areaId,"areaId");
    }

    public String get(String key){
        return maps.get(key);
    }
}
