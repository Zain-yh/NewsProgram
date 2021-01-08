package com.example.network;

public class MyNetworkApi extends NetworkApi {

    public static String key = "54fc7e79bf28b48f";

    private static volatile MyNetworkApi mInstance;

    public static MyNetworkApi getInstance(){
        if (mInstance == null) {
            synchronized (MyNetworkApi.class){
                if (mInstance == null) {
                    mInstance = new MyNetworkApi();
                }
            }
        }
        return mInstance;
    }

    public static <T> T getService(Class service) {
        return (T) getInstance().getRetrofit(service).create(service);
    }

    @Override
    String getTest() {
        return null;
    }

    @Override
    String getNormal() {
        return "https://api.jisuapi.com/news/";
    }
}
