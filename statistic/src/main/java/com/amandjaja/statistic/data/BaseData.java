package com.amandjaja.statistic.data;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.amandjaja.statistic.StatisticManager;

/**
 * Created by Dodi on 01/18/2017.
 */

public abstract class BaseData {
    @SerializedName("session_id")
    public String sessionId;
    public String token;
    @SerializedName("page")
    public String screenView;
    @SerializedName("page_type")
    public String screenType;
    {
        screenView = StatisticManager.getScreenView();
        screenType = StatisticManager.getScreenType();
        userId = StatisticManager.getUserID();
    }

    public boolean isInteraction;

    public Short age;
    public String gender;

    @SerializedName("user_id")
    public String userId;

    public String carrier;
    @SerializedName("device_id")
    public String deviceId;
    @SerializedName("device_brand")
    public String deviceBrand;
    @SerializedName("device_type")
    public String deviceType;
    @SerializedName("device_version")
    public String deviceVersion;
    @SerializedName("device_res")
    public String deviceRes;
    public Double longitude;
    public Double latitude;
    @SerializedName("location_subadmin")
    public String locationSubadmin;
    @SerializedName("location_admin")
    public String locationAdmin;
    @SerializedName("location_country")
    public String locationCountry;
    @SerializedName("location_country_code")
    public String locationCountryCode;

    @SerializedName("log_time")
    public String logTime;
    @SerializedName("log_date")
    public String logDate;
    public transient DateTime logDatetimeStart;
    public transient DateTime logDatetimeEnd;
    @SerializedName("long_time")
    public Integer longTime;
    public String callforward;

    public ArrayList<BaseData> data;

    public Map<String,Object> attributes;
    {
        data = new ArrayList<>();
        attributes = new HashMap<>();
        logDate = DateTime.now().toString("yyyy-MM-dd");
        logTime = DateTime.now().toString("HH:mm:ss");
    }

    public void triggerStart(){
        logDatetimeStart = DateTime.now();
    }
    public void triggerEnd(){
        if(longTime==null)longTime=0;
        logDatetimeEnd = DateTime.now();
        try {
            longTime += Seconds.secondsBetween(logDatetimeStart,logDatetimeEnd).getSeconds();
        }catch (Exception e){}
    }
}
