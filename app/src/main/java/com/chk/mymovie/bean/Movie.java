package com.chk.mymovie.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by chk on 17-3-27.
 */

public class Movie implements Parcelable{
    private int picImage;
    private String picText;

    private int id;
    private String name;
    private String main_performer;
    private String introduce;
    private boolean is_on_show;
    private String path;


    public String getPicText() {
        return picText;
    }

    public void setPicText(String picText) {
        this.picText = picText;
    }

    public boolean is_on_show() {
        return is_on_show;
    }

    public int getPicImage() {
        return picImage;
    }

    public void setPicImage(int picImage) {
        this.picImage = picImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMain_performer() {
        return main_performer;
    }

    public void setMain_performer(String main_performer) {
        this.main_performer = main_performer;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public boolean isIs_on_show() {
        return is_on_show;
    }

    public void setIs_on_show(boolean is_on_show) {
        this.is_on_show = is_on_show;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Movie() {

    }

    public Movie(Parcel in) {
        id = in.readInt();
        name = in.readString();
        main_performer = in.readString();
        introduce = in.readString();
        boolean[] bArray = new boolean[1];
        in.readBooleanArray(bArray);
        is_on_show = bArray[0];
        path = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(main_performer);
        dest.writeString(introduce);
        dest.writeBooleanArray(new boolean[]{is_on_show});
        dest.writeString(path);
    }

    public void readFromParcel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        main_performer = in.readString();
        introduce = in.readString();
        boolean[] bArray = new boolean[1];
        in.readBooleanArray(bArray);
        is_on_show = bArray[0];
        path = in.readString();
    }


    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


}
