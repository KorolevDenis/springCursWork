package com.example.restclient.entity;

import com.google.gson.annotations.SerializedName;

public class Sale {
    @SerializedName("id")
    private long id = -1;

    @SerializedName("goodCount")
    private int goodCount;

    @SerializedName("createDate")
    private String createDate;

    public Sale() {
    }

    public Sale(int goodCount, String createDate) {
        this.goodCount = goodCount;
        this.createDate = createDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public long getId() {
        return id;
    }

    public int getGoodCount() {
        return goodCount;
    }

    public String getCreateDate() {
        return createDate;
    }
}