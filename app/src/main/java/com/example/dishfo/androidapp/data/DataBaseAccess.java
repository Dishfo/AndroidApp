package com.example.dishfo.androidapp.data;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by dishfo on 18-3-17.
 */

public interface DataBaseAccess<T> {
    public SQLiteDatabase open();
    public void close(SQLiteDatabase database);

    public List<T> getData(SqlArgs<?> args);
    public long insertData(T data);
}
