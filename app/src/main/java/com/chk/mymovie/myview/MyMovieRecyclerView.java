package com.chk.mymovie.myview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.chk.mymovie.myinterface.OnLoadMoreListener;

/**
 * Created by chk on 17-3-28.
 */

public class MyMovieRecyclerView extends RecyclerView {

    private View lastChildView;
    private LinearLayoutManager layoutManager;
    private Context context;
    private int lastChildBottom;
    private int lastPosition;
    private int recyclerBottom;
    private boolean isLoading;


    private OnLoadMoreListener onLoadMoreListener;


    public MyMovieRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyMovieRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public void init(Context context) {
        this.context = context;
        layoutManager = (LinearLayoutManager) this.getLayoutManager();
    }

    @Override
    public void onScrolled(int dx, int dy) {
        layoutManager = (LinearLayoutManager) this.getLayoutManager();
        if(this.getChildCount() != 0) {
            lastChildView = this.getChildAt(this.getChildCount() - 1);
            lastChildBottom = lastChildView.getBottom() + this.getTop();
            lastPosition = layoutManager.getPosition(lastChildView);
            recyclerBottom = this.getBottom()+this.getPaddingTop();
            if(!isLoading &&
                    lastPosition == (layoutManager.getItemCount() - 1) &&
                    lastChildBottom == recyclerBottom) {
                if (onLoadMoreListener != null) {
                    onLoadMoreListener.onLoadMore();
                }
                isLoading = true;
            }
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoadedOrReload() {
        isLoading = false;
    }

}
