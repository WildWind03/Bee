package com.bee.client.base;

import com.bee.client.entity.Product;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import java.util.List;

public interface LoadProductsInCategoryService {
    @GET("products")
    Observable<List<Product>> loadProducts(@Query("category_id") String id);
}
