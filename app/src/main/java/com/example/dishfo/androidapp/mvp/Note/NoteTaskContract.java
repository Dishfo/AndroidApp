package com.example.dishfo.androidapp.mvp.Note;

import com.example.dishfo.androidapp.bean.DiscussInfo;
import com.example.dishfo.androidapp.bean.NoteInfo;
import com.example.dishfo.androidapp.bean.UserInfo;
import com.example.dishfo.androidapp.mvp.BaseModel;
import com.example.dishfo.androidapp.mvp.BasePresenter;
import com.example.dishfo.androidapp.mvp.BaseView;

import java.util.List;

/**
 * Created by dishfo on 18-2-28.
 */

public class NoteTaskContract {
    public static final  int FOLLOWUSER=0X15;
    public static final  int FOLLOW=0X8;
    public static final  int COMPETE=0X7;
    public static final  int DISCUSS=0X1;
    public static final  int NOTE=0X2;
    public static final  int SUCCEED=0X3;
    public static final  int FAILED=0X4;

    public interface NotePresenter extends BasePresenter{
        public void loadDicuss(List<DiscussInfo> discussInfos);
        public void onshowHead(NoteInfo noteInfo);
        public void onFollowUser(UserInfo userInfo);
    }

    public interface NoteModel extends BaseModel<NotePresenter> {
        public void getDiscuss();
        public void followUser(UserInfo userInfo);
    }

    public interface NoteView extends BaseView<NotePresenter>{
        public void showDiscuss(List<DiscussInfo> discussInfos);
        public void showHead(NoteInfo info);
    }

}
