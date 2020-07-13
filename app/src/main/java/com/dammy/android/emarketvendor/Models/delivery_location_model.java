package com.dammy.android.emarketvendor.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class delivery_location_model implements Parcelable {

    private String city;
    private String area;
    private String vendor_email;

    public delivery_location_model(String city, String area, String vendor_email){

        this.city = city;
        this.area = area;
        this.vendor_email = vendor_email;
    }

    protected delivery_location_model(Parcel in) {
        city = in.readString();
        area = in.readString();
        vendor_email = in.readString();
    }

    public static final Creator<delivery_location_model> CREATOR = new Creator<delivery_location_model>() {
        @Override
        public delivery_location_model createFromParcel(Parcel in) {
            return new delivery_location_model(in);
        }

        @Override
        public delivery_location_model[] newArray(int size) {
            return new delivery_location_model[size];
        }
    };

    public String getCity() {
        return city;
    }

    public String getArea() {
        return area;
    }

    public String getVendor_email() {
        return vendor_email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setVendor_email(String vendor_email) {
        this.vendor_email = vendor_email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(city);
        parcel.writeString(area);
        parcel.writeString(vendor_email);
    }
}
