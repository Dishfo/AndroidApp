package com.example.dishfo.androidapp.netMapBean;

import android.util.ArrayMap;

import com.example.dishfo.androidapp.constant.FieldConstant;
import com.example.dishfo.androidapp.constant.TableConstant;

/**
 * Created by dishfo on 18-3-8.
 */

public class UserInfoMapping extends Mapping<String>{
    public static UserInfoMapping INSTANCE=new UserInfoMapping();
    private ArrayMap<String,String> maps;

    private UserInfoMapping(){
        maps=new ArrayMap<>();
        maps.put(TableConstant.user+"."+ FieldConstant.email,"email");
        maps.put(TableConstant.user+"."+ FieldConstant.head,"headUrl");
        maps.put(TableConstant.user+"."+ FieldConstant.name,"name");
    }

    @Override
    public String get(String key) {
        return maps.get(key);
    }
}
