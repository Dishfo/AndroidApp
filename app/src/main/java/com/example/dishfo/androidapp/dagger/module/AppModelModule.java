package com.example.dishfo.androidapp.dagger.module;

import com.example.dishfo.androidapp.mvp.login.LoginModelImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 用于提供app相关的model类
 * Created by dishfo on 18-3-23.
 */
@Module
public class AppModelModule {
    @Singleton
    @Provides
    public LoginModelImpl provideLoginModel(){
        return new LoginModelImpl();
    }

}
