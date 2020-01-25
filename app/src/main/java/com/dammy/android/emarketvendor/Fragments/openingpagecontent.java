package com.dammy.android.emarketvendor.Fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dammy.android.emarketvendor.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class openingpagecontent extends Fragment {

    TextView welcome;
    View view;
    public openingpagecontent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

                view = inflater.inflate(R.layout.fragment_openingpagecontent, container, false);
                welcome = view.findViewById(R.id.welcometext);

              Typeface customfont= Typeface.createFromAsset(getActivity().getAssets(),"Poet.ttf");
              welcome.setTypeface(customfont);
                return view;
    }






}
