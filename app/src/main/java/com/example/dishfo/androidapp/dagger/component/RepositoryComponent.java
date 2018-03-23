package com.example.dishfo.androidapp.dagger.component;

import com.example.dishfo.androidapp.dagger.module.MessageDataBaseModule;
import com.example.dishfo.androidapp.dagger.module.NetWorkModule;
import com.example.dishfo.androidapp.dagger.module.RepositoryModule;
import com.example.dishfo.androidapp.mvp.Area.AreaModelImpl;
import com.example.dishfo.androidapp.mvp.AreaModules.AreaModulesModelImpl;
import com.example.dishfo.androidapp.mvp.login.LoginModelImpl;

import dagger.Component;

/**
 *
 * Created by dishfo on 18-3-20.
 */
@Component(modules = {RepositoryModule.class, NetWorkModule.class, MessageDataBaseModule.class})
public interface RepositoryComponent {
    public void inject(AreaModelImpl model);
    public void inject(LoginModelImpl model);
    public void inject(AreaModulesModelImpl modulesModel);
}
