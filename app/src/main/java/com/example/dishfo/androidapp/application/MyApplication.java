package com.example.dishfo.androidapp.application;

import android.app.Application;
import android.content.Intent;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.data.message.MessageDataBase;
import com.example.dishfo.androidapp.data.talk.TalkDataBase;
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

    private static HashMap<Class,MessageHandler> handlers;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        Intent intent=new Intent(this, LongConService.class);
        startService(intent);
        TalkDataBase.init(this);
        MessageDataBase.init(this);
        PropertiesReader.init();
        PropertiesReader.add(getResources().openRawResource(R.raw.reverse_maps),REVERSE_MAP);
        PropertiesReader.add(getResources().openRawResource(R.raw.maps),MAP);

        handlers=new HashMap<>();
    }

    public static void putHandler(Class key,MessageHandler handler){
        handlers.put(key,handler);
    }

    public static MessageHandler getHandler(Class key){
        return handlers.get(key);
    }
}
