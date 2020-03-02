package com.example.bca_bos.ui.transaksi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.bca_bos.dummy.ListTransaksiDummy;
import com.example.bca_bos.Method;
import com.example.bca_bos.R;
import com.example.bca_bos.interfaces.OnCallBackListener;
import com.example.bca_bos.models.Transaksi;

import java.util.ArrayList;
import java.util.List;

public class TransaksiFilterDetail extends AppCompatActivity implements OnCallBackListener {

    private ViewPager g_vp_transaksi_filterdetail;
    private PagerAdapter g_pageradapter_transaksi_filterdetail;
    private List<Fragment> g_list_fragment_transaksi;

    private List<Transaksi> g_list_transaksi;

    public static final int KEY_STATUS_BARUMASUK = 0;
    public static final int KEY_STATUS_SUDAHDIBAYAR = 1;
    public static final int KEY_STATUS_SUDAHDIKIRIM = 2;
    public static final int KEY_STATUS_SELESAI = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_filter_detail);
        Method.callback(this);

        g_list_transaksi = ListTransaksiDummy.transaksiList;

        g_list_fragment_transaksi = new ArrayList<>();
        g_list_fragment_transaksi.add(new TransaksiFilterDetailFragment(getListTransaksiByType(KEY_STATUS_BARUMASUK), KEY_STATUS_BARUMASUK, getPersentaseTransaksiBaruMasuk(),this));
        g_list_fragment_transaksi.add(new TransaksiFilterDetailFragment(getListTransaksiByType(KEY_STATUS_SUDAHDIBAYAR), KEY_STATUS_SUDAHDIBAYAR, getPersentaseTransaksiSudahDiBayar(),this));
        g_list_fragment_transaksi.add(new TransaksiFilterDetailFragment(getListTransaksiByType(KEY_STATUS_SUDAHDIKIRIM), KEY_STATUS_SUDAHDIKIRIM, getPersentaseTransaksiSudahDikirim(),this));
        g_list_fragment_transaksi.add(new TransaksiFilterDetailFragment(getListTransaksiByType(KEY_STATUS_SELESAI), KEY_STATUS_SELESAI, getPersentaseTransaksiSudahSelesai(),this));

        g_vp_transaksi_filterdetail = findViewById(R.id.apps_transaksi_filterdetail_fragment_container);
        g_pageradapter_transaksi_filterdetail = new TransaksiFilterDetailPagerAdapter(getSupportFragmentManager(), g_list_fragment_transaksi);
        g_vp_transaksi_filterdetail.setAdapter(g_pageradapter_transaksi_filterdetail);

        g_vp_transaksi_filterdetail.setCurrentItem(getIntent().getExtras().getInt("TRANSAKSI_PAGE"));
    }

    public List<Transaksi> getListTransaksiByType(int p_type){
        List<Transaksi> tmpListTransaksi = new ArrayList<>();
        for(int i = 0; i < g_list_transaksi.size(); i++){
            if(g_list_transaksi.get(i).getStatus() == p_type)
                tmpListTransaksi.add(g_list_transaksi.get(i));
        }
        return tmpListTransaksi;
    }

    public float getPersentaseTransaksiSudahSelesai(){
        float tmpHasil = 0f;
        if(g_list_transaksi.size() > 0){
            tmpHasil = (countTransaksibyStatus(KEY_STATUS_SELESAI) / g_list_transaksi.size()) * 100;
        }
        return tmpHasil;
    }

    public float getPersentaseTransaksiSudahDikirim(){
        float tmpHasil = 0f;
        if(g_list_transaksi.size() > 0){
            tmpHasil = (countTransaksibyStatus(KEY_STATUS_SUDAHDIKIRIM) / g_list_transaksi.size()) * 100;
        }
        return tmpHasil;
    }

    public float getPersentaseTransaksiSudahDiBayar(){
        float tmpHasil = 0f;
        if(g_list_transaksi.size() > 0){
            tmpHasil = (countTransaksibyStatus(KEY_STATUS_SUDAHDIBAYAR) / g_list_transaksi.size()) * 100;
        }
        return tmpHasil;
    }

    public float getPersentaseTransaksiBaruMasuk(){
        float tmpHasil = 0f;
        if(g_list_transaksi.size() > 0){
            tmpHasil = (countTransaksibyStatus(KEY_STATUS_BARUMASUK) / g_list_transaksi.size()) * 100;
        }
        return tmpHasil;
    }

    public float countTransaksibyStatus(int p_type){
        float count = 0;

        for(int i = 0; i < g_list_transaksi.size(); i++){
            if(g_list_transaksi.get(i).getStatus() == p_type)
                count++;
        }

        return count;
    }

    @Override
    public void OnCallBack(Object p_obj) {
        String tmpCommand = (String) p_obj;
        switch (tmpCommand){
            case "NEXT":
                if(g_vp_transaksi_filterdetail.getCurrentItem() + 1 == g_list_fragment_transaksi.size()){
                    g_vp_transaksi_filterdetail.setCurrentItem(0, true);
                }else {
                    g_vp_transaksi_filterdetail.setCurrentItem(g_vp_transaksi_filterdetail.getCurrentItem() + 1, true);
                }
                break;
            case "BACK":
                if(g_vp_transaksi_filterdetail.getCurrentItem() == 0){
                    g_vp_transaksi_filterdetail.setCurrentItem(g_list_fragment_transaksi.size() - 1, true);
                }else {
                    g_vp_transaksi_filterdetail.setCurrentItem(g_vp_transaksi_filterdetail.getCurrentItem() - 1, true);
                }
                break;
        }

    }

    private class TransaksiFilterDetailPagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> l_list_fragment;

        public TransaksiFilterDetailPagerAdapter(FragmentManager fm, List<Fragment> p_list) {
            super(fm);
            l_list_fragment = p_list;
        }

        @Override
        public Fragment getItem(int position) {
            return l_list_fragment.get(position);
        }

        @Override
        public int getCount() {
            return l_list_fragment.size();
        }
    }


}
