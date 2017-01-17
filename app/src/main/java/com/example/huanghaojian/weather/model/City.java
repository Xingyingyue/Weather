package com.example.huanghaojian.weather.model;

/**
 * Created by huanghaojian on 16/9/29.
 */

public class City {
    private  String city_name;
    private String city_code;
    private int provinceId;
    private int id;
    public String getCityName(){
        return city_name;
    }
    public String getCityCode(){
        return city_code;
    }
    public int getId(){
        return id;
    }
    public void setCityName(String cityName){
        city_name=cityName;
    }
    public void setCityCode(String cityCode){
        city_code=cityCode;
    }
    public void setId(int id){
        this.id=id;
    }
    public int getProvinceId(){return provinceId;}
    public void setProvinceId(int provinceId){this.provinceId=provinceId;}
}
