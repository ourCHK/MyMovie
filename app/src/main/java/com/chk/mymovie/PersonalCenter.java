package com.chk.mymovie;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PersonalCenter extends AppCompatActivity {

    TextView userName;
    TextView userPhone;
    TextView userPassword;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_personal_center);
        prefs = getSharedPreferences("MyMovie",MODE_PRIVATE);
        initView();
        load();

    }

    public void initView() {
        userName = (TextView) findViewById(R.id.userName);
        userPhone = (TextView) findViewById(R.id.userPhone);
    }

    public void load() {
        userName.setText(prefs.getString("name","昵称"));
        userPhone.setText(prefs.getString("phone","手机号"));
    };
}
