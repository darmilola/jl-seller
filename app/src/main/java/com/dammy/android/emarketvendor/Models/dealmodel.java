package com.dammy.android.emarketvendor.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class dealmodel implements Parcelable {
    private String dealdescription;
    private String dealID;

    public dealmodel(String dealdescription){
        this.dealdescription = dealdescription;
    }

    protected dealmodel(Parcel in) {
        dealdescription = in.readString();
        dealID = in.readString();
    }

    public static final Creator<dealmodel> CREATOR = new Creator<dealmodel>() {
        @Override
        public dealmodel createFromParcel(Parcel in) {
            return new dealmodel(in);
        }

        @Override
        public dealmodel[] newArray(int size) {
            return new dealmodel[size];
        }
    };

    public String getDealdescription() {
        return dealdescription;
    }

    public String getDealID() {
        return dealID;
    }

    public void setDealdescription(String dealdescription) {
        this.dealdescription = dealdescription;
    }

    public void setDealID(String dealID) {
        this.dealID = dealID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dealdescription);
        dest.writeString(dealID);
    }
}
