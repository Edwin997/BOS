package com.example.bca_bos.networks;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bca_bos.models.products.Product;
import com.example.bca_bos.models.Seller;
import com.example.bca_bos.models.TemplatedText;
import com.example.bca_bos.ui.produk.ProdukAdapter;
import com.example.bca_bos.ui.template.TemplateAdapter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class VolleyClass {

    private static final String TAG = "VOLLEY";
    private static final String ERROR_MESSAGE_BERHASIL = "Berhasil";
    private static final String ERROR_MESSAGE_GAGAL = "Gagal";
    private static RequestQueue g_requestqueue;
    private static Gson gson = new Gson();

//    private final static  String BASE_URL = "http://10.26.34.119:8321";
    private final static  String BASE_URL = "https://templatetext.apps.pcf.dti.co.id";
//    private final static  String BASE_URL = "http://10.1.132.28/server/server.php";
    private final static String URL_TEMPLATED_TEXT = BASE_URL + "/bos/templateText";
    private final static String URL_PRODUCT = BASE_URL + "/bos/product";
    private final static String URL_LOGIN = BASE_URL + "/bos/login";
    private final static String URL_PROFILE = BASE_URL + "/bos/profile";

    //region TEMPLATED TEXT
    public static void getTemplatedText(Context p_context, int p_id_seller, final TemplateAdapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_TEMPLATED_TEXT + "/" + p_id_seller,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, response);

                            String output = getOutputSchema(response);
                            Log.d(TAG, output);

//                            TemplatedText tempObject = gson.fromJson(response, TemplatedText.class);

                            List<TemplatedText> tempObject = Arrays.asList(gson.fromJson(output, TemplatedText[].class));
                            p_adapter.setListTemplate(tempObject);

                        } catch (Exception e) {
                            e.printStackTrace();
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

    public static void insertTemplatedText(final Context p_context, final TemplatedText p_templatedtext, final TemplateAdapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id_seller", String.valueOf(p_templatedtext.getSeller().getId_seller()));
        params.put("template_code", p_templatedtext.getTemplate_code());
        params.put("text", p_templatedtext.getText());

        JsonObjectRequest request_json = new JsonObjectRequest(URL_TEMPLATED_TEXT, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG, response.toString());
                            String message = getErrorMessage(response.toString());
                            if(message.equals(ERROR_MESSAGE_BERHASIL)){
                                Toast.makeText(p_context, "Penambahan data template text berhasil", Toast.LENGTH_SHORT).show();
                                getTemplatedText(p_context, p_templatedtext.getId_template_text(), p_adapter);
                            }
                            else
                            {
                                Toast.makeText(p_context, "Penambahan data template text gagal", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
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

    public static void updateTemplatedText(final Context p_context, final TemplatedText p_templatedtext, final TemplateAdapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id_template_text", String.valueOf(p_templatedtext.getId_template_text()));
        params.put("id_seller", String.valueOf(p_templatedtext.getSeller().getId_seller()));
        params.put("template_code", p_templatedtext.getTemplate_code());
        params.put("text", p_templatedtext.getText());

        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.PUT, URL_TEMPLATED_TEXT, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG, response.toString());
                            String message = getErrorMessage(response.toString());
                            if(message.equals(ERROR_MESSAGE_BERHASIL)){
                                Toast.makeText(p_context, "Pengubahan data template text berhasil", Toast.LENGTH_SHORT).show();
                                getTemplatedText(p_context, p_templatedtext.getId_template_text(), p_adapter);
                            }
                            else
                            {
                                Toast.makeText(p_context, "Pengubahan data template text gagal", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
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

    public static void deleteTemplatedText(final Context p_context, final int p_id_seller, final TemplateAdapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.DELETE ,URL_TEMPLATED_TEXT + "/" + p_id_seller,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String message = getErrorMessage(response.toString());
                            if(message.equals(ERROR_MESSAGE_BERHASIL)){
                                Toast.makeText(p_context, "Hapus data template text berhasil", Toast.LENGTH_SHORT).show();
                                getTemplatedText(p_context, p_id_seller, p_adapter);
                            }
                            else
                            {
                                Toast.makeText(p_context, "Hapus data template text gagal", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
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
    //endregion

    //region PRODUCT
    public static void getProduct(Context p_context, int p_id_seller, final ProdukAdapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_PRODUCT + "/" + p_id_seller,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, response);

                            String output = getOutputSchema(response);
                            Log.d(TAG, output);

//                            TemplatedText tempObject = gson.fromJson(response, TemplatedText.class);

                            List<Product> tempObject = Arrays.asList(gson.fromJson(output, Product[].class));
                            p_adapter.setDatasetProduk(tempObject);
                        } catch (Exception e) {
                            e.printStackTrace();
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

    public static void insertProduct(final Context p_context, final Product p_product, final ProdukAdapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        Log.d(TAG, p_product.getImage_path());

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id_seller", String.valueOf(p_product.getSeller().getId_seller()));
        params.put("id_prd_category", String.valueOf(p_product.getPrdCategory().getId_prd_category()));
        params.put("product_name", p_product.getProduct_name());
        params.put("price", String.valueOf(p_product.getPrice()));
        params.put("stock", String.valueOf(p_product.getStock()));
        params.put("base64StringImage", p_product.getImage_path());

        JsonObjectRequest request_json = new JsonObjectRequest(URL_PRODUCT, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        String message = getErrorMessage(response.toString());
                        if(message.equals(ERROR_MESSAGE_BERHASIL)){
                            Toast.makeText(p_context, "Penambahan data product berhasil", Toast.LENGTH_SHORT).show();
                            getProduct(p_context, p_product.getSeller().getId_seller(), p_adapter);
                        }
                        else
                        {
                            Toast.makeText(p_context, "Penambahan data product gagal", Toast.LENGTH_SHORT).show();
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

    public static void updateProduct(final Context p_context, final Product p_product, final ProdukAdapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id_product", String.valueOf(p_product.getId_product()));
        params.put("id_seller", String.valueOf(p_product.getSeller().getId_seller()));
        params.put("id_prd_category", String.valueOf(p_product.getPrdCategory().getId_prd_category()));
        params.put("product_name", p_product.getProduct_name());
        params.put("price", String.valueOf(p_product.getPrice()));
        params.put("stock", String.valueOf(p_product.getStock()));
        params.put("base64StringImage", p_product.getImage_path());

        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.PUT, URL_PRODUCT, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        String message = getErrorMessage(response.toString());
                        if(message.equals(ERROR_MESSAGE_BERHASIL)){
                            Toast.makeText(p_context, "Pengubahan data product berhasil", Toast.LENGTH_SHORT).show();
                            getProduct(p_context, p_product.getSeller().getId_seller(), p_adapter);
                        }
                        else
                        {
                            Toast.makeText(p_context, "Pengubahan data product gagal", Toast.LENGTH_SHORT).show();
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
    //endregion

    //region LOGIN AND REGISTER
    public static void loginProcess(Context p_context, String p_username, String p_password){
        g_requestqueue = Volley.newRequestQueue(p_context);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", p_username);
        params.put("password", p_password);

        JsonObjectRequest request_json = new JsonObjectRequest(URL_LOGIN, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG, response.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
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
    //endregion

    //region PROFILE
    public static void getProfile(Context p_context, int p_id_seller){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_PROFILE + "/" + p_id_seller,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, response);
                        } catch (Exception e) {
                            e.printStackTrace();
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

    public static void updateProfile(Context p_context, Seller p_seller){
        g_requestqueue = Volley.newRequestQueue(p_context);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id_seller", String.valueOf(p_seller.getId_seller()));
        params.put("shop_name", p_seller.getShop_name());
        params.put("id_kab_kota", String.valueOf(p_seller.getKotakab().getId_kota_kab()));
        params.put("base64StringImage", p_seller.getImage_path());

        //params put selected courier belum

        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.PUT, URL_PROFILE, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG, response.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
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
    //endregion

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

