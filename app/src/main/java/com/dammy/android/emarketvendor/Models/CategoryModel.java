package com.dammy.android.emarketvendor.Models;

import java.util.List;

public class CategoryModel {
    private String categoryName;
    private List<ProductModel> productList;

    public CategoryModel(String name) {
        this.categoryName = name;
    }

    public CategoryModel() {

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
}
