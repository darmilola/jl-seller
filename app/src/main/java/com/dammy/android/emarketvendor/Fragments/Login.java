package com.dammy.android.emarketvendor.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
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
import com.dammy.android.emarketvendor.Activities.forgotpassword;
import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.MainActivity;
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


public class Login extends Fragment {

    EditText emailfield, passwordfield;
    TextView forgotpassword;
    String email,password;
    Button login;
    View view;
    Dialog loadingdialog;
    private static String Vendor_Login_url = "http://jl-market.com/vendor/vendorlogin.php";
    public Login() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

                view = inflater.inflate(R.layout.fragment_login, container, false);
                InitializeView();
                SetOnclickListener();
                return view;
    }

    private void InitializeView(){

        emailfield = view.findViewById(R.id.loginemailaddress);
        passwordfield = view.findViewById(R.id.loginpassword);
        forgotpassword = view.findViewById(R.id.forgotpassword);

        login = view.findViewById(R.id.loginlogin);
        Typeface customfont= Typeface.createFromAsset(getActivity().getAssets(),"Kylo-Light.otf");
        emailfield.setTypeface(customfont);
        login.setTypeface(customfont);
    }

    private void SetOnclickListener(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               email = emailfield.getText().toString().trim();
               password = passwordfield.getText().toString().trim();

               if(!validateform()){

               }

               else{
                   loginTask loginTask = new loginTask();
                   loginTask.execute();
               }
              //Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
              //startActivity(intent);
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),forgotpassword.class);
                startActivity(intent);
            }
        });
    }

    private void showloadingdialog() {
        loadingdialog = new Dialog(getContext(), android.R.style.Theme_Light);
        loadingdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingdialog.setContentView(R.layout.loadingdialog);
        ImageView image = loadingdialog.findViewById(R.id.loadingimage);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(image);
        Glide.with(getContext()).load(R.drawable.loading).into(imageViewTarget);
        loadingdialog.setCancelable(false);

        loadingdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        loadingdialog.show();
    }
  public class loginTask extends AsyncTask {


      String prompt;

      @Override
      protected void onPreExecute() {
            showloadingdialog();
            forgotpassword.setEnabled(false);
          //Toast.makeText(getContext(), "Logging in vendor", Toast.LENGTH_LONG).show();
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

          loadingdialog.dismiss();
          //Toast.makeText(getContext(), result.toString(), Toast.LENGTH_LONG).show();
           if (result == null) {
               forgotpassword.setEnabled(true);
              Toast.makeText(getContext(), "Error Occured", Toast.LENGTH_LONG).show();
          }
          else if (result.toString().equalsIgnoreCase(prompt)) {
               forgotpassword.setEnabled(true);
              Toast.makeText(getContext(), prompt, Toast.LENGTH_LONG).show();
          }

          else if (result != null) {
              if (result instanceof vendorprofile) {

                  Intent intent = new Intent(getContext(), MainActivity.class);
                  ArrayList<vendorprofile> vendorprofile = new ArrayList<>();
                  vendorprofile.add((vendorprofile) result);
                  intent.putParcelableArrayListExtra("vendorprofile", vendorprofile);
                  intent.putExtra("profile", "profile");
                  startActivity(intent);
                  getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);


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

        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
            vendorprofile.setOpenportal(openportal);
            vendorprofile.setPriceofgoodssold(priceofgoodssold);
            vendorprofile.setPaymentstate(paymentstate);
        }

        return vendorprofile;

    }
    private boolean validateform(){
        boolean valid = true;

        if(TextUtils.isEmpty(email)){
            emailfield.setError("Required");
            valid = false;
        }
        else{
            emailfield.setError(null);
        }
        if(TextUtils.isEmpty(password)){
            passwordfield.setError("Required");
            valid = false;
        }
        else{
            passwordfield.setError(null);
        }
        return valid;
    }

}
