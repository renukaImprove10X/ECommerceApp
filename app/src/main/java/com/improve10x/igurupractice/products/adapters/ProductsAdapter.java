package com.improve10x.igurupractice.products.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.igurupractice.databinding.ProductItemBinding;
import com.improve10x.igurupractice.models.Product;
import com.improve10x.igurupractice.products.interfaces.OnProductActionListener;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsViewHolder>  {

    private List<Product> products;

    public void setListener(OnProductActionListener listener) {
        this.listener = listener;
    }

    private OnProductActionListener listener;
    public void setupData(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductItemBinding binding = ProductItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ProductsViewHolder viewHolder = new ProductsViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        holder.binding.setProduct(products.get(position));
        holder.binding.getRoot().setOnClickListener(v -> {
            listener.onProductClick(products.get(position).getId());
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

}
