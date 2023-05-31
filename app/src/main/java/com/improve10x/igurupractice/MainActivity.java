package com.improve10x.igurupractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.improve10x.igurupractice.databinding.ActivityMainBinding;
import com.improve10x.igurupractice.network.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private QuestionsService questionsService;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupApi();

    }

    private void setupApi() {
        ApiClient apiClient = new ApiClient();
        questionsService = apiClient.createService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchQuestions();
    }

    private void fetchQuestions() {
        Call<List<Question>> questionsCall = questionsService.fetchQuestions();
        questionsCall.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                Log.i("QUESTIONS", response.body().toString());
                setData(response.body());
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(List<Question> questions) {
        binding.question.setText(questions.get(0).question);
        binding.option1Rb.setText(questions.get(0).optionA);
        binding.option2Rb.setText(questions.get(0).optionB);
        binding.option3Rb.setText(questions.get(0).optionC);
        binding.ooption4Rb.setText(questions.get(0).optionD);

    }
}