package com.amandjaja.statistic.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dodi on 02/06/2017.
 */

public class EventData extends BaseData {
    @SerializedName("cid")
    public String ID;
    public String category;
    public String name;
    public String type;
    @SerializedName("isOk")
    public Integer isOk;
    public String status;
    @SerializedName("status_code")
    public String statusCode;
    @SerializedName("status_message")
    public String statusMessage;
}
