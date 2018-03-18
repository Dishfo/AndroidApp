package com.example.dishfo.androidapp.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dishfo.androidapp.GlideApp;
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
                GlideApp.with(mContext).load(item.headimageUrl).placeholder(R.mipmap.placeholder)
                        .error(R.mipmap.placeholder).
                        apply(RequestOptions.centerInsideTransform()).into((ImageView)
                        helper.getView(R.id.recyclerview_imageview_head));
                ((TextView)helper.getView(R.id.recyclerview_textview_name)).
                        setText(item.name);
                ((TextView)helper.getView(R.id.recyclerview_textview_autograph)).
                        setText(item.autograph);
                break;
            case MineInfo.SECOND_TYPE:
                ((TextView)helper.getView(R.id.textview_notes)).
                        setText(item.notes+"");
                ((TextView)helper.getView(R.id.textview_follow)).
                        setText(item.follow+"");
                ((TextView)helper.getView(R.id.textview_fensi)).
                        setText(item.fans+"");
                break;
            case MineInfo.THIRD_TYPE:
                break;
            case MineInfo.FOURTH_TYPE:
                Glide.with(mContext).load(item.imageresid).
                        apply(RequestOptions.centerInsideTransform()).into((ImageView)
                        helper.getView(R.id.imageview_label));
                ((TextView)helper.getView(R.id.textview_label)).
                        setText(item.label);
                ((TextView)helper.getView(R.id.textview_number)).
                        setText(item.number+"");

                helper.itemView.setOnClickListener(mItemlistener);
                helper.itemView.setTag(item.label);
                break;
            default:
                break;
        }
    }
}
