package com.example.huanghaojian.weather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.example.huanghaojian.weather.receiver.AutoUpdateReceiver;
import com.example.huanghaojian.weather.util.HttpCallbackListener;
import com.example.huanghaojian.weather.util.HttpUtil;
import com.example.huanghaojian.weather.util.Utillity;

/**
 * Created by huanghaojian on 16/10/10.
 */

public class AutoUpdateService extends Service {
    public IBinder onBind(Intent intent){
        return  null;
    }
    public int OnStartCommand(Intent intent,int flags,int startId){
        new Thread(new Runnable() {
            @Override
            public void run() {
               updateWeather();
            }
        }).start();
        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        int time=8*60*60*1000;
        long triggerAtTime= SystemClock.elapsedRealtime()+time;
        Intent i=new Intent(this, AutoUpdateReceiver.class);
        PendingIntent pi=PendingIntent.getBroadcast(this,0,i,0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent,flags,startId);
    }
    private void updateWeather(){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        String weatherCode=sharedPreferences.getString("weather_code","");
        String address="http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Utillity.handleWeatherResponse(AutoUpdateService.this,response);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
