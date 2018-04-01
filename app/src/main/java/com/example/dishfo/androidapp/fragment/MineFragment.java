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
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.adapter.MineMultipleAdapter;
import com.example.dishfo.androidapp.bean.MineInfo;
import com.example.dishfo.androidapp.customview.RefreshHeaderView;
import com.example.dishfo.androidapp.decoration.LinearRecyclerViewDecoration;
import com.example.dishfo.androidapp.listener.FragmentSendListener;
import com.example.dishfo.androidapp.mvp.ModelManager;
import com.example.dishfo.androidapp.mvp.UserInfo.UserInfoPresentImpl;
import com.example.dishfo.androidapp.mvp.UserInfo.UserInfoTaskContract;
import com.example.dishfo.androidapp.sqlBean.User;
import com.example.dishfo.androidapp.viewBean.ViewMine;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends Fragment implements View.OnClickListener,UserInfoTaskContract.UserInfoView{

    public static final int ENTER_SETTING=10;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int ITEMCOUNT=10;
    private static final int[] RESIDS=new int[]{R.mipmap.image_mine_collect,
    R.mipmap.image_mine_like,R.mipmap.image_mine_follow,
            R.mipmap.image_mine_history,R.mipmap.image_mine_words,
            R.mipmap.image_mine_good};
    private static final String[] LABELS=new String[]{"收藏的帖子",
            "喜欢的帖子","关注的专区","浏览记录",
    "我的评论","我的点赞"};

    private FragmentSendListener mFragmentSendListener=null;

    // TODO: Rename and change types of parameters
    private List<MineInfo> mDatas=null;
    private String mParam1;
    private String mParam2;
    private ImageButton mImageButtonSetting=null;
    private ImageButton mImageButtonSearch=null;
    private EasyRefreshLayout mEasyRefreshLayout=null;
    private RecyclerView mRecyclerView=null;
    private MineMultipleAdapter mMineMultipleAdapter=null;

    private OnFragmentInteractionListener mListener;
    private UserInfoTaskContract.UserInfoPresent present;
    private ViewMine viewMine;
    private PopupWindow popupWindow;

    public MineFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
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
        View view=inflater.inflate(R.layout.fragment_mine, container, false);
        initData();
        initContentView(view);
        return view;
    }

    private void initContentView(View view) {
        mEasyRefreshLayout=view.findViewById(R.id.fragment_mine_refreshlayout_refresh);

        mImageButtonSearch=view.findViewById(R.id.fragment_mine_imagebutton_search);
        mImageButtonSetting=view.findViewById(R.id.fragment_mine_imagebutton_setting);
        mRecyclerView=view.findViewById(R.id.fragment_mine_recylerview_setting);
        mMineMultipleAdapter=new MineMultipleAdapter(mDatas,this);

        mImageButtonSetting.setOnClickListener(this);

        mEasyRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onRefreshing() {
                present.start();
            }
        });
        mEasyRefreshLayout.setLoadMoreModel(LoadModel.NONE);
        mEasyRefreshLayout.setRefreshHeadView(new RefreshHeaderView(getContext()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new LinearRecyclerViewDecoration(getContext(),
                R.drawable.recyclerview_divider_dark1, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mMineMultipleAdapter);

        new UserInfoPresentImpl(ModelManager.INSTANCE.getUserInfoModel(),this);
        waitToCompete();
        present.start();

    }

    private void initData() {
        mDatas=new ArrayList<>();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof FragmentSendListener){
            mFragmentSendListener= (FragmentSendListener) context;
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

    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(),"item click",Toast.LENGTH_SHORT).show();
        switch (v.getId()){
            case R.id.fragment_mine_imagebutton_setting:
                User user=viewMine.getUser();
                mFragmentSendListener.action(ENTER_SETTING,user);
                break;
            case R.id.fragment_mine_imagebutton_search:
                break;

        }
    }

    @Override
    public void setPresent(UserInfoTaskContract.UserInfoPresent present) {
        this.present=present;
    }

    @Override
    public void waitToCompete() {
        startWait();
    }

    @Override
    public void compete(Object... args) {
        viewMine= (ViewMine) args[1];
        sendMessage((int)args[0],UserInfoTaskContract.SUCCEED,args[1]);
    }

    @Override
    public void error(Object... args) {
        sendMessage((int)args[0],UserInfoTaskContract.FAILED,null);
    }


    public void showUserInfo(ViewMine mineMessage) {
        getUserInfo(mineMessage);
    }


    public void showError(Object... args) {
        Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
    }


    public void showCompete(Object... args) {
        showUserInfo((ViewMine) args[0]);
    }

    private void getUserInfo(ViewMine msg){
        mDatas.clear();
        MineInfo mineInfo=null;

        int k=0;
        for(int i=0;i<ITEMCOUNT;i++){

            if(i==0){
                mineInfo=new MineInfo(1);
                mineInfo.headimageUrl=(msg.getUser()==null?"...":msg.getUser().getHeadUrl());
                mineInfo.name=(msg.getUser()==null?"...":msg.getUser().getName());
                mineInfo.autograph="....";

            }else if(i==1){
                mineInfo=new MineInfo(2);
                mineInfo.notes=msg.getNotes()==null?0:msg.getNotes().size();
                mineInfo.fans=msg.getFans()==null?0:msg.getFans().size();
                mineInfo.follow=msg.getFollowUsers()==null?0:msg.getFollowUsers().size();
            }else if(i==2||i==6){
                mineInfo=new MineInfo(3);
            }else{
                mineInfo=new MineInfo(4);
                mineInfo.label=LABELS[k];
                mineInfo.imageresid=RESIDS[k];
                mineInfo.number= getField(mineInfo.label,msg);
                k++;
            }
            mDatas.add(mineInfo);
        }
        mMineMultipleAdapter.notifyDataSetChanged();
    }

    private int getField(String label, ViewMine msg){
        switch (label){
            case "收藏的帖子":
                return (msg.getCollections()==null?0:msg.getCollections().size());
            case "喜欢的帖子":
                return (msg.getLikes()==null?0:msg.getLikes().size());
            case "关注的专区":
                return  (msg.getFollowAreas()==null?0:msg.getFollowAreas().size());
            case "浏览记录":
                return 0;
            case "我的评论":
                return (msg.getDiscusses()==null?0:msg.getDiscusses().size());
            default:
                return 0;
        }
    }

    private void startWait(){
        mFragmentSendListener.action(BaseActivity.DARKWINDOW,null);
    }

    private void stopWait(){
        mFragmentSendListener.action(BaseActivity.LIGHTWINDOW,null);
        if(mEasyRefreshLayout.isRefreshing()){
            mEasyRefreshLayout.refreshComplete();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void sendMessage(int code,int arg1,Object object){
        Message message=myhandler.obtainMessage();
        message.what=code;
        message.obj=object;
        message.arg1=arg1;
        myhandler.sendMessage(message);
    }


    @SuppressLint("HandlerLeak")
    private Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MineFragment.this.stopWait();
            switch (msg.what){
                case UserInfoTaskContract.USER:
                    if(msg.arg1==UserInfoTaskContract.SUCCEED){
                        MineFragment.this.showCompete(msg.obj);
                    }else {
                        MineFragment.this.showError();
                    }
            }
        }
    };



}
