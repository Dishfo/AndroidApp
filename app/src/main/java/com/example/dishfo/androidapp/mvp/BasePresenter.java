package com.example.dishfo.androidapp.mvp;

/**
 * Created by dishfo on 18-1-22.
 */

public interface BasePresenter {

    public void start(Object... args);
    public void stop();
    public void onCompete(Object...args);
    public void onError(Object...args);
}
