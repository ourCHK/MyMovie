package com.chk.mymovie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chk.mymovie.R;

/**
 * Created by chk on 17-3-13.
 * 个人中心的fragment
 */

public class PersonalCenterFragment extends Fragment {

    int resourceId; //界面id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_personal_center,container,false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


}
