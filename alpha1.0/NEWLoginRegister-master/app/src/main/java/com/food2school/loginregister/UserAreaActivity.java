package com.food2school.loginregister;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.food2school.loginregister.R.id.rv;


public class UserAreaActivity extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private LinearLayoutManager mLinearLayoutManager;
    private List<Restaurant> resLis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        mRecycleView=(RecyclerView)findViewById(rv);
        mRecycleView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        resLis=new ArrayList<Restaurant> ();
        final Button bProfile = (Button) findViewById(R.id.bProfile);
        final Button bOrder = (Button) findViewById(R.id.bOrder);
        final Button bPost = (Button) findViewById(R.id.bPost);
        final Button bDelivery = (Button) findViewById(R.id.bDelivery);


        final User user=getIntent().getParcelableExtra("user");
        //ArrayList<Restaurant> resLis=getNearbyRestaurant(getLocation());
        getNearbyRestaurant(getLocation());
        bProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAreaActivity.this, ProfileSettings.class);
                intent.putExtra("user", user);
                UserAreaActivity.this.startActivity(intent);
            }
        });

        bPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAreaActivity.this, DriverPost.class);
                intent.putExtra("user", user);
                UserAreaActivity.this.startActivity(intent);
            }
        });
    }
    public String getLocation(){
        return "Caltech";
    }
    public void getNearbyRestaurant(String location) {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        System.out.println("Fetch database success");
                        //List<Restaurant> resLis=new ArrayList();
                        JSONArray restaurantName = jsonResponse.getJSONArray("restaurantName");
                        JSONArray restaurantPhone = jsonResponse.getJSONArray("restaurantPhone");
                        JSONArray restaurantAddress = jsonResponse.getJSONArray("restaurantAddress");
                        JSONArray restaurantID = jsonResponse.getJSONArray("restaurantID");
                        JSONArray restaurantPhoto = jsonResponse.getJSONArray("restaurantPhoto");
                        for (int i = 0; i<restaurantName.length();i++){
                            if (restaurantPhoto.getString(i).toLowerCase().equals("null"))
                                resLis.add(new Restaurant(restaurantName.getString(i),restaurantAddress.getString(i),restaurantPhone.getString(i),Integer.parseInt(restaurantID.getString(i))));
                            else
                                resLis.add(new Restaurant(restaurantName.getString(i),restaurantAddress.getString(i),restaurantPhone.getString(i),Integer.parseInt(restaurantID.getString(i)),restaurantPhoto.getString(i)));
                        }
                        initializeAdapter();

                        /*
                        User userInfo = new User(username, userID, address, phone, email);
                        Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);
                        intent.putExtra("user", userInfo);

                        LoginActivity.this.startActivity(intent);
                        */

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UserAreaActivity.this);
                        builder.setMessage("Cannot Connect to Server!")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        FetchRestaurantRequest fetchRestaurantRequest = new FetchRestaurantRequest(location.toLowerCase(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(UserAreaActivity.this);
        queue.add(fetchRestaurantRequest);
    }


    private void initializeAdapter(){
        RVAdaptorUserArea adapter = new RVAdaptorUserArea(resLis);
        mRecycleView.setAdapter(adapter);
    }
}