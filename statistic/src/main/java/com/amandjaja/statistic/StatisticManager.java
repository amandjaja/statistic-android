package com.amandjaja.statistic;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.amandjaja.statistic.apis.StatisticAPI;
import com.amandjaja.statistic.apis.receivers.GeneralApiReceiver;
import com.amandjaja.statistic.data.BaseData;
import com.amandjaja.statistic.data.ContentData;
import com.amandjaja.statistic.data.EventData;
import com.amandjaja.statistic.data.HitData;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.util.concurrent.TimeUnit;

import id.kiosku.utils.CryptoDriver;
import id.kiosku.utils.KUtility;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("WeakerAccess")
public class StatisticManager {
    private static StatisticManager manager;
    private Context context;
    private String url, key;

    public static void init(Context context){
        init(context,"");
    }

    public static void init(Context context, String key){
        init(context, "", key);
    }

    public static void init(Context context, String url, String key){
        StatisticManager.manager = StatisticManager.with(context, url, key);
    }

    private Retrofit apis;
    public interface EventListener{
        void onResponse(GeneralApiReceiver response);
        void onFailed();
    }

    public StatisticManager(Context context){
        this(context,"");
    }
    public StatisticManager(Context context, String key){
        this(context,"", key);
    }

    public StatisticManager(Context context, @NonNull String url,@NonNull String key){
        StatisticConfig config = context.getApplicationContext().getClass().getAnnotation(StatisticConfig.class);
        if(config==null && url.isEmpty() && key.isEmpty()){
            throw new NoConfigurationException();
        }else if(config != null && config.url().isEmpty() && url.isEmpty()){
            throw new NoConfigurationException();
        }else if(config != null && config.key().isEmpty() && key.isEmpty()){
            throw new NoConfigurationException();
        }

        if(!url.isEmpty()) this.url = url;
        else if(config!=null) this.url = config.url();
        if(!key.isEmpty()) this.key = key;
        else if(config!=null) this.key = config.key();

        this.context = context;

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .callTimeout(3, TimeUnit.SECONDS)
                .addInterceptor((chain)->{
                    Request req = chain.request();
                    Request.Builder reqBuilder = req.newBuilder()
                            .addHeader("Authorization", "Basic "+ CryptoDriver.Base64.encode((this.key+":").getBytes()));
                    okhttp3.Response res = chain.proceed(reqBuilder.build());
                    if(res.body()!=null) Log.v("StatisticManager:", "Response: "+res.body().string());
                    return res.newBuilder().build();
                })
                .build();

        apis = new Retrofit.Builder()
                .baseUrl(this.url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static StatisticManager with(Context context){
        return with(context,"");
    }

    public static StatisticManager with(Context context, String key){
        return with(context,"",key);
    }

    public static StatisticManager with(Context context, String url, String key){
        return new StatisticManager(context, url, key);
    }

    private EventListener listener;
    public StatisticManager setListener(EventListener listener){
        this.listener = listener;
        return this;
    }

    public static HitProvider createHit(){
        return new HitProvider(new HitData());
    }
    public static ContentProvider createContent(){
        return new ContentProvider(new ContentData());
    }
    public static EventProvider createEvent(){
        return new EventProvider(new EventData());
    }

    public static <D extends BaseData> void triggerStart(D data){
        data.logDatetimeStart = DateTime.now();
    }
    public static <D extends BaseData> void triggerEnd(D data){
        if(data.longTime==null)data.longTime=0;
        data.logDatetimeEnd = DateTime.now();
        try {
            data.longTime += Seconds.secondsBetween(data.logDatetimeStart,data.logDatetimeEnd).getSeconds();
        }catch (Exception e){}
    }
    public <D extends BaseData> void send(D data){
        send(data,null);
    }
    @SuppressWarnings("unchecked")
    public <D extends BaseData> void send(final D data, final OnSend callback){
        if(callback!=null)callback.onPrepare(data);
        new Thread(()->{
            Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
            StatisticDriver.with(context).attach(data);
            StatisticAPI statisticAPI = apis.create(StatisticAPI.class);
            Call<GeneralApiReceiver> call;
            if (data instanceof HitData) {
                call = statisticAPI.hit((HitData)data);
            }else if(data instanceof ContentData){
                call = statisticAPI.content((ContentData) data);
            }else if(data instanceof EventData){
                call = statisticAPI.event((EventData)data);
            }else{
                throw new InvalidDataException();
            }
            call.enqueue(new Callback<GeneralApiReceiver>() {
                @Override
                public void onResponse(Call<GeneralApiReceiver> call, Response<GeneralApiReceiver> response) {
                    if(response.code()==200){
                        if(response.body()!=null){
                            if(listener!=null)listener.onResponse(response.body());
                        }else{
                            if(listener!=null)listener.onFailed();
                        }
                    }else {
                        if (listener != null) listener.onFailed();
                    }
                    if(callback!=null)callback.onSent(data);
                }

                @Override
                public void onFailure(Call<GeneralApiReceiver> call, Throwable t) {
                    if(listener!=null)listener.onFailed();
                    if(callback!=null)callback.onFail(data);
                }
            });
        }).start();
    }

    private static String session;
    private static String screenView;
    private static String screenType;
    private static String userID;

    static{
        if(session==null)startSession();
        screenType = "mobile";
        screenView = "NoPage";
    }

    public static String getSession() {
        return session;
    }
    public static void startSession(){
        session = KUtility.randomString(26)+ DateTime.now().toString("yMMddHHmmss");
    }

    public static void setSession(String session) {
        StatisticManager.session = session;
    }

    public static String getScreenView() {
        return screenView;
    }

    public static void setScreenView(String screenView) {
        StatisticManager.screenView = screenView;
    }

    public static String getScreenType() {
        return screenType;
    }

    public static void setScreenType(String screenType) {
        StatisticManager.screenType = screenType;
    }

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        StatisticManager.userID = userID;
    }

    public static void clearUserID(){
        StatisticManager.userID = null;
    }

    public static StatisticManager getInstance() {
        return manager;
    }

}
