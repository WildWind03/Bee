package com.bee.client.base;

import com.bee.client.entity.Product;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface LoadProductsInCategoryService {
    @GET("categories/{category}/products")
    Call<List<Product>> loadProducts(@Path("category") String category);
}
