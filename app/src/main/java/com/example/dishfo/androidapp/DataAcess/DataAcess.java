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

    public void generateDelete(JsonGenerator generator, String className, String id){
        generator.openNode();
        generator.compete(new AddAction2(),"className", className)
                .compete(new AddAction3(), FieldConstant.id,id)
                .closeNode("");
    }

}
