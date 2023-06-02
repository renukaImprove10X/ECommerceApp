package com.improve10x.igurupractice.network;

import com.improve10x.igurupractice.models.Product;
import com.improve10x.igurupractice.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FakeApiService {

    @GET(Constants.CATEGORIES_ENDPOINT)
    Call<List<String>> fetchCategories();

    @GET("products/category/{categoryName}")
    Call<List<Product>> fetchProducts(@Path("categoryName") String categoryName);

    @GET("products/{id}")
    Call<Product> fetchProductDetails(@Path("id") int id);

    @GET("products")
    Call<List<Product>> fetchAllProducts();

}
