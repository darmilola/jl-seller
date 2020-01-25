package com.dammy.android.emarketvendor.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.Adapter.productcategoryadapter;
import com.dammy.android.emarketvendor.Models.CategoryModel;
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


public class productselectcategory extends AppCompatActivity {
  RecyclerView recyclerView;
  LinearLayout progressbarlayout,nonetworklayout,errorlayout;
  Button nonetworkbutton,errorbuton;
  TextView nonetworktext,errortext;
  TextView emptycategorytext,title;
  ArrayList<CategoryModel> categoryModelArrayList;
  String vendoremail;
  Toolbar toolbar;
  private static String Vendor_GET_CATEGORY_URL = "http://jl-market.com/vendor/SelectProductCategories.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){

            if(savedInstanceState.getInt("pid",-1) == android.os.Process.myPid()){

            }
            else{

                startActivity(new Intent(this,splashscreen.class));

            }
        }
        setContentView(R.layout.activity_productselectcategory);
        vendoremail = getIntent().getStringExtra("email");
        Initializeview();
        getProductCategoryTask productCategoryTask = new getProductCategoryTask();
        productCategoryTask.execute();

    nonetworkbutton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new getProductCategoryTask().execute();
        }
    });

    errorbuton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new getProductCategoryTask().execute();
        }
    });
    }

    private void Initializeview() {

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = 0.5f;
        getWindow().setAttributes(layoutParams);
        recyclerView = findViewById(R.id.categorydisplayrecyclerview);
        emptycategorytext = findViewById(R.id.emptycategorytext);
        progressbarlayout = findViewById(R.id.categoryprogressbarlayout);
        nonetworklayout = findViewById(R.id.nonetworklayout);
        errorlayout = findViewById(R.id.errorlayout);
        nonetworkbutton = findViewById(R.id.nonetworkbutton);
        errorbuton = findViewById(R.id.errorbutton);
        nonetworktext = findViewById(R.id.nonetworktext);
        errortext = findViewById(R.id.errortext);
        title = findViewById(R.id.selectcategorytitle);
        toolbar = findViewById(R.id.selectcategorytoolbar);
        Typeface customfont2= Typeface.createFromAsset(getAssets(),"Kylo-Light.otf");
        emptycategorytext.setTypeface(customfont2);
        title.setTypeface(customfont2);
        errortext.setTypeface(customfont2);
        nonetworktext.setTypeface(customfont2);
        nonetworkbutton.setTypeface(customfont2);
        errorbuton.setTypeface(customfont2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
       nonetworklayout.setVisibility(View.GONE);
       errorlayout.setVisibility(View.GONE);
       emptycategorytext.setVisibility(View.GONE);
       progressbarlayout.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);

    }





    public class getProductCategoryTask extends AsyncTask {


        String prompt;

        @Override
        protected void onPreExecute() {

            progressbarlayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            emptycategorytext.setVisibility(View.GONE);
            nonetworklayout.setVisibility(View.GONE);
            errorlayout.setVisibility(View.GONE);

        }

        @Override
        protected Object doInBackground(Object[] objects) {

            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }

            String serverResponse = new getCategory().GetData(Vendor_GET_CATEGORY_URL, vendoremail);
            // return serverResponse;

            if (serverResponse != null) {
                try {
                    JSONObject jsonObject = new JSONObject(serverResponse);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");

                    if(status.equalsIgnoreCase("productcategoriesunavailable")){
                        prompt = "You have not created any product categories";
                        return prompt;
                    }

                    else if(status.equalsIgnoreCase("productcategoriesavailable")){
                        categoryModelArrayList = passCategoryJson(info);
                        return categoryModelArrayList;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        protected void onPostExecute(Object result) {
            if(result != null) {
                if (result.toString().equalsIgnoreCase("You have not created any product categories")) {
                    progressbarlayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    emptycategorytext.setVisibility(View.VISIBLE);
                    nonetworklayout.setVisibility(View.GONE);
                    errorlayout.setVisibility(View.GONE);

                    }
                    if(result.toString().equalsIgnoreCase("No Network Connection")){

                        progressbarlayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        emptycategorytext.setVisibility(View.GONE);
                        nonetworklayout.setVisibility(View.VISIBLE);
                        errorlayout.setVisibility(View.GONE);
                    }


                else if(result instanceof ArrayList) {
                        progressbarlayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        emptycategorytext.setVisibility(View.GONE);
                        nonetworklayout.setVisibility(View.GONE);
                        errorlayout.setVisibility(View.GONE);
                        ArrayList<CategoryModel> categoryModels = (ArrayList<CategoryModel>) result;
                        if (categoryModels.size() < 1) {
                            progressbarlayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.GONE);
                            emptycategorytext.setVisibility(View.VISIBLE);
                            nonetworklayout.setVisibility(View.GONE);
                            errorlayout.setVisibility(View.GONE);
                        } else {
                            setUpAdapter((ArrayList<CategoryModel>) result);
                        }
                    }
                else{
                        progressbarlayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        emptycategorytext.setVisibility(View.GONE);
                        nonetworklayout.setVisibility(View.GONE);
                        errorlayout.setVisibility(View.VISIBLE);

                }
            }



        }
    }


    private void setUpAdapter(ArrayList<CategoryModel> result) {
        productcategoryadapter adapter = new productcategoryadapter(result, new productcategoryadapter.ListItemClickListener() {
            @Override
            public void onListItemClick(int clickedItemIndex) {
                String category = result.get(clickedItemIndex).getCategoryName();
                Intent intent = new Intent();
                //Toast.makeText(productselectcategory.this, "hereeee", Toast.LENGTH_SHORT).show();
                intent.putExtra("category",category);
                setResult(1,intent);
                finish();
            }
        });
        recyclerView.setAdapter(adapter);

    }

    public ArrayList<CategoryModel> passCategoryJson(JSONObject jsonObject) throws JSONException {
        ArrayList<CategoryModel> categotrylist = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject j = jsonArray.getJSONObject(i);
            String cat1 = j.getString("productcategory1");
            String cat2 = j.getString("productcategory2");
            String cat3 = j.getString("productcategory3");
            String cat4 = j.getString("productcategory4");
            String cat5 = j.getString("productcategory5");
            String cat6 = j.getString("productcategory6");
            String cat7 = j.getString("productcategory7");
            String cat8 = j.getString("productcategory8");
            String cat9 = j.getString("productcategory9");
            String cat10 = j.getString("productcategory10");

            CategoryModel categoryModel1 = new CategoryModel(cat1);
            CategoryModel categoryModel2 = new CategoryModel(cat2);
            CategoryModel categoryModel3 = new CategoryModel(cat3);
            CategoryModel categoryModel4 = new CategoryModel(cat4);
            CategoryModel categoryModel5 = new CategoryModel(cat5);
            CategoryModel categoryModel6 = new CategoryModel(cat6);
            CategoryModel categoryModel7 = new CategoryModel(cat7);
            CategoryModel categoryModel8 = new CategoryModel(cat8);
            CategoryModel categoryModel9 = new CategoryModel(cat9);
            CategoryModel categoryModel10 = new CategoryModel(cat10);

            if(cat1.equalsIgnoreCase("") || cat1.equalsIgnoreCase("null")){

            }
            else {
                categotrylist.add(categoryModel1);
            }
            if(cat2.equalsIgnoreCase("") || cat2.equalsIgnoreCase("null")){

            }
            else {
                categotrylist.add(categoryModel2);
            }if(cat3.equalsIgnoreCase("") || cat3.equalsIgnoreCase("null")){

            }
            else {
                categotrylist.add(categoryModel3);
            }if(cat4.equalsIgnoreCase("") || cat4.equalsIgnoreCase("null")){

            }
            else {
                categotrylist.add(categoryModel4);
            }if(cat5.equalsIgnoreCase("") || cat5.equalsIgnoreCase("null")){

            }
            else {
                categotrylist.add(categoryModel5);
            }if(cat6.equalsIgnoreCase("") || cat6.equalsIgnoreCase("null")){

            }
            else {
                categotrylist.add(categoryModel6);
            }if(cat7.equalsIgnoreCase("") || cat7.equalsIgnoreCase("null")){

            }
            else {
                categotrylist.add(categoryModel7);
            }if(cat8.equalsIgnoreCase("") || cat8.equalsIgnoreCase("null")){

            }
            else {
                categotrylist.add(categoryModel8);
            }if(cat9.equalsIgnoreCase("") || cat9.equalsIgnoreCase("null")){

            }
            else {
                categotrylist.add(categoryModel9);
            }if(cat10.equalsIgnoreCase("") || cat10.equalsIgnoreCase("null")){

            }
            else {
                categotrylist.add(categoryModel10);
            }
        }
        return categotrylist;


    }

    public class getCategory {

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
    private Boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

@Override
    public void onBackPressed(){
        setResult(0);
        finish();
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
