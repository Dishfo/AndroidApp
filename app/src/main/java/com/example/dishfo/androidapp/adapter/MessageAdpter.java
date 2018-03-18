package com.example.dishfo.androidapp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dishfo.androidapp.GlideApp;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.bean.MessageInfo;


import java.util.List;

/**
 * Created by dishfo on 18-3-15.
 */

public class MessageAdpter extends BaseQuickAdapter<MessageInfo,BaseViewHolder>{


    public MessageAdpter(int layoutResId, @Nullable List<MessageInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageInfo item) {
        ImageView headImage=helper.getView(R.id.recylerview_item_talk_head);
        TextView textContent=helper.getView(R.id.recylerview_item_talk_text);
        TextView textName=helper.getView(R.id.recylerview_item_talk_name);

        GlideApp.with(mContext)
                .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                .load(item.headUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(headImage);

        helper.setText(R.id.recylerview_item_talk_name,item.userName);
        helper.setText(R.id.recylerview_item_talk_text,item.content);
    }
}
