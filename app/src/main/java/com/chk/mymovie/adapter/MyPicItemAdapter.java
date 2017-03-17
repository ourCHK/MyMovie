package com.chk.mymovie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chk.mymovie.R;
import com.chk.mymovie.bean.Pic;

import java.util.List;

/**
 * Created by chk on 17-3-17.
 */

public class MyPicItemAdapter extends ArrayAdapter<Pic>{

    int resourceId;
    List<Pic> picList;

    public MyPicItemAdapter(Context context, int resource, List<Pic> objects) {
        super(context, resource, objects);
        resourceId = resource;
        picList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Pic pic = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView picImage = (ImageView) view.findViewById(R.id.picImage);
        TextView picText = (TextView) view.findViewById(R.id.picText);
        picImage.setImageResource(pic.getPicImage());
        picText.setText(pic.getPicText());
        return view;
    }

}
