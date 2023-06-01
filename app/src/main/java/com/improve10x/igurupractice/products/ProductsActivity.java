package com.improve10x.igurupractice.products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.divider.MaterialDividerItemDecoration;
import com.improve10x.igurupractice.BaseActivity;
import com.improve10x.igurupractice.R;
import com.improve10x.igurupractice.categories.CategoriesActivity;
import com.improve10x.igurupractice.categories.CategoriesAdapter;
import com.improve10x.igurupractice.databinding.ActivityProductsBinding;
import com.improve10x.igurupractice.models.Product;
import com.improve10x.igurupractice.productDetails.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends BaseActivity {

    ActivityProductsBinding binding;
    ProductsAdapter adapter;
    List<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        String categoryName = intent.getStringExtra("categoryName");
        setupRecyclerView();
        setupAdapter();
        connectAdapter();
        fetchProducts(categoryName);
    }

    private void connectAdapter() {
        binding.productsRv.setAdapter(adapter);
    }

    private void setupAdapter() {
        adapter = new ProductsAdapter();
        adapter.setupData(products);
        adapter.setListener(id -> {
            Intent intent = new Intent(this, ProductDetailsActivity.class);
            intent.putExtra("id", id);
            Toast.makeText(this, "id : " + id, Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });
    }

    private void setupRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        RecyclerView.ItemDecoration decoration = new MaterialDividerItemDecoration(this, gridLayoutManager.getOrientation());
        DividerItemDecoration decorationVertical = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        binding.productsRv.addItemDecoration(decoration);
        binding.productsRv.addItemDecoration(decorationVertical);
        binding.productsRv.setLayoutManager(gridLayoutManager);
    }

    private void fetchProducts(String categoryName) {
        Call<List<Product>> productsCall = service.fetchProducts(categoryName);
        productsCall.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Toast.makeText(ProductsActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                Log.i("PRODUCTS", response.body().toString());
                adapter.setupData(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(ProductsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}