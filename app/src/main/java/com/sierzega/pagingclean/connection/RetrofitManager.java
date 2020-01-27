package com.sierzega.pagingclean.connection;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static final String URL = "https://pokeapi.co/api/v2/";
    // Create Logger

    // Create OkHttp Client
    private static OkHttpClient.Builder okHttp = new OkHttpClient.Builder();
    // Create Retrofit Builder
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttp.build());
    // Create Retrofit Instance
    private static Retrofit retrofit = builder.build();

    public static <T> T buildService(Class<T> type) {
        return retrofit.create(type);
    }

}
