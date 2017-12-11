package com.example.dishfo.androidapp.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.bean.MineInfo;

import java.util.List;

/**
 * Created by dishfo on 17-12-9.
 */

public class MineMultipleAdapter extends BaseMultiItemQuickAdapter<MineInfo,BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    private View.OnClickListener mItemlistener;

    public MineMultipleAdapter(List<MineInfo> data) {
        super(data);
        addItemType(MineInfo.FIRST_TYPE, R.layout.recyclerview_item_mine_1);
        addItemType(MineInfo.SECOND_TYPE, R.layout.recyclerview_item_mine_2);
        addItemType(MineInfo.THIRD_TYPE, R.layout.recyclerview_item_mine_3);
        addItemType(MineInfo.FOURTH_TYPE, R.layout.recyclerview_item_mine_4);
    }

    public MineMultipleAdapter(List<MineInfo> data, View.OnClickListener listener){
        this(data);
        this.mItemlistener=listener;
    }

    @Override
    protected void convert(BaseViewHolder helper, MineInfo item) {
        switch (item.getItemType()){
            case MineInfo.FIRST_TYPE:
                break;
            case MineInfo.SECOND_TYPE:
                break;
            case MineInfo.THIRD_TYPE:
                break;
            case MineInfo.FOURTH_TYPE:
                helper.itemView.setOnClickListener(mItemlistener);
                helper.itemView.setTag(item.label);
                break;
            default:
                break;
        }
    }
}
