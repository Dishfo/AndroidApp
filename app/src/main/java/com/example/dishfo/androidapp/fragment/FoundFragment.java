package com.example.dishfo.androidapp.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.baoyz.widget.PullRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.adapter.NoteAdapter;
import com.example.dishfo.androidapp.bean.NoteInfo;
import com.example.dishfo.androidapp.decoration.LinearRecyclerViewDecoration;
import com.example.dishfo.androidapp.listener.FragmentSendListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FoundFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FoundFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoundFragment extends Fragment implements
        BaseQuickAdapter.OnItemChildClickListener,BaseQuickAdapter.OnItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final  int ENTER_FOUND_NOTE=3;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<NoteInfo> mDatas;

    private FragmentSendListener mFragmentSendListener=null;
    private NoteAdapter mNoteAdapter=null;
    private ImageButton mImageButtonSearch=null;
    private RecyclerView mRecyclerViewList=null;
    private EasyRefreshLayout mEasyRefreshLayout=null;
    private OnFragmentInteractionListener mListener;

    public FoundFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoundFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoundFragment newInstance(String param1, String param2) {
        FoundFragment fragment = new FoundFragment();
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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_found, container, false);
        initData();
        initContent(view);

        return view;
    }

    private void initData() {
        mDatas=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NoteInfo noteInfo = new NoteInfo();
            noteInfo.setmHeadUrl("http://img3.imgtn.bdimg.com/it/u=4217792878,3100855251&fm=11&gp=0.jpg");
            noteInfo.setmNickName("pby");
            noteInfo.setmTime("2017-12-20");
            noteInfo.setmContent("这是我的第一张帖子");
        //    noteInfo.setmImageUrl("http://img1.imgtn.bdimg.com/it/u=1794894692,1423685501&fm=27&gp=0.jpg");
            noteInfo.setmAppreciateNumber("120");
            noteInfo.setmReadNumber("100");
            noteInfo.setmDiscussNumber("56");
            noteInfo.setmAreaName("动漫");
            mDatas.add(noteInfo);
          //  mNoteAdapter.notifyItemInserted(mDatas.size() - 1);
        }
    }


    private void initContent(View view){

        mEasyRefreshLayout=view.findViewById(R.id.fragment_found_pullrefresh_refresh);
        mEasyRefreshLayout.addEasyEvent(new MyEasyEvent());
        mImageButtonSearch=view.findViewById(R.id.fragment_found_imagebutton_search);
        mRecyclerViewList=view.findViewById(R.id.fragment_found_recyclerview_list);

        mNoteAdapter=new NoteAdapter(R.layout.recyclerview_item_note,null);

        mRecyclerViewList.setLayoutManager(
                new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL,false));
        mRecyclerViewList.addItemDecoration(new LinearRecyclerViewDecoration(getContext(),
                R.drawable.recyclerview_divider_dark2,LinearLayoutManager.VERTICAL));
        mNoteAdapter.setOnItemChildClickListener(this);
        mNoteAdapter.setOnItemClickListener(this);
        mRecyclerViewList.setAdapter(mNoteAdapter);
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
           // throw new RuntimeException(context.toString()
                   // + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mFragmentSendListener.action(ENTER_FOUND_NOTE,null);
    }

    public class MyEasyEvent implements EasyRefreshLayout.EasyEvent {

        @Override
        public void onLoadMore() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    FoundFragment.this.mEasyRefreshLayout.loadMoreComplete();
                }
            },3000);
        }

        @Override
        public void onRefreshing() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    FoundFragment.this.mEasyRefreshLayout.refreshComplete();
                }
            },3000);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
