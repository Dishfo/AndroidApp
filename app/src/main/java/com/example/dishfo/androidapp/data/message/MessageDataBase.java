package com.example.dishfo.androidapp.data.message;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dishfo.androidapp.data.DataBaseAccess;
import com.example.dishfo.androidapp.data.SqlArgs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dishfo on 18-3-17.
 */

public class MessageDataBase implements DataBaseAccess<MessageEntity>
        ,ParseClass<MessageEntity>{

    public static MessageDataBase INSTANCE;

    Context context;
    SQLiteOpenHelper helper;

    static  String querySelects=" where"+MessagedbContract.EMAIL_COLUMN+"=?,"
            +" order by time;";

    public MessageDataBase(Context context) {
        this.context = context;
        helper=new MessageDataHelper(context,MessagedbContract.TABLE_NAME,null,1);
    }

    public static void init(Context context){
        INSTANCE=new MessageDataBase(context);
    }

    public static MessageDataBase getInstance (){
        return INSTANCE;
    }

    @Override
    public SQLiteDatabase open() {
        return helper.getWritableDatabase();
    }

    @Override
    public void close(SQLiteDatabase database) {
        database.close();
        helper.close();
    }

    public List<MessageEntity> getData(String email) {
        SqlArgs args=new SqlArgs();
        args.setObj(new String[]{email});
        return getData(args);
    }

    @Override
    public List<MessageEntity> getData(SqlArgs<?> queryArgs) {
        SQLiteDatabase database=open();
        Object obj=queryArgs.getObj();
        if(obj instanceof String[]){
            throw new IllegalArgumentException("this query need uid mid be the args");
        }
        String[] array= (String[]) obj;
        Cursor cursor=database.rawQuery("select * from "+ MessagedbContract.TABLE_NAME+querySelects,
                array);
        ArrayList<MessageEntity> entities=new ArrayList<>();

        while (cursor.moveToNext()){
            entities.add(parse(cursor));
        }
        cursor.close();
        database.close();
        return entities;
    }



    @Override
    public long insertData(MessageEntity data) {
        SQLiteDatabase database=open();
        ContentValues values=new ContentValues();
        values.put(MessagedbContract.EMAIL_COLUMN,data.getEmail());
        values.put(MessagedbContract.MESSAGE_COLUMN,data.getMessage());
        values.put(MessagedbContract.TIME_COLUMN,data.getTime());
        values.put(MessagedbContract.SEND_COLUMN,data.getSendUser());
        values.put(MessagedbContract.NAME_COLUMN,data.getName());
        long id=database.insert(MessagedbContract.TABLE_NAME,null,values);
        database.close();
        return id;
    }


    @Override
    public MessageEntity parse(Cursor cursor) {
        String email=cursor.getString(cursor.getColumnIndex(MessagedbContract.EMAIL_COLUMN));
        String message=cursor.getString(cursor.getColumnIndex(MessagedbContract.MESSAGE_COLUMN));
        String time=cursor.getString(cursor.getColumnIndex(MessagedbContract.TIME_COLUMN));
        String senduser=cursor.getString(cursor.getColumnIndex(MessagedbContract.SEND_COLUMN));
        String name=cursor.getString(cursor.getColumnIndex(MessagedbContract.NAME_COLUMN));

        MessageEntity entity=new MessageEntity();
        entity.setEmail(email);
        entity.setMessage(message);
        entity.setSendUser(senduser);
        entity.setTime(time);
        entity.setName(name);

        return entity;
    }
}
