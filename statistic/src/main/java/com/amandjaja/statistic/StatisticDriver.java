package com.amandjaja.statistic;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;

import com.amandjaja.statistic.data.BaseData;

import id.kiosku.utils.DeviceDriver;
import id.kiosku.utils.KUtility;
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

    public <A extends BaseData> A attach(final A data){
        try {
            DeviceDriver deviceDriver = DeviceDriver.with(context);

            data.sessionId = StatisticManager.getSession();
            data.token = KUtility.randomString(15);

            data.carrier = "SDK-v"+ BuildConfig.VERSION_NAME+"("+BuildConfig.VERSION_CODE+")";
            data.screenView = StatisticManager.getScreenView();
            data.screenType = StatisticManager.getScreenType();

            if(context.checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                data.setLatlng(LocationDriver.getInstance().getLatitude(),LocationDriver.getInstance().getLongitude());
                data.location.put("subadmin", LocationDriver.getInstance().getAddress(LocationDriver.AddressType.SUBADMIN));
                data.location.put("admin", LocationDriver.getInstance().getAddress(LocationDriver.AddressType.ADMIN));
                data.location.put("country", LocationDriver.getInstance().getAddress(LocationDriver.AddressType.COUNTRY));
                data.location.put("country_code", LocationDriver.getInstance().getAddress(LocationDriver.AddressType.COUNTRY_CODE));
            }
            if(context.checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
                data.deviceID = deviceDriver.getID();
            else data.deviceID = deviceDriver.getAndroidID();
            data.device.put("brand", Build.BRAND);
            data.device.put("type", Build.MODEL);
            data.device.put("version", String.valueOf(Build.VERSION.SDK_INT));

            ScreenDensityReader.scan(context, new ScreenDensityReader.OnScreen() {
                @Override
                public void LDPI() {
                    data.device.put("res", "LDPI");
                }

                @Override
                public void MDPI() {
                    data.device.put("res", "MDPI");
                }

                @Override
                public void HDPI() {
                    data.device.put("res", "HDPI");
                }

                @Override
                public void XHDPI() {
                    data.device.put("res", "XHDPI");
                }

                @Override
                public void XXHDPI() {
                    data.device.put("res", "XXHDPI");
                }

                @Override
                public void XXXHDPI() {
                    data.device.put("res", "XXXHDPI");
                }

                @Override
                public void unknown() {
                    data.device.put("res", "unknown");
                }
            });

            data.logDate = ISODateTimeFormat.dateTime().print(dateTime);
            data.logTime = ISODateTimeFormat.dateTime().print(dateTime);
        }catch (Exception e){
        }
        return data;
    }
}
