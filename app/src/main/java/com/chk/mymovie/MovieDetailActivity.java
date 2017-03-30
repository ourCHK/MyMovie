package com.chk.mymovie;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chk.mymovie.bean.Movie;

public class MovieDetailActivity extends AppCompatActivity {
    public final static String TAG = "MovieDetailActivity";
    String genymotionIp = "http://192.168.56.1:8080";
    String nativeIp = "http://10.0.2.2:8080";
    String outerIp = "http://18.8.6.109:8080";
    String chooseIp = nativeIp;


    Toolbar toolbar;
    TextView test;
    ImageView moviePic;
    Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        movie = intent.getParcelableExtra("movie");
        moviePic = (ImageView) findViewById(R.id.moviePic);
        actionBarInit();
        Glide.with(this).load(chooseIp + "/MyMovieService/GetPicServlet?path="+movie.getPath()).into(moviePic);
        test = (TextView) findViewById(R.id.test);
        for(int i=0; i<50; i++) {
            test.append("123123124732478321647213413241234");
        }

    }

    public void actionBarInit() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(movie.getName());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
