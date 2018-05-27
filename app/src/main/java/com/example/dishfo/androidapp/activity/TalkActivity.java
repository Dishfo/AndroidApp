package com.example.dishfo.androidapp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.adapter.TalkAdapter;
import com.example.dishfo.androidapp.bean.DataAdapter;
import com.example.dishfo.androidapp.bean.sqlBean.Talk;
import com.example.dishfo.androidapp.bean.sqlBean.User;
import com.example.dishfo.androidapp.bean.viewBean.ViewTalk;
import com.example.dishfo.androidapp.customview.RefreshHeaderView;
import com.example.dishfo.androidapp.longconnect.AbstractHandler;
import com.example.dishfo.androidapp.longconnect.LongConService;
import com.example.dishfo.androidapp.mvp.ModelManager;
import com.example.dishfo.androidapp.mvp.talk.TalkContract;
import com.example.dishfo.androidapp.mvp.talk.TalkPresenterImpl;

import java.util.ArrayList;
import java.util.List;

public class TalkActivity extends BaseActivity implements TalkContract.TalkView{


    private EasyRefreshLayout mEasyRefreshLayout;
    private RecyclerView recyclerViewMessageContents;
    private EditText editTextMessage;
    private TalkAdapter adapter;
    private List<ViewTalk> talkInfos;
    private TalkContract.TalkPresenter presenter;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user= (User) getIntent().getSerializableExtra(USERINFO);
        setContentView(R.layout.activity_talk);
    }

    public void initData() {
        talkInfos=new ArrayList<>();
    }

    public void initView() {
        TextView mTextViewName = findViewById(R.id.activity_talk_textview_name);
        mTextViewName.setText(user.getName());
        mEasyRefreshLayout=findViewById(R.id.activity_talk_easyRefreshLayout);
        mEasyRefreshLayout.setRefreshHeadView(new RefreshHeaderView(this));
        mEasyRefreshLayout.setLoadMoreModel(null);
        recyclerViewMessageContents=findViewById(R.id.activity_talk_recylerview);
        editTextMessage=findViewById(R.id.activity_talk_edit_text);
        Button buttonSend = findViewById(R.id.activity_talk_button_send);
        buttonSend.setOnClickListener(mSendclickListener);
        adapter=new TalkAdapter(talkInfos);
        recyclerViewMessageContents.setAdapter(adapter);
        recyclerViewMessageContents.setLayoutManager(new LinearLayoutManager(this));

        new TalkPresenterImpl(ModelManager.INSTANCE.getTalkModel(),this);
        presenter.start(ModelManager.INSTANCE.getLoginModel().getCurrentUser().getEmail(),user.getEmail());
    }

    private View.OnClickListener mSendclickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text=editTextMessage.getText().toString();
            if(TextUtils.isEmpty(text)){
                Toast.makeText(TalkActivity.this,"输入消息为空",Toast.LENGTH_SHORT).show();
            }else {
                ViewTalk viewTalk=new ViewTalk();
                viewTalk.setMessageContent(text);
                viewTalk.setOther(false);
                viewTalk.setTime("");
                viewTalk.setOtherUser(user.getEmail());
                presenter.onSend(viewTalk);
                editTextMessage.setText("");
            }
        }
    };


    @Override
    public void setPresent(TalkContract.TalkPresenter present) {
        this.presenter=present;
    }

    @Override
    public void waitToCompete() {}

    @Override
    public void compete(Object... args) {
        sendMessage((Integer) args[0],TalkContract.SUCCEED,args[1]);
    }

    @Override
    public void error(Object... args) {
        sendMessage((Integer) args[0],TalkContract.FAILED,null);
    }

    private void sendMessage(int code,int arg1,Object obj){
        Message message=handler.obtainMessage();
        message.what=code;
        message.arg1=arg1;
        message.obj=obj;
        handler.sendMessage(message);
    }

    private MyHandler handler=new MyHandler();

    @SuppressLint("HandlerLeak")
    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case TalkContract.SEND:
                    addTalk((Talk) msg.obj);
                    break;
                case TalkContract.RECEIVER:
                    addTalk((Talk) msg.obj);
                    break;
                case TalkContract.LOAD:
                    if(msg.obj instanceof List){
                        addTalk((List<Talk>) msg.obj);
                    }
                    break;
            }
        }
    }

    private void addTalk(Talk talk){
        ViewTalk viewTalk=myAdapter.convert(talk);
        adapter.addData(viewTalk);
        recyclerViewMessageContents.scrollToPosition(adapter.getItemCount()-1);
    }

    private void addTalk(List<Talk> talks){
        List<ViewTalk> viewTalks=myAdapter.convert(talks);
        adapter.addData(viewTalks);
        recyclerViewMessageContents.scrollToPosition(adapter.getItemCount()-1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LongConService.getClient().addHandler(myMessageHandler);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
        LongConService.getClient().removeHandler(this.getClass(),true);
    }

    private MyMessageHandler myMessageHandler=new MyMessageHandler();

    private class MyMessageHandler extends AbstractHandler{

        /**
         * 需要对激活状态进行确认
         * @param message message参数 Message类型
         * @return 用于在责任链上继续传递
         */
        @Override
        public Object dispatchMessage(Object message) {
            if(message==null)
                return null;
            if(!active)
                return message;
            Talk talk=new Talk();
            com.example.dishfo.androidapp.bean.sqlBean.Message msg= (com.example.dishfo.androidapp.bean.sqlBean.Message) message;
            talk.setOwner(msg.getAcceptUser().getEmail());
            talk.setSend(msg.getSendUser());
            talk.setMessage(msg.getMessage());
            talk.setOther(msg.getSendUser());
            talk.setTime(null);
            sendMessage(TalkContract.RECEIVER,TalkContract.SUCCEED,talk);
            return message;
        }
    }


    private MyDataAdapter myAdapter=new MyDataAdapter();

    private class MyDataAdapter implements DataAdapter<Talk,ViewTalk>{
        public List<ViewTalk> convert(List<Talk> talks){
            List<ViewTalk> viewTalks=new ArrayList<>();
            for(Talk talk:talks){
                viewTalks.add(this.convert(talk));
            }
            return viewTalks;
        }

        @Override
        public ViewTalk convert(Talk src) {
            ViewTalk viewTalk=new ViewTalk();
            viewTalk.setOtherUser(src.getSend().getEmail());
            viewTalk.setTime(src.getTime()==null?"":src.getTime().toString());
            viewTalk.setMessageContent(src.getMessage());
            viewTalk.setOther(!src.getOwner().equals(src.getSend().getEmail()));
            return viewTalk;
        }
    }

}
