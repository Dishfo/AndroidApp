package com.example.dishfo.androidapp.netInterface.SelectAction;

import com.example.dishfo.androidapp.netInterface.AddAction2;
import com.example.dishfo.androidapp.netInterface.SimpleAction;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by dishfo on 18-1-26.
 */

public class SelectFieldsAction implements SimpleAction{
    @Override
    public Object behave(JsonElement dst, Object[] args) {
        if(args.length !=2) {
            throw new IllegalArgumentException("must be classname name");
        }
        if(!(dst instanceof JsonArray)){
            throw new IllegalArgumentException("must be a  jsonarray");
        }

        AddAction2 action2=new AddAction2();
        JsonArray array= (JsonArray) dst;
        JsonObject object=new JsonObject();

        action2.behave(object,new Object[]{"name",args[0]});
        action2.behave(object,new Object[]{"className",args[1]});
        array.add(object);

        return dst;
    }
}
