package com.example.dishfo.androidapp.DataAcess;

import android.util.Log;

import com.example.dishfo.androidapp.bean.AreaInfo;
import com.example.dishfo.androidapp.mvp.FieldConstant;
import com.example.dishfo.androidapp.mvp.TableConstant;
import com.example.dishfo.androidapp.mvp.TypeConstant;
import com.example.dishfo.androidapp.netInterface.JsonGenerator;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectClassNameAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectConditionAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectFieldsAction;
import com.example.dishfo.androidapp.util.Action;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by dishfo on 18-3-6.
 */

public class AreaAcess {
    public static AreaAcess INSTANCE=new AreaAcess();

    private AreaAcess(){

    }



    public Observable<JsonObject> getAreaByName(String name){
        return NetMethod.INSTANCE.generateObserable("query",(generator, args) -> {
            competeAreaQuerybyName(generator,(String)args[0]);
        },name);
    }

    private void competeAreaQueryClassName(JsonGenerator generator){
        SelectClassNameAction classname=new SelectClassNameAction();
        generator.openArray()
                .compete(classname, TableConstant.Area)
                .closeNode("className");
    }

    private void competeAreaQueryField(JsonGenerator generator){
        SelectFieldsAction field=new SelectFieldsAction();
        generator.openArray()
                .compete(field, FieldConstant.id,TableConstant.Area)
                .compete(field,FieldConstant.head,TableConstant.Area)
                .compete(field,FieldConstant.name,TableConstant.Area)
                .closeNode("field");
    }


    public void competeAreaQuerybyName(JsonGenerator generator,String name){

        SelectConditionAction condition=new SelectConditionAction();

        generator.openNode();
        competeAreaQueryClassName(generator);
        competeAreaQueryField(generator);
        generator.openArray()
                .compete(condition,FieldConstant.name,name, TypeConstant.varchar,TableConstant.Area,"0")
                .closeNode("condition");
        generator.closeNode("");
    }

}
