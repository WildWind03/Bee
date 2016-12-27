package com.bee.client.base;

import com.bee.client.entity.Comment;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

import java.util.List;

public interface LoadProductCommentsService {
    @GET("/{organisation}/{product}/comments")
    Observable<List<Comment>> loadComments(@Path("organisation") String organisation, @Path("product") String product);
}
