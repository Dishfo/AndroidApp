package com.example.dishfo.androidapp.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.decoration.LinearRecyclerViewDecoration;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FoundFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FoundFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoundFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageButton mImageButtonSearch=null;
    private RecyclerView mRecyclerViewList=null;
    private MyRecyclerViewAdapter mRecyclerViewAdapter=null;
    private PullRefreshLayout mPullRefreshLayout=null;

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
        initContent(view);
        return view;
    }

    private void initContent(View view){
        mPullRefreshLayout=view.findViewById(R.id.fragment_found_pullrefresh_refresh);
        mPullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        int color= Color.parseColor("#2fc0ed");
        int colorback=Color.parseColor("#FF4081");
        mPullRefreshLayout.setColorSchemeColors(color,colorback,color,colorback);;
        mPullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
   //     Log.d("test","11111111111111111111");
        mImageButtonSearch=view.findViewById(R.id.fragment_found_imagebutton_search);
        mRecyclerViewList=view.findViewById(R.id.fragment_found_recyclerview_list);

        mRecyclerViewAdapter=new MyRecyclerViewAdapter(getContext());
      //  Log.d("test","222222222222222222222");
        mRecyclerViewList.setLayoutManager(
                new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL,false));
        mRecyclerViewList.addItemDecoration(new LinearRecyclerViewDecoration(getContext(),
                R.drawable.recyclerview_divider_dark2,LinearLayoutManager.VERTICAL));
        mRecyclerViewList.setAdapter(mRecyclerViewAdapter);
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
           // throw new RuntimeException(context.toString()
                   // + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static class MyRecyclerViewAdapter extends
            RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>{

        private Context context;

        public MyRecyclerViewAdapter(Context context){
            this.context=context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder=new MyViewHolder(LayoutInflater.
                    from(context).
                    inflate(R.layout.listview_item_area_recommend,parent,false));
       //s     Log.d("test","11111111111111111111");
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.textViewTitle.setText(""+(position+1));
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class MyViewHolder extends  RecyclerView.ViewHolder{

            TextView textViewTitle=null;
            public MyViewHolder(View itemView) {
                super(itemView);
                textViewTitle=itemView.findViewById(R.id.fragment_area_textview_module);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
