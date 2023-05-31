package com.improve10x.igurupractice.categories;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.igurupractice.databinding.CategoryItemBinding;

public class CategoriesViewHolder extends RecyclerView.ViewHolder {
    CategoryItemBinding binding;
    public CategoriesViewHolder(CategoryItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
