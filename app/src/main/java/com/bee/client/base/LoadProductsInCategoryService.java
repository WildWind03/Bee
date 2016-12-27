package com.bee.client.base;

import com.bee.client.entity.Product;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

import java.util.List;

public interface LoadProductsInCategoryService {
    @GET("categories/{category}/products")
    Observable<List<Product>> loadProducts(@Path("category") String category);
}
