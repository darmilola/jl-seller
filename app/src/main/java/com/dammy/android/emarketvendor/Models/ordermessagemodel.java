package com.dammy.android.emarketvendor.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class ordermessagemodel implements Parcelable {

    private String Orderid;
    private String userid;

    public ordermessagemodel(String Orderid,String userid){
        this.Orderid = Orderid;
        this.userid = userid;
    }

    protected ordermessagemodel(Parcel in) {
        Orderid = in.readString();
        userid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Orderid);
        dest.writeString(userid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ordermessagemodel> CREATOR = new Creator<ordermessagemodel>() {
        @Override
        public ordermessagemodel createFromParcel(Parcel in) {
            return new ordermessagemodel(in);
        }

        @Override
        public ordermessagemodel[] newArray(int size) {
            return new ordermessagemodel[size];
        }
    };

    public String getOrderid() {
        return Orderid;
    }

    public void setOrderid(String orderid) {
        Orderid = orderid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
