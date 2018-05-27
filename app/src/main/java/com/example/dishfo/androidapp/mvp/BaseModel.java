package com.example.dishfo.androidapp.mvp;

/**
 *
 * Created by dishfo on 18-1-22.
 */

public interface BaseModel<T>{
    String HOST_IP="10.5.60.73";
    String HOST="http://"+HOST_IP+":8080/test/";
    void setPresent(T present);
    void setArgs(Object...args);
    void stop();
    void compete(Object...args);
    void error(Object...args);
}
