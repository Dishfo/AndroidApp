package com.example.dishfo.androidapp.data;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.util.Log;

import java.util.Date;

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
}
