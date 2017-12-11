package com.example.dishfo.androidapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.adapter.MessageAdapter;
import com.example.dishfo.androidapp.bean.MessageInfo;
import com.example.dishfo.androidapp.decoration.LinearRecyclerViewDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment implements
        BaseQuickAdapter.OnItemClickListener,BaseQuickAdapter.OnItemChildClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int[] ICON_RESID=new int[]{R.mipmap.imageview_words,
            R.mipmap.imageview_like,R.mipmap.imageview_follow,
            R.mipmap.imageview_other};
    private static String[] LABELS=new String[]{"评论","喜欢和点赞","关注","其他"};

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<Integer> numbers=null;
    private List<MessageInfo> mDatas=null;
    private RecyclerView mRecyclerViewFunction=null;
    private ImageButton mImageButtonSeach=null;
    private ImageButton mImageButtonSetting=null;
    private RecyclerView mRecyclerViewMsg=null;
    private EasyRefreshLayout mEasyRefreshLayout;
    private MessageAdapter mMessageAdapter=null;
    private MyRecyclerViewAdapter myRecyclerViewAdapter=null;
    private OnFragmentInteractionListener mListener;

    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
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
        View view=inflater.inflate(R.layout.fragment_message, container, false);
        initData();
        initContentView(view);
        return view;
    }

    private void initData() {
        mDatas=new ArrayList<>();
        for(int i=0;i<5;i++){
            MessageInfo info=new MessageInfo();
            info.username="sxh";
            info.date="2017-07-15";
            info.content="asdfgh......";
            info.imageheadUrl="http://img3.imgtn.bdimg.com/it/u=4217792878,3100855251&fm=11&gp=0.jpg";
            mDatas.add(info);
        }
        numbers=new ArrayList<>();
        numbers.add(0);
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
    }

    private void initContentView(View view) {
        Log.d("test","dddddddddddddddddddddddddddd");
        mRecyclerViewFunction=view.findViewById(R.id.fragment_msg_recyclerview_function);
        mImageButtonSeach=view.findViewById(R.id.fragment_msg_imagebutton_search);
        mImageButtonSetting=view.findViewById(R.id.fragment_msg_imagebutton_setting);
        mRecyclerViewMsg=view.findViewById(R.id.fragment_msg_recyclerview_container);
        mEasyRefreshLayout=view.findViewById(R.id.fragment_msg_refreshlayout_container);
        mMessageAdapter=new MessageAdapter(R.layout.recyclerview_item_msg,mDatas);

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
        myRecyclerViewAdapter=
                new MyRecyclerViewAdapter(R.layout.recyclerview_item_msg_function,
                        numbers);

        mRecyclerViewFunction.setLayoutManager(new GridLayoutManager(getContext(),3){
            @Override
            public boolean canScrollVertically() {
                return  false;
            }
        });
        mRecyclerViewMsg.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewMsg.addItemDecoration(new LinearRecyclerViewDecoration(getContext(),
                R.drawable.recyclerview_divider_dark1,LinearLayoutManager.VERTICAL));

        myRecyclerViewAdapter.setOnItemClickListener(this);
        mMessageAdapter.setOnItemClickListener(this);
        mMessageAdapter.setOnItemChildClickListener(this);


        mRecyclerViewFunction.setAdapter(myRecyclerViewAdapter);
        mRecyclerViewMsg.setAdapter(mMessageAdapter);
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
          //  throw new RuntimeException(context.toString()
                  //  + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Log.d("test","111111111111111");
        Toast.makeText(getContext(),"ITEM",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Toast.makeText(getContext(),"delete",Toast.LENGTH_LONG).show();
    }


    class MyRecyclerViewAdapter extends BaseQuickAdapter<Integer,BaseViewHolder>{

        public MyRecyclerViewAdapter(int layoutResId, @Nullable List<Integer> data) {
            super(layoutResId, data);
        }

        @Override
        public int getItemCount() {
            return super.getItemCount()+4;
        }


        @Override
        protected void convert(BaseViewHolder helper, Integer item) {

            int i=item;
            Log.d("test"," "+i);
            ImageView imageView=helper.getView(R.id.recyckerview_item_function_imageview);
            TextView textView=helper.getView(R.id.recyclerview_item_text);
            imageView.setImageResource(ICON_RESID[i]);
            textView.setText(LABELS[i]);

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
