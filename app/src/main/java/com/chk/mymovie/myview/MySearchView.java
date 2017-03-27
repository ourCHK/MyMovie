package com.chk.mymovie.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.chk.mymovie.R;

/**
 * Created by chk on 17-3-27.
 */

public class MySearchView extends LinearLayout {

    EditText et_search;
    Button bt_clear;



    public MySearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.search_view,this,true);
    }



}
