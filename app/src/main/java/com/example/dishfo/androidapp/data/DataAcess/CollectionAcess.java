package com.example.dishfo.androidapp.data.DataAcess;

import com.example.dishfo.androidapp.constant.FieldConstant;
import com.example.dishfo.androidapp.constant.TableConstant;
import com.example.dishfo.androidapp.constant.TypeConstant;
import com.example.dishfo.androidapp.netInterface.JsonGenerator;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectClassNameAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectConditionAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectFieldsAction;
import com.example.dishfo.androidapp.netMapBean.CollectionMapping;
import com.example.dishfo.androidapp.sqlBean.Collection;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by dishfo on 18-3-7.
 */

public class CollectionAcess extends DataAcess{

    public CollectionAcess(NetMethod netMethod){
        super(netMethod);
    }

    public List<Collection> getCollections(String email) throws IOException {
        Call<JsonObject> call=netMethod.generateCall("query",(generator, args) -> {
            competeCollectionQueryByUser(generator,(String)args[0]);
        },email);

        Response<JsonObject> response=call.execute();
        JsonObject object=response.body();
        if(!netMethod.isSucceed(object)){
            return null;
        }

        return objectParse.getBeans(netMethod.getResult(object),
                Collection.class, CollectionMapping.INSTANCE);
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
