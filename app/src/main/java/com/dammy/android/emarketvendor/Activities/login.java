package com.dammy.android.emarketvendor.Activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.Fragments.Login;
import com.dammy.android.emarketvendor.Models.BlurBuilder;
import com.dammy.android.emarketvendor.openingPageActivity;
import com.dammy.android.emarketvendor.splashscreen;

public class login extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeView();


    }


    private void initializeView(){

        container = (LinearLayout) findViewById(R.id.container);
        Bitmap original = BitmapFactory.decodeResource(getResources(),R.drawable.womansecondimage);
        Bitmap blurr = BlurBuilder.blur(login.this,original);
        container.setBackground(new BitmapDrawable(getResources(),blurr));
        Fragment fragment = new Login();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.logincontentframe,fragment);
        fragmentTransaction.commit();
        toolbar = (Toolbar) findViewById(R.id.loginpagetoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        if(menuItem.getItemId() == android.R.id.home){

           finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }




    }

