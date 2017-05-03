package com.food2school.loginregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.util.*;
import java.util.*;

public class DriverDeliveryStatus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Loading");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_delivery_status);
        Post driverPost = null;
        Intent intent = getIntent();
        if (intent.hasExtra("driverPost")){
            driverPost=intent.getParcelableExtra("driverPost");
        }
        System.out.println("Intent loaded");


        if (driverPost==null) {
            System.out.println("No driverPost received");
        } else {
            System.out.println("DriverPost received");
            final TextView orderInfo = (TextView) findViewById(R.id.tvDeliOrderInfo);
            final TextView status = (TextView) findViewById(R.id.tvDeliStatus);
            final TextView maxNum = (TextView) findViewById(R.id.tvDeliMaxNum);
            final TextView curNum = (TextView) findViewById(R.id.tvDeliCurNum);
            final TextView resName = (TextView) findViewById(R.id.tvDeliRestName);
            final TextView returnAdd = (TextView) findViewById(R.id.tvDeliReturnAddr);

            orderInfo.setText("Description of Address, Food name and quantity");
            status.setText("On delivery or delivered");
            maxNum.setText(Integer.toString(driverPost.getMaxOrderNum()));
            curNum.setText("Info from Backend");
            resName.setText(driverPost.getresName());
            returnAdd.setText(driverPost.gettargetAddress());

        }

    }
}
