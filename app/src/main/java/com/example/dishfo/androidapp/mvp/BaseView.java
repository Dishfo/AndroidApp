package com.example.dishfo.androidapp.mvp;

/**
 * Created by dishfo on 18-1-22.
 */

public interface BaseView <T>{

    public void setPresent(T present);
    public void waitToCompete();
    public void compete(Object...args);
    public void error(Object...args);
}
