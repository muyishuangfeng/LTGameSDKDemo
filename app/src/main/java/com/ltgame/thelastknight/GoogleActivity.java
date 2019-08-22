package com.ltgame.thelastknight;

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
import com.gentop.ltgame.ltgamesdkcore.impl.OnLoginStateListener;
import com.gentop.ltgame.ltgamesdkcore.manager.LoginManager;
import com.gentop.ltgame.ltgamesdkcore.model.LoginObject;
import com.gentop.ltgame.ltgamesdkcore.model.LoginResult;
import com.gentop.ltgame.ltgamesdkcore.util.DeviceUtils;
import com.sdk.ltgame.ltgoogle.GooglePlatform;
import com.sdk.ltgame.ltgoogle.uikit.GoogleLoginActivity;
import com.sdk.ltgame.ltnet.impl.OnAutoCheckLoginListener;
import com.sdk.ltgame.ltnet.manager.LoginRealizeManager;

import java.util.concurrent.Executors;

public class GoogleActivity extends AppCompatActivity {

    Button mBtnStart, mBtnLoginOut,mBtnAuto;
    TextView mTxtResult;
    private static final int REQUEST_CODE = 0x01;
    String LTAppKey = "ATGhGUQ3VbptAf5qq544njqBAK2TGKdz";
    String LTAppID = "28571";
    //    String LTAppID = "20001";
//    String LTAppKey = "f8XkF2vVDMh4BWxAayD0YOIl0C2QVEaW";
    String TAG = "GooglePlayActivity";
    //String clientID = "182767183123-v2l0sd2cs67ob9bet6bql80cuel09445.apps.googleusercontent.com";
    String clientID = "491715152271-lli7bduussokgt43cup0frsrl5k5un9s.apps.googleusercontent.com";
    // String mPackageID = "com.ltgames.yyjw.google";
    String mPackageID = "com.ltgame.thelastknight";
    String mAdID;
    String baseUrl = "http://login.gdpgold.com";
    //String baseUrl = "http://sdk.aktgo.com";
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
        mBtnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginObject object = new LoginObject();
                object.setBaseUrl(baseUrl);
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
                LoginRealizeManager.autoLoginCheck(GoogleActivity.this, baseUrl, LTAppID,
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
