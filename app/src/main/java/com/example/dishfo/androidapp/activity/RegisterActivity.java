package com.example.dishfo.androidapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.activity.base.BaseActivity;

/**
 * Created by apple on 2017/12/8.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImageViewBack = null;
    private EditText mEditTextEmail = null;
    private EditText mEditTextVerificationCode = null;
    private View mViewVerification = null;
    private Button mButtonNext = null;


    private static final String KEY_EMAIL = "email";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initView() {

        mImageViewBack = findView(R.id.activity_register_imageView_back);
        mEditTextEmail = findView(R.id.activity_register_editText_email);
        mEditTextVerificationCode = findView(R.id.activity_register_editText_verification_code);
        mViewVerification = findView(R.id.activity_register_view_verification_code);
        mButtonNext = findView(R.id.activity_register_button_next);

        mImageViewBack.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Glide.with(this).asBitmap().load(R.mipmap.imageview_back).into(mImageViewBack);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_register_imageView_back: {
                onBackPressed();
                break;
            }
            case R.id.activity_register_button_next: {
                String email = mEditTextEmail.getText().toString();
                Intent intent = new Intent(this, SetPasswordActivity.class);
                intent.putExtra(KEY_EMAIL, email);
                startActivity(intent);
                this.finish();
                break;
            }
        }
    }
}
