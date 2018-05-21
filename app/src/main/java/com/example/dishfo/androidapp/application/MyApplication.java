package com.example.dishfo.androidapp.application;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Intent;

import com.example.dishfo.androidapp.R;

import com.example.dishfo.androidapp.dagger.component.AppModuleComponent;
import com.example.dishfo.androidapp.dagger.component.DaggerAppModuleComponent;
import com.example.dishfo.androidapp.dagger.component.DaggerMessageComponent;
import com.example.dishfo.androidapp.dagger.component.DaggerRepositoryComponent;
import com.example.dishfo.androidapp.dagger.component.MessageComponent;
import com.example.dishfo.androidapp.dagger.component.RepositoryComponent;
import com.example.dishfo.androidapp.data.message.MessageDatebase;
import com.example.dishfo.androidapp.longconnect.bean.MessageHandler;
import com.example.dishfo.androidapp.longconnect.LongConService;
import com.example.dishfo.androidapp.mvp.ModelManager;
import com.example.dishfo.androidapp.util.PropertiesReader;

import java.util.HashMap;

/**
 *
 * 自定义的application 主要用与对组件初始化
 * Created by apple on 2017/12/10.
 */

public class MyApplication extends Application {

    public final static String REVERSE_MAP="r";
    public final static String MAP="m";

    private static MessageDatebase messageDataBase;
    private static RepositoryComponent repositoryComponent;
    private static MessageComponent component;
    public static AppModuleComponent appModuleComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        PropertiesReader.init();
        PropertiesReader.add(getResources().openRawResource(R.raw.reverse_maps),REVERSE_MAP);
        PropertiesReader.add(getResources().openRawResource(R.raw.maps),MAP);

        messageDataBase=Room.databaseBuilder(this, MessageDatebase.class,"dataes.db").build();
        repositoryComponent= DaggerRepositoryComponent.create();
        component= DaggerMessageComponent.create();
        appModuleComponent= DaggerAppModuleComponent.create();
        ModelManager.INSTANCE.init();


        //初始化长链接
        Intent intent=new Intent(this, LongConService.class);
        startService(intent);
    }

    public static AppModuleComponent getAppModuleComponent() {
        return appModuleComponent;
    }

    public static RepositoryComponent getRepositoryComponent() {
        return repositoryComponent;
    }

    public static MessageDatebase getMessageDataBase(){
        return messageDataBase;
    }

    public static MessageComponent getComponent(){
        return component;
    }
}
