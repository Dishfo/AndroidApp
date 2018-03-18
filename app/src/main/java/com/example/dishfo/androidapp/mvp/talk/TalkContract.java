package com.example.dishfo.androidapp.mvp.talk;

import com.example.dishfo.androidapp.bean.TalkInfo;
import com.example.dishfo.androidapp.mvp.BaseModel;
import com.example.dishfo.androidapp.mvp.BasePresenter;
import com.example.dishfo.androidapp.mvp.BaseView;

/**
 * Created by dishfo on 18-3-17.
 */

public class TalkContract {

    public final static int SEND=0X1;
    public final static int RECEIVER=0X2;
    public final static int SUCCEED=0X3;
    public final static int FAILED=0X4;

    public interface TalkPresenter extends BasePresenter{
        public void onSend(TalkInfo message);
        public void onRecevier(TalkInfo message);
    }

    public interface TalkModel extends BaseModel<TalkPresenter>{
        public void saveMessage(TalkInfo msg);
        public void loadMessage(String email,String ouser);
    }

    public interface TalkView extends BaseView<TalkPresenter>{
        public void afterSend(TalkInfo msg);
        public void afterRecevier(TalkInfo msg);
    }
}
