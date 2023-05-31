package com.improve10x.igurupractice.network;

import com.improve10x.igurupractice.utils.Constants;
import com.improve10x.igurupractice.QuestionsService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public QuestionsService createService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        QuestionsService questionsService = retrofit.create(QuestionsService.class);
        return questionsService;
    }
}
