package com.example.dishfo.androidapp.adapter;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.bean.SettingInfo;

import java.util.List;

import static com.example.dishfo.androidapp.bean.SettingInfo.FIRST_TYPE;
import static com.example.dishfo.androidapp.bean.SettingInfo.FOURTH_TYPE;
import static com.example.dishfo.androidapp.bean.SettingInfo.SECOND_TYPE;
import static com.example.dishfo.androidapp.bean.SettingInfo.THRID_TYPE;

/**
 * Created by dishfo on 17-12-9.
 */

public class SettingAdapter extends BaseMultiItemQuickAdapter<SettingInfo,BaseViewHolder>{
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    private View.OnClickListener mListener;

    public SettingAdapter(List<SettingInfo> data) {
        super(data);
        addItemType(FIRST_TYPE, R.layout.recyclerview_item_setting_1);
        addItemType(SECOND_TYPE, R.layout.recyclerview_item_setting_2);
        addItemType(THRID_TYPE, R.layout.recyclerview_item_setting_3);
        addItemType(FOURTH_TYPE, R.layout.recyclerview_item_setting_4);
        Log.d("test","lllllllllllllllllllll");
    }


    public SettingAdapter(List<SettingInfo> data, View.OnClickListener mListener) {
        this(data);
        this.mListener = mListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, SettingInfo item) {
        helper.itemView.setTag(item.label);

        switch (item.getItemType()){
            case FIRST_TYPE:

                ((TextView)helper.getView(R.id.recyclerview_item_textview)).setText(item.label);
                break;
            case SECOND_TYPE:
                helper.itemView.setOnClickListener(mListener);
                ((TextView)helper.getView(R.id.recyclerview_item_textview)).setText(item.label);
               // ((TextView)helper.getView(R.id.recyclerview_item_textview)).setText(item.label);
                break;
            case THRID_TYPE:
                helper.itemView.setOnClickListener(mListener);
                ((TextView)helper.getView(R.id.recyclerview_item_textview)).setText(item.label);
                ((TextView)helper.getView(R.id.recyclerview_item_textview2)).setText(item.essue);
                break;
            case FOURTH_TYPE:
                helper.getView(R.id.recyclerview_item_button).setOnClickListener(mListener);
                ((Button)helper.getView(R.id.recyclerview_item_button)).setText(item.label);
                break;
        }
    }
}
