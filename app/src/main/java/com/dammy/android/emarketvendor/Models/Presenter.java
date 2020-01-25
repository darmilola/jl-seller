package com.dammy.android.emarketvendor.Models;

import android.content.Context;
import android.util.Log;

import com.dammy.android.emarketvendor.interfaces.InteractorLayer;
import com.dammy.android.emarketvendor.interfaces.InteractorListener;
import com.dammy.android.emarketvendor.interfaces.PresenterLayer;
import com.dammy.android.emarketvendor.interfaces.ViewLayer;


import java.util.List;

public class Presenter implements PresenterLayer, InteractorListener {
    private InteractorLayer minteractorLayer;
    private ViewLayer mviewLayer;
    Context mcontext;
    String memail,useremail;

    public Presenter(ViewLayer viewLayer,Context context,String vendoremail){
        mviewLayer = viewLayer;
        this.mcontext = context;
        this.memail = vendoremail;
        minteractorLayer = new ProductsLoader(this,context,vendoremail);
    }

    @Override
    public void onProductsLoaded(int id, List<ProductModel> products) {
        mviewLayer.showProductsInCategory(id,products,this);
    }

    @Override
    public void onCategoriesLoaded(List<ProductCategory> categories) {
        List<ProductCategory> mcategoryList = categories;
        mviewLayer.showCategories(categories,this);
        for(int i = 0; i < categories.size(); i++){
            Log.e("category name", categories.get(i).getCategoryName() );
            minteractorLayer.getProducts(i,categories.get(i).getCategoryName());//i,for indexing,catname to load product for the category
        }
    }

    @Override
    public void onViewReady() {
     minteractorLayer.getCategories();

    }
}
