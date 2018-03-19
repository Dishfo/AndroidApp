package com.example.dishfo.androidapp.mvp.message;

import com.example.dishfo.androidapp.mvp.BaseModel;
import com.example.dishfo.androidapp.mvp.BasePresenter;
import com.example.dishfo.androidapp.mvp.BaseView;
import com.example.dishfo.androidapp.sqlBean.Message;

/**
 * Created by dishfo on 18-3-17.
 */

public class MessageContract {
    public static final int SAVE=0X5;
    public static final int MESSAGEGET=0X1;
    public static final int MESSAGELOAD=0X2;

    public static final int SUCCEED=0X2;
    public static final int FAILED=0X3;

    public interface MessagePresenter extends BasePresenter{
        public void onRecevier(Message message);
    }

    public interface MessageModel extends BaseModel<MessagePresenter>{
        public void loadMessage(String email);
        public void saveMessage(Message message);
    }

    public interface MessageView extends BaseView<MessagePresenter>{
        public void AfterRecevier(Message message);
    }

}
