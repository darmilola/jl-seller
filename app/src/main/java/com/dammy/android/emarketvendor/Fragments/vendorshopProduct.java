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


import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.dammy.android.emarketvendor.Activities.Showdeliverylocation;
import com.dammy.android.emarketvendor.Activities.Showproductcategory;
import com.dammy.android.emarketvendor.MainActivity;
import com.dammy.android.emarketvendor.Models.ProductCategory;
import com.dammy.android.emarketvendor.Models.vendorprofile;
import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.Activities.SetUpShop;
import com.dammy.android.emarketvendor.Activities.addnewProduct;
import com.dammy.android.emarketvendor.Adapter.AllProductsAdapter;
import com.dammy.android.emarketvendor.Models.Presenter;
import com.dammy.android.emarketvendor.Models.ProductModel;
import com.dammy.android.emarketvendor.interfaces.PresenterLayer;
import com.dammy.android.emarketvendor.interfaces.ViewLayer;
import com.dammy.android.emarketvendor.splashscreen;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class vendorshopProduct extends Fragment implements ViewLayer {

    FloatingActionButton addproductfab;
    RecyclerView AllproductsRecycler;
    PresenterLayer presenter;
    View view;
    TextView welcomemessage;
    Button setupshop,addproducts,addcategories,adddeliverylocation;
    LinearLayout progressbarlayout,welcomelayout;
    static  ArrayList<vendorprofile> mvendorprofiles;
    TextView shopopen;
    Button shopopen_toggle;
    RelativeLayout shop_toggle_container;
    Dialog loadingdialog;
    String shop_toggle_state;
    private static String shop_toggle_url = "http://jl-market.com/vendor/toggleshop.php";




    static String vendoremail;

    public vendorshopProduct() {
        // Required empty public constructor
    }


    public static vendorshopProduct newInstance(String email, ArrayList<vendorprofile> vendorprofiles){
        vendorshopProduct product = new vendorshopProduct();
        vendoremail = email;
        mvendorprofiles = vendorprofiles;
        return product;
    }


    @Override
    public void onActivityCreated( Bundle savedinstance){
        super.onActivityCreated(savedinstance);
        if(mvendorprofiles == null){
            Intent intent = new Intent(getContext(),splashscreen.class);
            startActivity(intent);
        }
        else {

            presenter = new Presenter(this,getContext(),vendoremail);
            presenter.onViewReady();
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_vendorshop_product, container, false);
        InitializeView();
        setOnclickListener();
        allProductsRecyclerInit();

       // MainActivity.enableproductcategories(false);


       return view;
    }

    private void setOnclickListener(){

       setupshop.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getContext(), SetUpShop.class);
               intent.putExtra("email", vendoremail);
               intent.putParcelableArrayListExtra("vendorprofile",mvendorprofiles);
               startActivity(intent);
           }
       });
       adddeliverylocation.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getContext(), Showdeliverylocation.class);
               intent.putParcelableArrayListExtra("vendorprofile",mvendorprofiles);
               startActivity(intent);
           }
       });
       addcategories.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getContext(), Showproductcategory.class);
               intent.putParcelableArrayListExtra("vendorprofile",mvendorprofiles);
               startActivity(intent);
           }
       });

         addproducts.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getContext(),addnewProduct.class);
                 intent.putExtra("email", vendoremail);
                 intent.putParcelableArrayListExtra("vendorprofile",mvendorprofiles);
                 startActivity(intent);
             }
         });

         addproductfab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getContext(),addnewProduct.class);
                 intent.putExtra("email", vendoremail);
                 intent.putParcelableArrayListExtra("vendorprofile",mvendorprofiles);
                 startActivity(intent);
             }
         });

         shopopen_toggle.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                  shop_toggle_state = shopopen_toggle.getText().toString();
                  if(shop_toggle_state.equalsIgnoreCase("closed")){
                      shop_toggle_state = "yes";
                      new ToggleShopTask().execute();
                  }
                  else{
                      shop_toggle_state = "no";
                      new ToggleShopTask().execute();
                  }
             }
         });
    }

    private void InitializeView(){

        addproductfab = view.findViewById(R.id.addnewproductfab);
        welcomemessage = view.findViewById(R.id.welcomemessage);
        setupshop = view.findViewById(R.id.vendorshopproductssetupshop);
        addproducts = view.findViewById(R.id.vendorshopproductaddproducts);
        addcategories = view.findViewById(R.id.vendorshopproductaddproductcategory);
        adddeliverylocation = view.findViewById(R.id.vendorshopproductadddeliverylocation);
        progressbarlayout = view.findViewById(R.id.progressbarlayout);
        welcomelayout = view.findViewById(R.id.welcomemelayout);
        shopopen = view.findViewById(R.id.shop_open_text);
        shopopen_toggle = view.findViewById(R.id.shop_open_toggle);
        shop_toggle_container = view.findViewById(R.id.shoptoggle_container);
        shop_toggle_container.setVisibility(View.GONE);
        welcomelayout.setVisibility(View.GONE);
        Typeface customfont2= Typeface.createFromAsset(getActivity().getAssets(),"Kylo-Light.otf");
        welcomemessage.setTypeface(customfont2);
        shopopen_toggle.setTypeface(customfont2);
        shopopen.setTypeface(customfont2);
        setupshop.setTypeface(customfont2);
        adddeliverylocation.setTypeface(customfont2);
        addcategories.setTypeface(customfont2);
        addproducts.setTypeface(customfont2);
        addproductfab.setVisibility(View.GONE);
        if(mvendorprofiles.get(0).getShopopen().equalsIgnoreCase("yes")){
            shopopen_toggle.setText("Live");
            shopopen_toggle.setBackgroundColor(Color.parseColor("#06e5b8"));
        }
        else{
            shopopen_toggle.setText("Closed");
            shopopen_toggle.setBackgroundColor(Color.parseColor("#f83737"));

        }




    }

    private void allProductsRecyclerInit() {
        AllproductsRecycler = (RecyclerView)view.findViewById(R.id.main_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        AllproductsRecycler.setLayoutManager(linearLayoutManager);
       // AllproductsRecycler.getRecycledViewPool().setMaxRecycledViews(0,0);
        //AllproductsRecycler.setItemAnimator(new DefaultItemAnimator());

    }
    @Override
    public void showCategories(List<ProductCategory> categoryItemList, PresenterLayer presenter) {
         //AllproductsRecycler.setNestedScrollingEnabled(false);
        AllproductsRecycler.setAdapter(new AllProductsAdapter(categoryItemList,presenter,getContext(),mvendorprofiles));
        if(categoryItemList.size() == 0){
            AllproductsRecycler.setVisibility(View.GONE);
            welcomelayout.setVisibility(View.VISIBLE);
            progressbarlayout.setVisibility(View.GONE);
            addproductfab.setVisibility(View.GONE);


        }
        else{
            AllproductsRecycler.setVisibility(View.VISIBLE);
            welcomelayout.setVisibility(View.GONE);
            addproductfab.setVisibility(View.VISIBLE);
            shop_toggle_container.setVisibility(View.VISIBLE);
            progressbarlayout.setVisibility(View.GONE);
//            Toast.makeText(getContext(), "herew", Toast.LENGTH_SHORT).show();

        }        int j = categoryItemList.size();
        Log.e("categorysize",Integer.toString(j) );

    }
    @Override
    public void showProductsInCategory(int categoryid, List<ProductModel> productList, PresenterLayer presenter) {

        //for(int i= 0; i<productList.size();i++){
            AllProductsAdapter adapter = (AllProductsAdapter)AllproductsRecycler.getAdapter();
            adapter.addProductsToCategory(categoryid,productList,View.VISIBLE,View.GONE);
          //  int j = productList.size();
            adapter.notifyItemChanged(categoryid);
            //Log.e("productslistsize",Integer.toString(j));

     //   }

    }


    public class ToggleShopTask extends AsyncTask {


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
            String serverResponse = new changeShopState().GetData(shop_toggle_url,vendoremail,shop_toggle_state);
            if (serverResponse != null) {
                try {
                    JSONObject jsonObject = new JSONObject(serverResponse);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");
                    if(status.equalsIgnoreCase("shopopened")){
                        prompt = "shopopened";
                        return prompt;
                    }
                    else if(status.equalsIgnoreCase("shopclosed")){
                        prompt = "shopclosed";
                        return prompt;
                    }
                    else if(status.equalsIgnoreCase("shopnotset")){
                         prompt = "shopnotset";
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

                Toast.makeText(getContext(), "Error Occured", Toast.LENGTH_LONG).show();
            }

            else if(result.toString().equalsIgnoreCase("No Network Connection")){
                Toast.makeText(getContext(), "No Network Connection", Toast.LENGTH_LONG).show();
            }
            else if(result.toString().equalsIgnoreCase("shopopened")){
                Toast.makeText(getContext(), "Shop is now Live, you can now receive orders for your products", Toast.LENGTH_LONG).show();
                shopopen_toggle.setText("Live");
                shopopen_toggle.setBackgroundColor(Color.parseColor("#06e5b8"));
            }
            else if(result.toString().equalsIgnoreCase("shopclosed")) {
                Toast.makeText(getContext(), "Shop has been closed, you can no longer receive orders for your products", Toast.LENGTH_LONG).show();
                shopopen_toggle.setText("Closed");
                shopopen_toggle.setBackgroundColor(Color.parseColor("#f83737"));
            }



            else if(result.toString().equalsIgnoreCase("shopnotset")){
                Toast.makeText(getContext(), "Please Complete Your Shop Setting Up", Toast.LENGTH_LONG).show();

            }


        }

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

    public class changeShopState {

        public String GetData(String url, String email,String shoptoggle) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                RequestBody formBody = new FormBody.Builder()
                        .add("email", email)
                        .add("shoptoggle", shoptoggle)
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


}
