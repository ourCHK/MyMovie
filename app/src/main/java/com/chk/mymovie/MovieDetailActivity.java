package com.chk.mymovie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chk.mymovie.application.MyApplication;
import com.chk.mymovie.bean.MovieDetail;
import com.chk.mymovie.tools.OKHttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieDetailActivity extends AppCompatActivity {
    public final static String TAG = "MovieDetailActivityBefore";
    public final static int MOVIE_IN_THEATER = 1;
    public final static int MOVIE_COMING_SOON = 2;
    public final static int GET_JSON_SUCCESS = 3;
    public final static int GET_JSON_FAILURE = 4;
    public final static int PARSE_JSON_COMPLETE = 5;

    String genymotionIp = "http://192.168.56.1:8080";
    String nativeIp = "http://10.0.2.2:8080";
    String outerIp = "http://18.8.6.109:8080";
    String chooseIp =  MyApplication.getContext().getString(R.string.choosedIp);


    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;

    ImageView moviePic;
    TextView movieYearView;
    TextView movieRatingView;
    TextView movieTypeView;
    TextView movieCastsView;
    TextView movieDirectorsView;
    TextView movieSummaryView;
    Button buy;
    Button lookMore;
    int movieType;

    MovieDetail movieDetail;
    int movieId;
    String movieDetailJson;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case GET_JSON_SUCCESS:
                        Toast.makeText(MovieDetailActivity.this,"获取json成功",Toast.LENGTH_SHORT).show();
                        parseMovieDetailJson();
                        break;
                    case  GET_JSON_FAILURE:
                        Toast.makeText(MovieDetailActivity.this,"获取json失败",Toast.LENGTH_SHORT).show();
                        break;
                    case PARSE_JSON_COMPLETE:
                        mappingData();
                        break;
                }
            }
        };
        init();
        startGetMovieDetailJson(movieId);

    }

    private void init() {
        Intent intent = getIntent();
        movieType = intent.getIntExtra("movieType",-1);
        movieId  =intent.getIntExtra("movieId",-1);
        moviePic = (ImageView) findViewById(R.id.moviePic);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_tool_bar);
        movieYearView = (TextView) findViewById(R.id.movieYear);
        movieRatingView = (TextView) findViewById(R.id.movieRating);
        movieTypeView = (TextView) findViewById(R.id.movieType);
        movieCastsView = (TextView) findViewById(R.id.movieCasts);
        movieDirectorsView = (TextView) findViewById(R.id.movieDirectors);
        movieSummaryView = (TextView) findViewById(R.id.movieSummary);
        buy = (Button) findViewById(R.id.buyTicket);
        lookMore = (Button) findViewById(R.id.lookMore);

        lookMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(movieDetail.getMobile_url());
                intent.setData(content_url);
                startActivity(intent);
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetailActivity.this,BuyActivity.class);
                intent.putExtra("data",movieDetail.getId()+" "+movieDetail.getTitle());
                startActivity(intent);
            }
        });

        actionBarInit();
//        movieSummaryView = (TextView) findViewById(R.id.movieSummaryView);
//        for(int i=0; i<50; i++) {
//            movieSummaryView.append("123123124732478321647213413241234");
//        }


    }

    public void actionBarInit() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(movie.getName());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 返回键点击事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }


    public void startGetMovieDetailJson(int movieId) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("movieId",movieId+"");
        OKHttpUtil.getRequest(chooseIp + "/MyMovieService/GetMovieDetailServlet", hashMap, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(GET_JSON_FAILURE);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                movieDetailJson = response.body().string();
                handler.sendEmptyMessage(GET_JSON_SUCCESS);
            }
        });
    }

    public void parseMovieDetailJson() {
        Gson gson = new Gson();
        movieDetail = gson.fromJson(movieDetailJson,MovieDetail.class);
        handler.sendEmptyMessage(PARSE_JSON_COMPLETE);
    }

    /**
     * 映射数据
     */
    public void mappingData() {
        collapsingToolbarLayout.setTitle(movieDetail.getTitle());
        movieYearView.setText("年代:"+movieDetail.getYear());
        movieRatingView.setText("评分:"+movieDetail.getRating());
        movieTypeView.setText("影片类型:"+movieDetail.getGenres());
        movieCastsView.setText("演员: "+movieDetail.getCasts());
        movieDirectorsView.setText("导演: "+movieDetail.getDirectors());
        movieSummaryView.setText("影片简介: "+movieDetail.getSummary());
        if (movieType == MOVIE_COMING_SOON) {
            buy.setVisibility(View.GONE);
        }
        Glide.with(this).load(chooseIp + "/MyMovieService/GetPicServlet?path="+movieDetail.getImage_path()).into(moviePic);
    }
}
