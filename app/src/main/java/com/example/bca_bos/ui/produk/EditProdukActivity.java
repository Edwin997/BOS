package com.example.bca_bos.ui.produk;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.models.products.Product;
import com.google.android.material.textfield.TextInputLayout;

public class EditProdukActivity extends AppCompatActivity {

    ImageView g_apps_produk_image;
    TextInputLayout g_apps_produk_nama, g_apps_produk_harga, g_apps_produk_stok;
    ImageButton g_apps_from_camera, g_apps_from_gallery;
    Button g_apps_simpan;

    Product g_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_produk);
        Method.callback(this);

        g_product = this.getIntent().getParcelableExtra(ProdukFragment.KEY_PRODUK);
        initLayout(g_product);


    }

    public void initLayout(Product p_product){
        g_apps_produk_nama = findViewById(R.id.apps_produk_edit_nama_et);
        g_apps_produk_harga = findViewById(R.id.apps_produk_edit_harga_et);
        g_apps_produk_stok = findViewById(R.id.apps_produk_edit_stok_et);
        g_apps_from_camera = findViewById(R.id.apps_produk_edit_camera_btn);
        g_apps_from_gallery = findViewById(R.id.apps_produk_edit_gallery_btn);
        g_apps_produk_image = findViewById(R.id.apps_produk_edit_iv);
        g_apps_simpan = findViewById(R.id.apps_produk_edit_simpan_btn);

        g_apps_produk_nama.getEditText().setText(p_product.getProduct_name());
        g_apps_produk_harga.getEditText().setText(String.valueOf(p_product.getPrice()));
        g_apps_produk_stok.getEditText().setText(String.valueOf(p_product.getStock()));
    }
}
