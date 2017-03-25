package com.chk.mymovie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.chk.mymovie.adapter.MyViewPagerFragmentAdapter;
import com.chk.mymovie.fragment.ContentFragment;
import com.chk.mymovie.fragment.PersonalCenterFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "MainActivity";

    ViewPager viewPager;
    FragmentManager fm;
    List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        fm = getSupportFragmentManager();
        fragmentList = new ArrayList<>();
        fragmentList.add(new ContentFragment());
        fragmentList.add(new ContentFragment());
        fragmentList.add(new PersonalCenterFragment());
        viewPager.setAdapter(new MyViewPagerFragmentAdapter(fm,fragmentList));

    }
}