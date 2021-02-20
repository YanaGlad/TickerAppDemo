package com.example.mynasaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.mynasaapp.dao.ApodDao;
import com.example.mynasaapp.models.ScienceI;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "APOD MainActivity";

//    private TextView mName;
//    private Button mDetailButton;
//    private VideoView mApodImage;
//
//    private Handler mHandler;
//
//    private String mImageUrl;
//    private String mDescription;
//    private String mTitle;
//    private Drawable mImage;
//
//    private ProgressBar mLoading;
//    private LinearLayout mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
//
//        mName = (TextView) findViewById(R.id.name);
//        mDetailButton = (Button) findViewById(R.id.detailButton);
//        mApodImage = (VideoView) findViewById(R.id.apodImage);
//        mHandler = new Handler(Looper.getMainLooper());
//        mLoading = (ProgressBar) findViewById(R.id.loading);
//        mContent = (LinearLayout) findViewById(R.id.content);
//
//        mLoading.setVisibility(View.VISIBLE);
//        mLoading.setIndeterminate(true);
//        mContent.setVisibility(View.GONE);
//
//        mDetailButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(MainActivity.this, DescriptionActivity.class);
//                i.putExtra("Title", mTitle);
//                i.putExtra("Explanation", mDescription);
////                i.putExtra("Author", "John O'Connor");
//                startActivity(i);
//            }
//        });
//
//        for (int i = 0; i < 3; i++) {
//            Log.d(TAG, Integer.toString(i));
//        }
        DownloaderTask task = new DownloaderTask();
        task.execute("Param1", "Param2", "etc");

    }
//
//    public void setName(String name) {
//        mName.setText(name);
//    }


    private class DownloaderTask extends AsyncTask<String, Double, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            Log.d(TAG, strings[0]);
            ApodDao apod = new ApodDao();

            apod.retrieve();

//            final ArrayList<ScienceI> images = (ArrayList<ScienceI>) apod.retrieve();
//
//            InputStream is = null;
//            try {
//                is = (InputStream) new URL(images.get(0).getImageUrl()).getContent();
//                mImage = Drawable.createFromStream(is, "APOD");
//                mTitle = images.get(0).getTitle();
//                mDescription = images.get(0).getDescription();
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mLoading.setVisibility(View.GONE);
//                        mContent.setVisibility(View.VISIBLE);
//                        mName.setText(mTitle);
//                        Uri myVideoUri = Uri.parse(images.get(0).getDescription());
//                        mApodImage.setVideoURI(myVideoUri);
//                    }
//                });
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            // Do something that will be run before ASyncTask runs.
        }

        @Override
        protected void onProgressUpdate(Double... values) {

        }
    }
}