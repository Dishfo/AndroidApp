package com.example.dishfo.androidapp.application;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Intent;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.dagger.DaggerMessageComponent;
import com.example.dishfo.androidapp.dagger.MessageComponent;
import com.example.dishfo.androidapp.data.message.MessageDatebase;
import com.example.dishfo.androidapp.listener.MessageHandler;
import com.example.dishfo.androidapp.longconnect.LongConService;
import com.example.dishfo.androidapp.util.PropertiesReader;

import java.util.HashMap;

/**
 * Created by apple on 2017/12/10.
 */

public class MyApplication extends Application {

    public final static String REVERSE_MAP="r";
    public final static String MAP="m";

    private static MessageDatebase messageDataBase;
    private static HashMap<Class,MessageHandler> handlers;
    private static MessageComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        Intent intent=new Intent(this, LongConService.class);
        startService(intent);
        PropertiesReader.init();
        PropertiesReader.add(getResources().openRawResource(R.raw.reverse_maps),REVERSE_MAP);
        PropertiesReader.add(getResources().openRawResource(R.raw.maps),MAP);
        messageDataBase=Room.databaseBuilder(this, MessageDatebase.class,"msg.db").build();
        component= DaggerMessageComponent.create();
        handlers=new HashMap<>();
    }

    public static MessageDatebase getMessageDataBase(){
        return messageDataBase;
    }

    public static MessageComponent getComponent(){
        return component;
    }

    public static void putHandler(Class key,MessageHandler handler){
        handlers.put(key,handler);
    }

    public static MessageHandler getHandler(Class key){
        return handlers.get(key);
    }
}
