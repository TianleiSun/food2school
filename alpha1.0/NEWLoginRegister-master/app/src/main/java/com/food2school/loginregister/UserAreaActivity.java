package com.food2school.loginregister;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;


public class UserAreaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final Button bProfile = (Button) findViewById(R.id.bProfile);
        final Button bOrder = (Button) findViewById(R.id.bOrder);
        final Button bPost = (Button) findViewById(R.id.bPost);
        final Button bDelivery = (Button) findViewById(R.id.bDelivery);

        final int userID = 233233;

        bProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAreaActivity.this, ProfileSettings.class);
                intent.putExtra("userId", userID);
                UserAreaActivity.this.startActivity(intent);
            }
        });
    }
}