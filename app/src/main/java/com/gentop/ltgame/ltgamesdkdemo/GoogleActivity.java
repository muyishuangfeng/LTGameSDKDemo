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

public class GoogleActivity extends AppCompatActivity {

    Button mBtnStart;
    TextView mTxtResult;
    private static final int REQUEST_CODE = 0x01;
    String LTAppID = "20001";
    String LTAppKey = "f8XkF2vVDMh4BWxAayD0YOIl0C2QVEaW";
    String TAG = "GooglePlayActivity";
    String clientID = "182767183123-v2l0sd2cs67ob9bet6bql80cuel09445.apps.googleusercontent.com";
    String mPackageID = "com.ltgames.yyjw.google";
    String mAdID;
    String baseUrl = "http://sdk.aktgo.com";
    private OnLoginStateListener mOnLoginListener;


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
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginObject object = new LoginObject();
                object.setBaseUrl(baseUrl);
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
                .packageID(mPackageID)
                .google(clientID)
                .requestCode(REQUEST_CODE)
                .build();
        LTGameSdk.init(options);
    }



}
