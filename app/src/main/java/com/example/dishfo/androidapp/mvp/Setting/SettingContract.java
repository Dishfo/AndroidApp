package com.example.dishfo.androidapp.mvp.Setting;

import com.example.dishfo.androidapp.mvp.BaseModel;
import com.example.dishfo.androidapp.mvp.BasePresenter;
import com.example.dishfo.androidapp.mvp.BaseView;
import com.example.dishfo.androidapp.sqlBean.User;

/**
 * Created by dishfo on 18-3-8.
 */

public class SettingContract {

    public static final int HEAD=1;
    public static final int NAME=2;
    public static final  int SUCCEED=0X3;
    public static final  int FAILED=0X4;

    public interface SettingPresent extends BasePresenter{

        public void changeHead(User user, String file);
        public void changeName(User user,String name);
    }

    public interface SettingView extends BaseView<SettingPresent>{
    }

    public interface SettingModel extends BaseModel<SettingPresent>{
        public void toChangeHead(User user,String file);
        public void toChangeName(User user,String name);
    }

}
