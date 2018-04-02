package com.example.dishfo.androidapp.dagger.component;

import com.example.dishfo.androidapp.dagger.module.MessageDataBaseModule;
import com.example.dishfo.androidapp.dagger.module.NetWorkModule;
import com.example.dishfo.androidapp.dagger.module.RepositoryModule;
import com.example.dishfo.androidapp.data.repository.MessageRepository;
import com.example.dishfo.androidapp.mvp.Area.AreaModelImpl;
import com.example.dishfo.androidapp.mvp.AreaModules.AreaModulesModelImpl;
import com.example.dishfo.androidapp.mvp.Discuss.DiscussModelImpl;
import com.example.dishfo.androidapp.mvp.NewNote.NewNoteModelImpl;
import com.example.dishfo.androidapp.mvp.Note.NoteModelImpl;
import com.example.dishfo.androidapp.mvp.Setting.SettingModelImpl;
import com.example.dishfo.androidapp.mvp.UserInfo.UserInfoModelImpl;
import com.example.dishfo.androidapp.mvp.login.LoginModelImpl;
import com.example.dishfo.androidapp.mvp.message.MessageModelImpl;
import com.example.dishfo.androidapp.mvp.talk.TalkModelImpl;

import dagger.Component;

/**
 *
 * Created by dishfo on 18-3-20.
 */
@Component(modules = {RepositoryModule.class, NetWorkModule.class, MessageDataBaseModule.class})
public interface RepositoryComponent {
    void inject(AreaModelImpl model);
    void inject(LoginModelImpl model);
    void inject(AreaModulesModelImpl modulesModel);
    void inject(NoteModelImpl model);
    void inject(DiscussModelImpl model);
    void inject(NewNoteModelImpl model);
    void inject(UserInfoModelImpl model);
    void inject(SettingModelImpl model);
    void inject(MessageModelImpl messageModel);
    void inject(TalkModelImpl model);
}
