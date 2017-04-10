package com.chk.mymovie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chk.mymovie.R;
import com.chk.mymovie.application.MyApplication;
import com.chk.mymovie.bean.Movie;

import java.util.List;

/**
 * Created by chk on 17-3-17.
 */

public class MyPicItemAdapter extends ArrayAdapter<Movie>{
    String chooseIp = MyApplication.getContext().getString(R.string.choosedIp);

    int resourceId;

    public MyPicItemAdapter(Context context, int resource, List<Movie> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = getItem(position);
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
        if(movie.getPath() != null && !movie.getPath().equals("")) {
            Glide.with(getContext())
                    .load(chooseIp + "/MyMovieService/GetPicServlet?path="+movie.getPath())
                    .into(viewHolder.picImage);
            Log.e("AG","执行");
            viewHolder.picText.setText(movie.getName());
        }
        return view;
    }

    class ViewHolder {
        ImageView picImage;
        TextView picText;
    }

}
