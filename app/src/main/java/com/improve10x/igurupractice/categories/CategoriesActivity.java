package com.improve10x.igurupractice.categories;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.improve10x.igurupractice.BaseActivity;
import com.improve10x.igurupractice.databinding.ActivityCategoriesBinding;
import com.improve10x.igurupractice.products.activities.ProductsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesActivity extends BaseActivity {

    private ActivityCategoriesBinding binding;
    private CategoriesAdapter adapter;
    private List<String> categories = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupRecyclerView();
        setupAdapter();
        connectAdapter();
        fetchCategories();
    }

    private void setupRecyclerView() {
        binding.categoriesRv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void connectAdapter() {
        binding.categoriesRv.setAdapter(adapter);
    }

    private void setupAdapter() {
        adapter = new CategoriesAdapter();
        adapter.setupData(categories);
        adapter.setListener(categoryName -> {
            Intent intent = new Intent(this, ProductsActivity.class);
            intent.putExtra("categoryName", categoryName);
            startActivity(intent);
        });
    }

    private void fetchCategories() {
        Call<List<String>> categoriesCall = service.fetchCategories();
        categoriesCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                categories = response.body();
                categories.add("All");
                adapter.setupData(categories);
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(CategoriesActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}