package com.food2school.loginregister;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Evelyn on 5/8/2017.
 */

public class Order implements Parcelable {
    List<MenuCategory.Food> foodlist;
    double foodTotal=0;
    double deliveryFee=(double)4.0;
    String restaurantName;
    int restaurantID;
    public Order(String restaurantName,int id){
        this.restaurantName=restaurantName;
        foodlist=new LinkedList();
        this.restaurantID=id;
    }
    public List<MenuCategory.Food> getFoodList(){return foodlist;}
    public double getFoodTotal(){return foodTotal;}
    public void setFoodTotal(double t){this.foodTotal=t;}
    public double getDeliveryFee(){return this.deliveryFee;}
    public String getRestaurantName(){return this.restaurantName;}
    public int getRestaurantID(){return this.restaurantID;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(restaurantID);
        dest.writeString(restaurantName);
        dest.writeDouble(foodTotal);
        dest.writeDouble(deliveryFee);
        dest.writeList(foodlist);

    }
    protected Order(Parcel in) {
        restaurantID=in.readInt();
        restaurantName = in.readString();
        foodTotal = in.readDouble();
        deliveryFee = in.readDouble();
        List<MenuCategory.Food> mlist=new ArrayList ();
        in.readList(mlist,MenuCategory.Food.class.getClassLoader());
        this.foodlist=mlist;

    }
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

}