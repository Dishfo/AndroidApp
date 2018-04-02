package com.example.dishfo.androidapp.mvp;

/**
 *
 * Created by dishfo on 18-1-22.
 */

public interface BaseModel<T>{
    public static final String HOST_IP="172.16.188.1";
    public static final String HOST="http://"+HOST_IP+":8080/test/";
    void setPresent(T present);
    void setArgs(Object...args);
    void stop();
    void compete(Object...args);
    void error(Object...args);
}
