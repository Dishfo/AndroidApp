package com.example.dishfo.androidapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.adapter.SettingAdapter;
import com.example.dishfo.androidapp.bean.DiscussInfo;
import com.example.dishfo.androidapp.bean.NoteInfo;
import com.example.dishfo.androidapp.bean.SettingInfo;
import com.example.dishfo.androidapp.bean.UserInfo;
import com.example.dishfo.androidapp.decoration.LinearRecyclerViewDecoration;
import com.example.dishfo.androidapp.mvp.Note.NoteTaskContract;
import com.example.dishfo.androidapp.mvp.Setting.SettingContract;
import com.example.dishfo.androidapp.mvp.Setting.SettingModelImpl;
import com.example.dishfo.androidapp.mvp.Setting.SettingPresentImpl;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.drakeet.materialdialog.MaterialDialog;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import  static com.example.dishfo.androidapp.bean.SettingInfo.*;
public class SettingActivity extends BaseActivity implements View.OnClickListener,
        BaseQuickAdapter.OnItemClickListener,BaseQuickAdapter.OnItemChildClickListener,
        SettingContract.SettingView{


    private List<SettingInfo> mDatas=null;
    private ImageView mImageViewBack = null;
    private RecyclerView mRecyclerView=null;
    private SettingAdapter mSettingAdapter=null;
    UserInfo info;
    private SettingContract.SettingPresent present;
    private String picture;
    private SettingInfo[] settingInfos=null;


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        String path = null;
                        if (media.isCompressed()) {
                            path = media.getCompressPath();
                        } else if (media.isCut()) {
                            path = media.getCutPath();
                        } else {
                            path = media.getPath();
                        }
                        picture=path;
                        UserInfo info=this.generateUserInfo();
                        present.changeHead(info,path);
                    }
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        info= (UserInfo) getIntent().getSerializableExtra(USERINFO);
        setContentView(R.layout.activity_setting);

    }

    @Override
    public void initView() {
        mImageViewBack = findView(R.id.toolBar_back_imageView_back);
        mImageViewBack.setImageResource(R.mipmap.imageview_back);
        mRecyclerView=findViewById(R.id.activity_setting_recyclertview_setting);
        mSettingAdapter=new SettingAdapter(mDatas,this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        });
        mSettingAdapter.setOnItemChildClickListener(this);
        mSettingAdapter.setOnItemClickListener(this);
        mImageViewBack.setOnClickListener(this);

        mRecyclerView.addItemDecoration(new LinearRecyclerViewDecoration(this,
                R.drawable.recyclerview_divider_dark3,LinearLayoutManager.VERTICAL));

        mRecyclerView.setAdapter(mSettingAdapter);
        mImageViewBack.setOnClickListener(this);
        new SettingPresentImpl(new SettingModelImpl(),this);
    }

    @Override
    public void initData() {
        mDatas=new ArrayList<>();
        settingInfos=new SettingInfo[]{
                new SettingInfo("通用设置",FIRST_TYPE),
                new SettingInfo(" 个人头像",SECOND_TYPE,info.head),
                new SettingInfo(" 个人昵称",info.name,THRID_TYPE),
                new SettingInfo(" 个人简介",FIRST_TYPE),
                new SettingInfo(" 密码管理",FIRST_TYPE),
                new SettingInfo(" 应用设置",FIRST_TYPE),
                new SettingInfo(" 清除缓存","0MB",THRID_TYPE),
                new SettingInfo(" 版本更新","1.0",THRID_TYPE),
                new SettingInfo(" 退出帐号",FOURTH_TYPE)
        };
        int len=settingInfos.length;
        for(int i=0;i<len;i++){
            mSettingAdapter.addData(settingInfos[i]);
        }
        OverScrollDecoratorHelper.setUpOverScroll(mRecyclerView,OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolBar_back_imageView_back:
                onBackPressed();
                break;
        }
    }



    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String label=((TextView)view.findViewById(R.id.recyclerview_item_textview))
                .getText()
                .toString();
        Log.d("test",label);
        if(label.equals(" 个人昵称")){
            showEditName();
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Toast.makeText(this,"item click",Toast.LENGTH_SHORT).show();
        switch (view.getId()){
            case R.id.recyclerview_item_iamgeview:
                Log.d("test","change head");
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(1)
                        .compress(true)
                        .enableCrop(true)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.recyclerview_item_button:
                configExit();
                break;
        }

    }

    @Override
    public void setPresent(SettingContract.SettingPresent present) {
        this.present=present;
    }

    @Override
    public void waitToCompete() {
        startWait();
    }

    public void startWait(){

    }

    public void stopWait(){

    }

    @Override
    public void compete(Object... args) {
        sendMessage((int)args[0],SettingContract.SUCCEED,args[1]);
    }

    @Override
    public void error(Object... args) {
        sendMessage((int)args[0],SettingContract.FAILED,null);
    }


    private void changeCompete(Object... args) {
        int arg= (int) args[0];
        UserInfo info= (UserInfo) args[1];
        if(arg== SettingContract.HEAD){
            mSettingAdapter.getData().get(1).imageurl=info.head;
        }else if(arg == SettingContract.NAME){
            mSettingAdapter.getData().get(2).essue=info.name;
        }
        mSettingAdapter.notifyDataSetChanged();
    }

    private void configExit(){
        final MaterialDialog dialog=new MaterialDialog(this);
        dialog.setTitle("退出帐号")
                .setMessage("确认退出当前帐号")
                .setPositiveButton("退出", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(SettingActivity.this,LoginActivity.class);
                        startActivity(intent);
                        Log.d("test","exit");
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

        dialog.show();
    }

    private void showEditName(){
        MaterialDialog dialog=new MaterialDialog(this);
        View view= LayoutInflater.from(this).inflate(R.layout.activity_dialog_editname,null);
        dialog.setView(view)
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText=view.findViewById(R.id.editText);
                        UserInfo info=generateUserInfo();
                        String name=editText.getText().toString();
                        info.name=name;
                        present.changeName(info,name);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }).show();
    }


    private UserInfo generateUserInfo(){
        UserInfo userInfo=new UserInfo();
        userInfo.head=this.info.head;
        userInfo.name=this.info.name;
        userInfo.email=this.info.email;
        return userInfo;
    }


    private void changeError(Object... args) {
        Toast.makeText(this,(String)args[0],Toast.LENGTH_SHORT).show();
    }

    private void sendMessage(int code,int arg1,Object object){
        Message message=mHandler.obtainMessage();
        message.what=code;
        message.arg1=arg1;
        message.obj=object;
        mHandler.sendMessage(message);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SettingActivity.this.stopWait();
            switch (msg.what){
                case SettingContract.HEAD:
                    if(msg.arg1==NoteTaskContract.SUCCEED){
                        changeCompete(SettingContract.HEAD,msg.obj);
                    }else {
                        changeError("更改昵称失败");
                    }
                    break;
                case SettingContract.NAME:
                    if(msg.arg1==NoteTaskContract.SUCCEED){
                        changeCompete(SettingContract.NAME,msg.obj);
                    }else {
                        changeError("更改头像失败");
                    }
                    break;
            }
        }
    };
}
