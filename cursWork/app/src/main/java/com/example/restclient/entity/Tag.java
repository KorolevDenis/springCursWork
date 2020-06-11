package com.example.restclient.entity;

public class Tag {
    private int count;
    private Type type;

    public enum Type {
        SALE,
        WR1,
        WR2
    }

    public Tag() {
    }

    public Tag(int count, Type type) {
        this.count = count;
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
