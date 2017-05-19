package com.food2school.loginregister;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Evelyn on 5/4/2017.
 */

public class ResNameRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://ec2-34-210-186-213.us-west-2.compute.amazonaws.com/resNameID.php";
    // private Map<String, String> params;

    public ResNameRequest(Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
    }
}