package com.example.bca_bos.ui.beranda;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import com.example.bca_bos.models.Seller;
import com.example.bca_bos.models.products.Product;
import com.example.bca_bos.networks.VolleyClass;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class BerandaFragment extends Fragment implements View.OnClickListener, OnCallBackListener {

    private Context g_context;
    private View g_view;

    //Profile
    private TextView g_beranda_tv_nama_toko, g_beranda_tv_nominal_jumlah_transaksi, g_beranda_tv_jumlah_transaksi;
    public static BerandaFragment g_instance;

    //Produk dan Pembeli RecyclerView
    private RecyclerView g_beranda_produk_fragment_recyclerview, g_beranda_pembeli_fragment_recyclerview;
    private LinearLayoutManager g_linearlayoutmanager_produk, g_linearlayoutmanager_pembeli;
    private BerandaProdukAdapter g_beranda_produk_adapter;
    private BerandaPembeliAdapter g_beranda_pembeli_adapter;
    private Button g_beranda_pesanan_belum_dikirim_button;

    //Product Popup
    private Dialog g_beranda_produk_popup;
    private RoundedImageView g_beranda_produk_popup_gambar;
    private TextView g_beranda_produk_popup_tv_nama, g_beranda_produk_popup_tv_harga, g_beranda_produk_popup_tv_berat,
            g_beranda_produk_popup_tv_stok, g_beranda_produk_popup_tv_terjual;
    private Button g_beranda_produk_popup_btn_close, g_beranda_produk_popup_btn_tawarkan;
    private RecyclerView g_beranda_produk_popup_tawarkan_pembeli_rv;
    private LinearLayoutManager g_linearlayoutmanager_tawarkan_pembeli;
    private BerandaTawarkanPembeliAdapter g_beranda_tawarkan_pembeli_adapter;

    private Product g_product_onclick;

    //Product Popup List
    private Dialog g_beranda_produk_list_popup;
    private TextView g_beranda_produk_list_judul;
    private Button g_beranda_produk_list_btn_close, g_beranda_produk_list_btn_tawarkan;
    private RecyclerView g_beranda_produk_list_rv;
    private LinearLayoutManager g_beranda_produk_list_layout_manager;
    private BerandaListTawarkanPembeliAdapter g_beranda_produk_list_adapter;

    //Buyer Popup
    private Dialog g_beranda_pembeli_popup;
    private TextView g_beranda_pembeli_popup_tv_nama ,g_beranda_pembeli_popup_tv_no_hp ,g_beranda_pembeli_popup_tv_transaksi;
    private Button g_beranda_pembeli_popup_btn_close, g_beranda_pembeli_popup_btn_tawarkan;
    private RecyclerView g_beranda_pembeli_popup_tawarkan_produk_rv;
    private LinearLayoutManager g_linearlayoutmanager_tawarkan_produk;
    private BerandaTawarkanProdukAdapter g_beranda_tawarkan_produk_adapter;

    private Buyer g_pembeli_onclick;

    //Buyer Popup List
    private Dialog g_beranda_pembeli_list_popup;
    private TextView g_beranda_pembeli_list_judul;
    private Button g_beranda_pembeli_list_btn_close, g_beranda_pembeli_list_btn_tawarkan;
    private RecyclerView g_beranda_pembeli_list_rv;
    private LinearLayoutManager g_beranda_pembeli_list_layout_manager;
    private BerandaListTawarkanProdukAdapter g_beranda_pembeli_list_adapter;

    //Shared Preference
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String SELLER_ID = "SELLER_ID";
    int g_seller_id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Get Seller ID
        SharedPreferences l_preference = this.getActivity().getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        g_seller_id = l_preference.getInt(SELLER_ID, -1);

        //Inisialisasi
        g_instance = this;
        g_context = container.getContext();
        g_view = inflater.inflate(R.layout.fragment_beranda, container, false);
        VolleyClass.getProfileForBeranda(g_context, g_seller_id);
        VolleyClass.getJumlahTransaksiBulanIni(g_context, g_seller_id);
        VolleyClass.getJumlahTransaksiSudahDIbayar(g_context, g_seller_id);

        //PROFILE
        g_beranda_tv_nama_toko = g_view.findViewById(R.id.apps_beranda_nama_toko_text_view);
        g_beranda_tv_nominal_jumlah_transaksi = g_view.findViewById(R.id.apps_beranda_nominal_jumlah_transaksi_text_view);
        g_beranda_tv_jumlah_transaksi = g_view.findViewById(R.id.apps_beranda_jumlah_transaksi_text_view);

        //TRANSAKSI
        g_beranda_pesanan_belum_dikirim_button = g_view.findViewById(R.id.beranda_btn_pesanan_belum_dikirim);
        g_beranda_pesanan_belum_dikirim_button.setOnClickListener(this);

        //PRODUK
        //RecyclerView
        g_beranda_produk_fragment_recyclerview = g_view.findViewById(R.id.apps_beranda_produk_fragment_recyclerview);
        g_linearlayoutmanager_produk = new LinearLayoutManager(g_context, RecyclerView.HORIZONTAL, false);
        g_beranda_produk_adapter = new BerandaProdukAdapter();
        VolleyClass.getProdukTerlaris(g_context, 3, g_beranda_produk_adapter);

        g_beranda_produk_fragment_recyclerview.setAdapter(g_beranda_produk_adapter);
        g_beranda_produk_fragment_recyclerview.setLayoutManager(g_linearlayoutmanager_produk);

        //Popup
        g_beranda_produk_adapter.setParentOnCallBack(this);
        g_beranda_produk_popup = new Dialog(g_context);
        g_beranda_produk_list_popup = new Dialog(g_context);

        //PEMBELI
        //RecyclerView
        g_beranda_pembeli_fragment_recyclerview = g_view.findViewById(R.id.apps_beranda_pembeli_fragment_recyclerview);
        g_linearlayoutmanager_pembeli = new LinearLayoutManager(g_context);
        g_beranda_pembeli_adapter = new BerandaPembeliAdapter();
        VolleyClass.getPembeliSetia(g_context, 3, g_beranda_pembeli_adapter);

        g_beranda_pembeli_fragment_recyclerview.setAdapter(g_beranda_pembeli_adapter);
        g_beranda_pembeli_fragment_recyclerview.setLayoutManager(g_linearlayoutmanager_pembeli);

        //Popup
        g_beranda_pembeli_adapter.setParentOnCallBack(this);
        g_beranda_pembeli_popup = new Dialog(g_context);
        g_beranda_pembeli_list_popup = new Dialog(g_context);

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

            //region tawarkan 1 produk ke banyak pembeli
            case R.id.btn_popup_beranda_produk_close:
                g_beranda_produk_popup.dismiss();
                break;
            case R.id.btn_popup_beranda_produk_tawarkan:
                showProdukListPopUp(g_product_onclick, g_beranda_tawarkan_pembeli_adapter.getListPembeliSetia());
                g_beranda_produk_popup.dismiss();
                break;
            //endregion

            //region tawarkan banyak produk ke 1 pembeli
            case R.id.btn_popup_beranda_pembeli_close:
                g_beranda_pembeli_popup.dismiss();
                break;
            case R.id.btn_popup_beranda_pembeli_tawarkan:
                showBuyerListPopUp(g_pembeli_onclick, g_beranda_tawarkan_produk_adapter.getListProductFavorit());
                g_beranda_pembeli_popup.dismiss();
                break;
            //endregion

            //region list dialog tawarkan
            case R.id.btn_dialog_listitem_beranda_tutup:
                if(p_view == g_beranda_pembeli_list_btn_close){
                    g_beranda_pembeli_list_popup.dismiss();
                }
                else if(p_view == g_beranda_produk_list_btn_close){
                    g_beranda_produk_list_popup.dismiss();
                }
                break;
            case R.id.btn_dialog_listitem_beranda_tawarkan:
                if(p_view == g_beranda_pembeli_list_btn_tawarkan){
                    g_beranda_pembeli_list_popup.dismiss();

                    Intent sendSMS = new Intent(Intent.ACTION_SENDTO);
                    sendSMS.setType("text/plain");
                    sendSMS.setData(Uri.parse("smsto:" + g_pembeli_onclick.getPhone()));
                    sendSMS.putExtra("sms_body", "Hallo, Kami dari toko Winstok Cell ingin menawarkan produk kami yaitu:" +
                            g_beranda_pembeli_list_adapter.getProdukUntukTawarkan() +
                            "\nTerima Kasih.");

                    if (sendSMS.resolveActivity(g_context.getPackageManager()) != null) {
                        startActivity(sendSMS);
                    }
                }
                else if(p_view == g_beranda_produk_list_btn_tawarkan){
                    g_beranda_produk_list_popup.dismiss();

                    Intent sendSMS = new Intent(Intent.ACTION_SENDTO);
                    sendSMS.setType("text/plain");
                    sendSMS.setData(Uri.parse("smsto:" + g_beranda_produk_list_adapter.getNomorUntukTawarkan()));
                    sendSMS.putExtra("sms_body", "Hallo, Kami dari toko Winstok Cell ingin menawarkan produk kami yaitu:" +
                            "\n- Nama Produk : " + g_product_onclick.getProduct_name() + "\n- Harga/Produk : " + Method.getIndoCurrency(g_product_onclick.getPrice()) +
                            "\nTerima Kasih.");

                    if (sendSMS.resolveActivity(g_context.getPackageManager()) != null) {
                        startActivity(sendSMS);
                    }
                }

                break;
            //endregion
        }
    }

    @Override
    public void OnCallBack(Object p_obj) {
        if(p_obj instanceof Product){
            g_product_onclick = (Product) p_obj;
            showProdukPopUp(g_product_onclick);
        }else if (p_obj instanceof Buyer){
            g_pembeli_onclick = (Buyer) p_obj;
            showBuyerPopUp(g_pembeli_onclick);
        }
    }

    public void showProdukPopUp(Product p_product){
        g_beranda_produk_popup.setContentView(R.layout.layout_popup_beranda_produk);

        g_beranda_produk_popup_tv_harga = g_beranda_produk_popup.findViewById(R.id.tv_popup_beranda_produk_harga_item);
        g_beranda_produk_popup_tv_berat = g_beranda_produk_popup.findViewById(R.id.tv_popup_beranda_produk_berat_item);
        g_beranda_produk_popup_tv_stok = g_beranda_produk_popup.findViewById(R.id.tv_popup_beranda_produk_stok_item);

        int tmp_id_product = p_product.getId_product();
        VolleyClass.getProdukDetail(g_context, tmp_id_product);

        g_beranda_produk_popup_gambar = g_beranda_produk_popup.findViewById(R.id.iv_popup_beranda_produk_item);
//        g_beranda_produk_popup_gambar.setImageDrawable(getResources().getDrawable(p_product.getGambar()));

        //Text View
        g_beranda_produk_popup_tv_nama = g_beranda_produk_popup.findViewById(R.id.tv_popup_beranda_produk_nama_item);
        g_beranda_produk_popup_tv_nama.setText(String.valueOf(p_product.getProduct_name()));
        g_beranda_produk_popup_tv_terjual = g_beranda_produk_popup.findViewById(R.id.tv_popup_beranda_produk_terjual_item);
        g_beranda_produk_popup_tv_terjual.setText("Terjual " + p_product.getQty()+" kali");

        //Button
        g_beranda_produk_popup_btn_close = g_beranda_produk_popup.findViewById(R.id.btn_popup_beranda_produk_close);
        g_beranda_produk_popup_btn_tawarkan = g_beranda_produk_popup.findViewById(R.id.btn_popup_beranda_produk_tawarkan);
        g_beranda_produk_popup_btn_close.setOnClickListener(this);
        g_beranda_produk_popup_btn_tawarkan.setOnClickListener(this);

        //Recyclerview


        g_beranda_produk_popup_tawarkan_pembeli_rv = g_beranda_produk_popup.findViewById(R.id.rv_popup_beranda_produk_tawarkan_pembeli);
        g_linearlayoutmanager_tawarkan_pembeli = new LinearLayoutManager(g_context, RecyclerView.VERTICAL, false);
        g_beranda_tawarkan_pembeli_adapter = new BerandaTawarkanPembeliAdapter();

        g_beranda_produk_popup_tawarkan_pembeli_rv.setAdapter(g_beranda_tawarkan_pembeli_adapter);
        g_beranda_produk_popup_tawarkan_pembeli_rv.setLayoutManager(g_linearlayoutmanager_tawarkan_pembeli);

        VolleyClass.buyerRecommendation(g_context, String.valueOf(3), String.valueOf(tmp_id_product), g_beranda_tawarkan_pembeli_adapter);

        g_beranda_produk_popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        g_beranda_produk_popup.show();

    }

    public void showProdukListPopUp(Product p_product, List<Buyer> p_list){
        g_beranda_produk_list_popup.setContentView(R.layout.layout_dialog_listitem_beranda);

        //Text View
        g_beranda_produk_list_judul = g_beranda_produk_list_popup.findViewById(R.id.tv_dialog_listitem_beranda_judul);
        g_beranda_produk_list_judul.setText("Silahkan pilih pembeli yang ingin anda tawarkan produk: \n" + p_product.getProduct_name());

        //Button
        g_beranda_produk_list_btn_close = g_beranda_produk_list_popup.findViewById(R.id.btn_dialog_listitem_beranda_tutup);
        g_beranda_produk_list_btn_tawarkan = g_beranda_produk_list_popup.findViewById(R.id.btn_dialog_listitem_beranda_tawarkan);
        g_beranda_produk_list_btn_close.setOnClickListener(this);
        g_beranda_produk_list_btn_tawarkan.setOnClickListener(this);

        //Recyclerview
        g_beranda_produk_list_rv = g_beranda_produk_list_popup.findViewById(R.id.rv_dialog_listitem_beranda);
        g_beranda_produk_list_layout_manager = new LinearLayoutManager(g_context, RecyclerView.VERTICAL, false);

        g_beranda_produk_list_adapter = new BerandaListTawarkanPembeliAdapter(p_list);
        g_beranda_produk_list_rv.setAdapter(g_beranda_produk_list_adapter);
        g_beranda_produk_list_rv.setLayoutManager(g_beranda_produk_list_layout_manager);

        g_beranda_produk_list_popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        g_beranda_produk_list_popup.show();

    }

    public void showBuyerPopUp(Buyer p_pembeli){
        g_beranda_pembeli_popup.setContentView(R.layout.layout_popup_beranda_pembeli);

        //Text View
        g_beranda_pembeli_popup_tv_nama = g_beranda_pembeli_popup.findViewById(R.id.tv_popup_beranda_pembeli_nama_item);
        g_beranda_pembeli_popup_tv_nama.setText(String.valueOf(p_pembeli.getBuyer_name()));
        g_beranda_pembeli_popup_tv_transaksi = g_beranda_pembeli_popup.findViewById(R.id.tv_popup_beranda_pembeli_transaksi_item);
        g_beranda_pembeli_popup_tv_transaksi.setText(String.valueOf(p_pembeli.getSum_trx()+" transaksi"));
        g_beranda_pembeli_popup_tv_no_hp = g_beranda_pembeli_popup.findViewById(R.id.tv_popup_beranda_pembeli_no_hp_item);
        g_beranda_pembeli_popup_tv_no_hp.setText(String.valueOf(p_pembeli.getPhone()));

        //get id buyer
        int tmp_id_buyer = p_pembeli.getId_buyer();

        //Button
        g_beranda_pembeli_popup_btn_close = g_beranda_pembeli_popup.findViewById(R.id.btn_popup_beranda_pembeli_close);
        g_beranda_pembeli_popup_btn_tawarkan = g_beranda_pembeli_popup.findViewById(R.id.btn_popup_beranda_pembeli_tawarkan);
        g_beranda_pembeli_popup_btn_close.setOnClickListener(this);
        g_beranda_pembeli_popup_btn_tawarkan.setOnClickListener(this);

        //Recyclerview
        g_beranda_pembeli_popup_tawarkan_produk_rv = g_beranda_pembeli_popup.findViewById(R.id.rv_popup_beranda_pembeli_tawarkan_produk);
        g_linearlayoutmanager_tawarkan_produk = new LinearLayoutManager(g_context, RecyclerView.HORIZONTAL, false);
        g_beranda_tawarkan_produk_adapter = new BerandaTawarkanProdukAdapter();

        g_beranda_pembeli_popup_tawarkan_produk_rv.setAdapter(g_beranda_tawarkan_produk_adapter);
        g_beranda_pembeli_popup_tawarkan_produk_rv.setLayoutManager(g_linearlayoutmanager_tawarkan_produk);
        VolleyClass.productRecommendation(g_context, String.valueOf(3), String.valueOf(tmp_id_buyer), g_beranda_tawarkan_produk_adapter);

        g_beranda_pembeli_popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        g_beranda_pembeli_popup.show();
    }

    public void setTawarkanProdukAdapter(List<Product> p_product){
        g_beranda_tawarkan_produk_adapter = new BerandaTawarkanProdukAdapter(p_product);
    }


    public void showBuyerListPopUp(Buyer p_pembeli, List<Product> p_list){
        g_beranda_pembeli_list_popup.setContentView(R.layout.layout_dialog_listitem_beranda);

        //Text View
        g_beranda_pembeli_list_judul = g_beranda_pembeli_list_popup.findViewById(R.id.tv_dialog_listitem_beranda_judul);
        g_beranda_pembeli_list_judul.setText("Silahkan pilih produk yang ingin anda tawarkan kepada: \n" + p_pembeli.getBuyer_name());

        //Button
        g_beranda_pembeli_list_btn_close = g_beranda_pembeli_list_popup.findViewById(R.id.btn_dialog_listitem_beranda_tutup);
        g_beranda_pembeli_list_btn_tawarkan = g_beranda_pembeli_list_popup.findViewById(R.id.btn_dialog_listitem_beranda_tawarkan);
        g_beranda_pembeli_list_btn_close.setOnClickListener(this);
        g_beranda_pembeli_list_btn_tawarkan.setOnClickListener(this);

        //Recyclerview
        g_beranda_pembeli_list_rv = g_beranda_pembeli_list_popup.findViewById(R.id.rv_dialog_listitem_beranda);
        g_beranda_pembeli_list_layout_manager = new LinearLayoutManager(g_context, RecyclerView.VERTICAL, false);

        g_beranda_pembeli_list_adapter = new BerandaListTawarkanProdukAdapter(p_list);
        g_beranda_pembeli_list_rv.setAdapter(g_beranda_pembeli_list_adapter);
        g_beranda_pembeli_list_rv.setLayoutManager(g_beranda_pembeli_list_layout_manager);

        g_beranda_pembeli_list_popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        g_beranda_pembeli_list_popup.show();

    }

    public void refreshNamaToko(Seller p_seller){
        g_beranda_tv_nama_toko.setText(p_seller.getShop_name());
    }

    public void refreshJumlahTransaksi(String p_nominal_jumlah_transaksi, String p_jumlah_transaksi){
        Double tmp_nominal_jumlah_transaksi = Double.parseDouble(p_nominal_jumlah_transaksi);
        g_beranda_tv_nominal_jumlah_transaksi.setText(Method.getIndoCurrency(tmp_nominal_jumlah_transaksi));
        g_beranda_tv_jumlah_transaksi.setText("dengan jumlah "+p_jumlah_transaksi+" transaksi");
    }

    public void refreshProduk(Product p_product){
        g_beranda_produk_popup_tv_harga.setText(Method.getIndoCurrency(p_product.getPrice()));
        g_beranda_produk_popup_tv_berat.setText(String.valueOf(p_product.getWeight())+ " gram");
        g_beranda_produk_popup_tv_stok.setText(p_product.getStock());
    }

    public void refreshJumlahTransaksiSudahDIbayar(int tmp_jumlah_transaksi){
        if (tmp_jumlah_transaksi > 0){
            g_beranda_pesanan_belum_dikirim_button.setText("Ada "+tmp_jumlah_transaksi+" pesanan yang belum dikirim");
        } else{
            g_beranda_pesanan_belum_dikirim_button.setVisibility(View.GONE);
        }

    }

}