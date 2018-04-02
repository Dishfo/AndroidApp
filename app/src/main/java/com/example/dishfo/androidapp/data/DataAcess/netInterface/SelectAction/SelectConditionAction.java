package com.example.dishfo.androidapp.data.DataAcess.netInterface.SelectAction;

import com.example.dishfo.androidapp.data.DataAcess.netInterface.AddAction2;
import com.example.dishfo.androidapp.data.DataAcess.netInterface.AddAction3;
import com.example.dishfo.androidapp.data.DataAcess.netInterface.SimpleAction;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by dishfo on 18-1-26.
 */

public class SelectConditionAction implements SimpleAction {
    @Override
    public Object behave(JsonElement dst, Object[] args) {
        if(args.length !=5) {
            throw new IllegalArgumentException("must be name value " +
                    " type classname flag");
        }
        if(!(dst instanceof JsonArray)){
            throw new IllegalArgumentException("must be a  jsonarray");
        }

        AddAction2 action2=new AddAction2();
        AddAction3 action3=new AddAction3();
        JsonArray array= (JsonArray) dst;
        JsonObject object=new JsonObject();

        action2.behave(object,new Object[]{"name",args[0]});
        action3.behave(object,new Object[]{"value",args[1]});
        action2.behave(object,new Object[]{"type",args[2]});
        action2.behave(object,new Object[]{"className",args[3]});
        action3.behave(object,new Object[]{"flag",args[4]});
        array.add(object);

        return dst;
    }
}
