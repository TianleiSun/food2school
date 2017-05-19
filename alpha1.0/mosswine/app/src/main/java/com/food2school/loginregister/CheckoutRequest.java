package com.food2school.loginregister;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Evelyn on 5/11/2017.
 */

public class CheckoutRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "http://ec2-34-210-186-213.us-west-2.compute.amazonaws.com/checkout.php";
    private Map<String, String> params;

    public CheckoutRequest(String schoolAddress, String address, String apt, int userID,Order morder, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        JSONObject jsonFood=new JSONObject();
        List<String> fname=new ArrayList();
        List<Integer> fquant=new ArrayList();
        List<Float> fprice=new ArrayList();
        params = new HashMap<>();
        params.put("address", address);
        params.put("apt",apt);
        params.put("userID",String.valueOf(userID));

        int i=0;
        for (MenuCategory.Food f : morder.getFoodList()){
            fname.add(f.getName());
            fquant.add(f.getNum());
            fprice.add(f.getPrice());
        }
        try {
            jsonFood.put("foodnameLis", new JSONArray(fname));
            jsonFood.put("foodpriceLis", new JSONArray(fprice));
            jsonFood.put("foodquantLis",new JSONArray(fquant));
        } catch(Exception e){
            //do nothing
        }
        params.put("food",jsonFood.toString());
        //params.put("foodquantLis",jsonFoodQuantity.toString());
        //params.put("foodpriceLis",jsonFoodPrice.toString());
        params.put("deliveryFee",String.valueOf(morder.getDeliveryFee()));
        params.put("totalPrice",String.valueOf(morder.getFoodTotal()));
        params.put("restaurantID",String.valueOf(morder.getRestaurantID()));
        Log.d("checkoutrequest",String.valueOf(morder.getRestaurantID()));
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}