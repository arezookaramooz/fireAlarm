package com.example.arezoo.firealarm;

import android.app.Application;

import rest.bef.BefrestFactory;

/**
 * Created by Arezoo on 05-Oct-18.
 */

public class BefrestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BefrestFactory.getInstance(this)
                .init(11876, "1FczPz-cjNTl-VCCLFpqHw", "quickstartchannel")
                .start();
    }
}
