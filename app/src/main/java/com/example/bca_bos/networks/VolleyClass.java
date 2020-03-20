package com.example.bca_bos.networks;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bca_bos.LoginActivity;
import com.example.bca_bos.PasswordActivity;
import com.example.bca_bos.keyboardadapters.KirimFormProdukAdapter;
import com.example.bca_bos.keyboardadapters.StokProdukAdapter;
import com.example.bca_bos.keyboardadapters.TemplatedTextAdapter;
import com.example.bca_bos.models.Buyer;
import com.example.bca_bos.models.products.PrdCategory;
import com.example.bca_bos.models.products.Product;
import com.example.bca_bos.models.Seller;
import com.example.bca_bos.models.TemplatedText;
import com.example.bca_bos.ui.beranda.BerandaFragment;
import com.example.bca_bos.ui.beranda.BerandaPembeliAdapter;
import com.example.bca_bos.ui.beranda.BerandaProdukAdapter;
import com.example.bca_bos.models.transactions.Transaction;
import com.example.bca_bos.ui.produk.ProdukAdapter;
import com.example.bca_bos.ui.produk.ProdukFragment;
import com.example.bca_bos.ui.profile.ProfileFragment;
import com.example.bca_bos.ui.template.TemplateAdapter;
import com.example.bca_bos.ui.template.TemplateFragment;
import com.example.bca_bos.ui.transaksi.OfflineTransaksiAdapter;
import com.example.bca_bos.ui.transaksi.OfflineTransaksiFragment;
import com.example.bca_bos.ui.transaksi.OnlineTransaksiAdapter;
import com.example.bca_bos.ui.transaksi.OnlineTransaksiFragment;
import com.example.bca_bos.ui.transaksi.TransaksiFragment;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class VolleyClass {

    private static final String ERROR_CODE_BERHASIL = "BIT-000";
    private static final String ERROR_CODE_BOS_ID_TIDAK_DITEMUKAN = "BOS-201";
    private static final String ERROR_CODE_PASSWORD_SALAH = "BOS-200";

    private static RequestQueue g_requestqueue;
    private static Gson gson = new Gson();

    private final static String TAG = "BOSVOLLEY";
    private final static  String BASE_URL = "http://10.26.34.119:8321";

    //URL BERANDA
    private final static  String BASE_URL_BERANDA= "https://home.apps.pcf.dti.co.id";
    private final static String URL_BERANDA_JUMLAH_TRANSAKSI = BASE_URL_BERANDA + "/home/trx?seller=";
    private final static String URL_BERANDA_PRODUK_TERLARIS = BASE_URL_BERANDA + "/home/prd?seller=";
    private final static String URL_BERANDA_PEMBELI_SETIA = BASE_URL_BERANDA + "/home/byr?seller=";
    private final static String JUMLAH_TRANSAKSI_START_DATE = "&start-dt=";
    private final static String JUMLAH_TRANSAKSI_END_DATE = "&end-dt=";

    // URL TEMPLATE TEXT
    private final static  String BASE_URL_TEMPLATED_TEXT= "https://templatetext.apps.pcf.dti.co.id";
    private final static String URL_TEMPLATED_TEXT = BASE_URL_TEMPLATED_TEXT + "/bos/templateText";

    //URL PRODUK
    private final static  String BASE_URL_PRODUCT = "https://product.apps.pcf.dti.co.id";
    private final static String URL_PRODUCT = BASE_URL_PRODUCT + "/bos/product";
    private final static String URL_PRODUCT_CATEGORY = BASE_URL_PRODUCT + "/bos/productCategory";
    private final static String URL_PRODUK_DETAIL = BASE_URL_PRODUCT + "/bos/productDetail/";
    private static List<PrdCategory> g_list_product_category = new ArrayList<>();
    private static String KEY_SEMUA_PRODUCT = "Semua Produk";

    // URL TRANSAKSI
    private final static  String BASE_URL_TRANSACTION= "https://transaction.apps.pcf.dti.co.id";
    private final static String URL_TRANSACTION_ONLINE = BASE_URL_TRANSACTION + "/bos/onlineTransaction";
    private final static String URL_TRANSACTION_OFFLINE = BASE_URL_TRANSACTION + "/bos/offlineTransaction";
    private final static String URL_TRANSACTION_DETAIL = BASE_URL_TRANSACTION + "/bos/onlineTransactionDetail";
    private final static String URL_TRANSACTION_DETAIL_OFFLINE = BASE_URL_TRANSACTION + "/bos/offlineTransactionDetail";

    private final static  String BASE_URL_LOGIN = "https://login.apps.pcf.dti.co.id";
    private final static String URL_LOGIN = BASE_URL_LOGIN + "/bos/login";

    //URL PROFILE
    private final static  String BASE_URL_PROFILE= "https://profile.apps.pcf.dti.co.id";
    private final static String URL_PROFILE = BASE_URL_PROFILE + "/bos/profile";
    private final static String URL_PROFILE_CHANGE_PASSWORD = BASE_URL_PROFILE + "/bos/profile/pass";

    //region BERANDA

    public static void getProfileForBeranda(Context p_context, int p_id_seller){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_PROFILE + "/" + p_id_seller,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("BOSVOLLEY", response);
                            String output = NetworkUtil.getOutputSchema(response);

                            Seller tempObject = gson.fromJson(output, Seller.class);
                            BerandaFragment.g_instance.refreshNamaToko(tempObject);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        });

        g_requestqueue.add(request_json);
    }

    public static void getSemuaJumlahTransaksi(final Context p_context, int p_id_seller){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET , URL_BERANDA_JUMLAH_TRANSAKSI + p_id_seller,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("BOSVOLLEY", response);
                            JSONObject objGeneral = new JSONObject(response);
                            JSONArray tmpObjectArray = objGeneral.getJSONArray("output_schema");
                            JSONObject tmpObject = tmpObjectArray.getJSONObject(0);
                            String tmp_nominal_jumlah_transaksi = tmpObject.getString("nom_trx");
                            String tmp_jumlah_transaksi = tmpObject.getString("sum_trx");

                            BerandaFragment.g_instance.refreshJumlahTransaksi(tmp_nominal_jumlah_transaksi, tmp_jumlah_transaksi);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        });

        g_requestqueue.add(request_json);
    }

    public static void getJumlahTransaksiBulanIni(final Context p_context, int p_id_seller){
        g_requestqueue = Volley.newRequestQueue(p_context);

        Date tmp_today_date = Calendar.getInstance().getTime();
        SimpleDateFormat start_date_formatter = new SimpleDateFormat("yyyy-MM-01");
        String tmp_start_date_string = start_date_formatter.format(tmp_today_date);

        int tmp_end_day_of_month = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        SimpleDateFormat end_date_formatter = new SimpleDateFormat("yyyy-MM-"+tmp_end_day_of_month);
        String tmp_end_date_string = end_date_formatter.format(tmp_today_date);


        StringRequest request_json = new StringRequest(Request.Method.GET , URL_BERANDA_JUMLAH_TRANSAKSI + p_id_seller + JUMLAH_TRANSAKSI_START_DATE + tmp_start_date_string +JUMLAH_TRANSAKSI_END_DATE+ tmp_end_date_string,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("BOSVOLLEY", response);
                            JSONObject objGeneral = new JSONObject(response);
                            JSONArray tmpObjectArray = objGeneral.getJSONArray("output_schema");
                            JSONObject tmpObject = tmpObjectArray.getJSONObject(0);
                            String tmp_nominal_jumlah_transaksi = tmpObject.getString("nom_trx");
                            String tmp_jumlah_transaksi = tmpObject.getString("sum_trx");

                            BerandaFragment.g_instance.refreshJumlahTransaksi(tmp_nominal_jumlah_transaksi, tmp_jumlah_transaksi);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        });

        g_requestqueue.add(request_json);
    }

    public static void getProdukTerlaris(final Context p_context, int p_id_seller, final RecyclerView.Adapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        //Get hari ini
        Date tmp_today_date = Calendar.getInstance().getTime();
        SimpleDateFormat end_date_formatter = new SimpleDateFormat("yyyy-MM-dd");
        String tmp_end_date_string = end_date_formatter.format(tmp_today_date);

        //Get 30 hari sebeleum hari ini
        Calendar tmp_thirty_day_before_calendar = Calendar.getInstance();
        tmp_thirty_day_before_calendar.add(Calendar.DAY_OF_MONTH, -30);
        Date tmp_thirty_day_before = tmp_thirty_day_before_calendar.getTime();
        SimpleDateFormat start_date_formatter = new SimpleDateFormat("yyyy-MM-dd");
        String tmp_start_date_string = start_date_formatter.format(tmp_thirty_day_before);


        StringRequest request_json = new StringRequest(Request.Method.GET , URL_BERANDA_PRODUK_TERLARIS + p_id_seller + JUMLAH_TRANSAKSI_START_DATE + tmp_start_date_string +JUMLAH_TRANSAKSI_END_DATE+ tmp_end_date_string,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("BOSVOLLEY", response);
                            String output = NetworkUtil.getOutputSchema(response);

                            List<Product> tempObject = Arrays.asList(gson.fromJson(output, Product[].class));
                            BerandaProdukAdapter tmpAdapter = (BerandaProdukAdapter) p_adapter;
                            tmpAdapter.setDatasetProduk(tempObject);

//                            String tmp_nominal_jumlah_transaksi = tmpObject.getString("nom_trx");
//                            String tmp_jumlah_transaksi = tmpObject.getString("sum_trx");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        });

        g_requestqueue.add(request_json);
    }

    public static void getPembeliSetia(final Context p_context, int p_id_seller, final RecyclerView.Adapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET , URL_BERANDA_PEMBELI_SETIA + p_id_seller,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("BOSVOLLEY", response);
                            String output = NetworkUtil.getOutputSchema(response);

                            List<Buyer> tempObject = Arrays.asList(gson.fromJson(output, Buyer[].class));
                            BerandaPembeliAdapter tmpAdapter = (BerandaPembeliAdapter) p_adapter;
                            tmpAdapter.setDatasetPembeli(tempObject);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        });

        g_requestqueue.add(request_json);
    }

    public static void getProdukDetail(Context p_context, int p_id_produk){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_PRODUK_DETAIL + "/" + p_id_produk,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("BOSVOLLEY", response);
                            String output = NetworkUtil.getOutputSchema(response);

                            Product tempObject = gson.fromJson(output, Product.class);
                            BerandaFragment.g_instance.refreshProduk(tempObject);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        });

        g_requestqueue.add(request_json);
    }

    //endregion

    //region TEMPLATED TEXT
    public static void getTemplatedText(Context p_context, int p_id_seller, final RecyclerView.Adapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_TEMPLATED_TEXT + "/" + p_id_seller,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String output = NetworkUtil.getOutputSchema(response);

//                            TemplatedText tempObject = gson.fromJson(response, TemplatedText.class);

                            List<TemplatedText> tempObject = Arrays.asList(gson.fromJson(output, TemplatedText[].class));

                            if(p_adapter instanceof TemplateAdapter){
                                TemplateAdapter tmpAdapter = (TemplateAdapter) p_adapter;
                                tmpAdapter.setListTemplate(tempObject);
                            }
                            else if(p_adapter instanceof TemplatedTextAdapter){
                                TemplatedTextAdapter tmpAdapter = (TemplatedTextAdapter) p_adapter;
                                tmpAdapter.setListTemplate(tempObject);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        });

        g_requestqueue.add(request_json);
    }

    public static void insertTemplatedText(final Context p_context, final TemplatedText p_templatedtext, final RecyclerView.Adapter p_adapter){
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
                            String message = NetworkUtil.getErrorCode(response.toString());
                            if(message.equals(ERROR_CODE_BERHASIL)){
                                Toast.makeText(p_context, "Penambahan data template text berhasil", Toast.LENGTH_SHORT).show();
                                getTemplatedText(p_context, p_templatedtext.getId_template_text(), p_adapter);
                                TemplateFragment.g_instance.refreshData();
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
                NetworkUtil.setErrorMessage(error);
            }
        });


        g_requestqueue.add(request_json);
    }

    public static void updateTemplatedText(final Context p_context, final TemplatedText p_templatedtext, final RecyclerView.Adapter p_adapter){
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
                            String message = NetworkUtil.getErrorCode(response.toString());
                            if(message.equals(ERROR_CODE_BERHASIL)){
                                Toast.makeText(p_context, "Pengubahan data template text berhasil", Toast.LENGTH_SHORT).show();
                                getTemplatedText(p_context, p_templatedtext.getId_template_text(), p_adapter);
                                TemplateFragment.g_instance.refreshData();
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
                NetworkUtil.setErrorMessage(error);
            }
        });


        g_requestqueue.add(request_json);
    }

    public static void deleteTemplatedText(final Context p_context, final int p_id_templatetext, final RecyclerView.Adapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.DELETE ,URL_TEMPLATED_TEXT + "/" + p_id_templatetext,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String message = NetworkUtil.getErrorCode(response.toString());
                            if(message.equals(ERROR_CODE_BERHASIL)){
                                Toast.makeText(p_context, "Hapus data template text berhasil", Toast.LENGTH_SHORT).show();
                                getTemplatedText(p_context, p_id_templatetext, p_adapter);
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
                NetworkUtil.setErrorMessage(error);
            }
        });

        g_requestqueue.add(request_json);
    }
    //endregion

    //region PRODUCT
    public static void getProduct(Context p_context, int p_id_seller, final RecyclerView.Adapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_PRODUCT + "/" + p_id_seller,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("BOSVOLLEY", response);
                            String output = NetworkUtil.getOutputSchema(response);

//                            TemplatedText tempObject = gson.fromJson(response, TemplatedText.class);
                            List<Product> tempObject = Arrays.asList(gson.fromJson(output, Product[].class));

                            Log.d("BOSVOLLEY", tempObject.get(0).getPrdCategory().getId_prd_category() + "coy");
                            if(p_adapter instanceof ProdukAdapter){
                                ProdukAdapter tmpAdapter = (ProdukAdapter) p_adapter;
                                tmpAdapter.setDatasetProduk(tempObject);
                            }
                            else if(p_adapter instanceof StokProdukAdapter){
                                List<Product> tmpList = new ArrayList<>();
                                tmpList.add(0, new Product(-1, "Tambah Produk", 0, "", 0, new PrdCategory(-1, "kosong")));
                                for (int i = 0; i < tempObject.size(); i++){
                                    tmpList.add(tempObject.get(i));
                                }

                                StokProdukAdapter tmpAdapter = (StokProdukAdapter) p_adapter;
                                tmpAdapter.setDatasetProduk(tmpList);
                            }
                            else if(p_adapter instanceof KirimFormProdukAdapter){
                                KirimFormProdukAdapter tmpAdapter = (KirimFormProdukAdapter) p_adapter;
                                tmpAdapter.setDatasetProduk(tempObject);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        });

        g_requestqueue.add(request_json);
    }

    public static void insertProduct(final Context p_context, final Product p_product, final RecyclerView.Adapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

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
                        String message = NetworkUtil.getErrorCode(response.toString());
                        if(message.equals(ERROR_CODE_BERHASIL)){
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
                NetworkUtil.setErrorMessage(error);
            }
        });


        g_requestqueue.add(request_json);
    }

    public static void updateProduct(final Context p_context, final Product p_product, final RecyclerView.Adapter p_adapter){
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
//                        Log.d(TAG, response.toString());
                        String message = NetworkUtil.getErrorCode(response.toString());
                        if(message.equals(ERROR_CODE_BERHASIL)){
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
                NetworkUtil.setErrorMessage(error);
            }
        });


        g_requestqueue.add(request_json);
    }

    public static void deleteProduct(final Context p_context, final int p_id_product, final RecyclerView.Adapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.DELETE ,URL_PRODUCT + "/" + p_id_product,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String message = NetworkUtil.getErrorCode(response);
                            if(message.equals(ERROR_CODE_BERHASIL)){
                                Toast.makeText(p_context, "Hapus data product berhasil", Toast.LENGTH_SHORT).show();
                                getProduct(p_context, p_id_product, p_adapter);
                            }
                            else
                            {
                                Toast.makeText(p_context, "Hapus data product gagal", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        });

        g_requestqueue.add(request_json);
    }

    public static void getProductCategory(Context p_context, final ArrayAdapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_PRODUCT_CATEGORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            String output = NetworkUtil.getOutputSchema(response);

                            g_list_product_category = Arrays.asList(gson.fromJson(output, PrdCategory[].class));

                            List<String> listnama = new ArrayList<>();
                            listnama.add(KEY_SEMUA_PRODUCT);
                            for (PrdCategory category : g_list_product_category){
                                listnama.add(category.getCategory_name());
                            }



                            p_adapter.clear();
                            p_adapter.addAll(listnama);
                            p_adapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        });

        g_requestqueue.add(request_json);
    }

    public static void getProductCategory(final Context p_context, final View p_view){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_PRODUCT_CATEGORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            String output = NetworkUtil.getOutputSchema(response);

                            g_list_product_category = Arrays.asList(gson.fromJson(output, PrdCategory[].class));

                            ProdukFragment.g_instance.showCategoryPopupMenu(p_view, g_list_product_category);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        });

        g_requestqueue.add(request_json);
    }

    public static int findProductCategoryId(int position){
        int idx = 0;
        if(position > 0)
            idx = g_list_product_category.get(position - 1).getId_prd_category();

        return idx;
    }
    //endregion

    //region TRANSAKSI
    public static void getTransaksi(Context p_context, int p_id_seller, final RecyclerView.Adapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_TRANSACTION_ONLINE + "/" + p_id_seller,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, response);
                            String output = NetworkUtil.getOutputSchema(response);

