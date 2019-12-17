package com.gnetop.sdk.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gentop.ltgame.ltgamesdkcore.common.LTGameOptions;
import com.gentop.ltgame.ltgamesdkcore.common.LTGameSdk;
import com.gentop.ltgame.ltgamesdkcore.common.Target;
import com.gentop.ltgame.ltgamesdkcore.exception.LTGameError;
import com.gentop.ltgame.ltgamesdkcore.impl.OnLoginStateListener;
import com.gentop.ltgame.ltgamesdkcore.manager.LoginManager;
import com.gentop.ltgame.ltgamesdkcore.model.LoginObject;
import com.gentop.ltgame.ltgamesdkcore.model.LoginResult;
import com.gentop.ltgame.ltgamesdkcore.util.DeviceUtils;
import com.sdk.ltgame.ltnet.impl.OnAutoCheckLoginListener;
import com.sdk.ltgame.ltnet.manager.LoginRealizeManager;

import java.util.concurrent.Executors;

public class GoogleActivity extends AppCompatActivity {

    Button mBtnStart, mBtnLoginOut,mBtnAuto;
    TextView mTxtResult;
    private static final int REQUEST_CODE = 0x01;
    String LTAppKey = "q2h75rE8MW3fOVed82muf5w8dkBfXiSG";
    String LTAppID = "20003";
//    String LTAppKey = "MJwk6bLlpGErRgLKkJPLP7VavHRGvTpA";
//    String LTAppID = "28576";
    String TAG = "GoogleActivity";
    String clientID = "443503959733-0vhjo7df08ahd9i7d5lj9mdtt7bahsbq.apps.googleusercontent.com";
    String mPackageID = "com.gnetop.sdk.demo";
    String mAdID;
    private OnLoginStateListener mOnLoginListener;
    String mLtToken,mLtId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);
        initView();
        initData();
    }

    private void initView() {
        mTxtResult = findViewById(R.id.txt_result);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnAuto = findViewById(R.id.btn_auto);
        mBtnLoginOut = findViewById(R.id.btn_loginOut);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginObject object = new LoginObject();
                object.setmAdID(mAdID);
                object.setLTAppID(LTAppID);
                object.setLTAppKey(LTAppKey);
                object.setmGoogleClient(clientID);
                object.setSelfRequestCode(REQUEST_CODE);
                object.setLoginOut(false);
                object.setmPackageID(mPackageID);
                LoginManager.login(GoogleActivity.this, Target.LOGIN_GOOGLE, object, mOnLoginListener);
            }
        });
        mBtnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginObject object = new LoginObject();
                object.setmAdID(mAdID);
                object.setLTAppID(LTAppID);
                object.setLTAppKey(LTAppKey);
                object.setmGoogleClient(clientID);
                object.setSelfRequestCode(REQUEST_CODE);
                object.setLoginOut(true);
                object.setmPackageID(mPackageID);
                LoginManager.login(GoogleActivity.this, Target.LOGIN_GOOGLE, object, mOnLoginListener);

            }
        });
        mBtnAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRealizeManager.autoLoginCheck(GoogleActivity.this, LTAppID,
                        LTAppKey, mLtId, mLtToken, mPackageID,
                        new OnAutoCheckLoginListener() {
                            @Override
                            public void onCheckedSuccess(String result) {
                                Log.e("TAG",result);
                                mTxtResult.setText(result);
                            }

                            @Override
                            public void onCheckedFailed(String failed) {
                                Log.e("TAG",failed);
                            }

                            @Override
                            public void onCheckedException(LTGameError ex) {
                                if (ex != null) {
                                    switch (ex.getCode()) {
                                        case LTGameError.CODE_PARAM_ERROR: {
                                            Log.e("RESULT123", ex.getMsg());
                                            break;
                                        }
                                        case LTGameError.CODE_REQUEST_ERROR: {
                                            Log.e("RESULT123", ex.getMsg());
                                            break;
                                        }
                                        case LTGameError.CODE_NOT_SUPPORT: {
                                            Log.e("RESULT123", ex.getMsg());
                                            break;
                                        }
                                    }
                                }
                            }
                        });

            }
        });
    }


    /**
     * 初始化数据
     */
    private void initData() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mAdID = DeviceUtils.getGoogleAdId(getApplicationContext());
                    if (!TextUtils.isEmpty(mAdID)) {
                        init();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mOnLoginListener = new OnLoginStateListener() {
            @Override
            public void onState(Activity activity, LoginResult result) {
                switch (result.state) {
                    case LoginResult.STATE_SUCCESS:
                        if (result.getResultModel() != null) {
                            mLtToken=result.getResultModel().getData().getLt_uid_token();
                            mLtId=result.getResultModel().getData().getLt_uid();
                            mTxtResult.setText(mLtToken+"===="+mLtId);
                        }
                        break;
                    case LoginResult.STATE_LOGIN_OUT:
                        if (result.getError().getMsg() != null) {
                            Toast.makeText(GoogleActivity.this, result.getError().getMsg(), Toast.LENGTH_SHORT).show();
                            mTxtResult.setText(result.getError().getMsg());
                        }
                        break;
                    case LoginResult.STATE_FAIL:
                        if (result.getError()!=null){
                            switch (result.getError().getCode()){
                                case LTGameError.CODE_PARAM_ERROR:{
                                    Log.e("RESULT123", result.getError().getMsg());
                                    break;
                                }
                                case LTGameError.CODE_REQUEST_ERROR:{
                                    Log.e("RESULT123", result.getError().getMsg());
                                    break;
                                }
                                case LTGameError.CODE_NOT_SUPPORT:{
                                    Log.e("RESULT123", result.getError().getMsg());
                                    break;
                                }
                            }
                        }
                        break;

                }
            }

        };
    }

    private void init() {
        LTGameOptions options = new LTGameOptions.Builder(this)
                .debug(true)
                .appID(LTAppID)
                .appKey(LTAppKey)
                .isServerTest(true)
                .setAdID(mAdID)
                .packageID(mPackageID)
                .google(clientID)
                .requestCode(REQUEST_CODE)
                .build();
        LTGameSdk.init(options);
    }


}
