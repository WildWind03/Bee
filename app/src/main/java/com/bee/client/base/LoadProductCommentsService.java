package com.bee.client.base;

import com.bee.client.entity.Comment;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface LoadProductCommentsService {
    @GET("/products/{product}/comments")
    Call<List<Comment>> loadComments(@Path("product") String product);
}
