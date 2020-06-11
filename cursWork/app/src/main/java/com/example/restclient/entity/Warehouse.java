package com.example.restclient.entity;

import com.google.gson.annotations.SerializedName;

public class Warehouse {
    @SerializedName("id")
    private long id = -1;

    @SerializedName("goodCount")
    private int goodCount;

    public Warehouse() {
    }

    public Warehouse(int goodCount) {
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
