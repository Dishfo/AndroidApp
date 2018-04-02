package com.example.dishfo.androidapp.dagger.module;

import com.example.dishfo.androidapp.data.repository.AreaRepository;
import com.example.dishfo.androidapp.data.repository.CollectionRepository;
import com.example.dishfo.androidapp.data.repository.DiscussRepository;
import com.example.dishfo.androidapp.data.repository.FansRepository;
import com.example.dishfo.androidapp.data.repository.FollowAreaRepository;
import com.example.dishfo.androidapp.data.repository.FollowUserRepository;
import com.example.dishfo.androidapp.data.repository.LikeRepository;
import com.example.dishfo.androidapp.data.repository.MessageRepository;
import com.example.dishfo.androidapp.data.repository.NoteRepository;
import com.example.dishfo.androidapp.data.repository.TalkRepository;
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
    AreaRepository provideAreaRepository(){
        return new AreaRepository();
    }

    @Provides
    LikeRepository provideLikeRepository(){
        return new LikeRepository();
    }

    @Provides
    MessageRepository provideMessageRepository(){
        return new MessageRepository();
    }

    @Provides
    NoteRepository provideNoteRepository(){
        return new NoteRepository();
    }

    @Provides
    UserRepository provideUserRepository(){
        return new UserRepository();
    }

    @Provides
    FollowAreaRepository provideFollowAreaRepository(){
        return new FollowAreaRepository();
    }

    @Provides
    FollowUserRepository provideFollowUserRepository(){
        return new FollowUserRepository();
    }

    @Provides
    DiscussRepository provideDiscussRepository(){
        return new DiscussRepository();
    }

    @Provides
    FansRepository provideFansRepository(){
        return new FansRepository();
    }

    @Provides
    CollectionRepository provideCollectionRepository(){
        return new CollectionRepository();
    }

    @Provides
    TalkRepository talkRepository(){
        return new TalkRepository();
    }

}
