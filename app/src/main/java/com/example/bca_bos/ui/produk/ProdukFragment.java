package com.example.bca_bos.ui.produk;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.dummy.ListProdukDummy;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Seller;
import com.example.bca_bos.models.products.PrdCategory;
import com.example.bca_bos.models.products.Product;
import com.example.bca_bos.networks.VolleyClass;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ProdukFragment extends Fragment implements OnCallBackListener, View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    //region DATA MEMBER
    //FINAL STATIC DATA MEMBER
    public final static String KEY_PRODUK = "product";

    //VIEW DATA MEMBER
    public static ProdukFragment g_instance;

    private Context g_context;
    private View g_view;
    private ChooseImageFromDialog g_choose_dialog;
    private BottomSheetDialog g_bottomsheet_dialog_add;
    private BottomSheetDialog g_bottomsheet_dialog_edit;
    private Product g_product_onclick;
    private PopupMenu g_popup;

    //FRAGMENT SECTION DATA MEMBER
    private LinearLayout g_produk_fragment_ll_add_button;
    private ConstraintLayout g_produk_fragment_not_found;

    private RecyclerView g_produkfragment_recyclerview;
    private ProdukAdapter g_produkadapter;
    private LinearLayoutManager g_linearlayoutmanager;

    private ImageView g_produk_fragment_sort_btn, g_produk_fragment_category_btn, g_produk_fragment_search_btn;
    private EditText g_produk_fragment_search_et;

    private TextView g_tv_not_found_judul;
    private LottieAnimationView g_iv_not_found_animation;

    //ADD BOTTOM SHEET PRODUK DATA MEMBER
    private RoundedImageView g_iv_bottom_sheet_produk_add_gambar;
    private EditText g_tv_bottom_sheet_produk_add_nama, g_tv_bottom_sheet_produk_add_harga,
            g_tv_bottom_sheet_produk_add_stok, g_tv_bottom_sheet_produk_add_berat;
    private Button g_btn_bottom_sheet_produk_add_tambah, g_btn_bottom_sheet_produk_add_batal;
    private ImageButton g_btn_bottom_sheet_produk_add_stok_add_tambah, g_btn_bottom_sheet_produk_minus_stok_add_tambah,
            g_btn_bottom_sheet_produk_add_berat_add_tambah, g_btn_bottom_sheet_produk_minus_berat_add_tambah;
    private Spinner g_btn_bottom_sheet_produk_spinner_add_tambah;
    private ArrayAdapter g_sp_bottom_sheet_produk_spinner_adapter;
    private PrdCategory g_product_category_add;
    private Bitmap g_bmp_bottom_sheet_produk_add;
    private Uri pathh;

    //EDIT BOTTOM SHEET PRODUK DATA MEMBER
    private RoundedImageView g_iv_bottom_sheet_produk_edit_gambar;
    private EditText g_tv_bottom_sheet_produk_edit_nama, g_tv_bottom_sheet_produk_edit_harga,
            g_tv_bottom_sheet_produk_edit_stok, g_tv_bottom_sheet_produk_edit_berat;
    private Button g_btn_bottom_sheet_produk_edit_simpan, g_btn_bottom_sheet_produk_edit_hapus;
    private ImageButton g_btn_bottom_sheet_produk_add_stok_edit_tambah, g_btn_bottom_sheet_produk_minus_stok_edit_tambah,
            g_btn_bottom_sheet_produk_add_berat_edit_tambah, g_btn_bottom_sheet_produk_minus_berat_edit_tambah;
    private Spinner g_btn_bottom_sheet_produk_spinner_edit_tambah;
    private ArrayAdapter g_sp_bottom_sheet_produk_spinner_edit_adapter;
    private PrdCategory g_product_category_edit;
    private Bitmap g_bmp_bottom_sheet_produk_edit;
    //endregion

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //inisiasi view, choose dialog, bottom sheet dialog
        g_instance = this;
        g_context = container.getContext();
        g_view = inflater.inflate(R.layout.fragment_produk, container, false);
        g_choose_dialog = new ChooseImageFromDialog(this);
        g_bottomsheet_dialog_add = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);
        g_bottomsheet_dialog_edit = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);

        //inisiasi layout
        g_produk_fragment_ll_add_button = g_view.findViewById(R.id.apps_produk_fragment_add_btn);
        g_produk_fragment_not_found = g_view.findViewById(R.id.apps_produk_fragment_not_found);

        //inisiasi recyclerview
        g_produkfragment_recyclerview = g_view.findViewById(R.id.apps_produk_fragment_recyclerview);

        //inisiasi imageview
        g_produk_fragment_sort_btn = g_view.findViewById(R.id.apps_produk_fragment_sort_btn);
        g_produk_fragment_category_btn = g_view.findViewById(R.id.apps_produk_fragment_category_btn);
        g_produk_fragment_search_btn = g_view.findViewById(R.id.apps_produk_fragment_search_btn);

        //inisiasi edittext
        g_produk_fragment_search_et = g_view.findViewById(R.id.apps_produk_fragment_search_et);

        //inisiasi textview
        g_tv_not_found_judul = g_view.findViewById(R.id.apps_tv_not_found_judul);

        //inisiasi lottie
        g_iv_not_found_animation = g_view.findViewById(R.id.apps_iv_not_found_animation);

        //config layout
        g_produk_fragment_ll_add_button.setOnClickListener(this);
        ProdukFragment.g_instance.showLayout(0, false);

        //config recyclerview
        g_linearlayoutmanager = new LinearLayoutManager(g_context);
        g_produkadapter = new ProdukAdapter();

        g_produkadapter.setParentOnCallBack(this);
        g_produkfragment_recyclerview.setAdapter(g_produkadapter);
        g_produkfragment_recyclerview.setLayoutManager(g_linearlayoutmanager);
        VolleyClass.getProduct(g_context, 3, g_produkadapter);
