package com.dammy.android.emarketvendor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.dammy.android.emarketvendor.Fragments.PaymentPage;
import com.dammy.android.emarketvendor.Fragments.PlaceOrders;
import com.dammy.android.emarketvendor.Fragments.help_fragment;
import com.dammy.android.emarketvendor.Fragments.vendorshopProduct;
import com.dammy.android.emarketvendor.Models.vendorprofile;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    static LinearLayout logoutlayout, myshoplayout,mydeallayout,orderslayout,accountlayout,paymentlayout,Helplayout;
    TextView welcome;
    TextView Name;
    TextView email;
    TextView shop;
    static TextView Deals;
    static TextView ProductCategories;
    static TextView deliverylocation;
    TextView Orders;
    TextView account;
    TextView payment;
    TextView info;
    TextView logout;
    TextView title;
    Toolbar toolbar;
    ImageView settings;
    String vendoremail;
    SimpleDraweeView profilepicture;
    ArrayList<vendorprofile>  vendorprofile;
    String firebaseid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Fresco.initialize(this);

        setContentView(R.layout.activity_main);

        InitializeView();
        getComingVendorInfo();
        InitializeDrawer();
        initializeDefaultFragment();
        setOnclicklistener();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        firebaseid = preferences.getString("firebaseid","");
        if(!firebaseid.equalsIgnoreCase("")) {
            new saveidtask().execute();
        }
      storeCredentialInSharedPref(vendorprofile.get(0).getEmail(),vendorprofile.get(0).getPassword());
    }
    private void storeCredentialInSharedPref(String email,String password){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email",email);
        editor.putString("password",password);
        editor.commit();
    }
    private void removecredentialFromSharedpref(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("email");
        editor.remove("password");
        editor.commit();
    }


    private void getComingVendorInfo(){
        vendorprofile = getIntent().getParcelableArrayListExtra("vendorprofile");
        getComingProfilepicturePath(vendorprofile);
        getComingOtherDetails(vendorprofile);

    }

    private void getComingProfilepicturePath(ArrayList<vendorprofile> vendorprofile){

        if(vendorprofile != null) {
            String ImageUri = vendorprofile.get(0).getProfileimage();
            Uri imageuri = Uri.parse(ImageUri);
            ImageRequest request = ImageRequest.fromUri(imageuri);
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setOldController(profilepicture.getController()).build();
            profilepicture.setController(controller);
        }
    }

    private void getComingOtherDetails(ArrayList<vendorprofile> vendorprofile){
        if(vendorprofile != null){
             String firstname = vendorprofile.get(0).getFirstname();
             vendoremail = vendorprofile.get(0).getEmail();
             Name.setText(firstname);
             email.setText(vendoremail);

        }
    }


    public class saveidtask extends AsyncTask {
        private String url = "http://jl-market.com/vendor/savefirebasetoken.php";
        @Override
        protected Object doInBackground(Object[] objects) {
            String serverresponse = new saveid().GetData(url,vendorprofile.get(0).getEmail(),firebaseid);
            return serverresponse;
        }
    }

    public class saveid {

        public String GetData(String url,String userid,String firebaseid) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();


                RequestBody formBody = new FormBody.Builder()
                        .add("vendorid", userid)
                        .add("firebaseid",firebaseid)
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

    private void initializeDefaultFragment(){


           if(vendorprofile.get(0).getOpenportal().equalsIgnoreCase("no")){
               notclosepaymentportal(false);
           }
           else{
               notclosepaymentportal(true);
           }
           Fragment fragment = new vendorshopProduct().newInstance(vendorprofile.get(0).getEmail(),vendorprofile);
           FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
           fragmentTransaction.replace(R.id.content_frame, fragment,"myshop");
           fragmentTransaction.commit();


    }
    private void setOnclicklistener(){
       /* shop.setOnClickListener(this);
        Deals.setOnClickListener(this);

        account.setOnClickListener(this);

        Orders.setOnClickListener(this);
        info.setOnClickListener(this);
        logout.setOnClickListener(this);
        payment.setOnClickListener(this);*/
        myshoplayout.setOnClickListener(this);
        mydeallayout.setOnClickListener(this);
        orderslayout.setOnClickListener(this);
        accountlayout.setOnClickListener(this);
        paymentlayout.setOnClickListener(this);
        Helplayout.setOnClickListener(this);
        logoutlayout.setOnClickListener(this);

    }


    private void InitializeDrawer(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

        }
    }


    private void InitializeView(){
        welcome = (TextView) findViewById(R.id.activity_main_welcome);
        Name = (TextView)findViewById(R.id.activity_main_user_firstname);
        email = (TextView)findViewById(R.id.useremail);
        shop = (TextView)findViewById(R.id.myshop);
        Deals = (TextView)findViewById(R.id.mydeals);

        Orders = (TextView)findViewById(R.id.orders);
        account = (TextView)findViewById(R.id.account);
        payment = (TextView)findViewById(R.id.payment);
        info = (TextView)findViewById(R.id.help);

        logout = (TextView)findViewById(R.id.logout);
        title = (TextView)findViewById(R.id.maintoolbartitle);
        profilepicture = (SimpleDraweeView) findViewById(R.id.mainprofilepicture);


        accountlayout = findViewById(R.id.accountlayout);
        mydeallayout = findViewById(R.id.mydeallayout);
        myshoplayout = findViewById(R.id.myshoplayout);
        orderslayout = findViewById(R.id.orderslayout);
        paymentlayout = findViewById(R.id.paymentslayout);
        Helplayout = findViewById(R.id.helplayout);
        logoutlayout = findViewById(R.id.logoutlayout);

        myshoplayout.setBackgroundColor(Color.parseColor("#b3fa2d65"));
        Typeface customfont= Typeface.createFromAsset(getAssets(),"Kylo-Light.otf");
        Typeface customfont2= Typeface.createFromAsset(getAssets(),"Kylo-Light.otf");
        welcome.setTypeface(customfont2);
        Name.setTypeface(customfont2);
        email.setTypeface(customfont2);
        shop.setTypeface(customfont);
        title.setTypeface(customfont);
        Deals.setTypeface(customfont);

        Orders.setTypeface(customfont);
        account.setTypeface(customfont);
        payment.setTypeface(customfont);
        info.setTypeface(customfont);
        logout.setTypeface(customfont);
        title.setText("My Shop");
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = 1.0f;
        getWindow().setAttributes(layoutParams);
        toolbar  = (Toolbar) findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onClick(View v) {
        int itemid = v.getId();
        int flag = 0;
        int currentpos = 1;
        DrawerLayout drawerLayout;

        switch (itemid){


            case R.id.myshoplayout:


                Fragment vendorshopProduct = getSupportFragmentManager().findFragmentByTag("myshop");
                if(vendorshopProduct != null && vendorshopProduct.isVisible()){

                }
                else{
                    changetoshopfragment();
                    changetomyshop();
                }
                 drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);

                break;

            case R.id.accountlayout:

                Fragment account = getSupportFragmentManager().findFragmentByTag("account");

                if(account != null && account.isVisible()){

                }
                else{
                    changetoacountfragment();
                    changetoaccount();
                }
                drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);

                break;
            case R.id.helplayout:

                Fragment help = getSupportFragmentManager().findFragmentByTag("help");

                if(help != null && help.isVisible()){

                }
                else{
                    changetohelpfragment();
                    changetohelp();
                }

                drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);


                break;
            case R.id.orderslayout:

                Fragment orders = getSupportFragmentManager().findFragmentByTag("orders");

                if(orders != null && orders.isVisible()){

                }
                else{
                    changetoordersfragment();
                    changetoorder();
                }
                drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);

                break;
            case R.id.mydeallayout:

                Fragment deals = getSupportFragmentManager().findFragmentByTag("deal");

                if(deals != null && deals.isVisible()){

                }
                else {
                    changetodealfragment();
                    changetodeal();
                }
                drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);

                break;

            case R.id.paymentslayout:
                Fragment payment = getSupportFragmentManager().findFragmentByTag("payment");

                if(payment != null && payment.isVisible()){

                }
                else {
                    changetopaymentfragment();
                    changetopayment();
                }
                drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);

                break;
            case R.id.logoutlayout:
                Intent intent = new Intent(MainActivity.this,openingPageActivity.class);
                removecredentialFromSharedpref();
                startActivity(intent);

        }






    }
    private void changetoshopfragment() {
        Fragment fragment = vendorshopProduct.newInstance(email.getText().toString(),vendorprofile);
        title.setText("My Shop");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment, "myshop");
        fragmentTransaction.commit();

    }

    private void changetoacountfragment() {
        title.setText("Account");
        Fragment  fragment = com.dammy.android.emarketvendor.Fragments.account.newInstance(vendorprofile);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment, "account");
        fragmentTransaction.commit();

    }
    private void changetohelpfragment() {
        title.setText("Help");
        Fragment  fragment = help_fragment.newInstance(vendorprofile);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment, "help");
        fragmentTransaction.commit();

    }
    private void changetoordersfragment() {
        title.setText("Orders");
        Fragment fragment = PlaceOrders.newInstance(vendorprofile.get(0).getEmail());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment, "orders");
        fragmentTransaction.commit();

    }
    private void changetodealfragment() {
        title.setText("Deals");
        Fragment  fragment = com.dammy.android.emarketvendor.Fragments.Deals.newInstance(vendorprofile.get(0).getEmail());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment, "deal");
        fragmentTransaction.commit();

    }

    private void changetopaymentfragment() {
        title.setText("Payment");
        Fragment  fragment = PaymentPage.newInstance(vendorprofile);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment, "payment");
        fragmentTransaction.commit();

    }
    private void changetomyshop(){
        myshoplayout.setBackgroundColor(Color.parseColor("#b3fa2d65"));
        paymentlayout.setBackgroundColor(Color.parseColor("#00000000"));
        accountlayout.setBackgroundColor(Color.parseColor("#00000000"));
        orderslayout.setBackgroundColor(Color.parseColor("#00000000"));
        mydeallayout.setBackgroundColor(Color.parseColor("#00000000"));
        Helplayout.setBackgroundColor(Color.parseColor("#00000000"));
    }

 private void changetoaccount(){
        myshoplayout.setBackgroundColor(Color.parseColor("#00000000"));
        paymentlayout.setBackgroundColor(Color.parseColor("#00000000"));
        accountlayout.setBackgroundColor(Color.parseColor("#b3fa2d65"));
        orderslayout.setBackgroundColor(Color.parseColor("#00000000"));
        mydeallayout.setBackgroundColor(Color.parseColor("#00000000"));
     Helplayout.setBackgroundColor(Color.parseColor("#00000000"));
    }

    private void changetopayment(){
        myshoplayout.setBackgroundColor(Color.parseColor("#00000000"));
        paymentlayout.setBackgroundColor(Color.parseColor("#b3fa2d65"));
        accountlayout.setBackgroundColor(Color.parseColor("#00000000"));
        orderslayout.setBackgroundColor(Color.parseColor("#00000000"));
        mydeallayout.setBackgroundColor(Color.parseColor("#00000000"));
        Helplayout.setBackgroundColor(Color.parseColor("#00000000"));
    }

    private void changetodeal(){
        myshoplayout.setBackgroundColor(Color.parseColor("#00000000"));
        paymentlayout.setBackgroundColor(Color.parseColor("#00000000"));
        accountlayout.setBackgroundColor(Color.parseColor("#00000000"));
        orderslayout.setBackgroundColor(Color.parseColor("#00000000"));
        mydeallayout.setBackgroundColor(Color.parseColor("#b3fa2d65"));
        Helplayout.setBackgroundColor(Color.parseColor("#00000000"));
    }

    private void changetoorder(){
        myshoplayout.setBackgroundColor(Color.parseColor("#00000000"));
        paymentlayout.setBackgroundColor(Color.parseColor("#00000000"));
        accountlayout.setBackgroundColor(Color.parseColor("#00000000"));
        orderslayout.setBackgroundColor(Color.parseColor("#b3fa2d65"));
        mydeallayout.setBackgroundColor(Color.parseColor("#00000000"));
        Helplayout.setBackgroundColor(Color.parseColor("#00000000"));
    }

    private void changetohelp(){
        myshoplayout.setBackgroundColor(Color.parseColor("#00000000"));
        paymentlayout.setBackgroundColor(Color.parseColor("#00000000"));
        accountlayout.setBackgroundColor(Color.parseColor("#00000000"));
        orderslayout.setBackgroundColor(Color.parseColor("#00000000"));
        mydeallayout.setBackgroundColor(Color.parseColor("#00000000"));
        Helplayout.setBackgroundColor(Color.parseColor("#b3fa2d65"));
    }
    public  interface passcloseMainDialog{
        void closeDialog(String data);
    }


   public static void notclosepaymentportal(boolean value){

        paymentlayout.setEnabled(value);
   }



}
