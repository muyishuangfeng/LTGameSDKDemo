package com.ltgame.thelastknight;

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

public class PhoneActivity extends AppCompatActivity {


    Button mBtnLogin, mBtnRegister, mBtnChange;
    TextView mTxtResult;
    String LTAppID = "20001";
    String LTAppKey = "f8XkF2vVDMh4BWxAayD0YOIl0C2QVEaW";
    String TAG = "PhoneActivity";
    String mAdID;
    String baseUrl = "http://sdk.aktgo.com";
    private OnLoginStateListener mOnLoginListener;
    String mPhone = "18302949079";
    String mPassword = "123456789";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        initView();
        initData();
    }

    private void initView() {
        mTxtResult = findViewById(R.id.txt_result);
        mBtnChange = findViewById(R.id.btn_change);
        mBtnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginObject object = new LoginObject();
                object.setBaseUrl(baseUrl);
                object.setmAdID(mAdID);
                object.setLTAppID(LTAppID);
                object.setLTAppKey(LTAppKey);
                object.setmPhone(mPhone);
                object.setmPassword("123456789");
                object.setmLoginCode("3");
                LoginManager.login(PhoneActivity.this, Target.LOGIN_PHONE, object, mOnLoginListener);

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
                object.setmPhone(mPhone);
                object.setmPassword(mPassword);
                object.setmLoginCode("2");
                LoginManager.login(PhoneActivity.this, Target.LOGIN_PHONE, object, mOnLoginListener);
            }
        });
        mBtnRegister = findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginObject object = new LoginObject();
                object.setBaseUrl(baseUrl);
                object.setmAdID(mAdID);
                object.setLTAppID(LTAppID);
                object.setLTAppKey(LTAppKey);
                object.setmPhone(mPhone);
                object.setmPassword(mPassword);
                object.setmLoginCode("1");
                LoginManager.login(PhoneActivity.this, Target.LOGIN_PHONE, object, mOnLoginListener);
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
                .loginCode("1")
                .phoneAndPass(mPhone, mPassword)
                .phoneEnable()
                .build();
        LTGameSdk.init(options);
    }

}
