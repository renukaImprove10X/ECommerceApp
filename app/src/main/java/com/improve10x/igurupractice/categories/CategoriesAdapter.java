package com.improve10x.igurupractice.categories;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.igurupractice.databinding.CategoryItemBinding;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesViewHolder>  {

    private List<String> categories;

    public void setListener(OnCategoryActionListener listener) {
        this.listener = listener;
    }

    private OnCategoryActionListener listener;
    public void setupData(List<String> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryItemBinding binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        CategoriesViewHolder viewHolder = new CategoriesViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        holder.binding.setCategory(categories.get(position));
        holder.binding.getRoot().setOnClickListener(v -> {
            listener.onCategoryClick(categories.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

}
