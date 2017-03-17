package com.chk.mymovie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chk.mymovie.R;
import com.chk.mymovie.bean.Pic;

import java.util.List;

/**
 * Created by chk on 17-3-17.
 */

public class MyPicItemAdapter extends ArrayAdapter<Pic>{

    int resourceId;

    public MyPicItemAdapter(Context context, int resource, List<Pic> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Pic pic = getItem(position);
        View view;
        ViewHolder viewHolder;

        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.picImage = (ImageView) view.findViewById(R.id.picImage);
            viewHolder.picText = (TextView) view.findViewById(R.id.picText);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if(pic.getPicAddress() != null && !pic.getPicAddress().equals("")) {
            Glide.with(getContext()).load("http://10.0.2.2:8080/MyMovieService/PicServlet?path="+pic.getPicAddress())
                    .into(viewHolder.picImage);
//            viewHolder.picImage.setImageResource(pic.getPicImage());
            viewHolder.picText.setText(pic.getPicName());
        }

        return view;
    }

    class ViewHolder {
        ImageView picImage;
        TextView picText;
    }

}
