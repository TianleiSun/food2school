package com.food2school.loginregister;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

/**
 * Created by Bryan on 4/28/2017.
 */


public class RVAdaptorUserArea extends RecyclerView.Adapter<RVAdaptorUserArea.RestaurantViewHolder>{
    public static Context contxt;
    public static User user;
    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView restaurantName;
        TextView waitingTime;
        ImageView restaurantPhoto;
        RestaurantViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_res);
            restaurantName = (TextView)itemView.findViewById(R.id.restaurant_name);
            waitingTime = (TextView)itemView.findViewById(R.id.waiting_time);
            restaurantPhoto = (ImageView)itemView.findViewById(R.id.restaurant_photo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    // item clicked
                    int itemPosition = getPosition();//depreciated in v22 lib
                    Intent intent = new Intent(contxt, MenuActivity.class);
                    intent.putExtra("restaurantID",restaurants.get(itemPosition).getRestaurantID());
                    intent.putExtra("restaurantName",restaurants.get(itemPosition).getRestaurantName());
                    intent.putExtra("user",user);
                    Log.d("adapator",String.valueOf(restaurants.get(itemPosition).getRestaurantID()));
                    contxt.startActivity(intent);
                }
            });
        }

    }
    static List<Restaurant> restaurants;

    RVAdaptorUserArea(List<Restaurant> restaurants, Context contxt, User user){
        this.restaurants = restaurants;
        this.contxt=contxt;
        this.user=user;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_res, viewGroup, false);
        RestaurantViewHolder rvh = new RestaurantViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder restaurantViewHolder, int i) {

        restaurantViewHolder.restaurantName.setText(restaurants.get(i).getRestaurantName());
        restaurantViewHolder.waitingTime.setText("30 min");
        if (restaurants.get(i).getPhotoURL()==null)
            restaurantViewHolder.restaurantPhoto.setImageResource(R.drawable.unknown);
        else {
            restaurantViewHolder.restaurantPhoto.setImageResource(R.drawable.unknown);
            ImageView imgView = restaurantViewHolder.restaurantPhoto;
            new DownloadImageTask(imgView).execute(restaurants.get(i).getPhotoURL());
        }
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

}