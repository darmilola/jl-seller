package com.dammy.android.emarketvendor.Fragments;


import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.Adapter.Fulldetailadapter;
import com.dammy.android.emarketvendor.Models.ProductModel;
import com.dammy.android.emarketvendor.Models.fullproductdetailimagemodel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;



/**
 * A simple {@link Fragment} subclass.
 */
/*
public class productfulldetailfragment extends Fragment {


    View view;
    int fullimage2;
    int fullimage3;
    Typeface customfont;
    ImageView indicatorimage1, indicatorimage2, indicatorimage3;
    TextView description,descriptionTitle,price, DeprecatedPrice,percentdecrease,productname;
    fullproductdetailimagemodel image2;
    fullproductdetailimagemodel image3;
    RecyclerView productimageRecycler;
    ArrayList<fullproductdetailimagemodel> list;
    static ArrayList<ProductModel> productdetail;
    Fulldetailadapter fulldetailadapter;
    Drawable smallimageback1,smallimageback2,smallimageback3;
    boolean flag3;
    Runnable runnable = null;
    Handler handler = new Handler();
    public static  productfulldetailfragment newInstance(ArrayList<ProductModel> mproductdetail) {
        productfulldetailfragment productfulldetail_fragment = new productfulldetailfragment();
        productdetail = mproductdetail;
        return productfulldetail_fragment;
    }
    public productfulldetailfragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_productfulldetailfragment, container, false);
        InitializeView();
        InitializePrice();
        InitializeProductFullImage();
        ProductsiamgeRecyclerInit();
        InitializeAdapter();
        startAutoscroll();

        return view;
    }

    private void ProductsiamgeRecyclerInit() {
        productimageRecycler = (RecyclerView)view.findViewById(R.id.productfulldetailrecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);

        productimageRecycler.setLayoutManager(linearLayoutManager);
        //AllproductsRecycler.setItemAnimator(new DefaultItemAnimator());

    }
    private void startAutoscroll() {
        final int[] scrollspeed = {3000};

        final int[] count = {0};
        final boolean[] flag = {true};
        runnable = new Runnable() {
            @Override
            public void run() {

                if (fulldetailadapter.getItemCount() == 1) {
                    return;
                }

                if (count[0] < fulldetailadapter.getItemCount()) {


                    if (count[0] == fulldetailadapter.getItemCount() - 1) {
                        flag[0] = false;
                    } else if (count[0] == 0) {
                        flag[0] = true;
                    }
                    if (flag[0]) {
                        count[0]++;


                    } else {
                        count[0]--;

                    }


                    LinearLayoutManager layoutManager = ((LinearLayoutManager) productimageRecycler.getLayoutManager());

                    int visibleposition = layoutManager.findLastVisibleItemPosition();
                    if (count[0] == 0) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            if (getActivity() != null) {
                                smallimageback1 = getActivity().getDrawable(R.drawable.productsmallimagebackgrounprimary);

                                smallimageback2 = getActivity().getDrawable(R.drawable.productsmallimagebackgroundblack);

                            }
                            if (indicatorimage2.getVisibility() == View.GONE) {
                                indicatorimage1.setBackground(smallimageback1);
                            } else {
                                indicatorimage2.setBackground(smallimageback2);
                                indicatorimage3.setBackground(smallimageback2);
                                indicatorimage1.setBackground(smallimageback1);
                            }

                        }
                        flag3 = false;
                        scrollspeed[0] = 3000;


                    }
                    if (count[0] == 1) {


                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            if (getActivity() != null) {
                                smallimageback1 = getActivity().getDrawable(R.drawable.productsmallimagebackgrounprimary);

                                smallimageback2 = getActivity().getDrawable(R.drawable.productsmallimagebackgroundblack);

                            }
                            if (indicatorimage2.getVisibility() == View.GONE) {
                                indicatorimage1.setBackground(smallimageback1);
                            } else {
                                indicatorimage2.setBackground(smallimageback1);
                                indicatorimage3.setBackground(smallimageback2);
                                indicatorimage1.setBackground(smallimageback2);
                            }
                        }
                        flag3 = false;
                        scrollspeed[0] = 3000;

                    }


                    if (count[0] == 2) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            if (getActivity() != null) {
                                smallimageback1 = getActivity().getDrawable(R.drawable.productsmallimagebackgrounprimary);

                                smallimageback2 = getActivity().getDrawable(R.drawable.productsmallimagebackgroundblack);

                            }
                            if (indicatorimage2.getVisibility() == View.GONE) {
                                indicatorimage1.setBackground(smallimageback1);
                            } else {
                                indicatorimage2.setBackground(smallimageback2);
                                indicatorimage3.setBackground(smallimageback1);
                                indicatorimage1.setBackground(smallimageback2);
                            }
                        }
                        flag3 = false;
                        scrollspeed[0] = 1500;


                    }
                    if (count[0] == 3) {
                        flag3 = true;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            if (getActivity() != null) {
                                smallimageback1 = getActivity().getDrawable(R.drawable.productsmallimagebackgrounprimary);

                                smallimageback2 = getActivity().getDrawable(R.drawable.productsmallimagebackgroundblack);

                            }
                            if (indicatorimage2.getVisibility() == View.GONE) {
                                indicatorimage1.setBackground(smallimageback1);
                            } else {
                                indicatorimage2.setBackground(smallimageback2);
                                indicatorimage3.setBackground(smallimageback1);
                                indicatorimage1.setBackground(smallimageback2);
                            }
                        }
                        scrollspeed[0] = 1500;

                    }
                    // Toast.makeText(getContext(), Integer.toString(count[0]), Toast.LENGTH_SHORT).show();
                    if (flag3) {

                    } else {
                        productimageRecycler.smoothScrollToPosition(count[0]);
                    }

                    if (handler != null) {
                        handler.postDelayed(this, scrollspeed[0]);
                    }

                }
            }
        };

        if (handler != null) {
            handler.postDelayed(runnable, scrollspeed[0]);
        }

    }

    private void InitializeProductFullImage(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            smallimageback1 = getDrawable(R.drawable.productsmallimagebackgrounprimary);
            indicatorimage1.setBackground(smallimageback1);//tint indicator 1 by default


        }
        list = new ArrayList<>();
        String fullimage1 = productdetail.get(0).getProductImagefirst();
        fullproductdetailimagemodel image1 = new fullproductdetailimagemodel(fullimage1);
        list.add(image1);
        if(!(productdetail.get(0).getProductImageSecond() == null)){//check if there is second image

            fullimage2 = productdetail.get(0).getProductImageSecond();
            image2 = new fullproductdetailimagemodel(fullimage2);
            list.add(image2);

            if(!(productdetail.get(0).getProductImageThird() == null)){//if there is second image check for third image
                Log.e("NOT NULL", productdetail.get(0).getProductImageThird());
                fullimage3 = productdetail.get(0).getProductImageThird();
                image3 = new fullproductdetailimagemodel(fullimage3);
                list.add(image3);
            }
            else{
                indicatorimage3.setVisibility(View.GONE);//if no third image then indicator 3 is gone
            }
        }
        else{
            indicatorimage2.setVisibility(View.GONE);//else if there is no second and third image indicator  2 and 3 gone
            indicatorimage3.setVisibility(View.GONE);
        }
        //if(!(productdetail.get(0).getProductImageThird() == null )){

        //  fullimage3 = productdetail.get(0).getProductImageThird();
        // image3 = new fullproductdetailimagemodel(fullimage3);
        //list.add(image3);
        //}
    }
    private void InitializeAdapter(){
        fulldetailadapter = new Fulldetailadapter(list);
        productimageRecycler.setAdapter(fulldetailadapter);
    }

    private void InitializePrice(){
        Locale NigerianLocale = new Locale("en","ng");
        if(productdetail.get(0).getProductReducedPrice() == null){
            Log.e("redude", "InitializePrice: null" );
            DeprecatedPrice.setVisibility(View.GONE);
            percentdecrease.setVisibility(View.GONE);
            String productprice = productdetail.get(0).getProductPrice();
            String unFormattedrealPrice = NumberFormat.getCurrencyInstance(NigerianLocale).format(Integer.parseInt(productprice));
            String formattedrealPrice = unFormattedrealPrice.replaceAll("\\.00","");
            price.setTextColor(getResources().getColor(R.color.colorPrimary));
            price.setText(formattedrealPrice);
        }

        else{
            DeprecatedPrice.setVisibility(View.VISIBLE);
            percentdecrease.setVisibility(View.VISIBLE);
            String reducedproductprice = productdetail.get(0).getProductReducedPrice();
            String unFormattedreducedPrice = NumberFormat.getCurrencyInstance(NigerianLocale).format(Integer.parseInt(reducedproductprice));
            String formattedrealreducedPrice = unFormattedreducedPrice.replaceAll("\\.00","");
            String productprice = productdetail.get(0).getProductPrice();
            String unFormattedrealPrice = NumberFormat.getCurrencyInstance(NigerianLocale).format(Integer.parseInt(productprice));
            String formattedrealPrice = unFormattedrealPrice.replaceAll("\\.00","");
            DeprecatedPrice.setTextColor(getResources().getColor(R.color.grey));
            DeprecatedPrice.setText(formattedrealPrice);
            price.setTextColor(getResources().getColor(R.color.colorPrimary));
            price.setText(formattedrealreducedPrice);
            DeprecatedPrice.setPaintFlags(price.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            String percentDecrease = productdetail.get(0).getPercentDecrease();
            percentdecrease.setText("-"+percentDecrease+"%");



        }    }
    private void InitializeView(){

        description = view.findViewById(R.id.fulldetailproductdescription);
        descriptionTitle = view.findViewById(R.id.fulldetailproductdescriptiontitle);
        price = view.findViewById(R.id.fulldetailproductprice);
        indicatorimage1 = view.findViewById(R.id.fulldetailproductsmallimagefirst);
        indicatorimage2 = view.findViewById(R.id.fulldetailproductsmallimagesecond);
        indicatorimage3 = view.findViewById(R.id.fulldetailproductsmallimagethird);
        DeprecatedPrice = view.findViewById(R.id.fulldetaildeprecatedprice);
        percentdecrease = view.findViewById(R.id.fulldetailpercentdecrease);
        productname = view.findViewById(R.id.productfulldetailname);
        customfont= Typeface.createFromAsset(getActivity().getAssets(),"Kylo-Light.otf");

        descriptionTitle.setTypeface(customfont);
        DeprecatedPrice.setTypeface(customfont);
        productname.setTypeface(customfont);
        productname.setText(productdetail.get(0).getProductName());
        price.setTypeface(customfont);
        percentdecrease.setTypeface(customfont);
        description.setTypeface(customfont);
    }
    @Override
    public void onDestroy() {
        handler = null;
        smallimageback1 = null;
        smallimageback2 = null;
        runnable = null;
        Log.e("destroy", "onDestroy: " );
        super.onDestroy();
    }

    @Override

    public void onStop() {
        handler = null;
        smallimageback1 = null;
        smallimageback2 = null;
        runnable = null;
        Log.e("stop", "onstop: " );
        super.onStop();
    }
}
*/