package com.bee.client.base;

import com.bee.client.entity.Category;
import retrofit2.http.GET;
import rx.Observable;

import java.util.List;

public interface LoadCategoriesService {
    @GET("categories")
    Observable<List<Category>> loadCategories();
}
