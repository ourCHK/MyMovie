package com.chk.mymovie.bean;

public class ComingSoonMovie {
	int id;
	String title;
	int collect_count;
	String original_title;
	int year;
	String images;	//获取图片地址
	String image_path;	//本地图片路径
	
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
	public int getCollect_count() {
		return collect_count;
	}
	public void setCollect_count(int collect_count) {
		this.collect_count = collect_count;
	}
	public String getOriginal_title() {
		return original_title;
	}
	public void setOriginal_title(String original_title) {
		this.original_title = original_title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
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
	
	
}
