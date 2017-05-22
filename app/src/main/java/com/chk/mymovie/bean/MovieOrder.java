package com.chk.mymovie.bean;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by chk on 17-5-14.
 * 电影订单类
 */

public class MovieOrder {

    int userId;
    int movieId;
    int choosed_row;
    int choosed_column;
    Date ticket_date;
    Timestamp create_date;



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

    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }

    public Timestamp getCreate_date() {
        return create_date;
    }

    public Date getTicket_date() {
        return ticket_date;
    }

    public void setTicket_date(Date ticket_date) {
        this.ticket_date = ticket_date;
    }

}
