package com.example.tornado.taabirkhab;

import android.app.Application;

import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellConfiguration;

/**
 * Created by tornado on 10/16/2018.
 */

public class TapsellApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        TapsellConfiguration config = new TapsellConfiguration(this);
        config.setPermissionHandlerMode(TapsellConfiguration.PERMISSION_HANDLER_DISABLED);
//        Tapsell.initialize(this, config, BuildConfig.tapsellSampleAppKey);
        Tapsell.initialize(this,config,"dtcedolhfbnqheachtddgimkmnmkonnrfobddmhdatgjigokdrenofqtngsnksconifenb");
    }
}
