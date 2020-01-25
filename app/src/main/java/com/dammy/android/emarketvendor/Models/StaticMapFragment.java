package com.dammy.android.emarketvendor.Models;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.maps.SupportMapFragment;

public class StaticMapFragment extends SupportMapFragment {

    public View mapView;
    public TouchableWrapper touchView;
    private StaticMapFragment.OnTouchListener listener;

    public static StaticMapFragment newInstance(){
        return  new StaticMapFragment();
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstance){

        mapView = super.onCreateView(inflater,parent,savedInstance);
        touchView = new TouchableWrapper(getActivity());
        touchView.addView(mapView);
        return touchView;
    }

    @Override
    public View getView(){
        return mapView;
    }

  public void setOntouchListener(StaticMapFragment.OnTouchListener listener){
        this.listener = listener;
  }
    public  interface OnTouchListener {
        void onTouch();
    }

    class TouchableWrapper extends FrameLayout {
        public TouchableWrapper(@NonNull Context context) {
            super(context);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent event){

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if(listener != null){
                        listener.onTouch();
                    }
                    return true;
            }
            return super.dispatchTouchEvent(event);
        }
    }

}
