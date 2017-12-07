package com.example.dishfo.androidapp.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.fragment.AreaFragment;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_test);


        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container,AreaFragment.newInstance("",""));
        transaction.commit();
    }
}
