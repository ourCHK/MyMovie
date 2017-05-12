package com.chk.mymovie.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chk.mymovie.R;
import com.chk.mymovie.adapter.MyComingSoonMovieAdapter;
import com.chk.mymovie.adapter.MyMovieAdapter;
import com.chk.mymovie.bean.ComingSoonMovie;
import com.chk.mymovie.impl.ComingSoonMovieManager;
import com.chk.mymovie.impl.MovieManager;
import com.chk.mymovie.myinterface.OnLoadMoreListener;
import com.chk.mymovie.myview.MyMovieRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chk on 17-5-10.
 */

public class ComingSoonFragment extends Fragment {

    Handler handler;
    String movieJson;
    List<ComingSoonMovie> movieList;
    ComingSoonMovieManager movieManager;
    LinearLayoutManager layoutManager;
    MyComingSoonMovieAdapter movieAdapter;
    MyMovieRecyclerView recyclerView;
    int from;
    int count;


    public void init() {
        movieList = new ArrayList<>();
        recyclerView = (MyMovieRecyclerView) getActivity().findViewById(R.id.comingSoonMovieRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        movieManager = new ComingSoonMovieManager();
        movieAdapter = new MyComingSoonMovieAdapter(movieList, recyclerView, new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                ComingSoonMovie csMovie = new ComingSoonMovie();
                csMovie.setTitle(MyMovieAdapter.SET_PROGRESS_BAR);
                movieList.add(csMovie);    //只要不是Movie类型就会显示progressbar
                movieAdapter.notifyItemInserted(movieList.size()-1);    //此方法可以在子线程中调用，和notifyDataSetChanged不同
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(MovieManager.GET_MORE);
                    }
                },2000);
            }
        });
        recyclerView.setAdapter(movieAdapter);
        from = 0;
        count = 10;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_coming_soon,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ComingSoonMovieManager.GET_JSON_COMPLETE:
                        movieJson = (String) msg.obj;
                        movieManager.parseMovieJson(movieList,movieJson,handler);
                        break;
                    case ComingSoonMovieManager.PARSE_JSON_COMPLETE:
                        movieAdapter.notifyDataSetChanged();
                        movieAdapter.setLoaded();
                        from += count;
                        break;
                    case ComingSoonMovieManager.GET_MORE: //获取更多数据
                        movieManager.getMovieJson(from,count,handler);
                        break;
                    case ComingSoonMovieManager.NO_MORE:  //没有跟多数据的时候
                        movieAdapter.notifyDataSetChanged();
                        break;
                    case ComingSoonMovieManager.NETWORK_ERROR:    //网络错误
                        Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        init();
        startGet();
    }

    /**
     * 开始获取数据
     */
    public void startGet() {
        movieManager.getMovieJson(from,count,handler);
    }
}
