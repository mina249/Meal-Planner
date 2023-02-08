package com.example.mealplaner.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CatigoryConn {
    private static retrofit2.Retrofit.Builder builder =
            new retrofit2.Retrofit.Builder()
                    .baseUrl("https://www.themealdb.com")
                    .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = builder.build();

    private static APIs postApi = retrofit.create(APIs.class);

    public static APIs getPostApi() {
        return postApi;
    }
}
