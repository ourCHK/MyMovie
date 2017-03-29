package com.chk.mymovie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chk.mymovie.R;
import com.chk.mymovie.adapter.MyMovieAdapter;
import com.chk.mymovie.bean.Movie;
import com.chk.mymovie.impl.MovieManager;
import com.chk.mymovie.myinterface.OnLoadMoreListener;
import com.chk.mymovie.myview.MyMovieRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chk on 17-3-13.
 */

public class MovieFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    MyMovieRecyclerView myMovieRecyclerView;
    LinearLayoutManager layoutManager;
    MyMovieAdapter myMovieAdapter;
    MovieManager movieManager;



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
        return inflater.inflate(R.layout.layout_fragment_movie,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MovieManager.GET_COMPLETE: //Json获取完成
                        movieJson = (String) msg.obj;
                        movieManager.parseMovieJson(movieList,movieJson, handler);
                        break;
                    case MovieManager.PARSE_COMPLETE:   //Json解析完成
                        if (swipeRefreshLayout.isRefreshing()) {    //如果是刷新的情况下，移动都第一个
                            swipeRefreshLayout.setRefreshing(false);
                            layoutManager.scrollToPosition(0);
                        }
                        myMovieAdapter.notifyDataSetChanged();
                        myMovieAdapter.setLoaded();
                        from += count;
                        break;
                    case MovieManager.GET_MORE: //获取更多数据
                        movieManager.getMovieJson(from,count,handler);
                        break;
                    case MovieManager.NO_MORE:  //没有跟多数据的时候
                        myMovieAdapter.notifyDataSetChanged();
                        break;
                    case MovieManager.NETWORK_ERROR:    //网络错误
                        Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
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

        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                    }
                },5000);
            }
        });

        myMovieRecyclerView = (MyMovieRecyclerView) getActivity().findViewById(R.id.myMovieRecycleView);
        layoutManager = new LinearLayoutManager(getActivity());
        myMovieRecyclerView.setLayoutManager(layoutManager);
        myMovieAdapter = new MyMovieAdapter(movieList, myMovieRecyclerView, new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Movie movie = new Movie();
                movie.setName(MyMovieAdapter.SET_PROGRESS_BAR);
                movieList.add(movie);    //只要不是Movie类型就会显示progressbar
                myMovieAdapter.notifyItemInserted(movieList.size()-1);    //此方法可以在子线程中调用，和notifyDataSetChanged不同
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(MovieManager.GET_MORE);
                    }
                },2000);
            }
        });
        myMovieRecyclerView.setAdapter(myMovieAdapter);
    }

    /**
     * 开始获取数据
     */
    public void startGet() {
        movieManager.getMovieJson(from,count,handler);
    }

    public void refresh() {
        from = 0;
        count = 5;
        movieList.clear();
        myMovieAdapter.setReload();
        startGet();

    }

}
