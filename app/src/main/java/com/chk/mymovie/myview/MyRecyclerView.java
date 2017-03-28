package com.chk.mymovie.myview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.chk.mymovie.R;

/**
 * Created by chk on 17-3-28.
 */

public class MyRecyclerView extends RecyclerView {

    private View footer;
    private int totalItem;
    private int lastItem;
    private boolean isLoading;
    private OnLoadMore onLoadMore;
    private LayoutInflater inflater;


    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    private void init(Context context) {
        inflater = LayoutInflater.from(context);
        footer = inflater.inflate(R.layout.footer,null,false);
        footer.setVisibility(View.GONE);

    }


    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        switch (state) {
            case SCROLL_STATE_IDLE:
//                Log.e("MyRecyclerView","SCROL_STATE_IDLE");
                break;
            case SCROLL_STATE_DRAGGING:
//                Log.e("MyRecyclerView","SCROL_STATE_DRAGGING");
                break;
            case SCROLL_STATE_SETTLING:
//                Log.e("MyRecyclerView","SCROL_STATE_SETTLING");
                break;
        }
    }


    @Override
    public void onScrolled(int dx, int dy) {
        View lastChildView = this.getLayoutManager().getChildAt(
                this.getLayoutManager().getChildCount()-1);

        Log.e("TAG",this.getLayoutManager().getChildCount()+"");
        int lastChildBottom = lastChildView.getBottom();
        int recyclerBottom = this.getBottom();

        Log.e("TAG",lastChildBottom + "  " + recyclerBottom);

        int lastPosition = this.getLayoutManager().getPosition(lastChildView);
        if (lastPosition == (this.getLayoutManager().getItemCount()-1) && lastChildBottom == recyclerBottom ) {
            Toast.makeText(getContext(), "滑动到底部", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 加载更多的接口
     */
    public interface OnLoadMore {
        public void loadMore();
    }
}
