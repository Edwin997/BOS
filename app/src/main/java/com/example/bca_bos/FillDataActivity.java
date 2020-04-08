package com.example.bca_bos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bca_bos.models.Courier;
import com.example.bca_bos.models.Seller;
import com.example.bca_bos.models.locations.KotaKab;
import com.example.bca_bos.networks.VolleyClass;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class FillDataActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    //Inisialisasi
    public static FillDataActivity g_instance;
    private Context g_context;

    //Nama Toko
    TextView g_filldata_tv_nama_toko;
    EditText g_filldata_et_nama_toko;

    //Kota
    TextView g_filldata_tv_kota;
    AutoCompleteTextView g_filldata_actv_kota;
    private ArrayAdapter<String> g_autocompleteadapter;
    public static List<String> g_city_name_list = new ArrayList<>();
    public List<KotaKab> g_city_list;
    private String g_asal_id_city;

    //Kurir Pilihan
    Button g_filldata_btn_jne, g_filldata_btn_tiki, g_filldata_btn_pos;
    private Boolean IS_CHOOSE_JNE = false, IS_CHOOSE_TIKI = false, IS_CHOOSE_POS = false;

    Button g_filldata_btn_submit;
    TextView g_filldata_tv_error, g_filldata_tv_skip;

    //Shared Preference
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String BOS_ID = "BOS_ID";
    private static final String SELLER_ID = "SELLER_ID";
    SharedPreferences g_preference;
    int g_seller_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_data);

        //Inisialisasi
        g_instance = this;
        g_context =getApplicationContext();

        //Get City
        VolleyClass.getCityFillData(g_context);

        //Get Seller ID
        g_preference = this.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        g_seller_id = g_preference.getInt(SELLER_ID, -1);

        //Text View
        g_filldata_tv_nama_toko = findViewById(R.id.filldata_tv_nama_toko);
        g_filldata_tv_kota = findViewById(R.id.filldata_tv_kota);

        //Edit Text
        g_filldata_et_nama_toko = findViewById(R.id.filldata_et_nama_toko);
        g_filldata_et_nama_toko.setOnTouchListener(this);
        g_filldata_actv_kota = findViewById(R.id.filldata_actv_kota);
        g_filldata_actv_kota.setAdapter(g_autocompleteadapter);
        g_filldata_actv_kota.setOnTouchListener(this);

        //Kurir Button
        g_filldata_btn_jne = findViewById(R.id.filldata_jne_button);
        configChooseCourierButton(g_filldata_btn_jne, IS_CHOOSE_JNE);
        g_filldata_btn_jne.setOnClickListener(this);
        g_filldata_btn_tiki = findViewById(R.id.filldata_tiki_button);
        configChooseCourierButton(g_filldata_btn_tiki, IS_CHOOSE_TIKI);
        g_filldata_btn_tiki.setOnClickListener(this);
        g_filldata_btn_pos = findViewById(R.id.filldata_pos_button);
        configChooseCourierButton(g_filldata_btn_pos, IS_CHOOSE_POS);
        g_filldata_btn_pos.setOnClickListener(this);

        //Error
        g_filldata_tv_error = findViewById(R.id.filldata_tv_error);

        //Submit Button
        g_filldata_btn_submit = findViewById(R.id.filldata_submit_button);
        g_filldata_btn_submit.setOnClickListener(this);

        //Skip
        g_filldata_tv_skip = findViewById(R.id.filldata_tv_skip);
        g_filldata_tv_skip.setOnClickListener(this);

    }

    @Override
    public void onClick(View p_view) {
        switch (p_view.getId()){
            case R.id.filldata_submit_button:
                Seller tmp_seller = new Seller();
                KotaKab tmp_kotakab = new KotaKab();

                tmp_seller.setId_seller(g_seller_id);
                tmp_seller.setShop_name(g_filldata_et_nama_toko.getText().toString());
                tmp_seller.setBase64StringImage("");
                //get city id
                getAsalCityId(g_filldata_actv_kota);
                tmp_kotakab.setId_kota_kab(Integer.parseInt(g_asal_id_city));
                tmp_seller.setKota_kab(tmp_kotakab);

                List<Courier> listCourier = new ArrayList<>();
                Courier tmp_courier1 = new Courier();
                tmp_courier1.setId_courier(1);
                tmp_courier1.setIs_selected(courierIsSelected(IS_CHOOSE_JNE));
                listCourier.add(tmp_courier1);

                Courier tmp_courier2 = new Courier();
                tmp_courier2.setId_courier(2);
                tmp_courier2.setIs_selected(courierIsSelected(IS_CHOOSE_TIKI));
                listCourier.add(tmp_courier2);

                Courier tmp_courier3 = new Courier();
                tmp_courier3.setId_courier(3);
                tmp_courier3.setIs_selected(courierIsSelected(IS_CHOOSE_POS));
                listCourier.add(tmp_courier3);

                tmp_seller.setSelected_courier(listCourier);

                try {
                    VolleyClass.updateProfileFillData(g_context, tmp_seller);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.filldata_tv_skip:
                filldataIntent();
                break;
            case R.id.filldata_jne_button:
                IS_CHOOSE_JNE = !IS_CHOOSE_JNE;
                configChooseCourierButton(p_view, IS_CHOOSE_JNE);
                break;
            case R.id.filldata_tiki_button:
                IS_CHOOSE_TIKI = !IS_CHOOSE_TIKI;
                configChooseCourierButton(p_view, IS_CHOOSE_TIKI);
                break;
            case R.id.filldata_pos_button:
                IS_CHOOSE_POS = !IS_CHOOSE_POS;
                configChooseCourierButton(p_view, IS_CHOOSE_POS);
                break;
        }

    }

    @Override
    public boolean onTouch(View p_view, MotionEvent event) {
        switch (p_view.getId()){
            case R.id.filldata_et_nama_toko:
                g_filldata_tv_nama_toko.setVisibility(View.VISIBLE);
                g_filldata_et_nama_toko.setHint("");
                break;
            case R.id.filldata_actv_kota:
                g_filldata_tv_kota.setVisibility(View.VISIBLE);
                g_filldata_actv_kota.setHint("");
                g_filldata_actv_kota.showDropDown();
                break;
        }
        return false;
    }

    private void configChooseCourierButton(View p_view, boolean p_choose){
        if (p_choose){
            p_view.setBackgroundResource(R.drawable.style_gradient_color_rounded_box_blue);
        }else {
            p_view.setBackgroundResource(R.drawable.style_gradient_color_rounded_box_grey);
        }
    }

    public void getCity(List<KotaKab> p_kotakab){
        g_city_name_list.clear();
        String l_city_name;

        for (int i = 0; i < p_kotakab.size(); i++){
            l_city_name = p_kotakab.get(i).getKota_kab_name();

            g_city_name_list.add(l_city_name);
        }

        g_autocompleteadapter = new ArrayAdapter<>(g_context, android.R.layout.simple_list_item_1, g_city_name_list);
        g_autocompleteadapter.notifyDataSetChanged();
    }

    private void getAsalCityId(AutoCompleteTextView p_autocomplete){
        int l_position = g_city_name_list.indexOf(p_autocomplete.getText().toString());
        g_asal_id_city = String.valueOf(g_city_list.get(l_position).getId_kota_kab());
    }

    private int courierIsSelected(Boolean tmp_choose){
        if (tmp_choose){
            return 1;
        }else {
            return 0;
        }
    }

    public void filldataIntent(){
        Intent tmp_filldata_intent = new Intent(FillDataActivity.this, ApplicationContainer.class);
        startActivity(tmp_filldata_intent);
        overridePendingTransition(R.anim.slide_down_in, R.anim.slide_down_out);
        finish();
    }

    public void setError(String p_message){
        g_filldata_tv_error.setVisibility(View.VISIBLE);
        g_filldata_tv_error.setText(p_message);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent tmp_back_intent = new Intent(FillDataActivity.this, RegisterActivity.class);
        startActivity(tmp_back_intent);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        finish();
    }

}
