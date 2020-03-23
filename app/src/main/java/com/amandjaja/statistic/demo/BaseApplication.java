package com.amandjaja.statistic.demo;

import android.app.Application;

import com.amandjaja.statistic.StatisticConfig;
import com.amandjaja.statistic.StatisticManager;
import id.kiosku.utils.LocationDriver;

@StatisticConfig(
        url = "HOST"
)
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LocationDriver.init(this);
        StatisticManager.init(this);
    }
}
