package com.example.dishfo.androidapp.mvp.Area;

import com.example.dishfo.androidapp.bean.NoteInfo;
import com.example.dishfo.androidapp.mvp.BaseModel;
import com.example.dishfo.androidapp.mvp.BasePresenter;
import com.example.dishfo.androidapp.mvp.BaseView;

/**
 * Created by dishfo on 18-2-12.
 */

public class AreaContract {
    public static final int RECOMMEND=0X1;
    public static final int APPRECIATE=0X2;
    public static final int NAPPRECIATE=0X3;

    public interface AreaPresent extends BasePresenter{

        public void onAppreciateNote(NoteInfo noteInfo);

    }

    public interface AreaModel extends BaseModel<AreaPresent>{
        public void loadNote();
        public void onAppreciateNote(NoteInfo noteInfo);
    }

    public interface AreaView extends BaseView<AreaPresent>{

        public void AppreciateNote();
        public void showRecommendNote(NoteInfo note);

    }

}
