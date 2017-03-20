package com.chk.mymovie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.chk.mymovie.adapter.MyPicItemAdapter;
import com.chk.mymovie.bean.Pic;
import com.chk.mymovie.bean.PicInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestGsonActivity extends AppCompatActivity {

    public static String TAG = "TestGsonActivity";
    public static final int PARSE_COMPLETE = 2;
    public PicInfo picInfo;
    ArrayList<Pic> picList;
    ArrayList<PicInfo> picInfoList;
    Button lastPage;
    Button nextPage;
    ListView picListView;
    ArrayAdapter picAdapter;
    int from = 0;   //开始查询的页数
    int to = 5;    //终止查询的页数
    int size = 5;   //查询的间隔

    String picListJson;
    Handler handler;
    byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_gson);
        init();
        getJson(from,to);
    }


    public void init() {
        picList = new ArrayList<>();
        picInfoList = new ArrayList<>();

        lastPage = (Button) findViewById(R.id.lastPage);
        nextPage = (Button) findViewById(R.id.nextPage);

        lastPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picList.clear();
                from -= size;
                to -= size;
                getJson(from,to);
            }
        });

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picList.clear();
                from += size;
                to += size;
                getJson(from,to);
            }
        });

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
                        break;
                    case 2:
                        picAdapter.notifyDataSetChanged();
                        picListView.setSelection(0);
                        break;
                }
             }
        };

    }


    /**
     *  查询指定页面的数据
     * @param from 起始的页数
     * @param to 终止的页数
     */
    public void getJson(int from,int to) {
                OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://10.0.2.2:8080/MyMovieService/GetJsonServlet?from=" + from + "&to=" + to).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"network request fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                picListJson = response.body().string();
                parseListJsonInfo();
                handler.sendEmptyMessage(PARSE_COMPLETE);
//                picInfo = parseJson(picListJson);
//                Log.e(TAG,picListJson);
//                Log.e(TAG,picInfo.getPicName());
//                Log.e(TAG,picInfo.getPicPath());
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
            Log.e(TAG,picInfo.getPicName()+picInfo.getPicPath()+"");
            Pic pic = new Pic();
            pic.setPicName(picInfo.getPicName());
            pic.setPicPath(picInfo.getPicPath());
            picList.add(pic);
        }
    }
}
