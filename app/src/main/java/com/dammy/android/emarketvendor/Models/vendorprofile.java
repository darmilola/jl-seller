package com.dammy.android.emarketvendor.Models;
import android.os.Parcel;
import android.os.Parcelable;

public class vendorprofile implements Parcelable {

    private String firstname;
    private String lastname;
    private String email;
    private String phonenumber;
    private String profileimage;
    private String displayname;
    private String deliveryminute;
    private String minimumorder;
    private String deliveryfee;
    private String category1;
    private String category2;
    private String category3;
    private String category4;
    private String category5;
    private String category6;
    private String category7;
    private String category8;
    private String category9;
    private String category10;
    private String paymentstate;
    private String openportal;
    private int priceofgoodssold;
    private String deliverycity1;
    private String deliverycity2;
    private String deliverycity3;
    private String deliverycity4;
    private String deliverycity5;
    private String deliverycity6;
    private String deliverycity7;
    private String deliverycity8;
    private String deliverycity9;
    private String deliverycity10;
    private String deliveryarea1;
    private String deliveryarea2;
    private String deliveryarea3;
    private String deliveryarea4;
    private String deliveryarea5;
    private String deliveryarea6;
    private String deliveryarea7;
    private String deliveryarea8;
    private String deliveryarea9;
    private String deliveryarea10;
    private String password;
    private String shopopen;



    public vendorprofile(){


    }
        public vendorprofile(String firstname, String lastname, String email, String phonenumber, String profileimage){
        this.firstname = firstname;
        this.lastname =  lastname;
        this.email = email;
        this.phonenumber = phonenumber;
        this.profileimage = profileimage;
    }


