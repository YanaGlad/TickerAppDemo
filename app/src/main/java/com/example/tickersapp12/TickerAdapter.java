package com.example.tickersapp12;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tickersapp12.Support.TickerInfo;

import java.util.List;

public class TickerAdapter extends RecyclerView.Adapter {

    private List<TickerInfo>tickerInfos;
    private Context context;

    TickerAdapter(List<TickerInfo>tickerInfos, Context context){
        this.tickerInfos = tickerInfos;
        this.context = context;
        
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
