package com.dammy.android.emarketvendor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dammy.android.emarketvendor.Activities.addnewProduct;
import com.dammy.android.emarketvendor.Models.vendorprofile;
import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.Activities.ProductFullDetail;
import com.dammy.android.emarketvendor.Models.ProductModel;
import com.dammy.android.emarketvendor.interfaces.PresenterLayer;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.request.ImageRequest;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ProductModel> productList;
    private PresenterLayer presenterLayer;
    public Context context;
    ArrayList<vendorprofile> vendorprofiles;
    Intent serviceintent;
    String userid,productname,productprice,firstimage,secondimage,reducedprice,description;

    public ProductsAdapter(List<ProductModel> productList, PresenterLayer presenterLayer,Context context,ArrayList<vendorprofile> vendorprofiles) {
        this.productList = productList;
        this.presenterLayer = presenterLayer;
        this.context = context;
        this.vendorprofiles = vendorprofiles;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
                return new ItemViewHolder(view);
            case 2:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_with_percentdecrease, parent, false);
                return new ItemViewHolderWithPercentDecrease(view2);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder ViewHolder, int position) {

        switch (ViewHolder.getItemViewType()) {
            case 1:
                ItemViewHolder itemViewHolder = (ItemViewHolder) ViewHolder;

                if (productList != null) {
                    itemViewHolder.productName.setText(productList.get(position).getProductName());

                    //String Price = productList.get(position).getoldProductPrice();
                    Locale NigerianLocale = new Locale("en", "ng");

                    String imagefirstUri = productList.get(position).getProductImagefirst();

                    /*Uri imageuri = Uri.parse(imagefirstUri);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.placeholder(R.drawable.marketpic);
                    requestOptions.error(R.drawable.marketpic);
                    Glide.with(itemViewHolder.itemView.getContext())
                            .setDefaultRequestOptions(requestOptions)
                            .load(imageuri)
                            .apply(RequestOptions.skipMemoryCacheOf(true))
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            .into(itemViewHolder.productImagefirst);
                     */

                    Uri imageuri = Uri.parse(imagefirstUri);
                    ImageRequest request = ImageRequest.fromUri(imageuri);
                    DraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setImageRequest(request)
                            .setOldController(itemViewHolder.productImagefirst.getController()).build();
                    itemViewHolder.productImagefirst.setController(controller);



                    //itemViewHolder.productImagefirst.setImageResource(productList.get(position).getProductImagefirst());
                    String productprice = productList.get(position).getoldProductPrice();
                    String unFormattedrealPrice = NumberFormat.getCurrencyInstance(NigerianLocale).format(Integer.parseInt(productprice));
                    String formattedrealPrice = unFormattedrealPrice.replaceAll("\\.00","");
                    itemViewHolder.productPrice.setText(formattedrealPrice);


                }
                break;
            case 2:
                ItemViewHolderWithPercentDecrease itemViewHolderWithPercentDecrease = (ItemViewHolderWithPercentDecrease) ViewHolder;

                if(productList != null){
                    itemViewHolderWithPercentDecrease.productName.setText(productList.get(position).getProductName());

                    Locale NigerianLocale = new Locale("en", "ng");
                    String reducedprice = productList.get(position).getProductnewPrice();
                    String DeprecatedPrice = productList.get(position).getoldProductPrice();
                    String unFormattedreducedPrice = NumberFormat.getCurrencyInstance(NigerianLocale).format(Integer.parseInt(reducedprice));
                    String formattedreducedPrice = unFormattedreducedPrice.replaceAll("\\.00","");
                    String unFormattedDeprecatedPrice = NumberFormat.getCurrencyInstance(NigerianLocale).format(Integer.parseInt(DeprecatedPrice));
                    String formattedDeprecatedPrice = unFormattedDeprecatedPrice.replaceAll("\\.00","");
                    itemViewHolderWithPercentDecrease.productPrice.setText(formattedreducedPrice);
                    itemViewHolderWithPercentDecrease.productDeprecatedPrice.setText(formattedDeprecatedPrice);
                    itemViewHolderWithPercentDecrease.productDeprecatedPrice.setPaintFlags(itemViewHolderWithPercentDecrease.productDeprecatedPrice.getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);

                    Double newPrice = Double.parseDouble(reducedprice);
                    Double oldPrice = Double.parseDouble(DeprecatedPrice);
                    Double decrease = oldPrice - newPrice;
                    String percentDecrease = Integer.toString((int) ((decrease/oldPrice)*100));
                    productList.get(position).setPercentDecrease(percentDecrease);
                    itemViewHolderWithPercentDecrease.percentDecrease.setText("-"+productList.get(position).getPercentDecrease()+"%");


                    String imagefirstUri = productList.get(position).getProductImagefirst();

                   /* Uri imageuri = Uri.parse(imagefirstUri);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.placeholder(R.drawable.marketpic);
                    requestOptions.error(R.drawable.marketpic);
                    Glide.with(itemViewHolderWithPercentDecrease.itemView.getContext())
                            .setDefaultRequestOptions(requestOptions)
                            .load(imageuri)
                            .apply(RequestOptions.skipMemoryCacheOf(true))
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            .into(itemViewHolderWithPercentDecrease.productImagefirst);
                        */

                    Uri imageuri = Uri.parse(imagefirstUri);

                    ImageRequest request = ImageRequest.fromUri(imageuri);
                    DraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setImageRequest(request)
                            .setOldController(itemViewHolderWithPercentDecrease.productImagefirst.getController()).build();
                    itemViewHolderWithPercentDecrease.productImagefirst.setController(controller);


                }
                break;
        }

    }

    @Override
    public int getItemViewType(int position){
        if(productList.get(position).getViewType() == "1"){
            return 1;
        }
        else if(productList.get(position).getViewType() == "2"){
            return 2;
        }
        return 0;
    }







    @Override
    public int getItemCount() {
        if(productList == null){
            return 0;
        }
        else {
            return productList.size();
        }

    }

    public void setProductList(List<ProductModel> productList){
        this.productList = productList;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public SimpleDraweeView productImagefirst;

        public TextView productName;

        public TextView productPrice;
        public Button OrderButton;

        public LinearLayout parentLayout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            productImagefirst = itemView.findViewById(R.id.vendorproductimage);
            productName = (TextView) itemView.findViewById(R.id.vendorproductnametext);

            productPrice = (TextView) itemView.findViewById(R.id.vendorproductpricetext);
            OrderButton = (Button) itemView.findViewById(R.id.edititembutton);
            parentLayout = (LinearLayout) itemView.findViewById(R.id.itemproductparentlayout);

            Typeface customfont2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Roboto-Regular.ttf");
            productPrice.setTypeface(customfont2);
            OrderButton.setTypeface(customfont2);

            productName.setTypeface(customfont2);

            parentLayout.setOnClickListener(this);
            OrderButton.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.edititembutton) {
                int i = getAdapterPosition();

                ProductModel current = productList.get(i);
                String productName = current.getProductName();
                String productDescription = current.getProductDescription();
                String productPrice = current.getoldProductPrice();
                String productid = current.getProductid();
                String vendoremail = current.getUseremail();

                Intent intent = new Intent(v.getContext(),addnewProduct.class);
                intent.putExtra("editproduct","editproduct");
                intent.putExtra("productname",productName);
                intent.putExtra("description",productDescription);
                intent.putExtra("oldprice",productPrice);
                intent.putExtra("id",productid);
                intent.putExtra("email",vendoremail);
                intent.putExtra("vendorprofile",vendorprofiles);
                Log.e("id", productid );
                v.getContext().startActivity(intent);




            } else {

                String reducedprice;
                //get product send arraylist to detail
                int i = getAdapterPosition();
                String productImageFirst, productImageSecond;
                ArrayList<ProductModel> productModels = new ArrayList<>();
                ProductModel model;
                Intent intent = new Intent(v.getContext(), ProductFullDetail.class);
                ProductModel current = productList.get(i);
                String productName = current.getProductName();
                String productDescription = current.getProductDescription();
                String productPrice = current.getoldProductPrice();
                productImageFirst = current.getProductImagefirst();


                model = new ProductModel(productDescription, productName, productPrice, productImageFirst);
                if (!(current.getProductImageSecond()== null)) {

                    productImageSecond = current.getProductImageSecond();
                    model = new ProductModel(productDescription, productName, productPrice, productImageFirst, productImageSecond);


                    //if (!(current.getProductImageThird() == null)) {
                    ///  productImageThird = current.getProductImageThird();
                    //model = new ProductModel(productDescription, productName, productPrice, productImageFirst, productImageSecond, productImageThird);


                    //}

                }
               // Log.e("useremail", current.getUseremail());
                //model.setUseremail(current.getUseremail());
                //model.setProductnewPrice(current.getProductnewPrice());
                model.setProductid(current.getProductid());
                model.setAvailability(current.getAvailability());
                productModels.add(model);
                intent.putParcelableArrayListExtra("productlist", productModels);
                intent.putParcelableArrayListExtra("vendorprofile",vendorprofiles);
                v.getContext().startActivity(intent);


            }
        }

    }


    public class ItemViewHolderWithPercentDecrease extends RecyclerView.ViewHolder implements View.OnClickListener{


        public SimpleDraweeView productImagefirst;

        public TextView productName;

        public TextView productPrice;
        public Button OrderButton;
        public TextView productDeprecatedPrice;
        public LinearLayout parentLayout;
        public TextView percentDecrease;
        public ItemViewHolderWithPercentDecrease(View itemView) {
            super(itemView);
            productImagefirst = itemView.findViewById(R.id.vendorproductimage);
            productName = (TextView)itemView.findViewById(R.id.vendorproductnametext);

            productPrice = (TextView)itemView.findViewById(R.id.vendorproductpricetext);
            OrderButton = (Button)itemView.findViewById(R.id.edititemwithpercentdecreasebutton);
            parentLayout = (LinearLayout)itemView.findViewById(R.id.itemproductparentlayout);
            productDeprecatedPrice = (TextView)itemView.findViewById(R.id.vendorproductdeprecatedpricetext);
            percentDecrease = (TextView)itemView.findViewById(R.id.percentdecrease);
            Typeface customfont2= Typeface.createFromAsset(itemView.getContext().getAssets(),"Roboto-Regular.ttf");
            productPrice.setTypeface(customfont2);
            OrderButton.setTypeface(customfont2);

            productName.setTypeface(customfont2);
            productDeprecatedPrice.setTypeface(customfont2);
            percentDecrease.setTypeface(customfont2);
            parentLayout.setOnClickListener(this);
            OrderButton.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {


            if(v.getId() == R.id.edititemwithpercentdecreasebutton){
                int i = getAdapterPosition();

                ProductModel current = productList.get(i);
                String productName = current.getProductName();
                String productDescription = current.getProductDescription();
                String productPrice = current.getoldProductPrice();
                String reducedprice = current.getProductnewPrice();
                String vendoremail = current.getUseremail();

                String productid = current.getProductid();
                Intent intent = new Intent(v.getContext(),addnewProduct.class);
                intent.putExtra("editproduct","editproduct");
                intent.putExtra("productname",productName);
                intent.putExtra("description",productDescription);
                intent.putExtra("oldprice",productPrice);
                intent.putExtra("newprice",reducedprice);
                intent.putExtra("id",productid);
                intent.putExtra("vendorprofile",vendorprofiles);
                intent.putExtra("email",vendoremail);

                v.getContext().startActivity(intent);


            }
            else {


                String reducedprice;
                //get product send arraylist to detail
                int i = getAdapterPosition();
                String productImageFirst, productImageSecond, productImageThird;
                ArrayList<ProductModel> productModels = new ArrayList<>();
                ProductModel model;
                Intent intent = new Intent(v.getContext(), ProductFullDetail.class);
                ProductModel current = productList.get(i);
                String productName = current.getProductName();
                String productDescription = current.getProductDescription();
                String productPrice = current.getoldProductPrice();

                productImageFirst = current.getProductImagefirst();


                model = new ProductModel(productDescription,productName,productPrice,productImageFirst);
                if(!(current.getProductImageSecond() == null)){

                    productImageSecond = current.getProductImageSecond();
                    model = new ProductModel(productDescription,productName,productPrice,productImageFirst,productImageSecond);
                    }

                reducedprice = current.getProductnewPrice();
                model.setProductnewPrice(reducedprice);
                String percentdecrease = current.getPercentDecrease();
                model.setPercentDecrease(percentdecrease);
                model.setUseremail(current.getUseremail());
                model.setProductid(current.getProductid());
                model.setAvailability(current.getAvailability());

                productModels.add(model);
                intent.putParcelableArrayListExtra("productlist",productModels);
                intent.putParcelableArrayListExtra("vendorprofile",vendorprofiles);
                v.getContext().startActivity(intent);



            }

        }
    }
}


