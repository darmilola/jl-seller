package com.dammy.android.emarketvendor.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.Models.vendorprofile;
import com.dammy.android.emarketvendor.splashscreen;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class account extends Fragment {

    View view;
    static ArrayList<vendorprofile> vendorprofiles = null;
    TextView editandapply;
    TextInputEditText firstnamevalue,lastnamevalue,phonenumbervalue,emailvalue;
    String firstname,lastname,phonenumber,email,picturepath;
    SimpleDraweeView accountpicture;
    Dialog loadingdialog;


    public account() {
        // Required empty public constructor
    }

    public static account newInstance(ArrayList<vendorprofile> vendorprofile){
        vendorprofiles = vendorprofile;
        account account = new account();
        return account;

    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);



    }

    @Override
    public void onActivityCreated(Bundle savedinstance){
        super.onActivityCreated(savedinstance);
        if(vendorprofiles == null){
            Intent intent = new Intent(getContext(),splashscreen.class);
            startActivity(intent);
            return;
        }
        else {
            InitializeView();
            getComingvendorinfo();
            setOnclicklistnener();
            }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
               Fresco.initialize(getContext());
               view =  inflater.inflate(R.layout.fragment_account, container, false);

               view.setLayerType(View.LAYER_TYPE_SOFTWARE,null);


               return view;
    }

    private void InitializeView(){
        firstnamevalue = view.findViewById(R.id.accountfirstnamevalue);

        lastnamevalue = view.findViewById(R.id.accountlastnamevalue);
        editandapply = view.findViewById(R.id.accounteditandapply);
        phonenumbervalue = view.findViewById(R.id.accountphonenumbervalue);

        emailvalue = view.findViewById(R.id.accountemailvalue);
        accountpicture = view.findViewById(R.id.accountpicture);
        Typeface customfont2= Typeface.createFromAsset(getActivity().getAssets(),"Kylo-Light.otf");
        Typeface customfont= Typeface.createFromAsset(getActivity().getAssets(),"Kylo-Regular.otf");
        firstnamevalue.setTypeface(customfont);
        emailvalue.setTypeface(customfont);
        editandapply.setTypeface(customfont);
        lastnamevalue.setTypeface(customfont);
        phonenumbervalue.setTypeface(customfont);

        firstnamevalue.setEnabled(false);
        lastnamevalue.setEnabled(false);
        phonenumbervalue.setEnabled(false);


    }

    private void setOnclicklistnener() {

        editandapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editandapply.getText().toString().equalsIgnoreCase("Edit")){
                    editandapply.setText("Apply");
                    firstnamevalue.setEnabled(true);
                    lastnamevalue.setEnabled(true);
                    phonenumbervalue.setEnabled(true);
                }
                else{
                    editandapply.setText("Edit");
                    firstnamevalue.setEnabled(false);
                    lastnamevalue.setEnabled(false);
                    phonenumbervalue.setEnabled(false);

                    saveProfiletask saveProfiletask = new saveProfiletask();
                    saveProfiletask.execute();

                }
            }
        });
    }

    private void getComingvendorinfo(){
        firstname = vendorprofiles.get(0).getFirstname();
        lastname = vendorprofiles.get(0).getLastname();
        email = vendorprofiles.get(0).getEmail();
        phonenumber = vendorprofiles.get(0).getPhonenumber();
        picturepath = vendorprofiles.get(0).getProfileimage();
        firstnamevalue.setText(firstname);
        lastnamevalue.setText(lastname);
        emailvalue.setText(email);
        formatphone();
        getComingProfilepicturePath(vendorprofiles);

    }

    private void formatphone(){
        phonenumber = vendorprofiles.get(0).getPhonenumber();

        if(phonenumber.startsWith("0")){
            phonenumbervalue.setText(phonenumber);
        }
        else{
            phonenumbervalue.setText("+234"+phonenumber);
        }
    }

    private void getComingProfilepicturePath(ArrayList<vendorprofile> vendorprofile){

        if(vendorprofile != null) {
            String ImageUri = vendorprofile.get(0).getProfileimage();
            Uri imageuri = Uri.parse(ImageUri);
            ImageRequest request = ImageRequest.fromUri(imageuri);
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setOldController(accountpicture.getController()).build();
            accountpicture.setController(controller);
        }
    }

    public class saveProfiletask extends AsyncTask {
        String prompt;
        String urlpath = "http://jl-market.com/vendor/vendorsaveProfile.php";

        @Override
        protected void onPreExecute() {
           // Toast.makeText(getContext(), "Saving profile", Toast.LENGTH_SHORT).show();
             showloadingdialog();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }
            String serverresponse = new updateProfile().GetData(urlpath, email, firstnamevalue.getText().toString(), lastnamevalue.getText().toString(), phonenumbervalue.getText().toString());
            if (serverresponse != null) {
                try {
                    JSONObject jsonObject = new JSONObject(serverresponse);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");

                    if (status.equalsIgnoreCase("Profile updated successfully")) {
                        prompt = "Profile Updated";
                        return prompt;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            return serverresponse;
        }

        protected void onPostExecute(Object result) {
              loadingdialog.dismiss();
            //Toast.makeText(getContext(), result.toString(), Toast.LENGTH_SHORT).show();
            if(result == null){
                Toast.makeText(getContext(), "Error Occured", Toast.LENGTH_SHORT).show();
            }
            else if(result.toString().equalsIgnoreCase(prompt)){
                Toast.makeText(getContext(), prompt, Toast.LENGTH_SHORT).show();
            }

        }

    }


    public class updateProfile {

        public String GetData(String url,String userid,String firstname,String lastname,String phone) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();


                RequestBody formBody = new FormBody.Builder()
                        .add("email", userid)
                        .add("firstname",firstname)
                        .add("lastname",lastname)
                        .add("phonenumber",phone)
                        .build();
                Request request = new Request.Builder().post(formBody).url(url).build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                return data;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void showloadingdialog() {
        loadingdialog = new Dialog(getContext(), android.R.style.Theme_Light);
        loadingdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingdialog.setContentView(R.layout.loadingdialog);
        ImageView image = loadingdialog.findViewById(R.id.loadingimage);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(image);
        Glide.with(getContext()).load(R.drawable.loading).into(imageViewTarget);
        loadingdialog.setCancelable(false);

        loadingdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        loadingdialog.show();
    }
        private Boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
