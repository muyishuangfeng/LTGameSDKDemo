//package com.gnetop.sdk.demo;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.gentop.ltgame.ltgamesdkcore.common.LTGameOptions;
//import com.gentop.ltgame.ltgamesdkcore.common.LTGameSdk;
//import com.gentop.ltgame.ltgamesdkcore.common.Target;
//import com.gentop.ltgame.ltgamesdkcore.exception.LTGameError;
//import com.gentop.ltgame.ltgamesdkcore.impl.OnLoginStateListener;
//import com.gentop.ltgame.ltgamesdkcore.manager.LoginManager;
//import com.gentop.ltgame.ltgamesdkcore.model.LoginObject;
//import com.gentop.ltgame.ltgamesdkcore.model.LoginResult;
//import com.gentop.ltgame.ltgamesdkcore.util.DeviceUtils;
//
//import java.util.concurrent.Executors;
//
//public class FacebookActivity extends AppCompatActivity {
//
//    Button mBtnStart, mBtnLoginOut;
//    TextView mTxtResult;
//    private static final int REQUEST_CODE = 0x01;
//    String LTAppID = "20003";
//    String LTAppKey = "q2h75rE8MW3fOVed82muf5w8dkBfXiSG";
//
////    String LTAppKey = "MJwk6bLlpGErRgLKkJPLP7VavHRGvTpA";
////    String LTAppID = "28576";
//
//    String TAG = "FacebookActivity";
//    String mPackageID = "com.ltgames.yyjw.google";
//    String mAdID;
//    String mFacebookId = "2717734461592670";
//    private OnLoginStateListener mOnLoginListener;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_google);
//        initView();
//        initData();
//    }
//
//    private void initView() {
//        mTxtResult = findViewById(R.id.txt_result);
//        mBtnLoginOut = findViewById(R.id.btn_loginOut);
//        mBtnStart = findViewById(R.id.btn_start);
//        mBtnStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LoginObject object = new LoginObject();
//                object.setFacebookAppID(mFacebookId);
//                object.setmAdID(mAdID);
//                object.setLTAppID(LTAppID);
//                object.setLTAppKey(LTAppKey);
//                object.setSelfRequestCode(REQUEST_CODE);
//                object.setLoginOut(false);
//                object.setmPackageID(mPackageID);
//                LoginManager.login(FacebookActivity.this, Target.LOGIN_FACEBOOK, object, mOnLoginListener);
//
//            }
//        });
//        mBtnLoginOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LoginObject object = new LoginObject();
//                object.setmAdID(mAdID);
//                object.setLTAppID(LTAppID);
//                object.setLTAppKey(LTAppKey);
//                object.setFacebookAppID(mFacebookId);
//                object.setSelfRequestCode(REQUEST_CODE);
//                object.setLoginOut(true);
//                object.setmPackageID(mPackageID);
//                LoginManager.login(FacebookActivity.this, Target.LOGIN_FACEBOOK, object, mOnLoginListener);
//
//            }
//        });
//    }
//
//
//    /**
//     * 初始化数据
//     */
//    private void initData() {
//        Executors.newSingleThreadExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    mAdID = DeviceUtils.getGoogleAdId(getApplicationContext());
//                    if (!TextUtils.isEmpty(mAdID)) {
//                        init();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        mOnLoginListener = new OnLoginStateListener() {
//            @Override
//            public void onState(Activity activity, LoginResult result) {
//                switch (result.state) {
//                    case LoginResult.STATE_SUCCESS:
//                        if (result.getResultModel()!=null){
//                            Log.e(TAG, result.getResultModel().toString());
//                            mTxtResult.setText(result.getResultModel().toString());
//                        }
//                        break;
//                    case LoginResult.STATE_LOGIN_OUT:
//                        if (result.getError().getMsg() != null) {
//                            Toast.makeText(FacebookActivity.this, result.getError().getMsg(), Toast.LENGTH_SHORT).show();
//                            mTxtResult.setText(result.getError().getMsg());
//                        }
//                        break;
//                    case LoginResult.STATE_FAIL:
//                        if (result.getError()!=null){
//                            switch (result.getError().getCode()){
//                                case LTGameError.CODE_PARAM_ERROR:{
//                                    Log.e("RESULT123", result.getError().getMsg());
//                                    break;
//                                }
//                                case LTGameError.CODE_REQUEST_ERROR:{
//                                    Log.e("RESULT123", result.getError().getMsg());
//                                    break;
//                                }
//                                case LTGameError.CODE_NOT_SUPPORT:{
//                                    Log.e("RESULT123", result.getError().getMsg());
//                                    break;
//                                }
//                            }
//                        }
//                        break;
//                }
//            }
//
//        };
//    }
//
//    private void init() {
//        LTGameOptions options = new LTGameOptions.Builder(this)
//                .debug(true)
//                .appID(LTAppID)
//                .appKey(LTAppKey)
//                .isServerTest(true)
//                .setAdID(mAdID)
//                .packageID(mPackageID)
//                .facebookEnable()
//                .facebook(mFacebookId)
//                .requestCode(REQUEST_CODE)
//                .build();
//        LTGameSdk.init(options);
//    }
//
//
//}
