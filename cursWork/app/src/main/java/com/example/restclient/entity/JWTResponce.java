package com.example.restclient.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JWTResponce implements Serializable {

    @SerializedName("token")
    @Expose
    private String token = "";

    public JWTResponce() {
    }

    public JWTResponce(String token) {
        this.token = token;
    }

    public String getJwttoken() {
        return token;
    }
}

