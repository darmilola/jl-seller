package com.dammy.android.emarketvendor.Models;

public class vendordisplayitem {
    String vendorname;
    String deliveringTime;
    String category1;
    String category2;
    String category3;
    String vendorID;
    int VendorDisplayImage;

   public vendordisplayitem(String Vendorname, String deliveringtime, String category1,String category2,String category3, int DisplayImage){
        this.vendorname = Vendorname;
        this.deliveringTime = deliveringtime;
        this.category1 =category1;
        this.category2 =category2;
        this.category3 =category3;
        this.VendorDisplayImage = DisplayImage;
    }

    public String getCategory1() {
        return category1;
    }

    public int getVendorDisplayImage() {
        return VendorDisplayImage;
    }

    public String getCategory2() {
        return category2;
    }

    public String getCategory3() {
        return category3;
    }

    public String getDeliveringTime() {
        return deliveringTime;
    }

    public String getVendorID() {
        return vendorID;
    }

    public String getVendorname() {
        return vendorname;
    }

    public void setVendorDisplayImage(int vendorDisplayImage) {
        VendorDisplayImage = vendorDisplayImage;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public void setCategory3(String category3) {
        this.category3 = category3;
    }

    public void setDeliveringTime(String deliveringTime) {
        this.deliveringTime = deliveringTime;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public void setVendorname(String vendorname) {
        this.vendorname = vendorname;
    }
}
