package com.improve10x.igurupractice.productDetails;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.improve10x.igurupractice.BaseActivity;
import com.improve10x.igurupractice.databinding.ActivityProductDetailsBinding;
import com.improve10x.igurupractice.models.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends BaseActivity {

    private ActivityProductDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 1);
        fetchProductDetails(id);
    }

    private void fetchProductDetails(int id) {
        Call<Product> productDetailsCall = service.fetchProductDetails(id);
        productDetailsCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                binding.setProduct(response.body());
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ProductDetailsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}