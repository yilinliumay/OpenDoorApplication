package com.example.dell.opendoorapplication;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity implements SettingFragment.showPro,ControlFragment.takePhoto,MainFragment.getIpString {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    MainFragment mainFragment;
    Bundle bundle;
    Intent intent;
    String ipAddress="original";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        intent = getIntent();

        ipAddress = intent.getStringExtra("ip");

        Log.e("ip",ipAddress);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        ControlFragment controlFragment=new ControlFragment();
        viewPagerAdapter.addFragments(controlFragment,"Control");
        mainFragment=new MainFragment();
        viewPagerAdapter.addFragments(mainFragment,"Main");
        SettingFragment settingFragment=new SettingFragment();
        viewPagerAdapter.addFragments(settingFragment,"Setting");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        Log.e("ip",ipAddress);
    }
    public void showProByName(String name) {
        mainFragment.judge(name);
        startActivity( new Intent(HomeActivity.this, LoginActivity.class));
    }
    public void showPhoto(String s) {
        mainFragment.judgePhoto(s);

    }
    public String getString()
    {
        return ipAddress;
    }

}
