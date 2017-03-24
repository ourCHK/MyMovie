package com.chk.mymovie.dao;

import android.os.Handler;

public interface UserDao {
	/**
	 * 用户登录验证
	 * @param userAccount
	 * @param userPassword
	 * @param handler	用于更UI
     */
	public void loginUser(String userAccount, String userPassword,Handler handler);

	/**
	 * 用户注册
	 * @param name
	 * @param sex
	 * @param account
	 * @param password
	 * @param phone
     * @param handler
     */
	public void registerUser(String name,String sex,String account,String password,String phone,Handler handler);
	
	public void deleteUser();
	
	public void updateUser();
}
