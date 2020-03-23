package com.amandjaja.statistic;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;

import com.amandjaja.statistic.data.BaseData;

/**
 * Created by Dodi on 09/19/2017.
 */
@SuppressWarnings("unchecked")
public abstract class Provider<A extends BaseData, B extends Provider> {
    protected A data;
    private Handler handler;
    private static final int TOTAL_TIMEOUT = 3000;
    private boolean isTimeout, isTotal;
    private int timeout=1000,total=5;

    public Provider(A data) {
        this.data = data;
        handler = new Handler(Looper.getMainLooper());
    }

    public B setUserId(int id) {
        data.userId = String.valueOf(id);
        return (B)this;
    }

    public B setUserId(short id) {
        data.userId = String.valueOf(id);
        return (B)this;
    }

    public B setUserId(long id) {
        data.userId = String.valueOf(id);
        return (B)this;
    }

    public B setUserId(String id) {
        data.userId = id;
        return (B)this;
    }

    public B setInteraction() {
        setInteraction(true);
        return (B)this;
    }

    public B setInteraction(boolean interaction) {
        data.isInteraction = interaction;
        return (B)this;
    }

    public B setCallforward(String callforward) {
        data.callforward = callforward;
        return (B)this;
    }

    public B putCustom(@NonNull String name, String value) {
        data.attributes.put(name,value);
        return (B) this;
    }

    public B putCustom(@NonNull String name, int value) {
        return putCustom(name, String.valueOf(value));
    }

    public B putCustom(@NonNull String name, long value) {
        return putCustom(name, String.valueOf(value));
    }

    public B putCustom(@NonNull String name, short value) {
        return putCustom(name, String.valueOf(value));
    }

    public B putCustom(@NonNull String name, float value) {
        return putCustom(name, String.valueOf(value));
    }

    public B putCustom(@NonNull String name, double value) {
        return putCustom(name, String.valueOf(value));
    }

    public B putCustom(@NonNull String name, char value) {
        return putCustom(name, String.valueOf(value));
    }

    public B clearCustom() {
        data.attributes.clear();
        return (B) this;
    }

    public B addData(A data){
        this.data.data.add(data);
        if(isTotal && this.data.data.size()>=total){
            send();
        }else if(isTotal){
            handler.removeCallbacksAndMessages(null);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    send();
                }
            },TOTAL_TIMEOUT);
        }
        if(isTimeout){
            handler.removeCallbacksAndMessages(null);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    send();
                }
            },timeout);
        }
        return (B)this;
    }

    public B clearData(){
        handler.removeCallbacksAndMessages(null);
        data.data.clear();
        return (B)this;
    }

    public B withTimeout(){
        isTimeout = true;
        isTotal = false;
        return (B)this;
    }

    public B withTimeout(int milliseconds){
        isTimeout = true;
        isTotal = false;
        timeout = milliseconds;
        return (B)this;
    }

    public B withTotal(){
        isTotal = true;
        isTimeout = false;
        return (B)this;
    }

    public B withTotal(int total){
        isTotal = true;
        isTimeout = false;
        this.total = total;
        return (B)this;
    }

    public abstract void send();

    public A build() {
        return data;
    }
}
