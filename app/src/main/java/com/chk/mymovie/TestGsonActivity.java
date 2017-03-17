package com.chk.mymovie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.chk.mymovie.bean.PicInfo;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestGsonActivity extends AppCompatActivity {

    public static String TAG = "TestGsonActivity";
    public PicInfo picInfo;
    ImageView picOne;
    ImageView picTwo;
    String picJson;
    Handler handler;
    byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_gson);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://10.0.2.2:8080/MyMovieService/PicServlet").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                picJson = response.body().string();
//                picInfo = parseJson(picJson);
//                Log.e(TAG,picJson);
//                Log.e(TAG,picInfo.getPicName());
//                Log.e(TAG,picInfo.getPicAddress());

                byte[] Picture_bt = response.body().bytes();
                Message msg = new Message();
                msg.obj = Picture_bt;
                msg.what = 1;
                handler.sendMessage(msg);


            }
        });

    }

    public void init() {
        picOne = (ImageView) findViewById(R.id.picOne);
        picTwo = (ImageView) findViewById(R.id.picTwo);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case 1:
                        byte[] Picture_bt = (byte[]) msg.obj;
                        Bitmap bitmap = BitmapFactory.decodeByteArray(Picture_bt, 0, Picture_bt.length);
                        //通过imageview，设置图片
                        picOne.setImageBitmap(bitmap);

                        break;
                }
             }
        };

    }

    public PicInfo parseJson(String picJson) {
        Gson gson = new Gson();
        return gson.fromJson(picJson,PicInfo.class);
    }
}
