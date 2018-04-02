package com.example.dishfo.androidapp.mvp.Note;

import com.example.dishfo.androidapp.mvp.BaseModel;
import com.example.dishfo.androidapp.mvp.BasePresenter;
import com.example.dishfo.androidapp.mvp.BaseView;
import com.example.dishfo.androidapp.bean.viewBean.ViewNote;
import com.example.dishfo.androidapp.bean.viewBean.ViewNoteHead;

/**
 * Created by dishfo on 18-2-28.
 */

public class NoteTaskContract {
    public static final  int FOLLOW=0X8;
    public static final  int DISCUSS=0X1;
    public static final  int NOTE=0X2;
    public static final  int SUCCEED=0X3;
    public static final  int FAILED=0X4;

    public interface NotePresenter extends BasePresenter{
        public void onFollowUser(ViewNoteHead viewNoteHead);
    }

    public interface NoteModel extends BaseModel<NotePresenter> {
        public void getDiscuss(ViewNote viewNote);
        public void followUser(ViewNoteHead viewNoteHead);
    }

    public interface NoteView extends BaseView<NotePresenter>{
    }

}
