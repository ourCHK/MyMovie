package com.chk.mymovie.fragment;

import android.content.Context;
import android.content.res.Resources;
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
import android.widget.Toast;

import com.chk.mymovie.R;
import com.chk.mymovie.adapter.MyComingSoonMovieAdapter;
import com.chk.mymovie.adapter.MyOptionAdapter;
import com.chk.mymovie.bean.ComingSoonMovie;
import com.chk.mymovie.impl.ComingSoonMovieManager;
import com.chk.mymovie.impl.MovieManager;
import com.chk.mymovie.myinterface.OnLoadMoreListener;
import com.chk.mymovie.myview.MyMovieRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chk on 17-3-13.
 */

public class ContentFragment extends Fragment {

    Handler handler;
    ComingSoonMovieManager csMovieManager;
    String csMovieJson;
    int from=82;
    int count=5;

    Resources res;
    String[] type;
    String[] area;
    String[] time;

    View view;
    RecyclerView optionType;
    RecyclerView optionArea;
    RecyclerView optionTime;
    LinearLayoutManager typeLayoutManager;
    LinearLayoutManager areaLayoutManager;
    LinearLayoutManager timeLayoutManager;
    MyOptionAdapter adapterType;
    MyOptionAdapter adapterArea;
    MyOptionAdapter adapterTime;

    MyMovieRecyclerView comingSoonMovie;
    LinearLayoutManager csMovieLayoutManager;
    List<ComingSoonMovie> csMovieList;
    MyComingSoonMovieAdapter csMovieAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.movie_search,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ComingSoonMovieManager.GET_JSON_COMPLETE:
                        csMovieJson = (String) msg.obj;
                        csMovieManager.parseMovieJson(csMovieList, csMovieJson, handler);
                        break;
                    case ComingSoonMovieManager.PARSE_JSON_COMPLETE:
                        csMovieAdapter.notifyDataSetChanged();
                        csMovieAdapter.setLoaded();
                        from += count;
                        break;
                    case ComingSoonMovieManager.GET_MORE:
                        csMovieManager.getMovieJson(from,count,handler);
                        break;
                    case ComingSoonMovieManager.NO_MORE:
                        csMovieAdapter.notifyDataSetChanged();
                        break;
                    case ComingSoonMovieManager.NETWORK_ERROR:
                        Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        init();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void init() {
        resInit();
        widgetInit();
        startGet();

    }

    private void resInit() {
        res = getResources();
        type = res.getStringArray(R.array.type);
        area = res.getStringArray(R.array.area);
        time = res.getStringArray(R.array.time);
        csMovieManager = new ComingSoonMovieManager();
        csMovieList = new ArrayList<>();
    }

    private void widgetInit() {

        optionType = (RecyclerView) view.findViewById(R.id.optionType);
        optionArea = (RecyclerView) view.findViewById(R.id.optionArea);
        optionTime = (RecyclerView) view.findViewById(R.id.optionTime);
        comingSoonMovie = (MyMovieRecyclerView) view.findViewById(R.id.comingSoonMovie);

        adapterType = new MyOptionAdapter(getActivity(),type);
        adapterArea = new MyOptionAdapter(getActivity(),area);
        adapterTime = new MyOptionAdapter(getActivity(),time);
        csMovieAdapter = new MyComingSoonMovieAdapter(csMovieList, comingSoonMovie, new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                ComingSoonMovie csMovie = new ComingSoonMovie();
                csMovie.setTitle(MyComingSoonMovieAdapter.SET_PROGRESS_BAR);
                csMovieList.add(csMovie);
                csMovieAdapter.notifyItemInserted(csMovieList.size()-1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(MovieManager.GET_MORE);
                    }
                },2000);
            }
        });

        adapterType.setOnItemClickListener(new MyOptionAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                if (view.isSelected())
                    view.setSelected(false);
                else
                    view.setSelected(true);
            }
        });
        adapterArea.setOnItemClickListener(new MyOptionAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                if (view.isSelected())
                    view.setSelected(false);
                else
                    view.setSelected(true);
            }
        });
        adapterTime.setOnItemClickListener(new MyOptionAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                if (view.isSelected())
                    view.setSelected(false);
                else
                    view.setSelected(true);
            }
        });

        csMovieAdapter.setOnItemClickListener(new MyComingSoonMovieAdapter.OnRecyclerViewItemClickListener(){
            @Override
            public void onItemClick(View view, String data) {
                Toast.makeText(getActivity(), "id:"+data, Toast.LENGTH_SHORT).show();
            }
        });

        typeLayoutManager = new LinearLayoutManager(getActivity());
        areaLayoutManager = new LinearLayoutManager(getActivity());
        timeLayoutManager = new LinearLayoutManager(getActivity());
        csMovieLayoutManager = new LinearLayoutManager(getActivity());

        typeLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        areaLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        timeLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        csMovieLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        optionType.setLayoutManager(typeLayoutManager);
        optionArea.setLayoutManager(areaLayoutManager);
        optionTime.setLayoutManager(timeLayoutManager);
        comingSoonMovie.setLayoutManager(csMovieLayoutManager);

        optionType.setAdapter(adapterType);
        optionArea.setAdapter(adapterArea);
        optionTime.setAdapter(adapterTime);
        comingSoonMovie.setAdapter(csMovieAdapter);


    }

    public void startGet() {
        csMovieManager.getMovieJson(from,count,handler);
    }



}
