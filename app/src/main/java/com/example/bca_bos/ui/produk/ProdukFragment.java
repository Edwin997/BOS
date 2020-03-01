package com.example.bca_bos.ui.produk;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.dummy.ListKategoriDummy;
import com.example.bca_bos.dummy.ListProdukDummy;
import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Produk;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class ProdukFragment extends Fragment implements OnCallBackListener, View.OnClickListener {

    //region DATA MEMBER
    //FINAL STATIC DATA MEMBER
    public final static String KEY_PRODUK = "produk";

    //VIEW DATA MEMBER
    private Context g_context;
    private View g_view;
    private ChooseImageFromDialog g_choose_dialog;
    private BottomSheetDialog g_bottomsheet_dialog;

    //FRAGMENT SECTION DATA MEMBER
    private LinearLayout g_produk_fragment_ll_add_button;

    private Spinner g_produk_fragment_spinner_kategori;
    private ArrayAdapter g_spinnerAdapter;

    private RecyclerView g_produkfragment_recyclerview;
    private ProdukAdapter g_produkadapter;
    private LinearLayoutManager g_linearlayoutmanager;

    //ADD BOTTOM SHEET PRODUK DATA MEMBER
    private RoundedImageView g_iv_bottom_sheet_produk_add_gambar;
    private TextView g_tv_bottom_sheet_produk_add_nama, g_tv_bottom_sheet_produk_add_harga,
            g_tv_bottom_sheet_produk_add_stok, g_tv_bottom_sheet_produk_add_berat;
    private Button g_btn_bottom_sheet_produk_add_tambah, g_btn_bottom_sheet_produk_add_batal;
    private Bitmap g_bmp_bottom_sheet_produk_add;

    //EDIT BOTTOM SHEET PRODUK DATA MEMBER
    private RoundedImageView g_iv_bottom_sheet_produk_edit_gambar;
    private TextView g_tv_bottom_sheet_produk_edit_nama, g_tv_bottom_sheet_produk_edit_harga,
            g_tv_bottom_sheet_produk_edit_stok, g_tv_bottom_sheet_produk_edit_berat;
    private Button g_btn_bottom_sheet_produk_edit_simpan, g_btn_bottom_sheet_produk_edit_hapus;
    private Bitmap g_bmp_bottom_sheet_produk_edit;
    //endregion

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //inisiasi view, choose dialog, bottom sheet dialog
        g_context = container.getContext();
        g_view = inflater.inflate(R.layout.fragment_produk, container, false);
        g_choose_dialog = new ChooseImageFromDialog(this);
        g_bottomsheet_dialog = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);

        //inisiasi layout
        g_produk_fragment_ll_add_button = g_view.findViewById(R.id.apps_produk_fragment_add_btn);

        //inisiasi recyclerview
        g_produkfragment_recyclerview = g_view.findViewById(R.id.apps_produk_fragment_recyclerview);

        //inisiasi spinner
        g_produk_fragment_spinner_kategori = g_view.findViewById(R.id.apps_produk_fragment_kategori_filter);

        //config layout
        g_produk_fragment_ll_add_button.setOnClickListener(this);

        //config recyclerview
        g_linearlayoutmanager = new LinearLayoutManager(g_context);
        g_produkadapter = new ProdukAdapter();
        g_produkadapter.setParentOnCallBack(this);
        g_produkfragment_recyclerview.setAdapter(g_produkadapter);
        g_produkfragment_recyclerview.setLayoutManager(g_linearlayoutmanager);

        //config spinner
        g_spinnerAdapter = new ArrayAdapter(g_context, android.R.layout.simple_spinner_item, ListKategoriDummy.getListTypeString());
        g_spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        g_produk_fragment_spinner_kategori.setAdapter(g_spinnerAdapter);
        g_produk_fragment_spinner_kategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idx = ListKategoriDummy.getListTypeString()[position];
                g_produkadapter.setDatasetProduk(ListProdukDummy.getProdukByKategory(idx));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                g_produkadapter.setDatasetProduk(ListProdukDummy.produkList);
            }
        });

        return g_view;
    }

    @Override
    public void OnCallBack(Object p_obj) {
        if(p_obj instanceof Produk) {
            Produk tmpProduk = (Produk) p_obj;
            showBottomSheetEditProduk(tmpProduk);
        }
        else if(p_obj instanceof Integer){
            int tmpCode = (int)p_obj;
            if(tmpCode == ChooseImageFromDialog.CODE_CAMERA_ADD_PRODUK){
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, ChooseImageFromDialog.CODE_CAMERA_ADD_PRODUK);
            }
            else if(tmpCode == ChooseImageFromDialog.CODE_GALLERY_ADD_PRODUK){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, ChooseImageFromDialog.CODE_GALLERY_ADD_PRODUK);
            }
            else if(tmpCode == ChooseImageFromDialog.CODE_CAMERA_EDIT_PRODUK){
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, ChooseImageFromDialog.CODE_CAMERA_EDIT_PRODUK);
            }
            else if(tmpCode == ChooseImageFromDialog.CODE_GALLERY_EDIT_PRODUK){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, ChooseImageFromDialog.CODE_GALLERY_EDIT_PRODUK);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.apps_produk_fragment_add_btn:
                showBottomSheetAddProduk();
                break;

            //region ADD PRODUK BOTTOM SHEET
            case R.id.apps_bottom_sheet_iv_gambar_add_produk:
                g_choose_dialog.showChooseDialogAdd(g_context);
                break;
            case R.id.apps_bottom_sheet_btn_tambah_add_produk:
                g_bottomsheet_dialog.dismiss();
                break;
            case R.id.apps_bottom_sheet_btn_batal_add_produk:
                g_bottomsheet_dialog.dismiss();
                break;
            //endregion

            //region EDIT PRODUK BOTTOM SHEET
            case R.id.apps_bottom_sheet_iv_gambar_edit_produk:
                g_choose_dialog.showChooseDialogEdit(g_context);
                break;
            case R.id.apps_bottom_sheet_btn_simpan_edit_produk:
                g_bottomsheet_dialog.dismiss();
                break;
            case R.id.apps_bottom_sheet_btn_hapus_edit_produk:
                g_bottomsheet_dialog.dismiss();
                break;
            //endregion

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && data != null){
            if(requestCode == ChooseImageFromDialog.CODE_GALLERY_ADD_PRODUK){
                Uri path = data.getData();

                try {
                    g_bmp_bottom_sheet_produk_add = MediaStore.Images.Media.getBitmap(g_context.getContentResolver(), path);
                    g_iv_bottom_sheet_produk_add_gambar.setImageBitmap(g_bmp_bottom_sheet_produk_add);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(requestCode == ChooseImageFromDialog.CODE_CAMERA_ADD_PRODUK){
                Uri path = data.getData();

                try {
                    g_bmp_bottom_sheet_produk_add = MediaStore.Images.Media.getBitmap(g_context.getContentResolver(), path);
                    g_iv_bottom_sheet_produk_add_gambar.setImageBitmap(g_bmp_bottom_sheet_produk_add);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(requestCode == ChooseImageFromDialog.CODE_GALLERY_EDIT_PRODUK){
                Uri path = data.getData();

                try {
                    g_bmp_bottom_sheet_produk_edit = MediaStore.Images.Media.getBitmap(g_context.getContentResolver(), path);
                    g_iv_bottom_sheet_produk_edit_gambar.setImageBitmap(g_bmp_bottom_sheet_produk_edit);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(requestCode == ChooseImageFromDialog.CODE_CAMERA_EDIT_PRODUK){
                Uri path = data.getData();

                try {
                    g_bmp_bottom_sheet_produk_edit = MediaStore.Images.Media.getBitmap(g_context.getContentResolver(), path);
                    g_iv_bottom_sheet_produk_edit_gambar.setImageBitmap(g_bmp_bottom_sheet_produk_edit);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //region BOTTOM SHEET METHOD
    private void showBottomSheetEditProduk(Produk p_produk){
        //inisiasi view
        View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_edit_produk,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_edit_produk)
        );

        //inisiasi imageview
        g_iv_bottom_sheet_produk_edit_gambar = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_iv_gambar_edit_produk);

        //inisiasi edittext
        g_tv_bottom_sheet_produk_edit_nama = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_nama_edit_produk);
        g_tv_bottom_sheet_produk_edit_harga = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_harga_edit_produk);
        g_tv_bottom_sheet_produk_edit_stok = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_stok_edit_produk);
        g_tv_bottom_sheet_produk_edit_berat = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_berat_edit_produk);

        //inisiasi button
        g_btn_bottom_sheet_produk_edit_simpan = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_btn_simpan_edit_produk);
        g_btn_bottom_sheet_produk_edit_hapus = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_btn_hapus_edit_produk);

        //config imageview
        g_iv_bottom_sheet_produk_edit_gambar.setImageDrawable(getResources().getDrawable(p_produk.getGambar()));
        g_iv_bottom_sheet_produk_edit_gambar.setOnClickListener(this);

        //config edittext
        g_tv_bottom_sheet_produk_edit_nama.setText(p_produk.getNama());
        g_tv_bottom_sheet_produk_edit_harga.setText(Method.getIndoCurrency(p_produk.getHarga()));
        g_tv_bottom_sheet_produk_edit_stok.setText(String.valueOf(p_produk.getStok()));
        g_tv_bottom_sheet_produk_edit_berat.setText(String.valueOf(0));

        //config button
        g_btn_bottom_sheet_produk_edit_simpan.setOnClickListener(this);
        g_btn_bottom_sheet_produk_edit_hapus.setOnClickListener(this);

        //show bottomsheet
        g_bottomsheet_dialog.setContentView(l_bottomsheet_view_add);
        g_bottomsheet_dialog.show();
    }

    private void showBottomSheetAddProduk(){
        //inisiasi view
        View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_add_produk,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_add_produk)
        );

        //inisiasi imageview
        g_iv_bottom_sheet_produk_add_gambar = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_iv_gambar_add_produk);

        //inisiasi edittext
        g_tv_bottom_sheet_produk_add_nama = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_nama_add_produk);
        g_tv_bottom_sheet_produk_add_harga = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_harga_add_produk);
        g_tv_bottom_sheet_produk_add_stok = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_stok_add_produk);
        g_tv_bottom_sheet_produk_add_berat = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_berat_add_produk);

        //inisiasi button
        g_btn_bottom_sheet_produk_add_tambah = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_btn_tambah_add_produk);
        g_btn_bottom_sheet_produk_add_batal = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_btn_batal_add_produk);

        //config imageview
        g_iv_bottom_sheet_produk_add_gambar.setOnClickListener(this);

        //config button
        g_btn_bottom_sheet_produk_add_tambah.setOnClickListener(this);
        g_btn_bottom_sheet_produk_add_batal.setOnClickListener(this);

        //show bottomsheet
        g_bottomsheet_dialog.setContentView(l_bottomsheet_view_add);
        g_bottomsheet_dialog.show();
    }
    //endregion

//    private void uploadImage(){
//        RequestQueue rq = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                "http://192.168.43.228:80/server/server.php",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject obj = new JSONObject(response);
//                            String res = obj.getString("response");
//                            Toast.makeText(AddProdukActivity.this, res, Toast.LENGTH_SHORT).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        }
//        ){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("name", "coba");
//                params.put("image", imageToString(bmp));
//                return params;
//            }
//        };
//
//        rq.add(stringRequest);
//    }

    public String imageToString(Bitmap bitmap){
        ByteArrayOutputStream bost = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 ,bost);
        byte[] img = bost.toByteArray();
        return Base64.encodeToString(img, Base64.DEFAULT);
    }

}