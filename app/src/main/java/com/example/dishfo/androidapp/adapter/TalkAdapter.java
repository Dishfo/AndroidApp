package com.example.dishfo.androidapp.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.bean.viewBean.ViewTalk;


import java.util.List;

/**
 * Created by dishfo on 18-3-15.
 */

public class TalkAdapter extends BaseMultiItemQuickAdapter<ViewTalk,BaseViewHolder> {


    public TalkAdapter(List<ViewTalk> data) {
        super(data);
        addItemType(ViewTalk.LEFT, R.layout.recylerview_talk_left);
        addItemType(ViewTalk.RIGHT,R.layout.recylerview_talk_right);
    }

    @Override
    protected void convert(BaseViewHolder helper, ViewTalk item) {
        if(item.getItemType()== ViewTalk.LEFT){
            helper.setText(R.id.recylerview_item_talk_text,item.getMessageContent());
        }else {
            helper.setText(R.id.recylerview_item_talk_text,item.getMessageContent());
        }
    }
}
