package com.dammy.android.emarketvendor.Adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.Models.CategoryModel;

import java.util.ArrayList;


public class productcategoryadapter extends RecyclerView.Adapter<productcategoryadapter.itemviewholder> {
    ArrayList<CategoryModel> categoryModelArrayList;
    private ListItemClickListener mOnClickListener;
    public productcategoryadapter(ArrayList<CategoryModel> categoryModels,ListItemClickListener mOnClickListener) {
        this.categoryModelArrayList = categoryModels;
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public productcategoryadapter.itemviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productselectcategoryitem, parent, false);
        return new itemviewholder(view);
    }

    @Override
    public void onBindViewHolder(productcategoryadapter.itemviewholder holder, int position) {
        holder.CategoryName.setText(categoryModelArrayList.get(position).getCategoryName());
    }

    @Override
    public int getItemCount() {
        return categoryModelArrayList.size();
    }

   public  interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


    public class itemviewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView CategoryName;

        public itemviewholder(View itemView) {
            super(itemView);
            CategoryName = itemView.findViewById(R.id.productselectcategoryitemdisplayname);
            Typeface customfont2 = Typeface.createFromAsset(itemView.getContext().getAssets(), "Kylo-Light.otf");
            CategoryName.setTypeface(customfont2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

}
