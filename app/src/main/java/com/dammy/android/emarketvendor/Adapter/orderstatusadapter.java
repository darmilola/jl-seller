package com.dammy.android.emarketvendor.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dammy.android.emarketvendor.Models.orderstatusmodel;
import com.dammy.android.emarketvendor.R;

import java.util.ArrayList;

public class orderstatusadapter extends RecyclerView.Adapter<orderstatusadapter.viewholder> {

    ArrayList<orderstatusmodel> orderstatusmodels;
    private Context c;
    final private ListItemClickListener mOnClickListener;

    public orderstatusadapter(Context c,ArrayList<orderstatusmodel> orderstatusmodels, ListItemClickListener mOnClickListener) {
        this.c = c;
        this.mOnClickListener = mOnClickListener;
        this.orderstatusmodels = orderstatusmodels;
    }


    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }



    @Override
    public orderstatusadapter.viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderstatusitem,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(orderstatusadapter.viewholder holder, int position) {
         holder.statusname.setText(orderstatusmodels.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return orderstatusmodels.size();
    }

    public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView statusname;
        public viewholder(View itemView) {
            super(itemView);
            statusname = itemView.findViewById(R.id.statustextview);
            Typeface customfont2= Typeface.createFromAsset(itemView.getContext().getAssets(),"Kylo-Light.otf");
            statusname.setTypeface(customfont2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
