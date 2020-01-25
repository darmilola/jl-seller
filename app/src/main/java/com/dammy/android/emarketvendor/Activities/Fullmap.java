package com.dammy.android.emarketvendor.Activities;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.openingPageActivity;
import com.dammy.android.emarketvendor.splashscreen;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.*;

public class Fullmap extends AppCompatActivity {

    GoogleMap googleMap;
    Button setPin;
    double longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullmap);
        if(savedInstanceState != null){

            if(savedInstanceState.getInt("pid",-1) == android.os.Process.myPid()){

            }
            else{

                startActivity(new Intent(Fullmap.this,splashscreen.class));

            }
        }
        latitude = getIntent().getDoubleExtra("userlat",0);
        longitude = getIntent().getDoubleExtra("userlong",0);

        if(latitude != 0  && longitude != 0){
            LatLng latLng = new LatLng(latitude,longitude);
            Log.e("not hesere", "onCreate: omg" );
            //Toast.makeText(this, Double.toString(latitude)+" "+Double.toString(longitude), Toast.LENGTH_SHORT).show();

            InitializeMapuser(latLng);//user after selecting position click on map already have long and lat fromlocation
        }else{
            InitializeMap();//no long and lat first time
            Log.e("not ", "o" );


        }
        InitializeView();
        setOnclickListener();

    }


    private void setOnclickListener(){

        setPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);
                intent.putExtra("flagfrommapselect","flag");
                setResult(1,intent);
                finish();

            }
        });
    }

    private void InitializeMapuser(LatLng latLng1){
        if(googleMap == null){

            ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.setupfullmap)).getMapAsync(Map -> {
                googleMap = Map;
                final MarkerOptions markerOptions = new MarkerOptions()
                        .position(latLng1)
                        .title("Location");
                Marker m = googleMap.addMarker(markerOptions);

                m.setPosition(latLng1);
                //  CameraUpdate current = CameraUpdateFactory.newLatLngZoom(new LatLng(6.4253,3.4219),15);
                //googleMap.animateCamera(current);

                CameraUpdate current =  CameraUpdateFactory.newLatLngZoom(latLng1,15);
                googleMap.animateCamera(CameraUpdateFactory.zoomIn());
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15),4000,null);
                googleMap.animateCamera(current);
                //moveTocurrentposition(new LatLng(6.4253,3.4219));

                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.setPadding(10,10,10,90);

                googleMap.setOnMapClickListener(latLng -> {
                    googleMap.clear();
                    moveTocurrentposition(latLng);


              //      Toast.makeText(this, Double.toString(latLng.latitude)+" "+Double.toString(latLng.longitude), Toast.LENGTH_SHORT).show();
                    animateMarker(m,latLng,false);

                    Marker m2 = googleMap.addMarker(markerOptions);
                    m2.setPosition(latLng);
                    latitude = latLng.latitude;  //asign long and lat to latiutde and longitude so as ro send to set up shop
                    longitude = latLng.longitude;
                    //Toast.makeText(Fullmap.this,lat+" "+log,Toast.LENGTH_LONG).show();
                });


            });
        }
    }

    private void InitializeMap(){
        if(googleMap == null){

            ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.setupfullmap)).getMapAsync((GoogleMap Map) -> {
                googleMap = Map;
                final MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(6.43,3.4219))
                        .title("Location");
                Marker m = googleMap.addMarker(markerOptions);

                m.setPosition(new LatLng(6.4253,3.4219));
              //  CameraUpdate current = CameraUpdateFactory.newLatLngZoom(new LatLng(6.4253,3.4219),15);
                //googleMap.animateCamera(current);

                CameraUpdate current =  CameraUpdateFactory.newLatLngZoom(new LatLng(6.4253,3.4219),15);
                googleMap.animateCamera(CameraUpdateFactory.zoomIn());
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15),4000,null);
                googleMap.animateCamera(current);
                //moveTocurrentposition(new LatLng(6.4253,3.4219));

                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.setPadding(10,10,10,90);

                googleMap.setOnMapClickListener((LatLng latLng) -> {
                    googleMap.clear();
                    moveTocurrentposition(latLng);
                   animateMarker(m,latLng,false);
                    Marker m2 = googleMap.addMarker(markerOptions);
                    m2.setPosition(latLng);
                   latitude = latLng.latitude;//asign long amd lat to long so as to send to setup shop
                   longitude = latLng.longitude;

                });


            });
        }
    }
   private void animateMarker(final Marker marker,final LatLng toposition,final boolean hideMarker){

        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
       Projection projection = googleMap.getProjection();
       Point startPoint = projection.toScreenLocation(marker.getPosition());
       final LatLng  startLatlng = projection.fromScreenLocation(startPoint);
       long duration = 1000;
       final LinearInterpolator interpolator = new LinearInterpolator();
       handler.post(new Runnable() {
           @Override
           public void run() {
               long elapsed = SystemClock.uptimeMillis()- start;
               float t = interpolator.getInterpolation((float)elapsed/duration);

               double lng = t * toposition.longitude + (1-t) * startLatlng.longitude;
               double lat = t * toposition.latitude +(1-t) * startLatlng.latitude;
               marker.setPosition(new LatLng(lat,lng));
               if(t<1.0){
                   handler.postDelayed(this,16);
                   }
                   else {
                  if(hideMarker){
                      marker.setVisible(false);
                  }
                  else{
                      marker.setVisible(true);
                  }
               }

               }
       });
   }

   private void InitializeView(){
       WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
       layoutParams.screenBrightness = 0.5f;
       getWindow().setAttributes(layoutParams);
       setPin = (Button) findViewById(R.id.setpinbutton);
       Typeface customfont= Typeface.createFromAsset(getAssets(),"Kylo-Light.otf");
       setPin.setTypeface(customfont);
   }



    private void moveTocurrentposition(LatLng currentlocation){
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentlocation,15));
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15),2000,null);
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
