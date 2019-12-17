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

import java.util.Map;
import java.util.WeakHashMap;

public class OneStoreActivity extends AppCompatActivity {

    Button mBtnPay;
    TextView mTxtResult;
    //String baseUrl = "http://sdk.aktgo.com";
    Map<String, Object> params = new WeakHashMap<>();
    String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCu9RPDbvVqM8XWqVc75JXccIXN1VS8XViRZzATUq62kkFIXCeo52LKzBCh3iWFQIvX3jqDhim4ESqHMezEx8CxaTq8NpNoQXutBNmOEl+/7HTUsZxI93wgn9+7pFMyoFlasqmVjCcM7zbbAx5G0bySsm98TFxTu16OGmO01JGonQIDAQAB";
    String LTAppKey = "f8XkF2vVDMh4BWxAayD0YOIl0C2QVEaW";
    String LTAppID = "20001";
    String packageName = "com.ltgames.yyjw.one";
    private static final int selfRequestCode = 0x01;
    String productID = "com.ltgamesyyjw.lslb1";
    private static final String TAG = OneStoreActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_store);
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
                //result.setBaseUrl(baseUrl);
                result.setLTAppID(LTAppID);
                result.setLTAppKey(LTAppKey);
                result.setSku(productID);
                result.setGoodsID("11");
                result.setmGoodsType("inapp");
                result.setPublicKey(PUBLIC_KEY);
                result.setmPackageID(packageName);
                result.setParams(params);
                result.setPayTest(1);
                RechargeManager.recharge(OneStoreActivity.this, Target.RECHARGE_ONE_STORE,
                        result, mOnRechargeListener);
            }
        });
    }

    private void init() {
        LTGameOptions options = new LTGameOptions.Builder(this)
                .debug(true)
                .appID(LTAppID)
                .appKey(LTAppKey)
                .publicKey(PUBLIC_KEY)
                .isServerTest(true)
                .goodsType("inapp")
                .setParams(params)
                .payTest(1)
                .oneStore()
                .goodsID(productID, "11")
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
                case RechargeResult.STATE_RECHARGE_RESULT:
                    switch (result.getResult()) {
                        case RESULT_BILLING_NEED_UPDATE: {
                            Log.e(TAG, "RESULT_BILLING_NEED_UPDATE");
                            break;
                        }
                        case RESULT_CLIENT_UN_CONNECTED: {
                            Log.e(TAG, "RESULT_CLIENT_UN_CONNECTED");
                            break;
                        }
                        case RESULT_CLIENT_CONNECTED: {
                            Log.e(TAG, "RESULT_CLIENT_CONNECTED");
                            break;
                        }
                        case RESULT_PURCHASES_REMOTE_ERROR: {
                            Log.e(TAG, "RESULT_PURCHASES_REMOTE_ERROR");
                            break;
                        }
                        case RESULT_PURCHASES_SECURITY_ERROR: {
                            Log.e(TAG, "RESULT_PURCHASES_SECURITY_ERROR");
                            break;
                        }
                        case RESULT_CLIENT_NOT_INIT: {
                            Log.e(TAG, "RESULT_CLIENT_NOT_INIT");
                            break;
                        }
                        case RESULT_BILLING_OK: {
                            Log.e(TAG, "RESULT_BILLING_OK");
                            break;
                        }
                        case RESULT_CONNECTED_NEED_UPDATE: {
                            Log.e(TAG, "RESULT_BILLING_OK");
                            break;
                        }
                        case RESULT_BILLING_REMOTE_ERROR: {
                            Log.e(TAG, "RESULT_BILLING_REMOTE_ERROR");
                            break;
                        }
                        case RESULT_BILLING_SECURITY_ERROR: {
                            Log.e(TAG, "RESULT_BILLING_SECURITY_ERROR");
                            break;
                        }
                        case IAP_ERROR_UNDEFINED_CODE: {
                            Log.e(TAG, "IAP_ERROR_UNDEFINED_CODE");
                            break;
                        }
                    }
                    break;
                case RechargeResult.STATE_RECHARGE_FAILED:
                    Log.e(TAG, "支付错误" + result.getErrorMsg());
                    break;

            }
        }

    };
}
