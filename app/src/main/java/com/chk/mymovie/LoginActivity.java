package com.chk.mymovie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.chk.mymovie.impl.UserManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "LoginActivity";

    /**
     * 网络错误
     */
    public static final int NETWORK_ERROR = 0;

    /**
     * 登录成功
     */
    public static final int  SUCCESS_LOGIN = 1;

    /**
     * 登录失败
     */
    public static final int FAILURE_LOGIN = 2;

    ProgressDialog pd;

    Button openReg;
    Button login;
    CheckBox autoLog;
    EditText account;
    EditText password;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case NETWORK_ERROR:
                        break;
                    case SUCCESS_LOGIN:
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                Intent intentLogin = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intentLogin);
                            }
                        },1000);
                        break;
                    case FAILURE_LOGIN:
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                Toast.makeText(LoginActivity.this,"登录失败,账号密码有误！",Toast.LENGTH_SHORT).show();
                            }
                        },3000);
                        break;
                }
            }
        };
    }

    public void init() {
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

        pd = new ProgressDialog(this);
        pd.setTitle("请稍等");
        pd.setMessage("正在登录中...");
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
                login();
                break;
        }
    }

    /**
     * 登录
     */
    public void login () {
        String accountContent = account.getText().toString();
        String passwordContent = password.getText().toString();
        if (accountContent.isEmpty()) {
            Toast.makeText(this, "请将账号密码填写完整", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordContent.isEmpty()) {
            Toast.makeText(this, "请将账号密码填写完整", Toast.LENGTH_SHORT).show();
            return;
        }
        pd.show();
        UserManager userManager = new UserManager();
        userManager.loginUser(accountContent,passwordContent,handler);

    }
}
