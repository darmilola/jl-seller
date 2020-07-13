package com.dammy.android.emarketvendor.Fragments;


import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.dammy.android.emarketvendor.Adapter.DealAdapter;
import com.dammy.android.emarketvendor.Models.dealmodel;
import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.service.updateservice.updatedeals;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Deals extends Fragment {


    View view;
    RecyclerView recyclerView;
    Button startdeal;
    ArrayList<dealmodel> dealmodelArrayList;
    Dialog startdealdialog,loadingdialog;
    static String vendorid;
    LinearLayout progressbarlayout,nonetwork,error,nodealalayout;
    Button errorbutton,nonetworkbutton;
    TextView errortext,nonetworktext,nodealtext;

    EditText dealdescriptionedittext;
    Context context;
    ArrayList<dealmodel> dealList;
    public Deals() {
        // Required empty public constructor
    }
    public static Deals newInstance(String mvendorid){
        vendorid = mvendorid;
        return new Deals();
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        this.context = context;
    }






    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if(bundle != null){

                dealList = bundle.getParcelableArrayList("deallist");
                if(dealList != null){
                    DealAdapter adapter = new DealAdapter(dealList, getContext(),vendorid);
                    recyclerView.setAdapter(adapter);

                    if (adapter.getItemCount() < 1) {
                        recyclerView.setVisibility(View.GONE);
                        progressbarlayout.setVisibility(View.GONE);
                        nonetwork.setVisibility(View.GONE);
                        error.setVisibility(View.GONE);
                        nodealalayout.setVisibility(View.VISIBLE);
                    }
                    else{
                        recyclerView.setVisibility(View.VISIBLE);
                        progressbarlayout.setVisibility(View.GONE);
                        nonetwork.setVisibility(View.GONE);
                        error.setVisibility(View.GONE);
                        nodealalayout.setVisibility(View.GONE);
                    }

                }
            }
        }
    };
    @Override
    public void onActivityCreated( Bundle savedinstance){
        super.onActivityCreated(savedinstance);

        if(vendorid == null){
            startActivity(new Intent(getContext(),splashscreen.class));
        }
        else {
            new displaydealfromserver().execute();
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

               view =  inflater.inflate(R.layout.fragment_deals, container, false);
               InitializeRecyclerView();
               initializeView();
               setOnclicklistener();






        return view;
    }

    private void setOnclicklistener() {
        startdeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartNewDealDialog();
            }
        });

        nonetworkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new displaydealfromserver().execute();
            }
        });

        errorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new displaydealfromserver().execute();
            }
        });
    }

    private void InitializeRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.dealrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
    private void initializeView(){
        startdeal = view.findViewById(R.id.startnewdeal);
        progressbarlayout = view.findViewById(R.id.progressbarlayout);
        nonetwork = view.findViewById(R.id.nonetworklayout);
        error = view.findViewById(R.id.errorlayout);

        errorbutton = view.findViewById(R.id.errorbutton);
        nonetworkbutton = view.findViewById(R.id.nonetworkbutton);
        errortext = view.findViewById(R.id.errortext);
        nonetworktext = view.findViewById(R.id.nonetworktext);
        nodealalayout = view.findViewById(R.id.nodeallayout);
        nodealtext = view.findViewById(R.id.nodealtext);
        Typeface customfont = Typeface.createFromAsset(getActivity().getAssets(), "Kylo-Regular.otf");
        errortext.setTypeface(customfont);
        errorbutton.setTypeface(customfont);
        nodealtext.setTypeface(customfont);
        nonetworktext.setTypeface(customfont);
        nonetworkbutton.setTypeface(customfont);
          Typeface customfont2= Typeface.createFromAsset(getContext().getAssets(),"Kylo-Light.otf");

        startdeal.setTypeface(customfont2);
    }

    private void showStartNewDealDialog(){
        TextView startdealtitle;

        Button startdealButton;
        startdealdialog = new Dialog(getContext(),R.style.Dialog_Theme);
        startdealdialog.setContentView(R.layout.startnewdealdialog);
        Typeface customfont2= Typeface.createFromAsset(getActivity().getAssets(),"Kylo-Light.otf");
        startdealtitle = startdealdialog.findViewById(R.id.startdealtitle);
        dealdescriptionedittext= startdealdialog.findViewById(R.id.startdealtext);
        startdealButton = startdealdialog.findViewById(R.id.startdealbutton);
        startdealtitle.setTypeface(customfont2);
        startdealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewdealtask addnewdealtask = new addnewdealtask();
                addnewdealtask.execute();

            }
        });
        dealdescriptionedittext.setTypeface(customfont2);
        startdealButton.setTypeface(customfont2);
        getActivity().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        startdealdialog.show();
        startdealdialog.setCancelable(true);
        startdealdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            startdealdialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            startdealdialog.getWindow().setStatusBarColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));

        }
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



    public class displaydealfromserver extends AsyncTask{

        private String displayurl = "http://jl-market.com/vendor/displaydeals.php";

        String prompt;

        @Override
        protected void onPreExecute() {
            progressbarlayout.setVisibility(View.VISIBLE);
            nonetwork.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            nodealalayout.setVisibility(View.GONE);
            }

        @Override
        protected Object doInBackground(Object[] objects) {
            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }
            String serverresponse = new displaydeals().GetData(displayurl, vendorid);
            if (serverresponse != null) {
                try {
                    JSONObject jsonObject = new JSONObject(serverresponse);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");

                    if(status.equalsIgnoreCase("deals unavailable")){
                        prompt = "deals unavailable";
                        return prompt;
                    }
                    else if(status.equalsIgnoreCase("dealsavailable")){

                        ArrayList<dealmodel> dealmodelArrayList = ParseJson(info);
                        return dealmodelArrayList;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return serverresponse;
        }

        protected void onPostExecute(Object result) {

            //Toast.makeText(getContext(), result.toString(), Toast.LENGTH_SHORT).show();
            if (result == null) {

                progressbarlayout.setVisibility(View.GONE);
                nonetwork.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
                nodealalayout.setVisibility(View.GONE);


                 }

                 else if(result.toString().equalsIgnoreCase("deals unavailable")){
                progressbarlayout.setVisibility(View.GONE);
                nonetwork.setVisibility(View.GONE);
                error.setVisibility(View.GONE);
                nodealalayout.setVisibility(View.VISIBLE);

            }
            else if (result.toString().equalsIgnoreCase("No Network Connection")) {

                progressbarlayout.setVisibility(View.GONE);
                nonetwork.setVisibility(View.VISIBLE);
                error.setVisibility(View.GONE);

                nodealalayout.setVisibility(View.GONE);
            }
            else if (result != null) {
                if (result instanceof ArrayList) {
                    ArrayList<dealmodel> dealmodels = (ArrayList<dealmodel>) result;
                    DealAdapter adapter = new DealAdapter(dealmodels, getContext(),vendorid);
                    recyclerView.setAdapter(adapter);

                    if (adapter.getItemCount() < 1) {
                        recyclerView.setVisibility(View.GONE);
                        progressbarlayout.setVisibility(View.GONE);
                        nonetwork.setVisibility(View.GONE);
                        error.setVisibility(View.GONE);
                        nodealalayout.setVisibility(View.VISIBLE);
                        }
                    else{
                        recyclerView.setVisibility(View.VISIBLE);
                        progressbarlayout.setVisibility(View.GONE);
                        nonetwork.setVisibility(View.GONE);
                        error.setVisibility(View.GONE);
                        nodealalayout.setVisibility(View.GONE);

                    }
                }

            }

        }

    }

    public class displaydeals {


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

    public class startdeals {


        public String GetData(String url, String vendorid,String dealdescription) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                RequestBody formBody = new FormBody.Builder()
                        .add("vendorid", vendorid)
                        .add("description",dealdescription)
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
    public class addnewdealtask extends AsyncTask {
        private String starturl = "http://jl-market.com/vendor/startdeal.php";

        String prompt;

        @Override
        protected void onPreExecute() {
            startdealdialog.dismiss();
            showloadingdialog();
            //Toast.makeText(getContext(), "adding deals", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }

            String serverresponse = new startdeals().GetData(starturl, vendorid, dealdescriptionedittext.getText().toString());

            return serverresponse;

        }

        protected void onPostExecute(Object result) {
            loadingdialog.dismiss();
            if (result != null) {
                if (result.toString().equalsIgnoreCase(prompt)) {

                    Toast.makeText(getContext(), prompt, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Deal Added Successfully", Toast.LENGTH_LONG).show();
                    Intent serviceintent = new Intent(context, updatedeals.class);
                    serviceintent.putExtra("url", "http://jl-market.com/vendor/displaydeals.php");
                    serviceintent.putExtra("vendorid", vendorid);
                    context.startService(serviceintent);

                }
            }
            else{
                Toast.makeText(getContext(), "Unable to add deal please try again", Toast.LENGTH_SHORT).show();
            }



            }



    }
    private Boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private ArrayList<dealmodel> ParseJson(JSONObject jsonObject) throws JSONException {
        ArrayList<dealmodel> dealmodels =  new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject j = jsonArray.getJSONObject(i);
            String description = j.getString("description");
            String id = j.getString("id");
            dealmodel dealmodel = new dealmodel(description);
            dealmodel.setDealID(id);
            dealmodels.add(dealmodel);
            }
        return dealmodels;

    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(receiver, new IntentFilter("com.dammy.android.emarketvendor.service.updateservice"));

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }

}


