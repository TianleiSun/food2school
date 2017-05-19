package com.food2school.loginregister;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DriverPostRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://ec2-34-210-186-213.us-west-2.compute.amazonaws.com/driverpost.php";
    private Map<String, String> params;

    public DriverPostRequest(String resName, int driverID, int restID, String targetAddress, String deliveryTime,
                              int maxOrder, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("resName", resName);
        params.put("driverID", Integer.toString(driverID));
        params.put("restID", Integer.toString(restID));
        params.put("targetAddress", targetAddress);
        params.put("deliveryTime", deliveryTime);
        params.put("maxOrder", Integer.toString(maxOrder));
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}