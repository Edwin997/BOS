package com.example.bca_bos.ui.beranda;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Buyer;
import com.example.bca_bos.models.products.Product;
import com.makeramen.roundedimageview.RoundedImageView;

public class BerandaFragment extends Fragment implements View.OnClickListener, OnCallBackListener {

    private Context g_context;
    private View g_view;

    private RecyclerView g_beranda_produk_fragment_recyclerview, g_beranda_pembeli_fragment_recyclerview;
    private LinearLayoutManager g_linearlayoutmanager_produk, g_linearlayoutmanager_pembeli;
    private BerandaProdukAdapter g_beranda_produk_adapter;
    private BerandaPembeliAdapter g_beranda_pembeli_adapter;
    private Button g_beranda_pesanan_belum_dikirim_button;

    //Product Popup
    private Dialog g_beranda_produk_popup;
    private RoundedImageView g_beranda_produk_popup_gambar;
    private TextView g_beranda_produk_popup_tv_nama, g_beranda_produk_popup_tv_harga, g_beranda_produk_popup_tv_stok;
    private Button g_beranda_produk_popup_btn_close;

    //Buyer Popup
    private Dialog g_beranda_pembeli_popup;
    private TextView g_beranda_pembeli_popup_tv_nama, g_beranda_pembeli_popup_tv_nominal, g_beranda_pembeli_popup_tv_transaksi;
    private Button g_beranda_pembeli_popup_btn_close;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        g_context = container.getContext();
        g_view = inflater.inflate(R.layout.fragment_beranda, container, false);

        //TRANSAKSI
        g_beranda_pesanan_belum_dikirim_button = g_view.findViewById(R.id.beranda_btn_pesanan_belum_dikirim);
        g_beranda_pesanan_belum_dikirim_button.setOnClickListener(this);

        //PRODUK
        //RecyclerView
        g_beranda_produk_fragment_recyclerview = g_view.findViewById(R.id.apps_beranda_produk_fragment_recyclerview);
        g_linearlayoutmanager_produk = new LinearLayoutManager(g_context, RecyclerView.HORIZONTAL, false);
        g_beranda_produk_adapter = new BerandaProdukAdapter();

        g_beranda_produk_fragment_recyclerview.setAdapter(g_beranda_produk_adapter);
        g_beranda_produk_fragment_recyclerview.setLayoutManager(g_linearlayoutmanager_produk);

        //Popup
        g_beranda_produk_adapter.setParentOnCallBack(this);
        g_beranda_produk_popup = new Dialog(g_context);

        //PEMBELI
        //RecyclerView
        g_beranda_pembeli_fragment_recyclerview = g_view.findViewById(R.id.apps_beranda_pembeli_fragment_recyclerview);
        g_linearlayoutmanager_pembeli = new LinearLayoutManager(g_context);
        g_beranda_pembeli_adapter = new BerandaPembeliAdapter();


        g_beranda_pembeli_fragment_recyclerview.setAdapter(g_beranda_pembeli_adapter);
        g_beranda_pembeli_fragment_recyclerview.setLayoutManager(g_linearlayoutmanager_pembeli);

        //Popup
        g_beranda_pembeli_adapter.setParentOnCallBack(this);
        g_beranda_pembeli_popup = new Dialog(g_context);



        return g_view;
    }

    @Override
    public void onClick(View p_view) {
        switch (p_view.getId()){
            case R.id.beranda_btn_pesanan_belum_dikirim:
                Bundle tmp_bundle = new Bundle();
                tmp_bundle.putInt("flag",1);
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.navigation_transaksi, tmp_bundle);
                break;
            case R.id.btn_popup_beranda_produk_close:
                g_beranda_produk_popup.dismiss();
                break;
            case R.id.btn_popup_beranda_pembeli_close:
                g_beranda_pembeli_popup.dismiss();
                break;
        }
    }

    @Override
    public void OnCallBack(Object p_obj) {
        if(p_obj instanceof Product){
            Product l_product = (Product) p_obj;
            showProdukPopUp(l_product);
        }else if (p_obj instanceof Buyer){
            Buyer l_pembeli = (Buyer) p_obj;
            showBuyerPopUp(l_pembeli);
        }
    }

    public void showProdukPopUp(Product p_product){
        g_beranda_produk_popup.setContentView(R.layout.layout_popup_beranda_produk);

        g_beranda_produk_popup_gambar = g_beranda_produk_popup.findViewById(R.id.iv_popup_beranda_produk_item);
//        g_beranda_produk_popup_gambar.setImageDrawable(getResources().getDrawable(p_product.getGambar()));

        g_beranda_produk_popup_tv_nama = g_beranda_produk_popup.findViewById(R.id.tv_popup_beranda_produk_nama_item);
        g_beranda_produk_popup_tv_nama.setText(String.valueOf(p_product.getProduct_name()));
        g_beranda_produk_popup_tv_harga = g_beranda_produk_popup.findViewById(R.id.tv_popup_beranda_produk_harga_item);
        g_beranda_produk_popup_tv_harga.setText(Method.getIndoCurrency(p_product.getPrice()));
        g_beranda_produk_popup_tv_stok = g_beranda_produk_popup.findViewById(R.id.tv_popup_beranda_produk_stok_item);
        g_beranda_produk_popup_tv_stok.setText("Stok : "+String.valueOf(p_product.getStock()));
        g_beranda_produk_popup_btn_close = g_beranda_produk_popup.findViewById(R.id.btn_popup_beranda_produk_close);
        g_beranda_produk_popup_btn_close.setOnClickListener(this);

        g_beranda_produk_popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        g_beranda_produk_popup.show();

    }

    public void showBuyerPopUp(Buyer p_pembeli){
        g_beranda_pembeli_popup.setContentView(R.layout.layout_popup_beranda_pembeli);

        g_beranda_pembeli_popup_tv_nama = g_beranda_pembeli_popup.findViewById(R.id.tv_popup_beranda_pembeli_nama_item);
        g_beranda_pembeli_popup_tv_nama.setText(String.valueOf(p_pembeli.getBuyer_name()));
        g_beranda_pembeli_popup_tv_nominal = g_beranda_pembeli_popup.findViewById(R.id.tv_popup_beranda_pembeli_nominal_item);
        g_beranda_pembeli_popup_tv_nominal.setText(Method.getIndoCurrency(p_pembeli.getNominalTransaksi()));
        g_beranda_pembeli_popup_tv_transaksi = g_beranda_pembeli_popup.findViewById(R.id.tv_popup_beranda_pembeli_transaksi_item);
        g_beranda_pembeli_popup_tv_transaksi.setText(String.valueOf(p_pembeli.getJumlahTransaksi()+" transaksi"));
        g_beranda_pembeli_popup_btn_close = g_beranda_pembeli_popup.findViewById(R.id.btn_popup_beranda_pembeli_close);
        g_beranda_pembeli_popup_btn_close.setOnClickListener(this);

        g_beranda_pembeli_popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        g_beranda_pembeli_popup.show();
    }
}