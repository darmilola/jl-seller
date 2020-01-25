package com.dammy.android.emarketvendor.Adapter;

import android.graphics.Typeface;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.Models.DeliveryCity;

import java.util.ArrayList;
import java.util.List;

public class DeliveryCityAdapter extends RecyclerView.Adapter<DeliveryCityAdapter.itemViewHolder> {

    private List<DeliveryCity> cityList;
    final private ListItemClickListener mOnClickListener;



    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public DeliveryCityAdapter(ArrayList<DeliveryCity> deliveryCities,ListItemClickListener listItemClickListener){
        this.cityList = deliveryCities;


        mOnClickListener = listItemClickListener;
    }    @Override
    public DeliveryCityAdapter.itemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cityitem,parent,false);
        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DeliveryCityAdapter.itemViewHolder holder, int position) {
      holder.City.setText(cityList.get(position).getCityName());
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
      TextView City;



        public itemViewHolder(View itemView) {
            super(itemView);
            City = itemView.findViewById(R.id.deliverylocationcityitemname);
            Typeface customfont2= Typeface.createFromAsset(itemView.getContext().getAssets(),"Kylo-Light.otf");
            City.setTypeface(customfont2);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);


        }
    }
}
