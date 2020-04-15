package com.example.bca_bos.ui.transaksi;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bca_bos.dummy.ListTransaksiDetailDummy;
import com.example.bca_bos.dummy.ListTransaksiDummy;
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
import java.util.List;

public class TransaksiFragment extends Fragment{

    private Context g_context;
    private View g_view;

    //DateTimePicker
    private Calendar g_calendar_awal, g_calendar_akhir;
    private DatePickerDialog.OnDateSetListener g_datepicker_listener_awal, g_datepicker_listener_akhir;
    private EditText txt_tanggal_awal, txt_tanggal_akhir;
    private ImageView btn_filter_tanggal;

    private LinearLayout g_layout_switch;
    private Switch g_switch;

    private FragmentTransaction g_fragment_transaction;
    private FragmentManager g_fragment_manager;

    private OnlineTransaksiFragment g_online_fragment;
    private OfflineTransaksiFragment g_offline_fragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        g_context = container.getContext();
        g_view = inflater.inflate(R.layout.fragment_transaksi, container, false);

        if(getArguments() != null){
            g_online_fragment = new OnlineTransaksiFragment(getArguments().getInt("flag"));
        }else {
            g_online_fragment = new OnlineTransaksiFragment();
        }

        g_switch = g_view.findViewById(R.id.apps_transaksi_fragment_switch);
        g_layout_switch = g_view.findViewById(R.id.apps_transaksi_fragment_switch_layout);

        //inisiasi tanggal
        txt_tanggal_awal = g_view.findViewById(R.id.apps_transaksi_et_tanggal_awal);
        txt_tanggal_akhir = g_view.findViewById(R.id.apps_transaksi_et_tanggal_akhir);
        btn_filter_tanggal = g_view.findViewById(R.id.apps_transaksi_btn_cari_tanggal);

        g_online_fragment.setParent(this);
        g_offline_fragment = new OfflineTransaksiFragment();

        g_fragment_manager = getParentFragmentManager();

        g_fragment_transaction = g_fragment_manager.beginTransaction();
        g_fragment_transaction.add(R.id.apps_transaksi_fragment_container, g_online_fragment);
        g_fragment_transaction.commit();

        g_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    refreshFilterDate();
                    changeSwitchAppearance(R.drawable.switch_custom_thumb_selector_white);
                    g_switch.setSwitchTextAppearance(g_context, R.style.SwitchTextAppearanceBlack);
                    g_fragment_transaction = g_fragment_manager.beginTransaction();
                    g_fragment_transaction.replace(R.id.apps_transaksi_fragment_container, g_offline_fragment);
                    g_fragment_transaction.commit();
                }else {
                    refreshFilterDate();
                    g_switch.setSwitchTextAppearance(g_context, R.style.SwitchTextAppearanceWhite);
                    g_fragment_transaction = g_fragment_manager.beginTransaction();
                    g_fragment_transaction.replace(R.id.apps_transaksi_fragment_container, g_online_fragment);
                    g_fragment_transaction.commit();
                }
            }
        });

        g_layout_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g_switch.setChecked(!g_switch.isChecked());
            }
        });

        txt_tanggal_awal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(g_context, g_datepicker_listener_awal, g_calendar_awal
                        .get(Calendar.YEAR), g_calendar_awal.get(Calendar.MONTH),
                        g_calendar_awal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        g_calendar_awal = Calendar.getInstance();
        g_datepicker_listener_awal = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                g_calendar_awal.set(Calendar.YEAR, year);
                g_calendar_awal.set(Calendar.MONTH, month);
                g_calendar_awal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(txt_tanggal_awal, g_calendar_awal);
            }
        };

        txt_tanggal_akhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(g_context, g_datepicker_listener_akhir, g_calendar_akhir
                        .get(Calendar.YEAR), g_calendar_akhir.get(Calendar.MONTH),
                        g_calendar_akhir.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        g_calendar_akhir = Calendar.getInstance();
        g_datepicker_listener_akhir = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                g_calendar_akhir.set(Calendar.YEAR, year);
                g_calendar_akhir.set(Calendar.MONTH, month);
                g_calendar_akhir.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(txt_tanggal_akhir, g_calendar_akhir);
            }
        };

        btn_filter_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txt_tanggal_awal.getText().toString().isEmpty() && !txt_tanggal_akhir.getText().toString().isEmpty()){
                    if(!g_switch.isChecked()){
                        //Online Transaction
                        g_online_fragment.filterByDate(
                                Method.getDate(txt_tanggal_awal.getText().toString()),
                                Method.getDate(txt_tanggal_akhir.getText().toString()));
                    }
                    else{
                        g_offline_fragment.filterByDate(
                                Method.getDate(txt_tanggal_awal.getText().toString()),
                                Method.getDate(txt_tanggal_akhir.getText().toString()));
                        //Offline Transaction
                    }
                }
            }
        });

        return g_view;
    }

    public void refreshFilterDate(){
        txt_tanggal_awal.setText("");
        txt_tanggal_akhir.setText("");
    }

    private void updateLabel(EditText p_edittext, Calendar p_calendar) {
        p_edittext.setText(Method.formatDatePicker(p_calendar.getTime()));
    }

    public void changeSwitchAppearance(int thumbResId) {
        g_switch.setThumbResource(thumbResId);
    }

}