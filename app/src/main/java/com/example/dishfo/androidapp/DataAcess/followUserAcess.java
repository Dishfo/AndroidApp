package com.example.dishfo.androidapp.DataAcess;

import com.example.dishfo.androidapp.mvp.FieldConstant;
import com.example.dishfo.androidapp.mvp.TableConstant;
import com.example.dishfo.androidapp.mvp.TypeConstant;
import com.example.dishfo.androidapp.netInterface.AddAction2;
import com.example.dishfo.androidapp.netInterface.InsertValuesAction;
import com.example.dishfo.androidapp.netInterface.JsonGenerator;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectClassNameAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectConditionAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectFieldsAction;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.PUT;

/**
 * Created by dishfo on 18-3-7.
 */

public class followUserAcess extends DataAcess{
    public static followUserAcess INSTANCE=new followUserAcess();

    private followUserAcess(){

    }

    public Observable getFollowUserByUser(String email){
        return NetMethod.INSTANCE.generateObserable("query",(generator, args) -> {
            competeFollowUserQueryByUser(generator, (String) args[0]);
        },email);
    }

    public Observable getFollowUserByFollow(String email,String followed){
        return NetMethod.INSTANCE.generateObserable("query",(generator, args) -> {
            competeFollowUserQueryByFollow(generator, (String) args[0],(String)args[1]);
        },email,followed);
    }

    public Observable<JsonObject> deleteFollow(String id){
        return NetMethod.INSTANCE.generateObserable("delete",(generator, args) -> {
            generateDelete(generator, TableConstant.followuser,(String)args[0]);
        },id);
    }

    public Observable<JsonObject> insertFollow(String id,String followedId){
        return NetMethod.INSTANCE.generateObserable("insert",(generator, args) -> {
            competeFollowUserInsert(generator,(String)args[0],(String)args[1]);
        },followedId,id);
    }

    private void competeFollowUserQueryClassName(JsonGenerator generator){
        SelectClassNameAction classname=new SelectClassNameAction();
        generator.openArray()
                .compete(classname, TableConstant.followuser)
                .closeNode("className");
    }

    private void competeFollowUserQueryField(JsonGenerator generator){
        SelectFieldsAction field=new SelectFieldsAction();
        generator.openArray()
                .compete(field, FieldConstant.id,TableConstant.followuser)
                .closeNode("field");

    }

    private void competeFollowUserInsertClassName(JsonGenerator generator){
        generator
                .compete(new AddAction2(),"className",TableConstant.followuser);
    }

    private void competFollowUserInsertField(JsonGenerator generator,String followedId,String id){
        InsertValuesAction insert=new InsertValuesAction();
        generator.openArray()
                .compete(insert,FieldConstant.id,"",TypeConstant.varchar,"primarykey")
                .compete(insert,FieldConstant.email,id,TypeConstant.varchar,"")
                .compete(insert,FieldConstant.followUserEmail,followedId,TypeConstant.varchar,"")
                .closeNode("values");
    }

    public void competeFollowUserInsert(JsonGenerator generator,String followedId,String id){
        generator.openNode();
        competeFollowUserInsertClassName(generator);
        competFollowUserInsertField(generator,followedId,id);
        generator.closeNode("");
    }

    public void competeFollowUserQueryByFollow(JsonGenerator generator, String email,String followed) {
        SelectConditionAction condition=new SelectConditionAction();
        generator.openNode();
        competeFollowUserQueryClassName(generator);
        competeFollowUserQueryField(generator);
        generator.openArray()
                .compete(condition,FieldConstant.email,email, TypeConstant.varchar,TableConstant.followuser,"0")
                .compete(condition,FieldConstant.followUserEmail,followed,TypeConstant.varchar,TableConstant.followuser,"0")
                .closeNode("condition");
        generator.closeNode("");
    }

    public void competeFollowUserQueryByUser(JsonGenerator generator, String email) {
        SelectConditionAction condition=new SelectConditionAction();
        generator.openNode();
        competeFollowUserQueryClassName(generator);
        competeFollowUserQueryField(generator);
        generator.openArray()
                .compete(condition,FieldConstant.email,email, TypeConstant.varchar,TableConstant.followuser,"0")
                .closeNode("condition");
        generator.closeNode("");
    }


}
