package com.example.dishfo.androidapp.dagger;

import com.example.dishfo.androidapp.DataAcess.NetMethod;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dishfo on 18-3-19.
 */
@Singleton
@Module
public class NetWorkModule {

    @Provides
    public NetMethod provideNetMethod(){
        return new NetMethod();
    }

}
