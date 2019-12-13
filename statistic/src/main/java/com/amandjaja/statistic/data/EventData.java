package com.amandjaja.statistic.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dodi on 02/06/2017.
 */

public class EventData extends BaseData {
    @SerializedName("id")
    public String ID;
    public String category;
    public String name;
    public String type;
    public Boolean isSuccess;
    @SerializedName("rejection_code")
    public String rejectionCode;
    @SerializedName("rejection_message")
    public String rejectionMessage;
}
