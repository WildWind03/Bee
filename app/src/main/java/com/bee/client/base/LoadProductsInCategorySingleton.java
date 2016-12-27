package com.bee.client.base;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.logging.Logger;

public class LoadProductsInCategorySingleton {
    private static final Logger logger = Logger.getLogger(LoadProductsInCategorySingleton.class.getName());

    private static class SingletonHolder {
        private final static String DEFAULT_SITE = "http://androidtraining.noveogroup.com";
        public static LoadProductsInCategoryService instance;

        static {
            Retrofit builder = new Retrofit.Builder()
                    .baseUrl(DEFAULT_SITE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            instance = builder.create(LoadProductsInCategoryService.class);
        }
    }

    public static LoadProductsInCategoryService getInstance() {
        return LoadProductsInCategorySingleton.SingletonHolder.instance;
    }

    private LoadProductsInCategorySingleton() {

    }
}
