package com.improve10x.igurupractice.products;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends BaseActivity {

    ActivityProductsBinding binding;
    ProductsAdapter adapter;
    List<Product> products = new ArrayList<>();
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
        handleApplyButtonClick();
        handleClearButtonClick();
        handleCategoryFilters();
    }

    private void handleCategoryFilters() {
        binding.categoriesChipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                List<String> categories = new ArrayList<>();
                for (Integer id: checkedIds) {
                    categories.add(String.valueOf(((Chip)findViewById(id)).getText()).toLowerCase());
                }
                List<Product> filteredProducts = products.stream()
                                .filter(product -> categories.contains(product.getCategory()))
                                .collect(Collectors.toList());
                adapter.setupData(filteredProducts);
            }
        });
    }

    private void handleClearButtonClick() {
        binding.clearBtn.setOnClickListener(v -> {
            binding.minPriceTxt.setText("");
            binding.maxPriceTxt.setText("");
            binding.applyBtn.setEnabled(false);
            binding.categoriesChipGroup.clearCheck();
            adapter.setupData(products);
        });
    }

    private void handleApplyButtonClick() {
        binding.minPriceTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding.minPriceTxt.getText().toString() != null && binding.maxPriceTxt.getText().toString() != null) {
                    binding.applyBtn.setEnabled(true);
                }
            }
        });
        binding.applyBtn.setOnClickListener(v -> {
            if (binding.minPriceTxt.getText().toString() != null && binding.maxPriceTxt.getText().toString() != null) {
                int min = Integer.parseInt(binding.minPriceTxt.getText().toString());
                int max = Integer.parseInt(binding.maxPriceTxt.getText().toString());
                List<Product> filteredProducts = products
                        .stream()
                        .filter(product -> product.getPrice() >= min && product.getPrice() <= max)
                        .collect(Collectors.toList());
                adapter.setupData(filteredProducts);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.store_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.filter_menu_item) {
            if (binding.priceLayout.getVisibility() == View.INVISIBLE || binding.priceLayout.getVisibility() == View.GONE) {
                binding.priceLayout.setVisibility(View.VISIBLE);
            } else {
                binding.priceLayout.setVisibility(View.GONE);
            }
            if (categoryName.equals("All")
                    && (binding.categoriesChipGroup.getVisibility() == View.INVISIBLE ||
                    binding.categoriesChipGroup.getVisibility() == View.GONE)) {
                binding.categoriesChipGroup.setVisibility(View.VISIBLE);
            } else {
                binding.categoriesChipGroup.setVisibility(View.GONE);
            }
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
}