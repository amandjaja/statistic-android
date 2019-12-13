package com.amandjaja.statistic.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dodi on 01/18/2017.
 */

public class ContentData extends BaseData {
    @SerializedName("id")
    public String ID;
    public String type;
    public String category;
    public String action;
}
