package com.chk.mymovie.impl;


import android.os.Handler;

import com.chk.mymovie.LoginActivity;
import com.chk.mymovie.RegisterActivity;
import com.chk.mymovie.dao.UserDao;
import com.chk.mymovie.tools.OKHttpUtil;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserManager implements UserDao {

	String genymotionIp = "http://192.168.56.1:8080";
	String nativeIp = "http://10.0.2.2:8080";
	String outerIp = "http://18.8.6.109:8080";
	String chooseIp = nativeIp;


	@Override
	public void loginUser(String userAccount,String userPassword,final Handler logHandler) {
		// TODO Auto-generated method stub

		HashMap<String,String>	hashMap = new HashMap<>();
		hashMap.put("account",userAccount);
		hashMap.put("password",userPassword);

		OKHttpUtil.postRequest(chooseIp + "/MyMovieService/LoginServlet", hashMap, new Callback() {

			@Override
			public void onFailure(Call call, IOException e) {
				logHandler.sendEmptyMessage(LoginActivity.NETWORK_ERROR);	//网络请求失败
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				String result = response.body().string();
				if (result.equals("SUCCESS")) 	//登录成功
					logHandler.sendEmptyMessage(LoginActivity.SUCCESS_LOGIN);
				else if (result.equals("FAILURE"))	//登录失败
					logHandler.sendEmptyMessage(LoginActivity.FAILURE_LOGIN);
			}
		});
	}

	@Override
	public void registerUser(String name, String sex, String account, String password, String phone, final Handler regHandler) {
		HashMap<String,String> hashMap = new HashMap<>();
		hashMap.put("name",name);
		hashMap.put("sex",sex);
		hashMap.put("account",account);
		hashMap.put("password",password);
		hashMap.put("phone",phone);
		OKHttpUtil.postRequest(chooseIp + "/MyMovieService/RegisterServlet", hashMap, new Callback() {

			@Override
			public void onFailure(Call call, IOException e) {
				regHandler.sendEmptyMessage(RegisterActivity.NETWORK_ERROR);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				String result = response.body().string();
				if (result.equals("SUCCESS")) 	//登录成功
					regHandler.sendEmptyMessage(RegisterActivity.SUCCESS_REGISTER);
				else if (result.equals("FAILURE"))	//登录失败
					regHandler.sendEmptyMessage(RegisterActivity.FAILURE_REGISTER);
			}
		});
	}

	@Override
	public void deleteUser() {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateUser() {
		// TODO Auto-generated method stub
	}

}
