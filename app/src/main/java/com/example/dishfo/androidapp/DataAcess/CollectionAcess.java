package com.example.dishfo.androidapp.DataAcess;

import com.example.dishfo.androidapp.mvp.FieldConstant;
import com.example.dishfo.androidapp.mvp.TableConstant;
import com.example.dishfo.androidapp.mvp.TypeConstant;
import com.example.dishfo.androidapp.netInterface.JsonGenerator;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectClassNameAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectConditionAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectFieldsAction;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by dishfo on 18-3-7.
 */

public class CollectionAcess {
    public static CollectionAcess INSTANCE=new CollectionAcess();

    private CollectionAcess(){

    }

    public Observable<JsonObject> getCollectionByUser(String email){
        return NetMethod.INSTANCE.generateObserable("query",(generator, args) -> {
            competeCollectionQueryByUser(generator, (String) args[0]);
        },email);
    }

    public void competeCollectionQueryByUser(JsonGenerator generator,String email){
        SelectClassNameAction classname=new SelectClassNameAction();
        SelectFieldsAction field=new SelectFieldsAction();
        SelectConditionAction condition=new SelectConditionAction();

        generator.openNode();
        generator.openArray()
                .compete(classname, TableConstant.collection)
                .closeNode("className");

        generator.openArray()
                .compete(field, FieldConstant.noteId,TableConstant.collection)
                .closeNode("field");

        generator.openArray()
                .compete(condition,FieldConstant.email,email, TypeConstant.varchar,TableConstant.collection,"0")
                .closeNode("condition");
        generator.closeNode("");
    }

}
