package com.chk.mymovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chk.mymovie.R;
import com.chk.mymovie.bean.ComingSoonMovie;

import java.util.List;

/**
 * Created by chk on 17-4-5.
 */

public class MyComingSoonMovie extends RecyclerView.Adapter implements View.OnClickListener{

    private Context context;
    private List<ComingSoonMovie> csMovieList;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = null;

    public MyComingSoonMovie(Context context, List<ComingSoonMovie> csMovieList) {
        this.context = context;
        this.csMovieList = csMovieList;
    }

    @Override
    public int getItemCount() {
        return csMovieList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layou_coming_soon_item,parent,false);
        RecyclerView.ViewHolder viewHolder = new ComingSoonMovieHolder(view);
        view.findViewById(R.id.option).setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ComingSoonMovie csMovie = csMovieList.get(position);
        ((ComingSoonMovieHolder) holder).id = csMovie.getId();
    }

    @Override
    public void onClick(View v) {
        if (onRecyclerViewItemClickListener != null) {
            onRecyclerViewItemClickListener.onItemClick(v,(String) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.onRecyclerViewItemClickListener = listener;
    }

    static class ComingSoonMovieHolder extends RecyclerView.ViewHolder {
        int id;
        ImageView csMovieImage;
        TextView csMovieName;
        TextView csMovieYear;
        TextView collect_count;
        TextView csMovieScore;
        public ComingSoonMovieHolder(View itemView) {
            super(itemView);
            csMovieImage = (ImageView) itemView.findViewById(R.id.csMoiveImage);
            csMovieYear = (TextView) itemView.findViewById(R.id.csMovieName);
            csMovieName = (TextView) itemView.findViewById(R.id.csMovieYear);
            collect_count = (TextView) itemView.findViewById(R.id.collect_count);
            csMovieScore = (TextView) itemView.findViewById(R.id.csMovieScore);

        }

    }

    /**
     * 事件点击接口
     */
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }
}
