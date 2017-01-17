package com.example.huanghaojian.weather.model;

/**
 * Created by huanghaojian on 16/9/29.
 */

public class Province {
    private  String province_name;
    private String province_code;
    private  int id;
    public String getProvinceName(){
        return province_name;
    }
    public String getProvinceCode(){
        return province_code;
    }
    public int getId(){
        return id;
    }
    public void setProvinceName(String provinceName){
        province_name=provinceName;
    }
    public void setProvinceCode(String provinceCode){
        province_code=provinceCode;
    }
    public void setId(int id){
        this.id=id;
    }
}
