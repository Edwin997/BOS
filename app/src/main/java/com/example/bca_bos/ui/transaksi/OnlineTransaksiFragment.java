package com.example.bca_bos.ui.transaksi;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.transactions.Transaction;
import com.example.bca_bos.networks.VolleyClass;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class OnlineTransaksiFragment extends Fragment implements View.OnClickListener, OnCallBackListener {

    private Context g_context;
    private View g_view;
    public static OnlineTransaksiFragment g_instance;
    private TransaksiFragment g_parent;
    private SwipeRefreshLayout g_transaksi_fragment_refresh;
    private ConstraintLayout g_transaksi_fragment_not_found;

    private TextView g_tv_not_found_judul;
    private LottieAnimationView g_iv_not_found_animation;

    //Tombol tab status transaksi
    private Button g_btn_tab_semua, g_btn_tab_pesanan_baru, g_btn_tab_pesanan_dibayar,
            g_btn_tab_pesanan_dikirim, g_btn_tab_selesai, g_btn_tab_batal;
    private View g_view_tab_semua, g_view_tab_pesanan_baru, g_view_tab_pesanan_dibayar,
            g_view_tab_pesanan_dikirim, g_view_tab_selesai, g_view_tab_batal;

    private ImageView g_transaksi_iv;

    private RecyclerView g_transaksi_fragment_recyclerview;
    private LinearLayoutManager g_linearlayoutmanager;
    private OnlineTransaksiAdapter g_transaksiadapter;

    private PieChart g_transaksi_fragment_piechart;
    private float g_percentage;

    private BottomSheetDialog g_bottomsheet_dialog;
//
//    private List<Transaction> g_list_transaction;

    private Transaction g_transaction_onclick;

    private int FLAG_FRAGMENT_TYPE;
    public final int KEY_STATUS_BARUMASUK = 0;
    public final int KEY_STATUS_SUDAHDIBAYAR = 1;
    public final int KEY_STATUS_SUDAHDIKIRIM = 2;
    public final int KEY_STATUS_SELESAI = 3;
    public final int KEY_STATUS_SEMUA = 4;
    public final int KEY_STATUS_BATAL = 5;


    //Shared Preference
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String BOS_ID = "BOS_ID";
    private static final String SELLER_ID = "SELLER_ID";
    private static final String NAMA_TOKO = "NAMA_TOKO";
    SharedPreferences g_preference;
    int g_seller_id;
    String g_nama_toko;

    public OnlineTransaksiFragment(){
    }

    public OnlineTransaksiFragment(int p_type){
        FLAG_FRAGMENT_TYPE = p_type;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Get Seller ID
        g_preference = this.getActivity().getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        g_seller_id = g_preference.getInt(SELLER_ID, -1);
        g_nama_toko = g_preference.getString(NAMA_TOKO, "");
        Log.d("BOSVOLLEY", g_seller_id + "");

        //Inisialisasi
        g_context = container.getContext();
        g_view = inflater.inflate(R.layout.fragment_online_transaction, container, false);
        g_instance = this;

        g_transaksi_fragment_refresh = g_view.findViewById(R.id.apps_online_transaksi_fragment_refresh);

        g_transaksi_fragment_not_found = g_view.findViewById(R.id.apps_online_transaksi_fragment_not_found);
        g_tv_not_found_judul  = g_view.findViewById(R.id.apps_tv_not_found_judul);
        g_iv_not_found_animation = g_view.findViewById(R.id.apps_iv_not_found_animation);


        g_linearlayoutmanager = new LinearLayoutManager(g_context);
        g_transaksiadapter = new OnlineTransaksiAdapter(this);
        g_transaksiadapter.setParentOnCallBack(this);

        g_transaksi_fragment_recyclerview = g_view.findViewById(R.id.apps_transaksi_fragment_recyclerview);
        g_transaksi_fragment_recyclerview.setLayoutManager(g_linearlayoutmanager);
        g_transaksi_fragment_recyclerview.setAdapter(g_transaksiadapter);

        g_transaksi_fragment_piechart = g_view.findViewById(R.id.apps_transaksi_filterdetail_piechart);
        g_percentage = g_transaksiadapter.getItemCount();

        //Inisialisasi Tab
        g_btn_tab_pesanan_baru = g_view.findViewById(R.id.apps_transaksi_fragment_tab_btn_pesanan_baru);
        g_btn_tab_pesanan_dibayar = g_view.findViewById(R.id.apps_transaksi_fragment_tab_btn_pesanan_dibayar);
        g_btn_tab_pesanan_dikirim = g_view.findViewById(R.id.apps_transaksi_fragment_tab_btn_pesanan_dikirim);
        g_btn_tab_selesai = g_view.findViewById(R.id.apps_transaksi_fragment_tab_btn_transaksi_selesai);
        g_btn_tab_semua = g_view.findViewById(R.id.apps_transaksi_fragment_tab_btn_semua_transaksi);
        g_btn_tab_batal = g_view.findViewById(R.id.apps_transaksi_fragment_tab_btn_transaksi_batal);

        g_transaksi_iv = g_view.findViewById(R.id.apps_transaksi_image_view);

        g_view_tab_pesanan_baru = g_view.findViewById(R.id.apps_transaksi_underline_pesanan_baru);
        g_view_tab_pesanan_dibayar = g_view.findViewById(R.id.apps_transaksi_underline_pesanan_dibayar);
        g_view_tab_pesanan_dikirim = g_view.findViewById(R.id.apps_transaksi_underline_pesanan_dikirim);
        g_view_tab_selesai = g_view.findViewById(R.id.apps_transaksi_underline_transaksi_selesai);
        g_view_tab_semua = g_view.findViewById(R.id.apps_transaksi_underline_semua_transaksi);
        g_view_tab_batal = g_view.findViewById(R.id.apps_transaksi_underline_transaksi_batal);


        g_btn_tab_pesanan_baru.setOnClickListener(this);
        g_btn_tab_pesanan_dikirim.setOnClickListener(this);
        g_btn_tab_pesanan_dibayar.setOnClickListener(this);
        g_btn_tab_selesai.setOnClickListener(this);
        g_btn_tab_semua.setOnClickListener(this);
        g_btn_tab_batal.setOnClickListener(this);

        if(FLAG_FRAGMENT_TYPE == KEY_STATUS_SUDAHDIBAYAR) {
            VolleyClass.getTransaksi(g_context, g_seller_id, KEY_STATUS_SUDAHDIBAYAR, g_transaksiadapter);
        }
        else {
            VolleyClass.getTransaksi(g_context, g_seller_id, KEY_STATUS_SEMUA, g_transaksiadapter);
        }

        g_transaksi_fragment_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                VolleyClass.getTransaksi(g_context, g_seller_id, FLAG_FRAGMENT_TYPE, g_transaksiadapter);
                g_transaksi_fragment_refresh.setRefreshing(false);
            }
        });

        return g_view;
    }

    public void filterByDate(Date p_awal, Date p_akhir){
        g_transaksiadapter.setListTransaksiFiltered(
                g_transaksiadapter.getListTransaksiByDate(FLAG_FRAGMENT_TYPE, p_awal, p_akhir));
    }

    public void refreshLayout(int p_type){
        FLAG_FRAGMENT_TYPE = p_type;

        g_parent.refreshFilterDate();

        if(FLAG_FRAGMENT_TYPE == KEY_STATUS_SEMUA){
            g_percentage = 100;
            drawPieChart(new int[]{R.color.white, R.color.white},
                    g_transaksiadapter.getItemMasterCount() + "\nTransaksi");
            g_transaksiadapter.setListTransaksiFiltered(g_transaksiadapter.getListTransaksiByType(FLAG_FRAGMENT_TYPE));
            setTabBar();
        }
        else if(FLAG_FRAGMENT_TYPE == KEY_STATUS_BARUMASUK){
            g_percentage = g_transaksiadapter.getPersentaseTransaksiBaruMasuk();
            drawPieChart(new int[]{R.color.purple, R.color.white},
                    g_transaksiadapter.countTransaksibyStatus(FLAG_FRAGMENT_TYPE) + "\nTransaksi");
            g_transaksiadapter.setListTransaksiFiltered(g_transaksiadapter.getListTransaksiByType(FLAG_FRAGMENT_TYPE));
            setTabBar();
        }
        else if(FLAG_FRAGMENT_TYPE == KEY_STATUS_SUDAHDIBAYAR){
            g_percentage = g_transaksiadapter.getPersentaseTransaksiSudahDiBayar();
            drawPieChart(new int[]{R.color.yellow, R.color.white},
                    g_transaksiadapter.countTransaksibyStatus(FLAG_FRAGMENT_TYPE) + "\nTransaksi");
            g_transaksiadapter.setListTransaksiFiltered(g_transaksiadapter.getListTransaksiByType(FLAG_FRAGMENT_TYPE));
            setTabBar();
        }
        else if(FLAG_FRAGMENT_TYPE == KEY_STATUS_SUDAHDIKIRIM){
            g_percentage = g_transaksiadapter.getPersentaseTransaksiSudahDikirim();
            drawPieChart(new int[]{R.color.blue, R.color.white},
                    g_transaksiadapter.countTransaksibyStatus(FLAG_FRAGMENT_TYPE) + "\nTransaksi");
            g_transaksiadapter.setListTransaksiFiltered(g_transaksiadapter.getListTransaksiByType(FLAG_FRAGMENT_TYPE));
            setTabBar();
        }
        else if(FLAG_FRAGMENT_TYPE == KEY_STATUS_SELESAI){
            g_percentage = g_transaksiadapter.getPersentaseTransaksiSudahSelesai();
            drawPieChart(new int[]{R.color.green, R.color.white},
                    g_transaksiadapter.countTransaksibyStatus(FLAG_FRAGMENT_TYPE) + "\nTransaksi");
            g_transaksiadapter.setListTransaksiFiltered(g_transaksiadapter.getListTransaksiByType(FLAG_FRAGMENT_TYPE));
            setTabBar();
        }
        else if(FLAG_FRAGMENT_TYPE == KEY_STATUS_BATAL){
            g_percentage = g_transaksiadapter.getPersentaseTransaksiBatal();
            drawPieChart(new int[]{R.color.red, R.color.white},
                    g_transaksiadapter.countTransaksibyStatus(FLAG_FRAGMENT_TYPE) + "\nTransaksi");
            g_transaksiadapter.setListTransaksiFiltered(g_transaksiadapter.getListTransaksiByType(FLAG_FRAGMENT_TYPE));
            setTabBar();
        }
    }

    public void setParent(TransaksiFragment transaksiFragment){
        g_parent = transaksiFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.apps_transaksi_fragment_tab_btn_semua_transaksi:
                VolleyClass.getTransaksi(g_context, g_seller_id, KEY_STATUS_SEMUA, g_transaksiadapter);
                break;
            case R.id.apps_transaksi_fragment_tab_btn_pesanan_baru:
                VolleyClass.getTransaksi(g_context, g_seller_id, KEY_STATUS_BARUMASUK, g_transaksiadapter);
                break;
            case R.id.apps_transaksi_fragment_tab_btn_pesanan_dibayar:
                VolleyClass.getTransaksi(g_context, g_seller_id, KEY_STATUS_SUDAHDIBAYAR, g_transaksiadapter);
                break;
            case R.id.apps_transaksi_fragment_tab_btn_pesanan_dikirim:
                VolleyClass.getTransaksi(g_context, g_seller_id, KEY_STATUS_SUDAHDIKIRIM, g_transaksiadapter);
                break;
            case R.id.apps_transaksi_fragment_tab_btn_transaksi_selesai:
                VolleyClass.getTransaksi(g_context, g_seller_id, KEY_STATUS_SELESAI, g_transaksiadapter);
                break;
            case R.id.apps_transaksi_fragment_tab_btn_transaksi_batal:
                VolleyClass.getTransaksi(g_context, g_seller_id, KEY_STATUS_BATAL, g_transaksiadapter);
                break;
        }
    }

    private void setTabBar(){
        refreshButton();
        if(FLAG_FRAGMENT_TYPE == KEY_STATUS_SEMUA){
            g_transaksi_iv.setBackgroundResource(R.drawable.style_chart_gradient_black);
            g_btn_tab_semua.setTextColor(g_context.getResources().getColor(R.color.black));
            g_parent.changeSwitchAppearance(R.drawable.switch_custom_thumb_selector_white);
            g_view_tab_semua.setVisibility(View.VISIBLE);
        }
        else if(FLAG_FRAGMENT_TYPE == KEY_STATUS_BARUMASUK){
            g_transaksi_iv.setBackgroundResource(R.drawable.style_chart_gradient_purple);
            g_btn_tab_pesanan_baru.setTextColor(g_context.getResources().getColor(R.color.purple));
            g_parent.changeSwitchAppearance(R.drawable.switch_custom_thumb_selector_purple);
            g_view_tab_pesanan_baru.setVisibility(View.VISIBLE);
        }
        else if(FLAG_FRAGMENT_TYPE == KEY_STATUS_SUDAHDIBAYAR){
            g_transaksi_iv.setBackgroundResource(R.drawable.style_chart_gradient_orange_layered);
            g_btn_tab_pesanan_dibayar.setTextColor(g_context.getResources().getColor(R.color.yellow));
            g_parent.changeSwitchAppearance(R.drawable.switch_custom_thumb_selector_orange);
            g_view_tab_pesanan_dibayar.setVisibility(View.VISIBLE);
        }
        else if(FLAG_FRAGMENT_TYPE == KEY_STATUS_SUDAHDIKIRIM){
            g_transaksi_iv.setBackgroundResource(R.drawable.style_chart_gradient_blue);
            g_btn_tab_pesanan_dikirim.setTextColor(g_context.getResources().getColor(R.color.blue));
            g_parent.changeSwitchAppearance(R.drawable.switch_custom_thumb_selector_blue);
            g_view_tab_pesanan_dikirim.setVisibility(View.VISIBLE);
        }
        else if(FLAG_FRAGMENT_TYPE == KEY_STATUS_SELESAI){
            g_transaksi_iv.setBackgroundResource(R.drawable.style_chart_gradient_green);
            g_btn_tab_selesai.setTextColor(g_context.getResources().getColor(R.color.green));
            g_parent.changeSwitchAppearance(R.drawable.switch_custom_thumb_selector_green);
            g_view_tab_selesai.setVisibility(View.VISIBLE);
        }
        else if(FLAG_FRAGMENT_TYPE == KEY_STATUS_BATAL){
            g_transaksi_iv.setBackgroundResource(R.drawable.style_chart_gradient_red);
            g_btn_tab_batal.setTextColor(g_context.getResources().getColor(R.color.red));
            g_parent.changeSwitchAppearance(R.drawable.switch_custom_thumb_selector_red);
            g_view_tab_batal.setVisibility(View.VISIBLE);
        }

    }

    private void refreshButton(){
        g_btn_tab_pesanan_baru.setTextColor(g_context.getResources().getColor(R.color.grey));
        g_view_tab_pesanan_baru.setVisibility(View.GONE);
        g_btn_tab_pesanan_dibayar.setTextColor(g_context.getResources().getColor(R.color.grey));
        g_view_tab_pesanan_dibayar.setVisibility(View.GONE);
        g_btn_tab_pesanan_dikirim.setTextColor(g_context.getResources().getColor(R.color.grey));
        g_view_tab_pesanan_dikirim.setVisibility(View.GONE);
        g_btn_tab_selesai.setTextColor(g_context.getResources().getColor(R.color.grey));
        g_view_tab_selesai.setVisibility(View.GONE);
        g_btn_tab_semua.setTextColor(g_context.getResources().getColor(R.color.grey));
        g_view_tab_semua.setVisibility(View.GONE);
        g_btn_tab_batal.setTextColor(g_context.getResources().getColor(R.color.grey));
        g_view_tab_batal.setVisibility(View.GONE);
    }

    public void drawPieChart(int[] p_colortemplate, String p_centertext){
        List<PieEntry> tmpListDataEntry = new ArrayList<>();
        tmpListDataEntry.add(new PieEntry(g_percentage, ""));
        tmpListDataEntry.add(new PieEntry((100f-g_percentage), ""));

        PieDataSet l_transaksi_piechart_dataset = new PieDataSet(tmpListDataEntry,"");
        PieData l_transaksi_piechart_data = new PieData(l_transaksi_piechart_dataset);
        l_transaksi_piechart_data.setDrawValues(false);

        Description l_description = new Description();
        l_description.setText("");

        g_transaksi_fragment_piechart.setDrawCenterText(true);

        String[] tmpStringSplit = p_centertext.split("\n");
        int tmpLength = tmpStringSplit[0].length();

        SpannableString tmpCustomSizeText=  new SpannableString(p_centertext);
        tmpCustomSizeText.setSpan(new RelativeSizeSpan(2f), 0, tmpLength, 0);
//        tmpCustomSizeText.setSpan(new ForegroundColorSpan(getResources().getColor(p_colortemplate[0])), 0, tmpLength, 0);
        tmpCustomSizeText.setSpan(new StyleSpan(Typeface.BOLD), 0, tmpLength, 0);

        l_transaksi_piechart_dataset.setColors(ColorTemplate.createColors(getResources(), p_colortemplate));
        g_transaksi_fragment_piechart.setCenterText(tmpCustomSizeText);
        g_transaksi_fragment_piechart.setCenterTextSize(18);
        g_transaksi_fragment_piechart.setCenterTextColor(getResources().getColor(R.color.white));



        g_transaksi_fragment_piechart.setData(l_transaksi_piechart_data);
        g_transaksi_fragment_piechart.setDrawEntryLabels(false);
        g_transaksi_fragment_piechart.setDrawHoleEnabled(true);
        g_transaksi_fragment_piechart.setHoleColor(Color.TRANSPARENT);

        g_transaksi_fragment_piechart.setDrawMarkers(false);
        g_transaksi_fragment_piechart.setDescription(l_description);
        g_transaksi_fragment_piechart.setHoleRadius(80f);
        g_transaksi_fragment_piechart.setTransparentCircleRadius(0f);
        g_transaksi_fragment_piechart.animateXY(1000,1000);
    }

    @Override
    public void OnCallBack(Object p_obj) {
        if(p_obj instanceof Transaction){
            g_transaction_onclick = (Transaction) p_obj;
            VolleyClass.getTransaksiDetail(g_context, g_transaction_onclick.getId_transaction(), g_transaction_onclick.getStatus());
        }
    }

    private void changeLayoutValue(int p_count){
        if(p_count > 0){
            g_transaksi_fragment_recyclerview.setVisibility(View.VISIBLE);
            g_transaksi_fragment_not_found.setVisibility(View.GONE);
        }
        else{
            if(FLAG_FRAGMENT_TYPE == KEY_STATUS_SEMUA){
                g_tv_not_found_judul.setText(getText(R.string.TRANSACTION_ONLINE_NOT_FOUND));
            }
            else if(FLAG_FRAGMENT_TYPE == KEY_STATUS_BARUMASUK){
                g_tv_not_found_judul.setText(getText(R.string.TRANSACTION_BARU_MASUK_NOT_FOUND));
            }
            else if(FLAG_FRAGMENT_TYPE == KEY_STATUS_SUDAHDIBAYAR){
                g_tv_not_found_judul.setText(getText(R.string.TRANSACTION_SUDAH_DIBAYAR_NOT_FOUND));
            }
            else if(FLAG_FRAGMENT_TYPE == KEY_STATUS_SUDAHDIKIRIM){
                g_tv_not_found_judul.setText(getText(R.string.TRANSACTION_SUDAH_DIKIRIM_NOT_FOUND));
            }
            else if(FLAG_FRAGMENT_TYPE == KEY_STATUS_SELESAI){
                g_tv_not_found_judul.setText(getText(R.string.TRANSACTION_SUDAH_SELESAI_NOT_FOUND));
            }
            else if(FLAG_FRAGMENT_TYPE == KEY_STATUS_BATAL){
                g_tv_not_found_judul.setText(getText(R.string.TRANSACTION_BATAL_NOT_FOUND));
            }
            g_iv_not_found_animation.setAnimation(R.raw.no_transaction_animation);
            g_iv_not_found_animation.playAnimation();
            g_iv_not_found_animation.loop(true);

            g_transaksi_fragment_recyclerview.setVisibility(View.GONE);
            g_transaksi_fragment_not_found.setVisibility(View.VISIBLE);
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

            g_transaksi_fragment_recyclerview.setVisibility(View.GONE);
            g_transaksi_fragment_not_found.setVisibility(View.VISIBLE);
        }
    }

    public void showBottomSheetPesananBaru(final Transaction p_transaction){
        g_bottomsheet_dialog = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);
        View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_transaksi_pesananbaru,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_transaksi_pesananbaru)
        );

        RecyclerView l_rv_bottom_sheet_transaksi_pesanbaru = l_bottomsheet_view_add.findViewById(R.id.rv_apps_bottom_sheet_transaksi_pesananbaru);
        TextView l_tv_bottom_sheet_transaksi_pesanbaru_nama = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananbaru_nama);
        TextView l_tv_bottom_sheet_transaksi_pesanbaru_tanggal = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananbaru_tanggal);
        TextView l_tv_bottom_sheet_transaksi_pesanbaru_total = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananbaru_total);
        TextView l_tv_bottom_sheet_transaksi_pesanbaru_phone = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananbaru_phone);
        TextView l_tv_bottom_sheet_transaksi_pesanbaru_alamat = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananbaru_alamat);

        Button l_btn_bottom_sheet_transaksi_pesanbaru_ingatkan = l_bottomsheet_view_add.findViewById(R.id.btn_apps_bottom_sheet_transaksi_pesananbaru_ingatkan);
        Button l_btn_bottom_sheet_transaksi_pesanbaru_batalkan = l_bottomsheet_view_add.findViewById(R.id.btn_apps_bottom_sheet_transaksi_pesananbaru_batalkan);

        TransaksiDetailAdapter l_tdAdapter = new TransaksiDetailAdapter();
        LinearLayoutManager l_layoutmanager = new LinearLayoutManager(g_context);

        l_rv_bottom_sheet_transaksi_pesanbaru.setAdapter(l_tdAdapter);
        l_rv_bottom_sheet_transaksi_pesanbaru.setLayoutManager(l_layoutmanager);

        l_tdAdapter.setListTransaksiDetail(p_transaction.getTransaction_detail());

        l_tv_bottom_sheet_transaksi_pesanbaru_nama.setText(p_transaction.getBuyer().getBuyer_name());
        l_tv_bottom_sheet_transaksi_pesanbaru_tanggal.setText(Method.formatDate(p_transaction.getOrder_time()));
        l_tv_bottom_sheet_transaksi_pesanbaru_total.setText(Method.getIndoCurrency(Double.parseDouble(p_transaction.getTotal_payment())));
        l_tv_bottom_sheet_transaksi_pesanbaru_phone.setText(p_transaction.getBuyer().getPhone());
        l_tv_bottom_sheet_transaksi_pesanbaru_alamat.setText(p_transaction.getAddress());

        l_btn_bottom_sheet_transaksi_pesanbaru_ingatkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendSMS = new Intent(Intent.ACTION_SENDTO);
                sendSMS.setType("text/plain");
                sendSMS.setData(Uri.parse("smsto:" + p_transaction.getBuyer().getPhone()));
                sendSMS.putExtra("sms_body", "Hallo, kami dari Toko " + g_nama_toko + " ingin mengingatkan kembali mengenai transaksi anda " +
                        "pada Tanggal " + Method.formatDate(p_transaction.getOrder_time()) + "\nSilahkan segera membayarkan pada Virtual Account:\n" +
                        p_transaction.getVa_number() +
                        "\nTerima kasih atas perhatiannya.");

                if (sendSMS.resolveActivity(g_context.getPackageManager()) != null) {
                    startActivity(sendSMS);
                }
            }
        });

        l_btn_bottom_sheet_transaksi_pesanbaru_batalkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleyClass.updatePembatalanTransaksi(g_context, p_transaction, g_transaksiadapter);
            }
        });

        g_bottomsheet_dialog.setContentView(l_bottomsheet_view_add);
        g_bottomsheet_dialog.show();
    }

    public void showBottomSheetPesananDikirim(final Transaction p_transaction){
        g_bottomsheet_dialog = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);
        View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_transaksi_pesanandikirim,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_transaksi_pesanandikirim)
        );

        RecyclerView l_rv_bottom_sheet_transaksi_pesandikirim = l_bottomsheet_view_add.findViewById(R.id.rv_apps_bottom_sheet_transaksi_pesanandikirim);
        TextView l_tv_bottom_sheet_transaksi_pesandikirim_nama = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandikirim_nama);
        TextView l_tv_bottom_sheet_transaksi_pesandikirim_tanggal = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandikirim_tanggal);
        TextView l_tv_bottom_sheet_transaksi_pesandikirim_total = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandikirim_total);
        TextView l_tv_bottom_sheet_transaksi_pesandikirim_phone = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandikirim_phone);
        TextView l_tv_bottom_sheet_transaksi_pesandikirim_alamat = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandikirim_alamat);
        Button l_btn_bottom_sheet_transaksi_pesandikirim_kirim_konfirmasi = l_bottomsheet_view_add.findViewById(R.id.btn_apps_bottom_sheet_transaksi_pesanandikirim_kirim_konfirmasi);


        TransaksiDetailAdapter l_tdAdapter = new TransaksiDetailAdapter();
        LinearLayoutManager l_layoutmanager = new LinearLayoutManager(g_context);

        l_rv_bottom_sheet_transaksi_pesandikirim.setAdapter(l_tdAdapter);
        l_rv_bottom_sheet_transaksi_pesandikirim.setLayoutManager(l_layoutmanager);

        l_tdAdapter.setListTransaksiDetail(p_transaction.getTransaction_detail());

        l_tv_bottom_sheet_transaksi_pesandikirim_nama.setText(p_transaction.getBuyer().getBuyer_name());
        l_tv_bottom_sheet_transaksi_pesandikirim_tanggal.setText(Method.formatDate(p_transaction.getOrder_time()));
        l_tv_bottom_sheet_transaksi_pesandikirim_total.setText(Method.getIndoCurrency(Double.parseDouble(p_transaction.getTotal_payment())));
        l_tv_bottom_sheet_transaksi_pesandikirim_phone.setText(p_transaction.getBuyer().getPhone());
        l_tv_bottom_sheet_transaksi_pesandikirim_alamat.setText(p_transaction.getAddress());

        l_btn_bottom_sheet_transaksi_pesandikirim_kirim_konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendSMS = new Intent(Intent.ACTION_SENDTO);
                sendSMS.setType("text/plain");
                sendSMS.setData(Uri.parse("smsto:" + p_transaction.getBuyer().getPhone()));
                sendSMS.putExtra("sms_body", "Hallo, kami dari Toko " + g_nama_toko + " ingin melakukan konfirmasi mengenai transaksi anda" +
                        "pada Tanggal " + Method.formatDate(p_transaction.getOrder_time()) + "\nDengan cara melakukan klik pada link berikut ini:\n" +
                        VolleyClass.BASE_WEB_URL + "/order/confirm/" + p_transaction.getId_transaction() +
                        "\nTerima Kasih.");

                if (sendSMS.resolveActivity(g_context.getPackageManager()) != null) {
                    startActivity(sendSMS);
                }
            }
        });

        g_bottomsheet_dialog.setContentView(l_bottomsheet_view_add);
        g_bottomsheet_dialog.show();
    }

    public void showBottomSheetPesananSelesai(Transaction p_transaction){
        g_bottomsheet_dialog = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);
        View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_transaksi_pesananselesai,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_transaksi_pesananselesai)
        );

        RecyclerView l_rv_bottom_sheet_transaksi_pesanselesai = l_bottomsheet_view_add.findViewById(R.id.rv_apps_bottom_sheet_transaksi_pesananselesai);
        TextView l_tv_bottom_sheet_transaksi_pesanselesai_nama = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananselesai_nama);
        TextView l_tv_bottom_sheet_transaksi_pesanselesai_tanggal = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananselesai_tanggal);
        TextView l_tv_bottom_sheet_transaksi_pesanselesai_total = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananselesai_total);
        TextView l_tv_bottom_sheet_transaksi_pesananselesai_phone = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananselesai_phone);
        TextView l_tv_bottom_sheet_transaksi_pesananselesai_alamat = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananselesai_alamat);

        TransaksiDetailAdapter l_tdAdapter = new TransaksiDetailAdapter();
        LinearLayoutManager l_layoutmanager = new LinearLayoutManager(g_context);

        l_rv_bottom_sheet_transaksi_pesanselesai.setAdapter(l_tdAdapter);
        l_rv_bottom_sheet_transaksi_pesanselesai.setLayoutManager(l_layoutmanager);

        l_tdAdapter.setListTransaksiDetail(p_transaction.getTransaction_detail());

        l_tv_bottom_sheet_transaksi_pesanselesai_nama.setText(p_transaction.getBuyer().getBuyer_name());
        l_tv_bottom_sheet_transaksi_pesanselesai_tanggal.setText(Method.formatDate(p_transaction.getOrder_time()));
        l_tv_bottom_sheet_transaksi_pesanselesai_total.setText(Method.getIndoCurrency(Double.parseDouble(p_transaction.getTotal_payment())));
        l_tv_bottom_sheet_transaksi_pesananselesai_phone.setText(p_transaction.getBuyer().getPhone());
        l_tv_bottom_sheet_transaksi_pesananselesai_alamat.setText(p_transaction.getAddress());

        g_bottomsheet_dialog.setContentView(l_bottomsheet_view_add);
        g_bottomsheet_dialog.show();
    }

    public void showBottomSheetPesananBatal(Transaction p_transaction){
        g_bottomsheet_dialog = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);
        View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_transaksi_pesananbatal,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_transaksi_pesananbatal)
        );

        RecyclerView l_rv_bottom_sheet_transaksi_pesananbatal = l_bottomsheet_view_add.findViewById(R.id.rv_apps_bottom_sheet_transaksi_pesananbatal);
        TextView l_tv_bottom_sheet_transaksi_pesananbatal_nama = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananbatal_nama);
        TextView l_tv_bottom_sheet_transaksi_pesananbatal_tanggal = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananbatal_tanggal);
        TextView l_tv_bottom_sheet_transaksi_pesananbatal_total = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananbatal_total);
        TextView l_tv_bottom_sheet_transaksi_pesananbatal_phone = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananbatal_phone);
        TextView l_tv_bottom_sheet_transaksi_pesananbatal_alamat = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesananbatal_alamat);

        TransaksiDetailAdapter l_tdAdapter = new TransaksiDetailAdapter();
        LinearLayoutManager l_layoutmanager = new LinearLayoutManager(g_context);

        l_rv_bottom_sheet_transaksi_pesananbatal.setAdapter(l_tdAdapter);
        l_rv_bottom_sheet_transaksi_pesananbatal.setLayoutManager(l_layoutmanager);

        l_tdAdapter.setListTransaksiDetail(p_transaction.getTransaction_detail());

        l_tv_bottom_sheet_transaksi_pesananbatal_nama.setText(p_transaction.getBuyer().getBuyer_name());
        l_tv_bottom_sheet_transaksi_pesananbatal_tanggal.setText(Method.formatDate(p_transaction.getOrder_time()));
        l_tv_bottom_sheet_transaksi_pesananbatal_total.setText(Method.getIndoCurrency(Double.parseDouble(p_transaction.getTotal_payment())));
        l_tv_bottom_sheet_transaksi_pesananbatal_phone.setText(p_transaction.getBuyer().getPhone());
        l_tv_bottom_sheet_transaksi_pesananbatal_alamat.setText(p_transaction.getAddress());

        g_bottomsheet_dialog.setContentView(l_bottomsheet_view_add);
        g_bottomsheet_dialog.show();
    }

    public void showBottomSheetPesananDibayar(final Transaction p_transaction){
        g_bottomsheet_dialog = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);
        View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_transaksi_pesanandibayar,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_transaksi_pesanandibayar)
        );

        RecyclerView l_rv_bottom_sheet_transaksi_pesandibayar = l_bottomsheet_view_add.findViewById(R.id.rv_apps_bottom_sheet_transaksi_pesanandibayar);

        TextView l_tv_bottom_sheet_transaksi_dibayar_nama = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandibayar_nama);
        TextView l_tv_bottom_sheet_transaksi_pesandibayar_tanggal = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandibayar_tanggal);
        TextView l_tv_bottom_sheet_transaksi_pesandibayar_total = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandibayar_total);
        TextView l_tv_bottom_sheet_transaksi_pesandibayar_phone = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandibayar_phone);
        TextView l_tv_bottom_sheet_transaksi_pesandibayar_alamat = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandibayar_alamat);
        TextView l_tv_bottom_sheet_transaksi_pesandibayar_resi = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_pesanandibayar_resi);

        final EditText l_et_bottom_sheet_transaksi_pesandibayar_resi = l_bottomsheet_view_add.findViewById(R.id.et_apps_bottom_sheet_transaksi_pesanandibayar_resi);
        Button l_btn_bottom_sheet_transaksi_pesandibayar_kirimresi = l_bottomsheet_view_add.findViewById(R.id.btn_apps_bottom_sheet_transaksi_pesanandibayar_sendresi);

        TransaksiDetailAdapter l_tdAdapter = new TransaksiDetailAdapter();
        LinearLayoutManager l_layoutmanager = new LinearLayoutManager(g_context);

        l_rv_bottom_sheet_transaksi_pesandibayar.setAdapter(l_tdAdapter);
        l_rv_bottom_sheet_transaksi_pesandibayar.setLayoutManager(l_layoutmanager);

        l_tdAdapter.setListTransaksiDetail(p_transaction.getTransaction_detail());

        l_tv_bottom_sheet_transaksi_dibayar_nama.setText(p_transaction.getBuyer().getBuyer_name());
        l_tv_bottom_sheet_transaksi_pesandibayar_tanggal.setText(Method.formatDate(p_transaction.getOrder_time()));
        l_tv_bottom_sheet_transaksi_pesandibayar_total.setText(Method.getIndoCurrency(Double.parseDouble(p_transaction.getTotal_payment())));
        l_tv_bottom_sheet_transaksi_pesandibayar_phone.setText(p_transaction.getBuyer().getPhone());
        l_tv_bottom_sheet_transaksi_pesandibayar_alamat.setText(p_transaction.getAddress());
        l_tv_bottom_sheet_transaksi_pesandibayar_resi.setText("No. Resi (" + p_transaction.getShipping_agent() + ") :");

        l_btn_bottom_sheet_transaksi_pesandibayar_kirimresi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!l_et_bottom_sheet_transaksi_pesandibayar_resi.getText().equals("")){
                    g_transaction_onclick.setShipping_code(l_et_bottom_sheet_transaksi_pesandibayar_resi.getText().toString());
                    VolleyClass.insertShippedCode(g_context, g_transaction_onclick, g_transaksiadapter);

                    Intent sendSMS = new Intent(Intent.ACTION_SENDTO);
                    sendSMS.setType("text/plain");
                    sendSMS.setData(Uri.parse("smsto:" + p_transaction.getBuyer().getPhone()));
                    sendSMS.putExtra("sms_body", "Hallo, kami dari Toko " + g_nama_toko + " ingin menginfokan mengenai no. resi dari transaksi anda " +
                            "pada Tanggal " + Method.formatDate(p_transaction.getOrder_time()) + "\nBerikut adalah no. resi anda:\n" +
                            g_transaction_onclick.getShipping_code() +
                            "\nTerima kasih atas perhatiannya.");

                    if (sendSMS.resolveActivity(g_context.getPackageManager()) != null) {
                        startActivity(sendSMS);
                    }

                    g_bottomsheet_dialog.dismiss();


                }
            }
        });

        g_bottomsheet_dialog.setContentView(l_bottomsheet_view_add);
        g_bottomsheet_dialog.show();
    }
}
