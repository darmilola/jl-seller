package com.dammy.android.emarketvendor.Models;

public class DeliveryLocationModel {

    String Cityname;
    String Area;

    public DeliveryLocationModel(String Cityname,String Area){
        this.Cityname = Cityname;
        this.Area = Area;
    }

    public String getArea() {
        return Area;
    }

    public String getCityname() {
        return Cityname;
    }

    public void setArea(String area) {
        Area = area;
    }

    public void setCityname(String cityname) {
        Cityname = cityname;
    }
}
