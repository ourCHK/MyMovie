package com.chk.mymovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chk.mymovie.R;

/**
 * Created by chk on 17-4-5.
 */

public class MyOptionAdapter extends RecyclerView.Adapter implements View.OnClickListener{

    private Context context;
    private String[] optionString;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = null;

    public MyOptionAdapter(Context context, String[] optionString) {
        this.context = context;
        this.optionString = optionString;
    }

    @Override
    public int getItemCount() {
        return optionString.length;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.option_search,parent,false);
        RecyclerView.ViewHolder viewHolder = new TextViewHolder(view);
        view.findViewById(R.id.option).setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String option = optionString[position];
        ((TextViewHolder) holder).textView.setTag(option);
        ((TextViewHolder) holder).textView.setText(option);


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

    static class TextViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public TextViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.option);
        }
    }

    /**
     * 事件点击接口
     */
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view,String data);
    }
}
