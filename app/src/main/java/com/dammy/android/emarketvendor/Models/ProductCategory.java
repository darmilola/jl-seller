package com.dammy.android.emarketvendor.Models;

import java.util.List;

public class ProductCategory {
    private String categoryName;
    private List<ProductModel> productList;
    private int progressbarvisibility;
    private int recyclervisibility;

    public   ProductCategory(String categoryName){
        this.categoryName = categoryName;
    }
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ProductModel> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductModel> productList) {
        this.productList = productList;
    }

    public int getRecyclervisibility() {
        return recyclervisibility;
    }

    public int getProgressbarvisibility() {
        return progressbarvisibility;
    }

    public void setRecyclervisibility(int recyclervisibility) {
        this.recyclervisibility = recyclervisibility;
    }

    public void setProgressbarvisibility(int progressbarvisibility) {
        this.progressbarvisibility = progressbarvisibility;
    }

}
