package com.example.news.inter;

import com.example.news.bean.AllNews;
import com.example.news.bean.Channels;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiInterface {

    @GET("get")
    Observable<AllNews> getAllNews(@Query("channel") String channel,
                                   @Query("num") int num,
                                   @Query("appkey") String appkey);

    @GET("channel")
    Observable<Channels> getChannels(@Query("appkey") String appkey);
}
