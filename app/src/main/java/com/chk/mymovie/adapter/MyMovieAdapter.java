package com.chk.mymovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chk.mymovie.MovieDetailActivity;
import com.chk.mymovie.R;
import com.chk.mymovie.bean.Movie;
import com.chk.mymovie.myinterface.OnLoadMoreListener;
import com.chk.mymovie.myview.MyMovieRecyclerView;

import java.util.List;

/**
 * Created by chk on 17-3-29.
 */

public class MyMovieAdapter extends RecyclerView.Adapter{

    public static final String SET_PROGRESS_BAR = "SHOW_PROGRESS_BAR";
    public static final String SET_NO_MORE_TEXT = "SHOW_NO_MORE_TEXT:";
    String genymotionIp = "http://192.168.56.1:8080";
    String nativeIp = "http://10.0.2.2:8080";
    String outerIp = "http://18.8.6.109:8080";
    String chooseIp = nativeIp;

    private final int VIEW_PROG = 0;
    private final int VIEW_ITEM = 1;
    private final int VIEW_TEXT = 2;
    private List<Movie> movieList;
    private MyMovieRecyclerView recyclerView;
    private Context context;



    public MyMovieAdapter(List<Movie> movieList, MyMovieRecyclerView recyclerView, OnLoadMoreListener onLoadMoreListener) {
        this.movieList = movieList;
        this.recyclerView = recyclerView;
        recyclerView.setOnLoadMoreListener(onLoadMoreListener);
        this.context = recyclerView.getContext();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        if(viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_movie_item,parent,false);
            vh = new MovieViewHolder(v);
        } else if (viewType == VIEW_PROG){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.footer_progressbar,parent,false);
            vh = new ProgressBarViewHolder(v);
        } else if (viewType == VIEW_TEXT) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.footer_no_more,parent,false);
            vh = new TextViewHolder(v);
        }
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        Movie movie = movieList.get(position);
        switch (movie.getName()) {
            case SET_PROGRESS_BAR:
                type = VIEW_PROG;
                break;
            case SET_NO_MORE_TEXT:
                type = VIEW_TEXT;
                break;
            default:
                type = VIEW_ITEM;
                break;
        }
        return type;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MovieViewHolder) {
            final Movie movie = movieList.get(position);

            View view = ((MovieViewHolder) holder).itemView;
            view.setOnClickListener(new View.OnClickListener() {   //设置整个view的点击事件
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MovieDetailActivity.class);
                    intent.putExtra("movie",movie);
//                    intent.putExtra("title","movie:"+movie.getName()+"  id:"+movie.getId());
                    context.startActivity(intent);
                }
            });
            ((MovieViewHolder) holder).buyTicket.setOnClickListener(new View.OnClickListener() {    //设置view内按钮的点击事件
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "you click the button", Toast.LENGTH_SHORT).show();
                }
            });
            ((MovieViewHolder) holder).movieName.setText(movie.getName());
            Glide.with(context).load(chooseIp + "/MyMovieService/GetPicServlet?path="+movie.getPath()).into(((MovieViewHolder) holder).movieImage);
        } else if (holder instanceof ProgressBarViewHolder){
            ((ProgressBarViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setLoaded() {
        recyclerView.setLoadedOrReload();
    }

    public void setReload() {
        recyclerView.setLoadedOrReload();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        TextView movieName;
        TextView movieScore;
        Button buyTicket;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieImage = (ImageView) itemView.findViewById(R.id.movieImage);
            movieName = (TextView) itemView.findViewById(R.id.movieName);
            movieScore = (TextView) itemView.findViewById(R.id.movieScore);
            buyTicket = (Button) itemView.findViewById(R.id.buyTicket);
        }
    }

    static class ProgressBarViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        public ProgressBarViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.load_more_progressBar);
        }
    }

    static class TextViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public TextViewHolder (View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.noMoreContent);
        }
    }
}
