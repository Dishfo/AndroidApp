package com.example.dishfo.androidapp.data.DataAcess;

import com.example.dishfo.androidapp.constant.FieldConstant;
import com.example.dishfo.androidapp.constant.TableConstant;
import com.example.dishfo.androidapp.constant.TypeConstant;
import com.example.dishfo.androidapp.data.DataAcess.netInterface.JsonGenerator;
import com.example.dishfo.androidapp.data.DataAcess.netInterface.SelectAction.SelectClassNameAction;
import com.example.dishfo.androidapp.data.DataAcess.netInterface.SelectAction.SelectConditionAction;
import com.example.dishfo.androidapp.data.DataAcess.netInterface.SelectAction.SelectFieldsAction;

/**
 * Created by dishfo on 18-3-7.
 */

public class FansAcess {

    public  static FansAcess INSTANCE=new FansAcess();

    private FansAcess(){

    }

    private void competeFansQueryClassName(JsonGenerator generator){
        SelectClassNameAction classname=new SelectClassNameAction();
        generator.openArray()
                .compete(classname, TableConstant.fans)
                .closeNode("className");
    }



    public void competeFansQueryByUser(JsonGenerator generator, String email) {
        SelectClassNameAction classname=new SelectClassNameAction();
        SelectFieldsAction field=new SelectFieldsAction();
        SelectConditionAction condition=new SelectConditionAction();

        generator.openNode();


        generator.openArray()
                .compete(field, FieldConstant.id,TableConstant.fans)
                .closeNode("field");

        generator.openArray()
                .compete(condition,FieldConstant.email,email, TypeConstant.varchar,TableConstant.fans,"0")
                .closeNode("condition");
        generator.closeNode("");
    }


}
