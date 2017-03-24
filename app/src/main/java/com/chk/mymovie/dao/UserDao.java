package com.chk.mymovie.dao;

import android.os.Handler;

public interface UserDao {
	/**
	 * 查询用户是否存在用户，用于登录使用
	 * @return
	 */
	public void loginUser(String userAccount, String userPassword,Handler handler);
	
	public void insertUser();
	
	public void deleteUser();
	
	public void updateUser();
}
