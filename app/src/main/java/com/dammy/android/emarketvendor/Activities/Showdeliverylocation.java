package com.dammy.android.emarketvendor.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.dammy.android.emarketvendor.MainActivity;
import com.dammy.android.emarketvendor.Models.vendorprofile;
import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.openingPageActivity;
import com.dammy.android.emarketvendor.splashscreen;

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
    TextView title,save;
    Button addmorelocation;
    LinearLayout deliverylocation1,deliverylocation2,deliverylocation3,deliverylocation4,deliverylocation5,deliverylocation6,deliverylocation7,deliverylocation8,deliverylocation9,deliverylocation10;
    TextView deliverylocation1title,deliverylocation2title,deliverylocation3title,deliverylocation4title,deliverylocation5title,deliverylocation6title,deliverylocation7title,deliverylocation8title,deliverylocation9title,deliverylocation10title;
    TextView deliverycity1,deliverycity2,deliverycity3,deliverycity4,deliverycity5,deliverycity6,deliverycity7,deliverycity8,deliverycity9,deliverycity10;
    TextView deliveryarea1,deliveryarea2,deliveryarea3,deliveryarea4,deliveryarea5,deliveryarea6,deliveryarea7,deliveryarea8,deliveryarea9,deliveryarea10;
    String city1,city2,city3,city4,city5,city6,city7,city8,city9,city10;
    String area1,area2,area3,area4,area5,area6,area7,area8,area9,area10;
    Dialog loadingdialog;
    ArrayList<vendorprofile> vendorprofiles;
    LinearLayout deliverylocationroot,progresbarlayout,errorlayout,nonetworklayout;
    Button errorbutton,nonetworkbutton;
    TextView errortext,nonetworktext;

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
        new showlocationtask().execute();
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
        deliverylocationroot = findViewById(R.id.deliverylocationroot);
        progresbarlayout = findViewById(R.id.progressbarlayout);
        errorlayout = findViewById(R.id.errorlayout);
        errortext = findViewById(R.id.errortext);
        errorbutton = findViewById(R.id.errorbutton);
        toolbar = findViewById(R.id.deliverylocationtoolbar);
        title = findViewById(R.id.deliverylocationtitle);
        addmorelocation = findViewById(R.id.addmoredeliverylocationbutton);
        deliverylocation1 = findViewById(R.id.deliverylocation1layout);
        deliverylocation2 = findViewById(R.id.deliverylocation2layout);
        deliverylocation3 = findViewById(R.id.deliverylocation3layout);
        deliverylocation4 = findViewById(R.id.deliverylocation4layout);
        deliverylocation5 = findViewById(R.id.deliverylocation5layout);
        deliverylocation6 = findViewById(R.id.deliverylocation6layout);
        deliverylocation7 = findViewById(R.id.deliverylocation7layout);
        deliverylocation8 = findViewById(R.id.deliverylocation8layout);
        deliverylocation9 = findViewById(R.id.deliverylocation9layout);
        deliverylocation10 = findViewById(R.id.deliverylocation10layout);

        deliverylocation1title = findViewById(R.id.deliverylocation1title);
        deliverylocation2title = findViewById(R.id.deliverylocation2title);
        deliverylocation3title = findViewById(R.id.deliverylocation3title);
        deliverylocation4title = findViewById(R.id.deliverylocation4title);
        deliverylocation5title = findViewById(R.id.deliverylocation5title);
        deliverylocation6title = findViewById(R.id.deliverylocation6title);
        deliverylocation7title = findViewById(R.id.deliverylocation7title);
        deliverylocation8title = findViewById(R.id.deliverylocation8title);
        deliverylocation9title = findViewById(R.id.deliverylocation9title);
        deliverylocation10title = findViewById(R.id.deliverylocation10title);


        deliverycity1 = findViewById(R.id.deliverycity1value);
        deliverycity2 = findViewById(R.id.deliverycity2value);
        deliverycity3 = findViewById(R.id.deliverycity3value);
        deliverycity4 = findViewById(R.id.deliverycity4value);
        deliverycity5 = findViewById(R.id.deliverycity5value);
        deliverycity6 = findViewById(R.id.deliverycity6value);
        deliverycity7 = findViewById(R.id.deliverycity7value);
        deliverycity8 = findViewById(R.id.deliverycity8value);
        deliverycity9 = findViewById(R.id.deliverycity9value);
        deliverycity10 = findViewById(R.id.deliverycity10value);


        deliveryarea1 = findViewById(R.id.deliveryarea1value);
        deliveryarea2 = findViewById(R.id.deliveryarea2value);
        deliveryarea3 = findViewById(R.id.deliveryarea3value);
        deliveryarea4 = findViewById(R.id.deliveryarea4value);
        deliveryarea5 = findViewById(R.id.deliveryarea5value);
        deliveryarea6 = findViewById(R.id.deliveryarea6value);
        deliveryarea7 = findViewById(R.id.deliveryarea7value);
        deliveryarea8 = findViewById(R.id.deliveryarea8value);
        deliveryarea9 = findViewById(R.id.deliveryarea9value);
        deliveryarea10 = findViewById(R.id.deliveryarea10value);

        save = findViewById(R.id.savedeliverylocation);

        Typeface customfont = Typeface.createFromAsset(getAssets(), "Kylo-Light.otf");

        errorbutton.setTypeface(customfont);
        errortext.setTypeface(customfont);
        nonetworktext.setTypeface(customfont);
        nonetworkbutton.setTypeface(customfont);
        deliverylocation1title.setTypeface(customfont);
        deliverylocation2title.setTypeface(customfont);
        deliverylocation3title.setTypeface(customfont);
        deliverylocation4title.setTypeface(customfont);
        deliverylocation5title.setTypeface(customfont);
        deliverylocation6title.setTypeface(customfont);
        deliverylocation7title.setTypeface(customfont);
        deliverylocation8title.setTypeface(customfont);
        deliverylocation9title.setTypeface(customfont);
        deliverylocation10title.setTypeface(customfont);

        save.setTypeface(customfont);
        deliverycity1.setTypeface(customfont);
        deliverycity2.setTypeface(customfont);
        deliverycity3.setTypeface(customfont);
        deliverycity4.setTypeface(customfont);
        deliverycity5.setTypeface(customfont);
        deliverycity6.setTypeface(customfont);
        deliverycity7.setTypeface(customfont);
        deliverycity8.setTypeface(customfont);
        deliverycity9.setTypeface(customfont);
        deliverycity10.setTypeface(customfont);

        deliveryarea1.setTypeface(customfont);
        deliveryarea2.setTypeface(customfont);
        deliveryarea3.setTypeface(customfont);
        deliveryarea4.setTypeface(customfont);
        deliveryarea5.setTypeface(customfont);
        deliveryarea6.setTypeface(customfont);
        deliveryarea7.setTypeface(customfont);
        deliveryarea8.setTypeface(customfont);
        deliveryarea9.setTypeface(customfont);
        deliveryarea10.setTypeface(customfont);

        title.setTypeface(customfont);
        addmorelocation.setTypeface(customfont);
        addmorelocation.setVisibility(View.GONE);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = 0.5f;
        getWindow().setAttributes(layoutParams);
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
                new showlocationtask().execute();
            }
        });

            errorbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new showlocationtask().execute();
                }
            });
        addmorelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(deliverylocation2.getVisibility() ==  View.GONE){
                    deliverylocation2.setVisibility(View.VISIBLE);
                    return;
                }
                if(deliverylocation3.getVisibility() ==  View.GONE){
                    deliverylocation3.setVisibility(View.VISIBLE);
                    return;
                }
                if(deliverylocation4.getVisibility() ==  View.GONE){
                    deliverylocation4.setVisibility(View.VISIBLE);
                    return;
                }
                if(deliverylocation5.getVisibility() ==  View.GONE){
                    deliverylocation5.setVisibility(View.VISIBLE);
                    return;
                }
                if(deliverylocation6.getVisibility() ==  View.GONE){
                    deliverylocation6.setVisibility(View.VISIBLE);
                    return;
                }
                if(deliverylocation7.getVisibility() ==  View.GONE){
                    deliverylocation7.setVisibility(View.VISIBLE);
                    return;
                }
                if(deliverylocation8.getVisibility() ==  View.GONE){
                    deliverylocation8.setVisibility(View.VISIBLE);
                    return;
                }
                if(deliverylocation9.getVisibility() ==  View.GONE){
                    deliverylocation9.setVisibility(View.VISIBLE);
                    return;
                }
                if(deliverylocation10.getVisibility() ==  View.GONE){
                    deliverylocation10.setVisibility(View.VISIBLE);
                    return;
                }
                else{
                    Toast.makeText(Showdeliverylocation.this, "You cannot deliver to more than 10 delivery location", Toast.LENGTH_LONG).show();
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                city1 = deliverycity1.getText().toString();
                city2 = deliverycity2.getText().toString();
                city3 = deliverycity3.getText().toString();
                city4 = deliverycity4.getText().toString();
                city5 = deliverycity5.getText().toString();
                city6 = deliverycity6.getText().toString();
                city7 = deliverycity7.getText().toString();
                city8 = deliverycity8.getText().toString();
                city9 = deliverycity9.getText().toString();
                city10 = deliverycity10.getText().toString();

                area1 = deliveryarea1.getText().toString();
                area2 = deliveryarea2.getText().toString();
                area3 = deliveryarea3.getText().toString();
                area4 = deliveryarea4.getText().toString();
                area5 = deliveryarea5.getText().toString();
                area6 = deliveryarea6.getText().toString();
                area7 = deliveryarea7.getText().toString();
                area8 = deliveryarea8.getText().toString();
                area9 = deliveryarea9.getText().toString();
                area10 = deliveryarea10.getText().toString();

                new savelocationtask().execute();


            }
        });

        deliverycity1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Showdeliverylocation.this, ChooseDeliveryLocation.class);
                    startActivityForResult(intent, 1);
                }
            });
            deliverycity2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Showdeliverylocation.this, ChooseDeliveryLocation.class);
                    startActivityForResult(intent, 2);
                }
            });
            deliverycity3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Showdeliverylocation.this, ChooseDeliveryLocation.class);
                    startActivityForResult(intent, 3);
                }
            });
            deliverycity4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Showdeliverylocation.this, ChooseDeliveryLocation.class);
                    startActivityForResult(intent, 4);
                }
            });
            deliverycity5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Showdeliverylocation.this, ChooseDeliveryLocation.class);
                    startActivityForResult(intent, 5);
                }
            });
            deliverycity6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Showdeliverylocation.this, ChooseDeliveryLocation.class);
                    startActivityForResult(intent, 6);
                }
            });
            deliverycity7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Showdeliverylocation.this, ChooseDeliveryLocation.class);
                    startActivityForResult(intent, 7);
                }
            });
            deliverycity8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Showdeliverylocation.this, ChooseDeliveryLocation.class);
                    startActivityForResult(intent, 8);
                }
            });
            deliverycity9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Showdeliverylocation.this, ChooseDeliveryLocation.class);
                    startActivityForResult(intent, 9);
                }
            });
            deliverycity10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Showdeliverylocation.this, ChooseDeliveryLocation.class);
                    startActivityForResult(intent, 10);
                }
            });

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            if (resultCode == 50) {

            } else {
                String city = data.getStringExtra("city");
                String area = data.getStringExtra("area");
                deliverycity1.setText(city);
                deliveryarea1.setText(area);

            }
        }
        if (requestCode == 2) {

            if (resultCode == 50) {

            } else {
                String city = data.getStringExtra("city");
                String area = data.getStringExtra("area");
                deliverycity2.setText(city);
                deliveryarea2.setText(area);

            }
        }
        if (requestCode == 3) {

            if (resultCode == 50) {

            } else {
                String city = data.getStringExtra("city");
                String area = data.getStringExtra("area");
                deliverycity3.setText(city);
                deliveryarea3.setText(area);

            }
        }
        if (requestCode == 4) {

            if (resultCode == 50) {

            } else {
                String city = data.getStringExtra("city");
                String area = data.getStringExtra("area");
                deliverycity4.setText(city);
                deliveryarea4.setText(area);

            }
        }
        if (requestCode == 5) {

            if (resultCode == 50) {

            } else {
                String city = data.getStringExtra("city");
                String area = data.getStringExtra("area");
                deliverycity5.setText(city);
                deliveryarea5.setText(area);

            }
        }
        if (requestCode == 6) {

            if (resultCode == 50) {

            } else {
                String city = data.getStringExtra("city");
                String area = data.getStringExtra("area");
                deliverycity6.setText(city);
                deliveryarea6.setText(area);

            }
        }
        if (requestCode == 7) {

            if (resultCode == 50) {

            } else {
                String city = data.getStringExtra("city");
                String area = data.getStringExtra("area");
                deliverycity7.setText(city);
                deliveryarea7.setText(area);

            }
        }
        if (requestCode == 8) {

            if (resultCode == 50) {

            } else {
                String city = data.getStringExtra("city");
                String area = data.getStringExtra("area");
                deliverycity8.setText(city);
                deliveryarea8.setText(area);

            }
        }
        if (requestCode == 9) {

            if (resultCode == 50) {

            } else {
                String city = data.getStringExtra("city");
                String area = data.getStringExtra("area");
                deliverycity9.setText(city);
                deliveryarea9.setText(area);

            }
        }
        if (requestCode == 10) {

            if (resultCode == 50) {

            } else {
                String city = data.getStringExtra("city");
                String area = data.getStringExtra("area");
                deliverycity10.setText(city);
                deliveryarea10.setText(area);

            }
        }
        }

        private class savelocationtask extends AsyncTask{

            String prompt;
            private String URL = "http://jl-market.com/vendor/updatedeliverylocation.php";

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

                String serverresponse = new savelocation().GetData(URL,vendorprofiles.get(0).getEmail(),city1,city2,city3,city4,city5,city6,city7,city8,city9,city10,area1,area2,area3,area4,area5,area6,area7,area8,area9,area10);
                try {
                    JSONObject jsonObject = new JSONObject(serverresponse);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");

                    if (status.equalsIgnoreCase("Updated successfully")) {
                        prompt = "Updated successfully";
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
                    } else if (result.toString().equalsIgnoreCase("Updated successfully")) {

                        Intent intent = new Intent(Showdeliverylocation.this, MainActivity.class);
                        intent.putParcelableArrayListExtra("vendorprofile", vendorprofiles);
                        Toast.makeText(Showdeliverylocation.this, "Successfully added delivery locations", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
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
    public class savelocation {

        public String GetData(String url,String email,String city1,String city2,String city3,String city4,String city5,String city6,String city7,String city8,String city9,String city10,String area1,String area2,String area3,String area4,String area5,String area6,String area7,String area8,String area9,String area10) {
            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                RequestBody formBody = new FormBody.Builder()
                        .add("deliverycity1",city1)
                        .add("deliverycity2",city2)
                        .add("deliverycity3",city3)
                        .add("deliverycity4",city4)
                        .add("deliverycity5",city5)
                        .add("deliverycity6",city6)
                        .add("deliverycity7",city7)
                        .add("deliverycity8",city8)
                        .add("deliverycity9",city9)
                        .add("deliverycity10",city10)
                        .add("deliveryarea1",area1)
                        .add("deliveryarea2",area2)
                        .add("deliveryarea3",area3)
                        .add("deliveryarea4",area4)
                        .add("deliveryarea5",area5)
                        .add("deliveryarea6",area6)
                        .add("deliveryarea7",area7)
                        .add("deliveryarea8",area8)
                        .add("deliveryarea9",area9)
                        .add("deliveryarea10",area10)
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

    private void showloadingdialog() {
        loadingdialog = new Dialog(this, R.style.Dialog_Theme);
        loadingdialog.setContentView(R.layout.loadingdialog);
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.loading);
        ImageView image = loadingdialog.findViewById(R.id.loadingimage);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(image);
        Glide.with(this).load(R.drawable.loading).into(imageViewTarget);
        loadingdialog.show();
        loadingdialog.setCancelable(false);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        loadingdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loadingdialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            loadingdialog.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private class showlocationtask extends AsyncTask {

        String prompt;
        private String URL = "http://jl-market.com/vendor/SelectDeliveryLocation.php";

        @Override
        protected void onPreExecute() {
            progresbarlayout.setVisibility(View.VISIBLE);
            deliverylocationroot.setVisibility(View.GONE);
            addmorelocation.setVisibility(View.GONE);
            errorlayout.setVisibility(View.GONE);
            nonetworklayout.setVisibility(View.GONE);

        }

        @Override
        protected Object doInBackground(Object[] objects) {

            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }
            String serverresponse = new selectlocation().GetData(URL, vendorprofiles.get(0).getEmail());

            try {
                JSONObject jsonObject = new JSONObject(serverresponse);
                JSONObject info = jsonObject.getJSONObject("info");
                String status = info.getString("status");

                if (status.equalsIgnoreCase("deliverylocationavailable")) {

                    vendorprofile vendorprofile = ParseJson(info);
                    return vendorprofile;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Object result) {

            progresbarlayout.setVisibility(View.VISIBLE);

            //   Toast.makeText(signup.this, result.toString(), Toast.LENGTH_SHORT).show();
            if (result == null) {
                progresbarlayout.setVisibility(View.GONE);
                deliverylocationroot.setVisibility(View.GONE);
                addmorelocation.setVisibility(View.GONE);
                errorlayout.setVisibility(View.VISIBLE);
                nonetworklayout.setVisibility(View.GONE);

            } else if (result.toString().equalsIgnoreCase("No Network Connection")) {
                progresbarlayout.setVisibility(View.GONE);
                deliverylocationroot.setVisibility(View.GONE);
                addmorelocation.setVisibility(View.GONE);
                errorlayout.setVisibility(View.GONE);
                nonetworklayout.setVisibility(View.VISIBLE);
            } else if (result != null) {
                if (result instanceof vendorprofile) {
                 vendorprofile vendorprofile = (com.dammy.android.emarketvendor.Models.vendorprofile) result;


                    progresbarlayout.setVisibility(View.GONE);
                    deliverylocationroot.setVisibility(View.VISIBLE);
                    addmorelocation.setVisibility(View.VISIBLE);
                    errorlayout.setVisibility(View.GONE);
                    nonetworklayout.setVisibility(View.GONE);

                    city1 = vendorprofile.getDeliverycity1();
                    city2 = vendorprofile.getDeliverycity2();
                    city3 = vendorprofile.getDeliverycity3();
                    city4 = vendorprofile.getDeliverycity4();
                    city5 = vendorprofile.getDeliverycity5();
                    city6 = vendorprofile.getDeliverycity6();
                    city7 = vendorprofile.getDeliverycity7();
                    city8 = vendorprofile.getDeliverycity8();
                    city9 = vendorprofile.getDeliverycity9();
                    city10 = vendorprofile.getDeliverycity10();



                    deliverycity1.setText(city1);
                    deliverycity2.setText(city2);
                    deliverycity3.setText(city3);
                    deliverycity4.setText(city4);
                    deliverycity5.setText(city5);
                    deliverycity6.setText(city6);
                    deliverycity7.setText(city7);
                    deliverycity8.setText(city8);
                    deliverycity9.setText(city9);
                    deliverycity10.setText(city10);

                    if(!deliverycity1.getText().toString().equalsIgnoreCase("")){
                        deliverylocation1.setVisibility(View.VISIBLE);
                    }
                    if(!deliverycity2.getText().toString().equalsIgnoreCase("")){
                        deliverylocation2.setVisibility(View.VISIBLE);
                    }
                    if(!deliverycity3.getText().toString().equalsIgnoreCase("")){
                        deliverylocation3.setVisibility(View.VISIBLE);
                    }
                    if(!deliverycity4.getText().toString().equalsIgnoreCase("")){
                        deliverylocation4.setVisibility(View.VISIBLE);
                    }
                    if(!deliverycity5.getText().toString().equalsIgnoreCase("")){
                        deliverylocation5.setVisibility(View.VISIBLE);
                    }
                    if(!deliverycity6.getText().toString().equalsIgnoreCase("")){
                        deliverylocation6.setVisibility(View.VISIBLE);
                    }
                    if(!deliverycity7.getText().toString().equalsIgnoreCase("")){
                        deliverylocation7.setVisibility(View.VISIBLE);
                    }
                    if(!deliverycity8.getText().toString().equalsIgnoreCase("")){
                        deliverylocation8.setVisibility(View.VISIBLE);
                    }
                    if(!deliverycity9.getText().toString().equalsIgnoreCase("")){
                        deliverylocation9.setVisibility(View.VISIBLE);
                    }
                    if(!deliverycity10.getText().toString().equalsIgnoreCase("")){
                        deliverylocation10.setVisibility(View.VISIBLE);
                    }


                    area1 = vendorprofile.getDeliveryarea1();
                    area2 = vendorprofile.getDeliveryarea2();
                    area3 = vendorprofile.getDeliveryarea3();
                    area4 = vendorprofile.getDeliveryarea4();
                    area5 = vendorprofile.getDeliveryarea5();
                    area6 = vendorprofile.getDeliveryarea6();
                    area7 = vendorprofile.getDeliveryarea7();
                    area8 = vendorprofile.getDeliveryarea8();
                    area9 = vendorprofile.getDeliveryarea9();
                    area10 = vendorprofile.getDeliveryarea10();

                    deliveryarea1.setText(area1);
                    deliveryarea2.setText(area2);
                    deliveryarea3.setText(area3);
                    deliveryarea4.setText(area4);
                    deliveryarea5.setText(area5);
                    deliveryarea6.setText(area6);
                    deliveryarea7.setText(area7);
                    deliveryarea8.setText(area8);
                    deliveryarea9.setText(area9);
                    deliveryarea10.setText(area10);

                }
            }
            else {
                progresbarlayout.setVisibility(View.GONE);
                deliverylocationroot.setVisibility(View.GONE);
                addmorelocation.setVisibility(View.GONE);
                errorlayout.setVisibility(View.VISIBLE);
                nonetworklayout.setVisibility(View.GONE);
            }
        }
    }

    public class selectlocation {

        public String GetData(String url,String email) {
            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                RequestBody formBody = new FormBody.Builder()

                        .add("email",email)
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


    private vendorprofile ParseJson(JSONObject jsonObject) throws JSONException {
        vendorprofile vendorprofile = null;
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject j = jsonArray.getJSONObject(i);

            String deliverycity1 = j.getString("deliverycity1");
            String deliverycity2 = j.getString("deliverycity2");
            String deliverycity3 = j.getString("deliverycity3");
            String deliverycity4 = j.getString("deliverycity4");
            String deliverycity5 = j.getString("deliverycity5");
            String deliverycity6 = j.getString("deliverycity6");
            String deliverycity7 = j.getString("deliverycity7");
            String deliverycity8 = j.getString("deliverycity8");
            String deliverycity9 = j.getString("deliverycity9");
            String deliverycity10 = j.getString("deliverycity10");
            String deliveryarea1 = j.getString("deliveryarea1");
            String deliveryarea2 = j.getString("deliveryarea2");
            String deliveryarea3 = j.getString("deliveryarea3");
            String deliveryarea4 = j.getString("deliveryarea4");
            String deliveryarea5 = j.getString("deliveryarea5");
            String deliveryarea6 = j.getString("deliveryarea6");
            String deliveryarea7 = j.getString("deliveryarea7");
            String deliveryarea8 = j.getString("deliveryarea8");
            String deliveryarea9 = j.getString("deliveryarea9");
            String deliveryarea10 = j.getString("deliveryarea10");

             vendorprofile = new vendorprofile();
            if(deliverycity1.equalsIgnoreCase("null")){
                deliverycity1 = "";
            }
            if(deliverycity2.equalsIgnoreCase("null")){
                deliverycity2 = "";
            }
            if(deliverycity3.equalsIgnoreCase("null")){
                deliverycity3 = "";
            }
            if(deliverycity4.equalsIgnoreCase("null")){
                deliverycity4 = "";
            }
            if(deliverycity5.equalsIgnoreCase("null")){
                deliverycity5 = "";
            }
            if(deliverycity6.equalsIgnoreCase("null")){
                deliverycity6 = "";
            }
            if(deliverycity7.equalsIgnoreCase("null")){
                deliverycity7 = "";
            }
            if(deliverycity8.equalsIgnoreCase("null")){
                deliverycity8 = "";
            }
            if(deliverycity9.equalsIgnoreCase("null")){
                deliverycity9 = "";
            }
            if(deliverycity10.equalsIgnoreCase("null")){
                deliverycity10 = "";
            }

            if(deliveryarea1.equalsIgnoreCase("null")){
                deliveryarea1 = "";
            }
            if(deliveryarea2.equalsIgnoreCase("null")){
                deliveryarea2 = "";
            }
            if(deliveryarea3.equalsIgnoreCase("null")){
                deliveryarea3 = "";
            }
            if(deliveryarea4.equalsIgnoreCase("null")){
                deliveryarea4 = "";
            }
            if(deliveryarea5.equalsIgnoreCase("null")){
                deliveryarea5 = "";
            }
            if(deliveryarea6.equalsIgnoreCase("null")){
                deliveryarea6 = "";
            }
            if(deliveryarea7.equalsIgnoreCase("null")){
                deliveryarea7 = "";
            }
            if(deliveryarea8.equalsIgnoreCase("null")){
                deliveryarea8 = "";
            }
            if(deliveryarea9.equalsIgnoreCase("null")){
                deliveryarea9 = "";
            }
            if(deliveryarea10.equalsIgnoreCase("null")){
                deliveryarea10 = "";
            }





            vendorprofile.setDeliverycity1(deliverycity1);
            vendorprofile.setDeliverycity2(deliverycity2);
            vendorprofile.setDeliverycity3(deliverycity3);
            vendorprofile.setDeliverycity4(deliverycity4);
            vendorprofile.setDeliverycity5(deliverycity5);
            vendorprofile.setDeliverycity6(deliverycity6);
            vendorprofile.setDeliverycity7(deliverycity7);
            vendorprofile.setDeliverycity8(deliverycity8);
            vendorprofile.setDeliverycity9(deliverycity9);
            vendorprofile.setDeliverycity10(deliverycity10);

            vendorprofile.setDeliveryarea1(deliveryarea1);
            vendorprofile.setDeliveryarea2(deliveryarea2);
            vendorprofile.setDeliveryarea3(deliveryarea3);
            vendorprofile.setDeliveryarea4(deliveryarea4);
            vendorprofile.setDeliveryarea5(deliveryarea5);
            vendorprofile.setDeliveryarea6(deliveryarea6);
            vendorprofile.setDeliveryarea7(deliveryarea7);
            vendorprofile.setDeliveryarea8(deliveryarea8);
            vendorprofile.setDeliveryarea9(deliveryarea9);
            vendorprofile.setDeliveryarea10(deliveryarea10);


        }
        return vendorprofile;

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



}
