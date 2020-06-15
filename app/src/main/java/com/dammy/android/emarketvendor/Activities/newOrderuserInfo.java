package com.dammy.android.emarketvendor.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.dammy.android.emarketvendor.Adapter.orderstatusadapter;
import com.dammy.android.emarketvendor.Fragments.PlaceOrders;
import com.dammy.android.emarketvendor.Models.orderinfo;
import com.dammy.android.emarketvendor.Models.orderstatusmodel;
import com.dammy.android.emarketvendor.Models.userprofile;
import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.openingPageActivity;
import com.dammy.android.emarketvendor.splashscreen;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

public class newOrderuserInfo extends AppCompatActivity {

    TextView Title, locationtitle, addresstitle, addressvalue, citytitle, cityvalue, areatitle, areavalue, phonetitle,
            phonevalue, paymentmethodtitle, paymentmethodvalue, statustitle, statusvalue;
    Button vieworder;
    String orderid, useremail;
    MapView mapView;
    GoogleMap googleMap;
    double lat, log;
    Toolbar toolbar;
    ImageView upadatestatus, makecall;
    AlertDialog DialogHandler;
    ArrayList<orderstatusmodel> list;
    orderstatusadapter orderstatusadapter;
    RecyclerView recyclerView;
    View statusdialog;
    ArrayList<orderstatusmodel> statuses;
    Dialog loadingdialog;
    String number;
    Uri call;
    Intent callnumber;
    LinearLayout nonetwork,error;
    Button errorbutton,nonetworkbutton;
    TextView errortext,nonetworktext;

