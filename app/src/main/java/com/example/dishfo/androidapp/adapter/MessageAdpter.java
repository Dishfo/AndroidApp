package com.example.dishfo.androidapp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dishfo.androidapp.data.DataAcess.NetMethod;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.bean.viewBean.ViewMessage;


import java.util.List;

/**
 *
 * Created by dishfo on 18-3-15.
 */

public class MessageAdpter extends BaseQuickAdapter<ViewMessage,BaseViewHolder>{


    public MessageAdpter(int layoutResId, @Nullable List<ViewMessage> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ViewMessage item) {
        NetMethod netMethod=new NetMethod();

        ImageView headImage=helper.getView(R.id.recylerview_item_talk_head);
        TextView textContent=helper.getView(R.id.recylerview_item_talk_text);
        TextView textName=helper.getView(R.id.recylerview_item_talk_name);

        netMethod.useGlide(mContext,item.getSend().getHeadUrl(),headImage);

        helper.setText(R.id.recylerview_item_talk_name,item.getSend().getName());
        helper.setText(R.id.recylerview_item_talk_text,item.getContent());
    }
}
