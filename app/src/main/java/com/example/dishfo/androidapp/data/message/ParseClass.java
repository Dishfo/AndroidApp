package com.example.dishfo.androidapp.data.message;

import android.database.Cursor;

/**
 * Created by dishfo on 18-3-17.
 */

public interface ParseClass<T>{
    public T parse(Cursor cursor);
}
