package com.amandjaja.statistic;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.amandjaja.statistic.data.BaseData;
import id.kiosku.utils.DeviceDriver;
import id.kiosku.utils.LocationDriver;
import id.kiosku.utils.ScreenDensityReader;

/**
 * Created by Dodi on 01/26/2017.
 */
@SuppressWarnings("WeakerAccess")
public class StatisticDriver{
    private Context context;
    private DateTime dateTime;

    public StatisticDriver(Context context){
        this.context = context;
        DateTimeZone.setDefault(DateTimeZone.forID("Asia/Jakarta"));
        dateTime = new DateTime();
        if(StatisticManager.getSession()==null)StatisticManager.startSession();
    }

    public static StatisticDriver with(Context context){
        return new StatisticDriver(context);
    }

    public <A extends BaseData> A attach(@NonNull final A data){
        try {
            DeviceDriver deviceDriver = DeviceDriver.with(context);

            data.sessionId = StatisticManager.getSession();
            data.token = DeviceDriver.with(context)
                    .getAndroidID().substring(0,5)+ StatisticUtility.randomString(4)+DateTime.now()
                    .toString("HHmmss");

            data.carrier = "Android Libs v"+ BuildConfig.VERSION_NAME+"("+BuildConfig.VERSION_CODE+")";
            data.screenView = StatisticManager.getScreenView();
            data.screenType = StatisticManager.getScreenType();

            if(context.checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                data.latitude = LocationDriver.getInstance().getLatitude();
                data.longitude = LocationDriver.getInstance().getLongitude();
                data.locationSubadmin = LocationDriver.getInstance().getAddress(LocationDriver.AddressType.SUBADMIN);
                data.locationAdmin = LocationDriver.getInstance().getAddress(LocationDriver.AddressType.ADMIN);
                data.locationCountry = LocationDriver.getInstance().getAddress(LocationDriver.AddressType.COUNTRY);
                data.locationCountryCode = LocationDriver.getInstance().getAddress(LocationDriver.AddressType.COUNTRY_CODE);
            }
            if(context.checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
                data.deviceId = deviceDriver.getID();
            else data.deviceId = deviceDriver.getAndroidID();
            data.deviceBrand = Build.BRAND;
            data.deviceType = Build.MODEL;
            data.deviceVersion = String.valueOf(Build.VERSION.SDK_INT);

            ScreenDensityReader.scan(context, new ScreenDensityReader.OnScreen() {
                @Override
                public void LDPI() {
                    data.deviceRes = "LDPI";
                }

                @Override
                public void MDPI() {
                    data.deviceRes = "MDPI";
                }

                @Override
                public void HDPI() {
                    data.deviceRes = "HDPI";
                }

                @Override
                public void XHDPI() {
                    data.deviceRes = "XHDPI";
                }

                @Override
                public void XXHDPI() {
                    data.deviceRes = "XXHDPI";
                }

                @Override
                public void XXXHDPI() {
                    data.deviceRes = "XXXHDPI";
                }

                @Override
                public void unknown() {
                    data.deviceRes = "unknown";
                }
            });

            data.logTime = dateTime.toString("HH:mm:ss");
            data.logDate = dateTime.toString("y-MM-dd");
        }catch (Exception e){
            return data;
        }
        return data;
    }
}
