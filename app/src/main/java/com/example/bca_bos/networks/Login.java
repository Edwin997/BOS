package com.example.bca_bos.networks;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bca_bos.LoginActivity;
import com.example.bca_bos.ui.produk.ProdukAdapter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Login {

    private static final String TAG = "VOLLEY";
    private static final String ERROR_MESSAGE_BERHASIL = "Berhasil";
    private static final String ERROR_MESSAGE_GAGAL = "Gagal";
    private static RequestQueue g_requestqueue;
    private static Gson gson = new Gson();

    private final static  String BASE_URL = "https://login.apps.pcf.dti.co.id";
    private final static String URL_LOGIN = BASE_URL + "/bos/login";

    public static void postUserPass(final Context p_context, final String p_bos_id, final String p_password){
        g_requestqueue = Volley.newRequestQueue(p_context);

//        Log.d(TAG, p_product.getImage_path());

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", p_bos_id);
        params.put("password", p_password);

        JsonObjectRequest request_json = new JsonObjectRequest(URL_LOGIN, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        String message = getErrorMessage(response.toString());
                        if(message.equals(ERROR_MESSAGE_BERHASIL)){
                            LoginActivity.g_instance.intentLogin();
                        }
                        else
                        {
                            Toast.makeText(p_context, "Login Gagal", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setErrorMessage(error);
            }
        });


        g_requestqueue.add(request_json);
    }

    public static void setErrorMessage(VolleyError volleyError){
        String message = "";
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
        Log.d(TAG, message);
    }

    public static String getOutputSchema(String p_response){
        try {
            JSONObject objGeneral = new JSONObject(p_response);
            if(objGeneral.get("output_schema") instanceof JSONObject){
                JSONObject tmpObject = objGeneral.getJSONObject("output_schema");
                return tmpObject.toString();
            }else if(objGeneral.get("output_schema") instanceof JSONArray){
                JSONArray tmpArray = objGeneral.getJSONArray("output_schema");
                return tmpArray.toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getErrorMessage(String p_response){
        try {
            JSONObject objGeneral = new JSONObject(p_response);
            if(objGeneral.get("error_schema") instanceof JSONObject){
                JSONObject tmpObject = objGeneral.getJSONObject("error_schema");
                JSONObject error_message = tmpObject.getJSONObject("error_message");

                return error_message.getString("indonesian");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }


}
