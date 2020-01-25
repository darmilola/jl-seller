package com.dammy.android.emarketvendor.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dammy.android.emarketvendor.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginPageSecondFragment extends Fragment {


    public LoginPageSecondFragment() {
        // Required empty public constructor
    }
  public static  LoginPageSecondFragment newInstance(){
        LoginPageSecondFragment   secondFragment = new LoginPageSecondFragment();
        return  secondFragment;
  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_page_second, container, false);
    }



    }




