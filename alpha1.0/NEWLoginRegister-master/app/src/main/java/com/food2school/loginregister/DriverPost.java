package com.food2school.loginregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DriverPost extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_post);

        final EditText resName = (EditText) findViewById(R.id.etRestaurant);
        final EditText targetAddress = (EditText) findViewById(R.id.etReturnAddress);
        final EditText finishTime = (EditText) findViewById(R.id.etFinishTime);
        final EditText acceptUntilTime = (EditText) findViewById(R.id.etAcceptUntil);
        final EditText maxOrderNum = (EditText) findViewById(R.id.etMaxOrderNum);



        final Button bDriverPost = (Button) findViewById(R.id.bDriverPost);
//        final Button bDriverSearch = (Button) findViewById(R.id.bDriverSearch);

        final User user = getIntent().getParcelableExtra("user");
        final int driverID = user.getUserID();

        bDriverPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverPost.this, DriverDeliveryStatus.class);
                Post driverPost=new Post(resName.getText().toString(), driverID, targetAddress.getText().toString(),
                        finishTime.getText().toString(), acceptUntilTime.getText().toString(), Integer.valueOf(maxOrderNum.getText().toString()));

                System.out.print("post successful!!!!!!!!!!");

                intent.putExtra("driverPost", driverPost);
                DriverPost.this.startActivity(intent);
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
}
