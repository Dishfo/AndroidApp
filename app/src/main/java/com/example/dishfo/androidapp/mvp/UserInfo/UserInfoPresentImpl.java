package com.example.dishfo.androidapp.mvp.UserInfo;

import com.example.dishfo.androidapp.mvp.ModelManager;
import com.example.dishfo.androidapp.bean.sqlBean.User;

/**
 *
 * Created by dishfo on 18-3-7.
 */

public class UserInfoPresentImpl implements UserInfoTaskContract.UserInfoPresent{

    private UserInfoTaskContract.UserInfoModel model;
    private UserInfoTaskContract.UserInfoView view;

    public UserInfoPresentImpl(UserInfoTaskContract.UserInfoModel model, UserInfoTaskContract.UserInfoView view) {
        this.model = model;
        this.view = view;

        model.setPresent(this);
        view.setPresent(this);
    }

    @Override
    public void start(Object... args) {
        User user= ModelManager.INSTANCE.getLoginModel().getCurrentUser();
        model.loadUserInfo(user.getEmail());
    }

    @Override
    public void stop() {

    }

    @Override
    public void onCompete(Object... args) {
        view.compete(args[0],args[1]);
    }

    @Override
    public void onError(Object... args) {
        view.error(args[0]);
    }

}
