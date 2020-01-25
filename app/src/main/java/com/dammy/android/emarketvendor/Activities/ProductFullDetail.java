package com.dammy.android.emarketvendor.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.dammy.android.emarketvendor.Adapter.Fulldetailadapter;
import com.dammy.android.emarketvendor.Fragments.vendorshopProduct;
import com.dammy.android.emarketvendor.MainActivity;
import com.dammy.android.emarketvendor.Models.fullproductdetailimagemodel;
import com.dammy.android.emarketvendor.Models.vendorprofile;
import com.dammy.android.emarketvendor.R;
//import com.dammy.android.emarketvendor.Fragments.productfulldetailfragment;
import com.dammy.android.emarketvendor.Models.ProductModel;
import com.dammy.android.emarketvendor.openingPageActivity;
import com.dammy.android.emarketvendor.splashscreen;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

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

public class ProductFullDetail extends AppCompatActivity {


    TextView Title;
    View view;
    String fullimage2;
    String fullimage3;
    Typeface customfont;
    SimpleDraweeView indicatorimage1, indicatorimage2;
    TextView description,descriptionTitle,price,pricetitle, DeprecatedPrice,percentdecrease,productname;
    fullproductdetailimagemodel image2;
    fullproductdetailimagemodel image3;
    RecyclerView productimageRecycler;
    ArrayList<fullproductdetailimagemodel> list;
    static ArrayList<ProductModel> productdetail;
    Fulldetailadapter fulldetailadapter;
    Drawable smallimageback1,smallimageback2;
    boolean flag3;
    Runnable runnable = null;
    Handler handler = new Handler();
    Toolbar toolbar;
    ImageView deleteproduct;
    Dialog loadingdialog;
    ArrayList<vendorprofile> vendorprofiles;
    String userid ;
    String myproductname;
    String productprice ;
    String firstimage;
    String secondimage;
    String reducedprice;
    String mydescription;
    Button available_toggle;
    TextView available_text;
    String availability_state;
    private static String product_toggle_url = "http://jl-market.com/vendor/toggleproduct.php";


