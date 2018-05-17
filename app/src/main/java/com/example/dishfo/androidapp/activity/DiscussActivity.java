package com.example.dishfo.androidapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;
import com.example.dishfo.androidapp.adapter.ExpressionAdapter;
import com.example.dishfo.androidapp.adapter.PictureAdapter;
import com.example.dishfo.androidapp.bean.ExpressionInfo;
import com.example.dishfo.androidapp.control.BitmapCache;
import com.example.dishfo.androidapp.mvp.Discuss.DiscussPresenterImpl;
import com.example.dishfo.androidapp.mvp.Discuss.DiscussTaskContract;
import com.example.dishfo.androidapp.mvp.ModelManager;
import com.example.dishfo.androidapp.mvp.NewNote.NewNoteTaskContract;
import com.example.dishfo.androidapp.bean.sqlBean.Area;
import com.example.dishfo.androidapp.bean.sqlBean.Discuss;
import com.example.dishfo.androidapp.bean.sqlBean.Note;
import com.example.dishfo.androidapp.bean.sqlBean.User;
import com.example.dishfo.androidapp.bean.viewBean.ViewDiscuss;
import com.example.dishfo.androidapp.bean.viewBean.ViewNote;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by dishfo on 18-3-3.
 */

public class DiscussActivity extends BaseActivity implements View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener,DiscussTaskContract.DiscussView{
    private ImageView mImageViewBack = null;
    private RecyclerView mRecyclerViewPicture = null;
    private EditText mEditTextContent = null;
    private ImageView mImageViewExpression = null;
    private ImageView mImageViewPicture = null;
    private Button mButtonOk = null;
    private RecyclerView mRecyclerViewExpression = null;


    private ExpressionAdapter mExpressionAdapter = null;
    private List<ExpressionInfo> mExpressions = null;

    private PictureAdapter mPictureAdapter = null;
    private List<String> mPictures = null;

    private BitmapCache mBitmapCache = null;

    private DiscussTaskContract.DiscussPresenter mPresenter;
    private ViewNote note;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        note= (ViewNote) intent.getSerializableExtra(NOTEID);
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
        mPresenter=new DiscussPresenterImpl(ModelManager.INSTANCE.getDiscussModel(),this);
        mPresenter.start();
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
                String files[]=mPictures.toArray(new String[]{});
                mPresenter.onDiscussNote(generateDiscussInfo(),files);

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
                break;
            }
            case R.id.recyclerView_item_picture_imageView_close: {
                mPictureAdapter.remove(position);
                break;
            }
        }
    }

    private void appendExpression(ExpressionInfo info) {
        Bitmap bitmap = null;
        if (mBitmapCache.contains(info.getName())) {
            bitmap = mBitmapCache.get(info.getName());
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inDensity = 300;
            options.inScaled = true;
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

    private ViewDiscuss generateDiscussInfo(){
        Discuss discuss=new Discuss();
        Note note=this.note.getNote();
        Area area=this.note.getArea();
        User user=this.note.getUser();

        discuss.setEmail(ModelManager.INSTANCE.getLoginModel().getCurrentUser().getEmail());
        discuss.setContent(mEditTextContent.getText().toString());
        discuss.setOldContent(note.getContent());
        discuss.setOldUser(user.getName());
        discuss.setNoteId(note.getId());
        discuss.setAreaId(area.getId());

        ViewDiscuss viewDiscuss=new ViewDiscuss();
        viewDiscuss.setDiscuss(discuss);
        viewDiscuss.setNote(note);
        viewDiscuss.setArea(area);

        return viewDiscuss;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBitmapCache.recycle();
        PictureFileUtils.deleteCacheDirFile(this);
    }

    @Override
    public void setPresent(DiscussTaskContract.DiscussPresenter present) {
        this.mPresenter=present;
    }

    @Override
    public void waitToCompete() {
        statrWait();
    }

    private void statrWait(){

    }

    private void stopWait(){

    }

    @Override
    public void compete(Object... args) {
        sendMessage((int)args[0],DiscussTaskContract.SUCCEED,null);
    }

    @Override
    public void error(Object... args) {
        sendMessage((int)args[0],DiscussTaskContract.FAILED,null);
    }

    private void sendMessage(int code, int arg1, Object object){
        Message message=errorHandler.obtainMessage();
        message.what=code;
        message.arg1=arg1;
        message.obj=object;
        errorHandler.sendMessage(message);
    }

    @SuppressLint("HandlerLeak")
    private Handler errorHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case NewNoteTaskContract.UPFILE:
                    if(msg.arg1==DiscussTaskContract.SUCCEED){

                    }else {
                        Toast.makeText(DiscussActivity.this,"文件上传失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case NewNoteTaskContract.NOTE:
                    if(msg.arg1==DiscussTaskContract.SUCCEED){
                        DiscussActivity.this.onBackPressed();
                    }else {
                        Toast.makeText(DiscussActivity.this,"评论失败",Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
            DiscussActivity.this.stopWait();
        }
    };

}
