package com.chk.mymovie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.chk.mymovie.bean.User;
import com.chk.mymovie.impl.UserManager;
import com.google.gson.Gson;

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

    /**
     * 活动启动码
     */
    public static final int REQUEST_CODE = 17;

    /**
     * 成功注册结果码
     */
    public static final int REGISTER_SUCCESS = 17;


    Button openReg;
    Button login;
    CheckBox remember;
    EditText account;
    EditText password;
    ProgressDialog pd;
    Handler handler;
    User user;
    String accountContent;
    String passwordContent;
    String nameContent;
    String phoneContent;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    boolean isRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case NETWORK_ERROR:
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this,"网络错误！",Toast.LENGTH_SHORT).show();
                        break;
                    case SUCCESS_LOGIN:
                        jsonToUser((String) msg.obj);
                        storeLogin();   //界面结束时存储信息
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                                Intent intentLogin = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intentLogin);
                                finish();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void init() {

        pref = getSharedPreferences("MyMovie",Context.MODE_PRIVATE);
        editor = pref.edit();

        openReg = (Button) findViewById(R.id.openReg);
        login = (Button) findViewById(R.id.login);
        remember = (CheckBox) findViewById(R.id.autoLogin);
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        openReg.setOnClickListener(this);
        login.setOnClickListener(this);
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isRemember = b;
            }
        });
        pd = new ProgressDialog(this);
        pd.setTitle("请稍等");
        pd.setMessage("正在登录中...");

        restoreLogin();

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.openReg:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
                break;
            case R.id.login:
                login();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       switch (resultCode) {
           case REGISTER_SUCCESS:
               account.setText(data.getStringExtra("account"));
               password.setText(data.getStringExtra("password"));
               break;
           default:
               break;
       }
    }

    /**
     * 登录
     */
    public void login () {
        accountContent = account.getText().toString();
        passwordContent = password.getText().toString();
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

    /**
     * 存储登录信息
     */
    public void storeLogin() {
        editor.putInt("id",user.getId());
        editor.putString("name",user.getName());
        editor.putString("phone",user.getPhone());
        editor.putString("account",user.getAccount());
        editor.putString("password",user.getPassword());
        editor.putString("sex",user.getSex());
        editor.putBoolean("isRemember",isRemember);
        editor.commit();
        Log.e("LoginActivioty",user.getAccount()+" "+user.getName()+" "+user.getPhone()+" "+user.getPassword());
    }

    /**
     * 恢复登录信息
     */
    public void restoreLogin() {
        isRemember = pref.getBoolean("isRemember",false);
        if (isRemember) {
            accountContent = pref.getString("account","");
            passwordContent = pref.getString("password","");
            account.setText(accountContent);
            password.setText(passwordContent);
            remember.setChecked(isRemember);
        }

    }

    public void jsonToUser(String json) {
        user = new User();
        Gson gson = new Gson();
        user = gson.fromJson(json,User.class);
    }
}
