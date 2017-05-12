package com.chk.mymovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chk.mymovie.R;
import com.chk.mymovie.application.MyApplication;
import com.chk.mymovie.bean.InTheaterMovie;
import com.chk.mymovie.myinterface.OnLoadMoreListener;
import com.chk.mymovie.myview.MyMovieRecyclerView;

import java.util.List;

/**
 * Created by chk on 17-4-5.
 */

public class MyInTheaterMovieAdapter extends RecyclerView.Adapter{
    public static final String SET_PROGRESS_BAR = "SHOW_PROGRESS_BAR";
    public static final String SET_NO_MORE_TEXT = "SHOW_NO_MORE_TEXT:";
    private final int VIEW_PROG = 0;
    private final int VIEW_ITEM = 1;
    private final int VIEW_TEXT = 2;

    String choosedIp = MyApplication.getContext().getString(R.string.choosedIp);
    private Context context;
    private List<InTheaterMovie> movieList;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = null;
    private OnRecyclerViewButtonClickListener onRecyclerViewButtonClickListener = null;
    private MyMovieRecyclerView recyclerView;

    public MyInTheaterMovieAdapter(List<InTheaterMovie> movieList, MyMovieRecyclerView recyclerView, OnLoadMoreListener onLoadMoreListener) {
        this.movieList = movieList;
        this.recyclerView = recyclerView;
        recyclerView.setOnLoadMoreListener(onLoadMoreListener);
        this.context = recyclerView.getContext();
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.layout_in_theater_item,parent,false);
            viewHolder = new InTheaterMovieHolder(view);
        } else if (viewType == VIEW_PROG) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.footer_progressbar,parent,false);
            viewHolder = new ProgressBarViewHolder(v);
        } else if (viewType == VIEW_TEXT) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.footer_no_more,parent,false);
            viewHolder = new TextViewHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InTheaterMovieHolder) {
            InTheaterMovie itMovie = movieList.get(position);
            ((InTheaterMovieHolder) holder).itMovieLayout.setTag(itMovie.getId()+"");
            ((InTheaterMovieHolder) holder).buyTicket.setTag(itMovie.getId()+"");
            Glide.with(context)
                    .load(choosedIp + "/MyMovieService/GetPicServlet?path="+itMovie.getImage_path())
                    .into(((InTheaterMovieHolder) holder).itMovieImage);
            ((InTheaterMovieHolder) holder).itMovieTitle.setText(itMovie.getTitle());
            ((InTheaterMovieHolder) holder).itMovieYear.setText("年代:"+itMovie.getYear()+"");
            if ((itMovie.getAverage() != 0.0))
                ((InTheaterMovieHolder) holder).itMovieAverage.setText("评分:"+itMovie.getAverage());
            else
                ((InTheaterMovieHolder) holder).itMovieAverage.setText("暂无评分");
        } else if (holder instanceof ProgressBarViewHolder) {
            ((ProgressBarViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        InTheaterMovie itMovie = movieList.get(position);
        switch (itMovie.getTitle()) {
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

    /**
     * item点击接口
     */
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.onRecyclerViewItemClickListener = listener;
    }

    /**
     * button点击接口
     */
    public static interface OnRecyclerViewButtonClickListener {
        void onButtonClick(View view,String data);
    }

    public void setOnButtonClickListener(OnRecyclerViewButtonClickListener listener) {
        this.onRecyclerViewButtonClickListener = listener;
    }

    public void setLoaded() {
        recyclerView.setLoadedOrReload();
    }

    public void setReload() {
        recyclerView.setLoadedOrReload();
    }



    class InTheaterMovieHolder extends RecyclerView.ViewHolder {

        LinearLayout itMovieLayout;
        ImageView itMovieImage;
        TextView itMovieTitle;
        TextView itMovieYear;
        TextView itMovieAverage;
        Button buyTicket;

        public InTheaterMovieHolder(View itemView) {
            super(itemView);
            itMovieLayout = (LinearLayout) itemView.findViewById(R.id.itMoiveLayout);
            itMovieImage = (ImageView) itemView.findViewById(R.id.itMoiveImage);
            itMovieYear = (TextView) itemView.findViewById(R.id.itMovieYear);
            itMovieTitle = (TextView) itemView.findViewById(R.id.itMovieTitle);
            itMovieAverage = (TextView) itemView.findViewById(R.id.itMovieAverage);
            buyTicket = (Button) itemView.findViewById(R.id.buyTicket);

            itMovieLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecyclerViewItemClickListener != null) {
                        onRecyclerViewItemClickListener.onItemClick(view,(String) view.getTag());
                    }
                }
            });

            buyTicket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecyclerViewButtonClickListener != null) {
                        onRecyclerViewButtonClickListener.onButtonClick(view,(String) view.getTag());
                    }
                }
            });
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
