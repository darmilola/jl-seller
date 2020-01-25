package com.dammy.android.emarketvendor.Models;

public class viewordermodel {
    private String orderjson;
    private String deliveryfee;

  public viewordermodel(String orderjson,String deliveryfee){
        this.orderjson = orderjson;
        this.deliveryfee =deliveryfee;
    }

    public String getOrderjson() {
        return orderjson;
    }

    public String getDeliveryfee() {
        return deliveryfee;
    }

    public void setOrderjson(String orderjson) {
        this.orderjson = orderjson;
    }

    public void setDeliveryfee(String deliveryfee) {
        this.deliveryfee = deliveryfee;
    }
}
