package com.example.restclient.entity;

import com.google.gson.annotations.SerializedName;

public class Warehouse2 {
    @SerializedName("id")
    private long id;

    @SerializedName("goodCount")
    private int goodCount;

    public Warehouse2() {
    }

    public Warehouse2(int goodCount) {
        this.goodCount = goodCount;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public long getId() {
        return id;
    }

    public int getGoodCount() {
        return goodCount;
    }
}