package com.dammy.android.emarketvendor.service.updateorderservice;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dammy.android.emarketvendor.Fragments.PlaceOrders;
import com.dammy.android.emarketvendor.Models.dealmodel;
import com.dammy.android.emarketvendor.Models.ordermessagemodel;
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


public class updateorder extends IntentService {

    String urlpath, vendorid;
    ArrayList<ordermessagemodel> ordermodelArrayList;
    public updateorder() {
        super("updateorder");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            urlpath = intent.getStringExtra("url");
            vendorid = intent.getStringExtra("vendorid");

            Log.e("got the int", "onHandleIntent: ");
            String serverResponse = new getid().GetData(urlpath, vendorid);
            if (serverResponse != null) {


                try {
                    JSONObject jsonObject = new JSONObject(serverResponse);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");

                    if (status.equalsIgnoreCase("available")) {
                        ordermodelArrayList = passIdJson(info);
                        }

                }catch (JSONException E){
                    E.printStackTrace();
                }

            }
            PublishResult(ordermodelArrayList);


        }

    }
    public ArrayList<ordermessagemodel> passIdJson(JSONObject jsonObject) throws JSONException {
        ArrayList<ordermessagemodel> orderidlist = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject j = jsonArray.getJSONObject(i);
            String orderid = j.getString("orderid");
            String userid = j.getString("useremail");
            ordermessagemodel ordermodel = new ordermessagemodel(orderid,userid);
            orderidlist.add(ordermodel);
        }
        return orderidlist;

        }
    public class getid {

        public String GetData(String url,String vendorid) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();


                RequestBody formBody = new FormBody.Builder()
                        .add("vendorid",vendorid )
                        .build();
                Request request = new Request.Builder().post(formBody).url(url).build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                return data;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void PublishResult(ArrayList<ordermessagemodel> ordermodelArrayList) {
        Intent intent = new Intent("com.dammy.android.emarketvendor.service.updateorderservice");
        intent.putParcelableArrayListExtra("orderlist",ordermodelArrayList);
        sendBroadcast(intent);

    }


}
