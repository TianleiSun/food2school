package com.food2school.loginregister;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Evelyn on 4/28/2017.
 */

public class FetchRestaurantRequest extends StringRequest {
    private static final String REQUEST_URL = "https://monoacid-condensati.000webhostapp.com/ResRequest.php";
    private Map<String, String> params;

    public FetchRestaurantRequest(String location, Response.Listener<String> listener) {
        super(Method.POST, REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("location", location);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}