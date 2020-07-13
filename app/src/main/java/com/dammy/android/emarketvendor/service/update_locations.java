package com.dammy.android.emarketvendor.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Parcelable;
import android.util.Log;

import com.dammy.android.emarketvendor.Activities.Showdeliverylocation;
import com.dammy.android.emarketvendor.Models.dealmodel;
import com.dammy.android.emarketvendor.Models.delivery_location_model;
import com.dammy.android.emarketvendor.service.updateservice.updatedeals;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class update_locations extends IntentService {

    String urlpath,vendorid;
    ArrayList<delivery_location_model> delivery_location_models;
    public update_locations() {
        super("update_locations");
    }



    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            urlpath = intent.getStringExtra("url");
            vendorid = intent.getStringExtra("vendorid");



            String serverresponse = new Getlocation().GetData(urlpath, vendorid);

            try {
                JSONObject jsonObject = new JSONObject(serverresponse);
                JSONObject info = jsonObject.getJSONObject("info");
                String status = info.getString("status");

                if (status.equalsIgnoreCase("available")) {

                    delivery_location_models = ParseJson(info);

                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            PublishResult(delivery_location_models);


        }
    }

    private ArrayList<delivery_location_model> ParseJson(JSONObject jsonObject) throws JSONException {
        ArrayList<delivery_location_model> delivery_location_models = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject j = jsonArray.getJSONObject(i);

            String city = j.getString("city");
            String area = j.getString("area");
            String email = j.getString("vendorid");

            delivery_location_model  delivery_location_model = new delivery_location_model(city,area,email);
            delivery_location_models.add(delivery_location_model);


        }
        return delivery_location_models;

    }


    public class Getlocation {

        public String GetData(String url,String email) {
            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                RequestBody formBody = new FormBody.Builder()

                        .add("vendorid",email)
                        .build();

                Request request = new Request.Builder().url(url).post(formBody).build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                return data;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void PublishResult(ArrayList<delivery_location_model> location_models) {
        Intent intent = new Intent("com.dammy.android.emarketvendor.service.updatelocation");
        intent.putParcelableArrayListExtra("locationlist", location_models);
        sendBroadcast(intent);

    }


}
