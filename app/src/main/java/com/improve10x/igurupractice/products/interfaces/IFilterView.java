package com.improve10x.igurupractice.products.interfaces;

import com.improve10x.igurupractice.models.Product;

import java.util.List;

//TODO : Rename file
public interface IFilterView {
    void updateFilteredProducts(List<Product> products);
    List<Product> getProducts();
}
