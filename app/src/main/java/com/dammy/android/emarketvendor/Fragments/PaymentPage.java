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
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
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
import com.dammy.android.emarketvendor.splashscreen;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.exceptions.InvalidEmailException;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentPage extends Fragment {

    TextView vendoremail;
    LinearLayout cardnumberlayout,cardexpirylayout,cvvlayout;
    TextView cardnumbertitle,cardexpirytitle,cvvtitle,lastpaymentvalue;
    EditText cardnumbervalue,cardexpiryvalue,cvvvalue;
    Button paybutton;
    static ArrayList<vendorprofile> vendorprofiles;
    View view;
    Dialog loadingdialog;
    String cardnum;
    int expirymonth;
    int expiryyear;
    String cvv;
    int amounttopay;
    String email;
    String mandy;
    public PaymentPage() {
        // Required empty public constructor
    }
    public static PaymentPage newInstance(ArrayList<vendorprofile> mvendorprofile){
        vendorprofiles = mvendorprofile;
        return new PaymentPage();
    }

    @Override
    public void onActivityCreated(Bundle savedinstance){
        super.onActivityCreated(savedinstance);
        if(vendorprofiles == null){
            Intent intent = new Intent(getContext(),splashscreen.class);
            startActivity(intent);
        }
        else {
            initView();
            preparepayment();
            setListener();

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

               view =  inflater.inflate(R.layout.fragment_payment_page, container, false);
        PaystackSdk.initialize(getContext());


               return view;
    }

    private void preparepayment(){


         if(vendorprofiles != null) {
             amounttopay = (int) Math.round(0.1 * (vendorprofiles.get(0).getPriceofgoodssold()));
             email = vendorprofiles.get(0).getEmail();

             Locale NigerianLocale = new Locale("en", "ng");

             String unFormattedamount = NumberFormat.getCurrencyInstance(NigerianLocale).format(amounttopay);
             String formattedamount = unFormattedamount.replaceAll("\\.00","");

             paybutton.setText("pay"+" "+formattedamount);

         }

    }

    private void initView(){
        cardexpirylayout = view.findViewById(R.id.cardexpirylayout);
        cardnumberlayout = view.findViewById(R.id.cardnumberlayout);
        cvvlayout = view.findViewById(R.id.cvvlayout);
        cardnumbertitle = view.findViewById(R.id.cardnumbertitle);
        cardexpirytitle = view.findViewById(R.id.cardexpirytitle);
        cvvtitle = view.findViewById(R.id.cvvtitle);
        cardnumbervalue = view.findViewById(R.id.cardnumbervalue);
        cardexpiryvalue = view.findViewById(R.id.cardexpiryvalue);
        cvvvalue = view.findViewById(R.id.cvvvalue);
        vendoremail = view.findViewById(R.id.vendoremail);
        paybutton = view.findViewById(R.id.paybutton);

        Typeface customfont= Typeface.createFromAsset(getActivity().getAssets(),"Kylo-Regular.otf");
        cardnumbertitle.setTypeface(customfont);
        cardexpirytitle.setTypeface(customfont);
        cvvtitle.setTypeface(customfont);
        cardnumbervalue.setTypeface(customfont);
        cardexpiryvalue.setTypeface(customfont);
        cvvvalue.setTypeface(customfont);
        paybutton.setTypeface(customfont);
        vendoremail.setTypeface(customfont);
        vendoremail.setText(vendorprofiles.get(0).getEmail());
    }

    private void setListener(){
        cardnumbervalue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    cardnumberlayout.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.productsmallimagebackgrounprimary));

                    cardexpirylayout.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.productsmallimagebackgroundblack));
                    cvvlayout.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.productsmallimagebackgroundblack));
                }
            }
        });

        cvvvalue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    cardnumberlayout.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.productsmallimagebackgroundblack));

                    cardexpirylayout.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.productsmallimagebackgroundblack));
                    cvvlayout.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.productsmallimagebackgrounprimary));


                }
            }
        });

        cardexpiryvalue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    cardnumberlayout.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.productsmallimagebackgroundblack));

                    cardexpirylayout.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.productsmallimagebackgrounprimary));
                    cvvlayout.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.productsmallimagebackgroundblack));

                }
            }
        });

        cardexpiryvalue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String value = s.toString();
                if(value.length() == 2){
                    String cardvalue = "";
                    cardvalue = cardvalue + cardexpiryvalue.getText().toString()+"/";
                    cardexpiryvalue.setText(cardvalue);
                    cardexpiryvalue.setSelection(cardvalue.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        paybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateform()) {
                    cardnum = cardnumbervalue.getText().toString(); //"4084084084084081";
                    mandy = cardexpiryvalue.getText().toString();
                    String arrmandy[] = mandy.split("\\/");
                    expirymonth = Integer.parseInt(arrmandy[0]); //11;
                    expiryyear = Integer.parseInt(arrmandy[1]); //19;
                    cvv = cvvvalue.getText().toString(); //"408"
                    showloadingdialog();
                    Card card = new Card(cardnum, expirymonth, expiryyear, cvv);
                    if (card.isValid()) {

                        Charge charge = new Charge();
                        charge.setCard(card);
                        charge.setAmount(amounttopay * 100);
                        try {
                            charge.setEmail(email);
                        } catch (InvalidEmailException E) {
                            loadingdialog.dismiss();
                            Toast.makeText(getContext(), "Invalid Email", Toast.LENGTH_LONG).show();
                        }
                        PaystackSdk.chargeCard(getActivity(), charge, new Paystack.TransactionCallback() {
                            @Override
                            public void onSuccess(Transaction transaction) {
                                new processpaymenttask().execute();
                                //Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void beforeValidate(Transaction transaction) {

                                //Toast.makeText(getContext(), "before validate", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable error, Transaction transaction) {

                                loadingdialog.dismiss();
                                if (!isNetworkAvailable()) {
                                    Toast.makeText(getContext(), "No Network Connection", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    } else {
                        loadingdialog.dismiss();
                        Toast.makeText(getContext(), "Card not valid", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public class processpaymenttask extends AsyncTask {
        String prompt;

        private String url = "http://jl-market.com/vendor/makepayment.php";


        @Override
        protected void onPreExecute() {
            //showloadingdialog();
            //Toast.makeText(getContext(), "Logging in vendor", Toast.LENGTH_LONG).show();
        }


        @Override
        protected Object doInBackground(Object[] objects) {

            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }
            String serverResponse = new processpayment().GetData(url, vendorprofiles.get(0).getEmail());
            if (serverResponse != null) {
                try {
                    JSONObject jsonObject = new JSONObject(serverResponse);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");

                    if (status.equalsIgnoreCase("updatesuccessfull")) {
                        prompt = "Payment Successfull";
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
            //Toast.makeText(getContext(), result.toString(), Toast.LENGTH_LONG).show();
            if (result == null) {
                Toast.makeText(getContext(), "Error Occured", Toast.LENGTH_LONG).show();
            } else if (result.toString().equalsIgnoreCase("Payment Successfull")) {
                Toast.makeText(getContext(), "Payment Successfull", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), MainActivity.class);
                vendorprofiles.get(0).setPaymentstate("paid");
                vendorprofiles.get(0).setPriceofgoodssold(0);
                vendorprofiles.get(0).setOpenportal("no");
                intent.putParcelableArrayListExtra("vendorprofile", vendorprofiles);
                intent.putExtra("profile", "profile");
                startActivity(intent);

            }
            else{
                Toast.makeText(getContext(), "No network connection", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class processpayment {

        public String GetData(String url, String vendorid) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                RequestBody formBody = new FormBody.Builder()
                        .add("vendorid", vendorid)
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

    private void showloadingdialog() {
        loadingdialog = new Dialog(getContext(), R.style.Dialog_Theme);
        loadingdialog.setContentView(R.layout.loadingdialog);
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.loading);
        ImageView image = loadingdialog.findViewById(R.id.loadingimage);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(image);
        Glide.with(getContext()).load(R.drawable.loading).into(imageViewTarget);
        loadingdialog.show();
        loadingdialog.setCancelable(false);
        getActivity().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        loadingdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loadingdialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            loadingdialog.getWindow().setStatusBarColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private boolean validateform(){
        boolean valid = true;

        if(TextUtils.isEmpty(cardnumbervalue.getText().toString())){
            cardnumbervalue.setError("Required");
            valid = false;
        }
        else{
            cardnumbervalue.setError(null);
        }
        if(TextUtils.isEmpty(cardexpiryvalue.getText().toString())){
            cardexpiryvalue.setError("Required");
            valid = false;
        }
        else{
            cardexpiryvalue.setError(null);
        }
        if(TextUtils.isEmpty(cvvvalue.getText().toString())){
            cvvvalue.setError("Required");
            valid = false;
        }
        else{
            cvvvalue.setError(null);
        }


        if(cardexpiryvalue.getText().toString().length() <  5){
            cardexpiryvalue.setError("Required");
            valid = false;
        }
        else{
            cardexpiryvalue.setError(null);
        }
        return valid;
    }




}
