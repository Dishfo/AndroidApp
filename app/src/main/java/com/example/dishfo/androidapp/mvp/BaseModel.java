package com.example.dishfo.androidapp.mvp;

import android.provider.Telephony;

/**
 *
 * Created by dishfo on 18-1-22.
 */

public interface BaseModel<T>{
    public static final String HOST_IP="222.196.255.31";
    public static final String HOST="http://"+HOST_IP+":8080/test/";
    public void setPresent(T present);
    public void setArgs(Object...args);
    public void stop();
    public void compete(Object...args);
    public void error(Object...args);
}
