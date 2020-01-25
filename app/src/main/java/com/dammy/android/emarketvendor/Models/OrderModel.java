package com.dammy.android.emarketvendor.Models;

public class OrderModel {

    private String productname;
    private String price;
    private String productcount;
    private int countprice;

    public OrderModel(String productname, String price, String productcount, int countprice){
        this.productname = productname;
        this.price = price;
        this.productcount = productcount;
        this.countprice = countprice;
    }

    public String getProductcount() {
        return productcount;
    }

    public int getCountprice() {
        return countprice;
    }

    public String getProductname() {
        return productname;
    }

    public String getPrice() {
        return price;
    }

    public void setCountprice(int countprice) {
        this.countprice = countprice;
    }

    public void setProductcount(String productcount) {
        this.productcount = productcount;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
