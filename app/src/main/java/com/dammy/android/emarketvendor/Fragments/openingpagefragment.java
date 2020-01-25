package com.dammy.android.emarketvendor.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ToxicBakery.viewpager.transforms.ZoomOutTranformer;
import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.Models.MyAutoScrollViewPager;


/**
 * A simple {@link Fragment} subclass.
 */
public class openingpagefragment extends Fragment {

    MyAutoScrollViewPager viewPager;




    View view;

    public openingpagefragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view =  inflater.inflate(R.layout.fragment_openingpagefragment, container, false);
        viewPager = (MyAutoScrollViewPager) view.findViewById(R.id.loginviewpager);

        viewPager.setPageTransformer(false,new ZoomOutTranformer());
        FragmentPagerAdapter  adapter=new LoginAdapter(getActivity().getSupportFragmentManager());

        Fragment fragment = new openingpagecontent();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.openingpagecontentfragment,fragment);
        fragmentTransaction.commit();
        viewPager.setAdapter(adapter);
        viewPager.setInterval(3000);
        viewPager.startAutoScroll();
        viewPager.setStopScrollWhenTouch(true);

        return view;

    }




    public class LoginAdapter extends FragmentPagerAdapter {
        int mNumofTabs;

        public LoginAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0:
                    return loginPageFirstFragment.newInstance();

                case 1:
                    return LoginPageSecondFragment.newInstance();

                case 2:
                    return loginPageThirdFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }



    @Override
    public void onResume(){

        super.onResume();
        viewPager.startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewPager.stopAutoScroll();
    }
}