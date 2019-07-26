package com.gentop.ltgame.ltgamesdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.gentop.ltgame.ltgamesdkcore.common.LTGameOptions;
import com.gentop.ltgame.ltgamesdkcore.common.LTGameSdk;
import com.gentop.ltgame.ltgamesdkcore.common.Target;
import com.gentop.ltgame.ltgamesdkcore.impl.OnRechargeListener;
import com.gentop.ltgame.ltgamesdkcore.manager.RechargeManager;
import com.gentop.ltgame.ltgamesdkcore.model.RechargeObject;
import com.gentop.ltgame.ltgamesdkcore.model.RechargeResult;

import java.util.Map;
import java.util.WeakHashMap;

public class GooglePlayActivity extends AppCompatActivity {

    Button mBtnPay;
    TextView mTxtResult;
    private static final String TAG = GooglePlayActivity.class.getSimpleName();

    String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAz2MGvRWyaE5sKrj91NRItsRFGvuyu9EIghr4pjCUVlyq297KvUhr3+3dVrvRk8yAqo5nWNkHkhnuzmMjS7k4xhybweVHVG3rgLLaP9FT9UpUSVkAv6rzvYD3oeV1GL1/iGMWyd1t6CCedi4fjNgy8CB7R0VX1u8SsL502qw84XH2e+che4eotjJp9tAekQD3Bo9XjBD0Rmq8xVEU/kJRhfba/CLNOOS1g72x5RcPQdAsvAMbpkpdI2ZcHUyLXtM+qVZ0fusSUmQtZudagx3ZAnmywEWp8ovfOXO+mhAOa3tBgdsYHrj0maJ+w+15PkqzlDdbeWjP9vyLU+cF0w9Z7QIDAQAB";
    String LTAppKey = "f8XkF2vVDMh4BWxAayD0YOIl0C2QVEaW";
    String LTAppID = "20001";
    String packageName = "com.ltgames.yyjw.google";
    private static final int selfRequestCode = 0x01;
    String productID = "com.ltgamesyyjw.5500d";
    String baseUrl = "http://sdk.aktgo.com";
    Map<String, Object> params = new WeakHashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_play);
        initView();
        init();
    }

    private void initView() {
        params.put("key", "123");
        mTxtResult = findViewById(R.id.txt_result);
        mBtnPay = findViewById(R.id.btn_pay);
        mBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeObject result = new RechargeObject();
                result.setBaseUrl(baseUrl);
                result.setLTAppID(LTAppID);
                result.setLTAppKey(LTAppKey);
                result.setSku(productID);
                result.setGoodsID("16");
                result.setPublicKey(base64EncodedPublicKey);
                result.setmPackageID(packageName);
                result.setParams(params);
                result.setPayTest(1);

                RechargeManager.recharge(GooglePlayActivity.this, Target.RECHARGE_GOOGLE,
                        result, mOnRechargeListener);
            }
        });
    }


    private void init() {
        LTGameOptions options = new LTGameOptions.Builder(this)
                .debug(true)
                .appID(LTAppID)
                .appKey(LTAppKey)
                .publicKey(base64EncodedPublicKey)
                .baseUrl(baseUrl)
                .setParams(params)
                .payTest(1)
                .goodsID(productID, "16")
                .packageID(packageName)
                .googlePlay(true)
                .requestCode(selfRequestCode)
                .build();
        LTGameSdk.init(options);
    }

    OnRechargeListener mOnRechargeListener = new OnRechargeListener() {
        @Override
        public void onState(Activity activity, RechargeResult result) {
            switch (result.state) {
                case RechargeResult.STATE_RECHARGE_SUCCESS:
                    mTxtResult.setText(result.getResultModel().getCode() + "======");
                    break;
                case RechargeResult.STATE_RECHARGE_START:
                    Log.e(TAG, "开始支付");
                    break;
                case RechargeResult.STATE_RECHARGE_FAILED:
                    Log.e(TAG, "支付错误" + result.getErrorMsg());
                    break;
            }
        }

    };
}
