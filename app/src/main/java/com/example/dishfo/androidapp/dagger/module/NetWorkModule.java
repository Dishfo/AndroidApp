package com.example.dishfo.androidapp.dagger.module;

import com.example.dishfo.androidapp.DataAcess.AreaAcess;
import com.example.dishfo.androidapp.DataAcess.FollowAreaAcess;
import com.example.dishfo.androidapp.DataAcess.LikeAcess;
import com.example.dishfo.androidapp.DataAcess.NetMethod;
import com.example.dishfo.androidapp.DataAcess.NoteAcess;
import com.example.dishfo.androidapp.DataAcess.UserAcess;
import com.example.dishfo.androidapp.sqlBean.Like;

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

    @Provides
    public UserAcess provideUserAcess(NetMethod netMethod){
        return new UserAcess(netMethod);
    }

    @Provides
    public AreaAcess provideAreaAcess(NetMethod netMethod){
        return new AreaAcess(netMethod);
    }

    @Provides
    public NoteAcess provideNoteAcess(NetMethod netMethod){
        return new NoteAcess(netMethod);
    }

    @Provides
    public LikeAcess provideLikeAcess(NetMethod netMethod){
        return new LikeAcess(netMethod);
    }

    @Provides
    public FollowAreaAcess provideFollowAreaAcess(NetMethod netMethod){
        return new FollowAreaAcess(netMethod);
    }





}
