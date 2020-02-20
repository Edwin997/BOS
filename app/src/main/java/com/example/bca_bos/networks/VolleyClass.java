package com.example.bca_bos.networks;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bca_bos.KeyboardBOS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleyClass {

    private static RequestQueue g_requestqueue;
    private static StringRequest g_stringrequest;

    public static void getProduk(Context p_context, String p_url){
        g_requestqueue = Volley.newRequestQueue(p_context);

        g_stringrequest = new StringRequest(Request.Method.GET, p_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Log.d("COBA", response);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                setErrorMessage(volleyError);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_seller", "3");
                return params;
            }
        };
        g_stringrequest.setTag("getProduk");
        g_requestqueue.add(g_stringrequest);
    }

    private static void setErrorMessage(VolleyError volleyError){
        String message = "";
        Log.d("COBA", volleyError.getMessage());
        if (volleyError instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection! (Network Error)";
        } else if (volleyError instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
        } else if (volleyError instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection! (Auth Failure Error)";
        } else if (volleyError instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (volleyError instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection! (No Connection Error)";
        } else if (volleyError instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
        }
        Log.d("COBA", message);
    }
}
