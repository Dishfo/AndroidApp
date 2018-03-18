package com.example.dishfo.androidapp.util;

import com.example.dishfo.androidapp.netInterface.JsonGenerator;

/**
 * Created by dishfo on 18-3-7.
 */

@FunctionalInterface
public interface JsonAction {
    public void competeJson(JsonGenerator generator,Object[] args);
}
