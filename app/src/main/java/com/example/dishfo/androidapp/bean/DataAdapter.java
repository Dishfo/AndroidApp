package com.example.dishfo.androidapp.bean;

/**
 * 用于将sqlbean 转化为viewbean
 * Created by dishfo on 18-4-2.
 */

public interface DataAdapter<F,T> {
    T convert(F src);
}
