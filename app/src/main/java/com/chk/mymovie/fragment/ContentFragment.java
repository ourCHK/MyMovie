package com.chk.mymovie.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chk.mymovie.R;
import com.chk.mymovie.adapter.MyOptionAdapter;

/**
 * Created by chk on 17-3-13.
 */

public class ContentFragment extends Fragment {

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

    RecyclerView ComingSoonMovie;


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
        init();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void init() {
        resInit();
        widgetInit();

    }

    private void resInit() {
        res = getResources();
        type = res.getStringArray(R.array.type);
        area = res.getStringArray(R.array.area);
        time = res.getStringArray(R.array.time);
    }

    private void widgetInit() {

        optionType = (RecyclerView) view.findViewById(R.id.optionType);
        optionArea = (RecyclerView) view.findViewById(R.id.optionArea);
        optionTime = (RecyclerView) view.findViewById(R.id.optionTime);

        adapterType = new MyOptionAdapter(getActivity(),type);
        adapterArea = new MyOptionAdapter(getActivity(),area);
        adapterTime = new MyOptionAdapter(getActivity(),time);

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

        typeLayoutManager = new LinearLayoutManager(getActivity());
        areaLayoutManager = new LinearLayoutManager(getActivity());
        timeLayoutManager = new LinearLayoutManager(getActivity());

        typeLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        areaLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        timeLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        optionType.setLayoutManager(typeLayoutManager);
        optionArea.setLayoutManager(areaLayoutManager);
        optionTime.setLayoutManager(timeLayoutManager);

        optionType.setAdapter(adapterType);
        optionArea.setAdapter(adapterArea);
        optionTime.setAdapter(adapterTime);


    }



}
