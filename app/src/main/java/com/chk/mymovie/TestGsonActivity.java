package com.chk.mymovie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.chk.mymovie.adapter.MyPicItemAdapter;
import com.chk.mymovie.bean.Pic;
import com.chk.mymovie.bean.PicInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestGsonActivity extends AppCompatActivity {

    public static String TAG = "TestGsonActivity";
    public static final int PARSE_COMPLETE = 2;
    public PicInfo picInfo;
    ImageView picOne;
    ImageView picTwo;
    ArrayList<Pic> picList;
    ArrayList<PicInfo> picInfoList;
    ListView picListView;
    ArrayAdapter picAdapter;

    String picListJson;
    Handler handler;
    byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_gson);
        init();
        getJson();
    }


    public void init() {
        picList = new ArrayList<>();
        picInfoList = new ArrayList<>();

        picListView = (ListView) findViewById(R.id.picList);
        picAdapter = new MyPicItemAdapter(TestGsonActivity.this,R.layout.layout_picitem,picList);
        picListView.setAdapter(picAdapter);


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
                    case 2:
                        picAdapter.notifyDataSetChanged();
                        break;
                }
             }
        };

    }


    public void getJson() {
                OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://10.0.2.2:8080/MyMovieService/GetJsonServlet?from=0&to=10").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                picListJson = response.body().string();
                parseListJsonInfo();
                handler.sendEmptyMessage(PARSE_COMPLETE);
//                picInfo = parseJson(picListJson);
//                Log.e(TAG,picListJson);
//                Log.e(TAG,picInfo.getPicName());
//                Log.e(TAG,picInfo.getPicAddress());
//
//                byte[] Picture_bt = response.body().bytes();
//                Message msg = new Message();
//                msg.obj = Picture_bt;
//                msg.what = 1;
//                handler.sendMessage(msg);


            }
        });
    }

    public PicInfo parseJson(String picJson) {
        Gson gson = new Gson();
        return gson.fromJson(picJson,PicInfo.class);
    }

    public void parseListJsonInfo() {
        Gson gson = new Gson();
        picInfoList = gson.fromJson(picListJson,new TypeToken<ArrayList<PicInfo>>(){}.getType());
        for(PicInfo picInfo:picInfoList) {
            Log.e(TAG,picInfo.getPicName()+picInfo.getPicAddress()+"");
            Pic pic = new Pic();
            pic.setPicName(picInfo.getPicName());
            pic.setPicAddress(picInfo.getPicAddress());
            picList.add(pic);
        }
    }
}
