package com.chk.mymovie.dao;

import android.os.Handler;

import com.chk.mymovie.bean.ComingSoonMovie;
import com.chk.mymovie.bean.Movie;

import java.util.List;

/**
 * Created by chk on 17-4-10.
 */

public interface ComingSoonMovieDao {
    /**
     *
     *  查询指定页面的数据
     * @param from 开始查询位置
     * @param count 要查询的数量
     */
    public void getMovieJson(int from,int count,Handler handler);

    public void parseMovieJson(List<ComingSoonMovie> csMovieList, String movieJson, Handler handler);
}
