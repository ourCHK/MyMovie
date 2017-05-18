package com.chk.mymovie.bean;

import java.sql.Date;

/**
 * Created by chk on 17-5-14.
 * 电影订单类
 */

public class MovieOrder {

    int userId;
    int movieId;
    int choosed_row;
    int choosed_column;
    Date create_date;

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getMovieId() {
        return movieId;
    }
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    public int getChoosed_row() {
        return choosed_row;
    }
    public void setChoosed_row(int choosed_row) {
        this.choosed_row = choosed_row;
    }
    public int getChoosed_column() {
        return choosed_column;
    }
    public void setChoosed_column(int choosed_column) {
        this.choosed_column = choosed_column;
    }
    public Date getCreate_date() {
        return create_date;
    }
    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }
}
