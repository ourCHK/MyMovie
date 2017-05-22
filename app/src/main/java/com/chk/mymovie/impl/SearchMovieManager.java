package com.chk.mymovie.impl;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.chk.mymovie.R;
import com.chk.mymovie.adapter.MyComingSoonMovieAdapter;
import com.chk.mymovie.adapter.MyMovieAdapter;
import com.chk.mymovie.application.MyApplication;
import com.chk.mymovie.bean.InTheaterMovie;
import com.chk.mymovie.bean.SearchMovie;
import com.chk.mymovie.tools.OKHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by chk on 17-5-18.
 */

public class SearchMovieManager {

    /**
     * 网络错误
     */
    public static final int NETWORK_ERROR = -1;

    /**
     * 获取Json完成
     */
    public static final int GET_JSON_COMPLETE = 1;

    /**
     * 解析Json完成
     */
    public static final int PARSE_JSON_COMPLETE = 2;

    /**
     * 获取跟多数据
     */
    public static final int GET_MORE = 3;

    /**
     * 没有更多数据
     */
    public static final int NO_MORE = 4;

    /**
     * 没有找到影片
     */
    public static final int SEARCH_NONE = 5;

    String chooseIp = MyApplication.getContext().getString(R.string.choosedIp);

    public void search(String keyword,Handler handler) {

    }

    public void getSearchMovieJson(String keyword,int start,int count,final Handler handler) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("start",start+"");
        hashMap.put("count",count+"");
        hashMap.put("keyword",keyword);
        OKHttpUtil.getRequest(chooseIp + "/MyMovieService/SearchServlet", hashMap, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(NETWORK_ERROR);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Message message = new Message();
                message.what = GET_JSON_COMPLETE;
                message.obj = result;
                handler.sendMessage(message);
            }
        });
    }

    public void parseMovieJson(List<SearchMovie> movieList, String movieJson, Handler handler) {
        Gson gson = new Gson();
        ArrayList<SearchMovie> movieTempList = gson.fromJson(movieJson,new TypeToken<ArrayList<SearchMovie>>(){}.getType());
        for(SearchMovie searchMovie: movieTempList) {
            movieList.add(searchMovie);
        }
        handler.sendEmptyMessage(PARSE_JSON_COMPLETE);
    }

}
