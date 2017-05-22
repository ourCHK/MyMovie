package com.chk.mymovie;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chk.mymovie.adapter.MyOrderAdapter;
import com.chk.mymovie.application.MyApplication;
import com.chk.mymovie.bean.MovieOrder;
import com.chk.mymovie.bean.MovieOrderBuy;
import com.chk.mymovie.tools.OKHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OrderActivity extends AppCompatActivity {

    public static final int NETWORK_ERROR = -1;
    public static final int GET_JSON_COMPLETE = 1;
    public static final int PARSE_JSON_COMPLETE = 2;

    String chooseIp = MyApplication.getContext().getString(R.string.choosedIp);
    SharedPreferences prefs;
    Handler handler;
    RecyclerView orderRecyclerView;
    List<MovieOrderBuy> movieOrderBuyList;
    MyOrderAdapter orderAdapter;
    LinearLayoutManager layoutManager;
    String orderJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case GET_JSON_COMPLETE:
                        orderJson = (String) msg.obj;
                        parseJson();
                        break;
                    case PARSE_JSON_COMPLETE:
                        orderAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
        init();
        startGet();
    }

    public void init() {
        prefs = getSharedPreferences("MyMovie",MODE_PRIVATE);
        movieOrderBuyList = new ArrayList<>();
        orderAdapter = new MyOrderAdapter(this,movieOrderBuyList);
        layoutManager = new LinearLayoutManager(this);
        orderRecyclerView = (RecyclerView) findViewById(R.id.orderRecyclerView);
        orderRecyclerView.setAdapter(orderAdapter);
        orderRecyclerView.setLayoutManager(layoutManager);
    }

    public void startGet() {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("id",prefs.getInt("id",-1)+"");
        OKHttpUtil.getRequest(chooseIp + "/MyMovieService/GetOrderServlet", hashMap, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(NETWORK_ERROR);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e("OrderActivity",result);
                Message message = new Message();
                message.what = GET_JSON_COMPLETE;
                message.obj = result;
                handler.sendMessage(message);
            }
        });
    }

    public void parseJson() {
        Gson gson = new Gson();
        List<MovieOrderBuy> tempList = gson.fromJson(orderJson,new TypeToken<ArrayList<MovieOrderBuy>>(){}.getType());

        if (tempList!= null) {
            for (int i=0; i<tempList.size(); i++) {
                MovieOrderBuy movieOrderBuy = tempList.get(i);
                movieOrderBuy.setSeats((movieOrderBuy.getChoosed_row()+1)+"排 "+(movieOrderBuy.getChoosed_column()+1)+"列");
                if (i+1 <tempList.size()) {
                    if (movieOrderBuy.getCreate_date().equals(tempList.get(i+1).getCreate_date())) {
                        String seats = movieOrderBuy.getSeats();
                        seats += "\n"+(tempList.get(i+1).getChoosed_row()+1)+"排 "+(tempList.get(i+1).getChoosed_column()+1)+"列";
                        movieOrderBuy.setSeats(seats);
                        i++;
                    }
                }
                movieOrderBuyList.add(movieOrderBuy);
            }
        }
        handler.sendEmptyMessage(PARSE_JSON_COMPLETE);
    }



}
