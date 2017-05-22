package com.chk.mymovie.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by chk on 17-5-10.
 */

public class MyMovieTypeAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> fragmentList;

    public MyMovieTypeAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyMovieTypeAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList)  {
        super(fm);
        this.fragmentList = fragmentList;

    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "正在上映";
            case 1:
                return "即将上映";
        }
        return null;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {

        super.setPrimaryItem(container, position, object);
    }
}
