package com.dammy.android.emarketvendor.Fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dammy.android.emarketvendor.Activities.SetUpShop;
import com.dammy.android.emarketvendor.Activities.Showdeliverylocation;
import com.dammy.android.emarketvendor.Activities.Showproductcategory;
import com.dammy.android.emarketvendor.Models.vendorprofile;
import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.Activities.GettingStartedActivity;
import com.dammy.android.emarketvendor.splashscreen;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class help_fragment extends Fragment {

     View view;
     TextView gettingStarted,editshopinfo,addproductcategory,adddeliverylocation;
     static ArrayList<vendorprofile> vendorprofiles;
    public help_fragment() {
        // Required empty public constructor
    }


    public static help_fragment newInstance(ArrayList<vendorprofile> mvendorprofiles){
        vendorprofiles = mvendorprofiles;
        help_fragment help_fragment = new help_fragment();
        return help_fragment;
    }
    @Override
    public void onActivityCreated( Bundle savedinstance){
        super.onActivityCreated(savedinstance);
        if(vendorprofiles == null){
            Intent intent = new Intent(getContext(),splashscreen.class);
            startActivity(intent);
            return;
        }
        else {
            InitializeView();
            setOnclickListener();

        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

               view =  inflater.inflate(R.layout.fragment_help_fragment, container, false);

               return view;
    }

    private void InitializeView(){
        gettingStarted = view.findViewById(R.id.gettingstarted);

        editshopinfo = view.findViewById(R.id.editshopinfo);

        adddeliverylocation = view.findViewById(R.id.adddeliverylocation);
        addproductcategory = view.findViewById(R.id.addproductcategory);
        Typeface customfont= Typeface.createFromAsset(getActivity().getAssets(),"Kylo-Light.otf");
        gettingStarted.setTypeface(customfont);

        editshopinfo.setTypeface(customfont);
        adddeliverylocation.setTypeface(customfont);
        addproductcategory.setTypeface(customfont);




    }

    private void setOnclickListener(){
        gettingStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GettingStartedActivity.class);
                //intent.putParcelableArrayListExtra("vendorprofilefromhelp",vendorprofiles);
                startActivity(intent);
            }
        });

        editshopinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),SetUpShop.class);
                intent.putParcelableArrayListExtra("vendorprofilefromhelp",vendorprofiles);
                intent.putExtra("email",vendorprofiles.get(0).getEmail());
                startActivity(intent);
            }
        });

        addproductcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Showproductcategory.class);
                intent.putParcelableArrayListExtra("vendorprofilefromhelp",vendorprofiles);
                startActivity(intent);
            }
        });

        adddeliverylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Showdeliverylocation.class);
                intent.putParcelableArrayListExtra("vendorprofilefromhelp",vendorprofiles);
                startActivity(intent);
            }
        });
    }

}
