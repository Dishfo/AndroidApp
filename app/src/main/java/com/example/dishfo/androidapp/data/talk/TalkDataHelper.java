package com.example.dishfo.androidapp.data.talk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dishfo on 18-3-17.
 */

public class TalkDataHelper extends SQLiteOpenHelper {

    public static final String DB_NAME="user.db";
    public static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE="CREATE TABLE"+
            TalkdbContract.TABLE_NAME+"("+
            TalkdbContract._ID+"intger primary key autoincrement,"+
            TalkdbContract.MESSAGE_COLUMN+"text,"+
            TalkdbContract.EMAIL_COLUMN+"text,"+
            TalkdbContract.TIME_COLUMN+"text,"+
            TalkdbContract.OTHERUSER_COLUMN+"text,"+
            TalkdbContract.OTHER_COLUMN+"bool"+
            ");";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TalkdbContract.TABLE_NAME;

    public TalkDataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
