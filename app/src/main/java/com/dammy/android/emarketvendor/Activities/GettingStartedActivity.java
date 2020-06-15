package com.dammy.android.emarketvendor.Activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.dammy.android.emarketvendor.R;
import com.dammy.android.emarketvendor.openingPageActivity;
import com.dammy.android.emarketvendor.splashscreen;

public class GettingStartedActivity extends AppCompatActivity {
    TextView setupshop,addcategories,adddeliverylocation,addproductstosell;
    TextView defaulttitle,toolbartitle,gettingstartedtext;
    String text = "<h2>Welcome to JL seller  App the mobile application for vendors selling on JL Market.</h2>\n" +
            "<p>Kindly follow the step stated below to have your shop listed and start selling.</p>\n" +
            "<h3>Step 1:</h3>\n" +
            "<p>Set up your shop.</p>\n" +
            "<p>Kindly visit the page for setting up shop and give the required information,then click on create shop to create your shop.\n" +
            "    you can always come back to edit the information given later. Note: by default your shop will be closed</p>\n" +
            "<h3>Step 2:</h3>\n" +
            "<p>Add product categories</p>\n" +
            "<p>Also you are to visit the page for adding product categories and add the categories for the products you are going to be selling.\n" +
            "    Note: you can only add up to 10 product categories. You can also come back later to edit the categories.</p>\n" +
            "<h3>Step 3:</h3>\n" +
            "<p>Add delivery location:</p>\n" +
            "<p>Visit the delivery location page to add the State and area you can deliver to within the delivery time specified during setting up shop.\n" +
            "    Note: You cannot deliver to more than 10 delivery locations.</p>\n" +
            "<h3>Step 4:</h3>\n" +
            "<p>Add products:</p>\n" +
            "    <p>You can add all the product you want to be selling grouped in their respective categories.</p>\n" +
            "<h3>Step 5:</h3>\n" +
            "<p>Open Your shop for the public:</p>\n" +
            "    <p>Finally you are to visit the product page and toggle on the shop open button, so that your shop can now be visible to the public</p>\n" +
            "\n" +
            "    <h2>Receiving Orders</h2>\n" +
            "    <p>Immediately an order is placed, you will receive a notification notifying you of the order, you can toggle the order state from the order tab. </p>    \n" +
            "    <h2>Payments:</h2>\n" +
            "<p>The payment is based on what you sell,you only pay 10% of what you sell in the previous selling month, this will serve as your subscription for the next month. The payment is valid for 30 days.\n" +
            "You will be contacted via email when your payment expires, you will also be given 7days to clear your dues,payment portal will be enabled in the process.\n" +
            "Failure to pay within the set time will make your shop to be delisted from the vendors list and will only be enlisted back when the due is paid.\n" +
            "There is exception for those whose payment amount is not up to 500 naira those ones will receive payment email only when their due is greater than 500 naira</p>\n" +
            "<h2>Product Availability</h2>\n" +
            "<p>When a product is no longer available on stock, you can remove it from stock by toggling the button in the product detail page, you can also toggle it back when it is available</p>\n" +
            "    ";
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);
        if(savedInstanceState != null){

            if(savedInstanceState.getInt("pid",-1) == android.os.Process.myPid()){

            }
            else{

                startActivity(new Intent(this,splashscreen.class));

            }
        }
        InitializeView();

    }

    private void InitializeView(){

        toolbar = findViewById(R.id.gettingstartedtoolbar);
        gettingstartedtext = findViewById(R.id.gettingstartedtext);
         gettingstartedtext.setText(Html.fromHtml(text));
        toolbartitle = findViewById(R.id.gettingstartedtoolbartitle);
        Typeface customfont2= Typeface.createFromAsset(getAssets(),"Kylo-Light.otf");
        Typeface customfont= Typeface.createFromAsset(getAssets(),"Kylo-Regular.otf");
        toolbartitle.setTypeface(customfont2);
        gettingstartedtext.setTypeface(customfont);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        if(menuItem.getItemId() == android.R.id.home){

            this.onBackPressed();
        }


        return super.onOptionsItemSelected(menuItem);
    }
    @Override
    public void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        outstate.putInt("pid",android.os.Process.myPid());

    }


}
