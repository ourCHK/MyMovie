package com.chk.mymovie.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.chk.mymovie.MovieDetailActivity;
import com.chk.mymovie.R;
import com.chk.mymovie.adapter.MySearchMovieAdapter;
import com.chk.mymovie.application.MyApplication;
import com.chk.mymovie.bean.SearchMovie;
import com.chk.mymovie.impl.SearchMovieManager;
import com.chk.mymovie.myinterface.OnLoadMoreListener;
import com.chk.mymovie.myview.MyMovieRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.chk.mymovie.fragment.ComingSoonFragment.MOVIE_COMING_SOON;

/**
 * Created by chk on 17-3-13.
 */

public class SearchFragment extends Fragment {
    Handler handler;
    SearchMovieManager searchMovieManager;
    SearchView searchView;
    View view;
    AlertDialog alertDialog;

    LinearLayoutManager layoutManager;
    MyMovieRecyclerView searchRecyclerView;
    MySearchMovieAdapter movieAdapter;
    TextView searchResult;

    List<SearchMovie> movieList;
    String searchMovieJson;
    String keyword;
    int start=0;
    int count=20;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_fragment_search,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void init() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SearchMovieManager.NETWORK_ERROR:
                        Toast.makeText(getContext(), "网络错误，请重试！", Toast.LENGTH_SHORT).show();
                        break;
                    case SearchMovieManager.GET_JSON_COMPLETE:
                        searchMovieJson = (String) msg.obj;
                        searchMovieManager.parseMovieJson(movieList,searchMovieJson,this);
                        break;
                    case SearchMovieManager.PARSE_JSON_COMPLETE:
                        for (SearchMovie searchMovie: movieList) {
                            Log.e("SearchFragment",searchMovie.getTitle());
                        }
                        alertDialog.dismiss();
                        searchResult.setVisibility(View.VISIBLE);
                        movieAdapter.notifyDataSetChanged();
                        movieAdapter.setLoaded();
                        break;
                }
            }
        };

        alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("搜索中，请稍候...")
                .setView(new ProgressBar(getContext()))
                .create();

        movieList = new ArrayList<>();
        searchMovieManager = new SearchMovieManager();
        resInit();
        widgetInit();

    }

    private void resInit() {

    }

    private void widgetInit() {
        searchResult = (TextView) view.findViewById(R.id.searchResult);
        searchView = (SearchView) view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) { //搜索按钮
                keyword = s;
                if (keyword != null && !keyword.isEmpty())
                    search();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText(getContext(), "点击close", Toast.LENGTH_SHORT).show();
                movieList.clear();
                movieAdapter.notifyDataSetChanged();
                searchResult.setVisibility(View.GONE);
                return false;
            }
        });



        layoutManager = new LinearLayoutManager(getActivity());
        searchRecyclerView = (MyMovieRecyclerView) view.findViewById(R.id.searchRecyclerView);
        searchRecyclerView.setLayoutManager(layoutManager);
        movieAdapter = new MySearchMovieAdapter(movieList, searchRecyclerView, new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

            }
        });
        movieAdapter.setOnItemClickListener(new MySearchMovieAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Toast.makeText(MyApplication.getContext(), "you click the item and the id is:"+data, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra("movieType",MOVIE_COMING_SOON);
                intent.putExtra("movieId",Integer.parseInt(data));
                getActivity().startActivity(intent);
            }
        });
        searchRecyclerView.setAdapter(movieAdapter);

    }

    /**
     * 开始搜索
     */
    public void search() {
        if (keyword.isEmpty()) {
            Toast.makeText(getContext(), "请输入搜索内容", Toast.LENGTH_SHORT).show();
            return;
        }
        alertDialog.show();
        movieList.clear();
        searchMovieManager.getSearchMovieJson(keyword,start,count,handler);
    }

}
