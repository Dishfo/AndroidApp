package com.example.dishfo.androidapp.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.bean.TalkInfo;


import java.util.List;

/**
 * Created by dishfo on 18-3-15.
 */

public class TalkAdapter extends BaseMultiItemQuickAdapter<TalkInfo,BaseViewHolder> {


    public TalkAdapter(List<TalkInfo> data) {
        super(data);
        addItemType(TalkInfo.LEFT, R.layout.recylerview_talk_left);
        addItemType(TalkInfo.RIGHT,R.layout.recylerview_talk_right);
    }

    @Override
    protected void convert(BaseViewHolder helper, TalkInfo item) {
        if(item.getItemType()==TalkInfo.LEFT){
            helper.setText(R.id.recylerview_item_talk_text,item.messageContent);
        }else {
            helper.setText(R.id.recylerview_item_talk_text,item.messageContent);
        }
    }
}
