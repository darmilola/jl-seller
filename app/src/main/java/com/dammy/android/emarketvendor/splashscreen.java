package com.dammy.android.emarketvendor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.dammy.android.emarketvendor.Fragments.Login;
import com.dammy.android.emarketvendor.Models.vendorprofile;

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

public class splashscreen extends AppCompatActivity {
    CountDownTimer countDownTimer;
    Intent intent;
    String email,password;

    private static String Vendor_Login_url = "http://jl-market.com/vendor/vendorlogin.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);

        Typeface customfont= Typeface.createFromAsset(getAssets(),"Kylo-Light.otf");

        countDownTimer = new CountDownTimer(4000,1000);
        countDownTimer.start();
        intent = new Intent(splashscreen.this,openingPageActivity.class);


    }


    public class CountDownTimer extends android.os.CountDownTimer {


        public CountDownTimer(long millisInFuture, long countDownInterval) {

            super(millisInFuture, countDownInterval);

        }

        @Override
        public void onTick(long millisUntilFinished) {


        }

        @Override
        public void onFinish() {

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            email = preferences.getString("email","");
            password = preferences.getString("password","");
            if(!email.equalsIgnoreCase("")&& !password.equalsIgnoreCase("")){
                 new loginTask().execute();
            }
            else {
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                finish();
            }

        }

    }

    public class loginTask extends AsyncTask {


        String prompt;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Object doInBackground(Object[] objects) {

            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }
            String serverResponse = new LoginVendor().GetData(Vendor_Login_url, email, password);
            if (serverResponse != null) {
                try {
                    JSONObject jsonObject = new JSONObject(serverResponse);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");

                    if(status.equalsIgnoreCase("wronguserameorpassword")){
                        prompt = "Invalid Username Or Password";
                        return prompt;
                    }
                    if(status.equalsIgnoreCase("loggedin")){
                        vendorprofile vendorprofile = ParseJson(info);
                        return vendorprofile;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return serverResponse;
        }

        protected void onPostExecute(Object result) {


             if (result == null) {

                   }
            else if (result.toString().equalsIgnoreCase(prompt)) {
                Toast.makeText(splashscreen.this, prompt, Toast.LENGTH_LONG).show();
            }

            else if (result != null) {
                if (result instanceof vendorprofile) {

                    Intent intent = new Intent(splashscreen.this, MainActivity.class);
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


    public class LoginVendor {

        public String GetData(String url, String email, String password) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                RequestBody formBody = new FormBody.Builder()
                        .add("email", email)
                        .add("password", password)
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

}
