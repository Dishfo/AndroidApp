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
import com.example.dishfo.androidapp.netbean.UserInfoMapping;
import com.example.dishfo.androidapp.sqlBean.User;
import com.example.dishfo.androidapp.util.JsonObjectParse;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.PUT;


/**
 * Created by dishfo on 18-3-8.
 */

public class UserAcess {
    public NetMethod netMethod;

    public UserAcess(NetMethod netMethod){
        this.netMethod=netMethod;
    }


    public List<User> getUser(String email) throws IOException {
        Call<JsonObject> objectCall= netMethod.generateCall("query",(generator, args) -> {
            competeUserQueryByUser(generator, (String) args[0]);
        },email);

        Response<JsonObject> response=objectCall.execute();
        JsonObject body=response.body();
        int code=body.get("code").getAsInt();
        if(code!=1){
            return null;
        }
        return new JsonObjectParse().getBeans(body.get("result").getAsString(),
                User.class, UserInfoMapping.INSTANCE);
    }

    public boolean updateUser(User user) throws IOException {
        if(user.getEmail()==null){
            return false;
        }

        Call<JsonObject> objectCall= netMethod.generateCall("update",(generator, args) -> {
            competeUpdateUseByUser(generator, (User) args[0]);
        },user);

        Response<JsonObject> response=objectCall.execute();
        JsonObject object=response.body();
        int code=object.get("code").getAsInt();
        if(code==1){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 用于构建user josn查询语句
     * @param generator
     * @param user
     */

    public void competeUpdateUseByUser(JsonGenerator generator, User user) {
        AddAction2 action2=new AddAction2();
        UpdateFieldsAction field=new UpdateFieldsAction();
        UpdateConditionAction condition=new UpdateConditionAction();

        generator.openNode();
        generator.compete(action2,"className",TableConstant.user);
        generator.openArray()
                .compete(field,FieldConstant.name,user.getName(),TypeConstant.varchar)
                .compete(field,FieldConstant.head,user.getHeadUrl(),TypeConstant.varchar)
                .closeNode("updateField");

        generator.openArray()
                .compete(condition,FieldConstant.email,user.getEmail(),TypeConstant.varchar)
                .closeNode("select");

        generator.closeNode("");
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
