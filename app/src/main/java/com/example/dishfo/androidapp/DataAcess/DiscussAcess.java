package com.example.dishfo.androidapp.DataAcess;

import com.example.dishfo.androidapp.bean.AreaInfo;
import com.example.dishfo.androidapp.bean.DiscussInfo;
import com.example.dishfo.androidapp.bean.NoteInfo;
import com.example.dishfo.androidapp.mvp.FieldConstant;
import com.example.dishfo.androidapp.mvp.TableConstant;
import com.example.dishfo.androidapp.mvp.TypeConstant;
import com.example.dishfo.androidapp.netInterface.AddAction2;
import com.example.dishfo.androidapp.netInterface.InsertValuesAction;
import com.example.dishfo.androidapp.netInterface.JsonGenerator;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectClassNameAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectConditionAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectFieldsAction;
import com.example.dishfo.androidapp.netbean.AreaWithNDMapping;
import com.example.dishfo.androidapp.netbean.DiscussInfoMapping;
import com.example.dishfo.androidapp.sqlBean.Area;
import com.example.dishfo.androidapp.sqlBean.Discuss;
import com.example.dishfo.androidapp.sqlBean.Note;
import com.example.dishfo.androidapp.sqlBean.User;
import com.example.dishfo.androidapp.util.JsonObjectParse;
import com.example.dishfo.androidapp.viewBean.ViewDiscuss;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by dishfo on 18-3-7.
 */

public class DiscussAcess {
    private NetMethod netMethod;
    private JsonObjectParse objectParse;

    public DiscussAcess(NetMethod netMethod){
        this.netMethod=netMethod;
        objectParse=new JsonObjectParse();
    }


    public Observable<JsonObject> getDiscussByUser(String email,String discuss){
        return NetMethod.INSTANCE.generateObserable("query",(generator, args) -> {
            NetMethod.INSTANCE.competeDiscussByUser(generator, (String) args[0], (String) args[1]);
        },email,discuss);
    }

    public List<Discuss> getDiscussByNote(Note note, Area area) throws IOException {
        String discuss=AreaWithNDMapping.INSTANCE.getDiscuss(area.getName());
        Call<JsonObject> call=netMethod.generateCall("query",(generator, args) -> {
            competeDiscussQueryByNote(generator,(String) args[0] , (String) args[1]);
        },note.getId(),discuss);

        Response<JsonObject> response=call.execute();
        JsonObject object=response.body();

        int code=object==null?-1:object.get("code").getAsInt();
        if(code!=1){
            return null;
        }
        return objectParse.getBeans(object.get("result").getAsString(),Discuss.class, DiscussInfoMapping.INSTANCE);
    }

    public Discuss insertDiscuss(Discuss discuss,String areaName) throws IOException {
        Call<JsonObject> call=netMethod.generateCall("insert",(generator, args) -> {
            competeDiscussInsert(generator,(Discuss)args[0],(String)args[1]);
        },discuss,areaName);

        Response<JsonObject> response=call.execute();
        JsonObject object=response.body();
        int code=object==null?-1:object.get("code").getAsInt();
        if (code!=1){
            return null;
        }

        return objectParse.getFromInsert(object.get("result").getAsString(),Discuss.class,DiscussInfoMapping.INSTANCE);
    }


    private void competeDiscussQueryField(JsonGenerator generator,String discussarea){
        SelectFieldsAction field=new SelectFieldsAction();
        generator.openArray()
                .compete(field,FieldConstant.id, discussarea)
                .compete(field,FieldConstant.email, discussarea)
                .compete(field,FieldConstant.content, discussarea)
                .compete(field,FieldConstant.images, discussarea)
                .compete(field,FieldConstant.oldContent, discussarea)
                .compete(field,FieldConstant.oldUserName, discussarea)
                .compete(field,FieldConstant.time, discussarea)
                .compete(field,FieldConstant.noteId, discussarea)
                .compete(field,FieldConstant.areaId, discussarea)
                .closeNode("field");
    }

    private void competeDiscussQueryClassName(JsonGenerator generator,String discussarea){
        SelectClassNameAction classname=new SelectClassNameAction();
        generator.openArray()
                .compete(classname,discussarea)
                .closeNode("className");
    }

    public void competeDiscussQueryByNote(JsonGenerator generator,String noteId,String discuss){
        SelectConditionAction condition=new SelectConditionAction();
        generator.openNode();
        competeDiscussQueryClassName(generator,discuss);
        competeDiscussQueryField(generator,discuss);

        generator.openArray()
                .compete(condition,FieldConstant.noteId,noteId,TypeConstant.varchar,discuss,"0")
                .closeNode("condition");
        generator.closeNode("");
    }

    private void competeDiscussInsertClassName(JsonGenerator generator,String discuss){
        generator.compete(new AddAction2(),"className",discuss);
    }

    private void competeDiscussInsertField(JsonGenerator generator,Discuss discuss){
        InsertValuesAction insert=new InsertValuesAction();

        generator.openArray()
                .compete(insert, FieldConstant.id,"", TypeConstant.varchar,"primarykey")
                .compete(insert,FieldConstant.email,discuss.getEmail(),TypeConstant.varchar,"")
                .compete(insert,FieldConstant.content,discuss.getContent(),TypeConstant.varchar,"")
                .compete(insert,FieldConstant.images,NetMethod.INSTANCE.parseList(discuss.getImages()),TypeConstant.varchar,"")
                .compete(insert,FieldConstant.oldUserName,discuss.getOldUser(),TypeConstant.varchar,"")
                .compete(insert,FieldConstant.oldContent,discuss.getOldContent(),TypeConstant.varchar,"")
                .compete(insert,FieldConstant.oldImages,"",TypeConstant.varchar,"")
                .compete(insert,FieldConstant.time,"",TypeConstant.varchar,"")
                .compete(insert,FieldConstant.noteId,discuss.getNoteId(),TypeConstant.varchar,"")
                .compete(insert,FieldConstant.areaId,discuss.getAreaId(),TypeConstant.varchar,"");
        generator.closeNode("values");
    }

    public void competeDiscussInsert(JsonGenerator generator,Discuss discuss,String areaName){
        generator.openNode();
        competeDiscussInsertClassName(generator, AreaWithNDMapping.INSTANCE.getDiscuss(areaName));
        competeDiscussInsertField(generator,discuss);
        generator.closeNode("");
    }


}
