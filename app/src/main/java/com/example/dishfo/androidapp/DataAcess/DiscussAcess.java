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
import com.google.gson.JsonObject;

import io.reactivex.Observable;

/**
 * Created by dishfo on 18-3-7.
 */

public class DiscussAcess {
    public static DiscussAcess INSTANCE=new DiscussAcess();

    private DiscussAcess(){

    }

    public Observable<JsonObject> getDiscussByUser(String email,String discuss){
        return NetMethod.INSTANCE.generateObserable("query",(generator, args) -> {
            NetMethod.INSTANCE.competeDiscussByUser(generator, (String) args[0], (String) args[1]);
        },email,discuss);
    }

    public Observable<JsonObject> getDiscussByNote(NoteInfo noteId,String discuss){
        return NetMethod.INSTANCE.generateObserable("query",(generator, args) -> {
            competeDiscussQueryByNote(generator,(NoteInfo)args[0] , (String) args[1]);
        },noteId,discuss);
    }

    public Observable<JsonObject> insertDiscuss(String email,NoteInfo info,DiscussInfo discussInfo,AreaInfo areaInfo){
        return NetMethod.INSTANCE.generateObserable("insert",(generator, args) -> {
            competeDiscussInsert(generator, (NoteInfo) args[0], (DiscussInfo) args[1], (AreaInfo) args[2]);
        },info,discussInfo,areaInfo);
    }


    private void competeDiscussQueryField(JsonGenerator generator,String discussarea){
        SelectFieldsAction field=new SelectFieldsAction();
        generator.openArray()
                .compete(field,FieldConstant.email, TableConstant.user)
                .compete(field,FieldConstant.head, TableConstant.user)
                .compete(field,FieldConstant.name,TableConstant.user)
                .compete(field,FieldConstant.oldContent,discussarea)
                .compete(field,FieldConstant.content,discussarea)
                .compete(field,FieldConstant.images,discussarea)
                .compete(field,FieldConstant.time,discussarea)
                .closeNode("field");
    }

    private void competeDiscussQueryClassName(JsonGenerator generator,String discussarea){
        SelectClassNameAction classname=new SelectClassNameAction();
        generator.openArray()
                .compete(classname,discussarea)
                .compete(classname,TableConstant.user)
                .closeNode("className");
    }

    public void competeDiscussQueryByNote(JsonGenerator generator,NoteInfo info,String discuss){
        SelectConditionAction condition=new SelectConditionAction();
        generator.openNode();
        competeDiscussQueryClassName(generator,discuss);
        competeDiscussQueryField(generator,discuss);

        String useremail=TableConstant.user+"."+FieldConstant.email;
        String fllowAreaEmail=TableConstant.followArea+"."+FieldConstant.email;
        String fllowAreaId=TableConstant.followArea+"."+FieldConstant.followAreaId;
        generator.openArray()
                .compete(condition,FieldConstant.noteId,info.id,TypeConstant.varchar,discuss,"0")
                .compete(condition,FieldConstant.email,useremail,TypeConstant.varchar,discuss,"1")
                .closeNode("condition");
        generator.closeNode("");
    }

    private void competeDiscussInsertClassName(JsonGenerator generator,String discuss){
        generator.compete(new AddAction2(),"className",discuss);
    }

    private void competeDiscussInsertField(JsonGenerator generator, NoteInfo info, DiscussInfo discussInfo){
        InsertValuesAction insert=new InsertValuesAction();
        generator.openArray()
                .compete(insert, FieldConstant.id,"", TypeConstant.varchar,"primarykey,autoincrement")
                .compete(insert,FieldConstant.email,NetMethod.INSTANCE.getUser(),TypeConstant.varchar,"")
                .compete(insert,FieldConstant.content,discussInfo.mReplayContent,TypeConstant.varchar,"")
                .compete(insert,FieldConstant.images,NetMethod.INSTANCE.parseList(discussInfo.mImageUrls),TypeConstant.varchar,"")
                .compete(insert,FieldConstant.oldUserName,info.mNickName,TypeConstant.varchar,"")
                .compete(insert,FieldConstant.oldContent,info.mContent,TypeConstant.varchar,"")
                .compete(insert,FieldConstant.oldImages,info.mImageUrl.toString(),TypeConstant.varchar,"")
                .compete(insert,FieldConstant.time,"",TypeConstant.varchar,"")
                .compete(insert,FieldConstant.noteId,info.id,TypeConstant.varchar,"");
        generator.closeNode("values");

    }

    public void competeDiscussInsert(JsonGenerator generator, NoteInfo info, DiscussInfo discussInfo, AreaInfo areaInfo){
        generator.openNode();
        competeDiscussInsertClassName(generator, AreaWithNDMapping.INSTANCE.getDiscuss(areaInfo.name));
        competeDiscussInsertField(generator,info,discussInfo);
        generator.closeNode("");
    }


}
