package com.example.dishfo.androidapp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.bean.NoteInfo;

import java.util.List;

/**
 * Created by apple on 2017/12/8.
 */

public class NoteAdapter extends BaseQuickAdapter<NoteInfo, BaseViewHolder> {

    private int mFunctionIcon=-1;
    private boolean useDeafultIcon=true;


    public NoteAdapter(int layoutResId, @Nullable List<NoteInfo> data) {
        super(layoutResId, data);
    }

    public void setFunctionIcon(int resid){
        this.mFunctionIcon=resid;
        useDeafultIcon=false;
    }

    @Override
    protected void convert(BaseViewHolder helper, NoteInfo item) {

        Glide.with(mContext).load(item.getmHeadUrl()).apply(RequestOptions.circleCropTransform()).into((ImageView) helper.getView(R.id.recyclerView_item_note_imageView_head));
        helper.setText(R.id.recyclerView_item_note_textView_nickName, item.getmNickName());
        helper.setText(R.id.recyclerView_item_note_textView_area, item.getmAreaName());
        helper.setText(R.id.recyclerView_item_note_textView_time, item.getmTime());
        helper.setText(R.id.recyclerView_item_note_textView_content, item.getmContent());
        Glide.with(mContext).load(item.getmImageUrl()).into((ImageView) helper.getView(R.id.recyclerView_item_note_imageView_image));
        Glide.with(mContext).load(R.mipmap.imageview_appreciate).into((ImageView) helper.getView(R.id.recyclerView_item_note_imageView_appreciate));
        helper.setText(R.id.recyclerView_item_note_textView_appreciate, item.getmAppreciateNumber());
        Glide.with(mContext).load(R.mipmap.imageview_read).into((ImageView) helper.getView(R.id.recyclerView_item_note_imageView_read));
        helper.setText(R.id.recyclerView_item_note_textView_read, item.getmReadNumber());
        Glide.with(mContext).load(R.mipmap.imageview_discuss).into((ImageView) helper.getView(R.id.recyclerView_item_note_imageView_discuss));
        helper.setText(R.id.recyclerView_item_note_textView_discuss, item.getmDiscussNumber());

        if(!useDeafultIcon) {
            Glide.with(mContext).load(R.mipmap.imageview_refresh).
                    into((ImageView) helper.getView(R.id.recyclerView_item_note_imageView_close));

        }else{
            Glide.with(mContext).load(R.mipmap.imageview_close).into((ImageView) helper.getView(R.id.recyclerView_item_note_imageView_close));

        }
    }
}
