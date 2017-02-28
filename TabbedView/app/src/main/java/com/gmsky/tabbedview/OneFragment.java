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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OneFragment extends FragmentActivity implements View.OnClickListener {

    Button b_kisi, b_grup, b_etkinlik;//, b_join;
    //EditText e_person_name, e_person_surname, e_address, e_phone, e_birthday;
    Intent intent = null;
    ViewPager viewPager;
    Toolbar toolbar;
    TabLayout tabLayout;
    //String j_mail, j_user_name, j_password, user_id, password, person_name, person_surname, address, phone, birthday;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_one);

        initializer();

        b_kisi.setOnClickListener(this);
        b_grup.setOnClickListener(this);
        b_etkinlik.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_one);

        viewPager = (ViewPager) findViewById(R.id.viewpager_one);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs_one);
        tabLayout.setupWithViewPager(viewPager);

        /*user_id = getIntent().getStringExtra("user_id");
        password = getIntent().getStringExtra("password");
        j_mail = getIntent().getStringExtra("j_mail");
        j_user_name = getIntent().getStringExtra("j_u_name");
        j_password = getIntent().getStringExtra("j_pass0");*/

        /*b_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //j_mail = et_mail.getText().toString().trim();

            }
        });*/

        /*Log.e("Login GELENLER__", user_id + " " + password);
        Log.e("Joın GELENLER__", j_mail + " " + j_user_name + " " + j_password);*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.b_persons):
                intent = new Intent(OneFragment.this, OneFragment.class);
                startActivity(intent);
                break;
            case (R.id.b_groups):
                intent = new Intent(OneFragment.this, TwoFragment.class);
                startActivity(intent);
                break;
            case (R.id.b_activities):
                intent = new Intent(OneFragment.this, ThreeFragment.class);
                startActivity(intent);
                break;
            /*case (R.id.btn_join):
                person_name = e_person_name.getText().toString().trim();
                person_surname = e_person_surname.getText().toString().trim();
                address = e_address.getText().toString().trim();
                phone = e_phone.getText().toString().trim();
                birthday = e_birthday.getText().toString().trim();
                if (person_name.equals("")) {
                    Toast.makeText(getApplicationContext(), "Lütfen Ad alanını doldurunuz!", Toast.LENGTH_LONG).show();
                } else if (person_surname.equals("")) {
                    Toast.makeText(getApplicationContext(), "Lütfen Soyad alanını doldurunuz!", Toast.LENGTH_LONG).show();
                } else if (address.equals("")) {
                    Toast.makeText(getApplicationContext(), "Lütfen  Adres alanını doldurunuz!", Toast.LENGTH_LONG).show();
                }
                *//*else if (phone.equals("")) {
                    Toast.makeText(getApplicationContext(), "Lütfen alanları doldurunuz!", Toast.LENGTH_LONG).show();
                } else if (birthday.equals("")) {
                    Toast.makeText(getApplicationContext(), "Lütfen alanları doldurunuz!", Toast.LENGTH_LONG).show();
                }*//*
                break;*/
        }
    }

    protected void initializer() {
        b_kisi = (Button) findViewById(R.id.b_persons);
        b_grup = (Button) findViewById(R.id.b_groups);
        b_etkinlik = (Button) findViewById(R.id.b_activities);
        /*b_join = (Button) findViewById(R.id.btn_join);

        e_person_name = (EditText) findViewById(R.id.et_person_name);
        e_person_surname = (EditText) findViewById(R.id.et_person_surname);
        e_address = (EditText) findViewById(R.id.et_address);
        e_phone = (EditText) findViewById(R.id.et_phone);
        e_birthday = (EditText) findViewById(R.id.et_birthday);*/
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FragmentPersonForm(), "Yeni");
        adapter.addFrag(new FragmentPersonList(), "Üye Listesi");
        viewPager.setAdapter(adapter);
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
