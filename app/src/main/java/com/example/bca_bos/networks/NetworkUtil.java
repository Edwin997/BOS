package com.example.bca_bos.networks;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class NetworkUtil {

    private static final String TAG = "BOSVOLLEY";

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

    public static String getErrorCode(String p_response){
        try {
            JSONObject objGeneral = new JSONObject(p_response);
            if(objGeneral.get("error_schema") instanceof JSONObject){
                JSONObject tmpObject = objGeneral.getJSONObject("error_schema");
                JSONObject error_code = tmpObject.getJSONObject("error_message");

                return error_code.getString("indonesian");
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

    public static void disableSSL() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception e) {
        }
    }
}
