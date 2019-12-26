package com.gnetop.sdk.demo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.sdk.ltgame.guest.GuestPlatform;

import java.util.concurrent.Executors;

public class GuestActivity extends AppCompatActivity {

    Button mBtnLogin, mBtnBind, mBtnUnBind, mBtnFB;
    TextView mTxtResult;
    private static final int REQUEST_CODE = 0x01;
    String LTAppID = "20003";
    String LTAppKey = "q2h75rE8MW3fOVed82muf5w8dkBfXiSG";
    String TAG = "GuestActivity";
//    String LTAppKey = "MJwk6bLlpGErRgLKkJPLP7VavHRGvTpA";
//    String LTAppID = "28576";

    String mFacebookId = "2717734461592670";
    private OnLoginStateListener mOnLoginListener;
    String clientID = "443503959733-0vhjo7df08ahd9i7d5lj9mdtt7bahsbq.apps.googleusercontent.com";
    String mPackageID = "com.gnetop.sdk.demo";
    String mAdID;
    String mLtToken, mLtId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
        initView();
        initData();
    }

    private void initView() {
        mTxtResult = findViewById(R.id.txt_result);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginObject object = new LoginObject();
                object.setmAdID(mAdID);
                object.setFacebookAppID(mFacebookId);
                object.setLTAppID(LTAppID);
                object.setLTAppKey(LTAppKey);
                object.setSelfRequestCode(REQUEST_CODE);
                object.setmPackageID(mPackageID);
                object.setmGoogleClient(clientID);
                object.setGuestType("1");
                LoginManager.login(GuestActivity.this, Target.LOGIN_GUEST, object, mOnLoginListener);
            }
        });
        mBtnFB = findViewById(R.id.btn_bind_fb);
        mBtnFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginObject object = new LoginObject();
                object.setmAdID(mAdID);
                object.setFacebookAppID(mFacebookId);
                object.setLTAppID(LTAppID);
                object.setLTAppKey(LTAppKey);
                object.setSelfRequestCode(REQUEST_CODE);
                object.setmPackageID(mPackageID);
                object.setmGoogleClient(clientID);
                object.setGuestType("2");
                LoginManager.login(GuestActivity.this, Target.LOGIN_GUEST, object, mOnLoginListener);
            }
        });
        mBtnBind = findViewById(R.id.btn_bind);
        mBtnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginObject object = new LoginObject();
                object.setmAdID(mAdID);
                object.setFacebookAppID(mFacebookId);
                object.setLTAppID(LTAppID);
                object.setLTAppKey(LTAppKey);
                object.setSelfRequestCode(REQUEST_CODE);
                object.setmPackageID(mPackageID);
                object.setmGoogleClient(clientID);
                object.setGuestType("3");
                LoginManager.login(GuestActivity.this, Target.LOGIN_GUEST, object, mOnLoginListener);
            }
        });
        mBtnUnBind = findViewById(R.id.btn_unbind);
        mBtnUnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginObject object = new LoginObject();
                object.setmAdID(mAdID);
                object.setFacebookAppID(mFacebookId);
                object.setLTAppID(LTAppID);
                object.setLTAppKey(LTAppKey);
                object.setSelfRequestCode(REQUEST_CODE);
                object.setmPackageID(mPackageID);
                object.setmGoogleClient(clientID);
                object.setGuestType("4");
                LoginManager.login(GuestActivity.this, Target.LOGIN_GUEST, object, mOnLoginListener);
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
                            mLtToken = result.getResultModel().getData().getLt_uid_token();
                            mLtId = result.getResultModel().getData().getLt_uid();
                            mTxtResult.setText(mLtToken + "====" + mLtId + "====" + result.getResultModel().getCode());
                        }
                        break;
                    case LoginResult.STATE_LOGIN_OUT:
                        if (result.getError().getMsg() != null) {
                            Toast.makeText(GuestActivity.this, result.getError().getMsg(), Toast.LENGTH_SHORT).show();
                            mTxtResult.setText(result.getError().getMsg());
                        }
                        break;
                    case LoginResult.STATE_FAIL:
                        if (result.getError() != null) {
                            mTxtResult.setText(result.getError().getMsg() + "=====" + result.getError().getCode());
                            Log.e(TAG, result.getError().getMsg() + "=====" + result.getError().getCode());
                            switch (result.getError().getCode()) {
                                case LTGameError.CODE_PARAM_ERROR: {
                                    Log.e(TAG, result.getError().getMsg());
                                    break;
                                }
                                case LTGameError.CODE_REQUEST_ERROR: {
                                    Log.e("RESULT123", result.getError().getMsg());
                                    break;
                                }
                                case LTGameError.CODE_NOT_SUPPORT: {
                                    Log.e("RESULT", result.getError().getMsg());
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
                .appName("name")
                .appKey(LTAppKey)
                .isServerTest(true)
                .setAdID(mAdID)
                .packageID(mPackageID)
                .guestEnable(true)
                .requestCode(REQUEST_CODE)
                .build();
        LTGameSdk.init(options);
    }
}
