package com.dammy.android.emarketvendor.Activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.Fragments.deliverylocationArea;
import com.dammy.android.emarketvendor.Fragments.deliverylocationcity;
import com.dammy.android.emarketvendor.openingPageActivity;
import com.dammy.android.emarketvendor.splashscreen;

public class ChooseDeliveryLocation extends AppCompatActivity implements deliverylocationcity.onCitySelected,deliverylocationArea.onAreaSelected
{

   static ViewPager viewPager;
   String cityname,areaname;
   Double longitude,latitude;
   Toolbar toolbar;
   TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_delivery_location);
        if(savedInstanceState != null){

            if(savedInstanceState.getInt("pid",-1) == android.os.Process.myPid()){

            }
            else{

                startActivity(new Intent(ChooseDeliveryLocation.this,splashscreen.class));

            }
        }
        Initializeview();

    }

    private void Initializeview(){
        Fragment fragment =   deliverylocationcity.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.deliverylocationcontentframe, fragment);
        fragmentTransaction.commit();
        title = findViewById(R.id.deliverylocationtoolbartitle);
        toolbar = findViewById(R.id.deliverylocationtoolbar);
        Typeface customfont = Typeface.createFromAsset(getAssets(), "Kylo-Light.otf");
        title.setTypeface(customfont);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = 0.5f;
        getWindow().setAttributes(layoutParams);
    }
    @Override
    public void passCity(String data) {
       cityname = data;
       AuthSelection();
    }

    @Override
    public void passArea(String data,double log, double lat) {
        areaname = data;
        longitude = log;
        latitude = lat;
        AuthSelection();
    }
     private void AuthSelection(){
        if(cityname != null && areaname != null && longitude != 0 && latitude != 0){
            Intent intent = new Intent();
            intent.putExtra("city",cityname);
            intent.putExtra("area",areaname);
            intent.putExtra("longitude",longitude);
            intent.putExtra("latitude",latitude);
            intent.putExtra("flag1","flag1");
            setResult(1,intent);
            finish();
        }
        else{
            Fragment fragment =   deliverylocationArea.newInstance(cityname);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.exit_to_right,0 );
            fragmentTransaction.replace(R.id.deliverylocationcontentframe, fragment);
            fragmentTransaction.commit();

        }

    }

    @Override
    public void onBackPressed(){
        //super.onBackPressed();
        setResult(50);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == android.R.id.home) {

            this.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        outstate.putInt("pid",android.os.Process.myPid());

    }
}
