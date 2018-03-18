package com.example.dishfo.androidapp.data;

/**
 * Created by dishfo on 18-3-16.
 */

public interface DataModule {
    public <T> T load(String args);
    public <T> T save(T data);
}















