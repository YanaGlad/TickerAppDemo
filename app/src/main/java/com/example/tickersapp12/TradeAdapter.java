package com.example.tickersapp12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynasaapp.R;
import com.example.tickersapp12.Support.TickerInfo;
import com.example.tickersapp12.Support.TradeInfo;

import java.util.List;

public class TradeAdapter extends RecyclerView.Adapter {

    private List<TradeInfo> tradeInfos;
    private Context context;

    interface OnTradeClickListener {
        void onTickerClick(TradeInfo tradeInfo, int position);
    }

    private final OnTradeClickListener onTradeClickListener;

    TradeAdapter(List<TradeInfo> tradeInfos, Context context, OnTradeClickListener onTradeClickListener) {
        this.tradeInfos = tradeInfos;
        this.context = context;
        this.onTradeClickListener = onTradeClickListener;
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
        h.setTradeDescription(context,
                tradeInfos.get(position).getTicker(),
                tradeInfos.get(position).getBuyPrice(),
                tradeInfos.get(position).getSellPrice(),
                tradeInfos.get(position).getLot());
    }

    @Override
    public int getItemCount() {
        return tradeInfos.size();
    }

}
