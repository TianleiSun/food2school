package com.food2school.loginregister;

/**
 * Created by Bryan on 4/28/2017.
 */


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Bryan on 4/23/17.
 */

public class Restaurant {
    private String resName;
    private String address;
    private String phone;
    private int resID;
    private String photoURL=null;

    public Restaurant(String A, String B, String C, int D, String photoURL) {
        resName = A;
        address = B;
        phone = C;
        resID = D;
        this.photoURL = photoURL;
    }
    public Restaurant(String A, String B, String C, int D) {
        resName = A;
        address = B;
        phone = C;
        resID = D;
    }

    public String getRestaurantName() {
        return this.resName;
    }
    public String getPhotoURL(){return this.photoURL;}
    public void setRestaurantName(String U) {
        this.resName = U;
    }

    public int getRestaurantID() {
        return this.resID;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String A) {
        this.address = A;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String P) {
        this.phone = P;
    }

}