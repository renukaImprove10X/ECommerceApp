package com.improve10x.igurupractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.improve10x.igurupractice.network.FakeApiClient;
import com.improve10x.igurupractice.network.FakeApiService;

public class BaseActivity extends AppCompatActivity {

    protected FakeApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupApi();
    }

    private void setupApi() {
        FakeApiClient apiClient = new FakeApiClient();
        service = apiClient.createService();
    }
}