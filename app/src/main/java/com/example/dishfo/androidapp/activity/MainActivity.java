package com.example.dishfo.androidapp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.PopupWindow;

import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.bean.sqlBean.User;
import com.example.dishfo.androidapp.bean.viewBean.ViewNote;
import com.example.dishfo.androidapp.fragment.AreaFragment;
import com.example.dishfo.androidapp.fragment.FoundFragment;
import com.example.dishfo.androidapp.fragment.MineFragment;
import com.example.dishfo.androidapp.fragment.TalkFragment;
import com.example.dishfo.androidapp.listener.FragmentSendListener;
import com.example.dishfo.androidapp.longconnect.LongConService;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.jpeng.jptabbar.animate.AnimationType;

import java.io.Serializable;

public class MainActivity extends BaseActivity implements
        OnTabSelectListener,FragmentSendListener{


    private Fragment[] fragments=new Fragment[4];
    private JPTabBar mJpTabBar=null;
    private int mPosition=-1;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setIsFullScreen(true);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initView() {
        int len=fragments.length;

        for(int i=0;i<len;i++){
            fragments[i]=null;
        }


        mJpTabBar=findViewById(R.id.activity_main_tabbar);
        mJpTabBar.setTitles("专区","发现","消息","我的");
        mJpTabBar.setSelectedIcons(R.mipmap.icon_level_01,
                R.mipmap.icon_level_01,
                R.mipmap.icon_level_01,
                R.mipmap.icon_level_01);
        mJpTabBar.setNormalIcons(R.mipmap.ic_launcher_round
                ,R.mipmap.ic_launcher_round,
                R.mipmap.ic_launcher_round,
                R.mipmap.ic_launcher_round
        );

        mJpTabBar.generate();
        mJpTabBar.setAnimation(AnimationType.SCALE2);
        mJpTabBar.setTabListener(this);
        mJpTabBar.setSelectTab(0);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onTabSelect(int index) {
        if(mPosition!=-1){
            changeFrament(index);
        }
        else{
            addFragment(index);
        }
    }

    private void addFragment(int index){
        switch (index){
            case 0:
                fragments[index]=AreaFragment.newInstance("","");
                break;
            case 1:
                fragments[index]= FoundFragment.newInstance("","");
                break;
            case 2:
                fragments[index]= TalkFragment.newInstance("","");
                break;
            case 3:
                fragments[index]= MineFragment.newInstance("","");
                break;
        }

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.activity_main_fragment_container,fragments[index]);
        mPosition=index;
        transaction.setCustomAnimations(R.anim.fragment_in,R.anim.fragment_out);
        transaction.commit();
    }

    private void changeFrament(int index){
        if(fragments[index]==null){
            switch (index){
                case 0:
                    fragments[index]=AreaFragment.newInstance("","");
                    break;
                case 1:
                    fragments[index]= FoundFragment.newInstance("","");
                    break;
                case 2:
                    fragments[index]= TalkFragment.newInstance("","");
                    break;
                case 3:
                    fragments[index]= MineFragment.newInstance("","");
                    break;
            }
        }

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[mPosition]);
        transaction.replace(R.id.activity_main_fragment_container,fragments[index]);
        mPosition=index;
        transaction.commit();
    }

    @Override
    public void action(int tag,Object arg) {
        Intent intent=null;

        switch (tag){
            case AreaFragment.ENTER_RECOMMEND:
                intent=new Intent(this,NoteActivity.class);
                ViewNote note= (ViewNote) arg;
                intent.putExtra(NOTEID,note);
                startActivity(intent);
                break;
            case AreaFragment.ENTER_MODULE:
                intent=new Intent(this,AreaActivity.class);
                intent.putExtra(AREANAME, (String) arg);
                startActivity(intent);
                break;
            case FoundFragment.ENTER_FOUND_NOTE:
                intent=new Intent(this,NoteActivity.class);
                startActivity(intent);
                break;
            case MineFragment.ENTER_SETTING:
                intent=new Intent(this,SettingActivity.class);
                intent.putExtra(USERINFO,(Serializable) arg);
                startActivity(intent);
                break;
            case TalkFragment.START_TALK:
                User user= (User) arg;
                intent=new Intent(this,TalkActivity.class);
                intent.putExtra(USERINFO,user);
                startActivity(intent);
                break;
            case DARKWINDOW:
                mPopupWindow=getWaitingWindow();
                mPopupWindow.showAsDropDown(getWindow().getDecorView(),0,0);
                darkBackground();
                break;
            case LIGHTWINDOW:
                mPopupWindow.dismiss();
                lightBackground();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LongConService.close();
    }
}
