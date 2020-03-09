package com.example.bca_bos.networks;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RajaOngkir {

    private static String TAG_VOLLEY = "volley";

    //DATA MEMBER API KEY + URL
    private static final String API_KEY_RAJA_ONGKIR = "63ab191d920c76c31dc6cfc441b5da33";

    private static final String URL_GET_CITY_RAJA_ONGKIR = "https://api.rajaongkir.com/starter/city";
    private static final String URL_POST_COST_RAJA_ONGKIR = "https://api.rajaongkir.com/starter/cost";

    //DATA MEMBER getRajaOngkirCost()
    private static List<String> serviceList = new ArrayList<>();
    private static List<String> estimationDayList = new ArrayList<>();
    private static List<String> costList = new ArrayList<>();
    private static String g_textongkir;

    //DATA MEMBER getRajaOngkirCity()
    private static ArrayAdapter<String> g_rajaongkir_city_adapter;
    public static List<String> g_city_name_list = new ArrayList<>();;

    public static ArrayAdapter<String> getRajaOngkirCity(Context context) {

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.GET, URL_GET_CITY_RAJA_ONGKIR, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                g_city_name_list.clear();

                String cityResponseJSON = response;

                try {
                    JSONObject cityJSON = new JSONObject(cityResponseJSON);
                    JSONObject cityRajaOngkirJSON = cityJSON.getJSONObject("rajaongkir");

                    JSONArray cityResultsArray = cityRajaOngkirJSON.getJSONArray("results");
                    for (int i = 0; i < cityResultsArray.length(); i++){
                        JSONObject cityResultsJSON = cityResultsArray.getJSONObject(i);

                        g_city_name_list.add(cityResultsJSON.getString("city_name"));
                    }

                    g_rajaongkir_city_adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.d(TAG_VOLLEY, "onResponse: ");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAG_VOLLEY, checkErrorMessage(volleyError));
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("key", API_KEY_RAJA_ONGKIR);
                return params;
            }
        };
        sr.setTag("getdata");
        queue.add(sr);

        g_rajaongkir_city_adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, g_city_name_list);
        return g_rajaongkir_city_adapter;
    }

    public static String getRajaOngkirCost(final KeyboardBOS parent, final String gAsal, final String gTujuan, final String gBerat, final String gKurir){

        RequestQueue queue = Volley.newRequestQueue(parent.getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST, URL_POST_COST_RAJA_ONGKIR, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                g_textongkir = "Daftar Ongkir "+ gKurir.toUpperCase() +":";

                serviceList.clear();
                estimationDayList.clear();
                costList.clear();

                String costResponseJSON = response;

                try {
                    JSONObject costJSON = new JSONObject(costResponseJSON);
                    JSONObject costRajaOngkirJSON = costJSON.getJSONObject("rajaongkir");

                    JSONArray costResultArray = costRajaOngkirJSON.getJSONArray("results");

                    JSONObject costResultJSON = costResultArray.getJSONObject(0);

                    JSONArray costCostsArray = costResultJSON.getJSONArray("costs");
                    for (int i = 0; i < costCostsArray.length(); i++){
                        JSONObject costCostsJSON = costCostsArray.getJSONObject(i);
                        serviceList.add(costCostsJSON.getString("service"));

                        JSONArray costCostArray = costCostsJSON.getJSONArray("cost");

                        JSONObject costCostJSON = costCostArray.getJSONObject(0);

                        estimationDayList.add(costCostJSON.getString("etd"));
                        costList.add(costCostJSON.getString("value"));

                        if (!gKurir.equals("pos")){
                            g_textongkir = g_textongkir + "\n" + serviceList.get(i)+" - "+estimationDayList.get(i)+" hari - "+costList.get(i);
                        }else {
                            g_textongkir = g_textongkir + "\n" + serviceList.get(i)+" - "+estimationDayList.get(i).toLowerCase()+" - "+costList.get(i);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                g_textongkir = g_textongkir + "\n";
                parent.commitTextToBOSKeyboardEditText(g_textongkir);
            }
        },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG_VOLLEY, checkErrorMessage(error));
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("key", API_KEY_RAJA_ONGKIR);
                headers.put("content-type", "application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("origin", gAsal);
                params.put("destination", gTujuan);
                params.put("weight", gBerat);
                params.put("courier", gKurir);
                return params;
            }

        };

        queue.add(sr);
        return g_textongkir;
    }

    private static String checkErrorMessage(VolleyError error){
        String message = "";
        if (error instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
        } else if (error instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (error instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
        }
        return message;
    }
}
