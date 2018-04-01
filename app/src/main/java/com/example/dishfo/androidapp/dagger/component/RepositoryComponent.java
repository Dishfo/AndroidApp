package com.example.dishfo.androidapp.dagger.component;

import com.example.dishfo.androidapp.dagger.module.MessageDataBaseModule;
import com.example.dishfo.androidapp.dagger.module.NetWorkModule;
import com.example.dishfo.androidapp.dagger.module.RepositoryModule;
import com.example.dishfo.androidapp.mvp.Area.AreaModelImpl;
import com.example.dishfo.androidapp.mvp.AreaModules.AreaModulesModelImpl;
import com.example.dishfo.androidapp.mvp.Discuss.DiscussModelImpl;
import com.example.dishfo.androidapp.mvp.NewNote.NewNoteModelImpl;
import com.example.dishfo.androidapp.mvp.Note.NoteModelImpl;
import com.example.dishfo.androidapp.mvp.Setting.SettingModelImpl;
import com.example.dishfo.androidapp.mvp.UserInfo.UserInfoModelImpl;
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
    public void inject(NoteModelImpl model);
    public void inject(DiscussModelImpl model);
    public void inject(NewNoteModelImpl model);
    public void inject(UserInfoModelImpl model);
    public void inject(SettingModelImpl model);
}
