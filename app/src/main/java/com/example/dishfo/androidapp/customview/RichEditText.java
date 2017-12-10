package com.example.dishfo.androidapp.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.EditText;

import com.example.dishfo.androidapp.bean.ExpressionInfo;

import java.util.LinkedList;

/**
 * Created by apple on 2017/12/10.
 */

@SuppressLint("AppCompatCustomView")
public class RichEditText extends EditText {

    private LinkedList<Bitmap> bitmapList = null;


    public RichEditText(Context context) {
        super(context);
        init();
    }

    public RichEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RichEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bitmapList = new LinkedList<>();
    }

    public void append(ExpressionInfo info) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), info.getId());
        bitmapList.add(bitmap);
        ImageSpan imageSpan = new ImageSpan(getContext(), bitmap);
        SpannableString spannableString = new SpannableString("," + info.getName() + ",");
        spannableString.setSpan(imageSpan, 0, info.getName().length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Editable editable = getText();
        editable.insert(getSelectionStart(), spannableString);
    }
}