    ScrollView rootscrollView;
    LinearLayout progressbarlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_info);
        initView();
        setOnclicklistener();
        if(savedInstanceState != null){

            if(savedInstanceState.getInt("pid",-1) == android.os.Process.myPid()){

            }
            else{

                startActivity(new Intent(this,splashscreen.class));

            }
        }
        mapView.onCreate(savedInstanceState);
        getvaluefromIntent();
        getuserInfo getuserInfo = new getuserInfo();
        getuserInfo.execute();





    }

    private void getvaluefromIntent() {
        orderid = getIntent().getStringExtra("orderid");
        useremail = getIntent().getStringExtra("useremail");
    }

    private void setOnclicklistener() {
        vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(newOrderuserInfo.this, OrderInfoVieworder.class);
                intent.putExtra("orderid", orderid);
                startActivity(intent);
            }
        });

        makecall.setOnClickListener(new View.OnClickListener() {
            //@SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(newOrderuserInfo.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(newOrderuserInfo.this,new String[]{android.Manifest.permission.CALL_PHONE},1);
                }
                else{
                     makecall();
                }

            }
        });

        upadatestatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeStatusdialog();
                statuses = getStatuses();
                initializeStatusrecyclerview();


            }
        });

        errorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getuserInfo().execute();
            }
        });

        nonetworkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getuserInfo().execute();
            }
        });
    }



    private void makecall(){
        number = phonevalue.getText().toString();
        call = Uri.parse("tel:" + number);
        callnumber = new Intent(Intent.ACTION_CALL, call);
        startActivity(callnumber);
    }

    private void initializeStatusdialog(){
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(newOrderuserInfo.this);
        LayoutInflater layoutInflater = LayoutInflater.from(newOrderuserInfo.this);
        statusdialog = layoutInflater.inflate(R.layout.orderstatusdialog, null);
        alertdialog.setView(statusdialog);
        alertdialog.setCancelable(true);
        DialogHandler = alertdialog.create();
        DialogHandler = alertdialog.show();
    }
    private void initializeStatusrecyclerview(){
        orderstatusadapter = new orderstatusadapter(getApplicationContext(), statuses, new orderstatusadapter.ListItemClickListener() {
            @Override
            public void onListItemClick(int clickedItemIndex) {
               String status = statuses.get(clickedItemIndex).getStatus();
               statusvalue.setText(status);
               DialogHandler.dismiss();
               updatestatustask updatestatustask = new updatestatustask(status);
               updatestatustask.execute();

            }
        });
        recyclerView = (RecyclerView) statusdialog.findViewById(R.id.orderstatusrecyclerview);
        recyclerView.setAdapter(orderstatusadapter);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(newOrderuserInfo.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
    }
    private ArrayList getStatuses(){
        orderstatusmodel status1 = new orderstatusmodel("Order confirmed");
        orderstatusmodel status4 = new orderstatusmodel("Delivery on the way");
        orderstatusmodel status5 = new orderstatusmodel("At your location");


        list = new ArrayList<>();
        list.add(status1);
        list.add(status4);
        list.add(status5);


        return  list;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //do stuffs permission granted
                    makecall();
                }
                else{
                    //permissions denied
                }
        }

    }





    public class getuserInfo extends AsyncTask{
        String prompt;
        private  String URL = "http://jl-market.com/user/getuserinfo.php";

        @Override
        protected void onPreExecute() {

                   nonetwork.setVisibility(View.GONE);
                   error.setVisibility(View.GONE);
                   progressbarlayout.setVisibility(View.VISIBLE);


                  }
        @Override
        protected Object doInBackground(Object[] objects) {
            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }
            String serverResponse = new getuserqueryinfo().GetData(URL, useremail);

            if (serverResponse != null) {
                try {
                    JSONObject jsonObject = new JSONObject(serverResponse);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");

                    if (status.equalsIgnoreCase("available")) {
                        userprofile userprofile = ParseUserProfileJson(info);
                        return userprofile;
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        protected void onPostExecute(Object result) {

            if(result == null){

                nonetwork.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
                progressbarlayout.setVisibility(View.GONE);


            }
            if (result != null){

                if (result.toString().equalsIgnoreCase("No Network Connection")){

                    nonetwork.setVisibility(View.VISIBLE);
                    error.setVisibility(View.GONE);
                    progressbarlayout.setVisibility(View.GONE);


                }
                else{
                    userprofile muserprofile = (com.dammy.android.emarketvendor.Models.userprofile) result;
                    addressvalue.setText(muserprofile.getAddress());
                    cityvalue.setText(muserprofile.getCity());
                    areavalue.setText(muserprofile.getArea());
                    phonevalue.setText(formatphone(muserprofile.getPhone()));
                    lat = muserprofile.getLat();
                    log = muserprofile.getLog();
                    InitializeMap(lat,log);

                    getOrderInfo getOrderInfo = new getOrderInfo();
                    getOrderInfo.execute();

                }
            }
        }
    }

    private String formatphone(String value){
        String newval;
        if(value.startsWith("0")){
             newval = value;
        }
        else{
             newval = "+234"+value;

        }
        return newval;
    }
    public  class getOrderInfo extends AsyncTask{
        String prompt;
        private  String URL = "http://jl-market.com/vendor/getOrderInformation.php";

        @Override
        protected void onPreExecute() {

            }
        @Override
        protected Object doInBackground(Object[] objects) {
            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }
            String serverResponse = new getorderqueryinfo().GetData(URL, orderid);
            if (serverResponse != null) {
                try {
                    JSONObject jsonObject = new JSONObject(serverResponse);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");

                     if (status.equalsIgnoreCase("available")) {
                        orderinfo orderinfo = ParseOrderInfoJson(info);
                        return orderinfo;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        protected void onPostExecute(Object result) {
            progressbarlayout.setVisibility(View.GONE);
            if(result == null){

                nonetwork.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
                progressbarlayout.setVisibility(View.GONE);


            }
            if (result != null){

               if (result.toString().equalsIgnoreCase("No Network Connection")){
                   nonetwork.setVisibility(View.VISIBLE);
                   error.setVisibility(View.GONE);
                   progressbarlayout.setVisibility(View.GONE);



               }
               else{
                  orderinfo orderinfo = (com.dammy.android.emarketvendor.Models.orderinfo) result;
                  paymentmethodvalue.setText(orderinfo.getPaymentmethod());
                  statusvalue.setText(orderinfo.getStatus());

                  rootscrollView.setVisibility(View.VISIBLE);
                  vieworder.setVisibility(View.VISIBLE);
                  nonetwork.setVisibility(View.GONE);
                   error.setVisibility(View.GONE);
                   progressbarlayout.setVisibility(View.GONE);


               }
            }
        }
    }

    public class updatestatustask extends AsyncTask{
            String prompt;
            String statusname;
            private  String URL = "http://jl-market.com/vendor/updateorderstatus.php";
       public updatestatustask(String statusname){
           this.statusname = statusname;
       }

            @Override
            protected void onPreExecute() {
                showloadingdialog();
                }
        @Override
        protected Object doInBackground(Object[] objects) {
            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }
            String serverresponse = new getorderstatusinfo().GetData(URL,orderid,statusname,useremail);
            return serverresponse;
        }

        protected void onPostExecute(Object result) {
            loadingdialog.dismiss();
            //Toast.makeText(newOrderuserInfo.this, result.toString(), Toast.LENGTH_SHORT).show();
            if (result == null) {
                Toast.makeText(newOrderuserInfo.this, "Error Occured", Toast.LENGTH_SHORT).show();
            }
            else if(result.toString().equalsIgnoreCase(prompt)){
                Toast.makeText(newOrderuserInfo.this, prompt, Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(newOrderuserInfo.this, "Order Status Updated Successfully", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public class getorderqueryinfo {

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

    public class getorderstatusinfo {

        public String GetData(String url,String orderid,String status,String userid) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();


                RequestBody formBody = new FormBody.Builder()
                        .add("orderid",orderid)
                        .add("status",status)
                        .add("userid",userid)
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
    public class getuserqueryinfo {

        public String GetData(String url,String userid) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();


                RequestBody formBody = new FormBody.Builder()
                        .add("userid",userid)
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
    private void showloadingdialog() {
        loadingdialog = new Dialog(this, R.style.Dialog_Theme);
        loadingdialog.setContentView(R.layout.loadingdialog);
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.loading);
        ImageView image = loadingdialog.findViewById(R.id.loadingimage);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(image);
        Glide.with(this).load(R.drawable.loading).into(imageViewTarget);
        loadingdialog.show();
        loadingdialog.setCancelable(false);
        //getActivity().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        loadingdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loadingdialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            loadingdialog.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }
    private Boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private userprofile ParseUserProfileJson(JSONObject jsonObject) throws JSONException {
        userprofile userprofile = null;
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject j = jsonArray.getJSONObject(i);
            String address = j.getString("address");
            double log = j.getDouble("log");
            double lat = j.getDouble("lat");
            String phone = j.getString("phone");
            String city = j.getString("city");
            String area = j.getString("area");
            userprofile = new userprofile(address, lat, log, city, area,phone);
        }

        return userprofile;

    }

    private orderinfo ParseOrderInfoJson(JSONObject jsonObject) throws JSONException {
        orderinfo morderinfo = null;
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject j = jsonArray.getJSONObject(i);
            String status = j.getString("orderstatus");
            String paymentmethod = j.getString("paymentmethod");
            morderinfo = new orderinfo(paymentmethod,status);
            }

        return morderinfo;

    }




    private void initView() {
        toolbar = findViewById(R.id.orderinfotoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);

        mapView = findViewById(R.id.orderinfomap);
        Title = findViewById(R.id.orderinfotoolbartitle);
        vieworder = findViewById(R.id.orderinfovieworder);
        locationtitle = findViewById(R.id.orderinfolocation);
        addresstitle = findViewById(R.id.orderinfoaddresstitle);
        addressvalue = findViewById(R.id.orderinfoaddressvalue);
        citytitle = findViewById(R.id.orderinfocitytitle);
        cityvalue = findViewById(R.id.orderinfocityvalue);
        areatitle = findViewById(R.id.orderinfoareatitle);
        areavalue = findViewById(R.id.orderinfoareavalue);
        phonetitle = findViewById(R.id.orderinfophonetitle);
        phonevalue = findViewById(R.id.orderinfophonevalue);
        paymentmethodtitle = findViewById(R.id.orderinfopaymentmethodtitle);
        paymentmethodvalue = findViewById(R.id.orderinfopaymentmethodvalue);
        statustitle = findViewById(R.id.orderinfostatustitle);
        statusvalue = findViewById(R.id.orderinfostatusvalue);
        upadatestatus = findViewById(R.id.updatestatusicon);
        makecall = findViewById(R.id.orderinfomakecallicon);
        progressbarlayout = findViewById(R.id.orderinfoprogressbarlayout);

        nonetwork = findViewById(R.id.nonetworklayout);
        error = findViewById(R.id.errorlayout);

        errorbutton = findViewById(R.id.errorbutton);
        nonetworkbutton =findViewById(R.id.nonetworkbutton);
        errortext = findViewById(R.id.errortext);
        nonetworktext = findViewById(R.id.nonetworktext);

        Typeface customfont2 = Typeface.createFromAsset(getAssets(), "Kylo-Regular.otf");
        errortext.setTypeface(customfont2);
        errorbutton.setTypeface(customfont2);

        nonetworktext.setTypeface(customfont2);
        nonetworkbutton.setTypeface(customfont2);

        rootscrollView = findViewById(R.id.orderinfoscrollview);
        rootscrollView.setVisibility(View.GONE);
        vieworder.setVisibility(View.GONE);



        Typeface customfont= Typeface.createFromAsset(getAssets(),"Kylo-Light.otf");
        Title.setTypeface(customfont);
        addresstitle.setTypeface(customfont);
        addressvalue.setTypeface(customfont);
        citytitle.setTypeface(customfont);
        cityvalue.setTypeface(customfont);
        areatitle.setTypeface(customfont);
        areavalue.setTypeface(customfont);
        phonetitle.setTypeface(customfont);
        phonevalue.setTypeface(customfont);
        paymentmethodtitle.setTypeface(customfont);
        paymentmethodvalue.setTypeface(customfont);
        statustitle.setTypeface(customfont);
        statusvalue.setTypeface(customfont);
        vieworder.setTypeface(customfont);
        locationtitle.setTypeface(customfont);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        mapView.onResume();

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onStart() {

        super.onStart();
        mapView.onStart();
    }
    @Override
    public void onLowMemory(){
        super.onLowMemory();
        mapView.onLowMemory();
    }
    private void InitializeMap(double lat,double log){
        if(googleMap == null){

            mapView.getMapAsync( new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap Map) {
                    googleMap = Map;
                    googleMap.getUiSettings().setRotateGesturesEnabled(false);
                    googleMap.getUiSettings().setScrollGesturesEnabled(false);
                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,log))
                            .draggable(false));


                    mapView.onResume();
                    moveTocurrentposition(new LatLng(lat,log));
                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            //Intent intent = new Intent(newOrderuserInfo.this, Fullmap.class);

                        }
                    });

                }
            });
        }
    }

    private void moveTocurrentposition(LatLng currentlocation){
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentlocation,15));
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15),5000,null);
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
