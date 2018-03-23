package com.example.dishfo.androidapp.dagger.component;

import com.example.dishfo.androidapp.dagger.module.MessageDataBaseModule;
import com.example.dishfo.androidapp.dagger.module.NetWorkModule;
import com.example.dishfo.androidapp.data.repository.UserRepository;

import dagger.Component;

/**
 * Created by dishfo on 18-3-19.
 */
@Component(modules = {NetWorkModule.class,MessageDataBaseModule.class})
public interface NetWorkComponent {
    public void inject(UserRepository repository);
}
