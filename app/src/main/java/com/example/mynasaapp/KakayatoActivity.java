package com.example.mynasaapp;

import android.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class KakayatoActivity extends Activity {
    FrameLayout content;
    FrameLayout toolbar;
    Fragment mMainFragment;
    Fragment mDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_entry);

        mMainFragment = new MainFragment();
        mDetailFragment = new DescriptionFragment();

        getFragmentManager()
                .beginTransaction()
                .add(R.id.content,  mMainFragment, "MAIN")
                .commit();
    }
}