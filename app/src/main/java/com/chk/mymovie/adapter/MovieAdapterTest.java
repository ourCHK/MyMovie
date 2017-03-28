package com.chk.mymovie.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chk.mymovie.R;
import com.chk.mymovie.bean.Movie;

import java.util.List;

/**
 * Created by chk on 17-3-28.
 */

public class MovieAdapterTest extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String genymotionIp = "http://192.168.56.1:8080";
    String nativeIp = "http://10.0.2.2:8080";
    String chooseIp = genymotionIp;

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private List<Movie> mDataset;
    private int visibleThreshold = 2;
    private int lastVisibleItem;
    private int totalItemCount;
    private boolean isLoading;
    private OnLoadMoreListener onLoadMoreListener;
    private Context context;


    public MovieAdapterTest(List<Movie> myDataset,RecyclerView recyclerView) {
        mDataset = myDataset;
        if(recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)
                    recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if(!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if(onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        isLoading =true;
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        RecyclerView.ViewHolder vh;
        if(viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_item,parent,false);
            vh = new MovieViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.footer,parent,false);
            vh = new ProgressBarViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MovieViewHolder) {
            Movie movie = mDataset.get(position);
            ((MovieViewHolder) holder).movieName.setText(movie.getName());
            Glide.with(context).load(chooseIp + "/MyMovieService/GetPicServlet?path="+movie.getPath()).into(((MovieViewHolder) holder).movieImage);
        } else {
            ((ProgressBarViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        isLoading = false;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        TextView movieName;
        TextView movieScore;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieImage = (ImageView) itemView.findViewById(R.id.movieImage);
            movieName = (TextView) itemView.findViewById(R.id.movieName);
            movieScore = (TextView) itemView.findViewById(R.id.movieScore);
        }
    }

    static class ProgressBarViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        public ProgressBarViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.load_more_progressBar);
        }
    }
}
