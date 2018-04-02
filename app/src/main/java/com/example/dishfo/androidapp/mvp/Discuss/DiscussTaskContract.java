package com.example.dishfo.androidapp.mvp.Discuss;

import com.example.dishfo.androidapp.mvp.BaseModel;
import com.example.dishfo.androidapp.mvp.BasePresenter;
import com.example.dishfo.androidapp.mvp.BaseView;
import com.example.dishfo.androidapp.bean.viewBean.ViewDiscuss;

/**
 * Created by dishfo on 18-3-1.
 */

public class DiscussTaskContract {
    public static final int UPFILE=0X1;
    public static final int DISCUSS=0X2;
    public static final int SUCCEED=0X4;
    public static final int FAILED=0X5;

    public interface DiscussPresenter extends BasePresenter{
        public void onDiscussNote(ViewDiscuss discuss, String files[]);
    }

    public interface DiscusssModel extends BaseModel<DiscussPresenter>{
        public void DisussNote(ViewDiscuss discuss, String files[]);
    }

    public interface DiscussView extends BaseView<DiscussPresenter>{
    }


}
