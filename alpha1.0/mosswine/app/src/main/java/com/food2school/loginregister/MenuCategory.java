package com.food2school.loginregister;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Evelyn on 5/7/2017.
 */

public class MenuCategory {
    private static List<MenuCategory> listAllCategories;
    private int cId;
    private String cName;
    private List<Food> foodList;
    private int badge;
    public int getcID(){
        return this.cId;
    }
    public void setcId(int cId) {
        this.cId = cId;
    }
    public void setFoodList(List<Food> subModelList) {
        this.foodList = subModelList;
    }
    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public static List<MenuCategory> getAllCategories(){return listAllCategories;}
    public List<Food> getFoodList(){return foodList;}
    public int getBadge() {
        return badge;
    }

    public void setBadge(int badge) {
        this.badge = badge;
    }
    public void setName(String name) {
        this.cName = name;
    }
    public MenuCategory(int a, String b, List<Food> c){
        this.cId=a;
        this.cName=b;
        this.foodList=c;
    }
    public static class Food implements Parcelable {
        private int cId;
        private String cName;
        private String name;
        private float price;
        private int num;
        public static final Creator<Food> CREATOR = new Creator<Food>() {
            @Override
            public Food createFromParcel(Parcel in) {
                return new Food(in);
            }

            @Override
            public Food[] newArray(int size) {
                return new Food[size];
            }
        };
        protected Food(Parcel in) {
            cId=in.readInt();
            cName = in.readString();
            name=in.readString();
            price = in.readFloat();
            num = in.readInt();

        }
        @Override
        public int describeContents() {
            return 0;
        }
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(cId);
            dest.writeString(cName);
            dest.writeString(name);
            dest.writeFloat(price);
            dest.writeInt(num);
        }
        public Food(String name, float price){
            this.name=name;
            this.price=price;
        }
        public int getcId() {
            return cId;
        }

        public void setcId(int cId) {
            this.cId = cId;
        }

        public String getcName() {
            return cName;
        }

        public void setcName(String cName) {
            this.cName = cName;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }
        public int getNum() {
            return num;
        }

        public  void  setNum(int num) {
            this.num = num;
        }
    }
    public  static void initData(Context context, String restaurantID){
        listAllCategories = new ArrayList<>();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Map<String, List<Food>> map=new HashMap();
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        JSONArray category = jsonResponse.getJSONArray("category");
                        JSONArray foodName = jsonResponse.getJSONArray("foodName");
                        JSONArray foodPrice = jsonResponse.getJSONArray("foodPrice");
                        for (int i=0;i<category.length();i++) {
                            String cat=category.get(i).toString();
                            if (!map.containsKey(cat)) map.put(cat,new ArrayList<Food>());
                            map.get(cat).add(new Food(foodName.get(i).toString(),Float.parseFloat(foodPrice.get(i).toString())));
                        }
                        List<String> menuCategoryName=new ArrayList (map.keySet());
                        for (int i=0;i<menuCategoryName.size();i++) {
                            listAllCategories.add(new MenuCategory(i, menuCategoryName.get(i), map.get(menuCategoryName.get(i))));
                            /*
                            System.out.println(menuCategoryName.get(i));
                            for (Food f: map.get(menuCategoryName.get(i))) {
                                System.out.println(f.getName());
                                System.out.println(f.getPrice());
                            }
                                System.out.println(" ");
                             */
                        }
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        MenuRequest menuRequest = new MenuRequest(restaurantID,responseListener);
        menuRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(menuRequest);
    }



}