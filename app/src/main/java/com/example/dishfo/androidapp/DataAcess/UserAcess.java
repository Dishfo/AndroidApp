package com.example.dishfo.androidapp.DataAcess;

import android.util.Log;

import com.example.dishfo.androidapp.bean.UserInfo;
import com.example.dishfo.androidapp.mvp.FieldConstant;
import com.example.dishfo.androidapp.mvp.TableConstant;
import com.example.dishfo.androidapp.mvp.TypeConstant;
import com.example.dishfo.androidapp.netInterface.AddAction2;
import com.example.dishfo.androidapp.netInterface.JsonGenerator;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectClassNameAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectConditionAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectFieldsAction;
import com.example.dishfo.androidapp.netInterface.UpdateAction.UpdateConditionAction;
import com.example.dishfo.androidapp.netInterface.UpdateAction.UpdateFieldsAction;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.Call;


/**
 * Created by dishfo on 18-3-8.
 */

public class UserAcess {
    public static UserAcess INSTANCE=new UserAcess();
    private UserAcess(){

    }

    public Observable<UserInfo> getUserById(String email){
         Observable<JsonObject> observable=NetMethod.INSTANCE.generateObserable("query",(generator, args) -> {
            competeUserQueryByUser(generator, (String) args[0]);
        },email);

        return observable.map(object -> {
            Log.d("userinfo",object.toString());
           return NetMethod.INSTANCE.praseUser(object.get("result").getAsString());
        });
    }



    public Observable<JsonObject> updateUserInfoByUser(String email,UserInfo info){
        Observable<JsonObject> observable=NetMethod.INSTANCE.generateObserable("update",(generator, args) -> {
            competeUpdateUseByUser(generator, (String) args[0],(UserInfo)args[1]);
        },email,info);

        return observable;
    }


    public void competeUpdateUseByUser(JsonGenerator generator, String arg, UserInfo arg1) {
        AddAction2 action2=new AddAction2();
        UpdateFieldsAction field=new UpdateFieldsAction();
        UpdateConditionAction condition=new UpdateConditionAction();

        generator.openNode();
        generator.compete(action2,"className",TableConstant.user);
        generator.openArray()
                .compete(field,FieldConstant.name,arg1.name,TypeConstant.varchar)
                .compete(field,FieldConstant.head,arg1.head,TypeConstant.varchar)
                .closeNode("updateField");

        generator.openArray()
                .compete(condition,FieldConstant.email,arg,TypeConstant.varchar)
                .closeNode("select");

        generator.closeNode("");
        String json=generator.toJson();
    }


    public void competeUserQueryByUser(JsonGenerator generator, String email) {
        SelectClassNameAction classname=new SelectClassNameAction();
        SelectFieldsAction field=new SelectFieldsAction();
        SelectConditionAction condition=new SelectConditionAction();

        generator.openNode();
        generator.openArray()
                .compete(classname, TableConstant.user)
                .closeNode("className");

        generator.openArray()
                .compete(field, FieldConstant.email,TableConstant.user)
                .compete(field,FieldConstant.name,TableConstant.user)
                .compete(field,FieldConstant.head,TableConstant.user)
                .closeNode("field");

        generator.openArray()
                .compete(condition,FieldConstant.email,email, TypeConstant.varchar,TableConstant.user,"0")
                .closeNode("condition");
        generator.closeNode("");
    }

}
