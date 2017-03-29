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
import com.chk.mymovie.bean.Movie;
import com.chk.mymovie.bean.Pic;
import com.chk.mymovie.bean.PicInfo;
import com.chk.mymovie.tools.OKHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TestGsonActivity extends AppCompatActivity {

    public static final String TAG = "TestGsonActivity";
    public static final int PARSE_COMPLETE = 2;

    Button lastPage;
    Button nextPage;
    ListView picListView;
    ArrayAdapter picAdapter;
    int from = 0;   //开始查询的页数
    int to = 5;    //终止查询的页数
    int size = 5;   //查询的间隔

    ArrayList<Movie> movieList;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_gson);
        init();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case 1:
                        //通过imageview，设置图片
                        break;
                    case 2:
                        Log.e(TAG,"shoudao");
                        picAdapter.notifyDataSetChanged();
                        picListView.setSelection(0);
                        break;
                }
            }
        };

        getJson(from,to);
    }


    public void init() {
        movieList = new ArrayList<>();

        lastPage = (Button) findViewById(R.id.lastPage);
        nextPage = (Button) findViewById(R.id.nextPage);
        picListView = (ListView) findViewById(R.id.picList);

        lastPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieList.clear();
                from -= size;
                to -= size;
                getJson(from,to);
            }
        });

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieList.clear();
                from += size;
                to += size;
                getJson(from,to);
            }
        });

        picAdapter = new MyPicItemAdapter(TestGsonActivity.this,R.layout.layout_picitem,movieList);
        picListView.setAdapter(picAdapter);

    }


    /**
     *  查询指定页面的数据
     * @param from 起始的页数
     * @param to 终止的页数
     */
    public void getJson(int from,int to) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("from",from+"");
        hashMap.put("to",to+"");
        OKHttpUtil.getRequest("http://10.0.2.2:8080/MyMovieService/GetJsonServlet", hashMap, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.getStackTraceString(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                parseListJsonInfo(result);

            }
        });
    }

    /**
     * 解析Json至List
     * @param movieInfo
     */
    public void parseListJsonInfo(String movieInfo) {
        movieList.clear();
        Gson gson = new Gson();
        ArrayList<Movie> movieTempList = gson.fromJson(movieInfo,new TypeToken<ArrayList<Movie>>(){}.getType());
        for(Movie movie: movieTempList) {
           movieList.add(movie);
        }
        handler.sendEmptyMessage(PARSE_COMPLETE);
    }
}
