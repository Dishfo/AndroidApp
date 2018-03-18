package com.example.dishfo.androidapp.netInterface.UpdateAction;

import com.example.dishfo.androidapp.netInterface.AddAction2;
import com.example.dishfo.androidapp.netInterface.AddAction3;
import com.example.dishfo.androidapp.netInterface.SimpleAction;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by dishfo on 18-2-27.
 */

public class UpdateConditionAction implements SimpleAction{
    @Override
    public Object behave(JsonElement dst, Object[] args) {
        if(args.length !=3) {
            throw new IllegalArgumentException("must be name value " +
                    " type");
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

        array.add(object);
        return dst;
    }
}
