package com.example.dishfo.androidapp.util;

import android.util.ArrayMap;
import android.util.Log;

import com.example.dishfo.androidapp.application.MyApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by dishfo on 18-1-19.
 */

//用于加载映射文件的类

public class PropertiesReader {

    private static PropertiesReader INSTANCE;
    private ArrayMap<String,Properties> mMaps;

    private  PropertiesReader(){
        mMaps=new ArrayMap<>();
    }

    public static synchronized void init(){
        INSTANCE=new PropertiesReader();
    }

    public static void add(InputStream inputStream,String name){
        Properties properties=new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            Log.e("error","load properties failed");
            throw new Error("");
        }
        INSTANCE.mMaps.put(name,properties);
    }

    public static String get(String key,String frommapkey){
        Properties properties=INSTANCE.mMaps.get(frommapkey);
        if(properties==null){
            throw  new IllegalArgumentException("the property file doesn't exist :"+frommapkey);
        }else{
            String value=properties.getProperty(key);
            if(value==null){
                throw new IllegalArgumentException("the property doesn't exist : "+key);
            }else {
                return value;
            }
        }
    }

    public static String strTurn(String str,String frommapkey){
        String contains[]=str.split("\\.");

        if(contains.length<2){
            Log.d("test",contains[0]);
            throw new IllegalArgumentException("string must contain front and behind");

        }

        return PropertiesReader.get(contains[0], frommapkey)+"."+
                PropertiesReader.get(contains[1],frommapkey);
    }

}
