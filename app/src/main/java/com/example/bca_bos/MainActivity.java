package com.example.bca_bos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView actvAsal, actvTujuan;
    EditText etBerat;
    Button btn;
    TextView tv;

    JSONObject cityJSON, cityRajaOngkirJSON, cityResultsJSON, costJSON, costRajaOngkirJSON, costResultJSON, costCostsJSON, costCostJSON;
    JSONArray cityResultsArray, costResultArray, costCostsArray, costCostArray;

    List<String> cityNameList = new ArrayList<>();
    List<String> serviceList = new ArrayList<>();
    List<String> estimationDayList = new ArrayList<>();
    List<String> costList = new ArrayList<>();

    final String urlGetCityRajaOngkir = "https://api.rajaongkir.com/starter/city";
    final String urlPostCostRajaOngkir = "https://api.rajaongkir.com/starter/cost";
    ArrayAdapter<String> asalAdapter;
    ArrayAdapter<String> tujuanAdapter;

    String gAsal, gTujuan, gBerat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actvAsal = findViewById(R.id.asalAutoCompleteTextView);
        actvTujuan = findViewById(R.id.tujuanAutoCompleteTextView);
        etBerat = findViewById(R.id.beratEditText);

        btn = findViewById(R.id.button);
        tv = findViewById(R.id.textView);

        getRajaOngkirCity();

        asalAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, cityNameList);
        tujuanAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, cityNameList);
        actvAsal.setAdapter(asalAdapter);
        actvTujuan.setAdapter(tujuanAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("");
                getAsalCityId();
                getTujuanCityId();
                gBerat = etBerat.getText().toString();
                getRajaOngkirCost();
            }
        });
    }

    private void getRajaOngkirCity() {

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        StringRequest sr = new StringRequest(Request.Method.GET, urlGetCityRajaOngkir, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String cityResponseJSON = response;
                try {
                    cityJSON = new JSONObject(cityResponseJSON);
                    cityRajaOngkirJSON = cityJSON.getJSONObject("rajaongkir");
                    cityResultsArray = cityRajaOngkirJSON.getJSONArray("results");

                    for (int i =0; i < cityResultsArray.length(); i++){
                        cityResultsJSON = cityResultsArray.getJSONObject(i);
                        cityNameList.add(cityResultsJSON.getString("city_name"));
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
                tv.setText(message);
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

    }

    private void getRajaOngkirCost(){

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        StringRequest sr = new StringRequest(Request.Method.POST, urlPostCostRajaOngkir, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       String costResponseJSON = response;
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
                                String tmpOngkir = serviceList.get(i)+" - "+estimationDayList.get(i)+" hari - "+costList.get(i)+"\n";
                                tv.append(tmpOngkir);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("origin", gAsal);
                params.put("destination", gTujuan);
                params.put("weight", gBerat);
                params.put("courier", "jne");
                return params;
            }

        };

        queue.add(sr);

    }

    private void getAsalCityId(){
        gAsal = String.valueOf(cityNameList.indexOf(actvAsal.getText().toString())+1);
    }

    private void getTujuanCityId(){
        gTujuan = String.valueOf(cityNameList.indexOf(actvTujuan.getText().toString())+1);
    }

}



