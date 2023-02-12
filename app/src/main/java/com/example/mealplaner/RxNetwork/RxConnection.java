package com.example.mealplaner.RxNetwork;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxConnection {
    private static retrofit2.Retrofit.Builder builder =
            new retrofit2.Retrofit.Builder()
                    .baseUrl("https://www.themealdb.com")
                   .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    private static Retrofit retrofit = builder.build();

    private static RxAPI postApi = retrofit.create(RxAPI.class);

    public static RxAPI getPostApi() {
        return postApi;
    }
}
