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
import android.view.View;
import android.view.ViewGroup;
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
import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.Models.vendorprofile;
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


import org.json.JSONArray;
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

public class signup extends AppCompatActivity {

    TextView signup, countrydigit;
    Button createaccount;
    EditText security_question_field, firstnamefield, lastnamefield, emailaddressfield, passwordfield, phonenumberfield, vendoridfield;
    Toolbar toolbar;
    String firstname, lastname, emailaddress, password, phonenumber, id,security_answer;
    Bitmap image, bitmap1;
    CloseableReference mref;
    SimpleDraweeView profileimage;
    Dialog loadingdialog;
    TextView getId;
    private static String Vendor_SignUp_url = "http://jl-market.com/vendor/newvendorregistration.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(signup.this);
        setContentView(R.layout.activity_signup);
        if(savedInstanceState != null){

            if(savedInstanceState.getInt("pid",-1) == android.os.Process.myPid()){

            }
            else{

                startActivity(new Intent(this,splashscreen.class));

            }
        }
        InitializeView();
        setOnclickListener();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == android.R.id.home) {

          finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }



    private void InitializeView() {
        Typeface customfont2 = Typeface.createFromAsset(getAssets(), "Kylo-Light.otf");
        signup = (TextView) findViewById(R.id.signupsignup);
        firstnamefield = (EditText) findViewById(R.id.firstname);
        lastnamefield = (EditText) findViewById(R.id.lastname);
        emailaddressfield = (EditText) findViewById(R.id.email);
        passwordfield = (EditText) findViewById(R.id.password);
        countrydigit = (TextView) findViewById(R.id.countrydigit);
        phonenumberfield = (EditText) findViewById(R.id.phone);
        createaccount = (Button) findViewById(R.id.createaccount);
        vendoridfield = (EditText) findViewById(R.id.vendorid);
        toolbar = (Toolbar) findViewById(R.id.signuptoolbar);
        security_question_field = findViewById(R.id.signup_security_answer);
        getId = findViewById(R.id.get_reg_id);
        profileimage = (SimpleDraweeView) findViewById(R.id.vendorsignupprofileimage);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        signup.setTypeface(customfont2);
        getId.setTypeface(customfont2);
        createaccount.setTypeface(customfont2);
        firstnamefield.setTypeface(customfont2);
        lastnamefield.setTypeface(customfont2);
        emailaddressfield.setTypeface(customfont2);
        passwordfield.setTypeface(customfont2);
        countrydigit.setTypeface(customfont2);
        security_question_field.setTypeface(customfont2);
        phonenumberfield.setTypeface(customfont2);
        vendoridfield.setTypeface(customfont2);


        // ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //getWindow().setStatusBarColor(Color.parseColor("#00000000"));
        }
        // Typeface customfont= Typeface.createFromAsset(getAssets(),"Lato-Regular.ttf");
    }


    private void setOnclickListener() {
        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(100, 100)
                        .setFixAspectRatio(true)
                        .start(com.dammy.android.emarketvendor.Activities.signup.this);
            }
        });

        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstname = firstnamefield.getText().toString().trim();
                lastname = lastnamefield.getText().toString().trim();
                emailaddress = emailaddressfield.getText().toString().trim();
                phonenumber = phonenumberfield.getText().toString().trim();
                id = vendoridfield.getText().toString().trim();
                password = passwordfield.getText().toString().trim();
                security_answer = security_question_field.getText().toString().trim();
                if(!validateForm()){

                }
                else {
                    SignUpVendorTask signUpVendorTask = new SignUpVendorTask();
                    signUpVendorTask.execute();
                }

            }
        });

        getId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://jl-market.com/RegistrationForm/registration.php"));
                startActivity(intent);
            }
        });
    }

    private void showloadingdialog() {
        loadingdialog = new Dialog(signup.this, android.R.style.Theme_Light);
        loadingdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingdialog.setContentView(R.layout.loadingdialog);
        ImageView image = loadingdialog.findViewById(R.id.loadingimage);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(image);
        Glide.with(signup.this).load(R.drawable.loading).into(imageViewTarget);
        loadingdialog.setCancelable(false);

        loadingdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        loadingdialog.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resulturi = result.getUri();
                Log.e("uri", resulturi.toString());
                getImageFromDrawee(resulturi);
                profileimage.setImageURI(resulturi);
                Log.e("uri", resulturi.toString());

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("uri", "eroe");
            }
        }
    }

    public class SignUpVendor {

        public String GetData(String url, String firstname, String lastname, String Email, String password, String phonenumber, String registrationid,
                              String image,String security_answer) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                RequestBody formBody = new FormBody.Builder()
                        .add("firstname", firstname)
                        .add("lastname", lastname)
                        .add("emailaddress", Email)
                        .add("password", password)
                        .add("phonenumber", phonenumber)
                        .add("registrationid", registrationid)
                        .add("image", image)
                        .add("security_answer",security_answer)
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

        //Bitmap scBitmap  = Bitmap.createScaledBitmap(bitmap, 300, 300, false);


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

    private Boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public class SignUpVendorTask extends AsyncTask {
        String prompt;

        @Override
        protected void onPreExecute() {
              showloadingdialog();
           //Toast.makeText(signup.this, "Signing up vendor", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }
            String uploadImage = getStringImage(image);
            String jsonString = new SignUpVendor().GetData(Vendor_SignUp_url, firstname, lastname, emailaddress, password, phonenumber, id, uploadImage,security_answer);

            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");
                    if (status.equalsIgnoreCase("emailalreadyexist")) {
                        prompt = "Email already exist. please try again with a different Email";
                        return prompt;
                    }
                    if (status.equalsIgnoreCase("invalidregistrationid")) {
                        prompt = "Registration id is not valid please try again";
                        return prompt;
                    }

                    if (status.equalsIgnoreCase("Vendor Registered Successfully")) {
                       vendorprofile vendorprofile =  ParseJson(info);
                       return vendorprofile;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        protected void onPostExecute(Object result) {
            loadingdialog.dismiss();
         //   Toast.makeText(signup.this, result.toString(), Toast.LENGTH_SHORT).show();
            if (result == null) {
                Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_LONG).show();
            }
           else if (result.toString().equalsIgnoreCase(prompt)) {
                Toast.makeText(signup.this, prompt, Toast.LENGTH_LONG).show();
            }

           else if (result != null) {
                if (result instanceof vendorprofile) {

                    Intent intent = new Intent(com.dammy.android.emarketvendor.Activities.signup.this, MainActivity.class);
                    ArrayList<vendorprofile> vendorprofile = new ArrayList<>();
                    vendorprofile.add((vendorprofile) result);
                    intent.putParcelableArrayListExtra("vendorprofile", vendorprofile);
                    intent.putExtra("profile", "profile");
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);


                }
            }

        }

    }

        private void getImageFromDrawee(Uri imageUri) {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            ImageRequest imageRequest = ImageRequestBuilder
                    .newBuilderWithSource(imageUri)
                    .setRequestPriority(Priority.HIGH)
                    .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                    .build();

            DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, com.dammy.android.emarketvendor.Activities.signup.this);

            dataSource.subscribe(new BaseBitmapDataSubscriber() {
                @Override
                protected void onNewResultImpl( Bitmap bitmap) {
                    if (dataSource.isFinished() && bitmap != null) {
                        Log.e("here", "onNewResultImpl: ");
                        image = Bitmap.createBitmap(bitmap);
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

        private vendorprofile ParseJson(JSONObject jsonObject) throws JSONException {
            vendorprofile vendorprofile = null;
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject j = jsonArray.getJSONObject(i);
                String firstname = j.getString("firstname");
                String lastname = j.getString("lastname");
                String phonenumber = j.getString("phonenumber");
                String email = j.getString("email");
                String profilepicture = j.getString("profilepicture");
                String displayname = j.getString("shopname");
                String deliveryminute = j.getString("deliveryminute");
                String minimumorder = j.getString("minimumorder");
                String deliveryfee = j.getString("deliveryfee");
                String openportal = j.getString("openpaymentportal");
                String paymentstate = j.getString("paymentstate");
                int priceofgoodssold = j.getInt("priceofgoodssold");
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
                String password = j.getString("password");
                String shopopen = j.getString("shopopen");


                vendorprofile = new vendorprofile(firstname, lastname, email, phonenumber, profilepicture);
                vendorprofile.setDisplayname(displayname);
                vendorprofile.setDeliveryminute(deliveryminute);
                vendorprofile.setMinimumorder(minimumorder);
                vendorprofile.setDeliveryfee(deliveryfee);
                vendorprofile.setPassword(password);
                vendorprofile.setShopopen(shopopen);

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

                vendorprofile.setOpenportal(openportal);
                vendorprofile.setPriceofgoodssold(priceofgoodssold);
                vendorprofile.setPaymentstate(paymentstate);
            }
            return vendorprofile;

        }

        private boolean validateForm(){

            boolean valid  = true;

            if(TextUtils.isEmpty(security_answer)){
                security_question_field.setError("Required");
                valid = false;
            }
            else {
                security_question_field.setError(null);
            }
            if(TextUtils.isEmpty(firstname)){
                firstnamefield.setError("Required");
                valid = false;
            }
            else {
                firstnamefield.setError(null);
            }
            if(TextUtils.isEmpty(lastname)){
                lastnamefield.setError("Required");
                valid = false;
            }
            else {
                lastnamefield.setError(null);
            }
            if(TextUtils.isEmpty(phonenumber)){
                phonenumberfield.setError("Required");
                valid = false;
            }
            else {
                phonenumberfield.setError(null);
            }
            if(TextUtils.isEmpty(id)){
                vendoridfield.setError("Required");
                valid = false;
            }
            else {
                vendoridfield.setError(null);
            }
            if(TextUtils.isEmpty(emailaddress)){
                emailaddressfield.setError("Required");
                valid = false;
            }
            else {
                emailaddressfield.setError(null);
            }
            if(TextUtils.isEmpty(password)){
                passwordfield.setError("Required");
                valid = false;
            }
            else {
                passwordfield.setError(null);
            }
            if(image == null){
                Toast.makeText(this, "Please Select Your Profile Picture", Toast.LENGTH_SHORT).show();
                valid = false;
            }
        return valid;
        }
    @Override
    public void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        outstate.putInt("pid",android.os.Process.myPid());

    }

    }