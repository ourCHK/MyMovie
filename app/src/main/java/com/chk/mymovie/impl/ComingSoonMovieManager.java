package com.chk.mymovie.impl;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.chk.mymovie.R;
import com.chk.mymovie.adapter.MyComingSoonMovieAdapter;
import com.chk.mymovie.adapter.MyMovieAdapter;
import com.chk.mymovie.application.MyApplication;
import com.chk.mymovie.bean.ComingSoonMovie;
import com.chk.mymovie.dao.ComingSoonMovieDao;
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
 * Created by chk on 17-4-10.
 */

public class ComingSoonMovieManager implements ComingSoonMovieDao{

    String chooseIp = MyApplication.getContext().getString(R.string.choosedIp);
    String type = MyApplication.getContext().getString(R.string.ComingSoonMovie);   //用于区别是获取电影的类型,比如正在上映和未上映等等

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
        hashMap.put("type",type);

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
    public void parseMovieJson(List<ComingSoonMovie> movieList, String movieJson, Handler handler) {
        if (!movieList.isEmpty()) {
            movieList.remove(movieList.size() - 1);
            if (movieList.get(movieList.size()-1).getTitle() == MyMovieAdapter.SET_PROGRESS_BAR)
                movieList.remove(movieList.size() - 1);
        }
        if(movieJson.isEmpty()) {//若Json返回数据为kong,则不进行解析
            ComingSoonMovie csMovie = new ComingSoonMovie();
            csMovie.setTitle(MyComingSoonMovieAdapter.SET_NO_MORE_TEXT);
            movieList.add(csMovie);
            handler.sendEmptyMessage(NO_MORE);
            return;
        }
        Gson gson = new Gson();
         ArrayList<ComingSoonMovie> movieTempList = gson.fromJson(movieJson,new TypeToken<ArrayList<ComingSoonMovie>>(){}.getType());
        for(ComingSoonMovie csMovie: movieTempList) {
            movieList.add(csMovie);
        }
        Log.e("tag",movieList.size()+"");
        handler.sendEmptyMessage(PARSE_COMPLETE);
    }
}
