package com.example.huanghaojian.weather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huanghaojian.weather.R;
import com.example.huanghaojian.weather.service.AutoUpdateService;
import com.example.huanghaojian.weather.util.HttpCallbackListener;
import com.example.huanghaojian.weather.util.HttpUtil;
import com.example.huanghaojian.weather.util.Utillity;

/**
 * Created by huanghaojian on 16/10/8.
 */

public class Weather extends Activity implements View.OnClickListener{
    private TextView cityName;
    private TextView publishTime;
    private LinearLayout weatherInformation;
    private TextView currentDateText;
    private  TextView weatherStateText;
    private  TextView temp1Text;
    private TextView temp2Text;
    private Button switchCity;
    private Button refresh;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather);
        cityName=(TextView)findViewById(R.id.city_name);
        publishTime=(TextView)findViewById(R.id.publish_time);
        weatherInformation=(LinearLayout)findViewById(R.id.weather_information);
        currentDateText=(TextView)findViewById(R.id.weather_time);
        weatherStateText=(TextView)findViewById(R.id.weather_state);
        temp1Text=(TextView)findViewById(R.id.temp1);
        temp2Text=(TextView)findViewById(R.id.temp2);

        switchCity=(Button)findViewById(R.id.switch_city);
        refresh=(Button)findViewById(R.id.refresh);
        switchCity.setOnClickListener(this);
        refresh.setOnClickListener(this);

        String countyCode=getIntent().getStringExtra("county_code");
        if(!TextUtils.isEmpty(countyCode)){
            publishTime.setText("加载中");
            weatherInformation.setSystemUiVisibility(View.INVISIBLE);
            cityName.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        }else{
            showWeather();
        }
    }
    public void queryWeatherCode(String countyCode){
        String address="http://www.weather.com.cn/data/list3/city"+countyCode+".xml";
        queryFromServer(address,"county_code");
    }
    public void queryWeatherInformation(String weatherCode){
        String address="http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
        queryFromServer(address,"weather_code");
    }
    public void queryFromServer(String address, final String type){
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if("county_code".equals(type)){
                    String weatherCode=Utillity.handleWeatherCodeResponse(response);
                    queryWeatherInformation(weatherCode);
                }else if("weather_code".equals(type)){
                    Utillity.handleWeatherResponse(Weather.this,response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishTime.setText("加载失败");
                    }
                });
            }
        });
    }
    public void showWeather(){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        cityName.setText(sharedPreferences.getString("city_name",""));
        publishTime.setText(sharedPreferences.getString("publish_time",""));
        currentDateText.setText(sharedPreferences.getString("current_date",""));
        weatherStateText.setText(sharedPreferences.getString("weather_state",""));
        temp1Text.setText(sharedPreferences.getString("temp1",""));
        temp2Text.setText(sharedPreferences.getString("temp2",""));
        weatherInformation.setVisibility(View.VISIBLE);
        cityName.setVisibility(View.VISIBLE);
        Intent intent=new Intent(Weather.this, AutoUpdateService.class);
        startService(intent);
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.switch_city:
                Intent intent=new Intent(this,ChooseArea.class);
                intent.putExtra("from_weather_activity",true);
                startActivity(intent);
                break;
            case R.id.refresh:
                publishTime.setText("加载中");
                weatherInformation.setVisibility(View.INVISIBLE);
                cityName.setVisibility(View.INVISIBLE);
                SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
                String weatherCode=sharedPreferences.getString("weather_code","");
                if(!TextUtils.isEmpty(weatherCode)){
                    queryWeatherInformation(weatherCode);
                }
                break;
            default:
                break;
        }
    }
}
