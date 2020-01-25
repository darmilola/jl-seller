package com.dammy.android.emarketvendor.Adapter;


import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.Models.fullproductdetailimagemodel;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import java.util.List;

public class Fulldetailadapter extends RecyclerView.Adapter<Fulldetailadapter.FulldetailViewHolder> {
     List<fullproductdetailimagemodel> fullproductdetailimagemodelArrayList;

     public Fulldetailadapter(List<fullproductdetailimagemodel> fullproductdetailimagemodelList){
         this.fullproductdetailimagemodelArrayList = fullproductdetailimagemodelList;
     }

    @Override
    public FulldetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productdetailrecyclerviewitem,parent,false);
        return new FulldetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FulldetailViewHolder holder, int position) {

        Uri imageuri = Uri.parse(fullproductdetailimagemodelArrayList.get(position).getFullImage());
        /*RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.marketpic);
        requestOptions.error(R.drawable.marketpic);
        Glide.with(holder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(imageuri)
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(holder.fullproductimage);*/


        ImageRequest request = ImageRequest.fromUri(imageuri);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.fullproductimage.getController()).build();
        holder.fullproductimage.setController(controller);

    }

    @Override
    public int getItemCount() {
        return fullproductdetailimagemodelArrayList.size();
    }

    public class FulldetailViewHolder extends RecyclerView.ViewHolder {
         SimpleDraweeView fullproductimage;
        public FulldetailViewHolder(View itemView) {
            super(itemView);
            fullproductimage = itemView.findViewById(R.id.recyclerviewfulldetailimage);

        }
    }
}
