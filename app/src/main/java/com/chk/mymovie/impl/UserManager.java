package com.chk.mymovie.impl;


import android.os.Handler;

import com.chk.mymovie.LoginActivity;
import com.chk.mymovie.dao.UserDao;
import com.chk.mymovie.tools.OKHttpUtil;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserManager implements UserDao {


	@Override
	public void loginUser(String userAccount,String userPassword,final Handler handler) {
		// TODO Auto-generated method stub

		HashMap<String,String>	hashMap = new HashMap<>();
		hashMap.put("account",userAccount);
		hashMap.put("password",userPassword);
//		OKHttpUtil.getRequest("http://10.0.2.2:8080/MyMovieService/LoginServlet", hashMap, new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//				handler.sendEmptyMessage(LoginActivity.NETWORK_ERROR);	//网络请求失败
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				String result = response.body().string();
//				if (result.equals("SUCCESS")) 	//登录成功
//					handler.sendEmptyMessage(LoginActivity.SUCCESS_LOGIN);
//				else if (result.equals("FAILURE"))	//登录失败
//					handler.sendEmptyMessage(LoginActivity.FAILURE_LOGIN);
//			}
//		});

		OKHttpUtil.postRequest("http://10.0.2.2:8080/MyMovieService/LoginServlet", hashMap, new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				handler.sendEmptyMessage(LoginActivity.NETWORK_ERROR);	//网络请求失败
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				String result = response.body().string();
				if (result.equals("SUCCESS")) 	//登录成功
					handler.sendEmptyMessage(LoginActivity.SUCCESS_LOGIN);
				else if (result.equals("FAILURE"))	//登录失败
					handler.sendEmptyMessage(LoginActivity.FAILURE_LOGIN);
			}
		});
	}

	@Override
	public void insertUser() {
		// TODO Auto-generated method stub
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
