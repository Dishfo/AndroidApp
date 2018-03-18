package com.example.dishfo.androidapp.DataAcess;

import com.example.dishfo.androidapp.bean.NoteInfo;
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

public class LikeAcess {
    public  static LikeAcess INSTANCE=new LikeAcess();

    private LikeAcess(){

    }

    public Observable<JsonObject> getLikeByUser(String email){
        return NetMethod.INSTANCE.generateObserable("query",(generator, args) -> {
            competeLikeQueryByUser(generator, (String) args[0]);
        },email);
    }

    public Observable<JsonObject> getLikeByNote(NoteInfo info){
        return NetMethod.INSTANCE.generateObserable("query",(generator, args) -> {
            competeLikeQueryByNote(generator, (NoteInfo) args[0]);
        },info);
    }

    public Observable<JsonObject> insertLike(NoteInfo info){
        return NetMethod.INSTANCE.generateObserable("insert",(generator, args) -> {
            competeLikeInsert(generator, (String)args[0],(NoteInfo) args[1]);
        },NetMethod.INSTANCE.getUser(),info);
    }

    public Observable<JsonObject> deleteLike(String id){
        return NetMethod.INSTANCE.generateObserable("delete",(generator, args) -> {
            competeLikeDeleteById(generator, (String)args[0]);
        },id);
    }


    private void competeLikeInsertClassName(JsonGenerator generator){
        generator
                .compete(new AddAction2(),"className",TableConstant.likeNote);
    }

    private void competeLikeInsertField(JsonGenerator generator,String email,NoteInfo info){
        InsertValuesAction insert=new InsertValuesAction();
        generator.openArray()
                .compete(insert,FieldConstant.id,"",TypeConstant.varchar,"primarykey")
                .compete(insert,FieldConstant.email,email,TypeConstant.varchar,"")
                .compete(insert,FieldConstant.noteId,info.id,TypeConstant.integer,"")
                .compete(insert,FieldConstant.areaName,info.mAreaName,TypeConstant.varchar,"")
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
                .closeNode("field");
    }

    public void competeLikeDeleteById(JsonGenerator generator,String id){
        generator.openNode();
        generator.compete(new AddAction2(),"className",TableConstant.likeNote)
                .compete(new AddAction3(),FieldConstant.id,id)
                .closeNode("");
    }

    public void competeLikeInsert(JsonGenerator generator,String email,NoteInfo info){
        generator.openNode();
        competeLikeInsertClassName(generator);
        competeLikeInsertField(generator,email,info);
        generator.closeNode("");
    }

    public void competeLikeQueryByNote(JsonGenerator generator, NoteInfo info){
        SelectConditionAction condition=new SelectConditionAction();
        generator.openNode();
        competeLikeQueryClassName(generator);
        competeLikeQueryField(generator);

        generator.openArray()
                .compete(condition,FieldConstant.email,NetMethod.INSTANCE.getUser(), TypeConstant.varchar,TableConstant.likeNote,"0")
                .compete(condition,FieldConstant.noteId,info.id,TypeConstant.varchar,TableConstant.likeNote,"0")
                .compete(condition,FieldConstant.areaName,info.mAreaName,TypeConstant.varchar,TableConstant.likeNote,"0")
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
