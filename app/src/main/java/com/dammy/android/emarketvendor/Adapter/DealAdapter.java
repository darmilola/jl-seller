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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.dammy.android.emarketvendor.Fragments.Deals;
import com.dammy.android.emarketvendor.Models.dealmodel;
import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.service.updateservice.updatedeals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.mViewholder> {

    private List<dealmodel> dealList;
    private Context context;
    Dialog loadingdialog;
    String vendorid;

   public DealAdapter(ArrayList<dealmodel> dealList, Context context,String vendorid){
        this.dealList = dealList;
        this.context = context;
        this.vendorid = vendorid;
    }
    @Override
    public mViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dealrecyclerviewitem,parent,false);
        return new mViewholder(view);
    }

    @Override
    public void onBindViewHolder(mViewholder holder, int position) {
       holder.description.setText(dealList.get(position).getDealdescription());

       holder.viewoptions.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               PopupMenu popupMenu =  new PopupMenu(context,holder.viewoptions);
               popupMenu.inflate(R.menu.dealsmenu);
               popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   @Override
                   public boolean onMenuItemClick(MenuItem item) {

                       switch (item.getItemId()){

                           case R.id.deletedealmenu:
                               deletedeal deletedeal = new deletedeal(dealList.get(position).getDealID());
                               deletedeal.execute();
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
        return dealList.size();
    }

    public  class mViewholder extends RecyclerView.ViewHolder{

       TextView description;
       TextView viewoptions;

        public mViewholder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.dealdescription);
            viewoptions = itemView.findViewById(R.id.viewoptions);
            Typeface customfont2= Typeface.createFromAsset(itemView.getContext().getAssets(),"Kylo-Light.otf");
            description.setTypeface(customfont2);
        }
    }

    public class deletedeal extends AsyncTask{
        private String deleteurl = "http://jl-market.com/vendor/deletedeal.php";
        String prompt;
        String dealid;

         public  deletedeal(String dealid){
            this.dealid = dealid;
        }
        @Override
        protected void onPreExecute() {
        showloadingdialog();

        }


        @Override
        protected Object doInBackground(Object[] objects) {
            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }
            String serverresponse = new deletedeals().GetData(deleteurl,dealid);
            return serverresponse;
        }

        protected void onPostExecute(Object result) {
            loadingdialog.dismiss();
             if (result != null) {
                if (result.toString().equalsIgnoreCase(prompt)) {

                    Toast.makeText(context, prompt, Toast.LENGTH_LONG).show();
                } else {
                    Intent serviceintent = new Intent(context, updatedeals.class);
                    serviceintent.putExtra("url", "http://jl-market.com/vendor/displaydeals.php");
                    serviceintent.putExtra("vendorid", vendorid);
                    context.startService(serviceintent);

                }
            }
            else{
                Toast.makeText(context, "Unable to Delete please try again", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public class deletedeals {


        public String GetData(String url, String dealid) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                RequestBody formBody = new FormBody.Builder()
                        .add("dealid", dealid)
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

    private void showloadingdialog(){
        loadingdialog = new Dialog(context,R.style.Dialog_Theme);
        loadingdialog.setContentView(R.layout.loadingdialog);
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.loading);
        ImageView image = loadingdialog.findViewById(R.id.loadingimage);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(image);
        Glide.with(context).load(R.drawable.loading).into(imageViewTarget);
        loadingdialog.show();
        loadingdialog.setCancelable(false);
        //context.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        loadingdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            loadingdialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            loadingdialog.getWindow().setStatusBarColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }


    }

}
