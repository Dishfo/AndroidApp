package com.example.dishfo.androidapp.data.DataAcess.netInterface;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by dishfo on 18-1-24.
 */

public class InsertValuesAction implements SimpleAction{

    //用于插入是补全　json array ｖalues 的内部的json
    @Override
    public Object behave(JsonElement dst, Object[] args) {
        if(args.length !=4) {
            throw new IllegalArgumentException("must be name value type constraint");
        }

        AddAction2 action2=new AddAction2();
        AddAction3 action3=new AddAction3();
        JsonArray array= (JsonArray) dst;
        JsonObject object=new JsonObject();
        action2.behave(object,new Object[]{"name",args[0]});
        action3.behave(object,new Object[]{"value",args[1]});
        action2.behave(object,new Object[]{"type",args[2]});
        action3.behave(object,new Object[]{"constraint",args[3]});

        array.add(object);
        return  dst;
    }
}
