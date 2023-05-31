package com.improve10x.igurupractice;

import org.junit.Test;

import static org.junit.Assert.*;

import com.improve10x.igurupractice.models.Product;
import com.improve10x.igurupractice.network.FakeApiClient;
import com.improve10x.igurupractice.network.FakeApiService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testCategories() throws IOException {
        FakeApiClient apiClient = new FakeApiClient();
        FakeApiService service = apiClient.createService();
        Call<List<String>> call = service.fetchCategories();
        List<String> categories = call.execute().body();
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
    }

    @Test
    public void testProducts() throws IOException {
        FakeApiClient apiClient = new FakeApiClient();
        FakeApiService service = apiClient.createService();
        Call<List<Product>> call = service.fetchProducts("jewelery");
        List<Product> products = call.execute().body();
        assertNotNull(products);
        assertFalse(products.isEmpty());
    }

    @Test
    public void testProductDetails() throws IOException {
        FakeApiClient apiClient = new FakeApiClient();
        FakeApiService service = apiClient.createService();
        Call<Product> call = service.fetchProductDetails("1");
        Product productDetails = call.execute().body();
        assertNotNull(productDetails);
    }
}