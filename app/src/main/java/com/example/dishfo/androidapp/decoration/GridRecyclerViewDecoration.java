package com.example.dishfo.androidapp.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.dishfo.androidapp.R;

/**
 * Created by dishfo on 17-12-8.
 */

public class GridRecyclerViewDecoration extends RecyclerView.ItemDecoration {

    private int mResId;
    private Drawable mDivider;
    private Context context;
    private int mSpancount;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GridRecyclerViewDecoration(int resId, int spancount, Context context){
        this.mResId=resId;
        this.context=context;
        mDivider=context.getResources().
                getDrawable(R.drawable.recyclerview_divider_dark1,null);
        mSpancount=spancount;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void  drawLeft(Canvas c,View view){
        RecyclerView.LayoutParams params=
                (RecyclerView.LayoutParams) view.getLayoutParams();

        int top=view.getTop()-mDivider.getIntrinsicHeight();
        int right=params.leftMargin+view.getLeft();
        int left=right-mDivider.getIntrinsicWidth();
        int bottom=view.getBottom()+params.bottomMargin+mDivider.getIntrinsicHeight();
        mDivider.setBounds(left,top,right,bottom);
        mDivider.draw(c);
      //  Log.d("test","left"+bottom);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void  drawTop(Canvas c,View view){
        RecyclerView.LayoutParams params=
                (RecyclerView.LayoutParams) view.getLayoutParams();

        int left=params.leftMargin+view.getLeft();
        int right=view.getRight()+params.rightMargin+mDivider.getIntrinsicWidth();
        int bottom=view.getTop()+params.topMargin;
        int top=bottom-mDivider.getIntrinsicHeight();
    //    Log.d("test","top"+top);
        mDivider.setBounds(left,top,right,bottom);
        mDivider.draw(c);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void  drawRight(Canvas c,View view){
        RecyclerView.LayoutParams params=
                (RecyclerView.LayoutParams) view.getLayoutParams();
        int left=view.getRight()+params.rightMargin;
        int top=view.getTop();
        int right=left+mDivider.getIntrinsicWidth();
        int bottom=view.getBottom()+mDivider.getIntrinsicHeight()+params.bottomMargin;
   //     Log.d("test","right"+bottom);
        mDivider.setBounds(left,top,right,bottom);
        mDivider.draw(c);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void  drawBottom(Canvas c,View view){
        RecyclerView.LayoutParams params=
                (RecyclerView.LayoutParams) view.getLayoutParams();
        int left=view.getLeft()-mDivider.getIntrinsicWidth();
        int top=view.getBottom();
        int right=view.getRight();
        int bottom=top+mDivider.getIntrinsicHeight();
        mDivider.setBounds(left,top,right,bottom);
        mDivider.draw(c);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int count=parent.getChildCount();
        View chlid=null;
        int left=1;
        int top=1;
        int right=0;
        int bottom=0;
        for(int i=0;i<count;i++){
            chlid=parent.getChildAt(i);
            right=0;
            bottom=0;
            if((i+1)%mSpancount==0){
                right=1;
            }
            if(i>count-mSpancount-1){
                bottom=1;
            }
            if(i==count-1){
                right=1;
                bottom=1;
            }

            drawLeft(c,chlid);
            drawTop(c,chlid);
            if(bottom==1)
                drawBottom(c,chlid);
            if(right==1)
                drawRight(c,chlid);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position=parent.indexOfChild(view);
        int count=parent.getChildCount();
        int left=mDivider.getIntrinsicWidth();
        int top=mDivider.getIntrinsicHeight();
        int right=0;
        int bottom=0;
        if((position+1)%mSpancount==0){
            right=left;
        }
        if(position>count-mSpancount-1){
            bottom=top;
        }

        outRect.set(left,top,right,bottom);
    }
}
