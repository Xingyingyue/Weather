package com.example.huanghaojian.weather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.huanghaojian.weather.model.City;
import com.example.huanghaojian.weather.model.County;
import com.example.huanghaojian.weather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huanghaojian on 16/9/29.
 */

public class WeatherDBOperation {
    public static final String DB_NAME = "weather";
    public static final int version = 1;
    private SQLiteDatabase db;
    //利用单例模型确保WeatherDBOperation类为单例类
    private static WeatherDBOperation weatherDBOperation;
    private WeatherDBOperation(Context context) {
        WeatherOpenHelper dbHelper = new WeatherOpenHelper(context, DB_NAME, null, version);
        db=dbHelper.getWritableDatabase();
    }

    public  static synchronized WeatherDBOperation getInstance(Context context) {
        if (weatherDBOperation == null) {
            weatherDBOperation = new WeatherDBOperation(context);
        }
        return weatherDBOperation;
    }

    //
    //储存Province实例到数据库
    public void saveProvinceToDB(Province province) {
        if(province!=null){
            ContentValues values=new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            db.insert("Province",null,values);
        }
    }

    //
    //从数据库读取全国所有省份信息
    public List<Province> readProvinceFromDB() {
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            } while (cursor.moveToNext());
        }
        if(cursor!=null) {
            cursor.close();
        }
            return list;
        }

    //
    //储存City实例到数据库
    public void saveCityToDB(City city) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("city_name", city.getCityName());
        contentValues.put("city_code", city.getCityCode());
        contentValues.put("province_id", city.getProvinceId());
        db.insert("City", null, contentValues);
    }

    //
    //从数据库读取全国城市信息
    public List<City> readCityFromDB(int provinceId) {
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City", null, "province_id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
        if(cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
                list.add(city);
            } while (cursor.moveToNext());
        }
        if(cursor!=null) {
            cursor.close();
        }
        return list;
    }

    //
    //储存County实例到数据库
    public void saveCountyToDB(County county) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("county_name", county.getCountyName());
        contentValues.put("county_code", county.getCountyCode());
        contentValues.put("city_id", county.getCityId());
        db.insert("County", null, contentValues);
    }
     //
    //从数据库读取全国县的信息
    public List<County> readCountyFromDB(int cityId) {
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County", null, "city_id=?", new String[]{String.valueOf(cityId)}, null, null, null);
        if(cursor.moveToFirst()) {
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
                list.add(county);
            } while (cursor.moveToNext());
        }
        if(cursor!=null) {
            cursor.close();
        }
        return list;
    }
    //
}