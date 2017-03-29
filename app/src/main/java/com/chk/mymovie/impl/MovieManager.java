package com.chk.mymovie.impl;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.chk.mymovie.adapter.MyMovieAdapter;
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

    String genymotionIp = "http://192.168.56.1:8080";
    String nativeIp = "http://10.0.2.2:8080";
    String outerIp = "http://18.8.6.109:8080";
    String chooseIp = nativeIp;


    /**
     * 网络错误
     */
    public static final int NETWORK_ERROR = -1;

    /**
     * 获取Json完成
     */
    public static final int GET_COMPLETE = 1;

    /**
     * 解析Json完成
     */
    public static final int PARSE_COMPLETE = 2;

    /**
     * 获取跟多数据
     */
    public static final int GET_MORE = 3;

    /**
     * 没有更多数据
     */
    public static final int NO_MORE = 4;


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

        OKHttpUtil.getRequest(chooseIp + "/MyMovieService/GetJsonServlet", hashMap, new Callback() {

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
        if (!movieList.isEmpty()) {
            movieList.remove(movieList.size() - 1);
            if (movieList.get(movieList.size()-1).getName() == MyMovieAdapter.SET_PROGRESS_BAR)
                movieList.remove(movieList.size() - 1);
        }
        if(movieJson.isEmpty()) {//若Json返回数据为kong,则不进行解析
            Movie movie = new Movie();
            movie.setName(MyMovieAdapter.SET_NO_MORE_TEXT);
            movieList.add(movie);
            handler.sendEmptyMessage(NO_MORE);
            return;
        }
        Gson gson = new Gson();
        ArrayList<Movie> movieTempList = gson.fromJson(movieJson,new TypeToken<ArrayList<Movie>>(){}.getType());
        for(Movie movie: movieTempList) {
            movieList.add(movie);
        }
        handler.sendEmptyMessage(PARSE_COMPLETE);
    }
}
