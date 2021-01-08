package com.example.network;

import java.util.HashMap;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class NetworkApi {

    private String baseUrl;
    private OkHttpClient okHttpClient;
    private static boolean isDebug;
    private HashMap<String, Retrofit> retrofitMap = new HashMap();

    public NetworkApi() {
        if (isDebug) {
            baseUrl = getTest();
        }
        baseUrl = getNormal();
    }

    abstract String getTest();

    abstract String getNormal();

    protected Retrofit getRetrofit(Class service) {
        if (retrofitMap.get(baseUrl + service.getName()) != null) {
            return retrofitMap.get(baseUrl + service.getName());
        }
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(baseUrl);
        retrofitBuilder.client(getOkHttpClient());
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = retrofitBuilder.build();
        retrofitMap.put(baseUrl + service.getName(), retrofit);
        return retrofit;
    }

    private OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder().build();
        }
        return okHttpClient;
    }

    //used for compose ObservableTransformer
    public <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer){
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable upstream) {
                Observable<T> observable = (Observable<T>) upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                observable.subscribe(observer);
                return observable;
            }
        };
    }
}
