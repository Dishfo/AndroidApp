package com.example.dishfo.androidapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.adapter.NoteAdapter;
import com.example.dishfo.androidapp.bean.NoteInfo;
import com.example.dishfo.androidapp.decoration.GridRecyclerViewDecoration;
import com.example.dishfo.androidapp.listener.FragmentSendListener;

import java.util.ArrayList;
import java.util.List;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AreaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AreaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AreaFragment extends Fragment implements View.OnClickListener
        ,BaseQuickAdapter.OnItemClickListener,BaseQuickAdapter.OnItemChildClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final int ENTER_RECOMMEND= 1;
    public static final int ENTER_MODULE= 2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RECOMMEND_COUNT=1;
    private static final int MODULE_COUNT=12;
    private static final String[] MODULE_TITLE=new String[]{"我关注的人",
    "我关注的专区","学习天堂","运动广场","旅游天地",
    "游戏","娱乐","明星","阅读","美食","动漫",""};
    private  static final int[] MODULE_IC=new int[]{R.mipmap.abc_btn_radio_to_on_mtrl_015};
    private List<NoteInfo> mDatas=null;

    // TODO: Rename and change types of parameters
    private FragmentSendListener mFragmentSendListener=null;
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerViewRecommend=null;
    private NoteAdapter mNoteAdapter=null;
    private ScrollView mScrollView=null;
    private RecyclerViewAdapter mRecyclerViewAdapter=null;
    private ImageButton mButtonSearch=null;
    private RecyclerView mRecyclerViewModule=null;

    private OnFragmentInteractionListener mListener;

    public AreaFragment() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AreaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AreaFragment newInstance(String param1, String param2) {
        AreaFragment fragment = new AreaFragment();
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
        View view=inflater.inflate(R.layout.fragment_area, container, false);
        initData();
        initContent(view);
        return view;
    }

    private void initData() {
        mDatas=new ArrayList<>();
        NoteInfo noteInfo=new NoteInfo();
        noteInfo.setmHeadUrl("http://img3.imgtn.bdimg.com/it/u=4217792878,3100855251&fm=11&gp=0.jpg");
        noteInfo.setmNickName("pby");
        noteInfo.setmTime("2017-12-20");
        noteInfo.setmContent("这是我的第一张帖子");
        noteInfo.setmImageUrl("http://img1.imgtn.bdimg.com/it/u=1794894692,1423685501&fm=27&gp=0.jpg");
        noteInfo.setmAppreciateNumber("120");
        noteInfo.setmReadNumber("100");
        noteInfo.setmDiscussNumber("56");
        noteInfo.setmAreaName("动漫");
        mDatas.add(noteInfo);
    }

    private void initContent(View view){
        mScrollView=view.findViewById(R.id.fragment_area_scrollview_container);
        mRecyclerViewRecommend=view.findViewById(R.id.fragment_area_recyclerview_recommend);

        mNoteAdapter=new NoteAdapter(R.layout.recyclerview_item_note,mDatas);
        mNoteAdapter.setFunctionIcon(R.mipmap.imageview_refresh);
        mRecyclerViewAdapter=new RecyclerViewAdapter(getContext());

        mRecyclerViewRecommend.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return  false;
            }
        });
        mRecyclerViewRecommend.setAdapter(mNoteAdapter);

        mRecyclerViewModule=view.findViewById(R.id.fragment_area_recyclerview_module);
        mButtonSearch=view.findViewById(R.id.fragment_area_imagebutton_search);

        mRecyclerViewModule.setLayoutManager(new GridLayoutManager(getContext(),2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecyclerViewModule.setItemAnimator(new DefaultItemAnimator());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mRecyclerViewModule.addItemDecoration(
                    new GridRecyclerViewDecoration(R.drawable.recyclerview_divider_dark1,
                            2,getContext()));
        }
        mRecyclerViewModule.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewModule.setScrollContainer(false);
        mScrollView.setVerticalScrollBarEnabled(false);
        mNoteAdapter.setOnItemClickListener(this);
        mNoteAdapter.setOnItemChildClickListener(this);
        OverScrollDecoratorHelper.setUpOverScroll(mScrollView);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        Toast.makeText(getContext(),"module 点击事件",Toast.LENGTH_SHORT).show();
        mFragmentSendListener.action(ENTER_MODULE,null);
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
        if(context instanceof  FragmentSendListener){
            mFragmentSendListener= (FragmentSendListener) context;
        }
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
          //  throw new RuntimeException(context.toString()
            //        + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Toast.makeText(getContext()," recommend 点击事件",Toast.LENGTH_SHORT).show();
        mFragmentSendListener.action(ENTER_RECOMMEND,null);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Toast.makeText(getContext()," child 点击事件",Toast.LENGTH_SHORT).show();
    }

    public  class RecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

        private Context context;

        public RecyclerViewAdapter(Context context){
            this.context=context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            MyViewHolder holder=new MyViewHolder(LayoutInflater.
                    from(context).
                    inflate(R.layout.recyclerview_item_area_module,parent,false));
            holder.itemView.setOnClickListener(AreaFragment.this);
            return holder;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.textView.setText(MODULE_TITLE[position]);
            holder.itemView.setTag(position);
            if(position!=11)
            holder.imageView.setImageDrawable(context.getDrawable(R.mipmap.abc_btn_radio_to_on_mtrl_015));
        }

        @Override
        public int getItemCount() {
            return MODULE_COUNT;
        }

        public class MyViewHolder extends  RecyclerView.ViewHolder{
            TextView textView=null;
            ImageView imageView=null;
            public MyViewHolder(View itemView) {
                super(itemView);
                textView=itemView.findViewById(R.id.fragment_area_textview_module_label);
                imageView=itemView.findViewById(R.id.fragment_area_imageview_module_label);
            }
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

        void onFragmentInteraction(Uri uri);
    }
}
