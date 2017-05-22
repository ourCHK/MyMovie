package com.chk.mymovie;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.chk.mymovie.impl.UserManager;

public class ModifyActivity extends AppCompatActivity {

    public static final int MODIFY_NAME = 1;
    public static final int MODIFY_PHONE = 2;
    public static final int MODIFY_PASSWORD = 3;
    public static final int MODIFY_SUCCESS = 4;
    public static final int MODIFY_FAILURE = 5;

    int type;

    TableLayout modifyTableLayout;
    TextView modifyResult;
    EditText oldName;
    EditText newName;
    EditText oldPhone;
    EditText newPhone;
    EditText oldPassword;
    EditText newPassword;

    TableRow oldNameRow;
    TableRow newNameRow;
    TableRow oldPhoneRow;
    TableRow newPhoneRow;
    TableRow oldPasswordRow;
    TableRow newPasswordRow;

    Button post;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String oldNameText;
    String oldPhoneText;
    String oldPasswordText;

    Handler handler;
    UserManager userManager;
    String name;
    String sex;
    String account;
    String password;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        type = getIntent().getIntExtra("type",-1);
        init();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MODIFY_SUCCESS:
                        Toast.makeText(ModifyActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                        storeInfo();
                        modifySuccess();
                        break;
                    case MODIFY_FAILURE:
                        Toast.makeText(ModifyActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    public void init() {
        prefs = getSharedPreferences("MyMovie",MODE_PRIVATE);
        editor = prefs.edit();
        userManager = new UserManager();
        name = prefs.getString("name","");
        sex = prefs.getString("sex","");
        account = prefs.getString("account","");
        password = prefs.getString("password","");
        phone = prefs.getString("phone","");
        initView();
        hideView();
    }

    public void initView() {
        modifyResult = (TextView) findViewById(R.id.modifyResult);
        modifyTableLayout = (TableLayout) findViewById(R.id.modifyTableLayout);
        oldName = (EditText) findViewById(R.id.oldName);
        newName = (EditText) findViewById(R.id.newName);
        oldPhone = (EditText) findViewById(R.id.oldPhone);
        newPhone = (EditText) findViewById(R.id.newPhone);
        oldPassword = (EditText) findViewById(R.id.oldPassword);
        newPassword = (EditText) findViewById(R.id.newPassword);

        oldNameRow = (TableRow) findViewById(R.id.oldNameRow);
        newNameRow = (TableRow) findViewById(R.id.newNameRow);
        oldPhoneRow = (TableRow) findViewById(R.id.oldPhoneRow);
        newPhoneRow = (TableRow) findViewById(R.id.newPhoneRow);
        oldPasswordRow = (TableRow) findViewById(R.id.oldPasswordRow);
        newPasswordRow = (TableRow) findViewById(R.id.newPasswordRow);

        post = (Button) findViewById(R.id.post);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post();
            }
        });
    }

    public void hideView() {
        switch (type) {
            case MODIFY_NAME:
                oldPhoneRow.setVisibility(View.GONE);
                oldPasswordRow.setVisibility(View.GONE);
                newPhoneRow.setVisibility(View.GONE);
                newPasswordRow.setVisibility(View.GONE);
                oldName.setText(prefs.getString("name",""));
                break;
            case MODIFY_PHONE:
                oldNameRow.setVisibility(View.GONE);
                oldPasswordRow.setVisibility(View.GONE);
                newNameRow.setVisibility(View.GONE);
                newPasswordRow.setVisibility(View.GONE);
                oldPhone.setText(prefs.getString("phone",""));
                break;
            case MODIFY_PASSWORD:
                oldPhoneRow.setVisibility(View.GONE);
                oldNameRow.setVisibility(View.GONE);
                newPhoneRow.setVisibility(View.GONE);
                newNameRow.setVisibility(View.GONE);
                break;
        }
    }

    private void post() {

        switch (type) {
            case MODIFY_NAME:
                if (newName.getText().toString().isEmpty()) {
                    Toast.makeText(this, "请将信息填写完整", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (name.equals(newName.getText().toString())) {
                    Toast.makeText(this, "新昵称与旧昵称一致，请重新输入.", Toast.LENGTH_SHORT).show();
                    return;
                }
                name = newName.getText().toString();
                break;
            case MODIFY_PHONE:
                if (newPhone.getText().toString().isEmpty()) {
                    Toast.makeText(this, "请将信息填写完整", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.equals(newPhone.getText().toString())) {
                    Toast.makeText(this, "新手机号码与旧手机号码一致，请重新输入.", Toast.LENGTH_SHORT).show();
                    return;
                }
                phone = newPhone.getText().toString();
                break;
            case MODIFY_PASSWORD:
                if (newPassword.getText().toString().isEmpty() || oldPassword.getText().toString().isEmpty()) {
                    Toast.makeText(this, "请将信息填写完整", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(oldPassword.getText().toString())) {
                    Toast.makeText(this, "旧密码输入错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.equals(newPassword.getText().toString())) {
                    Toast.makeText(this, "新旧密码一致，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                password = newPassword.getText().toString();
                break;
        }
        userManager.updateUser(name,sex,account,password,phone,handler);
    }

    public void storeInfo() {
        editor.putString("name",name);
        editor.putString("phone",phone);
        editor.putString("account",account);
        editor.putString("password",password);
        editor.putString("sex",sex);
        editor.commit();
    }

    public void modifySuccess() {
        switch (type) {
            case MODIFY_NAME:
                modifyResult.append("\n新昵称："+name);
                break;
            case MODIFY_PHONE:
                modifyResult.append("\n新手机号码："+phone);
                break;
            case MODIFY_PASSWORD:
                modifyResult.setText("密码修改成功！");
                break;
        }
        modifyTableLayout.setVisibility(View.GONE);
    }
}
