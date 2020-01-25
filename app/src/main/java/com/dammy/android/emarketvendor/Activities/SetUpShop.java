package com.dammy.android.emarketvendor.Activities;

import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SetUpShop extends AppCompatActivity {

    Toolbar toolbar;
    TextView paymentmethodtitle, title, shopcitytitle, shopdisplaynametitle, deliverytimetitle, minutestitle, minimumordertitle,
            openinghourvalue, openinghourtitle, closinghourtitle,
            deliveryfeetitle,
            cashondelivery, debitcardondelivery, closinghourvalue, shopcityvalue,
            shopareatitle, shopareavalue, setpintitle,deliveryarea1value,deliveryarea2value,deliveryarea3value
            ,deliveryarea4value,deliveryarea5value,deliveryarea6value,deliveryarea7value,deliveryarea8value,deliveryarea9value,deliveryarea10value
            ,deliverycity1value,deliverycity2value,deliverycity3value,deliverycity4value,deliverycity5value,deliverycity6value,deliverycity7value,deliverycity8value,deliverycity9value,deliverycity10value,
            deliveryarea1title,deliveryarea2title, deliveryarea3title,deliveryarea4title,deliveryarea5title, deliveryarea6title,deliveryarea7title,deliveryarea8title, deliveryarea9title,deliveryarea10title,
            deliverycity1title,deliverycity2title,deliverycity3title,deliverycity4title,deliverycity5title,deliverycity6title,deliverycity7title,deliverycity8title,deliverycity9title,deliverycity10title;

    private static String URL = "http://jl-market.com/vendor/updatevendorinfo.php";
    private static String Edit_URL = "http://jl-market.com/vendor/editshopinformation.php";

    SimpleDraweeView shopimage;
    EditText minimumordervalue,deliveryfeevalue,minutesvalue, displaynamevalue, category1value, category2value, category3value, category4value, category5value;
    Button uploadimage, createshop;
    Bitmap image;
    GoogleMap googleMap;
    String deliverycity1,deliverycity2,deliverycity3,areavalue1,areavalue2,areavalue3,
            displayname, citylocated, arealocated, deliveryminute, minimumorder, deliveryfee, cardpaymentmethods, cashpaymentmethod, openinghour, closinghour, shoplongitude, shoplatitude, flag, flag1;
    Bitmap shopdisplayimage;
    Uri imageuri;
    boolean isDebittCardChecked = false,isCashonDeliveryChecked = false;
    double latitude = 0, longitude = 0;//check
    GoogleMap googleMap1;
    MapView mapView;
    String vendoremail;
    private static final String MAP_BUNDLE = "MAP_BUNDLE";
    Dialog loadingdialog;
    ArrayList<vendorprofile> vendorprofiles;
    Button addmorelocation;
    LinearLayout deliverylocation1,deliverylocation2,deliverylocation3,deliverylocation4,deliverylocation5,deliverylocation6,deliverylocation7,deliverylocation8,deliverylocation9,deliverylocation10;

    CheckBox cashondeliverybox, debitcardondeliverycheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        if(savedInstanceState != null){

            if(savedInstanceState.getInt("pid",-1) == android.os.Process.myPid()){

            }
            else{

                startActivity(new Intent(this,splashscreen.class));

            }
        }
        setContentView(R.layout.activity_set_up_shop);
        mapView = findViewById(R.id.vendorlocationmap);

        if( getIntent().getStringExtra("email") != null){
            vendoremail = getIntent().getStringExtra("email");
        }

        if(getIntent().getParcelableArrayListExtra("vendorprofilefromhelp") != null){
            vendorprofiles = getIntent().getParcelableArrayListExtra("vendorprofilefromhelp");
        }
        else if(getIntent().getParcelableArrayListExtra("vendorprofile") != null){
            vendorprofiles = getIntent().getParcelableArrayListExtra("vendorprofile");
        }
        Bundle mapbundle = null;
        if(savedInstanceState != null){
            mapbundle = savedInstanceState.getBundle(MAP_BUNDLE);
        }

        InitializeView();



        mapView.onCreate(savedInstanceState);


        InitializeMap();
        setOnclickListener();


    }

    private void setOnclickListener() {
        shopcityvalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetUpShop.this, ChooseDeliveryLocation.class);
                startActivityForResult(intent, 1);
            }
        });




        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(300, 250)
                        .setFixAspectRatio(true)
                        .setAutoZoomEnabled(false)
                        .setActivityTitle("Crop Image")
                        .start(SetUpShop.this);
            }

        });



        openinghourvalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowOpeningHour();
            }
        });

        closinghourvalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowClosingHour();
            }
        });

        createshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cardpaymentmethods, cashpaymentmethod, openinghour, closinghour, shoplongitude, shoplatitude,
                displayname = displaynamevalue.getText().toString();
                citylocated = shopcityvalue.getText().toString();
                arealocated = shopareavalue.getText().toString();

                deliveryminute = minutesvalue.getText().toString();
                minimumorder = minimumordervalue.getText().toString();
                deliveryfee = deliveryfeevalue.getText().toString();
                openinghour = openinghourvalue.getText().toString().replace(":00", "");
                closinghour = closinghourvalue.getText().toString().replace(":00", "");
                isCashonDeliveryChecked = ((CheckBox) findViewById(R.id.cashondeliverycheckbox)).isChecked();
                if (isCashonDeliveryChecked) {
                    cashpaymentmethod = "yes";
                } else {
                    cashpaymentmethod = "no";
                }

                isDebittCardChecked = ((CheckBox) findViewById(R.id.debitcardondeliverycheckbox)).isChecked();

                if (isDebittCardChecked) {
                    cardpaymentmethods = "yes";
                } else {
                    cardpaymentmethods = "no";
                }





                if(!validateForm()){

                }

           else{
                    if(vendorprofiles.get(0).getDisplayname().equalsIgnoreCase("")){
                        SetUpShopTask setUpShopTask = new SetUpShopTask();
                        setUpShopTask.execute();
                    }
                    else{
                           new EditShopTask().execute();
                    }


                }
            }
        });
        }

    private Boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public class SetUpShopTask extends AsyncTask{



            String prompt;

            @Override
            protected void onPreExecute() {
                showloadingdialog();
                //Toast.makeText(SetUpShop.this, "Setting up shop", Toast.LENGTH_LONG).show();
            }
            @Override
            protected Object doInBackground(Object[] objects) {
                if (!isNetworkAvailable()) {
                    prompt = "No Network Connection";
                    return prompt;
                }
                String uploadStringImage = getStringImage(image);
                String serverResponse = new CreateShop().GetData(URL, displayname, citylocated, arealocated, Double.toString(latitude), Double.toString(longitude),
                        deliveryminute, minimumorder, deliveryfee, cashpaymentmethod, cardpaymentmethods, openinghour, closinghour, uploadStringImage, vendoremail);

                if (serverResponse != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(serverResponse);
                        JSONObject info = jsonObject.getJSONObject("info");
                        String status = info.getString("status");

                        if (status.equalsIgnoreCase("shopcreatedsuccessfully")) {
                            prompt = "shopcreatedsuccessfully";
                            return prompt;
                        } else if (status.equalsIgnoreCase("shopnamealreadyexist")) {
                          prompt = "shopnamealreadyexist";
                          return prompt;

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }
                 return null;
            }

        protected void onPostExecute(Object result) {


             loadingdialog.dismiss();
             if (result != null) {

                if(result.toString().equalsIgnoreCase("No Network Connection")){
                    Toast.makeText(SetUpShop.this, "No Network Connection", Toast.LENGTH_LONG).show();
                }
                else if(result.toString().equalsIgnoreCase("shopcreatedsuccessfully")){

                    Intent intent = new Intent(SetUpShop.this, MainActivity.class);
                    vendorprofiles.get(0).setDisplayname(displayname);
                    intent.putParcelableArrayListExtra("vendorprofile", vendorprofiles);
                    Toast.makeText(SetUpShop.this, "Shop setting up successful", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
                else if(result.toString().equalsIgnoreCase("shopnamealreadyexist")){
                    Toast.makeText(SetUpShop.this, "Shop name already exist please try again with a different name", Toast.LENGTH_LONG).show();
                }



            }
            else{
                Toast.makeText(SetUpShop.this, "Error Occured", Toast.LENGTH_SHORT).show();
            }
        }
        }

    public class EditShopTask extends AsyncTask{



        String prompt;

        @Override
        protected void onPreExecute() {
            showloadingdialog();
            //Toast.makeText(SetUpShop.this, "Setting up shop", Toast.LENGTH_LONG).show();
        }
        @Override
        protected Object doInBackground(Object[] objects) {
            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }
            String uploadStringImage = getStringImage(image);
            String serverResponse = new EditShop().GetData(Edit_URL,vendorprofiles.get(0).getDisplayname(), displayname, citylocated, arealocated, Double.toString(latitude), Double.toString(longitude),
                    deliveryminute, minimumorder, deliveryfee, cashpaymentmethod, cardpaymentmethods, openinghour, closinghour, uploadStringImage, vendoremail);

            if (serverResponse != null) {
                try {
                    JSONObject jsonObject = new JSONObject(serverResponse);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");

                    if (status.equalsIgnoreCase("shopcreatedsuccessfully")) {
                        prompt = "shopcreatedsuccessfully";
                        return prompt;
                    } else if (status.equalsIgnoreCase("shopnamealreadyexist")) {
                        prompt = "shopnamealreadyexist";
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

            if (result != null) {

                if(result.toString().equalsIgnoreCase("No Network Connection")){
                    Toast.makeText(SetUpShop.this, "No Network Connection", Toast.LENGTH_LONG).show();
                }
                else if(result.toString().equalsIgnoreCase("shopcreatedsuccessfully")){

                    Intent intent = new Intent(SetUpShop.this, MainActivity.class);
                    vendorprofiles.get(0).setDisplayname(displayname);
                    intent.putParcelableArrayListExtra("vendorprofile", vendorprofiles);
                    Toast.makeText(SetUpShop.this, "Shop setting up successful", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
                else if(result.toString().equalsIgnoreCase("shopnamealreadyexist")){
                    Toast.makeText(SetUpShop.this, "Shop name already exist please try again with a different name", Toast.LENGTH_LONG).show();
                }



            }
            else{
                Toast.makeText(SetUpShop.this, "Error Occured", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class CreateShop {

        public String GetData(String url, String displayname, String citylocated, String arealocated, String lat, String log,
                             String deliveryminute,String minimumorder,String deliveryfee,String cashpayment
                              ,String cardpayment, String openinghour, String closinghour,String displayimage,String email) {
            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                RequestBody formBody = new FormBody.Builder()
                        .add("displayname", displayname)
                        .add("citylocated", citylocated)
                        .add("arealocated",arealocated)
                        .add("lat", lat)
                        .add("log", log)
                        .add("deliveryminute", deliveryminute)
                        .add("minimumorder", minimumorder)
                        .add("deliveryfee", deliveryfee)
                        .add("cashpayment", cashpayment)
                        .add("cardpayment", cardpayment)
                        .add("openinghour", openinghour)
                        .add("closinghour", closinghour)
                        .add("displayimage",displayimage)
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

    public class EditShop {

        public String GetData(String url,String olddisplayname, String displayname, String citylocated, String arealocated, String lat, String log,
                              String deliveryminute,String minimumorder,String deliveryfee,String cashpayment
                ,String cardpayment, String openinghour, String closinghour,String displayimage,String email) {
            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                RequestBody formBody = new FormBody.Builder()
                        .add("displayname", displayname)
                        .add("citylocated", citylocated)
                        .add("arealocated",arealocated)
                        .add("lat", lat)
                        .add("log", log)
                        .add("deliveryminute", deliveryminute)
                        .add("minimumorder", minimumorder)
                        .add("deliveryfee", deliveryfee)
                        .add("cashpayment", cashpayment)
                        .add("cardpayment", cardpayment)
                        .add("openinghour", openinghour)
                        .add("closinghour", closinghour)
                        .add("displayimage",displayimage)
                        .add("email", email)
                        .add("olddisplayname",olddisplayname)
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

    public String getStringImage(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        byte[] imagebytes = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imagebytes, Base64.DEFAULT);
        return encodedImage;
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


    private void InitializeView() {




        Typeface customfont2 = Typeface.createFromAsset(getAssets(), "Kylo-Light.otf");
        Typeface customfont = Typeface.createFromAsset(getAssets(), "Kylo-Light.otf");




        mapView = findViewById(R.id.vendorlocationmap);
        toolbar = (Toolbar) findViewById(R.id.setupshoptoolbar);
        title = (TextView) findViewById(R.id.setupshoptitle);
        shopimage = (SimpleDraweeView) findViewById(R.id.setupshopshopimage);
        uploadimage = (Button) findViewById(R.id.setupshopuploadshopimage);
        shopcityvalue = findViewById(R.id.shopcityvalue);
        displaynamevalue = (EditText) findViewById(R.id.displaynamevalue);
        shopdisplaynametitle = (TextView) findViewById(R.id.displaynametitle);
        shopcitytitle = (TextView) findViewById(R.id.shopcitytitle);
        shopcityvalue = (TextView) findViewById(R.id.shopcityvalue);
        deliverytimetitle = (TextView) findViewById(R.id.deliverytimetitle);


        minimumordertitle = (TextView) findViewById(R.id.deliverytimeminutestitle);
        minutesvalue =  findViewById(R.id.deliverytimeminutesvalue);
        minutestitle = (TextView) findViewById(R.id.deliverytimeminutestitle);
        minimumordertitle = (TextView) findViewById(R.id.minimumordertitle);
        minimumordervalue = findViewById(R.id.minimumordervalue);
        openinghourtitle = (TextView) findViewById(R.id.openinghourtitle);
        openinghourvalue = (TextView) findViewById(R.id.openinghourvalue);
        closinghourtitle = (TextView) findViewById(R.id.closinghourtitle);
        closinghourvalue = (TextView) findViewById(R.id.closinghourvalue);
        deliveryfeetitle = (TextView) findViewById(R.id.deliveryfeetitle);
        deliveryfeevalue =  findViewById(R.id.deliveryfeevalue);
        cashondelivery = (TextView) findViewById(R.id.cashondeliverytitle);
        debitcardondelivery = (TextView) findViewById(R.id.debitcardondeliverytitle);
        createshop = (Button) findViewById(R.id.setupshopupcreateshopbutton);
        shopareatitle = (TextView) findViewById(R.id.shopareatitle);
        shopareavalue = (TextView) findViewById(R.id.shopareavalue);
        setpintitle = (TextView) findViewById(R.id.setpinlocationtitle);
        paymentmethodtitle = findViewById(R.id.paymentmethodtitle);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = 0.5f;
        getWindow().setAttributes(layoutParams);

        paymentmethodtitle.setTypeface(customfont);




        displaynamevalue.setTypeface(customfont);
        shopareavalue.setTypeface(customfont);
        shopareatitle.setTypeface(customfont);
        setpintitle.setTypeface(customfont);
        shopcityvalue.setTypeface(customfont);
        shopdisplaynametitle.setTypeface(customfont);
        shopcitytitle.setTypeface(customfont);
        shopcityvalue.setTypeface(customfont);
        deliverytimetitle.setTypeface(customfont);

        cashondelivery.setTypeface(customfont);
        debitcardondelivery.setTypeface(customfont);
        createshop.setTypeface(customfont);
        minimumordertitle.setTypeface(customfont);
        minimumordervalue.setTypeface(customfont);
        minimumordervalue.setTypeface(customfont);
        minimumordertitle.setTypeface(customfont);
        openinghourvalue.setTypeface(customfont);
        openinghourtitle.setTypeface(customfont);
        closinghourvalue.setTypeface(customfont);
        closinghourtitle.setTypeface(customfont);
        deliveryfeevalue.setTypeface(customfont);
        deliveryfeetitle.setTypeface(customfont);

        title.setTypeface(customfont2);
        uploadimage.setTypeface(customfont);
        minutesvalue.setTypeface(customfont);
        minutestitle.setTypeface(customfont);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);




             if(vendorprofiles.get(0).getDisplayname().equalsIgnoreCase("")){
                 vendorprofiles.get(0).setDisplayname("");
                 vendorprofiles.get(0).setMinimumorder("");
                 vendorprofiles.get(0).setDeliveryminute("");
                 vendorprofiles.get(0).setDeliveryfee("");
             }
            displaynamevalue.setText(vendorprofiles.get(0).getDisplayname());
            minutesvalue.setText(vendorprofiles.get(0).getDeliveryminute());
            minimumordervalue.setText(vendorprofiles.get(0).getMinimumorder());
            deliveryfeevalue.setText(vendorprofiles.get(0).getDeliveryfee());

        }




    private void ShowOpeningHour() {
        Calendar currenttime = Calendar.getInstance();
        int hour = currenttime.get(Calendar.HOUR_OF_DAY);
        int minute = currenttime.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog;
        timePickerDialog = new TimePickerDialog(SetUpShop.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                openinghourvalue.setText(hourOfDay + ":" + "00");
                openinghour = String.valueOf(hourOfDay);
            }
        }, hour, minute, true);
        timePickerDialog.setTitle("Select Opening Hour");
        timePickerDialog.show();
    }

    private void ShowClosingHour() {
        Calendar currenttime = Calendar.getInstance();
        int hour = currenttime.get(Calendar.HOUR_OF_DAY);
        int minute = currenttime.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog;
        timePickerDialog = new TimePickerDialog(SetUpShop.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                closinghourvalue.setText(hourOfDay + ":" + "00");
                closinghour = String.valueOf(hourOfDay);

            }
        }, hour, minute, true);
        timePickerDialog.setTitle("Select Closing Hour");
        timePickerDialog.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Uri imageuri = result.getUri();

            shopimage.setVisibility(View.VISIBLE);
            shopimage.setImageURI(imageuri);
            getImageFromDrawee(imageuri);

        }

        if (requestCode == 1) {

            if (resultCode == 50) {

            } else {
                String city = data.getStringExtra("city");
                String area = data.getStringExtra("area");//data coming from user city and area selection
                longitude = data.getDoubleExtra("longitude", 0);
                latitude = data.getDoubleExtra("latitude", 0);
                flag1 = data.getStringExtra("flag1");//flag1 to determine if coming fromarea selectiom
                LatLng latLng = new LatLng(latitude, longitude);//user selected longitude coming from location
                mapView.invalidate();
                //Toast.makeText(this, Double.toString(latLng.latitude) + " " + Double.toString(latLng.longitude), Toast.LENGTH_SHORT).show();
                UserSelectionAfterAreaInitializeMap(latitude, longitude);

                shopcityvalue.setText(city);
                shopareavalue.setText(area);
            }

        }
        if (requestCode == 4 ) {
            if (resultCode == 50) {


            } else {
                flag = data.getStringExtra("flagfrommapselect");
                latitude = data.getDoubleExtra("latitude", 0); //coming from userclickign on arae
                longitude = data.getDoubleExtra("longitude", 0);
                LatLng latLng = new LatLng(latitude, longitude);
                mapView.invalidate();
                //Toast.makeText(this, Double.toString(latLng.latitude) + " " + Double.toString(latLng.longitude), Toast.LENGTH_SHORT).show();
                UserSelectionAfterAreaInitializeMap(latitude, longitude);
                //changeMarker(latLng);
            }

        }
        if (requestCode == 3 ) {

            if (resultCode == 50) {


            } else {
                latitude = data.getDoubleExtra("latitude", 0); //coming from userclickign onmap
                longitude = data.getDoubleExtra("longitude", 0);
                LatLng latLng = new LatLng(latitude, longitude);
                //Toast.makeText(this, Double.toString(latLng.latitude) + " " + Double.toString(latLng.longitude), Toast.LENGTH_SHORT).show();
                mapView.invalidate();
                InitializeSetPinMap(latLng);
                //changeMarker(latLng);
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

        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, SetUpShop.this);

        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(Bitmap bitmap) {
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
    @Override
    public void onPause() {
      super.onPause();
    mapView.onPause();
  }

  @Override
    public void onResume(){
        super.onResume();
        mapView.onResume();
        if(googleMap1 != null){
            //googleMap1.clear();
           // UserSelectionAfterAreaInitializeMap(latitude,longitude);
        }
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
   private void InitializeMap(){
        if(googleMap1 == null){

                    mapView.getMapAsync( new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap Map) {
                    googleMap1 = Map;
                    googleMap1.getUiSettings().setRotateGesturesEnabled(false);
                    googleMap1.getUiSettings().setScrollGesturesEnabled(false);
                    googleMap1.clear();
                    googleMap1.addMarker(new MarkerOptions().position(new LatLng(6.4253,3.4219))
                            .title("Location")
                            .draggable(false));

                    moveTocurrentposition(new LatLng(6.4253,3.4219));
                    mapView.onResume();
                    googleMap1.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            Intent intent = new Intent(SetUpShop.this, Fullmap.class);
                            if (latitude == 0 && longitude == 0) {
                                startActivityForResult(intent, 3);//user sees map firsttime
                            }
                            else{
                                intent.putExtra("userlat", latitude);//map already have long and lat fromlocation chosen
                                intent.putExtra("userlong",longitude);
                                startActivityForResult(intent, 3);
                            }
                        }
                    });

                    }
            });
        }
    }
    private void InitializeSetPinMap(LatLng latLng){
        if(googleMap1 != null){
          //  googleMap1.getUiSettings().setScrollGesturesEnabled(false);
           // googleMap1.getUiSettings().setScrollGesturesEnabled(false);
            mapView.getMapAsync( new OnMapReadyCallback() {

                @Override
                public void onMapReady(GoogleMap Map) {
                    googleMap1 = Map;
                    googleMap1.getUiSettings().setRotateGesturesEnabled(false);
                    googleMap1.clear();
                    googleMap1.addMarker(new MarkerOptions().position(latLng)
                           // .title("Victoria Island")
                            .draggable(false));

                    moveTocurrentposition(latLng);
                    mapView.onResume();
                    googleMap1.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                        @Override
                        public void onMapClick(LatLng latLng) {

                            Intent intent = new Intent(SetUpShop.this, Fullmap.class);
                            if (latitude == 0 && longitude == 0) {
                                startActivityForResult(intent, 3);//user click map firsttime
                            }
                            else{
                                intent.putExtra("userlat", latitude);//map already have long and lat fromlocation chosen
                                intent.putExtra("userlong",longitude);
                                startActivityForResult(intent, 3);
                            }
                        }
                    });

                }
            });

        }

    }



    private void UserSelectionAfterAreaInitializeMap(Double lat, Double log){

        mapView.getMapAsync( new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap Map) {
                googleMap1.clear();
                googleMap1 = Map;

                googleMap1.addMarker(new MarkerOptions().position(new LatLng(lat,log))
                        .title("Victoria Island")
                        .draggable(false));
                googleMap1.getUiSettings().setRotateGesturesEnabled(false);
                googleMap1.getUiSettings().setScrollGesturesEnabled(false);
                moveTocurrentposition(new LatLng(lat,log));
                googleMap1.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        Intent intent = new Intent(SetUpShop.this, Fullmap.class);


                            intent.putExtra("userlat", latitude);//map already have long and lat fromlocation chosen
                            intent.putExtra("userlong",longitude);
                            startActivityForResult(intent, 4);

                    }
                });
            }
        });
    }
    private void moveTocurrentposition(LatLng currentlocation){
        googleMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(currentlocation,15));
        googleMap1.animateCamera(CameraUpdateFactory.zoomIn());
        googleMap1.animateCamera(CameraUpdateFactory.zoomTo(15),5000,null);
    }

    private boolean validateForm(){
            boolean valid = true;

            if(TextUtils.isEmpty(displayname)){
                displaynamevalue.setError("Required");
                valid = false;
            }
            else{
                displaynamevalue.setError(null);
            }
            if(TextUtils.isEmpty(citylocated)){
                shopcityvalue.setError("Required");
                valid = false;
            }
            else{
                shopcityvalue.setError(null);
            }
            if(TextUtils.isEmpty(arealocated)){
                shopareavalue.setError("Required");
                valid = false;
            }
            else{
                shopareavalue.setError(null);
            }

            if(TextUtils.isEmpty(deliveryminute)){
                minutesvalue.setError("Required");
                valid = false;
            }
            else{
                minutesvalue.setError(null);
            }
            if(TextUtils.isEmpty(minimumorder)){
                minimumordervalue.setError("Required");
                valid = false;
            }
            else{
                minimumordertitle.setError(null);
            }
            if(TextUtils.isEmpty(deliveryfee)){
                deliveryfeevalue.setError("Required");
                valid = false;
            }
            else{
                deliveryfeevalue.setError(null);
            }
            if(TextUtils.isEmpty(openinghour)){
                openinghourvalue.setError("Required");
                valid = false;
            }
            else{
                openinghourvalue.setError(null);
            }
            if(TextUtils.isEmpty(closinghour)){
                closinghourvalue.setError("Required");
                valid = false;
            }
            else{
                closinghourvalue.setError(null);
            }
            if(image == null){
                Toast.makeText(this, "Please Provide Shop Image", Toast.LENGTH_SHORT).show();
                valid = false;
            }

            if(!isCashonDeliveryChecked && !isDebittCardChecked){
                Toast.makeText(this, "Please Choose your delivery method", Toast.LENGTH_SHORT).show();
                valid = false;

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