//                            TemplatedText tempObject = gson.fromJson(response, TemplatedText.class);

                            List<Transaction> tempObject = Arrays.asList(gson.fromJson(output, Transaction[].class));

                            if(p_adapter instanceof OnlineTransaksiAdapter){
                                OnlineTransaksiAdapter tmpAdapter = (OnlineTransaksiAdapter) p_adapter;
                                tmpAdapter.setListTransaksi(tempObject);

                                OnlineTransaksiFragment.g_instance.firstLoad();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        });

        g_requestqueue.add(request_json);
    }

    public static void getTransaksiOffline(Context p_context, int p_id_seller, final RecyclerView.Adapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_TRANSACTION_OFFLINE + "/" + p_id_seller,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, response);
                            String output = NetworkUtil.getOutputSchema(response);

//                            TemplatedText tempObject = gson.fromJson(response, TemplatedText.class);

                            List<Transaction> tempObject = Arrays.asList(gson.fromJson(output, Transaction[].class));

                            if(p_adapter instanceof OfflineTransaksiAdapter){
                                OfflineTransaksiAdapter tmpAdapter = (OfflineTransaksiAdapter) p_adapter;
                                tmpAdapter.setListTransaksi(tempObject);
                                OfflineTransaksiFragment.g_instance.firstLoad();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        });

        g_requestqueue.add(request_json);
    }

    public static void getTransaksiDetail(Context p_context, int p_id_transaksi, final int p_status){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_TRANSACTION_DETAIL + "/" + p_id_transaksi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, response);
                            String output = NetworkUtil.getOutputSchema(response);

                            Transaction tempObject = gson.fromJson(output, Transaction.class);

                            if(p_status == OnlineTransaksiFragment.g_instance.KEY_STATUS_BARUMASUK){
                                OnlineTransaksiFragment.g_instance.showBottomSheetPesananBaru(tempObject);
                            }
                            else if(p_status == OnlineTransaksiFragment.g_instance.KEY_STATUS_SUDAHDIBAYAR){
                                OnlineTransaksiFragment.g_instance.showBottomSheetPesananDibayar(tempObject);
                            }
                            else if(p_status == OnlineTransaksiFragment.g_instance.KEY_STATUS_SUDAHDIKIRIM){
                                OnlineTransaksiFragment.g_instance.showBottomSheetPesananDikirim(tempObject);
                            }
                            else if(p_status == OnlineTransaksiFragment.g_instance.KEY_STATUS_SELESAI){
                                OnlineTransaksiFragment.g_instance.showBottomSheetPesananSelesai(tempObject);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        });

        g_requestqueue.add(request_json);
    }

    public static void getTransaksiDetailOffline(Context p_context, int p_id_transaksi, final int p_status){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_TRANSACTION_DETAIL_OFFLINE + "/" + p_id_transaksi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, response);
                            String output = NetworkUtil.getOutputSchema(response);

                            Transaction tempObject = gson.fromJson(output, Transaction.class);

                            OfflineTransaksiFragment.g_instance.showBottomSheetTransaksiOffline(tempObject);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        });

        g_requestqueue.add(request_json);
    }
    //endregion

    //region LOGIN AND REGISTER
    public static void loginByID(final Context p_context, final String p_bos_id, String p_password){
        g_requestqueue = Volley.newRequestQueue(p_context);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", p_bos_id);
        params.put("password", p_password);

        JsonObjectRequest request_json = new JsonObjectRequest(URL_LOGIN, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, response.toString());
                        String message = NetworkUtil.getErrorCode(response.toString());
                        if (message.equals(ERROR_CODE_PASSWORD_SALAH)){
                            LoginActivity.g_instance.intentLogin(p_bos_id);
                        }else if (message.equals(ERROR_CODE_BOS_ID_TIDAK_DITEMUKAN)){
                            LoginActivity.g_instance.setError();
                        }
                        else {
                            Toast.makeText(p_context, message, Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        });

        g_requestqueue.add(request_json);
    }

    public static void loginByPassword(final Context p_context, final String p_bos_id, String p_password){
        g_requestqueue = Volley.newRequestQueue(p_context);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", p_bos_id);
        params.put("password", p_password);

        JsonObjectRequest request_json = new JsonObjectRequest(URL_LOGIN, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, response.toString());
                        String message = NetworkUtil.getErrorCode(response.toString());
                        String output = NetworkUtil.getOutputSchema(response.toString());

                        if(message.equals(ERROR_CODE_BERHASIL)){
                            //Get BOS ID
                            int tmp_id_seller = 0;
                            try {
                                Seller tempObject = gson.fromJson(output, Seller.class);
                                tmp_id_seller = tempObject.getId_seller();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            PasswordActivity.g_instance.intentLogin(tmp_id_seller);

                        }else if(message.equals(ERROR_CODE_PASSWORD_SALAH)){
                            PasswordActivity.g_instance.setErrorPasswordSalah();
                        }else if(message.equals(ERROR_CODE_BOS_ID_TIDAK_DITEMUKAN)){
                            PasswordActivity.g_instance.setErrorIDSalah();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
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
                            Log.d("BOSVOLLEY", response);
                            String output = NetworkUtil.getOutputSchema(response);

                            Seller tempObject = gson.fromJson(output, Seller.class);
                            ProfileFragment.g_instance.refreshLayout(tempObject);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        });

        g_requestqueue.add(request_json);
    }

    public static void updateProfile(Context p_context, Seller p_seller) throws JSONException {
        g_requestqueue = Volley.newRequestQueue(p_context);

        JSONArray jsonArray = new JSONArray();
        JSONObject firstjsonObject = new JSONObject();
        JSONObject secondjsonObject = new JSONObject();
        JSONObject thirdjsonObject = new JSONObject();

        firstjsonObject.put("id_courier", p_seller.getSelected_courier().get(0).getId_courier());
        firstjsonObject.put("is_selected", p_seller.getSelected_courier().get(0).getIs_selected());
        secondjsonObject.put("id_courier", p_seller.getSelected_courier().get(1).getId_courier());
        secondjsonObject.put("is_selected",  p_seller.getSelected_courier().get(1).getIs_selected());
        thirdjsonObject.put("id_courier", p_seller.getSelected_courier().get(2).getId_courier());
        thirdjsonObject.put("is_selected",  p_seller.getSelected_courier().get(2).getIs_selected());


        jsonArray.put(0, firstjsonObject);
        jsonArray.put(1, secondjsonObject);
        jsonArray.put(2, thirdjsonObject);


        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("id_seller", String.valueOf(p_seller.getId_seller()));
        params.put("shop_name", p_seller.getShop_name());
        params.put("id_kota_kab", p_seller.getKota_kab().getId_kota_kab());
        params.put("base64StringImage", p_seller.getBase64StringImage());
        params.put("selected_courier", jsonArray);
        //params put selected courier

        Log.d("CEEEKKK", new JSONObject(params).toString());

        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.PUT, URL_PROFILE, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            ProfileFragment.g_instance.refreshLayoutAfterPut();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        });


        g_requestqueue.add(request_json);
    }

    public static void changePassword(final Context p_context, final String p_id_seller, String p_o_password, String p_n_password, String p_c_password){
        g_requestqueue = Volley.newRequestQueue(p_context);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id_seller", p_id_seller);
        params.put("o_password", p_o_password);
        params.put("n_password",p_n_password);
        params.put("c_password",p_c_password);

        JsonObjectRequest request_json = new JsonObjectRequest(URL_PROFILE_CHANGE_PASSWORD, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String message = NetworkUtil.getErrorCode(response.toString());

                        try {
                            JSONObject objGeneral = new JSONObject(response.toString());
                            String output = objGeneral.getString("output_schema");

                            if(message.equals(ERROR_CODE_BERHASIL)){
                                Toast.makeText(p_context, "Berhasil", Toast.LENGTH_SHORT).show();
                            }else if(output.equals("Old password is wrong")){
                                Toast.makeText(p_context, "Password lama salah", Toast.LENGTH_SHORT).show();
                            }else if(output.equals("New password did not match")){
                                Toast.makeText(p_context, "Konfirmasi password tidak sama", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        });

        g_requestqueue.add(request_json);
    }


    //endregion
}

