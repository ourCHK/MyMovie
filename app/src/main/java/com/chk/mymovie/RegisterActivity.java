package com.chk.mymovie;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.chk.mymovie.impl.UserManager;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    /**
     * 网络错误
     */
    public static final int NETWORK_ERROR = 0;

    /**
     * 注册成功
     */
    public static final int  SUCCESS_REGISTER = 1;

    /**
     * 注册失败
     */
    public static final int FAILURE_REGISTER = 2;

    EditText name;
    RadioGroup chooseSex;
    RadioButton chooseWhat;
    RadioButton chooseMan;
    EditText account;
    EditText password;
    EditText passwordRe;
    EditText phone;
    Button register;
    String accountContent;
    String passwordContent;
    String nameContent;
    String phoneContent;
    Handler handler;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case NETWORK_ERROR:
                        Toast.makeText(RegisterActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                        break;
                    case SUCCESS_REGISTER:
                        Toast.makeText(RegisterActivity.this, "注册成功,返回登录！", Toast.LENGTH_SHORT).show();
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent();
                                intent.putExtra("name",nameContent);
                                intent.putExtra("phone",phoneContent);
                                intent.putExtra("account",accountContent);
                                intent.putExtra("password",passwordContent);
                                setResult(LoginActivity.REGISTER_SUCCESS,intent);
                                finish();
                            }
                        },2000);
                        break;
                    case FAILURE_REGISTER:     //注册失败的情况,比如账号和昵称与服务器重复了
                        break;
                }
            }
        };
    }


    public void init() {
        name = (EditText) findViewById(R.id.name);
        account = (EditText) findViewById(R.id.accountReg);
        password = (EditText) findViewById(R.id.passwordReg);
        passwordRe = (EditText) findViewById(R.id.passwordRegRe);
        phone = (EditText) findViewById(R.id.phone);
        chooseMan = (RadioButton) findViewById(R.id.chooseMan);
        chooseMan.setChecked(true);
        chooseWhat = chooseMan;
        chooseSex = (RadioGroup) findViewById(R.id.chooseSex);
        chooseSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                chooseWhat = (RadioButton) findViewById(checkedId);
                Log.e(TAG,chooseWhat.getText().toString());
            }
        });

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    public void register() {
        nameContent = name.getText().toString();
        String sexContent = chooseWhat.getText().toString();
        accountContent = account.getText().toString();
        passwordContent = password.getText().toString();
        String passwordReContent = passwordRe.getText().toString();
        phoneContent = phone.getText().toString();
        if (nameContent.isEmpty() ||
                sexContent.isEmpty() ||
                accountContent.isEmpty() ||
                passwordContent.isEmpty() ||
                passwordReContent.isEmpty()||
                phoneContent.isEmpty()) {
            Toast.makeText(this, "请将信息填写完整", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!passwordContent.equals(passwordReContent)) {
            Toast.makeText(this, "两次密码不一致！", Toast.LENGTH_SHORT).show();
            return;
        }
        UserManager userManager = new UserManager();
        userManager.registerUser(nameContent,sexContent,accountContent,passwordContent,phoneContent,handler);
    }
}
