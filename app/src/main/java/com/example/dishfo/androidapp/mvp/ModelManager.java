package com.example.dishfo.androidapp.mvp;

import com.example.dishfo.androidapp.application.MyApplication;
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

import javax.inject.Inject;

/**
 * 用管理与提供model 的各个实现类
 * Created by dishfo on 18-4-1.
 */

public final class ModelManager {

    public final static ModelManager INSTANCE=new ModelManager();

    @Inject
    AreaModelImpl areaModel;

    @Inject
    AreaModulesModelImpl areaModulesModel;

    @Inject
    DiscussModelImpl discussModel;

    @Inject
    LoginModelImpl loginModel;

    @Inject
    MessageModelImpl messageModel;

    @Inject
    NewNoteModelImpl newNoteModel;

    @Inject
    NoteModelImpl noteModel;

    @Inject
    SettingModelImpl settingModel;

    @Inject
    TalkModelImpl talkModel;

    @Inject
    UserInfoModelImpl userInfoModel;

    private ModelManager(){

    }

    public void init(){
        MyApplication.getAppModuleComponent().inject(this);
    }

    public AreaModelImpl getAreaModel() {
        return areaModel;
    }

    public AreaModulesModelImpl getAreaModulesModel() {
        return areaModulesModel;
    }

    public DiscussModelImpl getDiscussModel() {
        return discussModel;
    }

    public LoginModelImpl getLoginModel() {
        return loginModel;
    }

    public MessageModelImpl getMessageModel() {
        return messageModel;
    }

    public NewNoteModelImpl getNewNoteModel() {
        return newNoteModel;
    }

    public NoteModelImpl getNoteModel() {
        return noteModel;
    }

    public SettingModelImpl getSettingModel() {
        return settingModel;
    }

    public TalkModelImpl getTalkModel() {
        return talkModel;
    }

    public UserInfoModelImpl getUserInfoModel() {
        return userInfoModel;
    }
}
