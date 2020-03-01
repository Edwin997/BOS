package com.example.bca_bos;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RajaOngkir {

    static ArrayAdapter<String> asalAdapter;
    static ArrayAdapter<String> tujuanAdapter;
    public static List<String> cityNameList  = new ArrayList<>();

    public static ArrayAdapter<String> getRajaOngkirCity(Context context) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String urlGetCityRajaOngkir = "https://api.rajaongkir.com/starter/city";
        final JSONObject[] cityJSON = new JSONObject[1];
        final JSONObject[] cityRajaOngkirJSON = new JSONObject[1];
        final JSONObject[] cityResultsJSON = new JSONObject[1];
        final JSONArray[] cityResultsArray = new JSONArray[1];

        StringRequest sr = new StringRequest(Request.Method.GET, urlGetCityRajaOngkir, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String cityResponseJSON = response;
                try {
                    cityNameList.clear();
                    cityJSON[0] = new JSONObject(cityResponseJSON);
                    cityRajaOngkirJSON[0] = cityJSON[0].getJSONObject("rajaongkir");
                    cityResultsArray[0] = cityRajaOngkirJSON[0].getJSONArray("results");

                    for (int i = 0; i < cityResultsArray[0].length(); i++){
                        cityResultsJSON[0] = cityResultsArray[0].getJSONObject(i);
                        cityNameList.add(cityResultsJSON[0].getString("city_name"));
                    }
                    asalAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String message = "";
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("key", "63ab191d920c76c31dc6cfc441b5da33");
                return params;
            }
        };
        sr.setTag("getdata");
        queue.add(sr);
        asalAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, cityNameList);
        return asalAdapter;
    }

    static final String urlPostCostRajaOngkir = "https://api.rajaongkir.com/starter/cost";
    static List<String> serviceList = new ArrayList<>();
    static List<String> estimationDayList = new ArrayList<>();
    static List<String> costList = new ArrayList<>();
    private static JSONObject costJSON, costRajaOngkirJSON, costResultJSON, costCostsJSON, costCostJSON;
    private static JSONArray costResultArray, costCostsArray, costCostArray;
    static String namaKurir, tmpOngkir;

    public static String getRajaOngkirCost(Context context, final String gAsal, final String gTujuan, final String gBerat, final String gKurir, final KeyboardBOS parent){

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest sr = new StringRequest(Request.Method.POST, urlPostCostRajaOngkir, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tmpOngkir = "Daftar Ongkir "+ gKurir.toUpperCase() +":";
                serviceList.clear();
                estimationDayList.clear();
                costList.clear();
                String costResponseJSON = response;
                Log.d("COBA", response);
                try {
                    costJSON = new JSONObject(costResponseJSON);
                    costRajaOngkirJSON = costJSON.getJSONObject("rajaongkir");
                    costResultArray = costRajaOngkirJSON.getJSONArray("results");
                    costResultJSON = costResultArray.getJSONObject(0);
                    costCostsArray = costResultJSON.getJSONArray("costs");

                    for (int i = 0; i < costCostsArray.length(); i++){

                        costCostsJSON = costCostsArray.getJSONObject(i);
                        serviceList.add(costCostsJSON.getString("service"));
                        costCostArray = costCostsJSON.getJSONArray("cost");
                        costCostJSON = costCostArray.getJSONObject(0);
                        estimationDayList.add(costCostJSON.getString("etd"));
                        costList.add(costCostJSON.getString("value"));
                        if (!gKurir.equals("pos")){
                            tmpOngkir = tmpOngkir + "\n" + serviceList.get(i)+" - "+estimationDayList.get(i)+" hari - "+costList.get(i);
                        }else {
                            tmpOngkir = tmpOngkir + "\n" + serviceList.get(i)+" - "+estimationDayList.get(i).toLowerCase()+" - "+costList.get(i);
                        }

                        Log.d("COBA", tmpOngkir);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tmpOngkir = tmpOngkir + "\n";
                parent.commitTextToBOSKeyboardEditTextWoy(tmpOngkir);
            }
        },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
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
                        Log.d("COBA", message);
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("key", "63ab191d920c76c31dc6cfc441b5da33");
                headers.put("content-type", "application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.d("COBA", gAsal);
                Log.d("COBA", gTujuan);
                Log.d("COBA", gBerat);
                Log.d("COBA", gKurir);
                Map<String, String> params = new HashMap<String, String>();
                params.put("origin", gAsal);
                params.put("destination", gTujuan);
                params.put("weight", gBerat);
                params.put("courier", gKurir);
                return params;
            }

        };

        queue.add(sr);

        return tmpOngkir;
    }


    public static String getRajaOngkirCost(Context context, final String gAsal, final String gTujuan, final String gBerat, final String gKurir, final KeyboardBOSnew parent){

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest sr = new StringRequest(Request.Method.POST, urlPostCostRajaOngkir, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tmpOngkir = "Daftar Ongkir "+ gKurir.toUpperCase() +":";
                serviceList.clear();
                estimationDayList.clear();
                costList.clear();
                String costResponseJSON = response;
                Log.d("COBA", response);
                try {
                    costJSON = new JSONObject(costResponseJSON);
                    costRajaOngkirJSON = costJSON.getJSONObject("rajaongkir");
                    costResultArray = costRajaOngkirJSON.getJSONArray("results");
                    costResultJSON = costResultArray.getJSONObject(0);
                    costCostsArray = costResultJSON.getJSONArray("costs");

                    for (int i = 0; i < costCostsArray.length(); i++){

                        costCostsJSON = costCostsArray.getJSONObject(i);
                        serviceList.add(costCostsJSON.getString("service"));
                        costCostArray = costCostsJSON.getJSONArray("cost");
                        costCostJSON = costCostArray.getJSONObject(0);
                        estimationDayList.add(costCostJSON.getString("etd"));
                        costList.add(costCostJSON.getString("value"));
                        if (!gKurir.equals("pos")){
                            tmpOngkir = tmpOngkir + "\n" + serviceList.get(i)+" - "+estimationDayList.get(i)+" hari - "+costList.get(i);
                        }else {
                            tmpOngkir = tmpOngkir + "\n" + serviceList.get(i)+" - "+estimationDayList.get(i).toLowerCase()+" - "+costList.get(i);
                        }

                        Log.d("COBA", tmpOngkir);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tmpOngkir = tmpOngkir + "\n";
                parent.commitTextToBOSKeyboardEditText(tmpOngkir);
            }
        },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
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
                        Log.d("COBA", message);
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("key", "63ab191d920c76c31dc6cfc441b5da33");
                headers.put("content-type", "application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.d("COBA", gAsal);
                Log.d("COBA", gTujuan);
                Log.d("COBA", gBerat);
                Log.d("COBA", gKurir);
                Map<String, String> params = new HashMap<String, String>();
                params.put("origin", gAsal);
                params.put("destination", gTujuan);
                params.put("weight", gBerat);
                params.put("courier", gKurir);
                return params;
            }

        };

        queue.add(sr);

        return tmpOngkir;
    }

}
