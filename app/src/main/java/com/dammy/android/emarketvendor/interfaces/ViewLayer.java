package com.dammy.android.emarketvendor.interfaces;

import com.dammy.android.emarketvendor.Models.CategoryModel;
import com.dammy.android.emarketvendor.Models.ProductCategory;
import com.dammy.android.emarketvendor.Models.ProductModel;


import java.util.List;

public interface ViewLayer {

    void showProductsInCategory(int categoryid, List<ProductModel> productList, PresenterLayer presenter);
    void showCategories(List<ProductCategory> categoryItemList, PresenterLayer presenter);
}
