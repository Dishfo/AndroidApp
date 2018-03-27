package com.example.dishfo.androidapp.netbean;

import android.util.ArrayMap;

import com.example.dishfo.androidapp.mvp.FieldConstant;
import com.example.dishfo.androidapp.mvp.TableConstant;

/**
 * Created by dishfo on 18-3-23.
 */

public class FollowAreaMapping extends Mapping<String> {
    public static FollowAreaMapping INSTANCE=new FollowAreaMapping();
    private ArrayMap<String,String> maps;

    private FollowAreaMapping() {
        maps=new ArrayMap<>();
        competeFollowArea();
    }

    private void competeFollowArea() {
        maps.put(TableConstant.followArea+"."+ FieldConstant.id,"id");
        maps.put(TableConstant.followArea+"."+ FieldConstant.email,"email");
        maps.put(TableConstant.followArea+"."+ FieldConstant.followAreaId,"followAreaId");
    }

    @Override
    public String get(String key) {
        return maps.get(key);
    }
}
