package com.example.dishfo.androidapp.mvp.login;

import com.example.dishfo.androidapp.mvp.BaseModel;
import com.example.dishfo.androidapp.mvp.BasePresenter;
import com.example.dishfo.androidapp.mvp.BaseView;

/**
 * Created by dishfo on 18-1-26.
 */

public class LoginTaskContract {

    public static int LOGIN=0X1;
    public static int LOADUER=0X2;

    public interface LoginPresent extends BasePresenter{
        public void login(String name,String pwd);
    }

    public interface LoginView extends BaseView<LoginPresent>{

    }

    public interface  LoginModel extends BaseModel<LoginPresent>{
        public void loginNow(String name,String pwd);
    }
}
