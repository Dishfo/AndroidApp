package com.example.dishfo.androidapp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.bean.ExpressionInfo;

import java.util.List;

/**
 * Created by apple on 2017/12/10.
 */

public class ExpressionAdapter extends BaseQuickAdapter<ExpressionInfo, BaseViewHolder> {

    public ExpressionAdapter(int layoutResId, @Nullable List<ExpressionInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExpressionInfo item) {
        Glide.with(mContext).load(item.getId()).into((ImageView) helper.getView(R.id.recyclerView_item_expression_imageView_expression));


        helper.addOnClickListener(R.id.recyclerView_item_expression_imageView_expression);
    }
}
