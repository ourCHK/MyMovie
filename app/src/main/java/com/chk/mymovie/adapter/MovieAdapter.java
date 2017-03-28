package com.chk.mymovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chk.mymovie.R;
import com.chk.mymovie.bean.Movie;

import java.util.List;

/**
 * Created by chk on 17-3-28.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    String genymotionIp = "http://192.168.56.1:8080";
    String nativeIp = "http://10.0.2.2:8080";
    String chooseIp = nativeIp;

    Context context;

    List<Movie> movieList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView movieImage;
        TextView movieName;
        TextView movieScore;


        public ViewHolder(View itemView) {
            super(itemView);
            movieImage = (ImageView) itemView.findViewById(R.id.movieImage);
            movieName = (TextView) itemView.findViewById(R.id.movieName);
            movieScore = (TextView) itemView.findViewById(R.id.movieScore);
        }

    }

    public MovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.movieName.setText(movie.getName());
        Glide.with(context).load(chooseIp + "/MyMovieService/GetPicServlet?path="+movie.getPath()).into(holder.movieImage);

    }



}
