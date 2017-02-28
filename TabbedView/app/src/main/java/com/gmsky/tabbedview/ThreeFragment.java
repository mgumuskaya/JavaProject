package com.gmsky.tabbedview;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gmsky on 21/12/2016.
 */

public class ThreeFragment extends FragmentActivity implements View.OnClickListener {

    Button b_kisi, b_grup, b_etkinlik;
    Intent intent = null;
    ViewPager viewPager;
    Toolbar toolbar;
    TabLayout tabLayout;

    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_three);

        b_kisi = (Button) findViewById(R.id.b_persons);
        b_grup = (Button) findViewById(R.id.b_groups);
        b_etkinlik = (Button) findViewById(R.id.b_activities);

        b_kisi.setOnClickListener(this);
        b_grup.setOnClickListener(this);
        b_etkinlik.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_three);

        viewPager = (ViewPager) findViewById(R.id.viewpager_three);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs_three);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FragmentActivityForm(), "Yeni");
        adapter.addFrag(new FragmentActivityList(), "Etkinlik Listesi");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.b_persons):
                intent = new Intent(ThreeFragment.this, OneFragment.class);
                startActivity(intent);
                break;
            case (R.id.b_groups):
                intent = new Intent(ThreeFragment.this, TwoFragment.class);
                startActivity(intent);
                break;
            case (R.id.b_activities):
                intent = new Intent(ThreeFragment.this, ThreeFragment.class);
                startActivity(intent);
                break;
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
