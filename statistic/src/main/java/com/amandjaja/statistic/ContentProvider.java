package com.amandjaja.statistic;


import com.amandjaja.statistic.data.ContentData;

/**
 * Created by Dodi on 09/19/2017.
 */
public class ContentProvider extends Provider<ContentData, ContentProvider> {
    public ContentProvider(ContentData data) {
        super(data);
    }

    public ContentProvider setId(int id) {
        data.ID = String.valueOf(id);
        return this;
    }

    public ContentProvider setId(short id) {
        data.ID = String.valueOf(id);
        return this;
    }

    public ContentProvider setId(long id) {
        data.ID = String.valueOf(id);
        return this;
    }

    public ContentProvider setId(String id) {
        data.ID = id;
        return this;
    }

    public ContentProvider setType(String type) {
        data.type = type;
        return this;
    }

    public ContentProvider setCategory(String category) {
        data.category = category;
        return this;
    }

    public ContentProvider setAction(String action) {
        data.action = action;
        return this;
    }

    @Override
    public void send() {
        if(data.data.size()==0) StatisticManager.getInstance().send(data);
        else{
            StatisticManager.getInstance().send(data, new OnSend<ContentData>(){
                @Override
                public void onPrepare(ContentData data) {

                }

                @Override
                public void onFail(ContentData data) {
                    clearData();
                }

                @Override
                public void onSent(ContentData data) {
                    clearData();
                }
            });
        }
    }
}