    protected vendorprofile(Parcel in) {
        firstname = in.readString();
        lastname = in.readString();
        email = in.readString();
        phonenumber = in.readString();
        profileimage = in.readString();
        displayname = in.readString();
        deliveryminute = in.readString();
        minimumorder = in.readString();
        deliveryfee = in.readString();
        category1 = in.readString();
        category2 = in.readString();
        category3 = in.readString();
        category4 = in.readString();
        category5 = in.readString();
        category6 = in.readString();
        category7 = in.readString();
        category8 = in.readString();
        category9 = in.readString();
        category10 = in.readString();
        paymentstate = in.readString();
        openportal = in.readString();
        priceofgoodssold = in.readInt();
        deliverycity1 = in.readString();
        deliverycity2 = in.readString();
        deliverycity3 = in.readString();
        deliverycity4 = in.readString();
        deliverycity5 = in.readString();
        deliverycity6 = in.readString();
        deliverycity7 = in.readString();
        deliverycity8 = in.readString();
        deliverycity9 = in.readString();
        deliverycity10 = in.readString();
        deliveryarea1 = in.readString();
        deliveryarea2 = in.readString();
        deliveryarea3 = in.readString();
        deliveryarea4 = in.readString();
        deliveryarea5 = in.readString();
        deliveryarea6 = in.readString();
        deliveryarea7 = in.readString();
        deliveryarea8 = in.readString();
        deliveryarea9 = in.readString();
        deliveryarea10 = in.readString();
        password = in.readString();
        shopopen = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(email);
        dest.writeString(phonenumber);
        dest.writeString(profileimage);
        dest.writeString(displayname);
        dest.writeString(deliveryminute);
        dest.writeString(minimumorder);
        dest.writeString(deliveryfee);
        dest.writeString(category1);
        dest.writeString(category2);
        dest.writeString(category3);
        dest.writeString(category4);
        dest.writeString(category5);
        dest.writeString(category6);
        dest.writeString(category7);
        dest.writeString(category8);
        dest.writeString(category9);
        dest.writeString(category10);
        dest.writeString(paymentstate);
        dest.writeString(openportal);
        dest.writeInt(priceofgoodssold);
        dest.writeString(deliverycity1);
        dest.writeString(deliverycity2);
        dest.writeString(deliverycity3);
        dest.writeString(deliverycity4);
        dest.writeString(deliverycity5);
        dest.writeString(deliverycity6);
        dest.writeString(deliverycity7);
        dest.writeString(deliverycity8);
        dest.writeString(deliverycity9);
        dest.writeString(deliverycity10);
        dest.writeString(deliveryarea1);
        dest.writeString(deliveryarea2);
        dest.writeString(deliveryarea3);
        dest.writeString(deliveryarea4);
        dest.writeString(deliveryarea5);
        dest.writeString(deliveryarea6);
        dest.writeString(deliveryarea7);
        dest.writeString(deliveryarea8);
        dest.writeString(deliveryarea9);
        dest.writeString(deliveryarea10);
        dest.writeString(password);
        dest.writeString(shopopen);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<vendorprofile> CREATOR = new Creator<vendorprofile>() {
        @Override
        public vendorprofile createFromParcel(Parcel in) {
            return new vendorprofile(in);
        }

        @Override
        public vendorprofile[] newArray(int size) {
            return new vendorprofile[size];
        }
    };

    public int getPriceofgoodssold() {
        return priceofgoodssold;
    }

    public void setPriceofgoodssold(int priceofgoodssold) {
        this.priceofgoodssold = priceofgoodssold;
    }

    public String getShopopen() {
        return shopopen;
    }

    public void setShopopen(String shopopen) {
        this.shopopen = shopopen;
    }

    public String getOpenportal() {
        return openportal;
    }

    public String getPaymentstate() {
        return paymentstate;
    }

    public void setOpenportal(String openportal) {
        this.openportal = openportal;
    }

    public void setPaymentstate(String paymentstate) {
        this.paymentstate = paymentstate;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public String getEmail() {
        return email;
    }

    public String getMinimumorder() {
        return minimumorder;
    }

    public String getDeliveryfee() {
        return deliveryfee;
    }

    public String getDeliveryminute() {
        return deliveryminute;
    }

    public String getCategory1() {
        return category1;
    }

    public String getDisplayname() {
        return displayname;
    }

    public String getCategory3() {
        return category3;
    }

    public String getCategory2() {
        return category2;
    }

    public String getCategory4() {
        return category4;
    }

    public String getCategory5() {
        return category5;
    }

    public void setDeliveryfee(String deliveryfee) {
        this.deliveryfee = deliveryfee;
    }

    public void setMinimumorder(String minimumorder) {
        this.minimumorder = minimumorder;
    }

    public void setDeliveryminute(String deliveryminute) {
        this.deliveryminute = deliveryminute;
    }

    public void setCategory3(String category3) {
        this.category3 = category3;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public void setCategory4(String category4) {
        this.category4 = category4;
    }

    public void setCategory5(String category5) {
        this.category5 = category5;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getCategory6() {
        return category6;
    }

    public String getCategory7() {
        return category7;
    }

    public String getCategory8() {
        return category8;
    }

    public String getCategory9() {
        return category9;
    }

    public String getCategory10() {
        return category10;
    }

    public void setCategory6(String category6) {
        this.category6 = category6;
    }

    public void setCategory7(String category7) {
        this.category7 = category7;
    }

    public void setCategory8(String category8) {
        this.category8 = category8;
    }

    public void setCategory9(String category9) {
        this.category9 = category9;
    }

    public void setCategory10(String category10) {
        this.category10 = category10;
    }

    public String getDeliveryarea1() {
        return deliveryarea1;
    }

    public String getDeliveryarea2() {
        return deliveryarea2;
    }

    public String getDeliveryarea3() {
        return deliveryarea3;
    }

    public String getDeliveryarea4() {
        return deliveryarea4;
    }

    public String getDeliveryarea5() {
        return deliveryarea5;
    }

    public String getDeliveryarea6() {
        return deliveryarea6;
    }

    public String getDeliveryarea7() {
        return deliveryarea7;
    }

    public String getDeliveryarea8() {
        return deliveryarea8;
    }

    public String getDeliveryarea9() {
        return deliveryarea9;
    }

    public String getDeliveryarea10() {
        return deliveryarea10;
    }

    public void setDeliveryarea1(String deliveryarea1) {
        this.deliveryarea1 = deliveryarea1;
    }

    public void setDeliveryarea2(String deliveryarea2) {
        this.deliveryarea2 = deliveryarea2;
    }

    public void setDeliveryarea3(String deliveryarea3) {
        this.deliveryarea3 = deliveryarea3;
    }

    public void setDeliveryarea4(String deliveryarea4) {
        this.deliveryarea4 = deliveryarea4;
    }

    public void setDeliveryarea5(String deliveryarea5) {
        this.deliveryarea5 = deliveryarea5;
    }

    public void setDeliveryarea6(String deliveryarea6) {
        this.deliveryarea6 = deliveryarea6;
    }

    public void setDeliveryarea7(String deliveryarea7) {
        this.deliveryarea7 = deliveryarea7;
    }

    public void setDeliveryarea8(String deliveryarea8) {
        this.deliveryarea8 = deliveryarea8;
    }

    public void setDeliveryarea9(String deliveryarea9) {
        this.deliveryarea9 = deliveryarea9;
    }

    public void setDeliveryarea10(String deliveryarea10) {
        this.deliveryarea10 = deliveryarea10;
    }

    public void setDeliverycity1(String deliverycity1) {
        this.deliverycity1 = deliverycity1;
    }

    public void setDeliverycity2(String deliverycity2) {
        this.deliverycity2 = deliverycity2;
    }

    public void setDeliverycity3(String deliverycity3) {
        this.deliverycity3 = deliverycity3;
    }

    public void setDeliverycity4(String deliverycity4) {
        this.deliverycity4 = deliverycity4;
    }

    public void setDeliverycity5(String deliverycity5) {
        this.deliverycity5 = deliverycity5;
    }

    public void setDeliverycity6(String deliverycity6) {
        this.deliverycity6 = deliverycity6;
    }

    public void setDeliverycity7(String deliverycity7) {
        this.deliverycity7 = deliverycity7;
    }

    public void setDeliverycity8(String deliverycity8) {
        this.deliverycity8 = deliverycity8;
    }

    public void setDeliverycity9(String deliverycity9) {
        this.deliverycity9 = deliverycity9;
    }

    public void setDeliverycity10(String deliverycity10) {
        this.deliverycity10 = deliverycity10;
    }

    public String getDeliverycity1() {
        return deliverycity1;
    }

    public String getDeliverycity2() {
        return deliverycity2;
    }

    public String getDeliverycity3() {
        return deliverycity3;
    }

    public String getDeliverycity4() {
        return deliverycity4;
    }

    public String getDeliverycity5() {
        return deliverycity5;
    }

    public String getDeliverycity6() {
        return deliverycity6;
    }

    public String getDeliverycity7() {
        return deliverycity7;
    }

    public String getDeliverycity8() {
        return deliverycity8;
    }

    public String getDeliverycity9() {
        return deliverycity9;
    }

    public String getDeliverycity10() {
        return deliverycity10;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
