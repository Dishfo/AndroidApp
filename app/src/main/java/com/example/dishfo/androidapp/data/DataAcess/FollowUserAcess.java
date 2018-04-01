package com.example.dishfo.androidapp.data.DataAcess;

import com.example.dishfo.androidapp.constant.FieldConstant;
import com.example.dishfo.androidapp.constant.TableConstant;
import com.example.dishfo.androidapp.constant.TypeConstant;
import com.example.dishfo.androidapp.netInterface.AddAction2;
import com.example.dishfo.androidapp.netInterface.InsertValuesAction;
import com.example.dishfo.androidapp.netInterface.JsonGenerator;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectClassNameAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectConditionAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectFieldsAction;
import com.example.dishfo.androidapp.netMapBean.FollowUserMapping;
import com.example.dishfo.androidapp.sqlBean.FollowUser;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * 用于提供FollowUser访问的网络接口
 * Created by dishfo on 18-3-7.
 */

public class FollowUserAcess extends DataAcess{


    public FollowUserAcess(NetMethod netMethod){
       super(netMethod);
    }

    public List<FollowUser> getFollowUsersByUser(String email) throws IOException {
        Call<JsonObject> call=netMethod.generateCall("query",(generator, args) -> {
            competeFollowUserQueryByUser(generator,(String)args[0]);
        },email);

        Response<JsonObject> response=call.execute();
        JsonObject object=response.body();

        if(!netMethod.isSucceed(object)){
            return null;
        }

        return objectParse.getBeans(netMethod.getResult(object),FollowUser.class,
                FollowUserMapping.INSTANCE);
    }


    public FollowUser getFollowUser(String email,String followed) throws IOException {
        Call<JsonObject> call=netMethod.generateCall("query",(generator, args) -> {
            competeFollowUserQueryByFollow(generator,(String)args[0],(String)args[1]);
        },email,followed);

        Response<JsonObject> response=call.execute();
        JsonObject object=response.body();
        int code=object==null?-1:object.get("code").getAsInt();
        if(code!=1){
            return null;
        }

        List<FollowUser> list=objectParse.getBeans(object.get("result").getAsString()
                ,FollowUser.class, FollowUserMapping.INSTANCE);
        if(list==null||list.size()==0){
            return null;
        }
        return list.get(0);
    }

    public FollowUser insertFollowUser(FollowUser followUser) throws IOException {
        Call<JsonObject> call=netMethod.generateCall("insert",(generator, args) -> {
            competeFollowUserInsert(generator,(String)args[0],(String)args[1]);
        },followUser.getFollowUser(),followUser.getEmail());

        Response<JsonObject> response=call.execute();
        JsonObject object=response.body();
        int code=object==null?-1:object.get("code").getAsInt();
        if(code!=1){
            return null;
        }

        FollowUser followUser1=objectParse.getFromInsert(object.get("result").getAsString(),
                FollowUser.class,FollowUserMapping.INSTANCE);
        return  followUser1;
    }

    public boolean deleteFollowUser(FollowUser followUser) throws IOException {
        Call<JsonObject> call=netMethod.generateCall("delete",(generator, args) -> {
            generateDelete(generator,TableConstant.followuser,(String)args[0]);
        },followUser.getId());

        Response<JsonObject> response=call.execute();
        JsonObject object=response.body();
        int code=object==null?-1:object.get("code").getAsInt();
        if(code!=1){
            return false;
        }
        return true;

    }


    /**
     * 用于构建相关的json语句
     * @param generator
     */

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
                .compete(field, FieldConstant.email,TableConstant.followuser)
                .compete(field, FieldConstant.followUserEmail,TableConstant.followuser)
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