//        g_produkadapter.setDatasetProduk(ListProdukDummy.productList);

        //config imageview
        g_produk_fragment_sort_btn.setOnClickListener(this);
        g_produk_fragment_category_btn.setOnClickListener(this);
        g_produk_fragment_search_btn.setOnClickListener(this);

        //config edittext
        g_produk_fragment_search_et.addTextChangedListener(new SearchTextWatcher(g_produk_fragment_search_et));

        return g_view;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.apps_produk_item_sort_asc:
                g_produkadapter.sortProduct("ASC");
                break;
            case R.id.apps_produk_item_sort_desc:
                g_produkadapter.sortProduct("DESC");
                break;
            default:
                int idx = VolleyClass.findProductCategoryId(item.getOrder());
                g_produkadapter.getProductFiltered(idx);
        }
        return true;
    }

    @Override
    public void OnCallBack(Object p_obj) {
        if(p_obj instanceof Product) {
            g_product_onclick = (Product) p_obj;
            Log.d("BOSVOLLEY", g_product_onclick.getPrdCategory().getId_prd_category() + "");
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
                VolleyClass.getProductCategory(g_context, 3, v);
                break;
            case R.id.apps_produk_fragment_search_btn:
                g_produkadapter.findProduct(g_produk_fragment_search_et.getText().toString());
                break;

            //region ADD PRODUK BOTTOM SHEET
            case R.id.apps_bottom_sheet_iv_gambar_add_produk:
                g_choose_dialog.showChooseDialogAdd(g_context);
                break;
            case R.id.apps_bottom_sheet_btn_tambah_add_produk:
                Seller seller = new Seller();
                seller.setId_seller(3);

                Product tmpProduct = new Product();
                tmpProduct.setProduct_name(g_tv_bottom_sheet_produk_add_nama.getText().toString());
                tmpProduct.setPrice(Integer.parseInt(g_tv_bottom_sheet_produk_add_harga.getText().toString()));
                tmpProduct.setStock(Integer.parseInt(g_tv_bottom_sheet_produk_add_stok.getText().toString()));
                tmpProduct.setWeight(Integer.parseInt(g_tv_bottom_sheet_produk_add_berat.getText().toString()));
                tmpProduct.setImage_path(imageToString(g_bmp_bottom_sheet_produk_add));
                tmpProduct.setPrdCategory(g_product_category_add);
                tmpProduct.setSeller(seller);

                VolleyClass.insertProduct(g_context, tmpProduct, g_produkadapter);

                g_bottomsheet_dialog_add.dismiss();
                break;
            case R.id.apps_bottom_sheet_btn_batal_add_produk:
                g_bottomsheet_dialog_add.dismiss();
                break;
            case R.id.apps_bottom_sheet_btn_add_berat_add_produk:
                if(g_tv_bottom_sheet_produk_add_berat.getText().toString().isEmpty()){
                    g_tv_bottom_sheet_produk_add_berat.setText("1");
                    break;
                }
                else{
                    int tmpcount =  Integer.parseInt(g_tv_bottom_sheet_produk_add_berat.getText().toString());
                    g_tv_bottom_sheet_produk_add_berat.setText(String.valueOf(tmpcount + 1));
                }
                break;
            case R.id.apps_bottom_sheet_btn_minus_berat_add_produk:
                if(g_tv_bottom_sheet_produk_add_berat.getText().toString().isEmpty()){
                    g_tv_bottom_sheet_produk_add_berat.setText("0");
                    break;
                }
                else{
                    int tmpcount =  Integer.parseInt(g_tv_bottom_sheet_produk_add_berat.getText().toString());
                    if(tmpcount - 1 <= 0){
                        g_tv_bottom_sheet_produk_add_berat.setText("0");
                    }
                    else{
                        g_tv_bottom_sheet_produk_add_berat.setText(String.valueOf(tmpcount - 1));
                    }
                }
                break;
            case R.id.apps_bottom_sheet_btn_add_stok_add_produk:
                if(g_tv_bottom_sheet_produk_add_stok.getText().toString().isEmpty()){
                    g_tv_bottom_sheet_produk_add_stok.setText("1");
                    break;
                }
                else{
                    int tmpcount =  Integer.parseInt(g_tv_bottom_sheet_produk_add_stok.getText().toString());
                    g_tv_bottom_sheet_produk_add_stok.setText(String.valueOf(tmpcount + 1));
                }
                break;
            case R.id.apps_bottom_sheet_btn_minus_stok_add_produk:
                if(g_tv_bottom_sheet_produk_add_stok.getText().toString().isEmpty()){
                    g_tv_bottom_sheet_produk_add_stok.setText("0");
                    break;
                }
                else{
                    int tmpcount =  Integer.parseInt(g_tv_bottom_sheet_produk_add_stok.getText().toString());
                    if(tmpcount - 1 <= 0){
                        g_tv_bottom_sheet_produk_add_stok.setText("0");
                    }
                    else{
                        g_tv_bottom_sheet_produk_add_stok.setText(String.valueOf(tmpcount - 1));
                    }
                }
                break;
            //endregion

            //region EDIT PRODUK BOTTOM SHEET
            case R.id.apps_bottom_sheet_iv_gambar_edit_produk:
                g_choose_dialog.showChooseDialogEdit(g_context);
                break;
            case R.id.apps_bottom_sheet_btn_simpan_edit_produk:
                Seller selleredit = new Seller();
                selleredit.setId_seller(3);

                Product tmpProductedit = new Product();
                tmpProductedit.setId_product(g_product_onclick.getId_product());
                tmpProductedit.setProduct_name(g_tv_bottom_sheet_produk_edit_nama.getText().toString());
                tmpProductedit.setPrice(Integer.parseInt(g_tv_bottom_sheet_produk_edit_harga.getText().toString()));
                tmpProductedit.setStock(Integer.parseInt(g_tv_bottom_sheet_produk_edit_stok.getText().toString()));
                tmpProductedit.setWeight(Integer.parseInt(g_tv_bottom_sheet_produk_edit_berat.getText().toString()));
                tmpProductedit.setImage_path(imageToString(g_bmp_bottom_sheet_produk_edit));
                tmpProductedit.setPrdCategory(g_product_category_edit);
                tmpProductedit.setSeller(selleredit);
                g_bottomsheet_dialog_edit.dismiss();
                VolleyClass.updateProduct(g_context, tmpProductedit, g_produkadapter);
                break;
            case R.id.apps_bottom_sheet_btn_hapus_edit_produk:
                g_bottomsheet_dialog_edit.dismiss();
                VolleyClass.deleteProduct(g_context, g_product_onclick.getId_product(), g_produkadapter);
                break;
            case R.id.apps_bottom_sheet_btn_add_berat_edit_produk:
                if(g_tv_bottom_sheet_produk_edit_berat.getText().toString().isEmpty()){
                    g_tv_bottom_sheet_produk_edit_berat.setText("1");
                    break;
                }
                else{
                    int tmpcount =  Integer.parseInt(g_tv_bottom_sheet_produk_edit_berat.getText().toString());
                    g_tv_bottom_sheet_produk_edit_berat.setText(String.valueOf(tmpcount + 1));
                }
                break;
            case R.id.apps_bottom_sheet_btn_minus_berat_edit_produk:
                if(g_tv_bottom_sheet_produk_edit_berat.getText().toString().isEmpty()){
                    g_tv_bottom_sheet_produk_edit_berat.setText("0");
                    break;
                }
                else{
                    int tmpcount =  Integer.parseInt(g_tv_bottom_sheet_produk_edit_berat.getText().toString());
                    if(tmpcount - 1 <= 0){
                        g_tv_bottom_sheet_produk_edit_berat.setText("0");
                    }
                    else{
                        g_tv_bottom_sheet_produk_edit_berat.setText(String.valueOf(tmpcount - 1));
                    }
                }
                break;
            case R.id.apps_bottom_sheet_btn_add_stok_edit_produk:
                if(g_tv_bottom_sheet_produk_edit_stok.getText().toString().isEmpty()){
                    g_tv_bottom_sheet_produk_edit_stok.setText("1");
                    break;
                }
                else{
                    int tmpcount =  Integer.parseInt(g_tv_bottom_sheet_produk_edit_stok.getText().toString());
                    g_tv_bottom_sheet_produk_edit_stok.setText(String.valueOf(tmpcount + 1));
                }
                break;
            case R.id.apps_bottom_sheet_btn_minus_stok_edit_produk:
                if(g_tv_bottom_sheet_produk_edit_stok.getText().toString().isEmpty()){
                    g_tv_bottom_sheet_produk_edit_stok.setText("0");
                    break;
                }
                else{
                    int tmpcount =  Integer.parseInt(g_tv_bottom_sheet_produk_edit_stok.getText().toString());
                    if(tmpcount - 1 <= 0){
                        g_tv_bottom_sheet_produk_edit_stok.setText("0");
                    }
                    else{
                        g_tv_bottom_sheet_produk_edit_stok.setText(String.valueOf(tmpcount - 1));
                    }
                }
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
    private void showBottomSheetEditProduk(final Product p_product){
        //inisiasi view
        final View l_bottomsheet_view_edit = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_edit_produk,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_edit_produk)
        );

        //inisiasi imageview
        g_iv_bottom_sheet_produk_edit_gambar = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_iv_gambar_edit_produk);

        //inisiasi edittext
        g_tv_bottom_sheet_produk_edit_nama = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_et_nama_toko_edit_produk);
        g_tv_bottom_sheet_produk_edit_harga = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_et_harga_edit_produk);
        g_tv_bottom_sheet_produk_edit_stok = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_et_stok_edit_produk);
        g_tv_bottom_sheet_produk_edit_berat = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_et_berat_edit_produk);

        //inisiasi button
        g_btn_bottom_sheet_produk_edit_simpan = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_btn_simpan_edit_produk);
        g_btn_bottom_sheet_produk_edit_hapus = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_btn_hapus_edit_produk);

        //inisiasi imagebutton
        g_btn_bottom_sheet_produk_add_berat_edit_tambah = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_btn_add_berat_edit_produk);
        g_btn_bottom_sheet_produk_minus_berat_edit_tambah = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_btn_minus_berat_edit_produk);
        g_btn_bottom_sheet_produk_add_stok_edit_tambah = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_btn_add_stok_edit_produk);
        g_btn_bottom_sheet_produk_minus_stok_edit_tambah = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_btn_minus_stok_edit_produk);

        //inisiasi spinner
        g_btn_bottom_sheet_produk_spinner_edit_tambah = l_bottomsheet_view_edit.findViewById(R.id.apps_bottom_sheet_spinner_edit_produk);


        //config imageview
