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
import com.example.dishfo.androidapp.netbean.NoteInfoMapping;
import com.example.dishfo.androidapp.sqlBean.Note;
import com.example.dishfo.androidapp.util.Action;
import com.example.dishfo.androidapp.util.CompeteAction;
import com.example.dishfo.androidapp.util.ErrorAction;
import com.example.dishfo.androidapp.util.JsonObjectParse;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by dishfo on 18-3-6.
 */

public class NoteAcess {
    NetMethod netMethod=null;
    JsonObjectParse objectParse;

    public  NoteAcess(NetMethod netMethod){
        this.netMethod=netMethod;
        objectParse=new JsonObjectParse();
    }

//    public Observable<JsonObject> getNotesById(String name,String id){;
//        return NetMethod.INSTANCE.generateObserable("query",(generator, args) -> {
//            competeNoteQuerybyId(generator,(String) args[0],(String) args[1]);
//        },name,id);
//    }
//
//    public Observable<JsonObject> getNotesByArea(String areaName){
//        return NetMethod.INSTANCE.generateObserable("query",(generator, args) -> {
//            competeNoteQuerybyArea(generator,(String) args[0]);
//        },areaName);
//    }
//
//    public void getNotesByLike(){
//
//
//    }
//
//    public Observable<JsonObject> getNoteByUser(String email,String note){
//        return NetMethod.INSTANCE.generateObserable("query",(generator, args) -> {
//            competeNoteQueryByUser(generator, (String) args[0], (String) args[1]);
//        },email,note);
//    }
//
//    public Observable<JsonObject> addNewNote(NoteInfo noteInfo,AreaInfo areaInfo){
//        return NetMethod.INSTANCE.generateObserable("insert",(generator, args) -> {
//            competeNoteInsert(generator,(NoteInfo) args[0],(AreaInfo)args[1]);
//        },noteInfo,areaInfo);
//    }
//
    public Note getNoteById(String id,String areaName)throws IOException{
        Call<JsonObject> call=netMethod.generateCall("query",(generator, args) -> {
            competeNoteQuerybyId(generator,(String) args[0],(String) args[1]);
        },id,areaName);

        Response<JsonObject> response=call.execute();
        JsonObject object=response.body();

        int code=object.get("code").getAsInt();
        if(code!=1) {
            return null;
        }

        List<Note> notes=objectParse.getBeans(object.get("result").getAsString()
                ,Note.class, NoteInfoMapping.INSTANCE);
        if(notes==null||notes.size()==0) {
            return null;
        }

        return notes.get(0);
    }


    /**
     * 用于构建note 查询的json语句
     * @param generator
     * @param table
     */


    private void competeNoteQueryClassName(JsonGenerator generator,String table){
        SelectClassNameAction classname=new SelectClassNameAction();
        generator.openArray()
                .compete(classname, table)
                .closeNode("className");
    }

    private void competeNoteQueryField(JsonGenerator generator,String table){
        SelectFieldsAction field=new SelectFieldsAction();
        generator.openArray()
                .compete(field,FieldConstant.id,table)
                .compete(field,FieldConstant.email,table)
                .compete(field,FieldConstant.content,table)
                .compete(field,FieldConstant.images,table)
                .compete(field,FieldConstant.appreciateNumber,table)
                .compete(field,FieldConstant.readNumber,table)
                .compete(field,FieldConstant.discussNumber,table)
                .compete(field,FieldConstant.time,table)
                .compete(field,FieldConstant.areaId,table)
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

    public void competeNoteQuerybyId(JsonGenerator generator,String id,String areaName){
        String table=AreaWithNDMapping.INSTANCE.getNote(areaName);
        generator.openNode();
        competeNoteQueryClassName(generator,table);
        competeNoteQueryField(generator,table);
        SelectConditionAction condition=new SelectConditionAction();
        generator.openArray()
                .compete(condition, FieldConstant.id,id, TypeConstant.varchar,table,"0")
                .closeNode("condition");
        generator.closeNode("");
    }



//    public void competeNoteQuerybyId(JsonGenerator generator,String name,String noteId){
//        generator.openNode();
//        competeNoteQueryClassName(generator,name);
//        competeNoteQueryField(generator,name);
//        SelectConditionAction condition=new SelectConditionAction();
//        generator.openArray()
//                .compete(condition, FieldConstant.email,"user.email", TypeConstant.varchar,name,"1")
//                .compete(condition, FieldConstant.id,noteId, TypeConstant.varchar,name,"0")
//                .closeNode("condition");
//        generator.closeNode("");
//    }

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
