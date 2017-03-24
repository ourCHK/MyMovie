package com.chk.mymovie.tools;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by chk on 17-3-15.
 */

public class OKHttpUtil {

    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    /**
     * 开启异步任务的线程访问网络
     * @param request
     */
    public static void enqueue(Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    /**
     * post请求
     */
    public static void postRequest() {

    }

    /**
     * get请求的封装
     * @param url   URL地址
     * @param hashMap   要添加的参数
     * @param callback  回调接口
     */
    public static void getRequest(String url, HashMap<String,String> hashMap,Callback callback) {
        if (hashMap != null) {
            url += "?";
                for(String key:hashMap.keySet()) {
                    url += key + "=" +hashMap.get(key) + "&";
                }
            }
        Request request = new Request.Builder()
                .url(url)
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);

    }

    public static void postRequest(String url, HashMap<String,String> hashMap,Callback callback) {

        FormBody.Builder builder = new FormBody.Builder();
        if (hashMap != null) {
            for(String key:hashMap.keySet()) {
                builder.add(key,hashMap.get(key));
            }
        }

        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }





}
