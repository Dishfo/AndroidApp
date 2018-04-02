package com.example.dishfo.androidapp.data.DataAcess;

import com.example.dishfo.androidapp.constant.FieldConstant;
import com.example.dishfo.androidapp.constant.TableConstant;
import com.example.dishfo.androidapp.constant.TypeConstant;
import com.example.dishfo.androidapp.data.DataAcess.netInterface.AddAction2;
import com.example.dishfo.androidapp.data.DataAcess.netInterface.AddAction3;
import com.example.dishfo.androidapp.data.DataAcess.netInterface.InsertValuesAction;
import com.example.dishfo.androidapp.data.DataAcess.netInterface.JsonGenerator;
import com.example.dishfo.androidapp.data.DataAcess.netInterface.SelectAction.SelectClassNameAction;
import com.example.dishfo.androidapp.data.DataAcess.netInterface.SelectAction.SelectConditionAction;
import com.example.dishfo.androidapp.data.DataAcess.netInterface.SelectAction.SelectFieldsAction;
import com.example.dishfo.androidapp.util.netMapBean.FollowAreaMapping;
import com.example.dishfo.androidapp.bean.sqlBean.Area;
import com.example.dishfo.androidapp.bean.sqlBean.FollowArea;
import com.example.dishfo.androidapp.bean.sqlBean.User;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * 提供followUser网络访问接口
 * Created by dishfo on 18-3-7.
 */

public class FollowAreaAcess extends DataAcess{

    public FollowAreaAcess(NetMethod netMethod){
        super(netMethod);
    }

    public List<FollowArea> getFollowAreasByUser(String email) throws IOException {
        Call<JsonObject> call=netMethod.generateCall("query",(generator, args) -> {
            competeFollowAreaByUser(generator,(String)args[0]);
        },email);

        Response<JsonObject> response=call.execute();
        JsonObject object=response.body();

        if(!netMethod.isSucceed(object)){
            return null;
        }

        return objectParse.getBeans(netMethod.getResult(object),
                FollowArea.class,FollowAreaMapping.INSTANCE);
    }


    public FollowArea getFollowArea(Area area, User user) throws IOException {
        Call<JsonObject> call=netMethod.generateCall("query",(generator, args) -> {
            competeFollowAreaByFollow(generator,(String)args[1],(String)args[0]);
        },area.getId(),user.getEmail());

        Response<JsonObject> response=call.execute();
        JsonObject object=response.body();
        int code=object==null?-1:object.get("code").getAsInt();
        if(code!=1){
            return null;
        }
        List<FollowArea> followAreas= objectParse.getBeans(object.get("result").getAsString(),
                FollowArea.class, FollowAreaMapping.INSTANCE);

        return (followAreas==null||followAreas.size()==0)?null:followAreas.get(0);
    }

    public boolean deleteFollowaArea(FollowArea followArea) throws IOException {
        Call<JsonObject> call=netMethod.generateCall("delete",(generator, args) -> {
            compteFollowDelete(generator,(String)args[0]);
        },followArea.getId());

        Response<JsonObject> response=call.execute();
        JsonObject object=response.body();
        int code=object==null?-1:object.get("code").getAsInt();
        if(code!=1){
            return false;
        }
        return true;
    }

    public FollowArea inertFollowArea(FollowArea followArea) throws IOException {
        Call<JsonObject> call=netMethod.generateCall("insert",(generator, args) -> {
            compteFollowAreaInsert(generator,followArea);
        },followArea);

        Response<JsonObject> response=call.execute();
        JsonObject object=response.body();
        int code=object==null?-1:object.get("code").getAsInt();
        if(code!=1){
            return null;
        }

        return objectParse.getFromInsert(object.get("result").getAsString(),
                FollowArea.class,FollowAreaMapping.INSTANCE);
    }

    /**
     * 构建相关的json 语句
     * @param generator
     */
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

    private void competeFollowAreaInsertField(JsonGenerator generator,FollowArea followArea){
        InsertValuesAction insert=new InsertValuesAction();
        generator.openArray()
                .compete(insert,FieldConstant.id,"",TypeConstant.varchar,"primarykey")
                .compete(insert,FieldConstant.email,followArea.getEmail(),TypeConstant.varchar,"")
                .compete(insert,FieldConstant.followAreaId,followArea.getFollowAreaId(),TypeConstant.integer,"")
                .compete(insert,FieldConstant.level,"",TypeConstant.varchar,"")
                .closeNode("values");
    }

    public void compteFollowAreaInsert(JsonGenerator generator,FollowArea followArea){
        generator.openNode();
        competeFollowAreaInsertClassName(generator);
        competeFollowAreaInsertField(generator,followArea);
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
