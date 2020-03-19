package com.example.bca_bos.ui.transaksi;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import java.util.List;

public class TransaksiFragment extends Fragment{

    private Context g_context;
    private View g_view;

    private Switch g_switch;

    private FragmentTransaction g_fragment_transaction;
    private FragmentManager g_fragment_manager;

    private OnlineTransaksiFragment g_online_fragment;
    private OfflineTransaksiFragment g_offline_fragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        g_context = container.getContext();
        g_view = inflater.inflate(R.layout.fragment_transaksi, container, false);

        g_switch = g_view.findViewById(R.id.apps_transaksi_fragment_switch);

        g_online_fragment = new OnlineTransaksiFragment();
        g_online_fragment.setParent(this);
        g_offline_fragment = new OfflineTransaksiFragment();

        g_fragment_manager = getParentFragmentManager();

        g_fragment_transaction = g_fragment_manager.beginTransaction();
        g_fragment_transaction.add(R.id.apps_transaksi_fragment_container, g_online_fragment);
        g_fragment_transaction.commit();

        g_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    changeSwitchAppearance(R.drawable.switch_custom_thumb_selector_purple, R.drawable.switch_custom_slide_selector_purple);
                    g_fragment_transaction = g_fragment_manager.beginTransaction();
                    g_fragment_transaction.replace(R.id.apps_transaksi_fragment_container, g_offline_fragment);
                    g_fragment_transaction.commit();
                }else {
                    g_fragment_transaction = g_fragment_manager.beginTransaction();
                    g_fragment_transaction.replace(R.id.apps_transaksi_fragment_container, g_online_fragment);
                    g_fragment_transaction.commit();
                }
            }
        });

        return g_view;
    }

    public void changeSwitchAppearance(int thumbResId, int trackResId){
        g_switch.setThumbResource(thumbResId);
//        g_switch.setTrackResource(trackResId);
    }

}