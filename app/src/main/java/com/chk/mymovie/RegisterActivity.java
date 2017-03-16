package com.chk.mymovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RegisterActivity extends AppCompatActivity {

    EditText name;
    RadioGroup chooseSex;
    RadioButton chooseBoy;
    RadioButton chooseGirl;
    EditText account;
    EditText password;
    EditText passwordRe;
    EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    public void init() {
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        passwordRe = (EditText) findViewById(R.id.passwordRe);
        phone = (EditText) findViewById(R.id.phone);


    }
}
