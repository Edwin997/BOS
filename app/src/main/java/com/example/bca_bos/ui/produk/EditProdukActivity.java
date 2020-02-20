package com.example.bca_bos.ui.produk;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.models.Produk;
import com.google.android.material.textfield.TextInputLayout;

public class EditProdukActivity extends AppCompatActivity {

    ImageView g_apps_produk_image;
    TextInputLayout g_apps_produk_nama, g_apps_produk_harga, g_apps_produk_stok;
    ImageButton g_apps_from_camera, g_apps_from_gallery;
    Button g_apps_simpan;

    Produk g_produk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_produk);
        Method.callback(this);

        g_produk = this.getIntent().getParcelableExtra(ProdukFragment.KEY_PRODUK);
        initLayout(g_produk);


    }

    public void initLayout(Produk p_produk){
        g_apps_produk_nama = findViewById(R.id.apps_produk_edit_nama_et);
        g_apps_produk_harga = findViewById(R.id.apps_produk_edit_harga_et);
        g_apps_produk_stok = findViewById(R.id.apps_produk_edit_stok_et);
        g_apps_from_camera = findViewById(R.id.apps_produk_edit_camera_btn);
        g_apps_from_gallery = findViewById(R.id.apps_produk_edit_gallery_btn);
        g_apps_produk_image = findViewById(R.id.apps_produk_edit_iv);
        g_apps_simpan = findViewById(R.id.apps_produk_edit_simpan_btn);

        g_apps_produk_nama.getEditText().setText(p_produk.getNama());
        g_apps_produk_harga.getEditText().setText(String.valueOf(p_produk.getHarga()));
        g_apps_produk_stok.getEditText().setText(String.valueOf(p_produk.getStok()));
    }
}
