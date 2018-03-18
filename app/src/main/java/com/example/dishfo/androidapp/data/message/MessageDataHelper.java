package com.example.dishfo.androidapp.data.message;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dishfo.androidapp.data.talk.TalkdbContract;

/**
 * Created b y dishfo on 18-3-17.
 */

public class MessageDataHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE="CREATE TABLE"+
            MessagedbContract.TABLE_NAME+"("+
            MessagedbContract._ID+"intger primary key autoincrement,"+
            MessagedbContract.MESSAGE_COLUMN+"text,"+
            MessagedbContract.EMAIL_COLUMN+"text,"+
            MessagedbContract.TIME_COLUMN+"text,"+
            MessagedbContract.SEND_COLUMN+"text,"+
            MessagedbContract.NAME_COLUMN+"text"+
            ");";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TalkdbContract.TABLE_NAME;

    public MessageDataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(CREATE_TABLE);
    }
}
