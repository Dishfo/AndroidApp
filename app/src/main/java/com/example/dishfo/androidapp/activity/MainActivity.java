package com.example.dishfo.androidapp.activity;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.fragment.AreaFragment;
import com.example.dishfo.androidapp.fragment.FoundFragment;
import com.example.dishfo.androidapp.fragment.MessageFragment;
import com.example.dishfo.androidapp.fragment.MineFragment;
import com.example.dishfo.androidapp.listener.FragmentSendListener;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.jpeng.jptabbar.animate.AnimationType;

public class MainActivity extends BaseActivity implements
        OnTabSelectListener,FragmentSendListener{


    private Fragment[] fragments=new Fragment[4];
    private JPTabBar mJpTabBar=null;
    private int mPosition=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIsFullScreen(true);
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
        changeFrament(index);
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
                    fragments[index]= MessageFragment.newInstance("","");
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
        transaction.setCustomAnimations(R.anim.fragment_in,R.anim.fragment_out);
        transaction.commit();
    }

    @Override
    public void action(int tag,Object arg) {
        Intent intent=null;
        switch (tag){
            case AreaFragment.ENTER_RECOMMEND:
                intent=new Intent(this,NoteActivity.class);
                startActivity(intent);
                break;
            case AreaFragment.ENTER_MODULE:
                intent=new Intent(this,AreaActivity.class);
                startActivity(intent);
                break;
            case FoundFragment.ENTER_FOUND_NOTE:
                intent=new Intent(this,NoteActivity.class);
                startActivity(intent);
                break;
            case MineFragment.ENTER_SETTING:
                intent=new Intent(this,SettingActivity.class);
                startActivity(intent);
        }
    }
}
