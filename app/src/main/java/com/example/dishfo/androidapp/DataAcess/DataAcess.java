package com.example.dishfo.androidapp.DataAcess;

import com.example.dishfo.androidapp.mvp.FieldConstant;
import com.example.dishfo.androidapp.mvp.TableConstant;
import com.example.dishfo.androidapp.netInterface.AddAction2;
import com.example.dishfo.androidapp.netInterface.AddAction3;
import com.example.dishfo.androidapp.netInterface.JsonGenerator;

/**
 * Created by dishfo on 18-3-12.
 */

public class DataAcess {

    protected static final String CLASS_NAME="className";
    protected static final String VALUES="values";
    protected static final String CONDITION="condition";
    protected static final String FIELD="field";


    public void generateDelete(JsonGenerator generator, String className, String id){
        generator.openNode();
        generator.compete(new AddAction2(),"className", className)
                .compete(new AddAction3(), FieldConstant.id,id)
                .closeNode("");
    }

}
