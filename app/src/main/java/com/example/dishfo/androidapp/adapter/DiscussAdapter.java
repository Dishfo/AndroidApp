package com.example.dishfo.androidapp.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.example.dishfo.androidapp.DataAcess.NetMethod;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.bean.DiscussInfo;
import com.example.dishfo.androidapp.constant.ArrayConstant;
import com.example.dishfo.androidapp.util.ScreenUtils;

import java.util.List;

/**
 * Created by apple on 2017/12/10.
 */

public class DiscussAdapter extends BaseQuickAdapter<DiscussInfo, BaseViewHolder> {


    RequestListener<Drawable> listener=new RequestListener<Drawable>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            Log.d("loadImage",e.getMessage());

            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            return false;
        }
    };


    public DiscussAdapter(int layoutResId, @Nullable List<DiscussInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiscussInfo item) {

        NetMethod netMethod=new NetMethod();

        helper.addOnClickListener(R.id.recyclerView_item_discuss_imageView_head);
        Glide.with(mContext).load(item.getmHeaUrl()).apply(RequestOptions.circleCropTransform()).into((ImageView) helper.getView(R.id.recyclerView_item_discuss_imageView_head));
        helper.setText(R.id.recyclerView_item_discuss_textView_nickName, item.getmNickName());
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
                viewGroup.addView(imageView);
                Log.d("test","urlis"+url);

                netMethod.useGlideWithoutCircle(mContext,url,imageView);

            }
        }
        helper.setText(R.id.recyclerView_item_discuss_textView_time, item.getmTime());
    }
}
