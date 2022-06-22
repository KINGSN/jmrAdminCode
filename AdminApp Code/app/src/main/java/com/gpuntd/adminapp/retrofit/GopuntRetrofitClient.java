package com.gpuntd.adminapp.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GopuntRetrofitClient {
    static GopuntRetrofitClient instance = null;
    String baseUrl = "https://darwinbark.com/projects/gopunt/api_v1/";
    Retrofit retrofit;
    GopuntAPI gopuntAPI = null;

    private GopuntRetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gopuntAPI = retrofit.create(GopuntAPI.class);
    }

    static synchronized public GopuntRetrofitClient getInstance() {
        if (instance == null)
            instance = new GopuntRetrofitClient();
        return instance;
    }

    public GopuntAPI getApi() {
        return gopuntAPI;
    }

}
