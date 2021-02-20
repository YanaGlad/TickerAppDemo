package com.example.mynasaapp;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DescriptionFragment extends Fragment {

    private String mName;
    private String mDetails;

    TextView mDetailName;
    TextView mDetailDescription;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_detail, container, false);
        mDetailName = (TextView)view.findViewById(R.id.detailName);
        mDetailDescription = (TextView) view.findViewById(R.id.detailDescription);

        if (getArguments() != null) {
            mName = getArguments().getString("Title");
            mDetails = getArguments().getString("Description");
            mDetailName.setText(mName);
            mDetailDescription.setText(mDetails);
        }

        return view;
    }
}
