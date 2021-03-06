package com.chk.mymovie.impl;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.chk.mymovie.R;
import com.chk.mymovie.adapter.MyComingSoonMovieAdapter;
import com.chk.mymovie.adapter.MyMovieAdapter;
import com.chk.mymovie.application.MyApplication;
import com.chk.mymovie.bean.InTheaterMovie;
import com.chk.mymovie.dao.InTheaterMovieDao;
import com.chk.mymovie.tools.OKHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by chk on 17-4-10.
 */

public class InTheaterMovieManager implements InTheaterMovieDao{

    String chooseIp = MyApplication.getContext().getString(R.string.choosedIp);
    String type = MyApplication.getContext().getString(R.string.InTheaterMovie);   //用于区别提交至服务端电影类型,比如正在上映和即将上映等等
    String typeMoviePrice = "MoviePrice";
    String typeChoosedMovieSeats = "ChoosedSeats";

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
     * 获取电影价格完成
     */
    public static final int GET_MOVIE_PRICE_COMPLETE = 5;

    /**
     * 获取已选择座位完成
     */
    public static final int GET_CHOOSED_MOVIE_SEATS_JSON_COMPLETE = 6;

    /**
     * 买票
     */
    public static final int BUY_TICKET = 7;

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
                message.what = GET_JSON_COMPLETE;
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
    public void parseMovieJson(List<InTheaterMovie> movieList, String movieJson, Handler handler) {
        if (!movieList.isEmpty()) {
            movieList.remove(movieList.size() - 1);
            if (movieList.get(movieList.size()-1).getTitle() == MyMovieAdapter.SET_PROGRESS_BAR)
                movieList.remove(movieList.size() - 1);
        }
        if(movieJson.isEmpty()) {//若Json返回数据为kong,则不进行解析
            InTheaterMovie itMovie = new InTheaterMovie();
            itMovie.setTitle(MyComingSoonMovieAdapter.SET_NO_MORE_TEXT);
            movieList.add(itMovie);
            handler.sendEmptyMessage(NO_MORE);
            return;
        }
        Gson gson = new Gson();
         ArrayList<InTheaterMovie> movieTempList = gson.fromJson(movieJson,new TypeToken<ArrayList<InTheaterMovie>>(){}.getType());
        for(InTheaterMovie inTheaterMovie: movieTempList) {
            movieList.add(inTheaterMovie);
        }
        Log.e("tag",movieList.size()+"");
        handler.sendEmptyMessage(PARSE_JSON_COMPLETE);
    }

    public void getMoviePrice(int movieId,final Handler handler) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("movieId",movieId+"");
        hashMap.put("type",typeMoviePrice);
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
                message.what = GET_MOVIE_PRICE_COMPLETE;
                message.obj = result;
                handler.sendMessage(message);
            }
        });

    }

    public void getChoosedMovieSeatJson(int movieId,String ticket_date,final Handler handler) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("movieId",movieId+"");
        hashMap.put("ticket_date",ticket_date);
        hashMap.put("type",typeChoosedMovieSeats);
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
                message.what = GET_CHOOSED_MOVIE_SEATS_JSON_COMPLETE;
                message.obj = result;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void buyTicket(int userId, int movieId, String rows, String columns, final Handler handler) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("userId",userId+"");
        hashMap.put("movieId",movieId+"");
        hashMap.put("rows",rows);
        hashMap.put("columns",columns);
        OKHttpUtil.getRequest(chooseIp + "/MyMovieService/BuyTicketServlet", hashMap, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(NETWORK_ERROR);
                Log.getStackTraceString(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Message message = new Message();
                message.what = BUY_TICKET;
                message.obj = result;
                handler.sendMessage(message);
            }
        });
    }
}
