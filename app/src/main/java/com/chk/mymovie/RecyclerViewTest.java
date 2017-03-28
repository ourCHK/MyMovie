package com.chk.mymovie;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chk.mymovie.adapter.MovieAdapter;
import com.chk.mymovie.adapter.MovieAdapterTest;
import com.chk.mymovie.bean.Movie;
import com.chk.mymovie.impl.MovieManager;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewTest extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    MovieManager movieManager;
    LinearLayoutManager layoutManager;
    MovieAdapter movieAdapter;
    MovieAdapterTest movieAdapterTest;

    Handler handler;
    String movieJson;
    List<Movie> movieList;
    int from;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_movie_test);
        init();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MovieManager.GET_COMPLETE:
                        movieJson = (String) msg.obj;
                        movieManager.parseMovieJson(movieList,movieJson, handler);
                        break;
                    case MovieManager.PARSE_COMPLETE:
//                        View view = LayoutInflater.from(RecyclerViewTest.this).inflate(R.layout.footer,null,false);
//                        recyclerView.addView(view);
//                        movieAdapter.notifyDataSetChanged();
                        break;
                    case MovieManager.NETWORK_ERROR:
                        break;
                }
            }
        };

        start();
    }



    public void init() {

        movieManager = new MovieManager();
        movieList = new ArrayList<>();
        from = 0;
        count = 7;

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        movieAdapterTest = new MovieAdapterTest(movieList,recyclerView);
        recyclerView.setAdapter(movieAdapterTest);
        movieAdapterTest.setOnLoadMoreListener(new MovieAdapterTest.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                movieList.add(null);
                movieAdapterTest.notifyItemInserted(movieList.size()-1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        movieList.remove(movieList.size() - 1);
                        movieAdapterTest.notifyItemRemoved(movieList.size());
                        for(int i=0; i<1; i++) {
                            Movie movie = new Movie();
                            movie.setName("new add");
                            movieList.add(movie);
                            movieAdapterTest.notifyItemInserted(movieList.size());
                        }
                        movieAdapterTest.setLoaded();
                    }
                },2000);
                Log.e("TAG","load");
            }
        });
//        movieAdapter = new MovieAdapter(movieList);
//        recyclerView.setAdapter(movieAdapter);

    }

    /**
     * 开始
     */
    public void start() {
        movieManager.getMovieJson(from,count, handler);
    }
}
