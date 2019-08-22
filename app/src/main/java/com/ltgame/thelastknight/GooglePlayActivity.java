package com.ltgame.thelastknight;

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
import com.gentop.ltgame.ltgamesdkcore.exception.LTGameError;
import com.gentop.ltgame.ltgamesdkcore.impl.OnRechargeListener;
import com.gentop.ltgame.ltgamesdkcore.manager.RechargeManager;
import com.gentop.ltgame.ltgamesdkcore.model.LoginResult;
import com.gentop.ltgame.ltgamesdkcore.model.RechargeObject;
import com.gentop.ltgame.ltgamesdkcore.model.RechargeResult;
import com.sdk.ltgame.ltgoogleplay.GooglePlayPlatform;

import java.util.Map;
import java.util.WeakHashMap;

public class GooglePlayActivity extends AppCompatActivity {

    Button mBtnPay;
    TextView mTxtResult;
    private static final String TAG = GooglePlayActivity.class.getSimpleName();

    // String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAz2MGvRWyaE5sKrj91NRItsRFGvuyu9EIghr4pjCUVlyq297KvUhr3+3dVrvRk8yAqo5nWNkHkhnuzmMjS7k4xhybweVHVG3rgLLaP9FT9UpUSVkAv6rzvYD3oeV1GL1/iGMWyd1t6CCedi4fjNgy8CB7R0VX1u8SsL502qw84XH2e+che4eotjJp9tAekQD3Bo9XjBD0Rmq8xVEU/kJRhfba/CLNOOS1g72x5RcPQdAsvAMbpkpdI2ZcHUyLXtM+qVZ0fusSUmQtZudagx3ZAnmywEWp8ovfOXO+mhAOa3tBgdsYHrj0maJ+w+15PkqzlDdbeWjP9vyLU+cF0w9Z7QIDAQAB";
    String base64EncodedPublicKey;
    String LTAppKey = "ATGhGUQ3VbptAf5qq544njqBAK2TGKdz";
    String LTAppID = "28571";
    //    String LTAppKey = "f8XkF2vVDMh4BWxAayD0YOIl0C2QVEaW";
//    String LTAppID = "20001";
    String packageName = "com.ltgame.thelastknight";
    private static final int selfRequestCode = 0x01;
    String productID = "com.ltgame.thelastknight.p01";
    String baseUrl = "http://login.gdpgold.com";
    Map<String, Object> params = new WeakHashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_play);
        initView();
        init();
    }

    private void initView() {
        base64EncodedPublicKey = getResources().getString(R.string.ltgame_google_iab_key);
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
                result.setGoodsID("56");
                result.setPublicKey(base64EncodedPublicKey);
                result.setmPackageID(packageName);
                result.setParams(params);
                result.setPayTest(0);
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
                .payTest(0)
                .goodsID(productID, "56")
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
                    Log.e(TAG, "支付失败");
                    break;
            }
        }
    };
}
