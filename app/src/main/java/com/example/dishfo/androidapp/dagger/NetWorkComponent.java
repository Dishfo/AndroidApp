package com.example.dishfo.androidapp.dagger;

import com.example.dishfo.androidapp.data.repository.UserRepository;

import dagger.Component;

/**
 * Created by dishfo on 18-3-19.
 */
@Component(modules = {NetWorkModule.class})
public interface NetWorkComponent {
    public void inject(UserRepository repository);
}
