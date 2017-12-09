package com.example.dishfo.androidapp.activity;

import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.fragment.AreaFragment;
import com.example.dishfo.androidapp.fragment.FoundFragment;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_test);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, FoundFragment.newInstance("",""));
        transaction.commit();
    }
}
