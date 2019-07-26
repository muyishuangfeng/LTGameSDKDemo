package com.gentop.ltgame.ltgamesdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.gentop.ltgame.ltgamesdkcore.common.LTGameOptions;
import com.gentop.ltgame.ltgamesdkcore.common.LTGameSdk;
import com.gentop.ltgame.ltgamesdkcore.common.Target;
import com.gentop.ltgame.ltgamesdkcore.impl.OnLoginStateListener;
import com.gentop.ltgame.ltgamesdkcore.manager.LoginManager;
import com.gentop.ltgame.ltgamesdkcore.model.LoginObject;
import com.gentop.ltgame.ltgamesdkcore.model.LoginResult;
import com.gentop.ltgame.ltgamesdkcore.util.DeviceUtils;

import java.util.concurrent.Executors;

public class QQActivity extends AppCompatActivity {

    Button mBtnLogin, mBtnLoginOut;
    TextView mTxtResult;
    String LTAppID = "20001";
    String LTAppKey = "f8XkF2vVDMh4BWxAayD0YOIl0C2QVEaW";
    String TAG = "QQActivity";
    String mAdID;
    String baseUrl = "http://sdk.aktgo.com";
    private OnLoginStateListener mOnLoginListener;
    String mQQAppID = "1108097616";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);
        initView();
        initData();
    }

    private void initView() {
        mTxtResult = findViewById(R.id.txt_result);
        mBtnLoginOut = findViewById(R.id.btn_loginOut);
        mBtnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginObject object = new LoginObject();
                object.setBaseUrl(baseUrl);
                object.setmAdID(mAdID);
                object.setLTAppID(LTAppID);
                object.setLTAppKey(LTAppKey);
                object.setQqAppID(mQQAppID);
                object.setLoginOut(true);
                LoginManager.login(QQActivity.this, Target.LOGIN_QQ, object, mOnLoginListener);

            }
        });
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginObject object = new LoginObject();
                object.setBaseUrl(baseUrl);
                object.setmAdID(mAdID);
                object.setLTAppID(LTAppID);
                object.setLTAppKey(LTAppKey);
                object.setQqAppID(mQQAppID);
                object.setLoginOut(false);
                LoginManager.login(QQActivity.this, Target.LOGIN_QQ, object, mOnLoginListener);
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
                        Log.e(TAG, result.getResultModel().toString());
                        mTxtResult.setText(result.getResultModel().toString());
                        break;

                    case LoginResult.STATE_FAIL:
                        Log.e(TAG,result.getError().toString());
                        Log.e(TAG, "STATE_FAIL");
                        break;
                    case LoginResult.STATE_CANCEL:
                        Log.e(TAG, "STATE_FAIL");
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
                .baseUrl(baseUrl)
                .setAdID(mAdID)
                .setQQEnable(true)
                .qq(mQQAppID)
                .build();
        LTGameSdk.init(options);
    }
}
