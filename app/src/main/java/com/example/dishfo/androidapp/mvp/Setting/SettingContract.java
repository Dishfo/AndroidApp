package com.example.dishfo.androidapp.mvp.Setting;

import com.example.dishfo.androidapp.bean.UserInfo;
import com.example.dishfo.androidapp.mvp.BaseModel;
import com.example.dishfo.androidapp.mvp.BasePresenter;
import com.example.dishfo.androidapp.mvp.BaseView;

/**
 * Created by dishfo on 18-3-8.
 */

public class SettingContract {

    public static final int HEAD=1;
    public static final int NAME=2;
    public static final  int SUCCEED=0X3;
    public static final  int FAILED=0X4;

    public interface SettingPresent extends BasePresenter{

        public void changeHead(UserInfo info,String file);
        public void changeName(UserInfo info,String name);
    }

    public interface SettingView extends BaseView<SettingPresent>{
    }

    public interface SettingModel extends BaseModel<SettingPresent>{
        public void toChangeHead(UserInfo info,String file);
        public void toChangeName(UserInfo info,String name);
    }

}
