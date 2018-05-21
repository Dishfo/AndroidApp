package com.example.dishfo.androidapp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dishfo.androidapp.R;

import java.util.List;

/**
 * Created by apple on 2017/12/10.
 */

public class PictureAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public PictureAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Glide.with(mContext).load(item).into((ImageView) helper.getView(R.id.recyclerView_item_picture_imageView_picture));

        helper.addOnClickListener(R.id.recyclerView_item_picture_imageView_close);
    }
}
