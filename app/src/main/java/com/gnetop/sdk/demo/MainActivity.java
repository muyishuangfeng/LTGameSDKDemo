package com.gnetop.sdk.demo;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gentop.ltgame.ltgamesdkcore.common.LTGameOptions;
import com.gentop.ltgame.ltgamesdkcore.common.LTGameSdk;
import com.sdk.ltgame.ltgoogleplay.GooglePlayHelper;
import com.sdk.ltgame.ltnet.base.Constants;

import java.util.Map;
import java.util.WeakHashMap;


public class MainActivity extends AppCompatActivity {

    Button mBtnStart, mBtnGoogle, mBtnOne, mBtnUI, mBtnPhone, mBtnQQ, mBtnFacebook, mBtnGuest;
    TextView mTxtResult;
    NetResultReceiver mReceiver;
    String base64EncodedPublicKey;
    String LTAppKey = "q2h75rE8MW3fOVed82muf5w8dkBfXiSG";
    String LTAppID = "20003";
    String packageName = "com.gnetop.sdk.demo";
    private static final int selfRequestCode = 0x01;
    private String mGoodsID="33";
    String productID = "com.gnetop.one";
    Map<String, Object> params = new WeakHashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
    }

    private void initView() {
        base64EncodedPublicKey = getResources().getString(R.string.ltgame_google_iab_key);
        IntentFilter filter = new IntentFilter(Constants.MSG_SEND_EXCEPTION);
        mReceiver = new NetResultReceiver();
        registerReceiver(mReceiver, filter);

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
                startActivity(new Intent(MainActivity.this, UIActivity.class));

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
        mBtnGuest = findViewById(R.id.btn_guest);
        mBtnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GuestActivity.class));

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }


    /**
     * 补单
     */
    private void init(){
        LTGameOptions options = new LTGameOptions.Builder(this)
                .debug(false)
                .appID(LTAppID)
                .appKey(LTAppKey)
                .publicKey(base64EncodedPublicKey)
                .isServerTest(true)
                .setParams(params)
                .payTest(0)
                .goodsID(productID, mGoodsID)
                .packageID(packageName)
                .googlePlay(true)
                .requestCode(selfRequestCode)
                .build();
        LTGameSdk.init(options);
        GooglePlayHelper mHelper=new GooglePlayHelper(this,base64EncodedPublicKey);
        mHelper.queryOrder();
    }
}
