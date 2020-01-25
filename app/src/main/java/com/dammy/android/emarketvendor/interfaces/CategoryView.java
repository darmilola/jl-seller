package com.dammy.android.emarketvendor.interfaces;

import com.dammy.android.emarketvendor.Models.ProductModel;

import java.util.List;

public interface CategoryView {
    void setCategoryName(String categoryName);
    void setProductList(List<ProductModel> productList);
}
