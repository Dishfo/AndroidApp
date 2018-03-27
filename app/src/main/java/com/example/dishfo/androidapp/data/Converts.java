package com.example.dishfo.androidapp.data;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dishfo on 18-3-19.
 */

public class Converts {
    @TypeConverter
    public static Date fromTimeStap(Long value){
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date==null?null:date.getTime();
    }


    @TypeConverter
    public static String fromList(List<String> data){
        return data==null?null:data.toString();
    }

    @TypeConverter
    public static List<String> toList(String data){
        List<String> list=new ArrayList<>();
        if(data==null){
            return list;
        }
        if(!data.endsWith("]")||!data.startsWith("[")) {
            return list;
        }
        String content=data.substring(1,data.length()-1);
        String[] items=content.split(",");
        for(String s:items){
            if(TextUtils.isEmpty(s))
                continue;
            list.add(s.trim());
        }
        return list;
    }

}
