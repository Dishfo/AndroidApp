package com.example.dishfo.androidapp.netInterface;

import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.util.PropertiesReader;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by dishfo on 18-1-24.
 */


//replace value
public class AddAction2 implements SimpleAction{


    @Override
    public Object behave(JsonElement dst, Object[] args) {
        if(args.length!=2){
            throw new IllegalArgumentException(" args should be name and value ");
        }
        String value=(String) args[1];
        value= PropertiesReader.get(value, MyApplication.REVERSE_MAP);
        ((JsonObject)dst).addProperty((String) args[0],value);
        return dst;
    }
}
