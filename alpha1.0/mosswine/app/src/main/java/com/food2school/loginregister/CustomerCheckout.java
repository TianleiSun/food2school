package com.food2school.loginregister;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import static com.food2school.loginregister.RVAdaptorUserArea.user;

public class CustomerCheckout extends AppCompatActivity{
    LinearLayout mFoodlistLayout;
    LinearLayout mLayout;
    TextView mResName;
    EditText mAddress;
    EditText mApt;
    Button mConfirm;
    User user;
    Order order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_checkout);

        mFoodlistLayout = (LinearLayout) findViewById(R.id.checkout_list);
        mLayout = (LinearLayout) findViewById(R.id.checkout_layout);
        mResName = (TextView) findViewById(R.id.checkout_resName);
        mAddress = (EditText) findViewById(R.id.txt_deliver_address);
        mApt = (EditText) findViewById(R.id.txt_deliver_apt);

        Intent intent = getIntent();
        user=intent.getParcelableExtra("user");
        if (intent.hasExtra("Order")) {
            order = intent.getParcelableExtra("Order");
            Log.d("afterCheckoutCreate",String.valueOf(order.getRestaurantID()));
        }
        if (order == null) {
            System.out.println("No order received");
        }
        mResName.setText(order.getRestaurantName());
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        //System.out.println(order.getFoodList().size());
        for (MenuCategory.Food f : order.getFoodList()) {
            View view = inflater.inflate(R.layout.item_checkout_food, null);
            TextView mFoodName = (TextView) view.findViewById(R.id.item_name);
            TextView mFoodNum = (TextView) view.findViewById(R.id.item_quantity);
            TextView mFoodPrice = (TextView) view.findViewById(R.id.price);
            //System.out.println(order.getFoodList().get(i).getName());
            //System.out.println(order.getFoodList().get(i).getcName());
            mFoodName.setText(f.getName());

            mFoodNum.setText("x" + String.valueOf(f.getNum()));
            mFoodPrice.setText("$" + String.valueOf(f.getPrice()));
            mFoodlistLayout.addView(view);
        }
        DecimalFormat df = new DecimalFormat("#.00");
        View viewTax = inflater.inflate(R.layout.item_checkout_food, null);
        TextView mTaxName = (TextView) viewTax.findViewById(R.id.item_name);
        TextView mTaxPrice = (TextView) viewTax.findViewById(R.id.price);
        mTaxName.setText("Tax");
        mTaxPrice.setText("x1.08");
        mFoodlistLayout.addView(viewTax);
        View viewDelivery = inflater.inflate(R.layout.item_checkout_food, null);
        TextView mDeliveryName = (TextView) viewDelivery.findViewById(R.id.item_name);
        TextView mDeliveryPrice = (TextView) viewDelivery.findViewById(R.id.price);
        mDeliveryName.setText("Delivery Fee");
        mDeliveryPrice.setText("+" + df.format(order.getDeliveryFee()));
        mFoodlistLayout.addView(viewDelivery);

        View view = inflater.inflate(R.layout.item_checkout_total, null);
        TextView mFoodTotalPrice = (TextView) view.findViewById(R.id.checkout_price);

        mFoodTotalPrice.setText("$" + df.format(order.getFoodTotal() * 1.08 + order.getDeliveryFee()));
        //System.out.println(order.getFoodTotal());
        mLayout.addView(view);
        mConfirm = (Button) view.findViewById(R.id.checkout_confirm);


        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String address = mAddress.getText().toString();
                final String apt = mApt.getText().toString();

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                //String temp = jsonResponse.getString("restaurantID");
                                //Log.d("restaurantID Checkout",temp);
                                finish();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerCheckout.this);
                                builder.setMessage("Server Error")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                if (apt==null || address==null || apt.equals("") || address.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CustomerCheckout.this);
                    builder.setMessage("Empty Address!")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }
                else {

                    CheckoutRequest checkoutRequest = new CheckoutRequest(null,address, apt, user.getUserID(), order, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(CustomerCheckout.this);
                    queue.add(checkoutRequest);
                }

            }
        });


    }


}