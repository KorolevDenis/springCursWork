package com.example.restclient.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Good {
    @SerializedName("id")
    private long id = -1;

    @SerializedName("name")
    private String name = "";

    @SerializedName("priority")
    private float priority = -1;

    @SerializedName("sales")
    private List<Sale> sales = new ArrayList<>();

    @SerializedName("warehouse1Goods")
    private List<Warehouse> warehouse1Goods = new ArrayList<>();

    @SerializedName("warehouse2Goods")
    private List<Warehouse> warehouse2Goods = new ArrayList<>();

    public Good() {
    }

    public Good(String name, Float priority, List<Sale> sales, List<Warehouse> warehouse1Goods,
                List<Warehouse> warehouse2Goods) {
        this.name = name;
        this.priority = priority;
        this.sales = sales;
        this.warehouse1Goods = warehouse1Goods;
        this.warehouse2Goods = warehouse2Goods;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(Float priority) {
        this.priority = priority;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    public void setWarehouse1Goods(List<Warehouse> warehouse1Goods) {
        this.warehouse1Goods = warehouse1Goods;
    }

    public void setWarehouse2Goods(List<Warehouse> warehouse2Goods) {
        this.warehouse2Goods = warehouse2Goods;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getPriority() {
        return priority;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public List<Warehouse> getWarehouse1Goods() {
        return warehouse1Goods;
    }

    public List<Warehouse> getWarehouse2Goods() {
        return warehouse2Goods;
    }
}
