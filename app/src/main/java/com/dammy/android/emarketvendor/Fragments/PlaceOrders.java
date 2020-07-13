package com.dammy.android.emarketvendor.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dammy.android.emarketvendor.Activities.Fullmap;
import com.dammy.android.emarketvendor.Activities.newOrderuserInfo;
import com.dammy.android.emarketvendor.Adapter.DealAdapter;
import com.dammy.android.emarketvendor.Adapter.ordermessageadapter;
import com.dammy.android.emarketvendor.Models.ordermessagemodel;
import com.dammy.android.emarketvendor.R;
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
public class PlaceOrders extends Fragment  {


    RecyclerView recyclerView;
    ArrayList<ordermessagemodel> ordermodelArrayList = new ArrayList<>();
    View view;
    static String vendoremail;
    LinearLayout progressbarlayout,nonetwork,error,emptyorderlayout;
    Button errorbutton,nonetworkbutton;
    TextView errortext,nonetworktext,emptyordertext;
    Context context;
    ArrayList<ordermessagemodel> OrdermodelArrayList;
    public PlaceOrders() {
        // Required empty public constructor
    }

    public static PlaceOrders newInstance(String email){
        vendoremail = email;
        return new PlaceOrders();
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

                OrdermodelArrayList = bundle.getParcelableArrayList("orderlist");
                ordermessageadapter ordermessageadapter = new ordermessageadapter(OrdermodelArrayList, new ordermessageadapter.ListItemClickListener() {

                    @Override
                    public void onListItemClick(int clickedItemIndex) {
                        ArrayList<ordermessagemodel> ordermessagemodels = OrdermodelArrayList;
                        Intent intent = new Intent(getContext(), newOrderuserInfo.class);
                        intent.putExtra("orderid", ordermessagemodels.get(clickedItemIndex).getOrderid());
                        intent.putExtra("useremail", ordermessagemodels.get(clickedItemIndex).getUserid());
                        startActivity(intent);
                    }
                }, getContext(),vendoremail);
                Log.e("i reach here", "onReceive:");
                ordermessageadapter.notifyDataSetChanged();
                recyclerView.setAdapter(ordermessageadapter);


            }
        }
    };


    @Override
    public void onActivityCreated(Bundle savedinstance){
        super.onActivityCreated(savedinstance);
        if(vendoremail == null){
         Intent intent = new Intent(getContext(),splashscreen.class);
         startActivity(intent);
        }
        else {

            new getidtask().execute();
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_place_orders, container, false);


        initializeview();
        initializerecyclerview();

        setOnclicklistener();





        return view;
    }

    private void setOnclicklistener(){
        errorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getidtask().execute();
            }
        });

        nonetworkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getidtask().execute();
            }
        });
    }
    private void initializeview(){

        progressbarlayout = view.findViewById(R.id.progressbarlayout);
        nonetwork = view.findViewById(R.id.nonetworklayout);
        error = view.findViewById(R.id.errorlayout);

        errorbutton = view.findViewById(R.id.errorbutton);
        nonetworkbutton = view.findViewById(R.id.nonetworkbutton);
        errortext = view.findViewById(R.id.errortext);
        nonetworktext = view.findViewById(R.id.nonetworktext);

        emptyorderlayout = view.findViewById(R.id.orderlayout);
        emptyordertext = view.findViewById(R.id.ordertext);

        Typeface customfont = Typeface.createFromAsset(getActivity().getAssets(), "Kylo-Regular.otf");
        errortext.setTypeface(customfont);
        errorbutton.setTypeface(customfont);
        emptyordertext.setTypeface(customfont);
        nonetworktext.setTypeface(customfont);
        nonetworkbutton.setTypeface(customfont);

    }



    private void initializerecyclerview(){
        recyclerView = (RecyclerView)view.findViewById(R.id.ordermessagerecyclerview);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    public class getidtask extends AsyncTask {
        String prompt;
        private  String URL = "http://jl-market.com/vendor/getOrderidVendor.php";

        @Override
        protected void onPreExecute() {



            progressbarlayout.setVisibility(View.VISIBLE);
            nonetwork.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            emptyorderlayout.setVisibility(View.GONE);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }
            String serverResponse = new getid().GetData(URL, vendoremail);
            if (serverResponse != null) {


                try {
                    JSONObject jsonObject = new JSONObject(serverResponse);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");

                    if (status.equalsIgnoreCase("available")) {
                        ordermodelArrayList = passIdJson(info);

                        return ordermodelArrayList;
                    }
                    else{
                        prompt = "unavailable";
                        return  prompt;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return serverResponse;
        }
        protected void onPostExecute(Object result) {

            //Toast.makeText(getContext(), result.toString(), Toast.LENGTH_LONG).show();
            if (result != null) {
                if (result.toString().equalsIgnoreCase("No Network Connection")) {
                    progressbarlayout.setVisibility(View.GONE);
                    nonetwork.setVisibility(View.VISIBLE);
                    error.setVisibility(View.GONE);
                    emptyorderlayout.setVisibility(View.GONE);
                }
                if(result.toString().equalsIgnoreCase("unavailable")){
                    progressbarlayout.setVisibility(View.GONE);
                    nonetwork.setVisibility(View.GONE);
                    error.setVisibility(View.GONE);
                    emptyorderlayout.setVisibility(View.VISIBLE);
                }
                if(result instanceof ArrayList) {
                    ordermessageadapter ordermessageadapter;
                    ordermessageadapter = new ordermessageadapter((ArrayList<ordermessagemodel>) result, new ordermessageadapter.ListItemClickListener() {
                        @Override
                        public void onListItemClick(int clickedItemIndex) {
                            ArrayList<ordermessagemodel> ordermessagemodels = (ArrayList<ordermessagemodel>) result;
                            Intent intent = new Intent(getContext(), newOrderuserInfo.class);
                            intent.putExtra("orderid", ordermessagemodels.get(clickedItemIndex).getOrderid());
                            intent.putExtra("useremail", ordermessagemodels.get(clickedItemIndex).getUserid());
                            startActivity(intent);
                        }
                    }, getContext(),vendoremail);
                    recyclerView.setAdapter(ordermessageadapter);

                    if(ordermessageadapter.getItemCount() < 1){
                        progressbarlayout.setVisibility(View.GONE);
                        nonetwork.setVisibility(View.GONE);
                        error.setVisibility(View.GONE);
                        emptyorderlayout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                    else {
                        progressbarlayout.setVisibility(View.GONE);
                        nonetwork.setVisibility(View.GONE);
                        error.setVisibility(View.GONE);
                        emptyorderlayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }

            }
            else {

                progressbarlayout.setVisibility(View.GONE);
                nonetwork.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
                emptyorderlayout.setVisibility(View.GONE);
            }
        }
    }

    public ArrayList<ordermessagemodel> passIdJson(JSONObject jsonObject) throws JSONException {
        ArrayList<ordermessagemodel> orderidlist = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject j = jsonArray.getJSONObject(i);
            String orderid = j.getString("orderid");
            String userid = j.getString("useremail");
            ordermessagemodel ordermodel = new ordermessagemodel(orderid,userid);
            orderidlist.add(ordermodel);
        }
        return orderidlist;


    }


    public class getid {

        public String GetData(String url,String vendorid) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();


                RequestBody formBody = new FormBody.Builder()
                        .add("vendorid",vendorid )
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

    private Boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(receiver, new IntentFilter("com.dammy.android.emarketvendor.service.updateorderservice"));

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }

}
