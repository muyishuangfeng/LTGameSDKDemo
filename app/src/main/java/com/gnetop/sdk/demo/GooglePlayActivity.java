package com.gnetop.sdk.demo;

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
import com.sdk.ltgame.ltgoogleplay.GooglePlayPlatform;

import java.util.Map;
import java.util.WeakHashMap;

public class GooglePlayActivity extends AppCompatActivity {

    Button mBtnPay;
    TextView mTxtResult;
    private static final String TAG = GooglePlayActivity.class.getSimpleName();
    String base64EncodedPublicKey;
    String LTAppKey = "q2h75rE8MW3fOVed82muf5w8dkBfXiSG";
    String LTAppID = "20003";
//    String LTAppKey = "MJwk6bLlpGErRgLKkJPLP7VavHRGvTpA";
//    String LTAppID = "28576";

    String packageName = "com.gnetop.sdk.demo";
    private static final int selfRequestCode = 0x01;
    private String mGoodsID="33";
   // private String mGoodsID="138";
    String productID = "com.gnetop.one";
    Map<String, Object> params = new WeakHashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_play);
        initView();
    }

    private void initView() {

        base64EncodedPublicKey = getResources().getString(R.string.ltgame_google_iab_key);
        params.put("key", "123");
        mTxtResult = findViewById(R.id.txt_result);
        mBtnPay = findViewById(R.id.btn_pay);
        mBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                RechargeObject result = new RechargeObject();
                result.setLTAppID(LTAppID);
                result.setLTAppKey(LTAppKey);
                result.setSku(productID);
                result.setGoodsID(mGoodsID);
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
                .isServerTest(true)
                .setParams(params)
                .payTest(0)
                .goodsID(productID, mGoodsID)
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
