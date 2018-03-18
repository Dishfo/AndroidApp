package com.example.dishfo.androidapp.DataAcess;

import com.example.dishfo.androidapp.mvp.FieldConstant;
import com.example.dishfo.androidapp.mvp.TableConstant;
import com.example.dishfo.androidapp.mvp.TypeConstant;
import com.example.dishfo.androidapp.netInterface.AddAction2;
import com.example.dishfo.androidapp.netInterface.AddAction3;
import com.example.dishfo.androidapp.netInterface.InsertValuesAction;
import com.example.dishfo.androidapp.netInterface.JsonGenerator;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectClassNameAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectConditionAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectFieldsAction;
import com.google.gson.JsonObject;

import io.reactivex.Observable;

/**
 * Created by dishfo on 18-3-7.
 */

public class followAreaAcess {
    public static followAreaAcess INSTANCE=new followAreaAcess();

    private followAreaAcess(){

    }

    public Observable<JsonObject> insertFolloArea(String email,String areaId){
        return NetMethod.INSTANCE.generateObserable("insert",(generator, args) -> {
            compteFollowAreaInsert(generator,(String)args[0],(String)args[1]);
        },email,areaId);
    }

    public Observable<JsonObject> deleteFollowArea(String followId){
        return NetMethod.INSTANCE.generateObserable("delete",(generator, args) -> {
            compteFollowDelete(generator,(String)args[0]);
        },followId);
    }

    public Observable<JsonObject> getFollowAreaByUser(String email){
        return NetMethod.INSTANCE.generateObserable("query",(generator, args) -> {
            competeFollowAreaByUser(generator, (String) args[0]);
        },email);
    }

    public Observable<JsonObject> getFollowAreaByFollow(String email,String areId){
        return NetMethod.INSTANCE.generateObserable("query",(generator, args) -> {
            competeFollowAreaByFollow(generator, (String) args[0],(String)args[1]);
        },email,areId);
    }


    private void competeFollowAreaClassName(JsonGenerator generator){
        SelectClassNameAction classname=new SelectClassNameAction();
        generator.openArray()
                .compete(classname, TableConstant.followArea)
                .closeNode("className");
    }

    private void competeFollowAreaField(JsonGenerator generator){
        SelectFieldsAction field=new SelectFieldsAction();
        generator.openArray()
                .compete(field, FieldConstant.id,TableConstant.followArea)
                .closeNode("field");
    }

    private void competeFollowAreaInsertClassName(JsonGenerator generator){
        generator.compete(new AddAction2(),"className",TableConstant.followArea);
    }

    private void competeFollowAreaInsertField(JsonGenerator generator,String email,String areaId){
        InsertValuesAction insert=new InsertValuesAction();
        generator.openArray()
                .compete(insert,FieldConstant.id,"",TypeConstant.varchar,"primarykey")
                .compete(insert,FieldConstant.email,email,TypeConstant.varchar,"")
                .compete(insert,FieldConstant.followAreaId,areaId,TypeConstant.integer,"")
                .compete(insert,FieldConstant.level,"",TypeConstant.varchar,"")
                .closeNode("values");
    }

    public void compteFollowAreaInsert(JsonGenerator generator,String email,String areId){
        generator.openNode();
        competeFollowAreaInsertClassName(generator);
        competeFollowAreaInsertField(generator,email,areId);
        generator.closeNode("");
    }

    public void compteFollowDelete(JsonGenerator generator,String followId){
        generator.openNode();
        generator.compete(new AddAction2(),"className",TableConstant.followArea)
                .compete(new AddAction3(),FieldConstant.id,followId)
                .closeNode("");
    }

    public void competeFollowAreaByUser(JsonGenerator generator, String email){
        SelectConditionAction condition=new SelectConditionAction();

        generator.openNode();
        competeFollowAreaClassName(generator);
        competeFollowAreaField(generator);
        generator.openArray()
                .compete(condition,FieldConstant.email,email, TypeConstant.varchar,TableConstant.followArea,"0")
                .closeNode("condition");
        generator.closeNode("");
    }

    public void competeFollowAreaByFollow(JsonGenerator generator,String email,String areaId){
        SelectConditionAction condition=new SelectConditionAction();

        generator.openNode();
        competeFollowAreaClassName(generator);
        competeFollowAreaField(generator);
        generator.openArray()
                .compete(condition,FieldConstant.email,email, TypeConstant.varchar,TableConstant.followArea,"0")
                .compete(condition,FieldConstant.followAreaId,areaId, TypeConstant.varchar,TableConstant.followArea,"0")
                .closeNode("condition");
        generator.closeNode("");
    }



}
