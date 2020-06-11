package com.example.restclient.entity;

public class GoodRecord {
    private Long id;
    private String name;
    private Float priotity;

    public GoodRecord(Long id, String name, Float date) {
        this.id = id;
        this.name = name;
        this.priotity = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GoodRecord() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriotity(Float date) {
        this.priotity = date;
    }

    public String getName() {
        return name;
    }

    public Float getPriotity() {
        return priotity;
    }
}
