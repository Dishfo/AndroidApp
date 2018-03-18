package com.example.dishfo.androidapp.mvp.message;

import com.example.dishfo.androidapp.bean.MessageBean;
import com.example.dishfo.androidapp.mvp.BaseModel;
import com.example.dishfo.androidapp.mvp.BasePresenter;
import com.example.dishfo.androidapp.mvp.BaseView;

/**
 * Created by dishfo on 18-3-17.
 */

public class MessageContract {
    public static final int MESSAGEGET=0X1;
    public static final int MESSAGELOAD=0X2;

    public static final int SUCCEED=0X2;
    public static final int FAILED=0X2;

    public interface MessagePresenter extends BasePresenter{
        public void onRecevier(MessageBean message);
    }

    public interface MessageModel extends BaseModel<MessagePresenter>{
        public void loadMessage(String email);
        public void saveMessage(MessageBean messageBean);
    }

    public interface MessageView extends BaseView<MessagePresenter>{
        public void AfterRecevier(MessageBean message);
    }

}
