package com.inspiration.makrandpawar.quotesdukan.rest;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitService {
    private Retrofit retrofit;
    private Retrofit retrofit2;

    public Retrofit getInstanceForQuote() {
        if (retrofit == null) {
            Interceptor interceptor = new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("authorization", "c6e2025366085672e9a5b3d86f179b57")
                            .build();
                    return chain.proceed(newRequest);
                }
            };
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
            okHttpBuilder.addInterceptor(interceptor);
            OkHttpClient okHttpClient = okHttpBuilder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://favqs.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public Retrofit getInstanceForOnThisDay() {
        if (retrofit2 == null) {
            Interceptor interceptor = new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder()
                            .build();
                    return chain.proceed(newRequest);
                }
            };
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
            okHttpBuilder.addInterceptor(interceptor);
            OkHttpClient okHttpClient = okHttpBuilder.build();

            retrofit2 = new Retrofit.Builder()
                    .baseUrl("http://history.muffinlabs.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit2;
    }
}
