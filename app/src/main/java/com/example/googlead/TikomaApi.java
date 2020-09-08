package com.example.googlead;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

public interface TikomaApi {


    @GET("hours")
    Call<List<Category>> getCategory();

    @GET("skilliq")
    Call<List<Category>> getSkills();
}
