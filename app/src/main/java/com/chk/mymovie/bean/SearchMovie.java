package com.chk.mymovie.bean;

public class SearchMovie {
	int id;
	String title;
	float average;
	int year;
	boolean isInThreater;
	String images;
	int total;
	String image_path;	//本地图片路径

	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public float getAverage() {
		return average;
	}
	public void setAverage(float average) {
		this.average = average;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public boolean isInThreater() {
		return isInThreater;
	}
	public void setInThreater(boolean isInThreater) {
		this.isInThreater = isInThreater;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}
}