    Typeface customfont2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        if(savedInstanceState != null){

            if(savedInstanceState.getInt("pid",-1) == android.os.Process.myPid()){

            }
            else{

                startActivity(new Intent(this,splashscreen.class));
                return;

            }
        }
        setContentView(R.layout.activity_product_full_detail);
        productdetail = getIntent().getParcelableArrayListExtra("productlist");
        InitializeView();
        displaydescription();
        setOnclickListener();
        InitializePrice();
        InitializeProductFullImage();
        ProductsimageRecyclerInit();
        InitializeAdapter();
        startAutoscroll();
        vendorprofiles = getIntent().getParcelableArrayListExtra("vendorprofile");






    }

    private void InitializeView(){

        description = findViewById(R.id.fulldetailproductdescription);
        descriptionTitle = findViewById(R.id.fulldetailproductdescriptiontitle);
        price = findViewById(R.id.fulldetailproductprice);
        indicatorimage1 = findViewById(R.id.fulldetailproductsmallimagefirst);
        indicatorimage2 = findViewById(R.id.fulldetailproductsmallimagesecond);
        pricetitle = findViewById(R.id.fulldetailproductpricetitle);
        DeprecatedPrice = findViewById(R.id.fulldetaildeprecatedprice);
        percentdecrease = findViewById(R.id.fulldetailpercentdecrease);
        productname = findViewById(R.id.productfulldetailname);
        toolbar = findViewById(R.id.productdetailtoolbar);
        deleteproduct = findViewById(R.id.deleteproducticon);
        available_toggle = findViewById(R.id.availability_toggle);
        available_text = findViewById(R.id.availability_text);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);

        customfont= Typeface.createFromAsset(getAssets(),"Kylo-Light.otf");



        Title = findViewById(R.id.productfulldetailtoolbartitle);

        customfont= Typeface.createFromAsset(getAssets(),"Kylo-Light.otf");
        Typeface customfont2= Typeface.createFromAsset(getAssets(),"Kylo-Regular.otf");

        pricetitle.setTypeface(customfont2);
        Title.setTypeface(customfont);
        available_text.setTypeface(customfont2);
        available_toggle.setTypeface(customfont2);
        //available_toggle.setChecked(true);

        if(productdetail.get(0).getAvailability().equalsIgnoreCase("yes")){
            available_toggle.setText("yes");
        }
        else{
            available_toggle.setText("no");
        }

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = 0.5f;
        getWindow().setAttributes(layoutParams);
        descriptionTitle.setTypeface(customfont2);
        DeprecatedPrice.setTypeface(customfont2);
        productname.setTypeface(customfont2);
        productname.setText(productdetail.get(0).getProductName());
        price.setTypeface(customfont2);
        percentdecrease.setTypeface(customfont2);
        description.setTypeface(customfont2);

        Title.setText(productdetail.get(0).getProductName());
    }

    private void displaydescription(){
      String mdescription = productdetail.get(0).getProductDescription();
      description.setText(mdescription);
    }
    private void InitializePrice(){
        Locale NigerianLocale = new Locale("en","ng");
        if(productdetail.get(0).getProductnewPrice() == null){
            Log.e("redude", "InitializePrice: null" );
            DeprecatedPrice.setVisibility(View.GONE);
            percentdecrease.setVisibility(View.GONE);
            String productprice = productdetail.get(0).getoldProductPrice();
            String unFormattedrealPrice = NumberFormat.getCurrencyInstance(NigerianLocale).format(Integer.parseInt(productprice));
            String formattedrealPrice = unFormattedrealPrice.replaceAll("\\.00","");
            price.setTextColor(getResources().getColor(R.color.colorPrimary));
            price.setText(formattedrealPrice);
        }

        else{
            DeprecatedPrice.setVisibility(View.VISIBLE);
            percentdecrease.setVisibility(View.VISIBLE);
            String reducedproductprice = productdetail.get(0).getProductnewPrice();
            String unFormattedreducedPrice = NumberFormat.getCurrencyInstance(NigerianLocale).format(Integer.parseInt(reducedproductprice));
            String formattedrealreducedPrice = unFormattedreducedPrice.replaceAll("\\.00","");
            String productprice = productdetail.get(0).getoldProductPrice();
            String unFormattedrealPrice = NumberFormat.getCurrencyInstance(NigerianLocale).format(Integer.parseInt(productprice));
            String formattedrealPrice = unFormattedrealPrice.replaceAll("\\.00","");
            DeprecatedPrice.setTextColor(getResources().getColor(R.color.grey));
            DeprecatedPrice.setText(formattedrealPrice);
            price.setTextColor(getResources().getColor(R.color.colorPrimary));
            price.setText(formattedrealreducedPrice);
            DeprecatedPrice.setPaintFlags(price.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            String percentDecrease = productdetail.get(0).getPercentDecrease();
            percentdecrease.setText("-"+percentDecrease+"%");



        }
    }
    private void setOnclickListener(){
        deleteproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(ProductFullDetail.this);

                builder.setMessage("Are you sure you want to delete this product")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                deleteproductTask deleteproductTask = new deleteproductTask();
                                deleteproductTask.execute();

                            }
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();



            }
        });

        available_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                availability_state = available_toggle.getText().toString();
                if(availability_state.equalsIgnoreCase("yes")){
                    availability_state = "no";
                    new ToggleProductTask().execute();
                }
                else{
                    availability_state = "yes";
                    new ToggleProductTask().execute();

                }
            }
        });
    }
    private void InitializeProductFullImage(){

            smallimageback1 = ContextCompat.getDrawable(getApplicationContext(),R.drawable.productsmallimagebackgrounprimary);
            indicatorimage1.setBackground(smallimageback1);//tint indicator 1 by default
        String indicatoruri1 = productdetail.get(0).getProductImagefirst();

       /* RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.marketpic);
        requestOptions.error(R.drawable.marketpic);
        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(indicatoruri1)
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(indicatorimage1);*/

        Uri imageuri = Uri.parse(indicatoruri1);
        ImageRequest request = ImageRequest.fromUri(imageuri);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(indicatorimage1.getController()).build();
        indicatorimage1.setController(controller);



        list = new ArrayList<>();
        String fullimage1 = productdetail.get(0).getProductImagefirst();
        fullproductdetailimagemodel image1 = new fullproductdetailimagemodel(fullimage1);
        list.add(image1);
        if(!(productdetail.get(0).getProductImageSecond() == null)){//check if there is second image

            fullimage2 = productdetail.get(0).getProductImageSecond();
            image2 = new fullproductdetailimagemodel(fullimage2);
            list.add(image2);
            String indicatoruri2 = productdetail.get(0).getProductImageSecond();

           /* RequestOptions requestOptions2 = new RequestOptions();
            requestOptions2.placeholder(R.drawable.marketpic);
            requestOptions2.error(R.drawable.marketpic);
            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(indicatoruri2)
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(indicatorimage2);*/

            Uri imageuri2 = Uri.parse(indicatoruri2);
            ImageRequest request2 = ImageRequest.fromUri(imageuri2);
            DraweeController controller2 = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request2)
                    .setOldController(indicatorimage2.getController()).build();
            indicatorimage2.setController(controller2);



        }
        else{
            indicatorimage2.setVisibility(View.GONE);//else if there is no second image indicator  2  is gone

        }

    }
    private void InitializeAdapter(){
        fulldetailadapter = new Fulldetailadapter(list);
        productimageRecycler.setAdapter(fulldetailadapter);
    }
    private void ProductsimageRecyclerInit() {
        productimageRecycler = (RecyclerView)findViewById(R.id.productfulldetailrecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        linearLayoutManager.setAutoMeasureEnabled(true);
        productimageRecycler.setLayoutManager(linearLayoutManager);

        //AllproductsRecycler.setItemAnimator(new DefaultItemAnimator());

    }

    private void startAutoscroll() {
        final int[] scrollspeed = {3000};

        final int[] count = {0};
        final boolean[] flag = {true};
        runnable = new Runnable() {
            @Override
            public void run() {

                if (fulldetailadapter.getItemCount() == 1) {//there is only one image dont autoscroll
                    return;
                }

                if (count[0] < fulldetailadapter.getItemCount()) {//count is less than images the autoscroll to them


                    if (count[0] == fulldetailadapter.getItemCount() - 1) {
                        flag[0] = false;//count is at maximum decrease count
                    } else if (count[0] == 0) {
                        flag[0] = true; //count is at minimum increase count
                    }
                    if (flag[0]) {
                        count[0]++;//if flag is true increase count


                    } else {
                        count[0]--; //else decrease count

                    }
                    if (count[0] == 0) {//meaning if count is at minimum

                            //get the drawable for coloring dispayed view
                            smallimageback1 =ContextCompat.getDrawable(getApplicationContext(),R.drawable.productsmallimagebackgrounprimary);

                            smallimageback2 = ContextCompat.getDrawable(getApplicationContext(),R.drawable.productsmallimagebackgroundblack);


                        if (indicatorimage2.getVisibility() == View.GONE) {//if there is no indicator 2 color only indicator 1
                            indicatorimage1.setBackground(smallimageback1);
                        } else {
                            indicatorimage2.setBackground(smallimageback2);//color image 2 black
                            indicatorimage1.setBackground(smallimageback1);//color image 1
                        }

                    }
                    flag3 = false;
                    scrollspeed[0] = 3000;


                }
                if (count[0] == 1) {



                        smallimageback1 = ContextCompat.getDrawable(getApplicationContext(),R.drawable.productsmallimagebackgrounprimary);

                        smallimageback2 = ContextCompat.getDrawable(getApplicationContext(),R.drawable.productsmallimagebackgroundblack);


                        if (indicatorimage2.getVisibility() == View.GONE) {//count is 1, if indicator 2 is gone definitely 3 will also
                            indicatorimage1.setBackground(smallimageback1);
                        } else {
                            indicatorimage2.setBackground(smallimageback1);//else color image 2
                            indicatorimage1.setBackground(smallimageback2);
                        }

                    flag3 = false;
                    scrollspeed[0] = 3000;

                }


                if (count[0] == 2) {

                        smallimageback1 = ContextCompat.getDrawable(getApplicationContext(),R.drawable.productsmallimagebackgrounprimary);

                        smallimageback2 = ContextCompat.getDrawable(getApplicationContext(),R.drawable.productsmallimagebackgroundblack);


                        if (indicatorimage2.getVisibility() == View.GONE) {
                            indicatorimage1.setBackground(smallimageback1);
                        } else {
                            indicatorimage2.setBackground(smallimageback2);
                            indicatorimage1.setBackground(smallimageback2);
                        }

                    flag3 = false;
                    scrollspeed[0] = 1500;


                }
                if (count[0] == 3) {//bug cuasing it to move up once thats why time is reduced to half
                    flag3 = true;//flag determine it has reach peak


                        smallimageback1 = ContextCompat.getDrawable(getApplicationContext(),R.drawable.productsmallimagebackgrounprimary);

                        smallimageback2 = ContextCompat.getDrawable(getApplicationContext(),R.drawable.productsmallimagebackgroundblack);


                        if (indicatorimage2.getVisibility() == View.GONE) {
                            indicatorimage1.setBackground(smallimageback1);
                        } else {
                            indicatorimage2.setBackground(smallimageback2);
                            indicatorimage1.setBackground(smallimageback2);
                        }

                    scrollspeed[0] = 1500;

                }
                // Toast.makeText(getContext(), Integer.toString(count[0]), Toast.LENGTH_SHORT).show();
                if (flag3) {

                } else {
                    productimageRecycler.smoothScrollToPosition(count[0]);
                }

                if (handler != null) {
                    handler.postDelayed(this, scrollspeed[0]);
                }

            }

        };

        if (handler != null) {
            handler.postDelayed(runnable, scrollspeed[0]);
        }

    }




    public class deleteproductTask extends AsyncTask {

        String url = "http://jl-market.com/vendor/deleteproduct.php";
        String prompt;

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
            String serverresponse = new getdeleteproductinfo().GetData(url,productdetail.get(0).getProductid());
            return serverresponse;

        }

        protected void onPostExecute(Object result) {
            loadingdialog.dismiss();
            if (result == null) {
                Toast.makeText(ProductFullDetail.this, "Error Occured", Toast.LENGTH_SHORT).show();
            }
            else if(result.toString().equalsIgnoreCase(prompt)){
                Toast.makeText(ProductFullDetail.this, prompt, Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(ProductFullDetail.this, "Product Deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProductFullDetail.this, MainActivity.class);
                intent.putExtra("vendorprofile",vendorprofiles);
                startActivity(intent);

            }

        }

    }
    public class getdeleteproductinfo {

        public String GetData(String url,String productid) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();


                RequestBody formBody = new FormBody.Builder()
                        .add("productid",productid)
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

    public class ToggleProductTask extends AsyncTask {


        String prompt;

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
            String serverResponse = new changeProductState().GetData(product_toggle_url,productdetail.get(0).getProductid(),availability_state);
            if (serverResponse != null) {
                try {
                    JSONObject jsonObject = new JSONObject(serverResponse);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");
                    if(status.equalsIgnoreCase("productopened")){
                        prompt = "productopened";
                        return prompt;
                    }
                    else if(status.equalsIgnoreCase("productclosed")){
                        prompt = "productclosed";
                        return prompt;
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return serverResponse;
        }

        protected void onPostExecute(Object result) {

            loadingdialog.dismiss();
            //Toast.makeText(getContext(), result.toString(), Toast.LENGTH_LONG).show();
            if (result == null) {

                Toast.makeText(ProductFullDetail.this, "Error Occured", Toast.LENGTH_LONG).show();
            }
            else if(result.toString().equalsIgnoreCase("No Network Connection")){
                Toast.makeText(ProductFullDetail.this, "No Network Connection", Toast.LENGTH_LONG).show();
            }
            else if(result.toString().equalsIgnoreCase("productopened")){
                Toast.makeText(ProductFullDetail.this, "Product has been made available, customers can now place order for this product", Toast.LENGTH_LONG).show();
                available_toggle.setText("yes");
            }
            else if(result.toString().equalsIgnoreCase("productclosed")){
                Toast.makeText(ProductFullDetail.this, "Product has been made unavailable, customers can no longer  place order for this product", Toast.LENGTH_LONG).show();
                available_toggle.setText("no");
            }


        }

    }


    public class changeProductState {

        public String GetData(String url, String productid,String producttoggle) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                RequestBody formBody = new FormBody.Builder()
                        .add("productid", productid)
                        .add("producttoggle", producttoggle)
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
