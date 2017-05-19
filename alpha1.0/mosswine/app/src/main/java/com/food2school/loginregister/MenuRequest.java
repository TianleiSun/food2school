package com.food2school.loginregister;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Evelyn on 5/7/2017.
 */

public class MenuRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://ec2-34-210-186-213.us-west-2.compute.amazonaws.com/menuRequest.php";
    private Map<String, String> params;

    public MenuRequest(String restaurantID, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("restaurantID", restaurantID);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}