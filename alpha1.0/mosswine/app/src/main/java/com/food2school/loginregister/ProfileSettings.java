package com.food2school.loginregister;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import java.util.*;

public class ProfileSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        Intent intent = getIntent();
        User user=null;
        if (intent.hasExtra("user")){
            user=intent.getParcelableExtra("user");
        }

        if (user==null) {
            System.out.println("No userID received");
        } else {
            final TextView userID_val = (TextView) findViewById(R.id.setting_username);
            final TextView emailView = (TextView) findViewById(R.id.setting_email);
            final TextView phoneView = (TextView) findViewById(R.id.setting_phone);
            final TextView addressView = (TextView) findViewById(R.id.setting_address);
            userID_val.setText(user.getUsername());
            emailView.setText(user.getEmail());
            phoneView.setText(user.getPhone());
            addressView.setText(user.getAddress());
        }
    }
}