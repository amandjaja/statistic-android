package com.amandjaja.statistic.apis;

import com.amandjaja.statistic.data.ContentData;
import com.amandjaja.statistic.data.EventData;
import com.amandjaja.statistic.apis.receivers.GeneralApiReceiver;
import com.amandjaja.statistic.data.HitData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Dodi on 01/27/2017.
 */

public interface StatisticAPI {
    @Headers("Content-Type: application/json")
    @POST("hit")
    Call<GeneralApiReceiver> hit(@Body HitData params);
    @Headers("Content-Type: application/json")
    @POST("content")
    Call<GeneralApiReceiver> content(@Body ContentData params);
    @Headers("Content-Type: application/json")
    @POST("event")
    Call<GeneralApiReceiver> event(@Body EventData params);
}
