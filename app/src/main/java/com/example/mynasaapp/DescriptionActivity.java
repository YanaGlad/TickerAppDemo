package com.example.mynasaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class DescriptionActivity extends Activity {

    TextView mDetailName;
    TextView mDetailDescription;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDetailName = (TextView)findViewById(R.id.detailName);
        mDetailDescription = (TextView) findViewById(R.id.detailDescription);

        Intent i = getIntent();
        mDetailName.setText(i.getStringExtra("Title"));
        mDetailDescription.setText(i.getStringExtra("Explanation"));
    }

}