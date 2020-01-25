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
import android.net.Uri;
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
import android.widget.EditText;
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

public class Showproductcategory extends AppCompatActivity {

    EditText category1value,category2value,category3value,category4value,category5value,category6value,category7value,category8value,category9value,category10value;
    TextView category1title,category2title,category3title,category4title,category5title,category6title,category7title,category8title,category9title,category10title;
    Toolbar toolbar;
    TextView title,edit;
    Button addnewcategory;
    Dialog loadingdialog;
    String category1,category2,category3,category4,category5,category6,category7,category8,category9,category10;
    String fcategory1,fcategory2,fcategory3,fcategory4,fcategory5,fcategory6,fcategory7,fcategory8,fcategory9,fcategory10;
    ArrayList<vendorprofile> vendorprofiles;
    LinearLayout progressbarlayout,errorlayout,showcategoriescontainer,nonetworklayout;
    Button errorbutton,nonetworkbutton;
    TextView errortext,nonetworktext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showproductcategory);
        if(savedInstanceState != null){

            if(savedInstanceState.getInt("pid",-1) == android.os.Process.myPid()){

            }
            else{

                startActivity(new Intent(this,splashscreen.class));

            }
        }
        initView();
        getComingvalues();
        setOnclickListner();
        new showcategoriestask().execute();
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
        showcategoriescontainer = findViewById(R.id.showproductcategoriescontainer);
        errorbutton = findViewById(R.id.errorbutton);
        errorlayout = findViewById(R.id.errorlayout);
        errortext = findViewById(R.id.errortext);
        progressbarlayout = findViewById(R.id.progressbarlayout);
        toolbar = findViewById(R.id.productcategoriestoolbar);
        title = findViewById(R.id.productcategoriestitle);
        addnewcategory = findViewById(R.id.addmorecategorybutton);
        category1value = findViewById(R.id.category1value);
        category2value = findViewById(R.id.category2value);
        category3value = findViewById(R.id.category3value);
        category4value = findViewById(R.id.category4value);
        category5value = findViewById(R.id.category5value);
        category6value = findViewById(R.id.category6value);
        category7value = findViewById(R.id.category7value);
        category8value = findViewById(R.id.category8value);
        category9value = findViewById(R.id.category9value);
        category10value = findViewById(R.id.category10value);
        edit = findViewById(R.id.editcategory);
        category1title = findViewById(R.id.category1title);
        category2title = findViewById(R.id.category2title);
        category3title = findViewById(R.id.category3title);
        category4title = findViewById(R.id.category4title);
        category5title = findViewById(R.id.category5title);
        category6title = findViewById(R.id.category6title);
        category7title = findViewById(R.id.category7title);
        category8title = findViewById(R.id.category8title);
        category9title = findViewById(R.id.category9title);
        category10title =findViewById(R.id.category10title);

        Typeface customfont = Typeface.createFromAsset(getAssets(), "Kylo-Light.otf");
        title.setTypeface(customfont);
        nonetworkbutton.setTypeface(customfont);
        nonetworktext.setTypeface(customfont);
        errorbutton.setTypeface(customfont);
        errortext.setTypeface(customfont);
        edit.setTypeface(customfont);
        category1title.setTypeface(customfont);
        category2title.setTypeface(customfont);
        category3title.setTypeface(customfont);
        category4title.setTypeface(customfont);
        category5title.setTypeface(customfont);
        category6title.setTypeface(customfont);
        category7title.setTypeface(customfont);
        category8title.setTypeface(customfont);
        category9title.setTypeface(customfont);
        category10title.setTypeface(customfont);
        addnewcategory.setTypeface(customfont);
        category1value.setTypeface(customfont);
        category2value.setTypeface(customfont);
        category3value.setTypeface(customfont);
        category4value.setTypeface(customfont);
        category5value.setTypeface(customfont);
        category6value.setTypeface(customfont);
        category7value.setTypeface(customfont);
        category8value.setTypeface(customfont);
        category9value.setTypeface(customfont);
        category10value.setTypeface(customfont);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = 0.5f;
        getWindow().setAttributes(layoutParams);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);

        category2title.setVisibility(View.GONE);
        category3title.setVisibility(View.GONE);
        category4title.setVisibility(View.GONE);
        category5title.setVisibility(View.GONE);
        category6title.setVisibility(View.GONE);
        category7title.setVisibility(View.GONE);
        category8title.setVisibility(View.GONE);
        category9title.setVisibility(View.GONE);
        category10title.setVisibility(View.GONE);


        category2value.setVisibility(View.GONE);
        category3value.setVisibility(View.GONE);
        category4value.setVisibility(View.GONE);
        category5value.setVisibility(View.GONE);
        category6value.setVisibility(View.GONE);
        category7value.setVisibility(View.GONE);
        category8value.setVisibility(View.GONE);
        category9value.setVisibility(View.GONE);
        category10value.setVisibility(View.GONE);

        category1value.setEnabled(false);
        category2value.setEnabled(false);
        category3value.setEnabled(false);
        category4value.setEnabled(false);
        category5value.setEnabled(false);
        category6value.setEnabled(false);
        category7value.setEnabled(false);
        category8value.setEnabled(false);
        category9value.setEnabled(false);
        category10value.setEnabled(false);

        addnewcategory.setVisibility(View.GONE);

    }

    private void setOnclickListner(){

        nonetworkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new showcategoriestask().execute();
            }
        });
        addnewcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(category2value.getVisibility() == View.GONE){
                    category2value.setVisibility(View.VISIBLE);
                    category2title.setVisibility(View.VISIBLE);
                    return;
                }
                if(category3value.getVisibility() == View.GONE){
                    category3value.setVisibility(View.VISIBLE);
                    category3title.setVisibility(View.VISIBLE);
                    return;
                }
                if(category4value.getVisibility() == View.GONE){
                    category4value.setVisibility(View.VISIBLE);
                    category4title.setVisibility(View.VISIBLE);
                    return;
                }
                if(category5value.getVisibility() == View.GONE){
                    category5value.setVisibility(View.VISIBLE);
                    category5title.setVisibility(View.VISIBLE);
                    return;
                }
                if(category6value.getVisibility() == View.GONE){
                    category6value.setVisibility(View.VISIBLE);
                    category6title.setVisibility(View.VISIBLE);
                    return;
                }
                if(category7value.getVisibility() == View.GONE){
                    category7value.setVisibility(View.VISIBLE);
                    category7title.setVisibility(View.VISIBLE);
                    return;
                }
                if(category8value.getVisibility() == View.GONE){
                    category8value.setVisibility(View.VISIBLE);
                    category8title.setVisibility(View.VISIBLE);
                    return;
                }
                if(category9value.getVisibility() == View.GONE){
                    category9value.setVisibility(View.VISIBLE);
                    category9title.setVisibility(View.VISIBLE);
                    return;
                }
                if(category10value.getVisibility() == View.GONE){
                    category10value.setVisibility(View.VISIBLE);
                    category10title.setVisibility(View.VISIBLE);
                    return;
                }
                else{
                    Toast.makeText(Showproductcategory.this, "You can Only create up to 10 product categories", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit.getText().toString().equalsIgnoreCase("Edit")){
                    edit.setText("Save");

                    category1value.setEnabled(true);
                    category2value.setEnabled(true);
                    category3value.setEnabled(true);
                    category4value.setEnabled(true);
                    category5value.setEnabled(true);
                    category6value.setEnabled(true);
                    category7value.setEnabled(true);
                    category8value.setEnabled(true);
                    category9value.setEnabled(true);
                    category10value.setEnabled(true);
                }
                else{
                    edit.setText("Edit");

                    category1value.setEnabled(false);
                    category2value.setEnabled(false);
                    category3value.setEnabled(false);
                    category4value.setEnabled(false);
                    category5value.setEnabled(false);
                    category6value.setEnabled(false);
                    category7value.setEnabled(false);
                    category8value.setEnabled(false);
                    category9value.setEnabled(false);
                    category10value.setEnabled(false);


                    category1 = category1value.getText().toString();
                    category2 = category2value.getText().toString();
                    category3 = category3value.getText().toString();
                    category4 = category4value.getText().toString();
                    category5 = category5value.getText().toString();
                    category6 = category6value.getText().toString();
                    category7 = category7value.getText().toString();
                    category8 = category8value.getText().toString();
                    category9 = category9value.getText().toString();
                    category10 = category10value.getText().toString();

                    new saveCategorytask().execute();
                }
            }
        });

        errorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new showcategoriestask().execute();
            }
        });


    }

    private class saveCategorytask extends AsyncTask {

        String prompt;
        private String URL = "http://jl-market.com/vendor/updateproductcategory.php";

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

            String serverresponse = new savecategory().GetData(URL, vendorprofiles.get(0).getEmail(), category1, category2, category3, category4, category5, category6, category7, category8, category9, category10, fcategory1, fcategory2, fcategory3, fcategory4, fcategory5, fcategory6, fcategory7, fcategory8, fcategory9, fcategory10);
            try {
                JSONObject jsonObject = new JSONObject(serverresponse);
                JSONObject info = jsonObject.getJSONObject("info");
                String status = info.getString("status");

                if (status.equalsIgnoreCase("categoryupdatedsuccessfully")) {
                    prompt = "categoryupdatedsuccessfully";
                    return prompt;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return serverresponse;
        }

        protected void onPostExecute(Object result) {


            loadingdialog.dismiss();
            if (result != null) {

               // Toast.makeText(Showproductcategory.this, result.toString(), Toast.LENGTH_LONG).show();
                if (result.toString().equalsIgnoreCase("No Network Connection")) {
                    Toast.makeText(Showproductcategory.this, "No Network Connection", Toast.LENGTH_LONG).show();
                } else if (result.toString().equalsIgnoreCase("categoryupdatedsuccessfully")) {

                    Intent intent = new Intent(Showproductcategory.this, MainActivity.class);
                    intent.putParcelableArrayListExtra("vendorprofile", vendorprofiles);
                    Toast.makeText(Showproductcategory.this, "Categories created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Showproductcategory.this, "Error Occured", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(Showproductcategory.this, "Error Occured", Toast.LENGTH_SHORT).show();

            }

            }
        }



    private Boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public class savecategory {

        public String GetData(String url,String email,String category1,String category2,String category3,String category4,String category5,String category6,String category7,String category8,String category9,String category10,
                              String fcategory1,String fcategory2,String fcategory3,String fcategory4,String fcategory5,String fcategory6,String fcategory7,String fcategory8,String fcategory9,String fcategory10) {
            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                RequestBody formBody = new FormBody.Builder()
                        .add("category1",category1)
                        .add("category2",category2)
                        .add("category3",category3)
                        .add("category4",category4)
                        .add("category5",category5)
                        .add("category6",category6)
                        .add("category7",category7)
                        .add("category8",category8)
                        .add("category9",category9)
                        .add("category10",category10)
                        .add("fcategory1",fcategory1)
                        .add("fcategory2",fcategory2)
                        .add("fcategory3",fcategory3)
                        .add("fcategory4",fcategory4)
                        .add("fcategory5",fcategory5)
                        .add("fcategory6",fcategory6)
                        .add("fcategory7",fcategory7)
                        .add("fcategory8",fcategory8)
                        .add("fcategory9",fcategory9)
                        .add("fcategory10",fcategory10)
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

    private class showcategoriestask extends  AsyncTask{


        String prompt;
        private String URL = "http://jl-market.com/vendor/SelectProductCategories.php";

        @Override
        protected void onPreExecute() {
            progressbarlayout.setVisibility(View.VISIBLE);
            addnewcategory.setVisibility(View.GONE);
            showcategoriescontainer.setVisibility(View.GONE);
            errorlayout.setVisibility(View.GONE);
            nonetworklayout.setVisibility(View.GONE);

        }

        @Override
        protected Object doInBackground(Object[] objects) {

            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }
            String serverresponse = new showcategory().GetData(URL,vendorprofiles.get(0).getEmail());


            if (serverresponse != null) {
                try {
                    JSONObject jsonObject = new JSONObject(serverresponse);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");
                    if (status.equalsIgnoreCase("productcategoriesavailable")) {

                    vendorprofile vendorprofile = ParseJson(info);
                    return vendorprofile;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        protected void onPostExecute(Object result) {
            progressbarlayout.setVisibility(View.GONE);
            //   Toast.makeText(signup.this, result.toString(), Toast.LENGTH_SHORT).show();
            if (result == null) {

                progressbarlayout.setVisibility(View.GONE);
                addnewcategory.setVisibility(View.GONE);
                showcategoriescontainer.setVisibility(View.GONE);
                errorlayout.setVisibility(View.VISIBLE);
                nonetworklayout.setVisibility(View.GONE);

                  }

            else if (result.toString().equalsIgnoreCase("No Network Connection")) {
                progressbarlayout.setVisibility(View.GONE);
                addnewcategory.setVisibility(View.GONE);
                showcategoriescontainer.setVisibility(View.GONE);
                errorlayout.setVisibility(View.GONE);
                nonetworklayout.setVisibility(View.VISIBLE);
            }

            else if (result != null) {
                if (result instanceof vendorprofile) {
                    vendorprofile vendorprofile = (com.dammy.android.emarketvendor.Models.vendorprofile) result;
                    fcategory1 =  vendorprofile.getCategory1();
                    fcategory2 =  vendorprofile.getCategory2();
                    fcategory3 =  vendorprofile.getCategory3();
                    fcategory4 =  vendorprofile.getCategory4();
                    fcategory5 =  vendorprofile.getCategory5();
                    fcategory6 =  vendorprofile.getCategory6();
                    fcategory7 =  vendorprofile.getCategory7();
                    fcategory8 =  vendorprofile.getCategory8();
                    fcategory9 =  vendorprofile.getCategory9();
                    fcategory10 =  vendorprofile.getCategory10();



                    category1value.setText(fcategory1);
                    category2value.setText(fcategory2);
                    category3value.setText(fcategory3);
                    category4value.setText(fcategory4);
                    category5value.setText(fcategory5);
                    category6value.setText(fcategory6);
                    category7value.setText(fcategory7);
                    category8value.setText(fcategory8);
                    category9value.setText(fcategory9);
                    category10value.setText(fcategory10);

                    if (!category1value.getText().toString().equalsIgnoreCase("")) {
                        category1value.setVisibility(View.VISIBLE);
                        category1title.setVisibility(View.VISIBLE);
                    }
                    if (!category2value.getText().toString().equalsIgnoreCase("")) {
                        category2value.setVisibility(View.VISIBLE);
                        category2title.setVisibility(View.VISIBLE);
                    }
                    if (!category3value.getText().toString().equalsIgnoreCase("")) {
                        category3value.setVisibility(View.VISIBLE);
                        category3title.setVisibility(View.VISIBLE);
                    }
                    if (!category4value.getText().toString().equalsIgnoreCase("")) {
                        category4value.setVisibility(View.VISIBLE);
                        category4title.setVisibility(View.VISIBLE);
                    }
                    if (!category5value.getText().toString().equalsIgnoreCase("")) {
                        category5value.setVisibility(View.VISIBLE);
                        category5title.setVisibility(View.VISIBLE);
                    }
                    if (!category6value.getText().toString().equalsIgnoreCase("")) {
                        category6value.setVisibility(View.VISIBLE);
                        category6title.setVisibility(View.VISIBLE);
                    }
                    if (!category7value.getText().toString().equalsIgnoreCase("")) {
                        category7value.setVisibility(View.VISIBLE);
                        category7title.setVisibility(View.VISIBLE);
                    }
                    if (!category8value.getText().toString().equalsIgnoreCase("")) {
                        category8value.setVisibility(View.VISIBLE);
                        category8title.setVisibility(View.VISIBLE);
                    }
                    if (!category9value.getText().toString().equalsIgnoreCase("")) {
                        category9value.setVisibility(View.VISIBLE);
                        category9title.setVisibility(View.VISIBLE);
                    }
                    if (!category10value.getText().toString().equalsIgnoreCase("")) {
                        category10value.setVisibility(View.VISIBLE);
                        category10title.setVisibility(View.VISIBLE);
                    }

                    progressbarlayout.setVisibility(View.GONE);
                    addnewcategory.setVisibility(View.VISIBLE);
                    showcategoriescontainer.setVisibility(View.VISIBLE);
                    errorlayout.setVisibility(View.GONE);
                    nonetworklayout.setVisibility(View.GONE);

                }
            }
            else{
                progressbarlayout.setVisibility(View.GONE);
                addnewcategory.setVisibility(View.GONE);
                showcategoriescontainer.setVisibility(View.GONE);
                errorlayout.setVisibility(View.VISIBLE);
                nonetworklayout.setVisibility(View.GONE);
            }

        }

    }








    private vendorprofile ParseJson(JSONObject jsonObject) throws JSONException {
        vendorprofile vendorprofile = null;
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject j = jsonArray.getJSONObject(i);

            String category1 = j.getString("productcategory1");
            String category2 = j.getString("productcategory2");
            String category3 = j.getString("productcategory3");
            String category4 = j.getString("productcategory4");
            String category5 = j.getString("productcategory5");
            String category6 = j.getString("productcategory6");
            String category7 = j.getString("productcategory7");
            String category8 = j.getString("productcategory8");
            String category9 = j.getString("productcategory9");
            String category10 = j.getString("productcategory10");

            vendorprofile = new vendorprofile();
            if(category1.equalsIgnoreCase("null")){
                category1 = "";
            }
            if(category2.equalsIgnoreCase("null")){
                category2 = "";
            }
            if(category3.equalsIgnoreCase("null")){
                category3 = "";
            }
            if(category4.equalsIgnoreCase("null")){
                category4 = "";
            }
            if(category5.equalsIgnoreCase("null")){
                category5 = "";
            }
            if(category6.equalsIgnoreCase("null")){
                category6 = "";
            }
            if(category7.equalsIgnoreCase("null")){
                category7 = "";
            }
            if(category8.equalsIgnoreCase("null")){
                category8 = "";
            }
            if(category9.equalsIgnoreCase("null")){
                category9 = "";
            }
            if(category10.equalsIgnoreCase("null")){
                category10 = "";
            }
            vendorprofile.setCategory1(category1);
            vendorprofile.setCategory2(category2);
            vendorprofile.setCategory3(category3);
            vendorprofile.setCategory4(category4);
            vendorprofile.setCategory5(category5);
            vendorprofile.setCategory6(category6);
            vendorprofile.setCategory7(category7);
            vendorprofile.setCategory8(category8);
            vendorprofile.setCategory9(category9);
            vendorprofile.setCategory10(category10);

        }
        return vendorprofile;

    }
    public class showcategory {

        public String GetData(String url, String email) {
            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                RequestBody formBody = new FormBody.Builder()

                        .add("email", email)
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

