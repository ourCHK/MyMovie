package com.chk.mymovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chk.mymovie.R;
import com.chk.mymovie.bean.MovieOrder;
import com.chk.mymovie.bean.MovieOrderBuy;

import java.util.List;

/**
 * Created by chk on 17-4-5.
 */

public class MyOrderAdapter extends RecyclerView.Adapter{

    private Context context;
    List<MovieOrderBuy> movieOrderBuyList;
    public MyOrderAdapter(Context context, List<MovieOrderBuy> movieOrderBuyList) {
        this.context = context;
        this.movieOrderBuyList = movieOrderBuyList;
    }

    @Override
    public int getItemCount() {
        return movieOrderBuyList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_order_item,parent,false);
        MyOrderHolder viewHolder = new MyOrderHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MovieOrderBuy movieOrderBuy = movieOrderBuyList.get(position);
        ((MyOrderHolder) holder).movieTitle.setText(movieOrderBuy.getTitle());
        ((MyOrderHolder) holder).movieSeats.setText(movieOrderBuy.getSeats());
        ((MyOrderHolder) holder).buyTime.setText(movieOrderBuy.getCreate_date());
    }


    static class MyOrderHolder extends RecyclerView.ViewHolder{
        TextView movieTitle;
        TextView movieSeats;
        TextView buyTime;
        public MyOrderHolder(View itemView) {
            super(itemView);
            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            movieSeats = (TextView) itemView.findViewById(R.id.movieSeats);
            buyTime  =(TextView) itemView.findViewById(R.id.buyTime);
        }
    }
}
