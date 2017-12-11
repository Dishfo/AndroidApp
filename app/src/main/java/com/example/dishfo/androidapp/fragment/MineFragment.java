package com.example.dishfo.androidapp.fragment;

import android.content.Context;
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
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.adapter.MineMultipleAdapter;
import com.example.dishfo.androidapp.bean.MineInfo;
import com.example.dishfo.androidapp.decoration.LinearRecyclerViewDecoration;

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
public class MineFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int ITEMCOUNT=10;
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

    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MineFragment.
     */
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
        // Inflate the layout for this fragment
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


        mEasyRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mEasyRefreshLayout.refreshComplete();
                    }
                },2000);
            }
        });
        mEasyRefreshLayout.setLoadMoreModel(LoadModel.NONE);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new LinearRecyclerViewDecoration(getContext(),
                R.drawable.recyclerview_divider_dark3, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mMineMultipleAdapter);
    }

    private void initData() {
        mDatas=new ArrayList<>();
        MineInfo mineInfo=null;
        for(int i=0;i<ITEMCOUNT;i++){

            if(i==0){
                mineInfo=new MineInfo(1);
            }else if(i==1){
                mineInfo=new MineInfo(2);
            }else if(i==2||i==6){
                mineInfo=new MineInfo(3);
            }else{
                mineInfo=new MineInfo(4);
            }
            mDatas.add(mineInfo);
        }
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
         //   throw new RuntimeException(context.toString()
              //      + " must implement OnFragmentInteractionListener");
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
