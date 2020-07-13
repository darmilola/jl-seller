package com.dammy.android.emarketvendor.service.updateservice;

import android.app.IntentService;
import android.content.Intent;

import android.util.Log;

import com.dammy.android.emarketvendor.Models.dealmodel;

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

public class updatedeals extends IntentService {
    String urlpath, vendorid;

    public updatedeals() {
        super("updatedeals");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        ArrayList<dealmodel> dealArrayList = new ArrayList<>();
        if (intent != null) {
            urlpath = intent.getStringExtra("url");
            vendorid = intent.getStringExtra("vendorid");

            Log.e("got the int", "onHandleIntent: ");

            String serverresponse = new displaydeals().GetData(urlpath, vendorid);

            if (serverresponse != null) {
                try {
                    JSONObject jsonObject = new JSONObject(serverresponse);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");


                     if(status.equalsIgnoreCase("dealsavailable")){

                         dealArrayList = ParseJson(info);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            PublishResult(dealArrayList);


        }

    }

    private ArrayList<dealmodel> ParseJson(JSONObject jsonObject) throws JSONException {
        ArrayList<dealmodel> dealmodels =  new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject j = jsonArray.getJSONObject(i);
            String description = j.getString("description");
            String id = j.getString("id");
            dealmodel dealmodel = new dealmodel(description);
            dealmodel.setDealID(id);
            dealmodels.add(dealmodel);
        }
        return dealmodels;

    }


    public class displaydeals {


        public String GetData(String url, String vendorid) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                RequestBody formBody = new FormBody.Builder()
                        .add("vendorid", vendorid)
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
    private void PublishResult(ArrayList<dealmodel> dealmodels) {
        Intent intent = new Intent("com.dammy.android.emarketvendor.service.updateservice");
        intent.putParcelableArrayListExtra("deallist", dealmodels);
        sendBroadcast(intent);

    }
}
