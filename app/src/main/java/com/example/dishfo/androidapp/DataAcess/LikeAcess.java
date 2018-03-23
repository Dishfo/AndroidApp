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
import com.example.dishfo.androidapp.netbean.LikeMapping;
import com.example.dishfo.androidapp.sqlBean.Like;
import com.example.dishfo.androidapp.util.JsonObjectParse;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * 提供访问like的网络接口
 * Created by dishfo on 18-3-7.
 */

public class LikeAcess {
    private NetMethod netMethod;
    private JsonObjectParse objectParse;

    public LikeAcess(NetMethod netMethod){
        objectParse=new JsonObjectParse();
        this.netMethod=netMethod;
    }

    public Like getLike(String email,String noteId,String areaId) throws IOException {
        Call<JsonObject> call=netMethod.generateCall("query",(generator, args) -> {
            competeLikeQueryByNote(generator,(String)args[0],(String)args[1],(String)args[2]);
        },email,noteId,areaId);
        Response<JsonObject> response=call.execute();
        JsonObject object=response.body();
        int code= object != null ? object.get("code").getAsInt() : -1;
        if(code!=1){
            return null;
        }
        List<Like> likes=objectParse.getBeans(object.get("result").getAsString(),Like.class, LikeMapping.INSTANCE);
        if(likes==null||likes.size()==0){
            return null;
        }
        return likes.get(0);
    }

    public Like saveLike(Like like) throws IOException {
        Call<JsonObject> call=netMethod.generateCall("insert",(generator, args) -> {
            competeLikeInsert(generator,(Like) args[0]);
        },like);

        Response<JsonObject> response=call.execute();
        JsonObject object=response.body();
        int code=object!=null?object.get("code").getAsInt():-1;
        if(code!=1){
            return null;
        }
        Like result=objectParse.getFromInsert(object.get("result").getAsString(),Like.class, LikeMapping.INSTANCE);
        return result;

    }

    public boolean deleteLike(Like like) throws IOException {
        Call<JsonObject> call=netMethod.generateCall("delete",(generator, args) -> {
            competeLikeDeleteById(generator,(String)args[0]);
        },like.getId());

        Response<JsonObject> response=call.execute();
        JsonObject object=response.body();
        int code=object!=null?object.get("code").getAsInt():-1;
        if(code!=1){
            return false;
        }
        return true;
    }


    /**
     * 用于构建like表json查询语句
     * @param generator
     */
    private void competeLikeInsertClassName(JsonGenerator generator){
        generator
                .compete(new AddAction2(),"className",TableConstant.likeNote);
    }

    private void competeLikeInsertField(JsonGenerator generator,Like like){
        InsertValuesAction insert=new InsertValuesAction();
        generator.openArray()
                .compete(insert,FieldConstant.id,"",TypeConstant.varchar,"primarykey")
                .compete(insert,FieldConstant.email,like.getEmail(),TypeConstant.varchar,"")
                .compete(insert,FieldConstant.noteId,like.getNoteId(),TypeConstant.integer,"")
                .compete(insert,FieldConstant.areaId,like.getAreaId(),TypeConstant.varchar,"")
                .closeNode("values");

    }

    private void competeLikeQueryClassName(JsonGenerator generator){
        SelectClassNameAction classname=new SelectClassNameAction();
        generator.openArray()
                .compete(classname, TableConstant.likeNote)
                .closeNode("className");
    }

    private void  competeLikeQueryField(JsonGenerator generator){
        SelectFieldsAction field=new SelectFieldsAction();
        generator.openArray()
                .compete(field, FieldConstant.id,TableConstant.likeNote)
                .compete(field, FieldConstant.email,TableConstant.likeNote)
                .compete(field, FieldConstant.noteId,TableConstant.likeNote)
                .compete(field, FieldConstant.areaId,TableConstant.likeNote)
                .closeNode("field");
    }

    public void competeLikeDeleteById(JsonGenerator generator,String id){
        generator.openNode();
        generator.compete(new AddAction2(),"className",TableConstant.likeNote)
                .compete(new AddAction3(),FieldConstant.id,id)
                .closeNode("");
    }

    public void competeLikeInsert(JsonGenerator generator,Like like){
        generator.openNode();
        competeLikeInsertClassName(generator);
        competeLikeInsertField(generator,like);
        generator.closeNode("");
    }

    public void competeLikeQueryByNote(JsonGenerator generator,String email,String areaId,String noteId){
        SelectConditionAction condition=new SelectConditionAction();
        generator.openNode();
        competeLikeQueryClassName(generator);
        competeLikeQueryField(generator);

        generator.openArray()
                .compete(condition,FieldConstant.email,email, TypeConstant.varchar,TableConstant.likeNote,"0")
                .compete(condition,FieldConstant.noteId,noteId,TypeConstant.varchar,TableConstant.likeNote,"0")
                .compete(condition,FieldConstant.areaId,areaId,TypeConstant.varchar,TableConstant.likeNote,"0")
                .closeNode("condition");
        generator.closeNode("");
    }

    public void competeLikeQueryByUser(JsonGenerator generator, String email){
        SelectConditionAction condition=new SelectConditionAction();
        generator.openNode();
        competeLikeQueryClassName(generator);
        competeLikeQueryField(generator);

        generator.openArray()
                .compete(condition,FieldConstant.email,email, TypeConstant.varchar,TableConstant.likeNote,"0")
                .closeNode("condition");
        generator.closeNode("");
    }
}
