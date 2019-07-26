package com.example.myviewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.Holder> {
    private ArrayList<WeatherItem> data = new ArrayList<>();

    public void setData(ArrayList<WeatherItem> items) {
        data.clear();
        data.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_layout, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tvNamaKota, tvTemperature, tvDescription;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvNamaKota = itemView.findViewById(R.id.tv_kota);
            tvTemperature = itemView.findViewById(R.id.tv_temp);
            tvDescription = itemView.findViewById(R.id.tv_desc);
        }

        void bind(WeatherItem weatherItem){
            tvNamaKota.setText(weatherItem.getName());
            tvDescription.setText(weatherItem.getDescription());
            tvTemperature.setText(weatherItem.getTemperature());
        }
    }
}
