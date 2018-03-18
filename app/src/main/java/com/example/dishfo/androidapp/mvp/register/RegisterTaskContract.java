package com.example.dishfo.androidapp.mvp.register;

import com.example.dishfo.androidapp.mvp.BasePresenter;
import com.example.dishfo.androidapp.mvp.BaseView;

/**
 * Created by dishfo on 18-1-22.
 */

public interface RegisterTaskContract {

    interface  RegisterView extends BaseView<RegisterPresenter>{
        public void showRegisterError(String...args);

        public void showRegisterSucceed();

        public void showSendEmailSucceed();

        public void showSendEmailError(String...args);

        public void waitUpdate();

        public void stopWait();

    }


    interface  RegisterPresenter extends BasePresenter{

        public void sendEmail(String email);

        public void register(String email,String password,String token);

    }

}
