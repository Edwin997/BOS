package com.example.bca_bos.ui.produk;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.google.android.material.textfield.TextInputLayout;

public class AddProdukActivity extends AppCompatActivity {

    ImageView g_apps_produk_image;
    TextInputLayout g_apps_produk_nama, g_apps_produk_harga, g_apps_produk_stok;
    ImageButton g_apps_from_camera, g_apps_from_gallery;
    Button g_apps_simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produk);
        Method.callback(this);

        initLayout();

    }

    public void initLayout(){
        g_apps_produk_nama = findViewById(R.id.apps_produk_add_nama_et);
        g_apps_produk_harga = findViewById(R.id.apps_produk_add_harga_et);
        g_apps_produk_stok = findViewById(R.id.apps_produk_add_stok_et);
        g_apps_from_camera = findViewById(R.id.apps_produk_add_camera_btn);
        g_apps_from_gallery = findViewById(R.id.apps_produk_add_gallery_btn);
        g_apps_produk_image = findViewById(R.id.apps_produk_add_iv);
        g_apps_simpan = findViewById(R.id.apps_produk_add_simpan_btn);
    }
}
