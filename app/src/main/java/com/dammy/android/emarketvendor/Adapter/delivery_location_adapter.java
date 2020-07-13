package com.dammy.android.emarketvendor.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.dammy.android.emarketvendor.Activities.Showdeliverylocation;
import com.dammy.android.emarketvendor.Models.delivery_location_model;
import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.service.update_locations;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class delivery_location_adapter  extends RecyclerView.Adapter<delivery_location_adapter.itemViewHolder>{


    List<delivery_location_model> locationList;
    Context context;
    Dialog loadingdialog;

    public delivery_location_adapter(ArrayList<delivery_location_model> locationList,Context context){
        this.locationList = locationList;
        this.context = context;
    }
    @Override
    public itemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_location_item,parent,false);
        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(itemViewHolder holder, int position) {
            delivery_location_model locationModel = locationList.get(position);
            holder.cityName.setText(locationModel.getCity());
            holder.AreaName.setText(locationModel.getArea());
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }





    public class itemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView AreaName;
        private TextView cityName;
        private Button remove;
        public itemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            Typeface customfont2= Typeface.createFromAsset(itemView.getContext().getAssets(),"Kylo-Light.otf");
            AreaName = itemView.findViewById(R.id.delivery_locations_item_area);
            cityName = itemView.findViewById(R.id.delivery_locations_item_state);
            remove = itemView.findViewById(R.id.delivery_locations_item_remove_button);
            remove.setOnClickListener(this);
            AreaName.setTypeface(customfont2);
            cityName.setTypeface(customfont2);
            remove.setTypeface(customfont2);

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            if(v.getId() == R.id.delivery_locations_item_remove_button){
                delivery_location_model model = locationList.get(clickedPosition);

                new DeleteDeliveryLocations(model.getArea(),model.getCity(),model.getVendor_email()).execute();


            }
        }
    }



    private class DeleteDeliveryLocations extends AsyncTask {

        String prompt;
        String area,city,vendorid;
        private String URL = "http://jl-market.com/vendor/DeleteDeliveryLocations.php";


        public DeleteDeliveryLocations(String marea, String mcity,String vendorid){
            this.area = marea;
            this.city = mcity;
            this.vendorid = vendorid;
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

            String serverresponse = new Deletelocation().GetData(URL,vendorid,city,area);
            try {
                JSONObject jsonObject = new JSONObject(serverresponse);
                JSONObject info = jsonObject.getJSONObject("info");
                String status = info.getString("status");

                if (status.equalsIgnoreCase("deletesuccess")) {
                    prompt = "deletesuccess";
                    return prompt;
                }
                else if(status.equalsIgnoreCase("deletefailure")){

                    prompt = "deletefailure";
                    return prompt;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Object result) {


            loadingdialog.dismiss();

            if(result == null){

            }
            else if(result.toString().equalsIgnoreCase("deletesuccess")){


                Intent serviceintent = new Intent(context, update_locations.class);
                serviceintent.putExtra("url", "http://jl-market.com/vendor/getDeliveryLocations.php");
                serviceintent.putExtra("vendorid",vendorid);
                context.startService(serviceintent);
            }
            else if(result.toString().equalsIgnoreCase("deletefailure")){
                Toast.makeText(context, "Error in deleting Please try Again", Toast.LENGTH_SHORT).show();
            }
            else if(result.toString().equalsIgnoreCase("No Network Connection")){
                Toast.makeText(context, "No Network Connection", Toast.LENGTH_SHORT).show();
            }

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


    public class Deletelocation {

        public String GetData(String url,String email,String city,String area) {
            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();


                RequestBody formBody = new FormBody.Builder()
                        .add("vendorid",email)
                        .add("city",city)
                        .add("area",area)
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


}
