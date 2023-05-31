package com.improve10x.igurupractice.network;

import com.improve10x.igurupractice.utils.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FakeApiClient {

    public FakeApiService createService(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_ECOMMERCE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        FakeApiService fakeApiService = retrofit.create(FakeApiService.class);
        return fakeApiService;
    }
}
