package com.example.dishfo.androidapp.mvp.NewNote;

import com.example.dishfo.androidapp.bean.AreaInfo;
import com.example.dishfo.androidapp.bean.DiscussInfo;
import com.example.dishfo.androidapp.bean.NoteInfo;
import com.example.dishfo.androidapp.mvp.BaseModel;
import com.example.dishfo.androidapp.mvp.BasePresenter;
import com.example.dishfo.androidapp.mvp.BaseView;

/**
 * Created by dishfo on 18-3-5.
 */

public class NewNoteTaskContract {

    public static final  int UPFILE=0X1;
    public static final  int NOTE=0X2;
    public static final  int SUCCEED=0X3;
    public static final  int FAILED=0X4;

    public interface NewNotePresenter extends BasePresenter{
        public void onPushNote(NoteInfo info, AreaInfo areaInfo, String files[]);
    }

    public interface NewNoteModel extends BaseModel<NewNotePresenter>{
        public void PushNote(NoteInfo info,AreaInfo areaInfo, String files[]);
    }

    public interface NewNoteView extends BaseView<NewNotePresenter>{
    }


}
