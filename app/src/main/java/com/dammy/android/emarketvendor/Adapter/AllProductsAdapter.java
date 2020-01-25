package com.dammy.android.emarketvendor.Adapter;

import android.content.Context;
import android.graphics.Typeface;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dammy.android.emarketvendor.Models.ProductCategory;
import com.dammy.android.emarketvendor.Models.vendorprofile;
import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.Models.CategoryModel;
import com.dammy.android.emarketvendor.Models.ProductModel;
import com.dammy.android.emarketvendor.interfaces.PresenterLayer;


import java.util.ArrayList;
import java.util.List;


public class AllProductsAdapter extends RecyclerView.Adapter<AllProductsAdapter.ItemViewHolder> {

    private List<ProductCategory> categoryList;
    private PresenterLayer presenterLayer;
    Context context;
    ArrayList<vendorprofile> vendorprofiles;

    public AllProductsAdapter(List<ProductCategory> categoryList, PresenterLayer presenterLayer, Context context, ArrayList<vendorprofile> vendorprofiles){
        this.categoryList = categoryList;
        this.presenterLayer = presenterLayer;
        this.context = context;
        this.vendorprofiles = vendorprofiles;
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
     holder.setCategoryName(categoryList.get(position).getCategoryName());
     holder.setProductList(categoryList.get(position).getProductList());
     holder.setProgressbarvisibility(categoryList.get(position).getProgressbarvisibility());
     holder.setRecyclervis(categoryList.get(position).getRecyclervisibility());
    }

    public void addProductsToCategory(int categoryid,List<ProductModel> productList,int recyclervis,int progressvis){
        categoryList.get(categoryid).setProductList(productList);
        categoryList.get(categoryid).setProgressbarvisibility(progressvis);
        categoryList.get(categoryid).setRecyclervisibility(recyclervis);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView categoryNameview;
        RecyclerView productsrecycler;
        LinearLayout progressbarlayout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            categoryNameview = (TextView)itemView.findViewById(R.id.category_name);
            productsrecycler = (RecyclerView)itemView.findViewById(R.id.product_item_recyclerview);
            Typeface customfont2= Typeface.createFromAsset(itemView.getContext().getAssets(),"Roboto-Regular.ttf");
            categoryNameview.setTypeface(customfont2);
            progressbarlayout = itemView.findViewById(R.id.progressbarlayout);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.HORIZONTAL,false);
            //linearLayoutManager.setAutoMeasureEnabled(true);
           // productsrecycler.setNestedScrollingEnabled(false);
            productsrecycler.setLayoutManager(linearLayoutManager);

            productsrecycler.setItemAnimator(new DefaultItemAnimator());
            productsrecycler.setAdapter(new ProductsAdapter(new ArrayList<ProductModel>(),presenterLayer,context,vendorprofiles));
        }


        public void setCategoryName(String categoryName) {
            categoryNameview.setText(categoryName);
        }


        public void setProductList(List<ProductModel> productList) {

            ProductsAdapter productsAdapter = (ProductsAdapter)productsrecycler.getAdapter();
            productsAdapter.setProductList(productList);
            productsAdapter.notifyDataSetChanged();
        }

        public void setProgressbarvisibility(int progressbarvalue){

            progressbarlayout.setVisibility(progressbarvalue);
        }

        public void setRecyclervis(int value){
            productsrecycler.setVisibility(value);
        }
    }
}
