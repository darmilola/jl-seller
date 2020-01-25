package com.dammy.android.emarketvendor.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.dammy.android.emarketvendor.Adapter.vieworderadapter;
import com.dammy.android.emarketvendor.Fragments.PlaceOrders;
import com.dammy.android.emarketvendor.Models.OrderModel;
import com.dammy.android.emarketvendor.Models.viewordermodel;
import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.openingPageActivity;
import com.dammy.android.emarketvendor.splashscreen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrderInfoVieworder extends AppCompatActivity   {

    String orderid,mydeliveryfee;
    RecyclerView recyclerView;
    Toolbar toolbar;
    ScrollView rootscrollView;
    LinearLayout progressbarlayout;
    LinearLayout nonetwork,error;
    Button errorbutton,nonetworkbutton;
    TextView errortext,nonetworktext,nodealtext;
    TextView Title, subtotal,subtotalvalue,deliveryfee,deliveryfeevalue,total,totalvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info_vieworder);
        if(savedInstanceState != null){

            if(savedInstanceState.getInt("pid",-1) == android.os.Process.myPid()){

            }
            else{

                startActivity(new Intent(this,splashscreen.class));

            }
        }
        getComingIntentValues();
        initview();
        initializerecyclerviewlayout();
        viewOrdertask viewOrdertask = new viewOrdertask();
        viewOrdertask.execute();
        setOnclickListner();



    }

   private void setOnclickListner(){
        errorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new viewOrdertask().execute();
            }
        });

        nonetworkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new viewOrdertask().execute();
            }
        });
   }
    private void initializerecyclerviewlayout(){
        recyclerView = findViewById(R.id.viewOrderRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    private void initview() {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = 0.5f;
        getWindow().setAttributes(layoutParams);
        recyclerView = findViewById(R.id.viewOrderRecyclerView);
        subtotal = findViewById(R.id.subtotal);
        subtotalvalue =findViewById(R.id.subtotalvalue);

        deliveryfee = findViewById(R.id.deliveryfee);
        deliveryfeevalue = findViewById(R.id.deliveryfeevalue);
        totalvalue = findViewById(R.id.totalvalue);
        total = findViewById(R.id.Total);
        toolbar = findViewById(R.id.viewordertoolbar);
        rootscrollView = findViewById(R.id.vieworderrootscrollview);
        progressbarlayout = findViewById(R.id.vieworderprogressbarlayout);
        rootscrollView.setVisibility(View.GONE);
        nonetwork = findViewById(R.id.nonetworklayout);
        error = findViewById(R.id.errorlayout);

        errorbutton = findViewById(R.id.errorbutton);
        nonetworkbutton = findViewById(R.id.nonetworkbutton);
        errortext = findViewById(R.id.errortext);
        nonetworktext = findViewById(R.id.nonetworktext);

        Typeface customfont2 = Typeface.createFromAsset(getAssets(), "Kylo-Regular.otf");
        errortext.setTypeface(customfont2);
        errorbutton.setTypeface(customfont2);
        nonetworktext.setTypeface(customfont2);
        nonetworkbutton.setTypeface(customfont2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);
        Title = findViewById(R.id.viewordertoolbartitle);
        Typeface customfont= Typeface.createFromAsset(getAssets(),"Kylo-Light.otf");
        subtotalvalue.setTypeface(customfont);
        subtotal.setTypeface(customfont);

        deliveryfeevalue.setTypeface(customfont);
        deliveryfee.setTypeface(customfont);
        total.setTypeface(customfont);
        totalvalue.setTypeface(customfont);
        Title.setTypeface(customfont);
    }

    private void getComingIntentValues() {
    orderid = getIntent().getStringExtra("orderid");

    }

    public class viewOrdertask extends AsyncTask {
        String url = "http://jl-market.com/vendor/vieworderVendor.php";
        String prompt;
        ArrayList<viewordermodel> orderList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
           progressbarlayout.setVisibility(View.VISIBLE);
           error.setVisibility(View.GONE);
           nonetwork.setVisibility(View.GONE);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }
            String serverresponse = new getOrder().GetData(url, orderid);
            if (serverresponse != null) {
                try {
                    JSONObject jsonObject = new JSONObject(serverresponse);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");

                    if (status.equalsIgnoreCase("available")) {
                        orderList = passvieworderjson(info);
                        return orderList;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

            return null;
        }

        protected void onPostExecute(Object result) {
            progressbarlayout.setVisibility(View.GONE);
            if(result == null) {

                progressbarlayout.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
                nonetwork.setVisibility(View.GONE);

                 }
                if(result != null){
                if(result instanceof ArrayList){
                    ArrayList<OrderModel> list = null;
                    ArrayList<viewordermodel> viewordermodel = (ArrayList<com.dammy.android.emarketvendor.Models.viewordermodel>) result;

                     mydeliveryfee = viewordermodel.get(0).getDeliveryfee();
                    deliveryfeevalue.setText(mydeliveryfee);
                    String orderjson = viewordermodel.get(0).getOrderjson();
                    try {
                        list = passOrderjsonJson(orderjson);
                        vieworderadapter vieworderadapter = new vieworderadapter(list);
                        recyclerView.setAdapter(vieworderadapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                   ShowPayment(list);

                    rootscrollView.setVisibility(View.VISIBLE);
                    progressbarlayout.setVisibility(View.GONE);
                    error.setVisibility(View.GONE);
                    nonetwork.setVisibility(View.GONE);

                }
                else if(result.equals("No Network Connection")){
                    progressbarlayout.setVisibility(View.GONE);
                    error.setVisibility(View.GONE);
                    nonetwork.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private ArrayList<OrderModel> passOrderjsonJson(String json) throws JSONException {
        ArrayList<OrderModel> orderModels = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        for(int i = 0; i < jsonArray.length(); i++){

            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String name = jsonObject.getString("name");
            String price = jsonObject.getString("price");
            String count = jsonObject.getString("count");
            int countprice = jsonObject.getInt("countprice");

            OrderModel orderModel = new OrderModel(name,price,count,countprice);
            orderModels.add(orderModel);
        }

        return orderModels;
    }

    public class getOrder {

        public String GetData(String url,String orderid) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();


                RequestBody formBody = new FormBody.Builder()
                        .add("orderid",orderid)
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

    private ArrayList<viewordermodel> passvieworderjson(JSONObject jsonObject) throws JSONException {
        ArrayList<viewordermodel> orderlist = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject j = jsonArray.getJSONObject(i);

            String orderjson = j.getString("orderjson");
            String deliveryfee = j.getString("deliveryfee");
            viewordermodel viewordermodel = new viewordermodel(orderjson,deliveryfee);

            orderlist.add(viewordermodel);



        }
        return orderlist;


    }
    private void ShowPayment(ArrayList<OrderModel> orderList){
        int subtotalprice = 0;
        Locale NigerianLocale = new Locale("en","ng");
        for (OrderModel a:orderList) {
            Log.e("got here", "onPostExecute: " );
            subtotalprice = subtotalprice + a.getCountprice();
            Log.e("here", Integer.toString(subtotalprice) );
        }
        String unFormattedSubtotalPrice = NumberFormat.getCurrencyInstance(NigerianLocale).format(subtotalprice);
        String formattedSubtotalPrice = unFormattedSubtotalPrice.replaceAll("\\.00","");
        subtotalvalue.setText(formattedSubtotalPrice);

        String unFormatteddeliveryfee = NumberFormat.getCurrencyInstance(NigerianLocale).format(Integer.parseInt(mydeliveryfee));
        String formatteddeliveryfee = unFormatteddeliveryfee.replaceAll("\\.00","");
        deliveryfeevalue.setText(formatteddeliveryfee);



        String unFormattedTotalprice = NumberFormat.getCurrencyInstance(NigerianLocale).format(Integer.parseInt(mydeliveryfee) + subtotalprice);
        String formattedtotalPrice = unFormattedTotalprice.replaceAll("\\.00","");
        totalvalue.setText(formattedtotalPrice);







    }


    private Boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        if(menuItem.getItemId() == android.R.id.home){

            this.onBackPressed();
        }


        return super.onOptionsItemSelected(menuItem);
    }
    @Override
    public void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        outstate.putInt("pid",android.os.Process.myPid());

    }

}
