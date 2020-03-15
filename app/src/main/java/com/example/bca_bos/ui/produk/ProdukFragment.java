package com.example.bca_bos.ui.produk;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.dummy.ListKategoriDummy;
import com.example.bca_bos.dummy.ListProdukDummy;
import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Seller;
import com.example.bca_bos.models.products.PrdCategory;
import com.example.bca_bos.models.products.Product;
import com.example.bca_bos.networks.VolleyClass;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;

import static android.app.Activity.RESULT_OK;

public class ProdukFragment extends Fragment implements OnCallBackListener, View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    //region DATA MEMBER
    //FINAL STATIC DATA MEMBER
    public final static String KEY_PRODUK = "product";

    //VIEW DATA MEMBER
    private Context g_context;
    private View g_view;
    private ChooseImageFromDialog g_choose_dialog;
    private BottomSheetDialog g_bottomsheet_dialog;
    Product g_product_onclick;

    //FRAGMENT SECTION DATA MEMBER
    private LinearLayout g_produk_fragment_ll_add_button;

    private Spinner g_produk_fragment_spinner_kategori;
    private ArrayAdapter g_spinnerAdapter;

    private RecyclerView g_produkfragment_recyclerview;
    private ProdukAdapter g_produkadapter;
    private LinearLayoutManager g_linearlayoutmanager;

    private ImageView g_produk_fragment_sort_btn, g_produk_fragment_category_btn;
    private EditText g_produk_fragment_search_et;

    //ADD BOTTOM SHEET PRODUK DATA MEMBER
    private RoundedImageView g_iv_bottom_sheet_produk_add_gambar;
    private EditText g_tv_bottom_sheet_produk_add_nama, g_tv_bottom_sheet_produk_add_harga,
            g_tv_bottom_sheet_produk_add_stok, g_tv_bottom_sheet_produk_add_berat;
    private Button g_btn_bottom_sheet_produk_add_tambah, g_btn_bottom_sheet_produk_add_batal;
    private Bitmap g_bmp_bottom_sheet_produk_add;
    private Uri pathh;
    private URI pathhhh;

    //EDIT BOTTOM SHEET PRODUK DATA MEMBER
    private RoundedImageView g_iv_bottom_sheet_produk_edit_gambar;
    private EditText g_tv_bottom_sheet_produk_edit_nama, g_tv_bottom_sheet_produk_edit_harga,
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

        //inisiasi imageview
        g_produk_fragment_sort_btn = g_view.findViewById(R.id.apps_produk_fragment_sort_btn);
        g_produk_fragment_category_btn = g_view.findViewById(R.id.apps_produk_fragment_category_btn);

        //inisiasi edittext
        g_produk_fragment_search_et = g_view.findViewById(R.id.apps_produk_fragment_search_et);

        //config layout
        g_produk_fragment_ll_add_button.setOnClickListener(this);

        //config recyclerview
        g_linearlayoutmanager = new LinearLayoutManager(g_context);
        g_produkadapter = new ProdukAdapter();

        g_produkadapter.setParentOnCallBack(this);
        g_produkfragment_recyclerview.setAdapter(g_produkadapter);
        g_produkfragment_recyclerview.setLayoutManager(g_linearlayoutmanager);
//        VolleyClass.getProduct(g_context, 3, g_produkadapter);

        //config spinner
        g_spinnerAdapter = new ArrayAdapter(g_context, android.R.layout.simple_spinner_item, ListKategoriDummy.getListTypeString());
        g_spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        g_produk_fragment_spinner_kategori.setAdapter(g_spinnerAdapter);
        g_produk_fragment_spinner_kategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int idx = VolleyClass.findProductCategoryId(position);
                g_produkadapter.getProductFiltered(idx);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                g_produkadapter.getProductFiltered(0);
            }
        });
