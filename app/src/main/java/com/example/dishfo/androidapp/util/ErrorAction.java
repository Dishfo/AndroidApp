package com.example.dishfo.androidapp.util;

/**
 * Created by dishfo on 18-3-6.
 */

@FunctionalInterface
public interface ErrorAction {
    public void onError(Object...args);
}
