package com.example.dishfo.androidapp.DataAcess;

import android.util.Log;

import com.example.dishfo.androidapp.bean.AreaInfo;
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
import com.example.dishfo.androidapp.util.Action;
import com.example.dishfo.androidapp.util.CompeteAction;
import com.example.dishfo.androidapp.util.ErrorAction;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by dishfo on 18-3-6.
 */

public class NoteAcess {
    public static NoteAcess INSTANCE=new NoteAcess();


    private NoteAcess(){

    }

    public Observable<JsonObject> getNotesById(String name,String id){;
        return NetMethod.INSTANCE.generateObserable("query",(generator, args) -> {
            competeNoteQuerybyId(generator,(String) args[0],(String) args[1]);
        },name,id);
    }

    public Observable<JsonObject> getNotesByArea(String areaName){
        return NetMethod.INSTANCE.generateObserable("query",(generator, args) -> {
            competeNoteQuerybyArea(generator,(String) args[0]);
        },areaName);
    }

    public void getNotesByLike(){


    }

    public Observable<JsonObject> getNoteByUser(String email,String note){
        return NetMethod.INSTANCE.generateObserable("query",(generator, args) -> {
            competeNoteQueryByUser(generator, (String) args[0], (String) args[1]);
        },email,note);
    }

    public Observable<JsonObject> addNewNote(NoteInfo noteInfo,AreaInfo areaInfo){
        return NetMethod.INSTANCE.generateObserable("insert",(generator, args) -> {
            competeNoteInsert(generator,(NoteInfo) args[0],(AreaInfo)args[1]);
        },noteInfo,areaInfo);
    }



    private void competeNoteQueryClassName(JsonGenerator generator,String name){
        SelectClassNameAction classname=new SelectClassNameAction();
        generator.openArray()
                .compete(classname, name)
                .compete(classname, TableConstant.Area)
                .compete(classname,TableConstant.user)
                .closeNode("className");
    }

    private void competeNoteQueryField(JsonGenerator generator,String name){
        SelectFieldsAction field=new SelectFieldsAction();
        generator.openArray()
                .compete(field, FieldConstant.head,TableConstant.user)
                .compete(field,FieldConstant.id,name)
                .compete(field,FieldConstant.name,TableConstant.user)
                .compete(field,FieldConstant.name,TableConstant.Area)
                .compete(field,FieldConstant.time,name)
                .compete(field,FieldConstant.email,name)
                .compete(field,FieldConstant.images,name)
                .compete(field,FieldConstant.appreciateNumber,name)
                .compete(field,FieldConstant.readNumber,name)
                .compete(field,FieldConstant.discussNumber,name)
                .compete(field,FieldConstant.head,TableConstant.user)
                .compete(field,FieldConstant.content,name)
                .closeNode("field");
    }

    public void competeNoteQuerybyArea(JsonGenerator generator,String name){
        generator.openNode();
        String areaName=AreaWithNDMapping.INSTANCE.getNote(name);
        competeNoteQueryClassName(generator,areaName);
        competeNoteQueryField(generator,areaName);
        SelectConditionAction condition=new SelectConditionAction();
        generator.openArray()
                .compete(condition, FieldConstant.email,"user.email", TypeConstant.varchar,areaName,"1")
                .compete(condition,FieldConstant.areaId,"Area.id",TypeConstant.varchar,areaName,"1")
                .closeNode("condition");
        generator.closeNode("");
    }

    public void competeNoteQuerybyId(JsonGenerator generator,String name,String noteId){
        generator.openNode();
        competeNoteQueryClassName(generator,name);
        competeNoteQueryField(generator,name);
        SelectConditionAction condition=new SelectConditionAction();
        generator.openArray()
                .compete(condition, FieldConstant.email,"user.email", TypeConstant.varchar,name,"1")
                .compete(condition, FieldConstant.id,noteId, TypeConstant.varchar,name,"0")
                .closeNode("condition");
        generator.closeNode("");
    }

    public void competeNoteQueryByUser(JsonGenerator generator,String email,String note){
        SelectConditionAction condition=new SelectConditionAction();

        generator.openNode();
        competeNoteQueryClassName(generator,note);
        competeNoteQueryField(generator,note);
        generator.openArray()
                .compete(condition,FieldConstant.email,email,TypeConstant.varchar,note,"0")
                .closeNode("condition");
        generator.closeNode("");
    }

    public void competeNoteInsert(JsonGenerator generator,NoteInfo noteInfo,AreaInfo areaInfo){
        AddAction2 action2=new AddAction2();
        String noteName= AreaWithNDMapping.INSTANCE.getNote(areaInfo.name);
        InsertValuesAction insert=new InsertValuesAction();
        generator.openNode();
        generator.compete(action2,"className",noteName)
                .openArray()
                .compete(insert,FieldConstant.id,"",TypeConstant.varchar,"primarykey")
                .compete(insert,FieldConstant.email,NetMethod.INSTANCE.getUser(),TypeConstant.varchar,"")
                .compete(insert,FieldConstant.content,noteInfo.mContent,TypeConstant.varchar,"")
                .compete(insert,FieldConstant.images,NetMethod.INSTANCE.parseList(noteInfo.mImageUrl),TypeConstant.varchar,"")
                .compete(insert,FieldConstant.appreciateNumber,"0",TypeConstant.integer,"")
                .compete(insert,FieldConstant.readNumber,"0",TypeConstant.integer,"")
                .compete(insert,FieldConstant.discussNumber,"0",TypeConstant.integer,"")
                .compete(insert,FieldConstant.time,"",TypeConstant.varchar,"")
                .compete(insert,FieldConstant.areaId,areaInfo.id,TypeConstant.varchar,"")
                .closeNode("values");
        generator.closeNode("");

    }

}
