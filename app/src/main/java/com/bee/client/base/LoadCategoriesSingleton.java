package com.bee.client.base;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoadCategoriesSingleton {

    private static class SingletonHolder {
        private final static String DEFAULT_SITE = "http://192.168.1.24:8080";
        public static LoadCategoriesService instance;

        static {
            Retrofit builder = new Retrofit.Builder()
                    .baseUrl(DEFAULT_SITE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            instance = builder.create(LoadCategoriesService.class);
        }
    }

    public static LoadCategoriesService getInstance() {
        return SingletonHolder.instance;
    }

    private LoadCategoriesSingleton() {

    }
}
