package com.example.dishfo.androidapp.data.DataAcess.netInterface.SelectAction;

import com.example.dishfo.androidapp.data.DataAcess.netInterface.AddAction2;
import com.example.dishfo.androidapp.data.DataAcess.netInterface.SimpleAction;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by dishfo on 18-1-26.
 */


//args 0,1,2,  为表名　用户名　密码
public class SelectClassNameAction implements SimpleAction {

    @Override
    public Object behave(JsonElement dst, Object[] args) {

        if(args.length!=1){
            throw new IllegalArgumentException("must be classname: value");
        }
        if(!(dst instanceof JsonArray)){
            throw new IllegalArgumentException("must be a  jsonarray");
        }

        AddAction2 action2=new AddAction2();
        JsonArray array= (JsonArray) dst;

        JsonObject object=new JsonObject();

        action2.behave(object,new Object[]{"className",args[0]});
        array.add(object);


        return dst;
    }
}
