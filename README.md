[![Release](https://jitpack.io/v/amandjaja/statistic-android.svg)](https://jitpack.io/com/github/amandjaja/statistic-android)
# statistic-android

### Compatibilities
- Version 1.0.0 - Statistic v7

### Dependencies
- [KID-Utils](https://github.com/verzth/kid-utils)

    ```groovy
    implementation 'com.github.verzth:kid-utils:1.0.1@aar'
    ```

### How to Install
1. Add repository jitpack in your root project gradle.
    ```groovy
    allprojects {
        repositories {
            maven { url "https://jitpack.io" }
        }
    }
    ```
2. Add below code in dependencies in app gradle.
    ```groovy
    dependencies{
        implementation 'com.github.amandjaja:statistic-android:x.y.z@aar'
    }
    ```

### How to Use
1. There are several methods used to initialize Statistic Module.
    ```java
    @StatisticConfig(
            url = "https://example.com/fig/",
            key = "ACCESS_KEY"
    )
    public class BaseApplication extends Application {
        @Override
        public void onCreate() {
            // .....
            LocationDriver.init(this); // It used to get Latitude Longitude in statistic
            StatisticManager.init(this); // Specify the url and key in annotation
            StatisticManager.init(this, key); // Specify the key directly in Object, and use Config value url
            StatisticManager.init(this, url, key); // Specify the url and key directly in Object
            // .....
        }
    }
    ```

2. Use StatisticManager.**getInstance()** to get default instance of StatisticManager.
   
   | No | Method | Return | Description |
   | :--: | :------ | :------: | :----------- |
   | 1 | StatisticManager.**setListener(StatisticManager.EventListener listener)** | `void` | Set global StatisticManager EventListener |
   | 2 | StatisticManager.**send(<X extends BaseData> data)** | `void` | Send statistic data (BaseData: HitData, ContentData, EventData) through StatisticManager with default **HIT** flag |
   | 3 | StatisticManager.**send(<X extends BaseData> data, int flag)** | `void` | Send statistic data through StatisticManager with flag (HIT, HIT_CONTENT, HIT_EVENT, HIT_BULK, HIT_CONTENT_BULK, HIT_EVENT_BULK) |
   | 4 | StatisticManager.**send(<X extends BaseData> data, int flag, OnSend callback)** | `void` | Send statistic data through StatisticManager with flag and callback (**OnSend**) |
   
3. Static method which is available in StatisticManager.

   | No | Method | Return | Description |
   | :--: | :------ | :------: | :----------- |
   | 1 | StatisticManager.**init(Context context)** | `void` | Initialize Global StatisticManager Instance |
   | 2 | StatisticManager.**init(Context context, TCX tcx)** | `void` | Initialize Global StatisticManager Instance with Injected Standalone TCX |
   | 3 | StatisticManager.**init(Context context, String url)** | `void` | Initialize Global StatisticManager Instance with url parameter |
   | 4 | StatisticManager.**init(Context context, String url, TCX tcx)** | `void` | Initialize Global StatisticManager Instance with url parameter and Injected Standalone TCX |
   | 5 | StatisticManager.**with(Context context)** | `StatisticManager` | Create StatisticManager Instance |
   | 6 | StatisticManager.**with(Context context, TCX tcx)** | `StatisticManager` | Create StatisticManager Instance with Injected Standalone TCX |
   | 7 | StatisticManager.**with(Context context, String url)** | `StatisticManager` | Create StatisticManager Instance with url parameter |
   | 8 | StatisticManager.**with(Context context, String url, TCX tcx)** | `StatisticManager` | Create StatisticManager Instance with url parameter and Injected Standalone TCX |
   | 9 | StatisticManager.**createHit()** | `HitProvider` | Create HitProvider to construct HitData |
   | 10 | StatisticManager.**createContent()** | `ContentProvider` | Create ContentProvider to construct ContentData |
   | 11 | StatisticManager.**createEvent()** | `EventProvider` | Create EventProvider to construct EventData |
   | 12 | StatisticManager.**triggerStart(<X extends BaseData> data)** | `void` | Start counter in statistic data to count access time |
   | 13 | StatisticManager.**triggerEnd(<X extends BaseData> data)** | `void` | Stop counter in statistic data, **longTime** will return 0 if triggerStart never called |
   | 14 | StatisticManager.**getSession()** | `String` | Get Session ID |
   | 15 | StatisticManager.**startSession()** | `void` | Start new session and replace existing Session ID with the new one, auto call on application open, so you don't need to call it in standard flow |
   | 16 | StatisticManager.**setSession(String sessionID)** | `void` | Set Session ID with your preferred ID |
   | 17 | StatisticManager.**getScreenView()** | `String` | Get screen view name, default 'NoPage' |
   | 18 | StatisticManager.**setScreenView(String name)** | `void` | Set screen view name (Ex: 'MainActivity', 'HomeActivity', or your preferred name) |
   | 19 | StatisticManager.**getScreenType()** | `String` | Get screen type name, default 'mobile' |
   | 20 | StatisticManager.**setScreenType(String name)** | `void` | Set screen type name (Ex: 'mobile', 'android', 'tablet', or your preferred name) |
   | 21 | StatisticManager.**getUserID()** | `String` | Get User ID |
   | 22 | StatisticManager.**setUserID(String ID)** | `void` | Set User ID for specific logged in user tracking |
   | 23 | StatisticManager.**getInstance()** | `StatisticManager` | Get Global StatisticManager Instance |
   
4. Data Model.
   
   - **BaseData**:
   
     - BaseData.**sessionId**: `String`.
     - BaseData.**token**: `String`.
     - BaseData.**screenView**: `String`.
       
       Auto-fill when object Initialize with value which was set in `StatisticManager`.
       
     - BaseData.**screenType**: `String`.
       
       Auto-fill when object Initialize with value which was set in `StatisticManager`.
       
     - BaseData.**isInteraction**: `Boolean`.
     - BaseData.**age**: `Short`.
     - BaseData.**gender**: `String`.
     - BaseData.**userId**: `String`.
       
       Auto-fill when object Initialize with value which was set in `StatisticManager`.
       
     - BaseData.**carrier**: `String`.
     - BaseData.**device**: `Hashmap<String,Object>`.
       
       Auto-fill as long as have access to [Android Phone State](https://developer.android.com/reference/android/Manifest.permission#READ_PHONE_STATE).

     - BaseData.**getLongitude()**: `Double`.
       
       Auto-fill as long as have access
        to [Android Coarse Location](https://developer.android.com/reference/android/Manifest.permission#ACCESS_COARSE_LOCATION)
        or [Android Fine Location](https://developer.android.com/reference/android/Manifest.permission#ACCESS_FINE_LOCATION).
       
     - BaseData.**getLatitude()**: `Double`.
       
       Auto-fill as long as have access
        to [Android Coarse Location](https://developer.android.com/reference/android/Manifest.permission#ACCESS_COARSE_LOCATION)
        or [Android Fine Location](https://developer.android.com/reference/android/Manifest.permission#ACCESS_FINE_LOCATION).

     - BaseData.**setLatlng(double latitude, double longitude)**: `void`.

       Auto-fill as long as have access
        to [Android Coarse Location](https://developer.android.com/reference/android/Manifest.permission#ACCESS_COARSE_LOCATION)
        or [Android Fine Location](https://developer.android.com/reference/android/Manifest.permission#ACCESS_FINE_LOCATION).

     - BaseData.**location**: `HashMap<String,Object>`.

       Auto-fill as long as have access
        to [Android Coarse Location](https://developer.android.com/reference/android/Manifest.permission#ACCESS_COARSE_LOCATION)
        or [Android Fine Location](https://developer.android.com/reference/android/Manifest.permission#ACCESS_FINE_LOCATION).

     - BaseData.**logTime**: `String`.
     - BaseData.**logDate**: `String`.
     - BaseData.**longTime**: `Integer`.
     - BaseData.**callforward**: `String`.
     - BaseData.**data**: `ArrayList<BaseData>`.
     - BaseData.**attributes**: `Map<String,Object>`.
     - BaseData.**triggerStart()**: `void`.
       Start counter in statistic data to count access time.
     - BaseData.**triggerEnd()**: `void`.
       Stop counter in statistic data, **longTime** will return 0 if triggerStart never called.
       
   - **HitData**, extends BaseData without any additional data.
   - **ContentData**, extends BaseData with some additional data:
   
     - ContentData.**ID**: `String`.
     - ContentData.**type**: `String`.
     - ContentData.**category**: `String`.
     - ContentData.**action**: `String`.
     
   - **EventData**, extends BaseData with some additional data:
   
     - EventData.**ID**: `String`.
     - EventData.**category**: `String`.
     - EventData.**name**: `String`.
     - EventData.**type**: `String`.
     - EventData.**isOk**: `Integer`.
     - EventData.**status**: `String`.
     - EventData.**statusCode**: `String`.
     - EventData.**statusMessage**: `String`.
     
5. **Provider**, it help you get statistic data and manage the sender especially in bulk data.
 
    | No | Method | Return | Description |
    | :--: | :------ | :------: | :----------- |
    | 1  | Provider.**setUserId(String id)** | `X extends Provider` | Set the statistic data user id manually with **String** value |
    | 2  | Provider.**setUserId(int id)** | `X extends Provider` | Set the statistic data user id manually with **int** value |
    | 3  | Provider.**setUserId(long id)** | `X extends Provider` | Set the statistic data user id manually with **long** value |
    | 4  | Provider.**setUserId(short id)** | `X extends Provider` | Set the statistic data user id manually with **short** value |
    | 5  | Provider.**setInteraction(boolean interaction)** | `X extends Provider` | Set the statistic data interaction flag |
    | 6  | Provider.**setInteraction()** | `X extends Provider` | Set the statistic data interaction flag to **true** |
    | 7  | Provider.**setCallforward(String callforward)** | `X extends Provider` | Set the statistic data callforward link |
    | 8  | Provider.**putCustom(String name, String value)** | `X extends Provider` | Add custom attributes with **String** value |
    | 9  | Provider.**putCustom(String name, int value)** | `X extends Provider` | Add custom attributes with **int** value |
    | 10 | Provider.**putCustom(String name, long value)** | `X extends Provider` | Add custom attributes with **long** value |
    | 11 | Provider.**putCustom(String name, short value)** | `X extends Provider` | Add custom attributes with **short** value |
    | 12 | Provider.**putCustom(String name, float value)** | `X extends Provider` | Add custom attributes with **float** value |
    | 13 | Provider.**putCustom(String name, double value)** | `X extends Provider` | Add custom attributes with **double** value |
    | 14 | Provider.**putCustom(String name, char value)** | `X extends Provider` | Add custom attributes with **char** value |
    | 15 | Provider.**clearCustom()** | `X extends Provider` | Clear all custom value |
    | 16 | Provider.**addData(<Y extends BaseData> data)** | `X extends Provider` | Add data to bulk pool |
    | 17 | Provider.**clearData()** | `X extends Provider` | Clear all bulk data |
    | 18 | Provider.**withTimeout(int milliseconds)** | `X extends Provider` | Set bulk data auto send to timeout mode with given timeout in milliseconds |
    | 19 | Provider.**withTimeout()** | `X extends Provider` | Set bulk data auto send to timeout mode with default **5.000 milliseconds** |
    | 20 | Provider.**withTotal(int total)** | `X extends Provider` | Set bulk data auto send to total mode with given total data count. Total Mode has timeout **10.000 milliseconds** |
    | 21 | Provider.**withTotal()** | `X extends Provider` | Set bulk data auto send to total mode with default **10 data count**. Total Mode has timeout **10.000 milliseconds** |
    | 22 | Provider.**send()** | `void` | Send Statistic Data |
    | 23 | Provider.**build()** | `Y extends BaseData` | Get statistic data |
   
6. **HitProvider**, extends Provider without any modification.
7. **ContentProvider**, extends Provider with some additional methods.

   | No | Method | Return | Description |
   | :--: | :------ | :------: | :----------- |
   | 1 | ContentProvider.**setId(String id)** | `ContentProvider` | Set the statistic data id with **String** value |
   | 2 | ContentProvider.**setId(int id)** | `ContentProvider` | Set the statistic data id with **int** value |
   | 3 | ContentProvider.**setId(long id)** | `ContentProvider` | Set the statistic data id with **long** value |
   | 4 | ContentProvider.**setId(short id)** | `ContentProvider` | Set the statistic data id with **short** value |
   | 5 | ContentProvider.**setType(String type)** | `ContentProvider` | Set the statistic data type |
   | 6 | ContentProvider.**setCategory(String category)** | `ContentProvider` | Set the statistic data category |
   | 7 | ContentProvider.**setAction(String action)** | `ContentProvider` | Set the statistic data action |
   
8. **EventProvider**, extends Provider with some additional methods.

   | No | Method | Return | Description |
   | :--: | :------ | :------: | :----------- |
   | 1 | EventProvider.**setId(String id)** | `EventProvider` | Set the statistic data id with **String** value |
   | 2 | EventProvider.**setId(int id)** | `EventProvider` | Set the statistic data id with **int** value |
   | 3 | EventProvider.**setId(long id)** | `EventProvider` | Set the statistic data id with **long** value |
   | 4 | EventProvider.**setId(short id)** | `EventProvider` | Set the statistic data id with **short** value |
   | 5 | EventProvider.**setType(String type)** | `EventProvider` | Set the statistic data type |
   | 6 | EventProvider.**setCategory(String category)** | `EventProvider` | Set the statistic data category |
   | 7 | EventProvider.**setName(String name)** | `EventProvider` | Set the statistic data name |
   | 8 | EventProvider.**setOk(int ok)** | `EventProvider` | Set the statistic data `ok` flag |
   | 9 | EventProvider.**setOk()** | `EventProvider` | Set the statistic data `ok` flag to **1** |
   | 10 | EventProvider.**setStatus(String status)** | `EventProvider` | Set the statistic data status |
   | 11 | EventProvider.**setStatusCode(String code)** | `EventProvider` | Set the statistic data status code |
   | 12 | EventProvider.**setStatusMessage(String message)** | `EventProvider` | Set the statistic data status message |
   
9. **OnSend** interface.
   
    ```java
    package com.amandjaja.statistic;
    
    public interface OnSend<T> {
       void onPrepare(T data);
       void onSent(T data);
       void onFail(T data);
    }
    ```
    
    sample usage
    ```java
    class MyActivity extends Activity{
        @Override
        public void onStart(){ 
            // INSIDE ACTIVITY LIFECYCLE
            StatisticManager.getInstance().send(data, StatisticManager.HIT, new OnSend<HitData>(){
                @Override
                public void onPrepare(HitData data) {
                    // DO SOMETHING
                }
            
                @Override
                public void onFail(HitData data) {
                    // DO SOMETHING
                }
            
                @Override
                public void onSent(HitData data) {
                    // DO SOMETHING
                }
            });
        }
    }
    ```
   
10. **StatisticConfig** annotation to set the statistic api url.

    ```java
    @StatisticConfig(
       url = "https://example.com/fig/",
       key = "ACCESS_KEY"
    )
    public class BaseApplication extends Application {
       @Override
       public void onCreate() {
           // DO SOMETHING
       }
    }
    ```
