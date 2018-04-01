package com.example.dishfo.androidapp.mvp.UserInfo;

import com.example.dishfo.androidapp.bean.MineMessage;
import com.example.dishfo.androidapp.mvp.BaseModel;
import com.example.dishfo.androidapp.mvp.BasePresenter;
import com.example.dishfo.androidapp.mvp.BaseView;
import com.google.gson.JsonArray;

/**
 * Created by dishfo on 18-3-7.
 */

public class UserInfoTaskContract {
    public static final int USER=0x1;
    public static final  int SUCCEED=0X3;
    public static final  int FAILED=0X4;

    public interface UserInfoPresent extends BasePresenter{

    }

    public interface UserInfoModel extends BaseModel<UserInfoPresent>{
        public void loadUserInfo(String email);

    }

    public interface UserInfoView extends BaseView<UserInfoPresent>{

    }

}