//        VolleyClass.getProductCategory(g_context, g_spinnerAdapter);

        //config imageview
        g_produk_fragment_sort_btn.setOnClickListener(this);
        g_produk_fragment_category_btn.setOnClickListener(this);

        return g_view;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.apps_produk_item_sort_asc:
                Toast.makeText(g_context, "asc", Toast.LENGTH_SHORT).show();
                break;
            case R.id.apps_produk_item_sort_desc:
                Toast.makeText(g_context, "desc", Toast.LENGTH_SHORT).show();
                break;
            case 0:
                Toast.makeText(g_context, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(g_context, item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void OnCallBack(Object p_obj) {
        if(p_obj instanceof Product) {
            g_product_onclick = (Product) p_obj;
            showBottomSheetEditProduk(g_product_onclick);
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
            case R.id.apps_produk_fragment_sort_btn:
                showSortPopupMenu(v);
                break;
            case R.id.apps_produk_fragment_category_btn:
                showCategoryPopupMenu(v);
                break;
            //region ADD PRODUK BOTTOM SHEET
            case R.id.apps_bottom_sheet_iv_gambar_add_produk:
                g_choose_dialog.showChooseDialogAdd(g_context);
                break;
            case R.id.apps_bottom_sheet_btn_tambah_add_produk:
                Seller seller = new Seller();
                seller.setId_seller(3);

                PrdCategory prdCategory = new PrdCategory();
                prdCategory.setId_prd_category(1);

                Product tmpProduct = new Product();
                tmpProduct.setProduct_name(g_tv_bottom_sheet_produk_add_nama.getText().toString());
                tmpProduct.setPrice(Integer.parseInt(g_tv_bottom_sheet_produk_add_harga.getText().toString()));
                tmpProduct.setStock(Integer.parseInt(g_tv_bottom_sheet_produk_add_stok.getText().toString()));
                tmpProduct.setImage_path(imageToString(g_bmp_bottom_sheet_produk_add));
                tmpProduct.setPrdCategory(prdCategory);
                tmpProduct.setSeller(seller);

                VolleyClass.insertProduct(g_context, tmpProduct, g_produkadapter);

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
                Seller selleredit = new Seller();
                selleredit.setId_seller(3);

                PrdCategory prdCategoryedit = new PrdCategory();
                prdCategoryedit.setId_prd_category(1);

                Product tmpProductedit = new Product();
                tmpProductedit.setId_product(g_product_onclick.getId_product());
                tmpProductedit.setProduct_name(g_tv_bottom_sheet_produk_edit_nama.getText().toString());
                tmpProductedit.setPrice(Integer.parseInt(g_tv_bottom_sheet_produk_edit_harga.getText().toString()));
                tmpProductedit.setStock(Integer.parseInt(g_tv_bottom_sheet_produk_edit_stok.getText().toString()));
                tmpProductedit.setImage_path(imageToString(g_bmp_bottom_sheet_produk_edit));
                tmpProductedit.setPrdCategory(prdCategoryedit);
                tmpProductedit.setSeller(selleredit);
                g_bottomsheet_dialog.dismiss();
                VolleyClass.updateProduct(g_context, tmpProductedit, g_produkadapter);
                break;
            case R.id.apps_bottom_sheet_btn_hapus_edit_produk:
                g_bottomsheet_dialog.dismiss();
                VolleyClass.deleteProduct(g_context, g_product_onclick.getId_product(), g_produkadapter);
                break;
            //endregion

        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && data != null){
            if(requestCode == ChooseImageFromDialog.CODE_GALLERY_ADD_PRODUK){
                pathh = data.getData();

                try {
                    g_bmp_bottom_sheet_produk_add = MediaStore.Images.Media.getBitmap(g_context.getContentResolver(), pathh);
                    g_iv_bottom_sheet_produk_add_gambar.setImageBitmap(g_bmp_bottom_sheet_produk_add);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(requestCode == ChooseImageFromDialog.CODE_CAMERA_ADD_PRODUK){
                try {
                    Bundle extras = data.getExtras();
                    g_bmp_bottom_sheet_produk_add = (Bitmap) extras.get("data");
                    g_iv_bottom_sheet_produk_add_gambar.setImageBitmap(g_bmp_bottom_sheet_produk_add);
                } catch (Exception e) {
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
                try {
                    Bundle extras = data.getExtras();
                    g_bmp_bottom_sheet_produk_edit = (Bitmap) extras.get("data");
                    g_iv_bottom_sheet_produk_edit_gambar.setImageBitmap(g_bmp_bottom_sheet_produk_edit);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //region BOTTOM SHEET METHOD
    private void showBottomSheetEditProduk(Product p_product){
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
//        g_iv_bottom_sheet_produk_edit_gambar.setImageDrawable(getResources().getDrawable(p_product.getBase64StringImage()));
        g_iv_bottom_sheet_produk_edit_gambar.setOnClickListener(this);

        //config edittext
        g_tv_bottom_sheet_produk_edit_nama.setText(p_product.getProduct_name());
        g_tv_bottom_sheet_produk_edit_harga.setText(Method.getIndoCurrency(p_product.getPrice()));
        g_tv_bottom_sheet_produk_edit_stok.setText(String.valueOf(p_product.getStock()));
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

    public String imageToString(Bitmap bitmap){
        ByteArrayOutputStream bost = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 ,bost);
        byte[] img = bost.toByteArray();
        String temp = Base64.encodeToString(img, Base64.NO_WRAP);
        return temp;
    }

    public void showSortPopupMenu(View v) {
        PopupMenu popup = new PopupMenu(g_context, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.produk_fragment_bar, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    public void showCategoryPopupMenu(View v) {
        PopupMenu popup = new PopupMenu(g_context, v);
        popup.getMenu().add(0, 1, 0, "Coba 1");
        popup.getMenu().add(0, 2, 1, "Coba 2");
        popup.getMenu().add(0, 3, 2, "Coba 3");
        popup.getMenu().add(0, 4, 3, "Coba 1");
        popup.getMenu().add(0, 5, 4, "Coba 2");
        popup.getMenu().add(0, 6, 5, "Coba 3");
        popup.getMenu().add(0, 7, 6, "Coba 1");
        popup.getMenu().add(0, 8, 7, "Coba 2");
        popup.getMenu().add(0, 9, 8, "Coba 3");
        popup.getMenu().add(0, 10, 9, "Coba 1");
        popup.getMenu().add(0, 11, 10, "Coba 2");
        popup.getMenu().add(0, 12, 11, "Coba 3");
        popup.getMenu().add(0, 13, 12, "Coba 1");
        popup.getMenu().add(0, 14, 13, "Coba 2");
        popup.getMenu().add(0, 15, 14, "Coba 3");
        popup.getMenu().add(0, 16, 15, "Coba 2");
        popup.getMenu().add(0, 17, 16, "Coba 3");
        popup.getMenu().add(0, 18, 17, "Coba 1");
        popup.getMenu().add(0, 19, 18, "Coba 2");
        popup.getMenu().add(0, 20, 19, "Coba 3");
        popup.getMenu().add(0, 21, 20, "Coba 2");
        popup.getMenu().add(0, 22, 21, "Coba 3");
        popup.getMenu().add(0, 23, 22, "Coba 1");
        popup.getMenu().add(0, 24, 23, "Coba 2");
        popup.getMenu().add(0, 25, 24, "Coba 3");
        popup.getMenu().add(0, 26, 25, "Coba 2");
        popup.getMenu().add(0, 27, 26, "Coba 3");
        popup.getMenu().add(0, 28, 27, "Coba 1");
        popup.getMenu().add(0, 29, 28, "Coba 2");
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }


}