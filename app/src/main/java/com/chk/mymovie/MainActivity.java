package com.chk.mymovie;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chk.mymovie.fragment.ContentFragment;
import com.chk.mymovie.fragment.MovieFragment;
import com.chk.mymovie.fragment.PersonalCenterFragment;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = "MainActivity";
    private final static String MOVIE_TAG = "MovieFragment";
    private final static String CONTENT_TAG = "ContentFragment";
    private final static String PERSONAL_CENTER_TAG = "PersonalCenterFragment";

    Toolbar toolbar;
    FrameLayout frameLayout;
    RadioGroup radioGroup;
    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;

    FragmentManager fm;
    FragmentTransaction ft;
    MovieFragment movieFragment;
    ContentFragment contentFragment;
    PersonalCenterFragment personalCenterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        fragmentInit();
        widgetInit();
    }

    private void fragmentInit() {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        movieFragment = new MovieFragment();
        contentFragment = new ContentFragment();
        personalCenterFragment = new PersonalCenterFragment();
        ft.add(R.id.frameLayout,personalCenterFragment,PERSONAL_CENTER_TAG);
        ft.add(R.id.frameLayout,contentFragment,CONTENT_TAG);
        ft.add(R.id.frameLayout,movieFragment,MOVIE_TAG);
        ft.hide(contentFragment);
        ft.hide(personalCenterFragment);
        ft.commit();
    }

    private void showFragment(String fragmentTAG) {
        FragmentTransaction ft = fm.beginTransaction();
        MovieFragment movieFragment = (MovieFragment) fm.findFragmentByTag(MOVIE_TAG);
        ContentFragment contentFragment = (ContentFragment) fm.findFragmentByTag(CONTENT_TAG);
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
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
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
                        showFragment(MOVIE_TAG);
                        Log.i(TAG,"click rb1");
                        break;
                    case R.id.rb2:
                        Log.i(TAG,"click rb2");
                        showFragment(CONTENT_TAG);
                        break;
                    case R.id.rb3:
                        showFragment(PERSONAL_CENTER_TAG);
                        Log.i(TAG,"click rb3");
                        break;
                    default:
                        break;
                }
            }
        });
    }



}
