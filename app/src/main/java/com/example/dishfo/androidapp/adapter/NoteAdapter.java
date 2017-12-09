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
    public NoteAdapter(int layoutResId, @Nullable List<NoteInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoteInfo item) {
        Glide.with(mContext).load(item.getmHeadUrl()).apply(RequestOptions.circleCropTransform()).into((ImageView) helper.getView(R.id.recyclerView_item_note_imageView_head));

    }
}
