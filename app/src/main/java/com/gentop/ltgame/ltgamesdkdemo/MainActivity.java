package com.gentop.ltgame.ltgamesdkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    Button mBtnStart, mBtnGoogle,mBtnOne,mBtnUI,mBtnPhone,mBtnQQ,mBtnFacebook;
    TextView mTxtResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTxtResult = findViewById(R.id.txt_result);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GooglePlayActivity.class));

            }
        });
        mBtnGoogle = findViewById(R.id.btn_google);
        mBtnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GoogleActivity.class));

            }
        });
        mBtnOne = findViewById(R.id.btn_one);
        mBtnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OneStoreActivity.class));

            }
        });
        mBtnUI = findViewById(R.id.btn_ui);
        mBtnUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, UIActivity.class));

            }
        });
        mBtnPhone = findViewById(R.id.btn_phone);
        mBtnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PhoneActivity.class));

            }
        });
        mBtnFacebook = findViewById(R.id.btn_facebook);
        mBtnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FacebookActivity.class));

            }
        });
        mBtnQQ = findViewById(R.id.btn_qq);
        mBtnQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QQActivity.class));

            }
        });
    }




}