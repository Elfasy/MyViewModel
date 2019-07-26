package com.example.myviewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {
    private static final String API_KEY = "d5569f52336a5ca84f5e4cad0c18726c";
    private MutableLiveData<ArrayList<WeatherItem>> listMutableLiveData = new MutableLiveData<>();

    void setWeather(final String cities) {
        //request API
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<WeatherItem> listItem = new ArrayList<>();
        String url = "https://api.openweathermap.org/data/2.5/group?id=" + cities + "&units=metric&appid=" + API_KEY;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("list");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject weather = list.getJSONObject(i);
                        WeatherItem weatherItem = new WeatherItem(weather);
                        listItem.add(weatherItem);
                    }
                    listMutableLiveData.postValue(listItem);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailuer", error.getMessage());
            }
        });
    }

    LiveData<ArrayList<WeatherItem>> getWeathers() {
        return listMutableLiveData;
    }
}
