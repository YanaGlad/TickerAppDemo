package com.example.mynasaapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mynasaapp.dao.ApodDao;
import com.example.mynasaapp.models.ScienceI;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class MainFragment extends Fragment {
    private static final String TAG = "APOD MainFragment";

    private TextView mName;
    private Button mDetailButton;
    private ImageView mApodImage;
    private Button mShareButton;

    private Handler mHandler;

    private String mImageUrl;
    private String mDescription;
    private String mTitle;
    private Drawable mImage;

    private ProgressBar mLoading;
    private LinearLayout mContent;

    private Fragment detailFragment;
    private Fragment mainFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DownloaderTask task = new DownloaderTask();
        task.execute("Param1", "Param2", "etc");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mName = (TextView)view.findViewById(R.id.name);
        mDetailButton = (Button)view.findViewById(R.id.detailButton);
        mApodImage = (ImageView)view.findViewById(R.id.apodImage);
        mShareButton = (Button)view.findViewById(R.id.shareButton);

        mHandler = new Handler(Looper.getMainLooper());
        mLoading = (ProgressBar)view.findViewById(R.id.loading);
        mContent = (LinearLayout)view.findViewById(R.id.content);

        mLoading.setVisibility(View.VISIBLE);
        mLoading.setIndeterminate(true);
        mContent.setVisibility(View.GONE);

        mDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getActivity(), "Clicked on the detail button", Toast.LENGTH_LONG).show();
                detailFragment = new DescriptionFragment();
                Bundle args = new Bundle();
                args.putString("Title", mTitle);
                args.putString("Description", mDescription);
                detailFragment.setArguments(args);

                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.content, detailFragment, "DETAIL")
                        .commit();

            }
        });

        mShareButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, mDescription);
                i.setType("text/plain");

                startActivity(i);
            }
        });
//                Intent i = new Intent(MainActivity.this, DetailActivity.class);
//                i.putExtra("Title", mTitle);
//                i.putExtra("Explanation", mDescription);
//                startActivity(i);
//            }
//        });


        return view;
    }

    public void setName(String name) {
        mName.setText(name);
    }


    private class DownloaderTask extends AsyncTask<String, Double, Void> {
        @Override
        protected Void doInBackground(String... strings) {
             ApodDao apod = new ApodDao();

            ArrayList<ScienceI> images = (ArrayList<ScienceI>) apod.retrieve();

            InputStream is = null;
            try {
                is = (InputStream) new URL(images.get(0).getImageUrl()).getContent();
                mImage = Drawable.createFromStream(is, "APOD");
                mTitle = images.get(0).getTitle();
                mDescription = images.get(0).getDescription();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoading.setVisibility(View.GONE);
                        mContent.setVisibility(View.VISIBLE);
                        mName.setText(mTitle);
                        mApodImage.setImageDrawable(mImage);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
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