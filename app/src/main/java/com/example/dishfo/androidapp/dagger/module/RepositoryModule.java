package com.example.dishfo.androidapp.dagger.module;

import com.example.dishfo.androidapp.DataAcess.UserAcess;
import com.example.dishfo.androidapp.data.message.UserDao;
import com.example.dishfo.androidapp.data.repository.AreaRepository;
import com.example.dishfo.androidapp.data.repository.LikeRepository;
import com.example.dishfo.androidapp.data.repository.MessageRepository;
import com.example.dishfo.androidapp.data.repository.NoteRepository;
import com.example.dishfo.androidapp.data.repository.UserRepository;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 *用于提供repository组件
 * Created by dishfo on 18-3-20.
 */
@Singleton
@Module
public class RepositoryModule {

    @Provides
    public  AreaRepository provideAreaRepository(){
        return new AreaRepository();
    }

    @Provides
    public LikeRepository provideLikeRepository(){
        return new LikeRepository();
    }

    @Provides
    public MessageRepository provideMessageRepository(){
        return new MessageRepository();
    }

    @Provides
    public NoteRepository provideNoteRepository(){
        return new NoteRepository();
    }

    @Provides
    public UserRepository provideUserRepository(){
        return new UserRepository();
    }

}
