package com.amandjaja.statistic;

import com.amandjaja.statistic.data.EventData;

/**
 * Created by Dodi on 09/19/2017.
 */
public class EventProvider extends Provider<EventData, EventProvider> {
    public EventProvider(EventData data) {
        super(data);
    }

    public EventProvider setCategory(String category) {
        data.category = category;
        return this;
    }

    public EventProvider setName(String name) {
        data.name = name;
        return this;
    }

    public EventProvider setType(String type) {
        data.type = type;
        return this;
    }

    public EventProvider setId(int id) {
        data.ID = String.valueOf(id);
        return this;
    }

    public EventProvider setId(short id) {
        data.ID = String.valueOf(id);
        return this;
    }

    public EventProvider setId(long id) {
        data.ID = String.valueOf(id);
        return this;
    }

    public EventProvider setId(String id) {
        data.ID = id;
        return this;
    }

    public EventProvider setSuccess() {
        return setSuccess(true);
    }

    public EventProvider setSuccess(boolean success) {
        data.isSuccess = success;
        return this;
    }

    public EventProvider setRejectionCode(String code) {
        data.rejectionCode = code;
        return this;
    }

    public EventProvider setRejectionMessage(String message) {
        data.rejectionMessage = message;
        return this;
    }
    @Override
    public void send() {
        if(data.data.size()==0) StatisticManager.getInstance().send(data, StatisticManager.HIT_EVENT);
        else{
            StatisticManager.getInstance().send(data, StatisticManager.HIT_EVENT_BULK, new OnSend<EventData>(){
                @Override
                public void onPrepare(EventData data) {

                }

                @Override
                public void onFail(EventData data) {
                    clearData();
                }

                @Override
                public void onSent(EventData data) {
                    clearData();
                }
            });
        }
    }
}
