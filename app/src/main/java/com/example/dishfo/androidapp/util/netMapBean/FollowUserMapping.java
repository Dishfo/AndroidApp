package com.example.dishfo.androidapp.util.netMapBean;

import android.util.ArrayMap;

import com.example.dishfo.androidapp.constant.FieldConstant;
import com.example.dishfo.androidapp.constant.TableConstant;

/**
 * Created by dishfo on 18-3-23.
 */

public class FollowUserMapping extends Mapping<String>{

    public static FollowUserMapping INSTANCE=new FollowUserMapping();
    private ArrayMap<String,String> maps;

    public FollowUserMapping() {
        maps=new ArrayMap<>();
        competeFollowUser();
    }

    private void competeFollowUser() {
        maps.put(TableConstant.followuser+"."+ FieldConstant.id,"id");
        maps.put(TableConstant.followuser+"."+ FieldConstant.email,"email");
        maps.put(TableConstant.followuser+"."+ FieldConstant.followUserEmail,"followUser");
    }

    @Override
    public String get(String key) {
        return maps.get(key);
    }
}
