package com.chk.mymovie.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by chk on 17-3-13.
 */

public class MyViewPagerFragmentAdapter extends FragmentStatePagerAdapter{

    List<Fragment> fragmentList;

    public MyViewPagerFragmentAdapter(FragmentManager fm, List<Fragment> fragmentLists) {
        super(fm);
        this.fragmentList = fragmentLists;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
