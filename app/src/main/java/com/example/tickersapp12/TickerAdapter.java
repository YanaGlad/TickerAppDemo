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

public class TickerAdapter extends RecyclerView.Adapter implements Filterable {

    private List<TickerInfo> tickerInfos;
    private Context context;


    TickerAdapter(List<TickerInfo> tickerInfos, Context context) {
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

        h.setStar(context,tickerInfos.get(position).getNameTicker(), (ImageButton) h.itemView.findViewById(R.id.fav));
    }

    @Override
    public int getItemCount() {
        return tickerInfos.size();
    }

    private Filter tickFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<TickerInfo> filteredInfo = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredInfo.addAll(MainMainActivity.tickerInfos);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (TickerInfo item : MainMainActivity.tickerInfos) {
                    if (item.getNameTicker().toLowerCase().contains(filterPattern)) {
                        filteredInfo.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredInfo;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            MainMainActivity.tickerInfos.clear();
            MainMainActivity.tickerInfos.addAll((ArrayList) results.values);
        }
    };

    @Override
    public Filter getFilter() {
        return tickFilter;
    }

//    public String getTickerName(@NonNull RecyclerView.ViewHolder holder, int position) {
//        return tickerInfos.get(position).getNameTicker();
//    }
}
