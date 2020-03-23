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

    public EventProvider setOk() {
        return setOk(1);
    }

    public EventProvider setOk(int ok) {
        data.isOk = ok;
        return this;
    }

    public EventProvider setStatus(String status) {
        data.status = status;
        return this;
    }

    public EventProvider setStatusCode(String code) {
        data.statusCode = code;
        return this;
    }

    public EventProvider setStatusMessage(String message) {
        data.statusMessage = message;
        return this;
    }
    @Override
    public void send() {
        if(data.data.size()==0) StatisticManager.getInstance().send(data);
        else{
            StatisticManager.getInstance().send(data, new OnSend<EventData>(){
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
