package com.example.tickersapp12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynasaapp.R;
import com.example.tickersapp12.Support.TickerInfo;

import java.util.ArrayList;
import java.util.List;

public class TickerAdapter extends RecyclerView.Adapter {

    private List<TickerInfo> tickerInfos;
    private Context context;

    interface OnTickerClickListener {
        void onTickerClick(TickerInfo tickerInfo, int position);
    }

    private final OnTickerClickListener onTickerClickListener;

    TickerAdapter(List<TickerInfo> tickerInfos, Context context, OnTickerClickListener onTickerClickListener) {
        this.tickerInfos = tickerInfos;
        this.context = context;
        this.onTickerClickListener = onTickerClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ticker, parent, false);


        return new TickerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        TickerViewHolder h = (TickerViewHolder) holder;
        h.setDescription(context,
                tickerInfos.get(position).getNameTicker(),
                tickerInfos.get(position).getNameCompany(),
                tickerInfos.get(position).getPrice(),
                tickerInfos.get(position).getPriceChange());

        h.setStar(context, tickerInfos.get(position).getNameTicker(), (ImageButton) h.itemView.findViewById(R.id.fav));

        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTickerClickListener.onTickerClick(tickerInfos.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tickerInfos.size();
    }

}
