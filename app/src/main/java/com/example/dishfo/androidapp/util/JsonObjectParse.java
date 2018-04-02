package com.example.dishfo.androidapp.util;

import android.util.Log;

import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.data.Converts;
import com.example.dishfo.androidapp.util.netMapBean.Mapping;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Singleton;

/**
 * Created by dishfo on 18-3-19.
 */

/**
 *
 * 用于将jsonObject中的复杂json字符串转化为对应的bean
 */
@Singleton
public class JsonObjectParse {

    private JsonParser mParser=new JsonParser();

    /**
     * 支持范型的的解析方法
     * @param result
     * @param cls 对应解析结果的class
     * @param mapping bean　的json映射
     * @param <T> 解析后的类型
     * @return
     */
    public <T> List<T> getBeans(String result, Class<T> cls, Mapping<String> mapping){
        List<T> list=new ArrayList<>();
        JsonObject object=mParser.parse(result).getAsJsonObject();
        JsonArray array=object.get("result")
                .getAsJsonArray();
        Iterator<JsonElement> elements=array.iterator();
        while (elements.hasNext()){
            list.add(get(elements.next(),cls,mapping));
        }
        return list;
    }

    public <T> T getFromInsert(String result,Class<T> cls,Mapping<String> mapping){
        JsonObject object=mParser.parse(result).getAsJsonObject();
        String table=object.get("className").getAsString();
        JsonArray array=object.get("values")
                .getAsJsonArray();

        T res= null;
        try {
            res = cls.getConstructor(new Class<?>[]{}).newInstance();
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        } catch (NoSuchMethodException e) {
            return null;
        }

        Iterator<JsonElement> elements=array.iterator();
        while (elements.hasNext()){
            JsonObject obj=elements.next().getAsJsonObject();
            String name=table+"."+obj.get("name").getAsString();
            try {
                String fieldname=mapping.
                        get(PropertiesReader.strTurn(name, MyApplication.MAP));
                if(fieldname==null)
                    continue;
                Field field=cls.getDeclaredField(fieldname);
                field.setAccessible(true);
                Type type=field.getType();
                if(type.equals(String.class)){
                    field.set(res,obj.get("value").getAsString());
                }else if(type.equals(List.class)){
                    List<String> tmplist= Converts.toList(obj.get("value").getAsString());
                    field.set(res,tmplist);
                }
            } catch (NoSuchFieldException e) {
                Log.d("test",e.toString()+" "+name);
            } catch (IllegalAccessException e) {
                Log.d("test",e.toString()+" "+name);
            }
        }
        return res;
    }

    public <T> T get(JsonElement element,Class<T> cls,Mapping<String> mapping){
        T res= null;
        try {
            res = cls.getConstructor(new Class<?>[]{}).newInstance();
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        } catch (NoSuchMethodException e) {
            return null;
        }

        JsonArray array=element.getAsJsonObject()
                .get("result")
                .getAsJsonArray();

        Iterator<JsonElement> elements=array.iterator();
        while (elements.hasNext()){
            JsonObject object=elements.next().getAsJsonObject();
            String name=object.get("name").getAsString();
            try {
                Field field=cls.getDeclaredField(mapping.
                        get(PropertiesReader.strTurn(name, MyApplication.MAP)));
                field.setAccessible(true);
                Type type=field.getType();
                if(type.equals(String.class)){
                    Log.d("error_test",name+"===="+object.get("value").getAsString());
                    field.set(res,object.get("value").getAsString());
                }else if(type.equals(List.class)){
                    List<String> tmplist= Converts.toList(object.get("value").getAsString());
                    field.set(res,tmplist);
                }
            } catch (NoSuchFieldException e) {
                Log.d("test",e.toString()+" "+name);
            } catch (IllegalAccessException e) {
                Log.d("test",e.toString()+" "+name);
            }
        }
        return res;


    }


}
