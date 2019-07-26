package com.example.myviewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private WeatherAdapter adapter;
    private EditText edtCity;
    private ProgressBar progressBar;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new WeatherAdapter();
        adapter.notifyDataSetChanged();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        edtCity = findViewById(R.id.edt_city);
        progressBar = findViewById(R.id.progress_bar);

        findViewById(R.id.btn_city).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = edtCity.getText().toString().trim();

                if (TextUtils.isEmpty(city)) return;

                mainViewModel.setWeather(city);
                showLoading(true);
            }
        });

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getWeathers().observe(this,getWeather);
    }

    private Observer<ArrayList<WeatherItem>> getWeather = new Observer<ArrayList<WeatherItem>>() {
        @Override
        public void onChanged(ArrayList<WeatherItem> weatherItems) {
            if (weatherItems != null){
                adapter.setData(weatherItems);
                showLoading(false);
            }
        }
    };

    private void showLoading(boolean b) {
        if (b){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
