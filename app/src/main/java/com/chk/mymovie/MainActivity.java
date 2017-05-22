package com.chk.mymovie;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chk.mymovie.fragment.SearchFragment;
import com.chk.mymovie.fragment.MovieFragment;
import com.chk.mymovie.fragment.PersonalCenterFragment;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = "MainActivity";
    private final static String MOVIE_TAG = "MovieFragment";
    private final static String CONTENT_TAG = "SearchFragment";
    private final static String PERSONAL_CENTER_TAG = "PersonalCenterFragment";


    AppBarLayout appBarLayout;
    Toolbar toolbar;
    FrameLayout frameLayout;
    CoordinatorLayout coordinatorLayout;
    RadioGroup radioGroup;
    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;

    FragmentManager fm;
    FragmentTransaction ft;
    MovieFragment movieFragment;
    SearchFragment contentFragment;
    PersonalCenterFragment personalCenterFragment;
    int currPage;   //标记当前页面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        toolbarInit();
        fragmentInit();
        widgetInit();
    }

    private void toolbarInit() {
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
    }



    private void fragmentInit() {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        movieFragment = new MovieFragment();
        contentFragment = new SearchFragment();
        personalCenterFragment = new PersonalCenterFragment();
        ft.add(R.id.frameLayout2,personalCenterFragment,PERSONAL_CENTER_TAG);
        ft.add(R.id.frameLayout2,contentFragment,CONTENT_TAG);
        ft.add(R.id.frameLayout2,movieFragment,MOVIE_TAG);
        ft.hide(contentFragment);
        ft.hide(personalCenterFragment);
        ft.commit();
    }

    private void showFragment(String fragmentTAG) {
        FragmentTransaction ft = fm.beginTransaction();
        MovieFragment movieFragment = (MovieFragment) fm.findFragmentByTag(MOVIE_TAG);
        SearchFragment contentFragment = (SearchFragment) fm.findFragmentByTag(CONTENT_TAG);
        PersonalCenterFragment personalCenterFragment = (PersonalCenterFragment) fm.findFragmentByTag(PERSONAL_CENTER_TAG);
        if (movieFragment != null)
            ft.hide(movieFragment);
        if (contentFragment != null)
            ft.hide(contentFragment);
        if (personalCenterFragment != null);
            ft.hide(personalCenterFragment);
        switch (fragmentTAG) {
            case MOVIE_TAG:
                ft.show(movieFragment);
                break;
            case CONTENT_TAG:
                ft.show(contentFragment);
                break;
            case PERSONAL_CENTER_TAG:
                ft.show(personalCenterFragment);
                break;
        }
        ft.commit();
    }

    private void widgetInit() {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.frameLayout);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout2);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupTest);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rb3 = (RadioButton) findViewById(R.id.rb3);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.rb1:
                        currPage = 1;
                        setToolbar();
                        showFragment(MOVIE_TAG);
                        break;
                    case R.id.rb2:
                        currPage = 2;
                        setToolbar();
                        showFragment(CONTENT_TAG);
                        break;
                    case R.id.rb3:
                        currPage = 3;
                        setToolbar();
                        showFragment(PERSONAL_CENTER_TAG);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 设置在某些页面不显示toolbar
     */
    void setToolbar() {
        if (currPage == 3 && appBarLayout.getTop() == 0) {
            toolbar.setVisibility(View.GONE);
        } else if (currPage == 2 && appBarLayout.getTop() == 0) {
            toolbar.setVisibility(View.GONE);
        } else if (currPage == 1) {
            toolbar.setVisibility(View.VISIBLE);
        }

    }

}
