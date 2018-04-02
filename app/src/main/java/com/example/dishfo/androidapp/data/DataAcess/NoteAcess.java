package com.example.dishfo.androidapp.data.DataAcess;

import android.util.Log;

import com.example.dishfo.androidapp.constant.FieldConstant;
import com.example.dishfo.androidapp.constant.TypeConstant;
import com.example.dishfo.androidapp.data.DataAcess.netInterface.AddAction2;
import com.example.dishfo.androidapp.data.DataAcess.netInterface.InsertValuesAction;
import com.example.dishfo.androidapp.data.DataAcess.netInterface.JsonGenerator;
import com.example.dishfo.androidapp.data.DataAcess.netInterface.SelectAction.SelectClassNameAction;
import com.example.dishfo.androidapp.data.DataAcess.netInterface.SelectAction.SelectConditionAction;
import com.example.dishfo.androidapp.data.DataAcess.netInterface.SelectAction.SelectFieldsAction;
import com.example.dishfo.androidapp.util.netMapBean.AreaWithNDMapping;
import com.example.dishfo.androidapp.util.netMapBean.NoteInfoMapping;
import com.example.dishfo.androidapp.bean.sqlBean.Area;
import com.example.dishfo.androidapp.bean.sqlBean.Note;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 *
 * Created by dishfo on 18-3-6.
 */

public class NoteAcess extends DataAcess{


    public  NoteAcess(NetMethod netMethod){
        super(netMethod);
    }

    public List<Note> getNoteByUser(String email,String areaName) throws IOException {

        Call<JsonObject> call=netMethod.generateCall("query",(generator, args) -> {
            competeNoteQueryByUser(generator,email,areaName);
        },email,areaName);

        Response<JsonObject> response=call.execute();
        JsonObject object=response.body();

        if(!netMethod.isSucceed(object)){
            return null;
        }

        return objectParse.getBeans(netMethod.getResult(object),
                Note.class,NoteInfoMapping.INSTANCE);
    }


    public List<Note> getNoteByArea(String areaName) throws IOException {
        Call<JsonObject> call=netMethod.generateCall("query",(generator, args) -> {
            competeNoteQuerybyArea(generator,(String)args[0]);
        },areaName);

        Response<JsonObject> response=call.execute();
        JsonObject object=response.body();
        int code=object==null?-1:object.get("code").getAsInt();
        if(code!=1){
            return null;
        }
        return objectParse.getBeans(object.get("result").getAsString(),
                Note.class,NoteInfoMapping.INSTANCE);
    }

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

    public Note insertNote(Note note,Area area) throws IOException {
        Call<JsonObject> call=netMethod.generateCall("insert",(generator, args) -> {
            competeNoteInsert(generator,(Note)args[0],(Area)args[1]);
        },note,area);

        Response<JsonObject> response=call.execute();
        JsonObject object=response.body();

        if(!netMethod.isSucceed(object)){
            return null;
        }
        return objectParse.getFromInsert(netMethod.getResult(object),
                Note.class,NoteInfoMapping.INSTANCE);
    }


    /**
     * 用于构建note 查询的json语句
     * @param generator
     * @param table
     */

    private void competeNoteQueryClassName(JsonGenerator generator,String table){
        SelectClassNameAction classname=new SelectClassNameAction();
        Log.d("test","error:" + table);
        generator.openArray()
                .compete(classname, table)
                .closeNode(CLASS_NAME);
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
                .closeNode(FIELD);
    }

    public void competeNoteQuerybyArea(JsonGenerator generator,String areaName){
        generator.openNode();
        String table=AreaWithNDMapping.INSTANCE.getNote(areaName);
        competeNoteQueryClassName(generator,table);
        competeNoteQueryField(generator,table);
        SelectConditionAction condition=new SelectConditionAction();
        generator.openArray()
                .closeNode(CONDITION);
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
                .closeNode(CONDITION);
        generator.closeNode("");
    }

    public void competeNoteQueryByUser(JsonGenerator generator,String email,String note){
        SelectConditionAction condition=new SelectConditionAction();

        generator.openNode();
        competeNoteQueryClassName(generator,note);
        competeNoteQueryField(generator,note);
        generator.openArray()
                .compete(condition,FieldConstant.email,email,TypeConstant.varchar,note,"0")
                .closeNode(CONDITION);
        generator.closeNode("");
    }

    private void competeNoteInsertClassName(JsonGenerator generator,String name){
        AddAction2 action2=new AddAction2();
        generator.compete(action2,CLASS_NAME,name);
    }

    private void competeNoteInsertField(JsonGenerator generator,Note note){
        InsertValuesAction insert=new InsertValuesAction();
        generator.openArray()
                .compete(insert,FieldConstant.id,"",TypeConstant.varchar,"primarykey")
                .compete(insert,FieldConstant.email,note.getEmail(),TypeConstant.varchar,"")
                .compete(insert,FieldConstant.content,note.getContent(),TypeConstant.varchar,"")
                .compete(insert,FieldConstant.images,netMethod.parseList(note.getImages()),
                        TypeConstant.varchar,"")
                .compete(insert,FieldConstant.appreciateNumber,"0",TypeConstant.integer,"")
                .compete(insert,FieldConstant.readNumber,"0",TypeConstant.integer,"")
                .compete(insert,FieldConstant.discussNumber,"0",TypeConstant.integer,"")
                .compete(insert,FieldConstant.time,"",TypeConstant.varchar,"")
                .compete(insert,FieldConstant.areaId,note.getAreaId(),TypeConstant.varchar,"")
                .closeNode(VALUES);

    }

    public void competeNoteInsert(JsonGenerator generator,Note note,Area area){
        String noteName= AreaWithNDMapping.INSTANCE.getNote(area.getName());
        generator.openNode();
        competeNoteInsertClassName(generator,noteName);
        competeNoteInsertField(generator,note);
        generator.closeNode("");
    }
}
