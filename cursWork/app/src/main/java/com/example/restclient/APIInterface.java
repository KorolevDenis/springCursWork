package com.example.restclient;

import com.example.restclient.entity.Good;
import com.example.restclient.entity.JWTResponce;
import com.example.restclient.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {
    @POST("/register")
    public Call<User> register(@Body User user);

    @POST("/authenticate")
    public Call<JWTResponce> login(@Body User user);

    @GET("/getGood")
    public Call<Good> getGood(@Query("id") long id, @Header("Authorization") String token);

    @DELETE("/deleteGood")
    public Call<Good> deleteGood(@Query("id") long id, @Header("Authorization") String token);

    @GET("/getGoods")
    public Call<List<Good>> getGoods(@Header("Authorization") String token);

    @POST("/addGood")
    public Call<Good> addGood(@Body Good good, @Header("Authorization") String token);
}