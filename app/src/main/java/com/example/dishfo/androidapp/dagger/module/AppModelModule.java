package com.example.dishfo.androidapp.dagger.module;

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

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 用于提供app相关的model类
 * Created by dishfo on 18-3-23.
 */
@Singleton
@Module
public class AppModelModule {

    @Singleton
    @Provides
    public LoginModelImpl provideLoginModel(){
        return new LoginModelImpl();
    }

    @Singleton
    @Provides
    public AreaModelImpl areaModel(){
        return new AreaModelImpl();
    }

    @Singleton
    @Provides
    public AreaModulesModelImpl areaModulesModel(){
        return new AreaModulesModelImpl();
    }

    @Singleton
    @Provides
    public DiscussModelImpl discussModel(){
        return new DiscussModelImpl();
    }

    @Singleton
    @Provides
    public MessageModelImpl messageModel(){
        return new MessageModelImpl();
    }

    @Singleton
    @Provides
    public NewNoteModelImpl newNoteModel(){
        return new NewNoteModelImpl();
    }

    @Singleton
    @Provides
    public NoteModelImpl noteModel(){
        return new NoteModelImpl();
    }

    @Singleton
    @Provides
    public SettingModelImpl settingModel(){
        return new SettingModelImpl();
    }

    @Singleton
    @Provides
    public UserInfoModelImpl userInfoModel(){
        return new UserInfoModelImpl();
    }

    @Singleton
    @Provides
    public TalkModelImpl talkModel(){
        return new TalkModelImpl();
    }

}
