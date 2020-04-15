package com.example.bca_bos.networks;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bca_bos.FillDataActivity;
import com.example.bca_bos.KeyboardBOSnew;
import com.example.bca_bos.LoginActivity;
import com.example.bca_bos.OTPActivity;
import com.example.bca_bos.Method;
import com.example.bca_bos.PasswordActivity;
import com.example.bca_bos.R;
import com.example.bca_bos.RegisterActivity;
import com.example.bca_bos.cobaImage;
import com.example.bca_bos.keyboardadapters.KirimFormProdukAdapter;
import com.example.bca_bos.keyboardadapters.MutasiRekeningAdapter;
import com.example.bca_bos.keyboardadapters.OfflineMutasiRekeningAdapter;
import com.example.bca_bos.keyboardadapters.StokProdukAdapter;
import com.example.bca_bos.keyboardadapters.TemplatedTextAdapter;
import com.example.bca_bos.models.Buyer;
import com.example.bca_bos.models.locations.KotaKab;
import com.example.bca_bos.models.products.PrdCategory;
import com.example.bca_bos.models.products.Product;
import com.example.bca_bos.models.Seller;
import com.example.bca_bos.models.TemplatedText;
import com.example.bca_bos.ui.beranda.BerandaFragment;
import com.example.bca_bos.ui.beranda.BerandaPembeliAdapter;
import com.example.bca_bos.ui.beranda.BerandaProdukAdapter;
import com.example.bca_bos.models.transactions.Transaction;
import com.example.bca_bos.ui.beranda.BerandaTawarkanPembeliAdapter;
import com.example.bca_bos.ui.beranda.BerandaTawarkanProdukAdapter;
import com.example.bca_bos.ui.produk.ProdukAdapter;
import com.example.bca_bos.ui.produk.ProdukFragment;
import com.example.bca_bos.ui.profile.ProfileFragment;
import com.example.bca_bos.ui.template.TemplateAdapter;
import com.example.bca_bos.ui.template.TemplateFragment;
import com.example.bca_bos.ui.transaksi.OfflineTransaksiAdapter;
import com.example.bca_bos.ui.transaksi.OfflineTransaksiFragment;
import com.example.bca_bos.ui.transaksi.OnlineTransaksiAdapter;
import com.example.bca_bos.ui.transaksi.OnlineTransaksiFragment;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VolleyClass {

    private static final String ERROR_CODE_BERHASIL = "BIT-000";
    private static final String ERROR_CODE_AKUN_BELUM_TERVERIFIKASI = "BOS-202";
    private static final String ERROR_CODE_BOS_ID_TIDAK_DITEMUKAN = "BOS-201";
    private static final String ERROR_CODE_PASSWORD_SALAH = "BOS-200";

    private static RequestQueue g_requestqueue;
    private static Gson gson = new Gson();

    private final static String TAG = "BOSVOLLEY";
    public final static String BASE_WEB_URL = "https://bos.bca.co.id";
    private final static String BASE_URL = "https://apigateway.apps.pcf.dti.co.id";

    private final static String URLRRR = "https://apigateway.apps.pcf.dti.co.id/product/bos/getImage/20200409_165540988.jpg";

    //URL DATA SCIENCE
//    private final static  String BASE_URL_DATASCIENCE = "https://datasciencebos.apps.pcf.dti.co.id";
    private final static  String BASE_URL_DATASCIENCE = "/datasciencebos";
    private final static String URL_BUYER_RECOMMENDATION = BASE_URL + BASE_URL_DATASCIENCE + "/buyer-recommendation";
    private final static String URL_PRODUCT_RECOMMENDATION = BASE_URL + BASE_URL_DATASCIENCE + "/product-recommendation";
    public static List<String> g_datascience_buyer_name = new ArrayList<>();
    public static List<String> g_datascience_buyer_phone_number = new ArrayList<>();
    public static List<String> g_datascience_product_name = new ArrayList<>();
    public static List<String> g_datascience_price = new ArrayList<>();

    //URL BERANDA
    private final static  String BASE_URL_BERANDA= "/home";
    private final static String URL_BERANDA_JUMLAH_TRANSAKSI = BASE_URL + BASE_URL_BERANDA + "/bos/home/trx?seller=";
    private final static String URL_BERANDA_PRODUK_TERLARIS = BASE_URL + BASE_URL_BERANDA + "/bos/home/prd?seller=";
    private final static String URL_BERANDA_PEMBELI_SETIA = BASE_URL + BASE_URL_BERANDA + "/bos/home/byr?seller=";
    private final static String JUMLAH_TRANSAKSI_START_DATE = "&start-dt=";
    private final static String JUMLAH_TRANSAKSI_END_DATE = "&end-dt=";

    // URL TEMPLATE TEXT
    private final static  String BASE_URL_TEMPLATED_TEXT= "/templatetext";
    private final static String URL_TEMPLATED_TEXT_BY_NAME = BASE_URL + BASE_URL_TEMPLATED_TEXT + "/bos/template-text-by-name";
    private final static String URL_TEMPLATED_TEXT_BY_DATE = BASE_URL + BASE_URL_TEMPLATED_TEXT + "/bos/template-text-by-date";
    private final static String URL_TEMPLATED_TEXT = BASE_URL + BASE_URL_TEMPLATED_TEXT + "/bos/template-text";

    //URL PRODUK
    private final static  String BASE_URL_PRODUCT = "/product";
    private final static String URL_PRODUCT_BY_NAME = BASE_URL + BASE_URL_PRODUCT + "/bos/product-by-name";
    private final static String URL_PRODUCT_BY_DATE = BASE_URL + BASE_URL_PRODUCT + "/bos/product-by-date";
    private final static String URL_PRODUCT_BY_PRICE = BASE_URL + BASE_URL_PRODUCT + "/bos/product-by-price";
    private final static String URL_PRODUCT_BY_BEST_SELLING = BASE_URL + BASE_URL_PRODUCT + "/bos/product-by-best-selling";
    private final static String URL_PRODUCT = BASE_URL + BASE_URL_PRODUCT + "/bos/product";

    private final static String URL_PRODUCT_CATEGORY = BASE_URL + BASE_URL_PRODUCT + "/bos/product-category";
    private final static String URL_PRODUK_DETAIL = BASE_URL + BASE_URL_PRODUCT + "/bos/product-detail";
    private static List<PrdCategory> g_list_product_category = new ArrayList<>();
    private static List<PrdCategory> g_list_product_category_all = new ArrayList<>();
    private static String KEY_SEMUA_PRODUCT = "Semua Produk";

    // URL TRANSAKSI
    private final static  String BASE_URL_TRANSACTION= "/transaction";
    private final static String URL_TRANSACTION_ONLINE = BASE_URL + BASE_URL_TRANSACTION + "/bos/online-transaction";
    private final static String URL_TRANSACTION_OFFLINE = BASE_URL + BASE_URL_TRANSACTION + "/bos/offline-transaction";
    private final static String URL_TRANSACTION_DETAIL = BASE_URL + BASE_URL_TRANSACTION + "/bos/online-transaction-detail";
    private final static String URL_TRANSACTION_DETAIL_OFFLINE = BASE_URL + BASE_URL_TRANSACTION + "/bos/offline-transaction-detail";
//    private final static  String URL_ORDER_SHIPPED = BASE_URL + BASE_URL_TRANSACTION + "/bos/orderShipped";

    //URL LOGIN
    private final static  String BASE_URL_LOGIN_REGISTER = "/account";
    private final static String URL_LOGIN = BASE_URL + BASE_URL_LOGIN_REGISTER + "/bos/seller";

    //URL REGISTER
    private final static  String BASE_URL_REGISTER = "/account";
    private final static String URL_REGISTER = BASE_URL + BASE_URL_REGISTER + "/bos/seller/sOTP";
    private final static String URL_SEND_OTP = BASE_URL + BASE_URL_REGISTER + "/bos/seller/vOTP";

    //URL PROFILE
    private final static  String BASE_URL_PROFILE = "/profile";
    private final static String URL_PROFILE = BASE_URL + BASE_URL_PROFILE + "/bos/profile";
    private final static String URL_PROFILE_CHANGE_PASSWORD = BASE_URL + BASE_URL_PROFILE + "/bos/profile/pass";

    //URL RAJA ONGKIR
    private final static  String BASE_URL_RAJAONGKIR = "/rajaongkir";
    private final static String URL_PROVINSI = BASE_URL + BASE_URL_RAJAONGKIR + "/bos/provinsi";
    private final static String URL_ONGKIR_COST = BASE_URL + BASE_URL_RAJAONGKIR + "/bos/ongkir";
    private final static String URL_CITY = BASE_URL + BASE_URL_RAJAONGKIR + "/bos/kota-kab";
    private static ArrayAdapter<String> g_rajaongkir_city_adapter;
    public static List<String> g_city_name_list = new ArrayList<>();

    //DATA MEMBER getOngkirCost()
    private static List<String> serviceList = new ArrayList<>();
    private static List<String> estimationDayList = new ArrayList<>();
    private static List<String> costList = new ArrayList<>();
    private static String g_textongkir;

    //URL ORDER
    private final static  String BASE_URL_ORDER = "/order";
    private final static String URL_KIRIM_FORM = BASE_URL + BASE_URL_ORDER + "/bos/form";

    //Shared Preference
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String BOS_ID = "BOS_ID";
    private static final String SELLER_ID = "SELLER_ID";
    private static final String NAMA_TOKO = "NAMA_TOKO";
    SharedPreferences g_preference;

    public static void getImage(Context p_context){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URLRRR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("BOSVOLLEY", response);

                            cobaImage.g_instance.settt(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    //region DATA SCIENCE
    public static void buyerRecommendation(final Context p_context, final String p_seller_id, final String p_product_id, final RecyclerView.Adapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        HashMap<String, Integer> params = new HashMap<String, Integer>();
        params.put("seller_id", Integer.parseInt(p_seller_id));
        params.put("product_id", Integer.parseInt(p_product_id));
        params.put("n_recommendation", 10);

        JsonObjectRequest request_json = new JsonObjectRequest(URL_BUYER_RECOMMENDATION, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        g_datascience_buyer_name.clear();
                        g_datascience_buyer_phone_number.clear();
                        List<Buyer> l_buyer = new ArrayList<>();

                        try {
                            JSONObject objGeneral = new JSONObject(String.valueOf(response));
                            JSONArray tmpObjectArray = objGeneral.getJSONArray("result");

                            for (int i = 0; i < tmpObjectArray.length(); i++){
                                JSONObject buyerResultsJSON = tmpObjectArray.getJSONObject(i);

                                Buyer buyer = new Buyer();
                                buyer.setBuyer_name(buyerResultsJSON.getString("buyer_name"));
                                buyer.setPhone(buyerResultsJSON.getString("phone"));

                                l_buyer.add(buyer);
                            }

                            if(p_adapter instanceof BerandaTawarkanPembeliAdapter)
                            {
                                BerandaTawarkanPembeliAdapter tmpadapter = (BerandaTawarkanPembeliAdapter) p_adapter;
                                tmpadapter.setDatasetPembeli(l_buyer);
                            }


                        }catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    public static void productRecommendation(final Context p_context, final String p_seller_id, final String p_buyer_id, final RecyclerView.Adapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        HashMap<String, Integer> params = new HashMap<String, Integer>();
        params.put("seller_id", Integer.valueOf(p_seller_id));
        params.put("buyer_id", Integer.valueOf(p_buyer_id));
        params.put("n_recommendation", 10);

        JsonObjectRequest request_json = new JsonObjectRequest(URL_PRODUCT_RECOMMENDATION, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        g_datascience_product_name.clear();
                        g_datascience_price.clear();
                        List<Product> l_product = new ArrayList<>();

                        try {
                            JSONObject objGeneral = new JSONObject(String.valueOf(response));
                            JSONArray tmpObjectArray = objGeneral.getJSONArray("result");

                            for (int i = 0; i < tmpObjectArray.length(); i++){
                                JSONObject productResultsJSON = tmpObjectArray.getJSONObject(i);

                                Product product = new Product();
                                product.setProduct_name(productResultsJSON.getString("product_name"));
                                product.setPrice(productResultsJSON.getInt("price"));
                                l_product.add(product);
                            }

                            if(p_adapter instanceof BerandaTawarkanProdukAdapter)
                            {
                                BerandaTawarkanProdukAdapter tmpadapter = (BerandaTawarkanProdukAdapter) p_adapter;
                                tmpadapter.setDatasetProduk(l_product);
                            }

                        }catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }
    //endregion

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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

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
                BerandaFragment.g_instance.showLayoutProduk(0, false);
                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

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
                BerandaFragment.g_instance.showLayoutPembeli(0, false);
                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    public static void getJumlahTransaksiSudahDIbayar(Context p_context, int p_id_seller){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_TRANSACTION_ONLINE + "/" + p_id_seller,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, response);
                            String output = NetworkUtil.getOutputSchema(response);
                            List<Transaction> tempObject = getListTransaksiByType(Arrays.asList(gson.fromJson(output, Transaction[].class)), 1);

                            int tmp_jumlah_transaksi = tempObject.size();

                            BerandaFragment.g_instance.refreshJumlahTransaksiSudahDIbayar(tmp_jumlah_transaksi);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    public static List<Transaction> getListTransaksiByType(List<Transaction> p_list, int p_type){
        List<Transaction> tmpListTransaction = new ArrayList<>();

        for(int i = 0; i < p_list.size(); i++){
            if(p_list.get(i).getStatus() == p_type)
                tmpListTransaction.add(p_list.get(i));
        }

        return tmpListTransaction;
    }

    //endregion

    //region TEMPLATED TEXT
    public static void getTemplatedTextByName(Context p_context, int p_id_seller, final String p_order, final RecyclerView.Adapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_TEMPLATED_TEXT_BY_NAME + "/" + p_id_seller,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String output = NetworkUtil.getOutputSchema(response);

                            List<TemplatedText> tempObject = Arrays.asList(gson.fromJson(output, TemplatedText[].class));

                            List<TemplatedText> tempResult = new ArrayList<>();

                            if(p_order.equals(Method.DESC)){
                                for (int i = tempObject.size()-1; i >= 0; i--)
                                {
                                    tempResult.add(tempObject.get(i));
                                }
                            }else{
                                tempResult = tempObject;
                            }

                            if(p_adapter instanceof TemplateAdapter){
                                TemplateFragment.g_instance.showLayout(tempResult.size(), true);
                                TemplateAdapter tmpAdapter = (TemplateAdapter) p_adapter;
                                tmpAdapter.setListTemplate(tempResult);
                            }
                            else if(p_adapter instanceof TemplatedTextAdapter){
                                KeyboardBOSnew.g_instance.showLayoutTemplateText(tempResult.size(), true);
                                TemplatedTextAdapter tmpAdapter = (TemplatedTextAdapter) p_adapter;
                                tmpAdapter.setListTemplate(tempResult);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(p_adapter instanceof TemplateAdapter)
                {
                    TemplateFragment.g_instance.showLayout(0, false);
                }
                else if(p_adapter instanceof TemplatedTextAdapter){
                    KeyboardBOSnew.g_instance.showLayoutTemplateText(0, false);

                }
                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    public static void getTemplatedTextByDate(Context p_context, int p_id_seller, final String p_order, final RecyclerView.Adapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_TEMPLATED_TEXT_BY_DATE + "/" + p_id_seller,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String output = NetworkUtil.getOutputSchema(response);

                            List<TemplatedText> tempObject = Arrays.asList(gson.fromJson(output, TemplatedText[].class));

                            List<TemplatedText> tempResult = new ArrayList<>();

                            if(p_order.equals(Method.DESC)){
                                for (int i = tempObject.size()-1; i >= 0; i--)
                                {
                                    tempResult.add(tempObject.get(i));
                                }
                            }else{
                                tempResult = tempObject;
                            }

                            if(p_adapter instanceof TemplateAdapter){
                                TemplateFragment.g_instance.showLayout(tempResult.size(), true);
                                TemplateAdapter tmpAdapter = (TemplateAdapter) p_adapter;
                                tmpAdapter.setListTemplate(tempResult);
                            }
                            else if(p_adapter instanceof TemplatedTextAdapter){
                                TemplatedTextAdapter tmpAdapter = (TemplatedTextAdapter) p_adapter;
                                tmpAdapter.setListTemplate(tempResult);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(p_adapter instanceof TemplateAdapter)
                {
                    TemplateFragment.g_instance.showLayout(0, false);
                }
                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

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
//                                getTemplatedTextByName(p_context, p_templatedtext.getId_template_text(), p_adapter);
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };


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
//                                getTemplatedTextByName(p_context, p_templatedtext.getId_template_text(), p_adapter);
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };


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
//                                getTemplatedTextByName(p_context, p_id_templatetext, p_adapter);
                                TemplateFragment.g_instance.refreshData();
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }
    //endregion

    //region PRODUCT
    public static void getProductByName(Context p_context, int p_id_seller, final String p_order, final RecyclerView.Adapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_PRODUCT_BY_NAME + "/" + p_id_seller,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String output = NetworkUtil.getOutputSchema(response);

                            List<Product> tempObject = Arrays.asList(gson.fromJson(output, Product[].class));

                            List<Product> tempResult = new ArrayList<>();

                            if(p_order.equals(Method.DESC)){
                                for (int i = tempObject.size()-1; i >= 0; i--)
                                {
                                    tempResult.add(tempObject.get(i));
                                }
                            }else{
                                tempResult = tempObject;
                            }

                            if(p_adapter instanceof ProdukAdapter){
                                ProdukFragment.g_instance.showLayout(tempResult.size(), true);
                                ProdukAdapter tmpAdapter = (ProdukAdapter) p_adapter;
                                tmpAdapter.setDatasetProduk(tempResult);
                            }
                            else if(p_adapter instanceof StokProdukAdapter){
                                KeyboardBOSnew.g_instance.showLayoutStok(tempResult.size(), true);
                                StokProdukAdapter tmpAdapter = (StokProdukAdapter) p_adapter;
                                tmpAdapter.setDatasetProduk(tempResult);
                            }
                            else if(p_adapter instanceof KirimFormProdukAdapter){
                                KeyboardBOSnew.g_instance.showLayoutKirimForm(tempResult.size(), true);
                                KirimFormProdukAdapter tmpAdapter = (KirimFormProdukAdapter) p_adapter;
                                tmpAdapter.setDatasetProduk(tempResult);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if(p_adapter instanceof ProdukAdapter){
                    ProdukFragment.g_instance.showLayout(0, false);
                }
                else if(p_adapter instanceof StokProdukAdapter){
                    KeyboardBOSnew.g_instance.showLayoutStok(0, false);
                }
                else if(p_adapter instanceof KirimFormProdukAdapter){
                    KeyboardBOSnew.g_instance.showLayoutKirimForm(0, false);
                }

                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    public static void getProductByDate(Context p_context, int p_id_seller, final String p_order, final RecyclerView.Adapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_PRODUCT_BY_DATE + "/" + p_id_seller,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String output = NetworkUtil.getOutputSchema(response);

                            List<Product> tempObject = Arrays.asList(gson.fromJson(output, Product[].class));

                            List<Product> tempResult = new ArrayList<>();

                            if(p_order.equals(Method.DESC)){
                                for (int i = tempObject.size()-1; i >= 0; i--)
                                {
                                    tempResult.add(tempObject.get(i));
                                }
                            }else{
                                tempResult = tempObject;
                            }

                            if(p_adapter instanceof ProdukAdapter){
                                ProdukFragment.g_instance.showLayout(tempResult.size(), true);
                                ProdukAdapter tmpAdapter = (ProdukAdapter) p_adapter;
                                tmpAdapter.setDatasetProduk(tempResult);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if(p_adapter instanceof ProdukAdapter){
                    ProdukFragment.g_instance.showLayout(0, false);
                }

                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    public static void getProductByPrice(Context p_context, int p_id_seller, final String p_order, final RecyclerView.Adapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_PRODUCT_BY_PRICE + "/" + p_id_seller,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String output = NetworkUtil.getOutputSchema(response);

                            List<Product> tempObject = Arrays.asList(gson.fromJson(output, Product[].class));

                            List<Product> tempResult = new ArrayList<>();

                            if(p_order.equals(Method.DESC)){
                                for (int i = tempObject.size()-1; i >= 0; i--)
                                {
                                    tempResult.add(tempObject.get(i));
                                }
                            }else{
                                tempResult = tempObject;
                            }

                            if(p_adapter instanceof ProdukAdapter){
                                ProdukFragment.g_instance.showLayout(tempResult.size(), true);
                                ProdukAdapter tmpAdapter = (ProdukAdapter) p_adapter;
                                tmpAdapter.setDatasetProduk(tempResult);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if(p_adapter instanceof ProdukAdapter){
                    ProdukFragment.g_instance.showLayout(0, false);
                }

                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    public static void getProductByBestSelling(Context p_context, int p_id_seller, final String p_order, final RecyclerView.Adapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_PRODUCT_BY_BEST_SELLING + "/" + p_id_seller,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String output = NetworkUtil.getOutputSchema(response);

                            List<Product> tempObject = Arrays.asList(gson.fromJson(output, Product[].class));

                            List<Product> tempResult = new ArrayList<>();

                            if(p_order.equals(Method.DESC)){
                                for (int i = tempObject.size()-1; i >= 0; i--)
                                {
                                    tempResult.add(tempObject.get(i));
                                }
                            }else{
                                tempResult = tempObject;
                            }

                            if(p_adapter instanceof ProdukAdapter){
                                ProdukFragment.g_instance.showLayout(tempResult.size(), true);
                                ProdukAdapter tmpAdapter = (ProdukAdapter) p_adapter;
                                tmpAdapter.setDatasetProduk(tempResult);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if(p_adapter instanceof ProdukAdapter){
                    ProdukFragment.g_instance.showLayout(0, false);
                }

                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

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
        params.put("weight", String.valueOf(p_product.getWeight()));
        params.put("base64StringImage", p_product.getImage_path());

        Log.d("BOSVOLLEY", p_product.getImage_path());

        JsonObjectRequest request_json = new JsonObjectRequest(URL_PRODUCT, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String message = NetworkUtil.getErrorCode(response.toString());
                        if(message.equals(ERROR_CODE_BERHASIL)){
                            Toast.makeText(p_context, "Penambahan data product berhasil", Toast.LENGTH_SHORT).show();
                            getProductByName(p_context, p_product.getSeller().getId_seller(), Method.ASC, p_adapter);
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };


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
        params.put("weight", String.valueOf(p_product.getWeight()));
        params.put("base64StringImage", p_product.getImage_path());

        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.PUT, URL_PRODUCT, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, response.toString());
                        String message = NetworkUtil.getErrorCode(response.toString());
                        if(message.equals(ERROR_CODE_BERHASIL)){
                            Toast.makeText(p_context, "Pengubahan data product berhasil", Toast.LENGTH_SHORT).show();
                            getProductByName(p_context, p_product.getSeller().getId_seller(), Method.ASC, p_adapter);
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };


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
                                getProductByName(p_context, p_id_product, Method.ASC, p_adapter);
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    public static void getProductCategory(Context p_context, int p_id_seller, final ArrayAdapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_PRODUCT_CATEGORY + "/" + p_id_seller,
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    public static void getProductCategory(final Context p_context, int p_id_seller, final View p_view){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_PRODUCT_CATEGORY + "/" + p_id_seller,
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    public static int findProductCategoryId(int position){
        int idx = 0;
        if(position > 0)
            idx = g_list_product_category.get(position - 1).getId_prd_category();

        return idx;
    }

    public static void getProductCategoryAll(Context p_context, final ArrayAdapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_PRODUCT_CATEGORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            String output = NetworkUtil.getOutputSchema(response);

                            g_list_product_category_all = Arrays.asList(gson.fromJson(output, PrdCategory[].class));

                            List<String> listnama = new ArrayList<>();

                            for (PrdCategory category : g_list_product_category_all){
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    public static void getProductCategoryAllEdit(Context p_context, final ArrayAdapter p_adapter, final int p_id){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_PRODUCT_CATEGORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            String output = NetworkUtil.getOutputSchema(response);

                            g_list_product_category_all = Arrays.asList(gson.fromJson(output, PrdCategory[].class));

                            List<String> listnama = new ArrayList<>();

                            for (PrdCategory category : g_list_product_category_all){
                                listnama.add(category.getCategory_name());
                            }

                            p_adapter.clear();
                            p_adapter.addAll(listnama);
                            p_adapter.notifyDataSetChanged();

                            ProdukFragment.g_instance.setSelectionSpinnerEdit(p_id);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    public static PrdCategory findFromAllProductCategory(int position){
        if(position >= 0) {
            if (g_list_product_category_all.size() > 0)
                return g_list_product_category_all.get(position);
            else
                return new PrdCategory();
        }
        else
            return new PrdCategory();
    }

    public static int findPositionFromAllProductCategory(int id_prd_category){

        int position = -1;
        if(id_prd_category > 0) {
            if (g_list_product_category_all.size() > 0)
                for(int i = 0 ; i < g_list_product_category_all.size(); i++){
                    if(g_list_product_category_all.get(i).getId_prd_category() == id_prd_category)
                        position = i;
                }
            else
                return position;
        }
        else
            return position;

        return position;
    }
    //endregion

    //region TRANSAKSI
    public static void  getTransaksi(Context p_context, int p_id_seller, final int p_type, final RecyclerView.Adapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_TRANSACTION_ONLINE + "/" + p_id_seller,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, response);
                            String output = NetworkUtil.getOutputSchema(response);

                            List<Transaction> tempObject = Arrays.asList(gson.fromJson(output, Transaction[].class));

                            if(p_adapter instanceof OnlineTransaksiAdapter){
                                OnlineTransaksiFragment.g_instance.showLayout(tempObject.size(), true);
                                OnlineTransaksiAdapter tmpAdapter = (OnlineTransaksiAdapter) p_adapter;
                                tmpAdapter.setListTransaksi(tempObject);

                                OnlineTransaksiFragment.g_instance.refreshLayout(p_type);

                            }else if (p_adapter instanceof MutasiRekeningAdapter){
                                KeyboardBOSnew.g_instance.showLayoutMutasi(tempObject.size(), true);
                                MutasiRekeningAdapter tmpAdapter = (MutasiRekeningAdapter) p_adapter;
                                tmpAdapter.setListTransaksi(tempObject);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(p_adapter instanceof OnlineTransaksiAdapter) {
                    OnlineTransaksiFragment.g_instance.showLayout(0, false);
                }
                else if (p_adapter instanceof MutasiRekeningAdapter){
                    KeyboardBOSnew.g_instance.showLayoutMutasi(0, false);
                }
                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

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

                            List<Transaction> tempObject = Arrays.asList(gson.fromJson(output, Transaction[].class));

                            if(p_adapter instanceof OfflineTransaksiAdapter){
                                OfflineTransaksiFragment.g_instance.showLayout(tempObject.size(), true);
                                OfflineTransaksiAdapter tmpAdapter = (OfflineTransaksiAdapter) p_adapter;
                                tmpAdapter.setListTransaksi(tempObject);
                                OfflineTransaksiFragment.g_instance.firstLoad();
                            }else if (p_adapter instanceof OfflineMutasiRekeningAdapter){
                                KeyboardBOSnew.g_instance.showLayoutMutasi(tempObject.size(), true);
                                OfflineMutasiRekeningAdapter tmpAdapter = (OfflineMutasiRekeningAdapter) p_adapter;
                                tmpAdapter.setListTransaksi(tempObject);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(p_adapter instanceof OfflineTransaksiAdapter){
                    OfflineTransaksiFragment.g_instance.showLayout(0, false);
                }
                else if (p_adapter instanceof OfflineMutasiRekeningAdapter){
                    KeyboardBOSnew.g_instance.showLayoutMutasi(0, false);
                }
                NetworkUtil.setErrorMessage(error);

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

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
                            else if(p_status == OnlineTransaksiFragment.g_instance.KEY_STATUS_BATAL){
                                OnlineTransaksiFragment.g_instance.showBottomSheetPesananBatal(tempObject);
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    public static void insertShippedCode(final Context p_context, final Transaction p_transaksi, final RecyclerView.Adapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id_transaction", String.valueOf(p_transaksi.getId_transaction()));
        params.put("shipping_code", p_transaksi.getShipping_code());

        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.PUT, URL_TRANSACTION_ONLINE, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = NetworkUtil.getErrorCode(response.toString());
                            if(message.equals(ERROR_CODE_BERHASIL)){
                                Toast.makeText(p_context, "Penambahan data Shipping Code berhasil", Toast.LENGTH_SHORT).show();
                                VolleyClass.getTransaksi(p_context, p_transaksi.getSeller().getId_seller(), OnlineTransaksiFragment.g_instance.KEY_STATUS_SUDAHDIKIRIM, p_adapter);
                            }
                            else
                            {
                                Toast.makeText(p_context, "Penambahan data Shipping Code gagal", Toast.LENGTH_SHORT).show();
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };


        g_requestqueue.add(request_json);
    }

    public static void updatePembatalanTransaksi(final Context p_context, final Transaction p_transaksi, final RecyclerView.Adapter p_adapter){
        g_requestqueue = Volley.newRequestQueue(p_context);


        StringRequest request_json = new StringRequest(Request.Method.PUT, URL_TRANSACTION_ONLINE + "/" + p_transaksi.getId_transaction(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String message = NetworkUtil.getErrorCode(response);
                            if(message.equals(ERROR_CODE_BERHASIL)){

                                Toast.makeText(p_context, "Pembatalan Transaksi berhasil", Toast.LENGTH_SHORT).show();
                                VolleyClass.getTransaksi(p_context, p_transaksi.getSeller().getId_seller(), OnlineTransaksiFragment.g_instance.KEY_STATUS_BATAL, p_adapter);
                            }
                            else
                            {
                                Toast.makeText(p_context, "Pembatalan Transaksi gagal", Toast.LENGTH_SHORT).show();
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };


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
                        String errorCode = NetworkUtil.getErrorCode(response.toString());
                        String message = NetworkUtil.getErrorMessage(response.toString());
                        String output = NetworkUtil.getOutputSchema(response.toString());
                        int tmp_seller_id = 0;
                        if (errorCode.equals(ERROR_CODE_PASSWORD_SALAH)){
                            LoginActivity.g_instance.moveToPasswordActivity(p_bos_id);
                        }else if (errorCode.equals(ERROR_CODE_AKUN_BELUM_TERVERIFIKASI)){
                            Seller tempObject = gson.fromJson(output, Seller.class);
                            tmp_seller_id = tempObject.getId_seller();
                            LoginActivity.g_instance.getProfile(tmp_seller_id);
                        }else{
                            LoginActivity.g_instance.setError(message);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LoginActivity.g_instance.setError("Gagal terhubung ke jaringan internet");
                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    public static void getProfileLogin(Context p_context, int p_seller_id){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_PROFILE + "/" + p_seller_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("BOSVOLLEY", response);
                            String output = NetworkUtil.getOutputSchema(response);
                            Seller tempObject = gson.fromJson(output, Seller.class);
                            LoginActivity.g_instance.generateOTP(tempObject);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    public static void getOTPLogin(final Context p_context, final String p_no_hp, final String p_bos_id){
        g_requestqueue = Volley.newRequestQueue(p_context);

        HashMap<String, String> params = new HashMap<String, String>();


        JsonObjectRequest request_json = new JsonObjectRequest(URL_REGISTER + "/" + p_no_hp, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String errorCode = NetworkUtil.getErrorCode(response.toString());
                        String output = NetworkUtil.getOutputSchema(response.toString());
                        String message = NetworkUtil.getErrorMessage(response.toString());

                        if(errorCode.equals(ERROR_CODE_BERHASIL)){
                            LoginActivity.g_instance.moveToOTPActivity(p_bos_id, p_no_hp);
                        }else{
                            LoginActivity.g_instance.setError(message);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

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
                        String error_message = NetworkUtil.getErrorMessage(response.toString());

                        if(message.equals(ERROR_CODE_BERHASIL)){
                            //Get BOS ID
                            int tmp_seller_id = 0;
                            try {
                                Seller tempObject = gson.fromJson(output, Seller.class);
                                tmp_seller_id = tempObject.getId_seller();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            PasswordActivity.g_instance.saveStringSharedPreference(BOS_ID, p_bos_id);
                            saveTokoToSharedPreference(p_context, tmp_seller_id);
                        }else {
                            PasswordActivity.g_instance.setError(error_message);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PasswordActivity.g_instance.setError("Gagal terhubung ke jaringan internet");
                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    public static void saveTokoToSharedPreference(final Context p_context, final int p_id_seller){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_PROFILE + "/" + p_id_seller,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("BOSVOLLEY", response);
                            String output = NetworkUtil.getOutputSchema(response);

                            Seller tempObject = gson.fromJson(output, Seller.class);
                            if (tempObject.getShop_name() == null || tempObject.getShop_name().isEmpty()){
                                PasswordActivity.g_instance.intentFillData(p_id_seller);
                            }else {
                                PasswordActivity.g_instance.saveStringSharedPreference("NAMA_TOKO", tempObject.getShop_name());
                                PasswordActivity.g_instance.intentLogin(p_id_seller);
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);

    }

    public static void register(final Context p_context, final String p_bos_id, final String p_nama, final String p_no_rek, final String p_no_hp, final String p_email, final String p_password){
        g_requestqueue = Volley.newRequestQueue(p_context);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("bos_id", p_bos_id);
        params.put("nama", p_nama);
        params.put("no_rek",p_no_rek);
        params.put("no_hp",p_no_hp);
        params.put("email", p_email);
        params.put("password",p_password);

        JsonObjectRequest request_json = new JsonObjectRequest(URL_REGISTER, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("BOSVOLLEY", response.toString());

                        String errorCode = NetworkUtil.getErrorCode(response.toString());
                        String message = NetworkUtil.getErrorMessage(response.toString());
                        String output = "";
                        try {
                            output = response.getString("output_schema");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(errorCode.equals(ERROR_CODE_BERHASIL)){
                            RegisterActivity.g_instance.moveToOTPActivity(p_bos_id, p_no_hp);
                        }else if(errorCode.equals("BIT-999")){
                            RegisterActivity.g_instance.moveToOTPActivity(p_bos_id, p_no_hp);
                        }else{
                            RegisterActivity.g_instance.setError("\n"+message);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                RegisterActivity.g_instance.setError("Gagal terhubung ke jaringan internet");
                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    public static void sendOTP(final Context p_context, final String p_otp, final String p_bos_id){
        g_requestqueue = Volley.newRequestQueue(p_context);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("bos_id", p_bos_id);
        params.put("otp", p_otp);

        JsonObjectRequest request_json = new JsonObjectRequest(URL_SEND_OTP, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String errorCode = NetworkUtil.getErrorCode(response.toString());
                        String output = NetworkUtil.getOutputSchema(response.toString());
                        String message = NetworkUtil.getErrorMessage(response.toString());
                        String tmpObject = "";
                        String output_id_seller = "";

                        try {
                            JSONObject objGeneral = new JSONObject(response.toString());
                            tmpObject =  objGeneral.getString("output_schema");
                            
                        }catch (Exception e) {
                            e.printStackTrace();
                        }


                        output_id_seller = tmpObject.substring(11);

                        if(errorCode.equals(ERROR_CODE_BERHASIL)){
                            OTPActivity.g_instance.moveToFillDataActivity(Integer.parseInt(output_id_seller), p_bos_id);
                        }else {
                            OTPActivity.g_instance.setError(message);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    public static void resendOTP(final Context p_context, final String p_no_hp){
        g_requestqueue = Volley.newRequestQueue(p_context);

        HashMap<String, String> params = new HashMap<String, String>();


        JsonObjectRequest request_json = new JsonObjectRequest(URL_REGISTER + "/" + p_no_hp, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String message = NetworkUtil.getErrorCode(response.toString());
                        String output = NetworkUtil.getOutputSchema(response.toString());

                        if(message.equals(ERROR_CODE_BERHASIL)){

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    //endregion

    //region PROFILE
    public static void getProfile(Context p_context, int p_seller_id){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET ,URL_PROFILE + "/" + p_seller_id,
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    public static void updateProfile(Context p_context, final Seller p_seller) throws JSONException {
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
                            ProfileFragment.g_instance.updateStringSharedPreference("NAMA_TOKO" ,p_seller.getShop_name());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };


        g_requestqueue.add(request_json);
    }

    public static void updateProfileFillData(Context p_context, final Seller p_seller) throws JSONException {
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

        Drawable drawable = p_context.getResources().getDrawable(R.drawable.ic_bos_mascot_default_profile);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);


        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("id_seller", String.valueOf(p_seller.getId_seller()));
        params.put("shop_name", p_seller.getShop_name());
        params.put("id_kota_kab", p_seller.getKota_kab().getId_kota_kab());
        params.put("base64StringImage", imageToString(bitmap));
        params.put("selected_courier", jsonArray);
        //params put selected courier

        Log.d("CEEEKKK", new JSONObject(params).toString());

        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.PUT, URL_PROFILE, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String message = NetworkUtil.getErrorCode(response.toString());
                            String output = NetworkUtil.getOutputSchema(response.toString());

                            if(message.equals(ERROR_CODE_BERHASIL)){
                                FillDataActivity.g_instance.filldataIntent(p_seller.getShop_name());
                            }else {
                                FillDataActivity.g_instance.setError(output);
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };


        g_requestqueue.add(request_json);
    }

    public static String imageToString(Bitmap bitmap){
        ByteArrayOutputStream bost = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 ,bost);
        byte[] img = bost.toByteArray();
        String temp = Base64.encodeToString(img, Base64.NO_WRAP);
        return temp;
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }


    //endregion

    //region CEK ONGKIR

    public static void getCityFillData(Context p_context){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET , URL_CITY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("BOSVOLLEY", response);
                            String output = NetworkUtil.getOutputSchema(response);

                            List<KotaKab> tempObject = Arrays.asList(gson.fromJson(output, KotaKab[].class));
                            FillDataActivity.g_instance.getCity(tempObject);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("BOSVOLLEY", "MASUK");
                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);

    }

    public static void getCityProfile(Context p_context){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET , URL_CITY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("BOSVOLLEY", response);
                            String output = NetworkUtil.getOutputSchema(response);

                            List<KotaKab> tempObject = Arrays.asList(gson.fromJson(output, KotaKab[].class));
                            ProfileFragment.g_instance.getCity(tempObject);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);

    }

    public static void getCityKeyboard(Context p_context){
        g_requestqueue = Volley.newRequestQueue(p_context);

        StringRequest request_json = new StringRequest(Request.Method.GET , URL_CITY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("BOSVOLLEY", response);
                            String output = NetworkUtil.getOutputSchema(response);

                            List<KotaKab> tempObject = Arrays.asList(gson.fromJson(output, KotaKab[].class));
                            KeyboardBOSnew.g_instance.getCity(tempObject);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);

    }

    public static void getOngkirCost(final Context p_context, final String p_asal, final String p_tujuan, final String p_berat, final String p_kurir){
        g_requestqueue = Volley.newRequestQueue(p_context);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("origin", p_asal);
        params.put("destination", p_tujuan);
        params.put("weight",p_berat);
        params.put("courier",p_kurir);

        JsonObjectRequest request_json = new JsonObjectRequest(URL_ONGKIR_COST, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        g_textongkir = "Daftar Ongkir "+ p_kurir.toUpperCase() +":";

                        serviceList.clear();
                        estimationDayList.clear();
                        costList.clear();

                        try {
                            JSONObject objGeneral = new JSONObject(String.valueOf(response));
                            JSONArray tmpObjectArray = objGeneral.getJSONArray("output_schema");
                            JSONObject costResultJSON = tmpObjectArray.getJSONObject(0);
                            JSONArray costCostsArray = costResultJSON.getJSONArray("costs");
                            for (int i = 0; i < costCostsArray.length(); i++){
                                JSONObject costCostsJSON = costCostsArray.getJSONObject(i);
                                serviceList.add(costCostsJSON.getString("service"));

                                JSONArray costCostArray = costCostsJSON.getJSONArray("cost");
                                JSONObject costCostJSON = costCostArray.getJSONObject(0);

                                estimationDayList.add(costCostJSON.getString("etd"));
                                costList.add(costCostJSON.getString("value"));

                                if (!p_kurir.equals("pos")){
                                    g_textongkir = g_textongkir + "\n" + serviceList.get(i)+" - "+estimationDayList.get(i)+" hari - "+costList.get(i);
                                }else {
                                    g_textongkir = g_textongkir + "\n" + serviceList.get(i)+" - "+estimationDayList.get(i).toLowerCase()+" - "+costList.get(i);
                                }
                            }
                            KeyboardBOSnew.g_instance.cekOngkirLoading("hide");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        g_textongkir = g_textongkir + "\n";
                        KeyboardBOSnew.g_instance.commitTextToBOSKeyboardEditText(g_textongkir);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
                KeyboardBOSnew.g_instance.cekOngkirError("show");
                KeyboardBOSnew.g_instance.cekOngkirLoading("hide");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };

        g_requestqueue.add(request_json);
    }

    //endregion

    //region ORDER
    public static void insertOrder(final KeyboardBOSnew p_parent, final int p_id_seller, final int p_id_origin,
                                   HashMap<String, Product> p_list) throws JSONException {
        g_requestqueue = Volley.newRequestQueue(p_parent.getApplicationContext());

        JSONArray jsonArray = new JSONArray();

        int iterator = 0;
        for (Map.Entry<String, Product> tmpProduct : p_list.entrySet()){
            JSONObject tmpjsonObject = new JSONObject();
            tmpjsonObject.put("id_product", tmpProduct.getValue().getId_product());
            tmpjsonObject.put("quantity", tmpProduct.getValue().getQty());

            jsonArray.put(iterator, tmpjsonObject);
            iterator++;
        }

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("id_seller", p_id_seller);
        params.put("origin_city", p_id_origin);
        params.put("product", jsonArray);

        JsonObjectRequest request_json = new JsonObjectRequest(URL_KIRIM_FORM , new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = NetworkUtil.getErrorCode(response.toString());
                            if(message.equals(ERROR_CODE_BERHASIL)){
                                Toast.makeText(p_parent.getApplicationContext(), "Order Berhasil", Toast.LENGTH_SHORT).show();
                                int tmpId = response.getInt("output_schema");
                                String comment = "Silahkan melengkapi data diri anda dan melakukan pengecekan terakhir" +
                                        " pesanan anda pada link dibawah ini:\n";
                                String url_kirimform = BASE_WEB_URL + "/order/" + tmpId;

                                p_parent.commitTextToBOSKeyboardEditText(comment + url_kirimform);
                            }
                            else
                            {
                                Toast.makeText(p_parent.getApplicationContext(), "Penambahan data template text gagal", Toast.LENGTH_SHORT).show();
                            }

                            KeyboardBOSnew.g_instance.kirimFormNextLoading("hide");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkUtil.setErrorMessage(error);
                KeyboardBOSnew.g_instance.kirimFormNextError("show");
                KeyboardBOSnew.g_instance.kirimFormNextLoading("hide");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return NetworkUtil.setBasicAuth();
            }
        };


        g_requestqueue.add(request_json);
    }
    //endregion
}

