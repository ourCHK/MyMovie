package com.chk.mymovie.utils;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by chk on 17-3-16.
 */

public class OkHttpUtil {

    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    private OkHttpUtil() {

    }

    /**
     * 用于访问注册用的网络
     * @param url   访问的网址
     * @param name
     * @param sex
     * @param account
     * @param password
     * @param phone
     * @param callback  需传入一个实现的callback
     */
    public static void register(String url, String name, String sex, String account, String password, String phone, Callback callback) {
        RequestBody requestBody = new FormBody.Builder()
                .add("name",name)
                .add("sex",sex)
                .add("account",account)
                .add("password",password)
                .add("phone",phone).build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);

    }


    public static void login(String url,String account,String password) {
        RequestBody requestBody = new FormBody.Builder()
                .add("account",account)
                .add("password",password).build();
//        Request request = new Request.Builder().url("");
    }







}
