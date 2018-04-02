package com.example.dishfo.androidapp.mvp.talk;

import com.example.dishfo.androidapp.bean.sqlBean.Message;
import com.example.dishfo.androidapp.bean.viewBean.ViewTalk;
import com.example.dishfo.androidapp.mvp.BaseModel;
import com.example.dishfo.androidapp.mvp.BasePresenter;
import com.example.dishfo.androidapp.mvp.BaseView;

/**
 *
 * Created by dishfo on 18-3-17.
 */

public class TalkContract {

    public final static int LOAD=0X7;
    public final static int SEND =0X1;
    public final static int RECEIVER=0X2;
    public final static int SUCCEED=0X3;
    public final static int FAILED=0X4;

    public interface TalkPresenter extends BasePresenter{
        void onSend(ViewTalk message);
        void onRecevier(Message message);
    }

    public interface TalkModel extends BaseModel<TalkPresenter>{
        void saveMessage(Message msg);
        void loadMessage(String email,String other);
        void sendMessage(ViewTalk viewTalk);

    }

    public interface TalkView extends BaseView<TalkPresenter>{

    }
}
