package com.example.bca_bos.ui.transaksi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;

import com.example.bca_bos.R;

import java.util.ArrayList;
import java.util.List;

public class TransaksiFilterDetail extends AppCompatActivity {

    private ViewPager mPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_filter_detail);

        List<Fragment> list = new ArrayList<>();
        list.add(new TransaksiFilterDetailFragment());
        list.add(new TransaksiFilterDetailFragment());
        list.add(new TransaksiFilterDetailFragment());
        list.add(new TransaksiFilterDetailFragment());

        mPager = findViewById(R.id.apps_transaksi_filterdetail_fragment_container);
        pagerAdapter = new TransaksiFilterDetailPagerAdapter(getSupportFragmentManager(), list);
        mPager.setAdapter(pagerAdapter);

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
