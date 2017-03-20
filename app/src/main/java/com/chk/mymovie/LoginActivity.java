package com.chk.mymovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "LoginActivity";

    Button openReg;
    Button login;
    CheckBox autoLog;
    EditText account;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        openReg = (Button) findViewById(R.id.openReg);
        login = (Button) findViewById(R.id.login);
        autoLog = (CheckBox) findViewById(R.id.autoLogin);
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        openReg.setOnClickListener(this);
        login.setOnClickListener(this);

        autoLog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.e(TAG,b+"");
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.openReg:
                OkHttpClient mOkHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8080/MyMovieService/RegisterServlet").build();
                mOkHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG,"error:"+e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e(TAG,"authSuccess");
                        Intent intentMain = new Intent(LoginActivity.this,RegisterActivity.class);
                        startActivity(intentMain);
                    }
                });




                break;
            case R.id.login:
                Intent intentRegister = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intentRegister);
                break;
        }
    }
}
