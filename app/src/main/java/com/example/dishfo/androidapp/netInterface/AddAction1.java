package com.example.dishfo.androidapp.netInterface;

import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.util.PropertiesReader;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

//replace name;
public class AddAction1 implements SimpleAction {

    @Override
    public Object behave(JsonElement dst, Object[] args) {
        if(args.length!=2){
            throw new IllegalArgumentException(" args should be name and value ");
        }
        String name=(String) args[0];
        name=PropertiesReader.get(name, MyApplication.REVERSE_MAP);
        ((JsonObject)dst).addProperty(name,(String) args[1]);
        return dst;
    }
}
