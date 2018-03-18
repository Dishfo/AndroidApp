package com.example.dishfo.androidapp.data;

/**
 * Created by dishfo on 18-3-17.
 */

public class SqlArgs<T> {
    public static final int SQL_STRING=0X4;
    public static final int SQL_OBJ=0X5;

    private int type;
    private String string;
    private T obj;

    public int arraylength;

    public int getType() {
        return type;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public T getObj() {
        return obj;
    }
}
