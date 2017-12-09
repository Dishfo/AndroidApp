package com.example.dishfo.androidapp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.bean.MessageInfo;

import java.util.List;

/**
 * Created by dishfo on 17-12-9.
 */

public class MessageAdapter extends BaseQuickAdapter<MessageInfo,BaseViewHolder> {


    public MessageAdapter(int layoutResId, @Nullable List<MessageInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageInfo item) {
        Glide.with(mContext).load(R.mipmap.ic_launcher_round).
                apply(RequestOptions.circleCropTransform()).
                into((ImageView) helper.getView(R.id.recyclerview_item_msg_headimage));

        ((TextView)helper.getView(R.id.recyclerview_item_msg_content)).setText(item.content);
        ((TextView)helper.getView(R.id.recyclerview_item_msg_date)).setText(item.date);
        ((TextView)helper.getView(R.id.recyclerview_item_msg_name)).setText(item.username);
    }
}
