package com.food2school.loginregister;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;

public class DriverPost extends AppCompatActivity {
    AutoCompleteTextView acTextView;
    String[] restlist = {"boiling point", "bluefin", "urban plate", "burger king"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_post);

        acTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.select_dialog_item,restlist);

        acTextView.setThreshold(1);
        acTextView.setAdapter(adapter);

        final EditText resName = (EditText) findViewById(R.id.autoCompleteTextView);
        final EditText targetAddress = (EditText) findViewById(R.id.etReturnAddress);
        final DatePicker deliveryDate = (DatePicker) findViewById(R.id.datePicker);


        final TimePicker deliveryTime = (TimePicker) findViewById(R.id.timePicker);
        deliveryTime.setIs24HourView(true);

        final EditText maxOrderNum = (EditText) findViewById(R.id.etMaxOrderNum);


        final Button bDriverPost = (Button) findViewById(R.id.bDriverPost);
//        final Button bDriverSearch = (Button) findViewById(R.id.bDriverSearch);

        final User user = getIntent().getParcelableExtra("user");
        final int driverID = user.getUserID();
        final int restID = 3;

        bDriverPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String localResName = resName.getText().toString();
                final String localTargetAddress = targetAddress.getText().toString();
                final int hour = deliveryTime.getHour();
                final int minute = deliveryTime.getMinute();
                final int month = deliveryDate.getMonth() + 1;
                final int day = deliveryDate.getDayOfMonth();
                final String localDeliveryTime = new StringBuilder().append("2017-").append(pad(month)).append("-")
                        .append(pad(day)).append(" ").append(pad(hour)).append(":").append(pad(minute)).append(":00").toString();
                final int localMaxOrder = Integer.valueOf(maxOrderNum.getText().toString());



                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(DriverPost.this, DriverDeliveryStatus.class);
                                Post driverPost = new Post(localResName, driverID, restID, localTargetAddress, localDeliveryTime, localMaxOrder);
                                intent.putExtra("driverPost", driverPost);
                                DriverPost.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(DriverPost.this);
                                builder.setMessage("Post Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                DriverPostRequest driverPostRequest = new DriverPostRequest(localResName, driverID, restID,
                        localTargetAddress,localDeliveryTime,localMaxOrder,responseListener);
                RequestQueue queue = Volley.newRequestQueue(DriverPost.this);
                queue.add(driverPostRequest);


            }
        });

//        bDriverSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DriverPost.this, ProfileSettings.class);
//                intent.putExtra("user", user);
//                DriverPost.this.startActivity(intent);
//            }
//        });

    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

}