package com.example.dishfo.androidapp.dagger.module;

import com.example.dishfo.androidapp.data.DataAcess.AreaAcess;
import com.example.dishfo.androidapp.data.DataAcess.CollectionAcess;
import com.example.dishfo.androidapp.data.DataAcess.DiscussAcess;
import com.example.dishfo.androidapp.data.DataAcess.FollowAreaAcess;
import com.example.dishfo.androidapp.data.DataAcess.FollowUserAcess;
import com.example.dishfo.androidapp.data.DataAcess.LikeAcess;
import com.example.dishfo.androidapp.data.DataAcess.NetMethod;
import com.example.dishfo.androidapp.data.DataAcess.NoteAcess;
import com.example.dishfo.androidapp.data.DataAcess.UserAcess;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dishfo on 18-3-19.
 */
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

    @Provides
    public FollowUserAcess provideFollowUserAcess(NetMethod netMethod){
        return new FollowUserAcess(netMethod);
    }

    @Provides
    public DiscussAcess provideDiscussAcess(NetMethod netMethod){
        return new DiscussAcess(netMethod);
    }

    @Provides
    public CollectionAcess provideCollectionAcess(NetMethod netMethod){
        return new CollectionAcess(netMethod);
    }


}
