package com.example.dishfo.androidapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.example.dishfo.androidapp.DataAcess.NetMethod;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.adapter.TalkAdapter;
import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.bean.MessageBean;
import com.example.dishfo.androidapp.bean.MessageInfo;
import com.example.dishfo.androidapp.bean.TalkInfo;
import com.example.dishfo.androidapp.bean.UserInfo;
import com.example.dishfo.androidapp.customview.RefreshHeaderView;
import com.example.dishfo.androidapp.data.talk.TalkdbContract;
import com.example.dishfo.androidapp.listener.MessageHandler;
import com.example.dishfo.androidapp.longconnect.LongConService;
import com.example.dishfo.androidapp.mvp.talk.TalkContract;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TalkActivity extends BaseActivity implements MessageHandler,TalkContract.TalkView{
    UserInfo info=null;
    private TextView mTextViewName;
    private EasyRefreshLayout mEasyRefreshLayout;
    private RecyclerView recyclerViewMessageContents;
    private EditText editTextMessage;
    private Button buttonSend;
    private TalkAdapter adapter;

    private List<TalkInfo> talkInfos;
    private TalkContract.TalkPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.putHandler(TalkActivity.class,this);
        info= (UserInfo) getIntent().getSerializableExtra(USERINFO);
        setContentView(R.layout.activity_talk);
    }

    public void initData() {
        talkInfos=new ArrayList<>();
    }

    public void initView() {
        mTextViewName=findViewById(R.id.activity_talk_textview_name);
        mTextViewName.setText(info.name);
        mEasyRefreshLayout=findViewById(R.id.activity_talk_easyRefreshLayout);
        mEasyRefreshLayout.setRefreshHeadView(new RefreshHeaderView(this));
        mEasyRefreshLayout.setLoadMoreModel(null);
        recyclerViewMessageContents=findViewById(R.id.activity_talk_recylerview);
        editTextMessage=findViewById(R.id.activity_talk_edit_text);
        buttonSend=findViewById(R.id.activity_talk_button_send);
        buttonSend.setOnClickListener(mSendclickListener);
        adapter=new TalkAdapter(talkInfos);
        recyclerViewMessageContents.setAdapter(adapter);
        recyclerViewMessageContents.setLayoutManager(new LinearLayoutManager(this));
    }


    private View.OnClickListener mSendclickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text=editTextMessage.getText().toString();
            if(TextUtils.isEmpty(text)){
                Toast.makeText(TalkActivity.this,"输入消息为空",Toast.LENGTH_SHORT).show();
            }else {
                MessageBean bean=new MessageBean(getUser(),info.email,text,MessageBean.USERMESSAGE);
                LongConService.send(new Gson().toJson(bean));
            }
        }
    };

    private String  getUser(){
        return NetMethod.INSTANCE.getUser();
    }


    @Override
    public void dispatchMessage(Message message) {
        TalkInfo info= (TalkInfo) message.obj;
        afterRecevier(info);
    }

    @Override
    public void setPresent(TalkContract.TalkPresenter present) {
        this.presenter=present;
    }

    @Override
    public void waitToCompete() {

    }

    @Override
    public void compete(Object... args) {

    }

    @Override
    public void error(Object... args) {

    }

    @Override
    public void afterSend(TalkInfo msg) {
        presenter.onSend(msg);
    }

    @Override
    public void afterRecevier(TalkInfo msg) {
        presenter.onRecevier(msg);
    }

    private void sendMessage(int code,int arg1,Object obj){

    }

    private MyHandler handler=new MyHandler();

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {

        }
    }
}
