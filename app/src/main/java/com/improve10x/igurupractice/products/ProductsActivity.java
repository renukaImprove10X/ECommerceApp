package com.improve10x.igurupractice.products;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.divider.MaterialDividerItemDecoration;
import com.improve10x.igurupractice.BaseActivity;
import com.improve10x.igurupractice.R;
import com.improve10x.igurupractice.categories.CategoriesActivity;
import com.improve10x.igurupractice.categories.CategoriesAdapter;
import com.improve10x.igurupractice.databinding.ActivityProductsBinding;
import com.improve10x.igurupractice.models.Product;
import com.improve10x.igurupractice.productDetails.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends BaseActivity implements IFilterView {

    private ActivityProductsBinding binding;
    private ProductsAdapter adapter;
    private List<Product> products = new ArrayList<>();
    private String categoryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        categoryName = intent.getStringExtra("categoryName");
        getSupportActionBar().setTitle(categoryName);
        setupRecyclerView();
        setupAdapter();
        connectAdapter();
        fetchProducts();
    }

    void updateFilteredData(List<Product> products) {
        adapter.setupData(products);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.store_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.filter_menu_item) {
            FilterFragment filterFragment = (FilterFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.filter_fragment);
            filterFragment.showOrHideFilters(categoryName);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
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

    private void fetchProducts() {
        if (categoryName.equals("All")) {
            fetchAllProducts();
        } else {
            fetchCategoryProducts();
        }
    }

    private void fetchAllProducts() {
        Call<List<Product>> productsCall = service.fetchAllProducts();
        productsCall.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                products = response.body();
                adapter.setupData(products);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(ProductsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchCategoryProducts() {
        Call<List<Product>> productsCall = service.fetchProducts(categoryName);
        productsCall.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                products = response.body();
                adapter.setupData(products);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(ProductsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void updateFilteredProducts(List<Product> products) {
        adapter.setupData(products);
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }
}