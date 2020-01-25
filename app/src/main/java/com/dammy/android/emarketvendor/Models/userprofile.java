package com.dammy.android.emarketvendor.Models;

public class userprofile {

    private String address;
    private double lat;
    private double log;
    private String city;
    private String area;
    private String phone;

   public userprofile(String address,double lat,double log,String city,String area,String phone){
       this.address = address;
       this.lat = lat;
       this.log = log;
       this.city = city;
       this.area = area;
       this.phone = phone;
   }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getArea() {
        return area;
    }

    public double getLat() {
        return lat;
    }

    public double getLog() {
        return log;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLog(double log) {
        this.log = log;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setArea(String area) {
        this.area = area;
    }


}
