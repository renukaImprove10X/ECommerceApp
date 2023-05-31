package com.improve10x.igurupractice;

import com.improve10x.igurupractice.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuestionsService {

    @GET(Constants.QUESTIONS_ENDPOINT)
    Call<List<Question>> fetchQuestions();

}
