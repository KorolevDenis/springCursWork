package com.example.restclient.entity;

import com.google.gson.annotations.SerializedName;

public class Role {
    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    public Role(String name) {
        this.id = id;
        this.name = name;
    }

    public Role() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
