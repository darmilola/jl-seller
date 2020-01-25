package com.dammy.android.emarketvendor.Adapter;

import android.graphics.Typeface;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.Models.DeliveryArea;

import java.util.ArrayList;
import java.util.List;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.itemViewHolder> {


    List<DeliveryArea> areaList;
    private ListItemClickListener mOnClickListener;

    public AreaAdapter(ArrayList<DeliveryArea> areaList,ListItemClickListener mOnClickListener){
         this.areaList = areaList;
         this.mOnClickListener = mOnClickListener;
     }
    @Override
    public itemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.areaitem,parent,false);
        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(itemViewHolder holder, int position) {
         holder.AreaName.setText(areaList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return areaList.size();
    }


    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


    public class itemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView AreaName;
        public itemViewHolder(View itemView) {
            super(itemView);
            AreaName = (TextView)itemView.findViewById(R.id.areaname);
            Typeface customfont2= Typeface.createFromAsset(itemView.getContext().getAssets(),"Kylo-Light.otf");
            AreaName.setTypeface(customfont2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }


    }

