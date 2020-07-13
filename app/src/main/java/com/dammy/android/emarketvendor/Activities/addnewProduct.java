package com.dammy.android.emarketvendor.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.dammy.android.emarketvendor.MainActivity;
import com.dammy.android.emarketvendor.Models.vendorprofile;
import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.openingPageActivity;
import com.dammy.android.emarketvendor.splashscreen;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class addnewProduct extends AppCompatActivity {
    private static String URL = "http://jl-market.com/vendor/addproduct.php";
    TextView productNametitle, pricetitle, reducedpricetitle, optional1, optional2, addnewproducttitle,
            categorytitle, categoryvalue, descriptiontitle, required, specialdealtitle;
    EditText productNamevalue, productpricevalue, product, descriptionvalue, reducedpricevalue, specialdealpriceperunit;
    Button uploadimage1, uploadimage2, uploadimage3, addnewproductbutton;
    String name, category, description, Image1, image2;
    String price, reducedprice;
    SimpleDraweeView productimage1, productimage2;
    Toolbar toolbar;
    int flag = 0;
    Bitmap bitmap1, bitmap2, bitmap3;
    String email;
    String updateFlag, updatename, updateprice, updatedescription, newprice, updateproductid;
    Dialog loadingdialog;
    ArrayList<vendorprofile> vendorprofiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_addnew_product);
        if(savedInstanceState != null){

            if(savedInstanceState.getInt("pid",-1) == android.os.Process.myPid()){

            }
            else{

                startActivity(new Intent(addnewProduct.this,splashscreen.class));

            }
        }
        email = getIntent().getStringExtra("email");
        InitializeView();
        setOnclickListener();
        processComingIntent();

    }

    private void processComingIntent() {
        email = getIntent().getStringExtra("email");
        updateFlag = getIntent().getStringExtra("editproduct");
        if (updateFlag != null) {

            addnewproductbutton.setText("Edit Product");

            updatename = getIntent().getStringExtra("productname");
            updateprice = getIntent().getStringExtra("oldprice");
            updatedescription = getIntent().getStringExtra("description");
            updateproductid = getIntent().getStringExtra("id");


            if (getIntent().getStringExtra("newprice") != null) {
                newprice = getIntent().getStringExtra("newprice");
                reducedpricevalue.setText(newprice);
            }

            productNamevalue.setText(updatename);
            descriptionvalue.setText(updatedescription);
            productpricevalue.setText(updateprice);
        }

        vendorprofiles = getIntent().getParcelableArrayListExtra("vendorprofile");

    }

    private void InitializeView() {

        toolbar = (Toolbar) findViewById(R.id.addnewproducttoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);
        addnewproducttitle = (TextView) findViewById(R.id.addnewproducttitle);
        productNametitle = (TextView) findViewById(R.id.newproductnametitle);
        categorytitle = (TextView) findViewById(R.id.newproductcategorytitle);
        categoryvalue = (TextView) findViewById(R.id.newproductcategoryvalue);
        descriptiontitle = (TextView) findViewById(R.id.newproductdescriptiontitle);
        descriptionvalue = (EditText) findViewById(R.id.newproductdescriptionvalue);


        productNamevalue = (EditText) findViewById(R.id.newproductnamevalue);
        addnewproductbutton = (Button) findViewById(R.id.addnewproductbutton);
        pricetitle = (TextView) findViewById(R.id.newproductpricetitle);
        required = (TextView) findViewById(R.id.newproductrequired);
        productpricevalue = (EditText) findViewById(R.id.newproductpricevalue);
        reducedpricetitle = (TextView) findViewById(R.id.newproductreducedpricetitle);
        reducedpricevalue = (EditText) findViewById(R.id.newproductreducedpricevalue);
        optional1 = (TextView) findViewById(R.id.newproductaddoptionalfirst);

        uploadimage1 = (Button) findViewById(R.id.addnewproductuploadimagefirst);
        uploadimage2 = (Button) findViewById(R.id.addnewproductuploadimagesecond);

        productimage1 = (SimpleDraweeView) findViewById(R.id.addnewproductimagefirst);
        productimage2 = (SimpleDraweeView) findViewById(R.id.addnewproductimagesecond);

        Typeface customfont = Typeface.createFromAsset(getAssets(), "Kylo-Light.otf");
        Typeface customfont2 = Typeface.createFromAsset(getAssets(), "Kylo-Light.otf");
        addnewproducttitle.setTypeface(customfont);
        productNamevalue.setTypeface(customfont2);
        addnewproductbutton.setTypeface(customfont2);
        required.setTypeface(customfont2);
        addnewproductbutton.setTypeface(customfont2);
        productNametitle.setTypeface(customfont2);
        pricetitle.setTypeface(customfont2);
        productpricevalue.setTypeface(customfont2);
        reducedpricetitle.setTypeface(customfont2);
        reducedpricevalue.setTypeface(customfont2);
        optional1.setTypeface(customfont2);

        uploadimage1.setTypeface(customfont2);
        uploadimage2.setTypeface(customfont2);


        categoryvalue.setTypeface(customfont2);
        categorytitle.setTypeface(customfont2);

        descriptiontitle.setTypeface(customfont2);
        descriptionvalue.setTypeface(customfont2);




    }

    private void setOnclickListener() {

        uploadimage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(200, 250)
                        .setFixAspectRatio(true)
                        .setAutoZoomEnabled(false)
                        .setActivityTitle("Crop Image")
                        .start(addnewProduct.this);
            }

        });


        uploadimage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 2;
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(100, 150)
                        .setFixAspectRatio(true)
                        .setAutoZoomEnabled(false)
                        .setActivityTitle("Crop Image")
                        .start(addnewProduct.this);

            }
        });


        addnewproductbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addnewproductbutton.getText().toString().equalsIgnoreCase("Edit Product")) {
                    name = productNamevalue.getText().toString();
                    category = categoryvalue.getText().toString();
                    description = descriptionvalue.getText().toString();
                    price = productpricevalue.getText().toString();
                    if(!TextUtils.isEmpty(reducedpricevalue.getText().toString())) {
                        reducedprice = reducedpricevalue.getText().toString();
                    }
                    else {
                        reducedprice = "0";
                    }
                    if (!validateForm()) {


                    } else {
                        updateproducttask updateproducttask = new updateproducttask();
                        updateproducttask.execute();
                    }
                }
                else {

                    name = productNamevalue.getText().toString();
                    category = categoryvalue.getText().toString();
                    description = descriptionvalue.getText().toString();
                    price = productpricevalue.getText().toString();

                    if(!TextUtils.isEmpty(reducedpricevalue.getText().toString())) {
                        reducedprice = reducedpricevalue.getText().toString();
                    }
                    else {
                        reducedprice = "0";
                    }
                    if (!validateForm()) {


                    } else {
                        addproductTask addproductTask = new addproductTask();
                        addproductTask.execute();
                    }
                }
            }
        });

        categoryvalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addnewProduct.this, productselectcategory.class);
                intent.putExtra("email", email);
                startActivityForResult(intent, 50);
            }
        });

        descriptionvalue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(descriptionvalue.hasFocus()){
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Uri imageuri = result.getUri();
            if (flag == 1) {

                productimage1.setVisibility(View.VISIBLE);
                productimage1.setImageURI(imageuri);
                uploadimage2.setEnabled(true);
                getImage1FromDrawee(imageuri);

            } else if (flag == 2) {
                productimage2.setVisibility(View.VISIBLE);
                productimage2.setImageURI(imageuri);

                getImage2FromDrawee(imageuri);
            }


        }

        if (requestCode == 50) {

            if(resultCode == 0){

            }else {
                String category = data.getStringExtra("category");
                categoryvalue.setText(category);

            }
        }


    }


    private void getImage1FromDrawee(Uri imageUri) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(imageUri)
                .setRequestPriority(Priority.HIGH)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .build();

        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, addnewProduct.this);

        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl( Bitmap bitmap) {
                if (dataSource.isFinished() && bitmap != null) {
                    Log.e("here", "onNewResultImpl: ");
                    bitmap1 = Bitmap.createBitmap(bitmap);
                    dataSource.close();
                }

            }


            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                Log.e("close", "onFailureImpl: ");
                dataSource.close();
            }
        }, CallerThreadExecutor.getInstance());
    }

    private void getImage2FromDrawee(Uri imageUri) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(imageUri)
                .setRequestPriority(Priority.HIGH)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .build();

        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, addnewProduct.this);

        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(Bitmap bitmap) {
                if (dataSource.isFinished() && bitmap != null) {
                    Log.e("here", "onNewResultImpl: ");
                    bitmap2 = Bitmap.createBitmap(bitmap);
                    dataSource.close();
                }

            }


            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                Log.e("close", "onFailureImpl: ");
                dataSource.close();
            }
        }, CallerThreadExecutor.getInstance());
    }


    public class addproductTask extends AsyncTask {
        String prompt;

        @Override
        protected void onPreExecute() {

            showloadingdialog();
            //Toast.makeText(addnewProduct.this, "ADDING PRODUCT", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;

            }
            if (bitmap1 != null && bitmap2 == null) {

                Image1 = getStringImage(bitmap1);


                String serverresponse = new addproduct().GetData(URL, email, name, category, description, Image1, price, reducedprice);
                if (serverresponse != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(serverresponse);
                        JSONObject info = jsonObject.getJSONObject("info");
                        String status = info.getString("status");

                        if (status.equalsIgnoreCase("productaddedsuccessfully")) {
                            prompt = "productaddedsuccessfully";
                            return prompt;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }



            } else if (bitmap1 != null && bitmap2 != null) {


                Image1 = getStringImage(bitmap1);
                image2 = getStringImage(bitmap2);


                String serverresponse = new addproduct().GetData(URL, email, name, category, description, Image1, image2, price, reducedprice);
                if (serverresponse != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(serverresponse);
                        JSONObject info = jsonObject.getJSONObject("info");
                        String status = info.getString("status");

                        if (status.equalsIgnoreCase("productaddedsuccessfully")) {
                            prompt = "productaddedsuccessfully";
                            return prompt;
                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }

            }

            return null;
        }

        protected void onPostExecute(Object result) {

            loadingdialog.dismiss();
            //Toast.makeText(addnewProduct.this, result.toString(), Toast.LENGTH_SHORT).show();
            if (result != null) {

                if (result.toString().equalsIgnoreCase("No Network Connection")) {
                    Toast.makeText(addnewProduct.this, "No Network Connection", Toast.LENGTH_LONG).show();
                }


                if (result.toString().equalsIgnoreCase("productaddedsuccessfully")) {
                    Toast.makeText(addnewProduct.this, "Product Added Successfully", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(addnewProduct.this, MainActivity.class);
                    intent.putParcelableArrayListExtra("vendorprofile", vendorprofiles);
                    startActivity(intent);
                } else {
                    Toast.makeText(addnewProduct.this,"Error Occured", Toast.LENGTH_LONG).show();
                }
            }

            else{
                Toast.makeText(addnewProduct.this, "Error Occured", Toast.LENGTH_LONG).show();
            }

        }
    }

    private Boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showloadingdialog() {
        loadingdialog = new Dialog(addnewProduct.this, android.R.style.Theme_Light);
        loadingdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingdialog.setContentView(R.layout.loadingdialog);
        ImageView image = loadingdialog.findViewById(R.id.loadingimage);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(image);
        Glide.with(addnewProduct.this).load(R.drawable.loading).into(imageViewTarget);
        loadingdialog.setCancelable(false);

        loadingdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        loadingdialog.show();
    }

    public class addproduct {


        public String GetData(String url, String emailaddress, String name, String category, String description, String image1,
                              String productprice, String reducedprice) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                RequestBody formBody = new FormBody.Builder()
                        .add("productname", name)
                        .add("productprice", productprice)
                        .add("reducedprice", reducedprice)
                        .add("category", category)
                        .add("description", description)

                        .add("emailaddress", emailaddress)
                        .add("firstimage", image1)
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

        public String GetData(String url, String emailaddress, String name, String category, String description, String image1, String image2,
                              String productprice, String reducedprice) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                RequestBody formBody = new FormBody.Builder()
                        .add("productname", name)
                        .add("productprice", productprice)
                        .add("reducedprice", reducedprice)
                        .add("category", category)
                        .add("description", description)

                        .add("emailaddress", emailaddress)
                        .add("firstimage", image1)
                        .add("secondimage", image2)
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

    public static String getStringImage(Bitmap bitmap){
        final int MAX_IMAGE_SIZE = 500 * 1024; // max final file size in kilobytes
        byte[] bmpPicByteArray;

        //Bitmap scBitmap  = Bitmap.createScaledBitmap(bitmap, 200, 250, false);


        int compressQuality = 100; // quality decreasing by 5 every loop.
        int streamLength;
        do{
            ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
            Log.d("compressBitmap", "Quality: " + compressQuality);
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream);
            bmpPicByteArray = bmpStream.toByteArray();
            streamLength = bmpPicByteArray.length;
            compressQuality -= 5;
            Log.d("compressBitmap", "Size: " + streamLength/1024+" kb");
        }while (streamLength >= MAX_IMAGE_SIZE);

        String encodedImage = Base64.encodeToString(bmpPicByteArray, Base64.DEFAULT);
        return encodedImage;

    }

    public class updateproducttask extends AsyncTask {
        private String updateURL = "http://jl-market.com/vendor/updateproduct.php";

        String prompt;

        @Override
        protected void onPreExecute() {

            showloadingdialog();
            //Toast.makeText(addnewProduct.this, "updating PRODUCT", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;

            }
            if (bitmap1 != null && bitmap2 == null) {
                Image1 = getStringImage(bitmap1);


                String serverresponse = new updateproduct().GetData(updateURL, name, category, description, Image1, price, reducedprice,updateproductid);
                if (serverresponse != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(serverresponse);
                        JSONObject info = jsonObject.getJSONObject("info");
                        String status = info.getString("status");

                        if (status.equalsIgnoreCase("updatesuccessfull")) {
                            prompt = "updatesuccessfull";
                            return prompt;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }

            }
            else if (bitmap1 != null && bitmap2 != null ) {
                Image1 = getStringImage(bitmap1);
                image2 = getStringImage(bitmap2);

                String serverresponse = new updateproduct().GetData(updateURL, name, category, description, Image1, image2, price, reducedprice,updateproductid);
                if (serverresponse != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(serverresponse);
                        JSONObject info = jsonObject.getJSONObject("info");
                        String status = info.getString("status");

                        if (status.equalsIgnoreCase("updatesuccessfull")) {
                            prompt = "updatesuccessfull";
                            return prompt;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }

            }
            return null;
        }
        protected void onPostExecute(Object result) {


            loadingdialog.dismiss();
            if (result != null) {

                if (result.toString().equalsIgnoreCase("No Network Connection")) {
                    Toast.makeText(addnewProduct.this, "No Network Connection", Toast.LENGTH_LONG).show();
                }

                else if (result.toString().equalsIgnoreCase("updatesuccessfull")) {
                    Toast.makeText(addnewProduct.this, "Product updated Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(addnewProduct.this, MainActivity.class);
                    intent.putParcelableArrayListExtra("vendorprofile", vendorprofiles);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(addnewProduct.this, "Error Occured", Toast.LENGTH_LONG).show();
                }

                //Toast.makeText(addnewProduct.this, result.toString(), Toast.LENGTH_LONG).show();
                // }
            }
            else{
                Toast.makeText(addnewProduct.this, "Error Occured", Toast.LENGTH_LONG).show();
            }

        }

        }



    public class updateproduct {


        public String GetData(String url, String name, String category, String description, String image1,
                              String productprice, String reducedprice,String productid) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                RequestBody formBody = new FormBody.Builder()
                        .add("productname", name)
                        .add("productprice", productprice)
                        .add("reducedprice", reducedprice)
                        .add("category", category)
                        .add("description", description)
                        .add("productid",productid)
                        .add("firstimage", image1)
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

        public String GetData(String url, String name, String category, String description, String image1, String image2,
                              String productprice, String reducedprice,String productid) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                RequestBody formBody = new FormBody.Builder()
                        .add("productname", name)
                        .add("productprice", productprice)
                        .add("reducedprice", reducedprice)
                        .add("category", category)
                        .add("description", description)
                        .add("productid",productid)
                        .add("firstimage", image1)
                        .add("secondimage", image2)
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

    private boolean validateForm(){
        boolean valid = true;

        if(TextUtils.isEmpty(name)){
            valid = false;
            productNamevalue.setError("Required");
        }
        else{
            productNamevalue.setError(null);
        }
        if(TextUtils.isEmpty(category)){
            valid = false;
            categoryvalue.setError("Required");
        }
        else{
            categoryvalue.setError(null);
        }
        if(TextUtils.isEmpty(description)){
            valid = false;
            descriptionvalue.setError("Required");
        }
        else{
            descriptionvalue.setError(null);
        }
        if(TextUtils.isEmpty(price)){
            valid = false;
            productpricevalue.setError("Required");
        }
        else{
            productpricevalue.setError(null);
        }

        if(bitmap1 == null){
            Toast.makeText(this, "Please Upload Product Image", Toast.LENGTH_LONG).show();
            valid = false;
        }
        if(!TextUtils.isEmpty(price) && !TextUtils.isEmpty(reducedprice)) {
            if (Integer.parseInt(reducedprice) >= Integer.parseInt(price)) {
                Toast.makeText(this, "Discounted price cannot be greater than real price", Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }

        return valid;
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



