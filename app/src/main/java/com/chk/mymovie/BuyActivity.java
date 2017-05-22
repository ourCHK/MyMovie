package com.chk.mymovie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chk.mymovie.application.MyApplication;
import com.chk.mymovie.bean.MovieOrder;
import com.chk.mymovie.impl.InTheaterMovieManager;
import com.chk.mymovie.myview.MyChooseSeatView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class BuyActivity extends AppCompatActivity {

    String chooseaIp = MyApplication.getContext().getString(R.string.choosedIp);

    SharedPreferences prefs;

    int rows = 6;
    int columns = 8;
    int[][] seats = new int[rows][columns];
    Handler handlerChooseSeatView;
    Button pay;
    MyChooseSeatView chooseSeatView;
    TextView movieTitleTextView;
    TextView ticketPrice;

    String[] data;
    String movieId;
    String movieTitle;

    InTheaterMovieManager inTheaterMovieManager;
    int price = 10;
    int count = 0;
    String movieOrderJson;
    AlertDialog alertDialog;

    TextView buySuccess;
    TextView appBarText;
    LinearLayout textBuy;
    RelativeLayout buyLayout;

    public void init() {
        prefs = getSharedPreferences("MyMovie",MODE_PRIVATE);
        Intent intent = getIntent();
        data = intent.getStringExtra("data").split(" ");
        movieId = data[0];
        movieTitle = data[1];

        ticketPrice = (TextView) findViewById(R.id.ticketPrice);
        appBarText = (TextView) findViewById(R.id.appBarText);
        textBuy = (LinearLayout) findViewById(R.id.textBuy);
        buyLayout = (RelativeLayout) findViewById(R.id.buyLayout);
        buySuccess = (TextView) findViewById(R.id.buySuccess);

        movieTitleTextView = (TextView) findViewById(R.id.movieTitleTextView);
        movieTitleTextView.setText(movieTitle);
        inTheaterMovieManager = new InTheaterMovieManager();

        chooseSeatView = (MyChooseSeatView) findViewById(R.id.chooseSeats);
        chooseSeatView.setHandler(handlerChooseSeatView);
        pay = (Button) findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BuyActivity.this, "click this ", Toast.LENGTH_SHORT).show();
                String choosedRows = "";
                String choosedColumns = "";
                for(int i=0; i<rows; i++)
                    for (int j=0; j<columns; j++) {
                        if (seats[i][j] == 1) {
                            choosedRows += i+",";
                            choosedColumns += j+",";
                        }
                    }
                inTheaterMovieManager.buyTicket(prefs.getInt("id",-1),Integer.parseInt(movieId),choosedRows,choosedColumns,handlerChooseSeatView);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        handlerChooseSeatView = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case InTheaterMovieManager.GET_MOVIE_PRICE_COMPLETE:
                        price = Integer.parseInt((String) msg.obj) ;
                        ticketPrice.append(price+"元");
                        inTheaterMovieManager.getChoosedMovieSeatJson(Integer.parseInt(movieId),new Date(System.currentTimeMillis()).toString(),this);
                        return;
                    case InTheaterMovieManager.GET_CHOOSED_MOVIE_SEATS_JSON_COMPLETE:
                        Log.e("BuyActivity","开始解析");
                        Log.e("BuyActivity","");
                        movieOrderJson = (String) msg.obj;
                        this.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                parseSelectedSeats();
                                alertDialog.dismiss();
                            }
                        },2000);
                        return;
                    case InTheaterMovieManager.BUY_TICKET:
                        if (((String)msg.obj).equalsIgnoreCase("SUCCESS")) {
                            buySuccess();
                        } else {
                            Toast.makeText(BuyActivity.this, "购买失败,请返回重试", Toast.LENGTH_SHORT).show();
                        }
                        return;
                }

                count = (int) msg.obj;
//                Log.e("BuyActivity",msg.arg1+" "+msg.arg2);
                switch (msg.what) {
                    case MyChooseSeatView.SELECT:
                        seats[msg.arg1][msg.arg2] = 1;
                        break;
                    case MyChooseSeatView.CANCEL:
                        seats[msg.arg1][msg.arg2] = 0;
                        break;
                }
                if (count == 0) {
                    pay.setEnabled(false);
                    pay.setText("选座购买");
                } else {
                    pay.setEnabled(true);
                    pay.setText("提交订单 "+price*(int)msg.obj+" 元");
                }

            }

        };

        init();


//        handlerChooseSeatView = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case InTheaterMovieManager.GET_MOVIE_PRICE_COMPLETE:
//                        price = (int) msg.obj;
////                        alertDialog.dismiss();
//                        break;
//                    case InTheaterMovieManager.GET_CHOOSED_MOVIE_SEATS_JSON_COMPLETE:
//                        break;
//                }
//            }
//        };


        alertDialog = new AlertDialog.Builder(this)
                .setTitle("座位加载中...")
                .setView(new ProgressBar(this))
                .create();
        alertDialog.show();
        startGetPrice(Integer.parseInt(movieId));

    }

    public void startGetPrice(int movieId) {
        inTheaterMovieManager.getMoviePrice(movieId,handlerChooseSeatView);
    }

    /**
     * 讲json解析成已选择的座位
     */
    public void parseSelectedSeats() {
        Log.e("BuyActivity","parseSelectSeats"+movieOrderJson);
        List<MovieOrder> movieOrderList;
        Gson gson = new GsonBuilder().setDateFormat("MM-yyyy-dd").create();
        Log.e("BuyActivity",movieOrderJson);
        movieOrderList = gson.fromJson(movieOrderJson,new TypeToken<ArrayList<MovieOrder>>(){}.getType());
        if (movieOrderList != null) {
            for (MovieOrder movieOrder:movieOrderList) {
                int row = movieOrder.getChoosed_row();
                int column = movieOrder.getChoosed_column();
                seats[row][column] = -1;
            }
        }
        chooseSeatView.setSoldSeats(seats); //更新view的座位信息
    }

    public void buySuccess() {
        appBarText.setText("购买成功");
        chooseSeatView.setVisibility(View.GONE);
        textBuy.setVisibility(View.GONE);
        buyLayout.setVisibility(View.GONE);
        buySuccess.setText(new Date(System.currentTimeMillis()).toString());
        buySuccess.append("\n恭喜购买成功,座位号是:\n");
        for(int i=0; i<rows; i++)
            for (int j=0; j<columns; j++) {
                if (seats[i][j] == 1) {
                    buySuccess.append((i+1)+"排,"+(j+1)+"列\n");
                }
            }
    }

}
