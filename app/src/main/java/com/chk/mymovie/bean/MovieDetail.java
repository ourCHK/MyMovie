package com.chk.mymovie.bean;

/**
 * Created by chk on 17-5-15.
 */

public class MovieDetail {

    int id;
    int rating;
    int year;
    String title;
    String genres;
    String summary;
    String casts;
    String directors;
    String mobile_url;
    String images;
    String image_path;
    String aka;

    public String getGenres() {
        return genres;
    }
    public void setGenres(String genres) {
        this.genres = genres;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getCasts() {
        return casts;
    }
    public void setCasts(String casts) {
        this.casts = casts;
    }
    public String getDirectors() {
        return directors;
    }
    public void setDirectors(String directors) {
        this.directors = directors;
    }
    public String getMobile_url() {
        return mobile_url;
    }
    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }
    public String getImages() {
        return images;
    }
    public void setImages(String images) {
        this.images = images;
    }
    public String getImage_path() {
        return image_path;
    }
    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
    public String getAka() {
        return aka;
    }
    public void setAka(String aka) {
        this.aka = aka;
    }
}
