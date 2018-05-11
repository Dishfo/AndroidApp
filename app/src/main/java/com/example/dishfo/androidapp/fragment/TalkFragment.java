package com.example.dishfo.androidapp.fragment;

import android.annotation.SuppressLint;
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
import com.example.dishfo.androidapp.bean.viewBean.ViewMessage;
import com.example.dishfo.androidapp.customview.RefreshHeaderView;
import com.example.dishfo.androidapp.decoration.LinearRecyclerViewDecoration;
import com.example.dishfo.androidapp.listener.FragmentSendListener;
import com.example.dishfo.androidapp.longconnect.AbstractHandler;
import com.example.dishfo.androidapp.longconnect.LongConService;
import com.example.dishfo.androidapp.longconnect.bean.InstanceMessage;
import com.example.dishfo.androidapp.mvp.ModelManager;
import com.example.dishfo.androidapp.mvp.message.MessageContract;
import com.example.dishfo.androidapp.mvp.message.MessagePresenterImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TalkFragment extends Fragment implements
        MessageContract.MessageView{
    public static final int START_TALK=0X78;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1="";
    private String mParam2="";
    private RecyclerView mRecyclerView;
    private MessageAdpter messageAdpter;
    private EasyRefreshLayout mEasyRefreshLayout;
    private FragmentSendListener fragmentSendListener;
    private List<ViewMessage> messageInfos=null;
    private OnFragmentInteractionListener mListener;
    private MessageContract.MessagePresenter presenter;
    private MyDataAdapter dataAdapter;


    public TalkFragment() {
        messageInfos=new ArrayList<>();
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
        LongConService.getClient().addHandler(mhandler);
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
        dataAdapter=new MyDataAdapter();
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
        presenter.start(ModelManager.INSTANCE.getLoginModel().getCurrentUser().getEmail());
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
                        ViewMessage viewMessage=messageInfos.get(position);
                        fragmentSendListener.action(START_TALK,viewMessage.getSend());
                    }
                }
            };





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



    private void addMessage(ViewMessage info){
        if(messageAdpter!=null){
            Iterator<ViewMessage> iterator=messageInfos.iterator();
            int i=0;
            while(iterator.hasNext()){
                ViewMessage next=iterator.next();
                if(next.getSend().getName().equals(info.getSend().getName())){
                    break;
                }else {
                    i++;
                }
            }
            if(i==messageInfos.size()){
                messageAdpter.addData(info);
            }else {
                messageInfos.set(i,info);
            }
        }
        messageAdpter.notifyDataSetChanged();
    }

    private void addMessages(List<ViewMessage> infos){
        if(messageAdpter!=null){
            messageAdpter.addData(infos);
        }
    }



    private final class MyMessageHandler extends AbstractHandler{

        @Override
        public com.example.dishfo.androidapp.bean.sqlBean.Message dispatchMessage(Object message) {
            if(message==null)
                return null;
            Message message1=handler.obtainMessage();
            message1.what=MessageContract.SAVE;
            message1.arg1=MessageContract.SUCCEED;
            message1.obj=message;
            handler.sendMessage(message1);
            return (com.example.dishfo.androidapp.bean.sqlBean.Message) message;
        }

    }
    private MyMessageHandler mhandler=new MyMessageHandler();

    @Override
    public void onPause() {
        super.onPause();
        LongConService.getClient().removeHandler(mhandler.getClass(),false);
    }




    @Override
    public void AfterRecevier(InstanceMessage message) {

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
            presenter.start(ModelManager.INSTANCE.getLoginModel().getCurrentUser().getEmail());
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

    @SuppressLint("HandlerLeak")
    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            mEasyRefreshLayout.refreshComplete();
            switch (msg.what){
                case MessageContract.MESSAGELOAD:
                    if(msg.arg1==MessageContract.SUCCEED){
                        addMessage(dataAdapter.convert((com.example.dishfo.androidapp.bean.sqlBean.Message) msg.obj));
                    }else {
                        Toast.makeText(TalkFragment.this.getContext(),"error",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case MessageContract.MESSAGEGET:
                    if(msg.arg1==MessageContract.SUCCEED){

                        List<ViewMessage> list=dataAdapter.convert((List<com.example.dishfo.androidapp.bean.sqlBean.Message>) msg.obj);
                        addMessages(list);
                    }else {
                        Toast.makeText(TalkFragment.this.getContext(),"error",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case MessageContract.SAVE:
                    if(msg.arg1==MessageContract.SUCCEED){
                        addMessage(dataAdapter.convert((com.example.dishfo.androidapp.bean.sqlBean.Message) msg.obj));
                    }else {

                    }
                    break;
            }
        }
    }

    private class MyDataAdapter implements DataAdapter<com.example.dishfo.androidapp.bean.sqlBean.Message,ViewMessage>{

        public List<ViewMessage> convert(List<com.example.dishfo.androidapp.bean.sqlBean.Message> messages){
            List<ViewMessage> list=new ArrayList<>();
            for(com.example.dishfo.androidapp.bean.sqlBean.Message message:messages){
                list.add(convert(message));
            }
            return list;
        }

        @Override
        public ViewMessage convert(com.example.dishfo.androidapp.bean.sqlBean.Message message) {
            ViewMessage result=new ViewMessage();
            result.setContent(message.getMessage());
            result.setEmail(message.getAcceptUser().getEmail());
            result.setSend(message.getSendUser());
            result.setTime(message.getTime()==null?"":message.getTime().toString());
            return result;

        }
    }

    public interface DataAdapter<F,T>{
        T convert(F f);
    }



}