//        g_iv_bottom_sheet_produk_edit_gambar.setImageDrawable(getResources().getDrawable(p_product.getBase64StringImage()));
        g_iv_bottom_sheet_produk_edit_gambar.setOnClickListener(this);

        //config edittext
        g_tv_bottom_sheet_produk_edit_nama.setText(p_product.getProduct_name());
        g_tv_bottom_sheet_produk_edit_harga.setText(String.valueOf(p_product.getPrice()));
        g_tv_bottom_sheet_produk_edit_stok.setText(String.valueOf(p_product.getStock()));
        g_tv_bottom_sheet_produk_edit_berat.setText(String.valueOf(p_product.getWeight()));

        //config button
        g_btn_bottom_sheet_produk_edit_simpan.setOnClickListener(this);
        g_btn_bottom_sheet_produk_edit_hapus.setOnClickListener(this);

        //config imagebutton
        g_btn_bottom_sheet_produk_add_berat_edit_tambah.setOnClickListener(this);
        g_btn_bottom_sheet_produk_minus_berat_edit_tambah.setOnClickListener(this);
        g_btn_bottom_sheet_produk_add_stok_edit_tambah.setOnClickListener(this);
        g_btn_bottom_sheet_produk_minus_stok_edit_tambah.setOnClickListener(this);

        //config spinner
        ArrayList<String> tmpCategoryAdd = new ArrayList<>();
        g_sp_bottom_sheet_produk_spinner_edit_adapter = new ArrayAdapter(g_context, android.R.layout.simple_spinner_item, tmpCategoryAdd);
        g_sp_bottom_sheet_produk_spinner_edit_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        g_btn_bottom_sheet_produk_spinner_edit_tambah.setAdapter(g_sp_bottom_sheet_produk_spinner_edit_adapter);
        g_btn_bottom_sheet_produk_spinner_edit_tambah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                g_product_category_edit = VolleyClass.findFromAllProductCategory(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                g_btn_bottom_sheet_produk_spinner_edit_tambah.setSelection(VolleyClass.findPositionFromAllProductCategory(p_product.getPrdCategory().getId_prd_category()));
                g_product_category_edit = VolleyClass.findFromAllProductCategory(VolleyClass.findPositionFromAllProductCategory(p_product.getPrdCategory().getId_prd_category()));
            }
        });

        VolleyClass.getProductCategoryAllEdit(g_context, g_sp_bottom_sheet_produk_spinner_edit_adapter, p_product.getPrdCategory().getId_prd_category());

        //show bottomsheet
        g_bottomsheet_dialog_edit.setContentView(l_bottomsheet_view_edit);
        g_bottomsheet_dialog_edit.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                LinearLayout layoutsss = l_bottomsheet_view_edit.findViewById(R.id.layout_apps_bottom_sheet_container_edit_produk);
                BottomSheetBehavior.from(layoutsss).setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
            }
        });
        g_bottomsheet_dialog_edit.show();
    }

    public void setSelectionSpinnerEdit(int p_id_prd_category){
        g_btn_bottom_sheet_produk_spinner_edit_tambah.setSelection(VolleyClass.findPositionFromAllProductCategory(p_id_prd_category));
    }

    private void showBottomSheetAddProduk(){
        //inisiasi view
        final View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
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

        //inisiasi imagebutton
        g_btn_bottom_sheet_produk_add_berat_add_tambah = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_btn_add_berat_add_produk);
        g_btn_bottom_sheet_produk_minus_berat_add_tambah = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_btn_minus_berat_add_produk);
        g_btn_bottom_sheet_produk_add_stok_add_tambah = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_btn_add_stok_add_produk);
        g_btn_bottom_sheet_produk_minus_stok_add_tambah = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_btn_minus_stok_add_produk);

        //inisiasi spinner
        g_btn_bottom_sheet_produk_spinner_add_tambah = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_spinner_add_produk);

        //config imageview
        g_iv_bottom_sheet_produk_add_gambar.setOnClickListener(this);

        //config button
        g_btn_bottom_sheet_produk_add_tambah.setOnClickListener(this);
        g_btn_bottom_sheet_produk_add_batal.setOnClickListener(this);

        //config imagebutton
        g_btn_bottom_sheet_produk_add_berat_add_tambah.setOnClickListener(this);
        g_btn_bottom_sheet_produk_minus_berat_add_tambah.setOnClickListener(this);
        g_btn_bottom_sheet_produk_add_stok_add_tambah.setOnClickListener(this);
        g_btn_bottom_sheet_produk_minus_stok_add_tambah.setOnClickListener(this);

        //config spinner
        ArrayList<String> tmpCategoryAdd = new ArrayList<>();
        g_sp_bottom_sheet_produk_spinner_adapter = new ArrayAdapter(g_context, android.R.layout.simple_spinner_item, tmpCategoryAdd);
        g_sp_bottom_sheet_produk_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        g_btn_bottom_sheet_produk_spinner_add_tambah.setAdapter(g_sp_bottom_sheet_produk_spinner_adapter);
        g_btn_bottom_sheet_produk_spinner_add_tambah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                g_product_category_add = VolleyClass.findFromAllProductCategory(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                g_product_category_add = VolleyClass.findFromAllProductCategory(0);
            }
        });

        VolleyClass.getProductCategoryAll(g_context, g_sp_bottom_sheet_produk_spinner_adapter);

        //show bottomsheet
        g_bottomsheet_dialog_add.setContentView(l_bottomsheet_view_add);
        g_bottomsheet_dialog_add.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                LinearLayout layoutsss = l_bottomsheet_view_add.findViewById(R.id.layout_apps_bottom_sheet_container_add_produk);
                BottomSheetBehavior.from(layoutsss).setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
            }
        });

        g_bottomsheet_dialog_add.show();
    }
    //endregion

    private void changeLayoutValue(int p_count){
        if(p_count > 0){
            g_produkfragment_recyclerview.setVisibility(View.VISIBLE);
            g_produk_fragment_not_found.setVisibility(View.GONE);
        }
        else{
            g_tv_not_found_judul.setText(getText(R.string.PRODUCT_NOT_FOUND));
            g_iv_not_found_animation.setAnimation(R.raw.no_product_animation);
            g_iv_not_found_animation.playAnimation();
            g_iv_not_found_animation.loop(true);

            g_produkfragment_recyclerview.setVisibility(View.GONE);
            g_produk_fragment_not_found.setVisibility(View.VISIBLE);
        }
    }

    public void showLayout(int p_count, boolean p_connect){
        if(p_connect){
            changeLayoutValue(p_count);
        }
        else{
            g_tv_not_found_judul.setText(getText(R.string.INTERNET_NOT_FOUND));
            g_iv_not_found_animation.setAnimation(R.raw.no_internet_animation);
            g_iv_not_found_animation.playAnimation();
            g_iv_not_found_animation.loop(true);

            g_produkfragment_recyclerview.setVisibility(View.GONE);
            g_produk_fragment_not_found.setVisibility(View.VISIBLE);
        }
    }

    public String imageToString(Bitmap bitmap){
        ByteArrayOutputStream bost = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 ,bost);
        byte[] img = bost.toByteArray();
        String temp = Base64.encodeToString(img, Base64.NO_WRAP);
        return temp;
    }

    public void showSortPopupMenu(View p_view) {
        g_popup = new PopupMenu(g_context, p_view);
        MenuInflater inflater = g_popup.getMenuInflater();
        inflater.inflate(R.menu.produk_fragment_bar, g_popup.getMenu());
        g_popup.setOnMenuItemClickListener(this);
        g_popup.show();
    }

    public void showCategoryPopupMenu(View p_view, List<PrdCategory> p_list_category) {
        g_popup = new PopupMenu(g_context, p_view);
        g_popup.getMenu().add(0, 0, 0, "Semua Produk");
        for (int i = 0; i < p_list_category.size(); i++){
            g_popup.getMenu().add(0, p_list_category.get(i).getId_prd_category(),
                    i + 1, p_list_category.get(i).getCategory_name());
        }
        g_popup.setOnMenuItemClickListener(this);
        g_popup.show();
    }

    private class SearchTextWatcher implements TextWatcher {

        private EditText l_edittext;

        private int l_delay = 1000;
        private long l_time_last_editted = 0;
        private Handler l_handler;

        private Runnable l_thread_show_search_result = new Runnable() {
            public void run() {
                if (l_edittext != null) {
                    if (System.currentTimeMillis() > (l_time_last_editted + l_delay - 500)) {
                        g_produkadapter.findProduct(l_edittext.getText().toString());
                    }
                }
            }
        };

        public SearchTextWatcher(EditText p_edittext){
            l_edittext = p_edittext;
            l_handler = new Handler();
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(final Editable editable) {
            if(l_edittext != null){
                l_time_last_editted = System.currentTimeMillis();
                l_handler.postDelayed(l_thread_show_search_result, l_delay);
            }
        }
    }
}