package com.amandjaja.statistic.apis;

import com.amandjaja.statistic.data.ContentData;
import com.amandjaja.statistic.data.EventData;
import com.amandjaja.statistic.apis.receivers.GeneralApiReceiver;
import com.amandjaja.statistic.data.HitData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Dodi on 01/27/2017.
 */

public interface StatisticAPI {
    @POST("hit")
    Call<GeneralApiReceiver> hit(@Body HitData params);
    @POST("hit")
    Call<GeneralApiReceiver> hitBulk(@Body HitData params);
    @POST("content")
    Call<GeneralApiReceiver> content(@Body ContentData params);
    @POST("content/bulk")
    Call<GeneralApiReceiver> contentBulk(@Body ContentData params);
    @POST("event")
    Call<GeneralApiReceiver> event(@Body EventData params);
    @POST("event/bulk")
    Call<GeneralApiReceiver> eventBulk(@Body EventData params);
}
