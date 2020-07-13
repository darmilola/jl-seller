package com.dammy.android.emarketvendor.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.dammy.android.emarketvendor.Models.ordermessagemodel;
import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.service.updateorderservice.updateorder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ordermessageadapter extends RecyclerView.Adapter<ordermessageadapter.viewholder> {

    private ListItemClickListener mOnClickListener;
    List<ordermessagemodel> ordermessagemodelList;
    Context context;
    Dialog loadingdialog;
    String vendorid;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
    public ordermessageadapter(ArrayList<ordermessagemodel> modelArrayList,ListItemClickListener mOnClickListener,Context context,String vendorid){
        this.mOnClickListener = mOnClickListener;
        this.ordermessagemodelList = modelArrayList;
        this.context = context;
        this.vendorid = vendorid;

    }
    @Override
    public ordermessageadapter.viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderitem,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(ordermessageadapter.viewholder holder, int position) {
        ordermessagemodel current = ordermessagemodelList.get(position);
        holder.orderno.setText("Order  "+"#"+current.getOrderid().toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onListItemClick(holder.getAdapterPosition());

            }
            });
        holder.viewoptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu =  new PopupMenu(context,holder.viewoptions);
                popupMenu.inflate(R.menu.orderitemmenu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){

                            case R.id.deleteordermenu:
                                 DeleteOrdertask deleteOrdertask = new DeleteOrdertask(current.getOrderid());
                                 deleteOrdertask.execute();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();

            }

        });
    }

    @Override
    public int getItemCount() {
        return ordermessagemodelList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        TextView orderno;
        TextView ordermessage;
        TextView viewoptions;
        public viewholder(View itemView) {
            super(itemView);
            orderno = itemView.findViewById(R.id.orderno);
            viewoptions = itemView.findViewById(R.id.orderviewoptions);
            ordermessage = itemView.findViewById(R.id.receivedordermessage);
            Typeface customfont= Typeface.createFromAsset(itemView.getContext().getAssets(),"Kylo-Light.otf");
            orderno.setTypeface(customfont);
            ordermessage.setTypeface(customfont);
        }
    }
    public class DeleteOrdertask extends AsyncTask{


        private String deleteurl = "http://jl-market.com/vendor/deleteorder.php";
        String prompt;
        String orderid;

        public  DeleteOrdertask(String orderid){
            this.orderid = orderid;
        }

        @Override
        protected void onPreExecute() {

            //Toast.makeText(context, "deleting order", Toast.LENGTH_LONG).show();
            showloadingdialog();
        }
        @Override
        protected Object doInBackground(Object[] objects) {
            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }
            String serverresponse = new deleteorders().GetData(deleteurl,orderid);
            return serverresponse;
        }

        protected void onPostExecute(Object result) {
            loadingdialog.dismiss();
            if (result != null) {
                if (result.toString().equalsIgnoreCase(prompt)) {

                    Toast.makeText(context, prompt, Toast.LENGTH_LONG).show();
                } else {

                    Intent serviceintent = new Intent(context, updateorder.class);
                    serviceintent.putExtra("url", "http://jl-market.com/vendor/getOrderidVendor.php");
                    serviceintent.putExtra("vendorid", vendorid);
                    context.startService(serviceintent);
                }
            }
            else{
                Toast.makeText(context, "Error Occured", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public class deleteorders {


        public String GetData(String url, String orderid) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                RequestBody formBody = new FormBody.Builder()
                        .add("orderid", orderid)
                        .build();

                Request request = new Request.Builder().url(url).post(formBody).build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                return data;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private Boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showloadingdialog() {
        loadingdialog = new Dialog(context, android.R.style.Theme_Light);
        loadingdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingdialog.setContentView(R.layout.loadingdialog);
        ImageView image = loadingdialog.findViewById(R.id.loadingimage);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(image);
        Glide.with(context).load(R.drawable.loading).into(imageViewTarget);
        loadingdialog.setCancelable(false);

        loadingdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        loadingdialog.show();
    }
}
