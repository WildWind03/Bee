package com.bee.client.base;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.logging.Logger;

public class LoadProductCommentsSingleton {
    private static final Logger logger = Logger.getLogger(LoadProductCommentsSingleton.class.getName());

    private static class SingletonHolder {
        private final static String DEFAULT_SITE = "http://androidtraining.noveogroup.com";
        public static LoadProductCommentsService instance;

        static {
            Retrofit builder = new Retrofit.Builder()
                    .baseUrl(DEFAULT_SITE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            instance = builder.create(LoadProductCommentsService.class);
        }
    }

    public static LoadProductCommentsService getInstance() {
        return LoadProductCommentsSingleton.SingletonHolder.instance;
    }

    private LoadProductCommentsSingleton() {

    }
}
