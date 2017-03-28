package com.chk.mymovie.impl;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.chk.mymovie.bean.Movie;
import com.chk.mymovie.dao.MovieDao;
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
 * Created by chk on 17-3-27.
 */

public class MovieManager implements MovieDao{

    public static final int NETWORK_ERROR = -1;
    public static final int GET_COMPLETE = 1;
    public static final int PARSE_COMPLETE = 2;

    /**
     * 获取movie的Json
     * @param from 开始查询的位置
     * @param count 查询的数量
     * @param handler 用于和主线程通信
     */
    @Override
    public void getMovieJson(int from, int count, final Handler handler) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("from",from+"");
        hashMap.put("to",count+"");
        OKHttpUtil.getRequest("http://10.0.2.2:8080/MyMovieService/GetJsonServlet", hashMap, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(NETWORK_ERROR);
                Log.getStackTraceString(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Message message = new Message();
                message.what = GET_COMPLETE;
                message.obj = result;
                handler.sendMessage(message);
            }
        });
    }

    /**
     * 解析Json至movie
     * @param movieList 主线程传近来的movieList
     * @param movieJson 用于解析的MOVIEJSON
     * @param handler 用于和主线程进行通信
     */
    @Override
    public void parseMovieJson(List<Movie> movieList,String movieJson,Handler handler) {
        movieList.clear();
        Gson gson = new Gson();
        ArrayList<Movie> movieTempList = gson.fromJson(movieJson,new TypeToken<ArrayList<Movie>>(){}.getType());
        for(Movie movie: movieTempList) {
            movieList.add(movie);
        }
        handler.sendEmptyMessage(PARSE_COMPLETE);
    }
}
