package com.chk.mymovie.tools;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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





}
