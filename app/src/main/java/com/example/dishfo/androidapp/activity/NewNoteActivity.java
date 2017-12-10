package com.example.dishfo.androidapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.adapter.ExpressionAdapter;
import com.example.dishfo.androidapp.adapter.PictureAdapter;
import com.example.dishfo.androidapp.bean.ExpressionInfo;
import com.example.dishfo.androidapp.control.BitmapCache;
import com.example.dishfo.androidapp.customview.RichEditText;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/12/10.
 */

public class NewNoteActivity extends BaseActivity implements View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener {
    private ImageView mImageViewBack = null;
    private RecyclerView mRecyclerViewPicture = null;
    private RichEditText mEditTextContent = null;
    private ImageView mImageViewExpression = null;
    private ImageView mImageViewPicture = null;
    private Button mButtonOk = null;
    private RecyclerView mRecyclerViewExpression = null;


    private ExpressionAdapter mExpressionAdapter = null;
    private List<ExpressionInfo> mExpressions = null;

    private PictureAdapter mPictureAdapter = null;
    private List<String> mPictures = null;

    private BitmapCache mBitmapCache = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
    }

    @Override
    public void initView() {
        mImageViewBack = findView(R.id.toolBar_back_imageView_back);
        mRecyclerViewPicture = findView(R.id.activity_new_note_recyclerView_picture);
        mImageViewExpression = findView(R.id.activity_new_note_imageView_expression);
        mImageViewPicture = findView(R.id.activity_new_note_imageView_picture);
        mButtonOk = findView(R.id.activity_new_note_button_ok);
        mRecyclerViewExpression = findView(R.id.activity_new_note_recyclerView_expression);
        mEditTextContent = findView(R.id.activity_new_note_editText_content);

        mImageViewBack.setOnClickListener(this);
        mImageViewPicture.setOnClickListener(this);
        mImageViewExpression.setOnClickListener(this);
        mButtonOk.setOnClickListener(this);

        getAllExpression();
        mExpressionAdapter = new ExpressionAdapter(R.layout.recyclerview_item_expression, mExpressions);
        mRecyclerViewExpression.setAdapter(mExpressionAdapter);
        mRecyclerViewExpression.setLayoutManager(new GridLayoutManager(this, 7));
        mExpressionAdapter.setOnItemChildClickListener(this);

        mPictures = new ArrayList<>();
        mPictureAdapter = new PictureAdapter(R.layout.recyclerview_item_picture, mPictures);
        mPictureAdapter.setOnItemChildClickListener(this);
        mRecyclerViewPicture.setAdapter(mPictureAdapter);
        mRecyclerViewPicture.setLayoutManager(new GridLayoutManager(this, 3));

        mBitmapCache = new BitmapCache();
    }

    @Override
    public void initData() {
        Glide.with(this).load(R.mipmap.imageview_back).into(mImageViewBack);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_new_note_imageView_expression: {
                if (mRecyclerViewExpression.getVisibility() == View.GONE) {
                    Glide.with(this).load(R.mipmap.imageview_expression_2).into(mImageViewExpression);
                    mRecyclerViewExpression.setVisibility(View.VISIBLE);
                } else {
                    Glide.with(this).load(R.mipmap.imageview_expression_1).into(mImageViewExpression);
                    mRecyclerViewExpression.setVisibility(View.GONE);
                }
                break;
            }
            case R.id.activity_new_note_button_ok: {
                Intent intent = new Intent(this, TestActivity.class);
                startActivity(intent);
                this.finish();
                break;
            }
            case R.id.activity_new_note_imageView_picture: {
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(3 - mRecyclerViewPicture.getChildCount())
                        .compress(true)
                        .enableCrop(true)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            }
        }
    }

    private void getAllExpression() {
        mExpressions = new ArrayList<>();
        Field[] fields = R.raw.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                if (field.getName().contains("write_face_")) {
                    ExpressionInfo info = new ExpressionInfo();
                    info.setId(Integer.valueOf(field.get(null).toString()));
                    info.setName(field.getName());
                    mExpressions.add(info);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.recyclerView_item_expression_imageView_expression: {
                appendExpression(mExpressions.get(position));
            }
            case R.id.recyclerView_item_picture_imageView_close: {
                mPictureAdapter.remove(position);
            }
        }
    }

    private void appendExpression(ExpressionInfo info) {
        Bitmap bitmap = null;
        if (mBitmapCache.contains(info.getName())) {
            bitmap = mBitmapCache.get(info.getName());
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            bitmap = BitmapFactory.decodeResource(getResources(), info.getId(), options);
            mBitmapCache.put(info.getName(), bitmap);
        }
        ImageSpan imageSpan = new ImageSpan(this, bitmap);
        SpannableString spannableString = new SpannableString("," + info.getName() + ",");
        spannableString.setSpan(imageSpan, 0, info.getName().length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Editable editable = mEditTextContent.getText();
        editable.insert(mEditTextContent.getSelectionStart(), spannableString);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        String path = null;
                        if (media.isCompressed()) {
                            path = media.getCompressPath();
                        } else if (media.isCut()) {
                            path = media.getCutPath();
                        } else {
                            path = media.getPath();
                        }
                        mPictureAdapter.addData(path);
                    }
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBitmapCache.recycle();
        PictureFileUtils.deleteCacheDirFile(this);
    }
}
