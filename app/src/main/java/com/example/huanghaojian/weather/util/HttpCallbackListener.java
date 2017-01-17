package com.example.huanghaojian.weather.util;

/**
 * Created by huanghaojian on 16/9/30.
 */

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
