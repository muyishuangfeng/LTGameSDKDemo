package com.gnetop.sdk.demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gentop.ltsdk.common.model.ResultData;
import com.gentop.ltsdk.common.util.DeviceUtils;
import com.gentop.ltsdk.ltsdkui.impl.OnReLoginInListener;
import com.gentop.ltsdk.ltsdkui.impl.OnResultClickListener;
import com.gentop.ltsdk.ltsdkui.manager.LoginUIManager;

import java.util.concurrent.Executors;



public class UIActivity extends AppCompatActivity {

    Button mBtnLogin, mBtnLoginOut;
    TextView mTxtResult;
    String LTAppKey = "MJwk6bLlpGErRgLKkJPLP7VavHRGvTpA";
    String LTAppID = "28576";
    String mAgreementUrl = "http://www.baidu.com";
    String mProvacyUrl = "http://www.baidu.com";
    String mAdID;
    String mPackageID = "com.gnetop.sdk.demo";
    String clientID = "443503959733-0vhjo7df08ahd9i7d5lj9mdtt7bahsbq.apps.googleusercontent.com";
    String mFacebookId = "2717734461592670";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        initView();
        initData();
    }


    protected void initView() {
        mTxtResult = findViewById(R.id.txt_result);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mAdID)) {
                    login();
                }
            }
        });
        mBtnLoginOut = findViewById(R.id.btn_loginOut);
        mBtnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mAdID)) {
                    loginOut();
                }

            }
        });
    }

    protected void initData() {
        getAdID();
    }


    private void login() {
        LoginUIManager.getInstance().loginIn(this, true, mFacebookId, mAgreementUrl, mProvacyUrl, clientID,
                LTAppID, LTAppKey, mAdID, mPackageID, false, new OnResultClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResult(ResultData result) {
                        mTxtResult.setText("=====登录成功:\n" + result.toString());
                        Log.e("TAG", result.toString());
                    }
                }, new OnReLoginInListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void OnLoginResult(ResultData result) {
                        mTxtResult.setText("OnReLoginInListener=====结果:\n" + result.toString());
                        Log.e("TAG", result.toString());
                    }
                });
    }

    private void loginOut() {
        LoginUIManager.getInstance().loginOut(this, true, mFacebookId, mAgreementUrl, mProvacyUrl, clientID,
                LTAppID, LTAppKey, mAdID, mPackageID, true, new OnResultClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResult(ResultData result) {
                        mTxtResult.setText("=====结果:\n" + result.toString());
                        Log.e("TAG", result.toString());
                    }
                });
    }


    private void getAdID() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mAdID = DeviceUtils.getGoogleAdId(UIActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
