package com.amandjaja.statistic.data;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.joda.time.format.ISODateTimeFormat;

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
    public Map<String,Object> device;
    private Double latitude;
    private Double longitude;
    public Map<String,Object> location;

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
        device = new HashMap<>();
        location = new HashMap<>();
        attributes = new HashMap<>();
        logDate = ISODateTimeFormat.dateTime().print(DateTime.now());
        logTime = logDate;
    }

    public void setLatlng(double lat, double lng){
        this.latitude = lat;
        this.longitude = lng;
    }

    public Double getLatitude(){
        return this.latitude;
    }

    public Double getLongitude(){
        return this.longitude;
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
