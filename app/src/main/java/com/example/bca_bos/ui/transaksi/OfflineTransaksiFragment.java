package com.example.bca_bos.ui.transaksi;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class OfflineTransaksiFragment extends Fragment implements View.OnClickListener, OnCallBackListener {
    private Context g_context;
    private View g_view;
    public static OfflineTransaksiFragment g_instance;
    private ConstraintLayout g_transaksi_fragment_not_found;

    private ImageView g_transaksi_iv;

    private RecyclerView g_transaksi_fragment_recyclerview;
    private LinearLayoutManager g_linearlayoutmanager;
    private OfflineTransaksiAdapter g_transaksiadapteroffline;

    private PieChart g_transaksi_fragment_piechart;
    private float g_percentage;

    private TextView g_tv_not_found_judul;
    private LottieAnimationView g_iv_not_found_animation;

    private BottomSheetDialog g_bottomsheet_dialog;

    //Shared Preference
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String BOS_ID = "BOS_ID";
    private static final String SELLER_ID = "SELLER_ID";
    SharedPreferences g_preference;
    int g_seller_id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Get Seller ID
        g_preference = this.getActivity().getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        g_seller_id = g_preference.getInt(SELLER_ID, -1);

        g_context = container.getContext();

        g_view = inflater.inflate(R.layout.fragment_offline_transaction, container, false);

        g_transaksi_fragment_not_found = g_view.findViewById(R.id.apps_offline_transaksi_fragment_not_found);
        g_tv_not_found_judul  = g_view.findViewById(R.id.apps_tv_not_found_judul);
        g_iv_not_found_animation = g_view.findViewById(R.id.apps_iv_not_found_animation);

        g_instance = this;

        g_linearlayoutmanager = new LinearLayoutManager(g_context);
        g_transaksiadapteroffline = new OfflineTransaksiAdapter(this);
        g_transaksiadapteroffline.setParentOnCallBack(this);

        g_transaksi_fragment_recyclerview = g_view.findViewById(R.id.apps_transaksi_fragment_recyclerview_off);
        g_transaksi_fragment_recyclerview.setLayoutManager(g_linearlayoutmanager);

        g_transaksi_fragment_piechart = g_view.findViewById(R.id.apps_transaksi_filterdetail_piechart_off);
        g_percentage = g_transaksiadapteroffline.getItemCount();

        firstLoad();

        VolleyClass.getTransaksiOffline(g_context, g_seller_id, g_transaksiadapteroffline);

        return g_view;
    }

    public void firstLoad(){
        g_percentage = 100;
        drawPieChart(new int[]{R.color.black, R.color.black},
                g_transaksiadapteroffline.getItemMasterCount() + "\nTransaksi");
        g_transaksiadapteroffline.setListTransaksi(g_transaksiadapteroffline.getList());
        g_transaksi_fragment_recyclerview.setAdapter(g_transaksiadapteroffline);
    }

    @Override
    public void onClick(View v) {


    }

    private void changeLayoutValue(int p_count){
        if(p_count > 0){
            g_transaksi_fragment_recyclerview.setVisibility(View.VISIBLE);
            g_transaksi_fragment_not_found.setVisibility(View.GONE);
        }
        else{
            g_tv_not_found_judul.setText(getText(R.string.TRANSACTION_OFFLINE_NOT_FOUND));
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
        g_transaksi_fragment_piechart.setCenterTextColor(getResources().getColor(R.color.black));



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
            Transaction tmpTransaction = (Transaction) p_obj;
            VolleyClass.getTransaksiDetailOffline(g_context, tmpTransaction.getId_transaction(), tmpTransaction.getStatus());
        }
    }

    public void showBottomSheetTransaksiOffline(Transaction p_transaction){
        g_bottomsheet_dialog = new BottomSheetDialog(g_context, R.style.BottomSheetDialogTheme);
        View l_bottomsheet_view_add = LayoutInflater.from(g_context).inflate(
                R.layout.layout_bottom_sheet_transaksi_offline,
                (LinearLayout)g_view.findViewById(R.id.layout_apps_bottom_sheet_container_transaksi_offline)
        );

        RecyclerView l_rv_bottom_sheet_transaksi_offline = l_bottomsheet_view_add.findViewById(R.id.rv_apps_bottom_sheet_transaksi_offline);
        TextView l_tv_bottom_sheet_transaksi_offline_tanggal = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_offline_tanggal);
        TextView l_tv_bottom_sheet_transaksi_offline_total = l_bottomsheet_view_add.findViewById(R.id.tv_apps_bottom_sheet_transaksi_offline_total);

        TransaksiDetailAdapter l_tdAdapter = new TransaksiDetailAdapter();
        LinearLayoutManager l_layoutmanager = new LinearLayoutManager(g_context);

        l_rv_bottom_sheet_transaksi_offline.setAdapter(l_tdAdapter);
        l_rv_bottom_sheet_transaksi_offline.setLayoutManager(l_layoutmanager);

        l_tdAdapter.setListTransaksiDetail(p_transaction.getTransaction_detail());

        l_tv_bottom_sheet_transaksi_offline_tanggal.setText(Method.formatDate(p_transaction.getOrder_time()));
        l_tv_bottom_sheet_transaksi_offline_total.setText(Method.getIndoCurrency(Double.parseDouble(p_transaction.getTotal_payment())));

        g_bottomsheet_dialog.setContentView(l_bottomsheet_view_add);
        g_bottomsheet_dialog.show();
    }

}
