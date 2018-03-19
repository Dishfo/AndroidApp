package com.example.dishfo.androidapp.dagger;

import com.example.dishfo.androidapp.data.repository.MessageRepository;
import com.example.dishfo.androidapp.mvp.message.MessageContract;

import dagger.Component;

/**
 * Created by dishfo on 18-3-19.
 */
@Component(modules = {MessageDataBaseModule.class})
public interface MessageComponent {
    public void inject(MessageRepository repository);
}
