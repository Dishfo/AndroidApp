package com.example.dishfo.androidapp.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.bean.FollowAreaInfo;
import com.example.dishfo.androidapp.constant.ArrayConstant;

import java.util.List;

/**
 * Created by apple on 2017/12/9.
 */

public class FollowAreaAdapter extends BaseQuickAdapter<FollowAreaInfo, BaseViewHolder> {
    public FollowAreaAdapter(int layoutResId, @Nullable List<FollowAreaInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FollowAreaInfo item) {
        helper.setText(R.id.recyclerView_item_follow_area_textView_areaName, item.getmAreaName());
        Glide.with(mContext).load(ArrayConstant.LEVEL_ARRAY[Integer.valueOf(item.getmLevel())]).into((ImageView) helper.getView(R.id.recyclerView_item_follow_area_imageView_level));
        helper.getView(R.id.recyclerView_item_follow_area_imageView_delete).setVisibility(item.ismIsEdit() ? View.VISIBLE : View.GONE);
        if (item.ismIsEdit()) {
            ImageView imageViewDelete = helper.getView(R.id.recyclerView_item_follow_area_imageView_delete);
            Glide.with(mContext).load(R.mipmap.icon_ba_delete_n).into(imageViewDelete);
        }

        helper.addOnClickListener(R.id.recyclerView_item_follow_area_imageView_delete);
    }
}
