package com.amandjaja.statistic;

public interface OnSend<T> {
    void onPrepare(T data);
    void onSent(T data);
    void onFail(T data);
}
