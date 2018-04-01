package com.example.dishfo.androidapp.data.DataAcess;

import android.support.annotation.NonNull;

import com.example.dishfo.androidapp.constant.FieldConstant;
import com.example.dishfo.androidapp.constant.TableConstant;
import com.example.dishfo.androidapp.constant.TypeConstant;
import com.example.dishfo.androidapp.netInterface.JsonGenerator;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectClassNameAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectConditionAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectFieldsAction;
import com.example.dishfo.androidapp.netMapBean.AreaInfoMapping;
import com.example.dishfo.androidapp.sqlBean.Area;
import com.example.dishfo.androidapp.util.JsonObjectParse;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by dishfo on 18-3-6.
 */

/**
 *
 * 提供同步的area数据访问
 */
public class AreaAcess {
   NetMethod netMethod;
   JsonObjectParse objectParse;

    public AreaAcess(@NonNull NetMethod netMethod){
        this.netMethod=netMethod;
        objectParse=new JsonObjectParse();
    }

    public List<Area> getAreaByName(String name) throws IOException {
        Call<JsonObject> call=netMethod.generateCall("query",(generator, args) -> {
            competeAreaQuerybyName(generator,(String)args[0]);
        },name);
        Response<JsonObject> response=call.execute();
        JsonObject object=response.body();
        int code=object.get("code").getAsInt();
        if(code!=1){
            return null;
        }

        return objectParse.getBeans(object.get("result").getAsString(),Area.class, AreaInfoMapping.INSTANCE);
    }

    public List<Area> getAreaById(String id) throws IOException {
        Call<JsonObject> call=netMethod.generateCall("query",(generator, args) -> {
            competeAreaQuerybyName(generator,(String)args[0]);
        },id);
        Response<JsonObject> response=call.execute();
        JsonObject object=response.body();
        int code=object.get("code").getAsInt();
        if(code!=1){
            return null;
        }
        return objectParse.getBeans(object.get("result").getAsString(),Area.class, AreaInfoMapping.INSTANCE);
    }

    /**
     * 用与构建向服务器查询的方法
     * @param generator
     */

    private void competeAreaQueryClassName(JsonGenerator generator){
        SelectClassNameAction classname=new SelectClassNameAction();
        generator.openArray()
                .compete(classname, TableConstant.Area)
                .closeNode("className");
    }

    private void competeAreaQueryField(JsonGenerator generator){
        SelectFieldsAction field=new SelectFieldsAction();
        generator.openArray()
                .compete(field, FieldConstant.id,TableConstant.Area)
                .compete(field,FieldConstant.head,TableConstant.Area)
                .compete(field,FieldConstant.name,TableConstant.Area)
                .closeNode("field");
    }

    public void competeAreaQueryById(JsonGenerator generator,String id){
        SelectConditionAction condition=new SelectConditionAction();

        generator.openNode();
        competeAreaQueryClassName(generator);
        competeAreaQueryField(generator);
        generator.openArray()
                .compete(condition,FieldConstant.id,id, TypeConstant.varchar,TableConstant.Area,"0")
                .closeNode("condition");
        generator.closeNode("");
    }

    public void competeAreaQuerybyName(JsonGenerator generator,String name){
        SelectConditionAction condition=new SelectConditionAction();

        generator.openNode();
        competeAreaQueryClassName(generator);
        competeAreaQueryField(generator);
        generator.openArray()
                .compete(condition,FieldConstant.name,name, TypeConstant.varchar,TableConstant.Area,"0")
                .closeNode("condition");
        generator.closeNode("");
    }

}
