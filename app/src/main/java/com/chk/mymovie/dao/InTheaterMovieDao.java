package com.chk.mymovie.dao;

import android.os.Handler;

import com.chk.mymovie.bean.ComingSoonMovie;
import com.chk.mymovie.bean.InTheaterMovie;

import java.util.List;

/**
 * Created by chk on 17-4-10.
 */

public interface InTheaterMovieDao {
    /**
     *
     *  查询指定页面的数据
     * @param from 开始查询位置
     * @param count 要查询的数量
     */
    void getMovieJson(int from, int count, Handler handler);

    void parseMovieJson(List<InTheaterMovie> itMovieList, String movieJson, Handler handler);

    /**
     * 采用rows 和 columns 采用 , 符号分隔开
     * @param userId
     * @param movieId
     * @param rows
     * @param columns
     * @param handler
     * @return
     */
    void buyTicket(int userId,int movieId,String rows,String columns, Handler handler);
}
