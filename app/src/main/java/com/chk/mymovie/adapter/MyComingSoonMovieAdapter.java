package com.chk.mymovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chk.mymovie.R;
import com.chk.mymovie.application.MyApplication;
import com.chk.mymovie.bean.ComingSoonMovie;
import com.chk.mymovie.bean.Movie;
import com.chk.mymovie.myinterface.OnLoadMoreListener;
import com.chk.mymovie.myview.MyMovieRecyclerView;

import java.util.List;

/**
 * Created by chk on 17-4-5.
 */

public class MyComingSoonMovieAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    public static final String SET_PROGRESS_BAR = "SHOW_PROGRESS_BAR";
    public static final String SET_NO_MORE_TEXT = "SHOW_NO_MORE_TEXT:";
    private final int VIEW_PROG = 0;
    private final int VIEW_ITEM = 1;
    private final int VIEW_TEXT = 2;

    String choosedIp = MyApplication.getContext().getString(R.string.choosedIp);
    private Context context;
    private List<ComingSoonMovie> csMovieList;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = null;
    private MyMovieRecyclerView recyclerView;

    public MyComingSoonMovieAdapter(List<ComingSoonMovie> csMovieList,MyMovieRecyclerView recyclerView,OnLoadMoreListener onLoadMoreListener) {
        this.csMovieList = csMovieList;
        this.recyclerView = recyclerView;
        recyclerView.setOnLoadMoreListener(onLoadMoreListener);
        this.context = recyclerView.getContext();
    }

    @Override
    public int getItemCount() {
        return csMovieList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.layout_coming_soon_item,parent,false);
            view.findViewById(R.id.csMoiveLayout).setOnClickListener(this);
            viewHolder = new ComingSoonMovieHolder(view);
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
        if (holder instanceof ComingSoonMovieHolder) {
            ComingSoonMovie csMovie = csMovieList.get(position);

            ((ComingSoonMovieHolder) holder).csMovieLayout.setTag(csMovie.getId()+"");
            Glide.with(context)
                    .load(choosedIp + "/MyMovieService/GetPicServlet?path="+csMovie.getImage_path())
                    .into(((ComingSoonMovieHolder) holder).csMovieImage);
            ((ComingSoonMovieHolder) holder).csMovieTitle.setText(csMovie.getTitle());
            ((ComingSoonMovieHolder) holder).csMovieYear.setText(csMovie.getYear()+"");
        } else if (holder instanceof ProgressBarViewHolder) {
            ((ProgressBarViewHolder) holder).progressBar.setIndeterminate(true);
        }


    }

    @Override
    public void onClick(View v) {
        if (onRecyclerViewItemClickListener != null) {
            onRecyclerViewItemClickListener.onItemClick(v,(String) v.getTag());
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        ComingSoonMovie csMovie = csMovieList.get(position);
        switch (csMovie.getTitle()) {
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

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.onRecyclerViewItemClickListener = listener;
    }

    public void setLoaded() {
        recyclerView.setLoadedOrReload();
    }

    public void setReload() {
        recyclerView.setLoadedOrReload();
    }

    /**
     * 事件点击接口
     */
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    static class ComingSoonMovieHolder extends RecyclerView.ViewHolder {

        LinearLayout csMovieLayout;
        ImageView csMovieImage;
        TextView csMovieTitle;
        TextView csMovieYear;
        TextView collect_count;

        public ComingSoonMovieHolder(View itemView) {
            super(itemView);
            csMovieLayout = (LinearLayout) itemView.findViewById(R.id.csMoiveLayout);
            csMovieImage = (ImageView) itemView.findViewById(R.id.csMoiveImage);
            csMovieYear = (TextView) itemView.findViewById(R.id.csMovieYear);
            csMovieTitle = (TextView) itemView.findViewById(R.id.csMovieTitle);
            collect_count = (TextView) itemView.findViewById(R.id.collect_count);

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
