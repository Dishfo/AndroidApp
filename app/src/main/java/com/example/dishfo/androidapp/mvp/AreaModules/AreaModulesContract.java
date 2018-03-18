package com.example.dishfo.androidapp.mvp.AreaModules;

import com.example.dishfo.androidapp.bean.AreaInfo;
import com.example.dishfo.androidapp.bean.NoteInfo;
import com.example.dishfo.androidapp.mvp.BaseModel;
import com.example.dishfo.androidapp.mvp.BasePresenter;
import com.example.dishfo.androidapp.mvp.BaseView;

import java.util.List;

/**
 * Created by dishfo on 18-3-6.
 */

public class AreaModulesContract {
    public static final int FOLLOW=0X44;
    public static final int AREA=0X1;
    public static final int NOTE=0X2;
    public static final int SUCCEED=0X4;
    public static final int FAILED=0X5;

    public interface AreaModulesPresent extends BasePresenter {
        public void onFollowArea(AreaInfo areaInfo);
    }

    public interface AreaModulesView extends BaseView<AreaModulesPresent>{
        public void showNotes(List<NoteInfo> infos);
        public void showArea(AreaInfo areaInfo);
        public void onFollowArea(AreaInfo areaInfo);
    }

    public interface  AreaModulesModel extends BaseModel<AreaModulesPresent>{
        public void getAreaWithNotes(String name);
        public void FollowArea(AreaInfo areaInfo);
    }

}
