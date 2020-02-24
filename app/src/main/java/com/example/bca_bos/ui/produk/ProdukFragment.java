package com.example.bca_bos.ui.produk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.ListKategoriDummy;
import com.example.bca_bos.ListProdukDummy;
import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Produk;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.makeramen.roundedimageview.RoundedImageView;

public class ProdukFragment extends Fragment implements OnCallBackListener, View.OnClickListener {

    public final static String KEY_PRODUK = "produk";

    private Context g_context;
    View g_view;

    private LinearLayoutManager g_linearlayoutmanager;
    private ProdukAdapter g_produkadapter;
    private RecyclerView g_produkfragment_recyclerview;

    private BottomSheetDialog g_bottomsheet_dialog;

    private LinearLayout g_produk_fragment_ll_add_button;
    private Spinner g_produk_fragment_spinner_kategori;
    private ArrayAdapter g_spinnerAdapter;
//    private ImageButton g_produk_fragment_btn_search, g_produk_fragment_btn_add, g_produk_fragment_btn_filter;
//    private EditText g_produk_fragment_et_search;
//    private TextView g_produk_fragment_tv_title;

    private boolean IS_NOT_LINEAR = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        g_context = container.getContext();
        g_view = inflater.inflate(R.layout.fragment_produk, container, false);

        g_produk_fragment_ll_add_button = g_view.findViewById(R.id.apps_produk_fragment_add_btn);
        g_produk_fragment_ll_add_button.setOnClickListener(this);

        g_produkfragment_recyclerview = g_view.findViewById(R.id.apps_produk_fragment_recyclerview);
        g_bottomsheet_dialog = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);

        g_linearlayoutmanager = new LinearLayoutManager(g_context);
        g_produkadapter = new ProdukAdapter();
        g_produkadapter.setParentOnCallBack(this);

        g_produkfragment_recyclerview.setAdapter(g_produkadapter);
        g_produkfragment_recyclerview.setLayoutManager(g_linearlayoutmanager);

        g_produk_fragment_spinner_kategori = g_view.findViewById(R.id.apps_produk_fragment_kategori_filter);
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

    public void OnCallBack(Object p_obj) {
        if(p_obj instanceof Produk) {
            Produk tmpProduk = (Produk) p_obj;
            showBottomSheetEditProduk(tmpProduk);
        }

    }

    @Override
    public void onClick(View v) {
        if(v == g_produk_fragment_ll_add_button){
            showBottomSheetAddProduk();
        }
    }

    private void showBottomSheetEditProduk(Produk p_produk){
        View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_edit_produk,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_edit_produk)
        );

        RoundedImageView l_iv_bottom_sheet_produk_edit_gambar = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_iv_gambar_edit_produk);

        TextView l_tv_bottom_sheet_produk_edit_nama = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_nama_edit_produk);
        TextView l_tv_bottom_sheet_produk_edit_harga = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_harga_edit_produk);
        TextView l_tv_bottom_sheet_produk_edit_stok = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_stok_edit_produk);
        TextView l_tv_bottom_sheet_produk_edit_berat = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_berat_edit_produk);

        Button l_btn_bottom_sheet_produk_edit_simpan = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_btn_simpan_edit_produk);
        Button l_btn_bottom_sheet_produk_edit_hapus = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_btn_hapus_edit_produk);

        l_iv_bottom_sheet_produk_edit_gambar.setImageDrawable(getResources().getDrawable(p_produk.getGambar()));

        l_tv_bottom_sheet_produk_edit_nama.setText(p_produk.getNama());
        l_tv_bottom_sheet_produk_edit_harga.setText(Method.getIndoCurrency(p_produk.getHarga()));
        l_tv_bottom_sheet_produk_edit_stok.setText(String.valueOf(p_produk.getStok()));
        l_tv_bottom_sheet_produk_edit_berat.setText(String.valueOf(0));

        l_btn_bottom_sheet_produk_edit_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g_bottomsheet_dialog.dismiss();
            }
        });

        l_btn_bottom_sheet_produk_edit_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g_bottomsheet_dialog.dismiss();
            }
        });

        g_bottomsheet_dialog.setContentView(l_bottomsheet_view_add);
        g_bottomsheet_dialog.show();
    }

    private void showBottomSheetAddProduk(){
        View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_add_produk,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_add_produk)
        );

        RoundedImageView l_iv_bottom_sheet_produk_add_gambar = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_iv_gambar_add_produk);

        TextView l_tv_bottom_sheet_produk_add_nama = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_nama_add_produk);
        TextView l_tv_bottom_sheet_produk_add_harga = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_harga_add_produk);
        TextView l_tv_bottom_sheet_produk_add_stok = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_stok_add_produk);
        TextView l_tv_bottom_sheet_produk_add_berat = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_et_berat_add_produk);

        Button l_btn_bottom_sheet_produk_add_tambah = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_btn_tambah_add_produk);
        Button l_btn_bottom_sheet_produk_add_batal = l_bottomsheet_view_add.findViewById(R.id.apps_bottom_sheet_btn_batal_add_produk);

        l_btn_bottom_sheet_produk_add_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g_bottomsheet_dialog.dismiss();
            }
        });

        l_btn_bottom_sheet_produk_add_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g_bottomsheet_dialog.dismiss();
            }
        });

        g_bottomsheet_dialog.setContentView(l_bottomsheet_view_add);
        g_bottomsheet_dialog.show();
    }
}