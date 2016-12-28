package com.bee.client.base;

import com.bee.client.entity.Comment;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import java.util.List;

public interface LoadProductCommentsService {
    @GET("/comments")
    Observable<List<Comment>> loadComments(@Query("product_id") String id);
}
