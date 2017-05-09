package com.food2school.loginregister;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
        }
    }
    List<Restaurant> restaurants;

    RVAdaptorUserArea(List<Restaurant> restaurants){
        this.restaurants = restaurants;
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
            ImageView imgView = restaurantViewHolder.restaurantPhoto;
            new DownloadImageTask(imgView).execute(restaurants.get(i).getPhotoURL());
        }
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

}