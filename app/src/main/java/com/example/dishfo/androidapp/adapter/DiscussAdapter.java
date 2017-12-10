package com.example.dishfo.androidapp.adapter;

import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.bean.DiscussInfo;
import com.example.dishfo.androidapp.constant.ArrayConstant;
import com.example.dishfo.androidapp.util.ScreenUtils;

import java.util.List;

/**
 * Created by apple on 2017/12/10.
 */

public class DiscussAdapter extends BaseQuickAdapter<DiscussInfo, BaseViewHolder> {
    public DiscussAdapter(int layoutResId, @Nullable List<DiscussInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiscussInfo item) {
        Glide.with(mContext).load(item.getmHeaUrl()).apply(RequestOptions.circleCropTransform()).into((ImageView) helper.getView(R.id.recyclerView_item_discuss_imageView_head));
        helper.setText(R.id.recyclerView_item_discuss_textView_nickName, item.getmNickName());
        Glide.with(mContext).load(ArrayConstant.LEVEL_ARRAY[Integer.valueOf(item.getmLevel())]).into((ImageView) helper.getView(R.id.recyclerView_item_discuss_imageView_level));
        if (item.getmReplayedContent() != null) {
            helper.setText(R.id.recyclerView_item_discuss_textView_replyed, item.getmReplayedContent());
        }
        helper.setText(R.id.recyclerView_item_discuss_textView_replayContent, item.getmReplayContent());
        if (item.getmImageUrls() != null && item.getmImageUrls().size() != 0) {
            ViewGroup viewGroup = helper.getView(R.id.recyclerView_item_discuss_linearLayout);
            viewGroup.removeAllViews();
            for (String url : item.getmImageUrls()) {
                ImageView imageView = new ImageView(mContext);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.topMargin = (int) ScreenUtils.dpToPx(mContext, 10);
                lp.leftMargin = (int) ScreenUtils.dpToPx(mContext, 20);
                lp.rightMargin = (int) ScreenUtils.dpToPx(mContext, 20);
                imageView.setLayoutParams(lp);
                Glide.with(mContext).load(url).into(imageView);

                viewGroup.addView(imageView);
            }
        }
        helper.setText(R.id.recyclerView_item_discuss_textView_time, item.getmTime());
    }
}
