package com.example.shopmall.TypeActivity;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Type_GetRequest_Interface {
    @GET("aabb/json/TYPE_URL.json")
    Observable<CategoryBean> getCall();
    //注解里传入网络请求的部分URL地址
    //Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求里
    //如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    //采用Observable<...>接口
    //getCall()是接受网络请求数据的方法
}


