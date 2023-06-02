package com.improve10x.igurupractice.products.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.improve10x.igurupractice.databinding.FragmentFilterBinding;
import com.improve10x.igurupractice.models.Product;
import com.improve10x.igurupractice.products.interfaces.IFilterView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FilterFragment extends Fragment {

    private FragmentFilterBinding binding;
    private List<Product> filteredProducts = new ArrayList<>();
    private List<String> selectedCategories = new ArrayList<>();
    private Integer minPrice;
    private Integer maxPrice;
    private IFilterView filterView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentFilterBinding.inflate(getLayoutInflater(), container, false);
        if (getActivity() instanceof IFilterView) {
            filterView = (IFilterView) getActivity();
        }
        handleSortButtons();
        handleCategoryFilters();
        handleClearButtonClick();
        handleApplyButtonClick();
        return binding.getRoot();
    }

    private void handleSortButtons() {
        binding.sortLowHighBtn.setOnClickListener(v -> {
            filteredProducts.sort(Comparator.comparingDouble(value -> value.getPrice()));
            filterView.updateFilteredProducts(filteredProducts);
        });
        binding.sortHighLowBtn.setOnClickListener(v -> {
            filteredProducts.sort((o1, o2) -> (o2.getPrice().compareTo(o1.getPrice())));
            filterView.updateFilteredProducts(filteredProducts);
        });
    }

    private void handleCategoryFilters() {
        binding.categoriesChipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                selectedCategories.clear();
                for (Integer id : checkedIds) {
                    selectedCategories.add(String.valueOf(((Chip) binding.getRoot().findViewById(id)).getText()).toLowerCase());
                }
                applyFilters();
            }
        });
    }

    private void handleClearButtonClick() {
        binding.clearBtn.setOnClickListener(v -> {
            binding.minPriceTxt.setText("");
            binding.maxPriceTxt.setText("");
            binding.applyBtn.setEnabled(false);
            binding.categoriesChipGroup.clearCheck();
            filteredProducts.clear();
            filteredProducts.addAll(filterView.getProducts());
            filterView.updateFilteredProducts(filteredProducts);
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
            minPrice = Integer.parseInt(binding.minPriceTxt.getText().toString());
            maxPrice = Integer.parseInt(binding.maxPriceTxt.getText().toString());
            applyFilters();
        });
    }

    private void applyFilters() {
        filteredProducts.clear();
        filteredProducts.addAll(selectedCategories.isEmpty() ? filterView.getProducts() : filterView.getProducts().stream()
                .filter(product -> selectedCategories.contains(product.getCategory()))
                .collect(Collectors.toList()));
        if (minPrice != null && maxPrice != null) {
            filteredProducts = filteredProducts
                    .stream()
                    .filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
                    .collect(Collectors.toList());
        }
        filterView.updateFilteredProducts(filteredProducts);
    }

    public void showOrHideFilters(String categoryName) {
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
    }
}
