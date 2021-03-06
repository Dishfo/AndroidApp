package com.example.dishfo.androidapp.dagger.component;

import com.example.dishfo.androidapp.dagger.module.MessageDataBaseModule;
import com.example.dishfo.androidapp.dagger.module.NetWorkModule;
import com.example.dishfo.androidapp.data.repository.AreaRepository;
import com.example.dishfo.androidapp.data.repository.CollectionRepository;
import com.example.dishfo.androidapp.data.repository.DiscussRepository;
import com.example.dishfo.androidapp.data.repository.FollowAreaRepository;
import com.example.dishfo.androidapp.data.repository.FollowUserRepository;
import com.example.dishfo.androidapp.data.repository.LikeRepository;
import com.example.dishfo.androidapp.data.repository.MessageRepository;
import com.example.dishfo.androidapp.data.repository.NoteRepository;
import com.example.dishfo.androidapp.data.repository.TalkRepository;
import com.example.dishfo.androidapp.data.repository.UserRepository;

import dagger.Component;

/**
 * Created by dishfo on 18-3-19.
 */
@Component(modules = {MessageDataBaseModule.class,NetWorkModule.class})
public interface MessageComponent {
    public void inject(UserRepository repository);
    public void inject(MessageRepository repository);
    public void inject(NoteRepository repository);
    public void inject(AreaRepository repository);
    public void inject(LikeRepository repository);
    public void inject(FollowAreaRepository repository);
    public void inject(FollowUserRepository repository);
    public void inject(DiscussRepository repository);
    public void inject(CollectionRepository repository);
    public void inject(TalkRepository repository);
}
