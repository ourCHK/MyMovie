package com.chk.mymovie;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

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
                    case MovieManager.GET_COMPLETE: //Json获取完成
                        movieJson = (String) msg.obj;
                        movieManager.parseMovieJson(movieList,movieJson, handler);
                        break;
                    case MovieManager.PARSE_COMPLETE:   //Json解析完成
//                        View view = LayoutInflater.from(RecyclerViewTest.this).inflate(R.layout.footer,null,false);
//                        recyclerView.addView(view);
                        movieAdapterTest.notifyDataSetChanged();
                        break;
                    case MovieManager.GET_MORE: //获取更多数据
                        this.postDelayed(null,2000);
                        movieAdapterTest.notifyItemInserted(movieList.size()-1);    //通知显示progressbar
                        movieManager.getMovieJson(from+count,count,handler);
                        break;
                    case MovieManager.NETWORK_ERROR:    //网络错误
                        Toast.makeText(RecyclerViewTest.this, "网络错误", Toast.LENGTH_SHORT).show();
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
        count = 5;

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        movieAdapterTest = new MovieAdapterTest(movieList,recyclerView);
        recyclerView.setAdapter(movieAdapterTest);

        movieAdapterTest.setOnLoadMoreListener(new MovieAdapterTest.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                movieList.add(null);    //只要是其他类型就会显示progressbar
                handler.sendEmptyMessage(MovieManager.GET_MORE);


//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        movieList.remove(movieList.size() - 1); //移除progressbar
//                        movieAdapterTest.notifyItemRemoved(movieList.size());   //通知移除
//                        for(int i=0; i<1; i++) {
//                            Movie movie = new Movie();
//                            movie.setName("new add");
//                            movieList.add(movie);   //添加数据
//                            movieAdapterTest.notifyItemInserted(movieList.size());  //通知添加数据
//                        }
//                        movieAdapterTest.setLoaded();   //结束载入
//                    }
//                },2000);
//                Log.e("TAG","load");
            }
        });
    }

    /**
     * 开始
     */
    public void start() {
        movieManager.getMovieJson(from,count,handler);
    }
}
