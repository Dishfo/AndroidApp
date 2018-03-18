package com.example.dishfo.androidapp.data.talk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dishfo.androidapp.data.DataBaseAccess;
import com.example.dishfo.androidapp.data.SqlArgs;
import com.example.dishfo.androidapp.data.message.ParseClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dishfo on 18-3-17.
 */

public final class TalkDataBase implements DataBaseAccess<TalkEntity> ,ParseClass<TalkEntity>{
    Context context;
    SQLiteOpenHelper helper;
    private static TalkDataBase INSTANCE=null;

    static String querySelects=" where"+TalkdbContract.EMAIL_COLUMN+"=?,"+
            TalkdbContract.OTHERUSER_COLUMN+"=?"+" order by time;";

    public TalkDataBase(Context context) {
        this.context = context;
        helper=new TalkDataHelper(context,TalkDataHelper.DB_NAME,null,1);
    }

    public static void init(Context context){
        INSTANCE=new TalkDataBase(context);
    }

    public static TalkDataBase getInstance(){
        return INSTANCE;
    }

    @Override
    public SQLiteDatabase open() {
        SQLiteDatabase database=helper.getReadableDatabase();
        return database;
    }

    @Override
    public void close(SQLiteDatabase database) {
        database.close();
    }

    public List<TalkEntity> getData(String uid,String mid) {
        SqlArgs args=new SqlArgs();
        args.setObj(new String[]{mid,uid});
        return getData(args);
    }

    @Override
    public List<TalkEntity> getData(SqlArgs<?> queryArgs) {
        SQLiteDatabase database=open();
        Object obj=queryArgs.getObj();
        if(obj instanceof String[]){
            throw new IllegalArgumentException("this query need uid mid be the args");
        }
        String[] array= (String[]) obj;
        Cursor cursor=database.rawQuery("select * from "+TalkdbContract.TABLE_NAME+querySelects,
               new String[]{array[0],array[1]});
        ArrayList<TalkEntity> entities=new ArrayList<>();

        while (cursor.moveToNext()){
            entities.add(parse(cursor));
        }
        cursor.close();
        database.close();
        return entities;
    }

    @Override
    public long insertData(TalkEntity data) {
        SQLiteDatabase database=open();
        ContentValues values=new ContentValues();
        values.put(TalkdbContract.EMAIL_COLUMN,data.getEmail());
        values.put(TalkdbContract.MESSAGE_COLUMN,data.getMessage());
        values.put(TalkdbContract.OTHER_COLUMN,data.isOther());
        values.put(TalkdbContract.TIME_COLUMN,data.getTime());
        values.put(TalkdbContract.OTHERUSER_COLUMN,data.getOtherUser());
        long id=database.insert(TalkdbContract.TABLE_NAME,null,values);
        database.close();
        return id;
    }

    public TalkEntity parse(Cursor cursor){
        boolean other=isTrue(cursor.getBlob(cursor.getColumnIndex(TalkdbContract.OTHER_COLUMN))[0]);
        String email=cursor.getString(cursor.getColumnIndex(TalkdbContract.EMAIL_COLUMN));
        String message=cursor.getString(cursor.getColumnIndex(TalkdbContract.MESSAGE_COLUMN));
        String otheruser=cursor.getString(cursor.getColumnIndex(TalkdbContract.OTHERUSER_COLUMN));
        String time=cursor.getString(cursor.getColumnIndex(TalkdbContract.TIME_COLUMN));
        String id=cursor.getString(cursor.getColumnIndex(TalkdbContract._ID));

        TalkEntity entity=new TalkEntity();
        entity.setId(id);
        entity.setEmail(email);
        entity.setOtherUser(otheruser);
        entity.setOther(other);
        entity.setMessage(message);
        entity.setTime(time);
        return entity;
    }

    private boolean isTrue(Byte b){
        if(b==1){
            return true;
        }else {
            return false;
        }
    }
}
