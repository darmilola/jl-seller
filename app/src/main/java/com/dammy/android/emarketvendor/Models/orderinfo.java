package com.dammy.android.emarketvendor.Models;

public class orderinfo {
    private String paymentmethod;
    private String status;

    public  orderinfo(String deliverymethod,String status){
        this.paymentmethod = deliverymethod;
        this.status = status;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public String getStatus() {
        return status;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
