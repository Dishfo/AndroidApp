package com.example.dishfo.androidapp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dishfo.androidapp.data.DataAcess.NetMethod;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.bean.viewBean.ViewNote;

import java.util.List;

/**
 * Created by apple on 2017/12/8.
 */

public class NoteAdapter extends BaseQuickAdapter<ViewNote, BaseViewHolder> {

    private int mFunctionIcon=-1;
    private boolean useDeafultIcon=true;
    private NetMethod netMethod;


    public NoteAdapter(int layoutResId, @Nullable List<ViewNote> data) {
        super(layoutResId, data);
        netMethod=new NetMethod();
    }

    public void setFunctionIcon(int resid){
        this.mFunctionIcon=resid;
        useDeafultIcon=false;
    }

    @Override
    protected void convert(BaseViewHolder helper, ViewNote item) {
        netMethod.useGlide(mContext
                ,item.getUser().getHeadUrl()
                ,helper.getView(R.id.recyclerView_item_note_imageView_head));

        helper.setText(R.id.recyclerView_item_note_textView_nickName, item.getUser().getName());
        helper.setText(R.id.recyclerView_item_note_textView_area, item.getArea().getName());
        helper.setText(R.id.recyclerView_item_note_textView_time, item.getNote().getTime());
        helper.setText(R.id.recyclerView_item_note_textView_content, item.getNote().getContent());

        netMethod.useGlideWithoutCircle(mContext,item.getNote().getImages().get(0),
                helper.getView(R.id.recyclerView_item_note_imageView_image));

        netMethod.useGlideWithoutCircle(mContext
                ,item.getLike()==null ? R.mipmap.imageview_appreciate : R.mipmap.imageview_appreciate2
                ,helper.getView(R.id.recyclerView_item_note_imageView_appreciate));
        helper.setText(R.id.recyclerView_item_note_textView_appreciate, item.getNote().getAppreciateNumber());

        netMethod.useGlideWithoutCircle(mContext
                ,R.mipmap.imageview_read
                ,helper.getView(R.id.recyclerView_item_note_imageView_read));
        helper.setText(R.id.recyclerView_item_note_textView_read, item.getNote().getReadNumber());

        netMethod.useGlideWithoutCircle(mContext
                ,R.mipmap.imageview_discuss
                ,helper.getView(R.id.recyclerView_item_note_imageView_discuss));
        helper.setText(R.id.recyclerView_item_note_textView_discuss, item.getNote().getDiscussNumber());

        if(!useDeafultIcon) {
            Glide.with(mContext).load(R.mipmap.imageview_refresh).
                    into((ImageView) helper.getView(R.id.recyclerView_item_note_imageView_close));

        }else{
            Glide.with(mContext).load(R.mipmap.imageview_close).into((ImageView) helper.getView(R.id.recyclerView_item_note_imageView_close));

        }

        helper.addOnClickListener(R.id.recyclerView_item_note_imageView_close);
        helper.addOnClickListener(R.id.recyclerView_item_note_imageView_appreciate);
    }

}
