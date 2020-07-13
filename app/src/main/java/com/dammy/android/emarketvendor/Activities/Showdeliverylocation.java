package com.dammy.android.emarketvendor.Activities;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.dammy.android.emarketvendor.Adapter.DealAdapter;
import com.dammy.android.emarketvendor.MainActivity;
import com.dammy.android.emarketvendor.Models.delivery_location_model;
import com.dammy.android.emarketvendor.Models.vendorprofile;
import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.openingPageActivity;
import com.dammy.android.emarketvendor.service.update_locations;
import com.dammy.android.emarketvendor.service.updateservice.updatedeals;
import com.dammy.android.emarketvendor.splashscreen;
import com.dammy.android.emarketvendor.Adapter.delivery_location_adapter;


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

public class Showdeliverylocation extends AppCompatActivity {
    Toolbar toolbar;
    TextView title;
    Button addmorelocation;
    Dialog loadingdialog;
    RecyclerView recyclerView;
    ArrayList<vendorprofile> vendorprofiles;
    LinearLayout progresbarlayout,errorlayout,nonetworklayout,no_location_layout;
    Button errorbutton,nonetworkbutton;
    TextView errortext,nonetworktext,no_location_text;
    ArrayList<delivery_location_model> delivery_location_list = new ArrayList<>();
    delivery_location_adapter delivery_location_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdeliverylocation);
        if(savedInstanceState != null){

            if(savedInstanceState.getInt("pid",-1) == android.os.Process.myPid()){

            }
            else{

                startActivity(new Intent(this,splashscreen.class));

            }
        }
        initView();
        getComingvalues();
        setOnclickListener();
        new getlocationtask().execute();
    }

    private void getComingvalues() {
        if (getIntent().getParcelableArrayListExtra("vendorprofilefromhelp") != null) {
            vendorprofiles = getIntent().getParcelableArrayListExtra("vendorprofilefromhelp");
            }
            if(getIntent().getParcelableArrayListExtra("vendorprofile") != null){
            vendorprofiles = getIntent().getParcelableArrayListExtra("vendorprofile");
            }

    }

    private void initView(){
        nonetworklayout = findViewById(R.id.nonetworklayout);
        nonetworkbutton = findViewById(R.id.nonetworkbutton);
        nonetworktext = findViewById(R.id.nonetworktext);
        progresbarlayout = findViewById(R.id.progressbarlayout);
        errorlayout = findViewById(R.id.errorlayout);
        errortext = findViewById(R.id.errortext);
        errorbutton = findViewById(R.id.errorbutton);
        toolbar = findViewById(R.id.deliverylocationtoolbar);
        title = findViewById(R.id.deliverylocationtitle);
        recyclerView = findViewById(R.id.show_delivery_location_recyclerview);
        addmorelocation = findViewById(R.id.addmoredeliverylocationbutton);
        no_location_layout = findViewById(R.id.show_delivery_locations_no_location_layout);
        no_location_text = findViewById(R.id.no_location_text);


        Typeface customfont = Typeface.createFromAsset(getAssets(), "Kylo-Light.otf");
        errorbutton.setTypeface(customfont);
        errortext.setTypeface(customfont);
        nonetworktext.setTypeface(customfont);
        nonetworkbutton.setTypeface(customfont);
        title.setTypeface(customfont);
        addmorelocation.setTypeface(customfont);
        no_location_text.setTypeface(customfont);




        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);
        }

        private void setOnclickListener(){

        nonetworkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getlocationtask().execute();
            }
        });

            errorbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new getlocationtask().execute();
                }
            });
        addmorelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Showdeliverylocation.this,ChooseDeliveryLocation.class);
                startActivityForResult(intent,5);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && requestCode == 5) {

            if (resultCode == 50) {

            } else {
                String city = data.getStringExtra("city");
                String area = data.getStringExtra("area");

                new AddDeliveryLocations(area,city).execute();


            }
        }

        }


        private class AddDeliveryLocations extends AsyncTask{

            String prompt;
            String area,city;
            private String URL = "http://jl-market.com/vendor/InsertDeliveryLocation.php";


            public AddDeliveryLocations(String marea, String mcity){
                this.area = marea;
                this.city = mcity;
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

                String serverresponse = new Addlocation().GetData(URL,vendorprofiles.get(0).getEmail(),city,area);
                try {
                    JSONObject jsonObject = new JSONObject(serverresponse);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");

                    if (status.equalsIgnoreCase("insertsuccess")) {
                        prompt = "insertsuccess";
                        return prompt;
                    }
                    else if(status.equalsIgnoreCase("insertfailure")){

                        prompt = "insertfailure";
                        return prompt;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            protected void onPostExecute(Object result) {


                loadingdialog.dismiss();
                if (result != null) {

                    if (result.toString().equalsIgnoreCase("No Network Connection")) {
                        Toast.makeText(Showdeliverylocation.this, "No Network Connection", Toast.LENGTH_LONG).show();
                    } else if (result.toString().equalsIgnoreCase("insertsuccess")) {

                        Toast.makeText(Showdeliverylocation.this, "Location Added Successfully", Toast.LENGTH_LONG).show();
                        Intent serviceintent = new Intent(Showdeliverylocation.this, update_locations.class);
                        serviceintent.putExtra("url", "http://jl-market.com/vendor/getDeliveryLocations.php");
                        serviceintent.putExtra("vendorid", vendorprofiles.get(0).getEmail());
                        startService(serviceintent);
                    }
                    else if(result.toString().equalsIgnoreCase("insertfailure")){

                        Toast.makeText(Showdeliverylocation.this, "Error Occured Please Try Again", Toast.LENGTH_LONG).show();

                    }
                    else{
                        Toast.makeText(Showdeliverylocation.this, "Error Occured", Toast.LENGTH_SHORT).show();

                    }
                }
                else{
                    Toast.makeText(Showdeliverylocation.this, "Error Occured", Toast.LENGTH_SHORT).show();

                }

            }
        }


    private Boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public class Addlocation {

        public String GetData(String url,String email,String city,String area) {
            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();


                RequestBody formBody = new FormBody.Builder()
                        .add("vendorid",email)
                        .add("city",city)
                        .add("area",area)
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

    private void showloadingdialog() {
        loadingdialog = new Dialog(Showdeliverylocation.this, android.R.style.Theme_Light);
        loadingdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingdialog.setContentView(R.layout.loadingdialog);
        ImageView image = loadingdialog.findViewById(R.id.loadingimage);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(image);
        Glide.with(Showdeliverylocation.this).load(R.drawable.loading).into(imageViewTarget);
        loadingdialog.setCancelable(false);

        loadingdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        loadingdialog.show();
    }

    private class getlocationtask extends AsyncTask {

        String prompt;
        private String URL = "http://jl-market.com/vendor/getDeliveryLocations.php";

        @Override
        protected void onPreExecute() {
            progresbarlayout.setVisibility(View.VISIBLE);
            addmorelocation.setVisibility(View.GONE);
            errorlayout.setVisibility(View.GONE);
            nonetworklayout.setVisibility(View.GONE);
            no_location_layout.setVisibility(View.GONE);

        }

        @Override
        protected Object doInBackground(Object[] objects) {

            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }
            String serverresponse = new Getlocation().GetData(URL, vendorprofiles.get(0).getEmail());


            try {
                JSONObject jsonObject = new JSONObject(serverresponse);
                JSONObject info = jsonObject.getJSONObject("info");
                String status = info.getString("status");

                if (status.equalsIgnoreCase("available")) {

                    ArrayList<delivery_location_model> delivery_location_models = ParseJson(info);
                    return delivery_location_models;
                }
                else if(status.equalsIgnoreCase("unavailable")){

                    prompt = "unavailable";
                    return  prompt;
                }
            } catch (JSONException e) {
                Toast.makeText(Showdeliverylocation.this, "its here in passing error", Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Object result) {


            //Toast.makeText(Showdeliverylocation.this, result.toString(), Toast.LENGTH_SHORT).show();

            //   Toast.makeText(signup.this, result.toString(), Toast.LENGTH_SHORT).show();
           if (result == null) {
                progresbarlayout.setVisibility(View.GONE);
                addmorelocation.setVisibility(View.GONE);
                errorlayout.setVisibility(View.VISIBLE);
                nonetworklayout.setVisibility(View.GONE);
                no_location_layout.setVisibility(View.GONE);


            } else if (result.toString().equalsIgnoreCase("No Network Connection")) {
                progresbarlayout.setVisibility(View.GONE);
                addmorelocation.setVisibility(View.GONE);
                errorlayout.setVisibility(View.GONE);
                nonetworklayout.setVisibility(View.VISIBLE);
                no_location_layout.setVisibility(View.GONE);
            }


            else if (result != null) {
                if (result instanceof ArrayList) {

                    //Toast.makeText(Showdeliverylocation.this, "its here in arraylist", Toast.LENGTH_SHORT).show();
                    delivery_location_list = (ArrayList<delivery_location_model>) result;
                    progresbarlayout.setVisibility(View.GONE);
                    addmorelocation.setVisibility(View.VISIBLE);
                    errorlayout.setVisibility(View.GONE);
                    nonetworklayout.setVisibility(View.GONE);
                    no_location_layout.setVisibility(View.GONE);
                   setUpRecyclerView(delivery_location_list);

                }
                if(result.toString().equalsIgnoreCase("unavailable")){

                    progresbarlayout.setVisibility(View.GONE);
                    addmorelocation.setVisibility(View.GONE);
                    errorlayout.setVisibility(View.GONE);
                    nonetworklayout.setVisibility(View.GONE);
                    no_location_layout.setVisibility(View.VISIBLE);
                    addmorelocation.setVisibility(View.VISIBLE);
                }

            }
            else {
                progresbarlayout.setVisibility(View.GONE);
                addmorelocation.setVisibility(View.GONE);
                errorlayout.setVisibility(View.VISIBLE);
                nonetworklayout.setVisibility(View.GONE);
                no_location_layout.setVisibility(View.GONE);
            }
        }
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

    @Override
    public void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        outstate.putInt("pid",android.os.Process.myPid());

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }



    private void setUpRecyclerView(ArrayList<delivery_location_model> delivery_location_models){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        delivery_location_adapter = new delivery_location_adapter(delivery_location_models,Showdeliverylocation.this);
        recyclerView.setAdapter(delivery_location_adapter);

    }



    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if(bundle != null){

                delivery_location_list = bundle.getParcelableArrayList("locationlist");
                if(delivery_location_list != null){
                    setUpRecyclerView(delivery_location_list);
                    progresbarlayout.setVisibility(View.GONE);
                    addmorelocation.setVisibility(View.VISIBLE);
                    errorlayout.setVisibility(View.GONE);
                    nonetworklayout.setVisibility(View.GONE);
                    no_location_layout.setVisibility(View.GONE);


                }
            }
        }
    };



    @Override
    public void onResume() {
        super.onResume();
       registerReceiver(receiver, new IntentFilter("com.dammy.android.emarketvendor.service.updatelocation"));

    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
