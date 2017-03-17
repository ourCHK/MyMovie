package com.chk.mymovie.bean;

/**
 * Created by chk on 17-3-17.
 */

public class Pic {

    int picImage;
    String picText;
    String picName;
    String picAddress;

    public String getPicAddress() {
        return picAddress;
    }

    public void setPicAddress(String picAddress) {
        this.picAddress = picAddress;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public int getPicImage() {
        return picImage;
    }

    public void setPicImage(int picImage) {
        this.picImage = picImage;
    }

    public String getPicText() {
        return picText;
    }

    public void setPicText(String picText) {
        this.picText = picText;
    }
}
