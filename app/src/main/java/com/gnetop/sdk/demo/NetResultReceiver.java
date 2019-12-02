package com.gnetop.sdk.demo;

import android.util.Log;

import com.sdk.ltgame.ltnet.receiver.NetReceiver;


public class NetResultReceiver extends NetReceiver {

    @Override
    public void getResult(String result) {
        Log.e("NETResult",result);
    }
}
