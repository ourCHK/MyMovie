package com.chk.mymovie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chk.mymovie.R;
import com.chk.mymovie.adapter.MovieAdapter;
import com.chk.mymovie.bean.Movie;
import com.chk.mymovie.impl.MovieManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chk on 17-3-13.
 */

public class MovieFragment extends Fragment {

    RecyclerView recyclerView;
    MovieManager movieManager;
    LinearLayoutManager layoutManager;
    MovieAdapter movieAdapter;

    Handler handler;
    String movieJson;
    List<Movie> movieList;
    int from;
    int count;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_movie,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MovieManager.GET_COMPLETE:
                        movieJson = (String) msg.obj;
                        movieManager.parseMovieJson(movieList,movieJson, handler);
                        break;
                    case MovieManager.PARSE_COMPLETE:
                        movieAdapter.notifyDataSetChanged();
                        break;
                    case MovieManager.NETWORK_ERROR:
                        break;
                }
            }
        };
        init();
        startGet();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    public void init() {
        movieManager = new MovieManager();
        movieList = new ArrayList<>();
        from = 0;
        count = 5;

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        movieAdapter = new MovieAdapter(movieList);
        recyclerView.setAdapter(movieAdapter);
    }

    /**
     * 开始获取数据
     */
    public void startGet() {
        movieManager.getMovieJson(from,count,handler);
    }

}
