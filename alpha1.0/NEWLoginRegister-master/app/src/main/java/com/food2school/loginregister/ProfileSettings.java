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

        int userID = intent.getIntExtra("userId", -1);
        if (userID == -1) {
            System.out.println("No userID received");
        } else {
            final TextView userID_val = (TextView) findViewById(R.id.setting_username);
            userID_val.setText(Integer.toString(userID));
        }
    }
}
