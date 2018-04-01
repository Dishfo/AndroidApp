package com.example.dishfo.androidapp.dagger.component;

import com.example.dishfo.androidapp.activity.LoginActivity;
import com.example.dishfo.androidapp.dagger.module.AppModelModule;
import com.example.dishfo.androidapp.mvp.ModelManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 *
 * Created by dishfo on 18-3-26.
 */
@Singleton
@Component(modules = {AppModelModule.class})
public interface AppModuleComponent {
    void inject(ModelManager modelManager);
}
