package com.improve10x.igurupractice.products;

import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.igurupractice.databinding.CategoryItemBinding;
import com.improve10x.igurupractice.databinding.ProductItemBinding;

public class ProductsViewHolder extends RecyclerView.ViewHolder {
    ProductItemBinding binding;
    public ProductsViewHolder(ProductItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
