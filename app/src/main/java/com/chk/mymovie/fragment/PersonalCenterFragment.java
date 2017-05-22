package com.chk.mymovie.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.chk.mymovie.LoginActivity;
import com.chk.mymovie.ModifyActivity;
import com.chk.mymovie.OrderActivity;
import com.chk.mymovie.R;
import com.chk.mymovie.myview.MyTextView;
import com.chk.mymovie.myview.MyTextView2;

/**
 * Created by chk on 17-3-13.
 * 个人中心的fragment
 */

public class PersonalCenterFragment extends Fragment {

    View view;
    TextView userName;
    TextView userPhone;
    TextView userPassword;
    TableRow modifyUserName;
    TableRow modifyUserPhone;
    TableRow modifyUserPassword;
    TableRow myOrder;
    TableRow loginOut;
    TextView myName;


    SharedPreferences prefs;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fragment_personal_center,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void init() {
        prefs = getActivity().getSharedPreferences("MyMovie",Context.MODE_PRIVATE);
        initView();
        load();
    }

    public void initView() {
        myName = (TextView) view.findViewById(R.id.myName);
        userName = (TextView) view.findViewById(R.id.userName);
        userPhone = (TextView) view.findViewById(R.id.userPhone);

        modifyUserName = (TableRow) view.findViewById(R.id.modifyUserName);
        modifyUserPhone = (TableRow) view.findViewById(R.id.modifyUserPhone);
        modifyUserPassword = (TableRow) view.findViewById(R.id.modifyUserPassword);
        myOrder= (TableRow) view.findViewById(R.id.myOrder);
        loginOut = (TableRow) view.findViewById(R.id.loginOut);

        modifyUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ModifyActivity.class);
                intent.putExtra("type",ModifyActivity.MODIFY_NAME);
                intent.putExtra("oldName",prefs.getString("name",""));
                getActivity().startActivity(intent);
            }
        });
        modifyUserPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ModifyActivity.class);
                intent.putExtra("type",ModifyActivity.MODIFY_PHONE);
                intent.putExtra("oldPhone",prefs.getString("oldPhone",""));
                getActivity().startActivity(intent);
            }
        });
        modifyUserPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ModifyActivity.class);
                intent.putExtra("type",ModifyActivity.MODIFY_PASSWORD);
                intent.putExtra("oldPassword",prefs.getString("oldPassword",""));
                getActivity().startActivity(intent);
            }
        });

        myOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                getActivity().startActivity(intent);
            }
        });

        loginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
    }

    public void load() {
        myName.setText(prefs.getString("name","昵称"));
        userName.setText(prefs.getString("name","昵称"));
        userPhone.setText(prefs.getString("phone","手机号"));
    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }
}
