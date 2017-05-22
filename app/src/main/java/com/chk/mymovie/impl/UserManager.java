package com.chk.mymovie.impl;


import android.os.Handler;
import android.os.Message;

import com.chk.mymovie.LoginActivity;
import com.chk.mymovie.ModifyActivity;
import com.chk.mymovie.R;
import com.chk.mymovie.RegisterActivity;
import com.chk.mymovie.application.MyApplication;
import com.chk.mymovie.dao.UserDao;
import com.chk.mymovie.tools.OKHttpUtil;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserManager implements UserDao {

	String chooseIp = MyApplication.getContext().getString(R.string.choosedIp);

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
				if (!result.equals("SUCCESS"))  {//登录成功
					Message message = new Message();
					message.what = LoginActivity.SUCCESS_LOGIN;
					message.obj = result;
					logHandler.sendMessage(message);
				}
				else //登录失败
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
				if (result.equals("SUCCESS")) 	//注册成功
					regHandler.sendEmptyMessage(RegisterActivity.SUCCESS_REGISTER);
				else if (result.equals("FAILURE"))	//注册失败
					regHandler.sendEmptyMessage(RegisterActivity.FAILURE_REGISTER);
			}
		});
	}

	@Override
	public void deleteUser() {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateUser(String name,String sex,String account,String password,String phone,final Handler handler) {
		// TODO Auto-generated method stub
		HashMap<String,String> hashMap = new HashMap<>();
		hashMap.put("name",name);
		hashMap.put("sex",sex);
		hashMap.put("account",account);
		hashMap.put("password",password);
		hashMap.put("phone",phone);
		OKHttpUtil.postRequest(chooseIp + "/MyMovieService/UpdateUserServlet", hashMap, new Callback() {

			@Override
			public void onFailure(Call call, IOException e) {
				handler.sendEmptyMessage(RegisterActivity.NETWORK_ERROR);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				String result = response.body().string();
				if (result.equals("SUCCESS")) 	//注册成功
					handler.sendEmptyMessage(ModifyActivity.MODIFY_SUCCESS);
				else if (result.equals("FAILURE"))	//注册失败
					handler.sendEmptyMessage(ModifyActivity.MODIFY_FAILURE);
			}
		});
	}

}
