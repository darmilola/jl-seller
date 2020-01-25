package com.dammy.android.emarketvendor.interfaces;

import com.dammy.android.emarketvendor.Models.CategoryModel;
import com.dammy.android.emarketvendor.Models.ProductCategory;
import com.dammy.android.emarketvendor.Models.ProductModel;


import java.util.List;

public interface InteractorListener {
    void onProductsLoaded(int id, List<ProductModel> products);
    void onCategoriesLoaded(List<ProductCategory> categories);
}
