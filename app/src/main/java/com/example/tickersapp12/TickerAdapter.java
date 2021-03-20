package com.example.tickersapp12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mynasaapp.R;
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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ticker, parent, false);
        return new TickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TickerViewHolder h = (TickerViewHolder) holder;
        h.setDescription(context,
                tickerInfos.get(position).getNameTicker(),
                tickerInfos.get(position).getNameCompany(),
                tickerInfos.get(position).getPrice(),
                tickerInfos.get(position).getPriceChange());
    }

    @Override
    public int getItemCount() {
        return tickerInfos.size();
    }
}
