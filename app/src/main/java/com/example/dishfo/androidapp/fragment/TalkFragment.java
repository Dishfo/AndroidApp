package com.example.dishfo.androidapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.adapter.MessageAdpter;
import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.bean.MessageBean;
import com.example.dishfo.androidapp.bean.MessageInfo;
import com.example.dishfo.androidapp.bean.UserInfo;
import com.example.dishfo.androidapp.customview.RefreshHeaderView;
import com.example.dishfo.androidapp.decoration.LinearRecyclerViewDecoration;
import com.example.dishfo.androidapp.listener.FragmentSendListener;
import com.example.dishfo.androidapp.listener.MessageHandler;
import com.example.dishfo.androidapp.data.DataAcess.NetMethod;
import com.example.dishfo.androidapp.mvp.ModelManager;
import com.example.dishfo.androidapp.mvp.message.MessageContract;
import com.example.dishfo.androidapp.mvp.message.MessagePresenterImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TalkFragment extends Fragment implements
        BaseQuickAdapter.OnItemClickListener,MessageContract.MessageView,
        MessageHandler{
    public static final int START_TALK=0X78;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private MessageAdpter messageAdpter;
    private EasyRefreshLayout mEasyRefreshLayout;
    private FragmentSendListener fragmentSendListener;
    private List<MessageInfo> messageInfos=null;
    private OnFragmentInteractionListener mListener;
    private MessageContract.MessagePresenter presenter;


    public TalkFragment() {
        messageInfos=new ArrayList<>();
        MyApplication.putHandler(TalkFragment.class,this);
    }

    public static TalkFragment newInstance(String param1, String param2) {
        TalkFragment fragment = new TalkFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_talk, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initData() {
        messageInfos=new ArrayList<>();

    }

    private void initView(View view){
        mEasyRefreshLayout=view.findViewById(R.id.fragment_talk_easyrefreshlayout);
        mEasyRefreshLayout.setLoadMoreModel(null);
        mEasyRefreshLayout.setRefreshHeadView(new RefreshHeaderView(getContext()));
        mEasyRefreshLayout.addEasyEvent(this.easyEvent);


        mRecyclerView=view.findViewById(R.id.fragment_talk_recyclerview);
        messageAdpter=new MessageAdpter(R.layout.recylerview_talk_item,messageInfos);
        mRecyclerView.setAdapter(messageAdpter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new LinearRecyclerViewDecoration(getContext(),
                R.drawable.recyclerview_divider_dark1, LinearLayoutManager.VERTICAL));
        messageAdpter.setOnItemClickListener(itemClickListener);
        new MessagePresenterImpl(ModelManager.INSTANCE.getMessageModel(),this);
        presenter.start(NetMethod.INSTANCE.getUser());
    }

    @Override
    public void onResume() {
        super.onResume();
        messageAdpter.notifyDataSetChanged();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof FragmentSendListener){
            this.fragmentSendListener = (FragmentSendListener) context;
        }
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setFragmentSendListener(FragmentSendListener listener){
        this.fragmentSendListener=listener;
    }


    private BaseQuickAdapter.OnItemClickListener itemClickListener=
            new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if(fragmentSendListener!=null){
                        MessageInfo info=messageInfos.get(position);
                        UserInfo userInfo=new UserInfo();
                        userInfo.name=info.userName;
                        userInfo.head=info.headUrl;
                        userInfo.email=info.email;
                        fragmentSendListener.action(START_TALK,userInfo);
                    }
                }
            };



    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void setPresent(MessageContract.MessagePresenter present) {
        this.presenter=present;
    }

    @Override
    public void waitToCompete() {

    }

    @Override
    public void compete(Object... args) {
        sendMessage((int)args[0],MessageContract.SUCCEED,args[1]);

    }

    @Override
    public void error(Object... args) {
        sendMessage((int)args[0],MessageContract.FAILED,null);
    }

    @Override
    public void AfterRecevier(com.example.dishfo.androidapp.sqlBean.Message message) {
        presenter.onRecevier(message);
    }

    private void addMessage(MessageInfo info){
        if(messageAdpter!=null){
            Iterator<MessageInfo> iterator=messageInfos.iterator();
            int i=0;
            while(iterator.hasNext()){
                MessageInfo next=iterator.next();
                if(next.email==info.email){
                    break;
                }
                i++;
            }
            if(i==messageInfos.size()){
                messageAdpter.addData(info);
            }else {
                messageInfos.set(i,info);
            }

        }
    }

    private void addMessages(List<MessageInfo> infos){
        if(messageAdpter!=null){
            messageAdpter.addData(infos);
        }
    }

    @Override
    public void dispatchMessage(Message message) {
        switch (message.what){
            case MessageBean.GETUSERS:
                    com.example.dishfo.androidapp.sqlBean.Message bean= (com.example.dishfo.androidapp.sqlBean.Message) message.obj;
                presenter.onRecevier(bean);
                break;
        }
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    private EasyRefreshLayout.EasyEvent easyEvent=new EasyRefreshLayout.EasyEvent() {
        @Override
        public void onLoadMore() {

        }

        @Override
        public void onRefreshing() {
            mEasyRefreshLayout.refreshComplete();
        }
    };

    private void sendMessage(int code,int arg1,Object obj){
        Message message=handler.obtainMessage();
        message.what=code;
        message.arg1=arg1;
        message.obj=obj;
        handler.sendMessage(message);
    }

    private MyHandler handler=new MyHandler();

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MessageContract.MESSAGELOAD:
                    if(msg.arg1==MessageContract.SUCCEED){
                        TalkFragment.this.addMessages((List<MessageInfo>) msg.obj);
                    }else {
                        Toast.makeText(TalkFragment.this.getContext(),"error",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case MessageContract.MESSAGEGET:
                    if(msg.arg1==MessageContract.SUCCEED){
                        TalkFragment.this.addMessage((MessageInfo) msg.obj);
                    }else {
                        Toast.makeText(TalkFragment.this.getContext(),"error",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }


}
