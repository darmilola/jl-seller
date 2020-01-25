package com.dammy.android.emarketvendor;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dammy.android.emarketvendor.Fragments.openingpagefragment;

public class openingPageActivity extends AppCompatActivity {

    Button login,signup;
    TextView alreadyhaveaccount;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_page);
        InitializeView();
        if(savedInstanceState != null) {

            if (savedInstanceState.getInt("pid", -1) == android.os.Process.myPid()) {

            } else {

                startActivity(new Intent(this, splashscreen.class));

            }
        }
        SetOnclickListener();




    }

    private void SetOnclickListener(){

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(openingPageActivity.this, com.dammy.android.emarketvendor.Activities.login.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(openingPageActivity.this, com.dammy.android.emarketvendor.Activities.signup.class);
                startActivity(intent);
            }
        });

    }
    private void InitializeView(){
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = 1.0f;
        getWindow().setAttributes(layoutParams);
        //ActionBar actionBar = getSupportActionBar();
        // actionBar.hide();

        login = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);
        alreadyhaveaccount = (TextView) findViewById(R.id.alreadyhaveanaccount);

        Typeface customfont2= Typeface.createFromAsset(getAssets(),"Kylo-Light.otf");
        login.setTypeface(customfont2);
        signup.setTypeface(customfont2);
        alreadyhaveaccount.setTypeface(customfont2);
        Fragment fragment = new openingpagefragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.openingpagefragmentcontent,fragment);
        fragmentTransaction.commit();

    }







    @Override
    public void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        outstate.putInt("pid",android.os.Process.myPid());

    }

   @Override
    public void onBackPressed(){

   }

}

